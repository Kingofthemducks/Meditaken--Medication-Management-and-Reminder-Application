package com.laszlo.meditaken


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.laszlo.meditaken.ui.theme.MeditakenTheme

@Composable
fun SettingsScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Settings Screen")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {  }) {
            Text("Change Settings")
        }
        Button(onClick = { navController.popBackStack() }) {
            Text("Back")
        }

    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    MeditakenTheme {
        SettingsScreen(navController = rememberNavController())
    }
}
