package com.laszlo.meditaken

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController

@Composable
fun LockScreen(
    navController: NavController,
    activity: FragmentActivity
) {
    val context = LocalContext.current
    val executor = ContextCompat.getMainExecutor(context)

    val lockType = remember { getLockType(context) }

    if (lockType == "none") {
        NoLockScreenMessage()
        return
    }

    val authenticators =
        BiometricManager.Authenticators.BIOMETRIC_STRONG or
                BiometricManager.Authenticators.DEVICE_CREDENTIAL

    val biometricManager = BiometricManager.from(context)
    val canAuth = biometricManager.canAuthenticate(authenticators)

    var authError by remember { mutableStateOf<String?>(null) }

    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Unlock Meditaken")
        .setSubtitle("Please use a password for your lockscreen instead of pin or pattern!")
        .setAllowedAuthenticators(authenticators)
        .build()

    val biometricPrompt = BiometricPrompt(
        activity,
        executor,
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                navController.navigate("home") {
                    popUpTo("lock") { inclusive = true }
                }
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                authError = errString.toString()
            }

            override fun onAuthenticationFailed() {
                authError = "Authentication failed"
            }
        }
    )

    LaunchedEffect(Unit) {
        if (canAuth == BiometricManager.BIOMETRIC_SUCCESS) {
            biometricPrompt.authenticate(promptInfo)
        } else {
            authError = "Your device cannot authenticate. Please set up a secure lock screen."
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Meditaken Locked", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(16.dp))

            if (authError != null) {
                Text(authError!!, color = MaterialTheme.colorScheme.error)
                Spacer(Modifier.height(16.dp))
            }

            Button(onClick = { biometricPrompt.authenticate(promptInfo) }) {
                Text("Unlock")
            }
        }
    }
}

@Composable
fun NoLockScreenMessage() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Your device has no screen lock enabled.\n\n" +
                        "To use Meditaken, please set up a secure lock screen (PIN, password, or pattern).",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
