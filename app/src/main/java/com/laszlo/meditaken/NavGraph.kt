package com.laszlo.meditaken

import LoginScreen
import SignUpScreen
import androidx.compose.runtime.*
import androidx.navigation.compose.*

@Composable
fun NavGraph(startDestination: String = "login") {
    val navController = rememberNavController()
    val medications = remember { mutableStateListOf<Medication>() }

    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") { LoginScreen(navController) }
        composable("signup") { SignUpScreen(navController) }
        composable("home") { HomeScreen(navController, medications) }
        composable("addMedication") { AddMedicationScreen(navController, medications) }
        composable("medDetail/{medId}") { backStackEntry ->
            val medId = backStackEntry.arguments?.getString("medId") ?: ""
            MedicationDetailScreen(navController, medId, medications)
        }
        composable("settings") { SettingsScreen(navController) }
    }
}

