package com.laszlo.meditaken

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.content.ContextCompat
import com.laszlo.meditaken.ui.theme.MeditakenTheme

private const val ENABLE_AUTO_LOGIN = true

class MainActivity : ComponentActivity() {

    private val requestNotificationPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (!granted) {
            // Optional: Show a message explaining notifications won't work
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                requestNotificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }


        val prefs = getSharedPreferences("MeditakenPrefs", MODE_PRIVATE)

        val startDestination = if (ENABLE_AUTO_LOGIN) {
            if (prefs.getBoolean("isLoggedIn", false)) "home" else "login"
        } else {
            "login"
        }

        val darkModeEnabled = prefs.getBoolean("dark_mode", false)
        val alarmUriString = prefs.getString("alarm_sound_uri", null)

        setContent {
            val darkModeState = remember { mutableStateOf(darkModeEnabled) }
            val selectedAlarmUri = remember {
                mutableStateOf(alarmUriString?.let { Uri.parse(it) })
            }

            MeditakenTheme(darkTheme = darkModeState.value) {
                NavGraph(
                    startDestination = startDestination,
                    darkModeState = darkModeState,
                    selectedAlarmSoundUri = selectedAlarmUri
                )
            }
        }
    }
}
