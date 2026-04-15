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
fun MedicationDetailScreen(
    navController: NavController,
    medId: String,
    medications: SnapshotStateList<Medication>,
    darkModeState: MutableState<Boolean>? = null
) {
    val med = medications.find { it.id == medId }

    val daysLabel = remember(med) {
        "Every day"
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
                "Medication Details",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (med != null) {
                Text("Name: ${med.name}", color = MaterialTheme.colorScheme.onBackground)
                Text("Dosage: ${med.dosage}", color = MaterialTheme.colorScheme.onBackground)
                Text("Time: ${med.time}", color = MaterialTheme.colorScheme.onBackground)
                Text("Frequency: ${med.frequency} per day", color = MaterialTheme.colorScheme.onBackground)
                Text("Notes: ${med.notes ?: "None"}", color = MaterialTheme.colorScheme.onBackground)
                Text("Days: $daysLabel", color = MaterialTheme.colorScheme.onBackground)
            } else {
                Text("Medication not found.", color = MaterialTheme.colorScheme.onBackground)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Back")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MedicationDetailScreenPreview() {
    val sampleMedsList = remember {
        mutableStateListOf(
            Medication("1","Aspirin","1 pill","08:00",1,""),
            Medication("2","Vitamin D","2 pills","12:00",1,""),
            Medication("3","Metformin","1 pill","18:00",1,"")
        )
    }
    val darkModeState = remember { mutableStateOf(false) }

    MeditakenTheme(darkTheme = darkModeState.value) {
        MedicationDetailScreen(
            navController = rememberNavController(),
            medId = "1",
            medications = sampleMedsList,
            darkModeState = darkModeState
        )
    }
}
