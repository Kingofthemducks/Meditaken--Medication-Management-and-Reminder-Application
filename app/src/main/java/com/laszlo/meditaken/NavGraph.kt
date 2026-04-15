package com.laszlo.meditaken

import android.net.Uri
import androidx.fragment.app.FragmentActivity
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.*
import com.laszlo.meditaken.database.KeyStoreManager
import com.laszlo.meditaken.database.MeditakenDatabase

@Composable
fun NavGraph(
    startDestination: String = "lock",
    darkModeState: MutableState<Boolean>,
    selectedAlarmSoundUri: MutableState<Uri?>,
    activity: FragmentActivity   // ← FIXED HERE
) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val db = MeditakenDatabase.getInstance(context, KeyStoreManager.getDatabaseKey(context))

    val medications = remember { mutableStateListOf<Medication>() }

    NavHost(navController = navController, startDestination = startDestination) {

        composable("lock") {
            LockScreen(navController = navController, activity = activity)
        }

        composable("home") {
            HomeScreen(
                navController = navController,
                medicationDao = db.medicationDao(),
                darkModeState = darkModeState
            )
        }

        composable("addMedication") {
            AddMedicationScreen(
                navController = navController,
                medications = medications,
                darkModeState = darkModeState
            )
        }

        composable("addMedication/{medId}") { backStackEntry ->
            val medId = backStackEntry.arguments?.getString("medId")
            AddMedicationScreen(
                navController = navController,
                medications = medications,
                editMedId = medId,
                darkModeState = darkModeState
            )
        }

        composable("medDetail/{medId}") { backStackEntry ->
            val medId = backStackEntry.arguments?.getString("medId") ?: ""
            MedicationDetailScreen(
                navController = navController,
                medId = medId,
                medications = medications,
                darkModeState = darkModeState
            )
        }


    }
}
