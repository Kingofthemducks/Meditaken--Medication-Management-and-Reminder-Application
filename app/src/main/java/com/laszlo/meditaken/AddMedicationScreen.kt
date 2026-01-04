package com.laszlo.meditaken

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.laszlo.meditaken.ui.theme.MeditakenTheme

@Composable
fun AddMedicationScreen(
    navController: NavController,
    medications: SnapshotStateList<Medication>
) {
    var name by remember { mutableStateOf("") }
    var dosage by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Add Medication Screen", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        TextField(value = name, onValueChange = { name = it }, label = { Text("Medication Name") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = dosage, onValueChange = { dosage = it }, label = { Text("Dosage") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = time, onValueChange = { time = it }, label = { Text("Time") })
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (name.isNotEmpty() && dosage.isNotEmpty() && time.isNotEmpty()) {
                    val newMed = Medication(
                        id = (medications.size + 1).toString(),
                        name = name,
                        dosage = dosage,
                        time = time
                    )
                    medications.add(newMed)
                    navController.popBackStack()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddMedicationScreenPreview() {
    val sampleMeds = remember {
        mutableStateListOf(
            Medication("1", "Aspirin", "1 pill", "08:00 AM")
        )
    }

    MeditakenTheme {
        AddMedicationScreen(navController = rememberNavController(), medications = sampleMeds)
    }
}
