package com.laszlo.meditaken

import android.media.MediaPlayer
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.laszlo.meditaken.ui.theme.MeditakenTheme

@Composable
fun SettingsScreen(
    navController: NavController,
    isDarkMode: Boolean,
    onDarkModeToggle: (Boolean) -> Unit,
    selectedAlarmSoundUri: Uri?,
    onAlarmSoundSelected: (Uri?) -> Unit
) {
    val context = LocalContext.current

    // Load only mp3 files
    val localSounds = remember {
        getLocalMusicFiles(context).filter { it.first.endsWith(".mp3", ignoreCase = true) }
    }

    var expanded by remember { mutableStateOf(false) }

    var selectedSoundName by remember {
        mutableStateOf(
            localSounds.firstOrNull { it.second == selectedAlarmSoundUri }?.first
                ?: "Default"
        )
    }

    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }

    // Wrap in Surface to respect dark mode background
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Settings", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onBackground)
            Spacer(modifier = Modifier.height(16.dp))

            // Dark Mode Toggle
            Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                Text("Dark Mode", color = MaterialTheme.colorScheme.onBackground)
                Spacer(modifier = Modifier.width(8.dp))
                Switch(
                    checked = isDarkMode,
                    onCheckedChange = { onDarkModeToggle(it) }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Alarm Sound Selection
            Text("Select Alarm Sound", color = MaterialTheme.colorScheme.onBackground)
            Spacer(modifier = Modifier.height(8.dp))

            Box {
                Button(onClick = { expanded = true }) {
                    Text(selectedSoundName)
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    // Default option
                    DropdownMenuItem(
                        text = { Text("Default") },
                        onClick = {
                            selectedSoundName = "Default"
                            onAlarmSoundSelected(null)
                            expanded = false

                            mediaPlayer?.stop()
                            mediaPlayer?.release()
                            mediaPlayer = null
                        }
                    )

                    // List mp3 files
                    localSounds.forEach { (name, uri) ->
                        DropdownMenuItem(
                            text = { Text(name) },
                            onClick = {
                                selectedSoundName = name
                                onAlarmSoundSelected(uri)
                                expanded = false

                                mediaPlayer?.stop()
                                mediaPlayer?.release()
                                mediaPlayer = MediaPlayer.create(context, uri)
                                mediaPlayer?.start()
                            }
                        )
                    }
                }
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
fun SettingsScreenPreview() {
    var isDark by remember { mutableStateOf(false) }
    var alarmUri: Uri? by remember { mutableStateOf(null) }

    MeditakenTheme(darkTheme = isDark) {
        SettingsScreen(
            navController = rememberNavController(),
            isDarkMode = isDark,
            onDarkModeToggle = { isDark = it },
            selectedAlarmSoundUri = alarmUri,
            onAlarmSoundSelected = { alarmUri = it }
        )
    }
}
