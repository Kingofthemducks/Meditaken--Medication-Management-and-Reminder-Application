package com.laszlo.meditaken.alarm

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.laszlo.meditaken.R
import android.net.Uri
import android.os.Build
import androidx.core.content.ContextCompat

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val medicationId =
            intent.getIntExtra(AlarmActions.EXTRA_MED_ID, -1)

        if (medicationId == -1) return

        val serviceIntent = Intent(context, AlarmSoundService::class.java).apply {
            action = AlarmActions.ACTION_START
            putExtra(AlarmActions.EXTRA_MED_ID, medicationId)
        }

        context.startForegroundService(serviceIntent)
    }
}