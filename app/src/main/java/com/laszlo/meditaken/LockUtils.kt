package com.laszlo.meditaken

import android.content.Context
import android.os.Build
import android.provider.Settings

fun getLockType(context: Context): String {
    val keyguard = context.getSystemService(Context.KEYGUARD_SERVICE) as android.app.KeyguardManager

    if (!keyguard.isDeviceSecure) return "none"

    val isEmulator =
        Build.FINGERPRINT.contains("generic", ignoreCase = true) ||
                Build.MODEL.contains("Emulator", ignoreCase = true) ||
                Build.MODEL.contains("Android SDK", ignoreCase = true)

    val raw = Settings.Secure.getString(
        context.contentResolver,
        "lockscreen.password_type"
    )

    if (isEmulator && (raw == null || raw == "0" || raw == "0x0")) {
        return "pattern"
    }

    if (raw == null) return "secure"

    return when {
        raw.contains("pattern", ignoreCase = true) -> "pattern"
        raw.contains("pin", ignoreCase = true) -> "pin"
        raw.contains("password", ignoreCase = true) -> "password"
        else -> "secure"
    }
}
