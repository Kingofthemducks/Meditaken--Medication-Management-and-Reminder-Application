package com.laszlo.meditaken

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.laszlo.meditaken.database.MedicationDao
import com.laszlo.meditaken.ui.theme.MeditakenTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    medicationDao: MedicationDao? = null,
    previewMedications: SnapshotStateList<Medication>? = null,
    darkModeState: MutableState<Boolean>? = null
) {
    // Always use a mutableStateList for the UI
    val medications = remember { mutableStateListOf<Medication>() }

    // Load preview medications if provided
    LaunchedEffect(previewMedications) {
        previewMedications?.let {
            medications.clear()
            medications.addAll(it)
        }
    }

    // Load medications from DB if medicationDao is provided
    LaunchedEffect(medicationDao) {
        medicationDao?.let { dao ->
            val medsFromDb = dao.getAll()
            medications.clear()
            medications.addAll(
                medsFromDb.map { entity ->
                    Medication(
                        id = entity.id.toString(),
                        name = entity.name,
                        dosage = entity.dosage,
                        time = entity.time,
                        frequency = entity.frequency,
                        notes = entity.notes ?: ""
                    )
                }
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Home") },
                actions = {
                    IconButton(onClick = { navController.navigate("settings") }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(medications) { med ->
                    Text(
                        "${med.name} - ${med.dosage} at ${med.time}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { navController.navigate("addMedication/${med.id}") }
                            .padding(vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.navigate("addMedication") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Medication")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val sampleMedsList = remember { mutableStateListOf(*sampleMedications.toTypedArray()) }

    // Provide darkModeState preview
    val darkModeState = remember { mutableStateOf(false) }

    MeditakenTheme(darkTheme = darkModeState.value) {
        HomeScreen(
            navController = rememberNavController(),
            previewMedications = sampleMedsList,
            darkModeState = darkModeState
        )
    }
}
