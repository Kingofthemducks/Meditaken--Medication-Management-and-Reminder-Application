package com.laszlo.meditaken

import android.app.TimePickerDialog
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.laszlo.meditaken.alarm.AlarmScheduler
import com.laszlo.meditaken.database.KeyStoreManager
import com.laszlo.meditaken.database.MeditakenDatabase
import com.laszlo.meditaken.database.MedicationEntity
import kotlinx.coroutines.launch
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMedicationScreen(
    navController: NavController,
    medications: SnapshotStateList<Medication>,
    editMedId: String? = null,
    darkModeState: MutableState<Boolean>? = null
) {
    val context = LocalContext.current
    val db = remember {
        MeditakenDatabase.getInstance(
            context,
            KeyStoreManager.getDatabaseKey(context)
        )
    }
    val dao = db.medicationDao()
    val scope = rememberCoroutineScope()

    var existingMed by remember { mutableStateOf<MedicationEntity?>(null) }
    val isEditing = existingMed != null

    var name by remember { mutableStateOf("") }
    var dosage by remember { mutableStateOf("") }
    var frequency by remember { mutableStateOf(1) }
    var timeInputs by remember { mutableStateOf(listOf("08:00")) }
    var notes by remember { mutableStateOf("") }

    val dayOptions = listOf(
        "MON" to "M",
        "TUE" to "T",
        "WED" to "W",
        "THU" to "T",
        "FRI" to "F",
        "SAT" to "S",
        "SUN" to "S"
    )

    var selectedDays by remember { mutableStateOf(mutableSetOf<String>()) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    // Load from DB
    LaunchedEffect(editMedId) {
        if (editMedId != null) {
            val entity = dao.getById(editMedId.toLong())
            if (entity != null) {
                existingMed = entity

                name = entity.name
                dosage = entity.dosage
                frequency = entity.frequency
                timeInputs = entity.time.split(",")
                notes = entity.notes ?: ""

                selectedDays = if (entity.days == "ALL") {
                    dayOptions.map { it.first }.toMutableSet()
                } else {
                    entity.days.split(",").filter { it.isNotBlank() }.toMutableSet()
                }
            }
        } else {
            selectedDays = mutableSetOf()
        }
    }

    // Keep timeInputs aligned with frequency
    LaunchedEffect(frequency) {
        val current = timeInputs.toMutableList()
        when {
            current.size < frequency -> repeat(frequency - current.size) { current.add("08:00") }
            current.size > frequency -> repeat(current.size - frequency) { current.removeAt(current.lastIndex) }
        }
        timeInputs = current
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditing) "Edit Medication" else "Add Medication") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->

        //  Entire screen scrolls properly
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = dosage,
                onValueChange = { dosage = it },
                label = { Text("Dosage") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            FrequencyDropdown(
                frequency = frequency,
                onFrequencyChange = { frequency = it }
            )

            Spacer(Modifier.height(8.dp))

            timeInputs.forEachIndexed { index, time ->
                TimePickerRow(
                    label = "Time ${index + 1}",
                    time = time,
                    onTimeChange = { newTime ->
                        val list = timeInputs.toMutableList()
                        list[index] = newTime
                        timeInputs = list
                    }
                )
                Spacer(Modifier.height(4.dp))
            }

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Notes") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            Text("Days")
            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                dayOptions.forEach { (code, label) ->
                    FilterChip(
                        selected = selectedDays.contains(code),
                        onClick = {
                            selectedDays = selectedDays.toMutableSet().apply {
                                if (contains(code)) remove(code) else add(code)
                            }
                        },
                        label = { Text(label) }
                    )
                }
            }

            Spacer(Modifier.height(8.dp))

            TextButton(onClick = {
                selectedDays = dayOptions.map { it.first }.toMutableSet()
            }) {
                Text("Select All Days")
            }

            Spacer(Modifier.height(24.dp))
            //Never allow empty days - treat as ALL


            Button(
                onClick = {
                    if (name.isBlank() || dosage.isBlank() || timeInputs.any { it.isBlank() }) {
                        Toast.makeText(context, "Please fill all required fields", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    val daysString =
                        if (selectedDays.isEmpty() || selectedDays.size == dayOptions.size) {
                            "ALL"
                        } else {
                            selectedDays.joinToString(",")
                        }

                    val timesString = timeInputs.joinToString(",")

                    scope.launch {
                        if (isEditing) {
                            val old = existingMed!!

                            AlarmScheduler.cancelMedicationAlarms(
                                context, old.id, old.time.split(","), old.days
                            )

                            val updated = old.copy(
                                name = name,
                                dosage = dosage,
                                time = timesString,
                                frequency = frequency,
                                notes = notes,
                                days = daysString
                            )

                            dao.update(updated)

                            AlarmScheduler.scheduleMedicationAlarms(
                                context = context,
                                medId = updated.id.toLong(),
                                name = updated.name,
                                times = updated.time.split(","),
                                days = updated.days,
                                dosage = updated.dosage
                            )

                        } else {
                            val entity = MedicationEntity(
                                name = name,
                                dosage = dosage,
                                time = timesString,
                                frequency = frequency,
                                notes = notes,
                                days = daysString
                            )

                            val newId = dao.insert(entity)

                            AlarmScheduler.scheduleMedicationAlarms(
                                context = context,
                                medId = newId,
                                name = entity.name,
                                times = entity.time.split(","),
                                days = entity.days,
                                dosage = entity.dosage
                            )
                        }

                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (isEditing) "Update" else "Save")
            }

            // Delete button now always visible because screen scrolls
            if (isEditing) {
                Spacer(Modifier.height(8.dp))

                OutlinedButton(
                    onClick = { showDeleteDialog = true },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Delete")
                }
            }

            Spacer(Modifier.height(40.dp))
        }

        if (showDeleteDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                title = { Text("Delete Medication") },
                text = { Text("Are you sure you want to delete this medication?") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showDeleteDialog = false
                            val entity = existingMed!!

                            scope.launch {
                                AlarmScheduler.cancelMedicationAlarms(
                                    context, entity.id, entity.time.split(","), entity.days
                                )
                                dao.delete(entity)
                                navController.popBackStack()
                            }
                        }
                    ) {
                        Text("Delete", color = MaterialTheme.colorScheme.error)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}

@Composable
private fun FrequencyDropdown(
    frequency: Int,
    onFrequencyChange: (Int) -> Unit
) {
    val options = listOf(1, 2, 3, 4, 5)
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedTextField(
            value = "$frequency times per day",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth().clickable { expanded = true },
            enabled = false,
            label = { Text("Times per day") },
            trailingIcon = {
                Icon(Icons.Default.ArrowDropDown, contentDescription = "Select frequency")
            }
        )

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { value ->
                DropdownMenuItem(
                    text = { Text("$value times per day") },
                    onClick = {
                        onFrequencyChange(value)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
private fun TimePickerRow(
    label: String,
    time: String,
    onTimeChange: (String) -> Unit
) {
    val context = LocalContext.current
    val parts = time.split(":")
    val hour = parts.getOrNull(0)?.toIntOrNull() ?: 8
    val minute = parts.getOrNull(1)?.toIntOrNull() ?: 0

    OutlinedButton(
        onClick = {
            val cal = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
            }

            TimePickerDialog(
                context,
                { _, h, m ->
                    val newTime = String.format("%02d:%02d", h, m)
                    onTimeChange(newTime)
                },
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("$label: $time")
    }
}
