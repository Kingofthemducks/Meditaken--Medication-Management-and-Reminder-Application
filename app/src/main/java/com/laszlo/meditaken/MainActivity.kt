package com.laszlo.meditaken

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.laszlo.meditaken.ui.theme.MeditakenTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPrefs = getSharedPreferences("MeditakenPrefs", MODE_PRIVATE)
        val isLoggedIn = sharedPrefs.getBoolean("isLoggedIn", false)

        setContent {
            MeditakenTheme {
                if (isLoggedIn) {
                    NavGraph(startDestination = "home")
                } else {
                    NavGraph(startDestination = "login")
                }
            }
        }
    }
}