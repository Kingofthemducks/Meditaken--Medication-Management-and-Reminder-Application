package com.laszlo.meditaken

import android.app.TimePickerDialog
import android.widget.TimePicker
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.laszlo.meditaken.database.KeyStoreManager
import com.laszlo.meditaken.database.MeditakenDatabase
import com.laszlo.meditaken.database.MedicationEntity
import kotlinx.coroutines.launch
import com.laszlo.meditaken.ui.theme.MeditakenTheme
import java.util.*
import com.laszlo.meditaken.alarm.AlarmScheduler

@Composable
fun AddMedicationScreen(
    navController: NavController,
    medications: SnapshotStateList<Medication>,
    editMedId: String? = null,
    darkModeState: MutableState<Boolean>? = null
) {
    val context = LocalContext.current
    val db = MeditakenDatabase.getInstance(context, KeyStoreManager.getDatabaseKey(context))
    val scope = rememberCoroutineScope()

    var name by remember { mutableStateOf("") }
    var dosage by remember { mutableStateOf("") }
    var frequency by remember { mutableStateOf(1) }
    var times by remember { mutableStateOf(List(frequency) { "" }) }
    var notes by remember { mutableStateOf("") }

    LaunchedEffect(editMedId) {
        if (editMedId != null) {
            val medEntity = db.medicationDao().getById(editMedId.toInt())
            medEntity?.let { entity ->
                name = entity.name
                dosage = entity.dosage
                times = entity.time.split(",")
                frequency = times.size
                notes = entity.notes ?: ""
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                if (editMedId == null) "Add Medication" else "Edit Medication",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Medication Name") }
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = dosage,
                onValueChange = { dosage = it },
                label = { Text("Dosage") }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text("Times per day", color = MaterialTheme.colorScheme.onBackground)

            var expanded by remember { mutableStateOf(false) }
            Box {
                Button(onClick = { expanded = true }) {
                    Text(frequency.toString())
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    (1..5).forEach { freq ->
                        DropdownMenuItem(
                            text = { Text(freq.toString()) },
                            onClick = {
                                frequency = freq
                                times = List(freq) { index -> times.getOrElse(index) { "" } }
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            times.forEachIndexed { index, timeValue ->
                TimePickerField(index, timeValue) { newTime ->
                    times = times.toMutableList().also { it[index] = newTime }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            TextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Notes (optional)") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // ===== SAVE / UPDATE =====
            Button(
                onClick = {
                    if (name.isNotBlank() && dosage.isNotBlank() && times.all { it.isNotBlank() }) {
                        scope.launch {
                            val timeString = times.joinToString(",")

                            if (editMedId == null) {
                                val newMed = MedicationEntity(
                                    name = name,
                                    dosage = dosage,
                                    time = timeString,
                                    frequency = frequency,
                                    notes = notes
                                )

                                val newId = db.medicationDao().insert(newMed).toInt()

                                medications.add(
                                    Medication(
                                        newId.toString(),
                                        name,
                                        dosage,
                                        timeString,
                                        frequency,
                                        notes
                                    )
                                )

                                times.forEach { time ->
                                    AlarmScheduler.scheduleMedicationAlarm(
                                        context = context,
                                        medicationId = newId,
                                        time = time
                                    )
                                }

                            } else {
                                val idInt = editMedId.toInt()
                                val existingMed = db.medicationDao().getById(idInt)

                                existingMed?.let { med ->
                                    med.time.split(",").forEach { oldTime ->
                                        AlarmScheduler.cancelMedicationAlarm(
                                            context = context,
                                            medicationId = idInt,
                                            time = oldTime
                                        )
                                    }

                                    med.name = name
                                    med.dosage = dosage
                                    med.time = timeString
                                    med.frequency = frequency
                                    med.notes = notes
                                    db.medicationDao().update(med)

                                    times.forEach { time ->
                                        AlarmScheduler.scheduleMedicationAlarm(
                                            context = context,
                                            medicationId = idInt,
                                            time = time
                                        )
                                    }

                                    val index = medications.indexOfFirst { it.id == editMedId }
                                    if (index != -1) {
                                        medications[index] = Medication(
                                            med.id.toString(),
                                            med.name,
                                            med.dosage,
                                            timeString,
                                            frequency,
                                            notes
                                        )
                                    }
                                }
                            }

                            navController.popBackStack()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (editMedId == null) "Save" else "Update")
            }

            Spacer(modifier = Modifier.height(8.dp))

            // ✅ BACK BUTTON RESTORED
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Back")
            }
        }
    }
}

@Composable
fun TimePickerField(index: Int, currentTime: String, onTimeSelected: (String) -> Unit) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    if (currentTime.isNotBlank()) {
        val parts = currentTime.split(":")
        if (parts.size == 2) {
            calendar.set(Calendar.HOUR_OF_DAY, parts[0].toIntOrNull() ?: 0)
            calendar.set(Calendar.MINUTE, parts[1].toIntOrNull() ?: 0)
        }
    }

    var displayTime by remember { mutableStateOf(currentTime) }

    Button(
        onClick = {
            val picker = TimePickerDialog(
                context,
                { _: TimePicker, hour: Int, minute: Int ->
                    val formatted = String.format("%02d:%02d", hour, minute)
                    displayTime = formatted
                    onTimeSelected(formatted)
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            )
            picker.show()
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(if (displayTime.isNotBlank()) displayTime else "Set time ${index + 1}")
    }
}

@Preview(showBackground = true)
@Composable
fun AddMedicationScreenPreview() {
    val sampleMedsList = remember {
        mutableStateListOf(
            Medication("1", "Aspirin", "1 pill", "08:00", 1, ""),
            Medication("2", "Vitamin D", "2 pills", "12:00", 1, ""),
            Medication("3", "Metformin", "1 pill", "18:00", 1, "")
        )
    }

    val darkModeState = remember { mutableStateOf(false) }

    MeditakenTheme(darkTheme = darkModeState.value) {
        AddMedicationScreen(
            navController = rememberNavController(),
            medications = sampleMedsList,
            darkModeState = darkModeState
        )
    }
}
