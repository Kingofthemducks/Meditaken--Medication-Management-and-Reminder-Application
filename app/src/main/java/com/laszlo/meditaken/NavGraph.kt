package com.laszlo.meditaken

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.*
import com.laszlo.meditaken.database.KeyStoreManager
import com.laszlo.meditaken.database.MeditakenDatabase

@Composable
fun NavGraph(startDestination: String = "login") {
    val navController = rememberNavController()
    val context = LocalContext.current
    val db = MeditakenDatabase.getInstance(context, KeyStoreManager.getDatabaseKey(context))
    val medicationDao = db.medicationDao()

    val medications = remember { mutableStateListOf<Medication>() }

    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") { LoginScreen(navController) }
        composable("signup") { SignUpScreen(navController) }

        composable("home") {
            HomeScreen(
                navController = navController,
                medicationDao = db.medicationDao()
            )
        }

        composable("addMedication") {
            AddMedicationScreen(navController, medications)
        }

        composable("addMedication/{medId}") { backStackEntry ->
            val medId = backStackEntry.arguments?.getString("medId")
            AddMedicationScreen(navController, medications, editMedId = medId)
        }

        composable("medDetail/{medId}") { backStackEntry ->
            val medId = backStackEntry.arguments?.getString("medId") ?: ""
            MedicationDetailScreen(navController, medId, medications)
        }

        composable("settings") { SettingsScreen(navController) }
    }
}
