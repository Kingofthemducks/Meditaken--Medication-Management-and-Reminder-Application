package com.laszlo.meditaken

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

@Composable
fun AddMedicationScreen(
    navController: NavController,
    medications: SnapshotStateList<Medication>,
    editMedId: String? = null
) {
    var name by remember { mutableStateOf("") }
    var dosage by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }

    val context = LocalContext.current
    val db = MeditakenDatabase.getInstance(context, KeyStoreManager.getDatabaseKey(context))
    val scope = rememberCoroutineScope()

    // Load medication for editing
    LaunchedEffect(editMedId) {
        if (editMedId != null) {
            val medEntity = db.medicationDao().getById(editMedId.toInt())
            medEntity?.let { entity ->
                name = entity.name
                dosage = entity.dosage
                time = entity.time
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            if (editMedId == null) "Add Medication" else "Edit Medication",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextField(value = name, onValueChange = { name = it }, label = { Text("Medication Name") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = dosage, onValueChange = { dosage = it }, label = { Text("Dosage") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = time, onValueChange = { time = it }, label = { Text("Time") })
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (name.isNotBlank() && dosage.isNotBlank() && time.isNotBlank()) {
                    scope.launch {
                        if (editMedId == null) {
                            val newMedEntity = MedicationEntity(name = name, dosage = dosage, time = time)
                            val newId = db.medicationDao().insert(newMedEntity).toInt()
                            medications.add(Medication(newId.toString(), name, dosage, time))
                        } else {
                            val idInt = editMedId.toInt()
                            val existingMed = db.medicationDao().getById(idInt)
                            existingMed?.let { med ->
                                med.name = name
                                med.dosage = dosage
                                med.time = time
                                db.medicationDao().update(med)
                                val index = medications.indexOfFirst { it.id == editMedId }
                                if (index != -1) {
                                    medications[index] = Medication(med.id.toString(), med.name, med.dosage, med.time)
                                }
                            }
                        }
                        name = ""
                        dosage = ""
                        time = ""
                        navController.popBackStack()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (editMedId == null) "Save" else "Update")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { navController.popBackStack() }, modifier = Modifier.fillMaxWidth()) {
            Text("Back")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddMedicationScreenPreview() {
    val sampleMedsList = remember { mutableStateListOf(*sampleMedications.toTypedArray()) }
    MeditakenTheme {
        AddMedicationScreen(navController = rememberNavController(), medications = sampleMedsList)
    }
}
