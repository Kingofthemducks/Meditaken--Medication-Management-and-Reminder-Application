package com.laszlo.meditaken

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.laszlo.meditaken.ui.theme.MeditakenTheme

private const val ENABLE_AUTO_LOGIN = true //  make sure its true when done

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("MeditakenPrefs", MODE_PRIVATE)

        val startDestination = if (ENABLE_AUTO_LOGIN) {
            val isLoggedIn = prefs.getBoolean("isLoggedIn", false)
            if (isLoggedIn) "home" else "login"
        } else {

            "login"
        }

        setContent {
            MeditakenTheme {
                NavGraph(startDestination = startDestination)
            }
        }
    }
}
