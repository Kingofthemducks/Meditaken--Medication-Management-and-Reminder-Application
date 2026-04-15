package com.laszlo.meditaken

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import com.laszlo.meditaken.database.MedicationDao
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    medicationDao: MedicationDao? = null,
    previewMedications: SnapshotStateList<Medication>? = null,
    darkModeState: MutableState<Boolean>? = null
) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("MeditakenPrefs", Context.MODE_PRIVATE)

    val medications = remember { mutableStateListOf<Medication>() }

    // Load preview meds (for preview mode)
    LaunchedEffect(previewMedications) {
        previewMedications?.let {
            medications.clear()
            medications.addAll(it)
        }
    }

    // Load real meds
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
                        notes = entity.notes,
                        days = entity.days
                    )
                }
            )
        }
    }



    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Hello, Is your Meditaken?") },
                actions = {
                    if (darkModeState != null) {
                        IconButton(onClick = { darkModeState.value = !darkModeState.value }) {
                            Icon(Icons.Default.Settings, contentDescription = "Settings")
                        }
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


            // Card-based medication list
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(medications) { med ->
                    val nextTime = getNextUpcomingTime(med)
                    val daysLabel = formatDaysLabel(med.days)

                    MedicationCard(
                        name = med.name,
                        dosage = med.dosage,
                        nextTime = nextTime,
                        days = daysLabel,
                        onClick = {
                            navController.navigate("addMedication/${med.id}")
                        }
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

private fun getNextUpcomingTime(med: Medication): String {
    val times = med.time.split(",")
    val now = Calendar.getInstance()

    val upcoming = times
        .mapNotNull { time ->
            val parts = time.split(":")
            if (parts.size < 2) return@mapNotNull null

            val hour = parts[0].toIntOrNull() ?: return@mapNotNull null
            val minute = parts[1].toIntOrNull() ?: return@mapNotNull null

            val cal = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }

            if (cal.before(now)) cal.add(Calendar.DAY_OF_YEAR, 1)

            cal.timeInMillis to time
        }
        .minByOrNull { it.first }

    return upcoming?.second ?: med.time.split(",").first()
}

private fun formatDaysLabel(days: String): String {
    if (days == "ALL") return "Every day"
    if (days.isBlank()) return "Every day"

    return days.split(",")
        .filter { it.isNotBlank() }
        .joinToString(" ") { code ->
            when (code) {
                "MON" -> "M"
                "TUE" -> "T"
                "WED" -> "W"
                "THU" -> "T"
                "FRI" -> "F"
                "SAT" -> "S"
                "SUN" -> "S"
                else -> ""
            }
        }
}

@Composable
fun MedicationCard(
    name: String,
    dosage: String,
    nextTime: String,
    days: String,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            //  Name + Dosage , Next time
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "$name  •  $dosage",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = "Next: $nextTime",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(Modifier.height(10.dp))

            // Days at the bottom
            Text(
                text = "Days: $days",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
