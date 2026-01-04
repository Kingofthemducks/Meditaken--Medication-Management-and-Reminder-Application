package com.laszlo.meditaken

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.laszlo.meditaken.ui.theme.MeditakenTheme

@Composable
fun MedicationDetailScreen(
    navController: NavController,
    medId: String,
    medications: SnapshotStateList<Medication>
) {
    val med = medications.find { it.id == medId }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        med?.let {
            Text(it.name, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Dosage: ${it.dosage}")
            Text("Time: ${it.time}")
        } ?: Text("Medication not found")

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.popBackStack() }) {
            Text("Back")
        }
    }
}




@Preview(showBackground = true)
@Composable
fun MedicationDetailScreenPreview() {
    // Sample medications for preview
    val sampleMeds = remember {
        mutableStateListOf(
            Medication("1", "Aspirin", "1 pill", "08:00 AM"),
            Medication("2", "Vitamin D", "2 pills", "12:00 PM")
        )
    }

    MeditakenTheme {
        MedicationDetailScreen(
            navController = rememberNavController(),
            medId = "1", // match one of the sample meds
            medications = sampleMeds
        )
    }
}
