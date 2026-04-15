package com.laszlo.meditaken.alarm

import android.app.*
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.laszlo.meditaken.MainActivity
import com.laszlo.meditaken.R

class AlarmSoundService : Service() {

    companion object {
        private const val CHANNEL_ID = "med_alarm"
    }

    private val players = mutableMapOf<Long, MediaPlayer>()

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val medId = intent?.getLongExtra(AlarmActions.EXTRA_MED_ID, -1L) ?: return START_NOT_STICKY
        val medName = intent.getStringExtra("med_name") ?: "Medication"
        val medDosage = intent.getStringExtra("med_dosage") ?: ""

        when (intent.action) {
            AlarmActions.ACTION_START -> startAlarm(medId, medName, medDosage)
            AlarmActions.ACTION_STOP -> stopAlarm(medId)
            AlarmActions.ACTION_SNOOZE -> snoozeAlarm(medId)
        }

        return START_NOT_STICKY
    }

    private fun startAlarm(medId: Long, medName: String, medDosage: String) {
        if (players.containsKey(medId)) return

        // Start foreground FIRST
        startForeground(medId.toInt(), buildNotification(medId, medName, medDosage))

        val player = MediaPlayer.create(this, R.raw.default_alarm)
        player.isLooping = true
        player.start()

        players[medId] = player
    }

    private fun stopAlarm(medId: Long) {
        players[medId]?.apply {
            stop()
            release()
        }
        players.remove(medId)

        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    private fun snoozeAlarm(medId: Long) {
        stopAlarm(medId)
        AlarmScheduler.snooze(this, medId, 10)
    }

    private fun buildNotification(medId: Long, medName: String, medDosage: String): Notification {

        val openIntent = Intent(this, MainActivity::class.java)
        val openPendingIntent = PendingIntent.getActivity(
            this, 0, openIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val stopIntent = Intent(this, AlarmSoundService::class.java).apply {
            action = AlarmActions.ACTION_STOP
            putExtra(AlarmActions.EXTRA_MED_ID, medId)
        }

        val snoozeIntent = Intent(this, AlarmSoundService::class.java).apply {
            action = AlarmActions.ACTION_SNOOZE
            putExtra(AlarmActions.EXTRA_MED_ID, medId)
        }

        val stopPending = PendingIntent.getService(
            this, medId.toInt() + 1, stopIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val snoozePending = PendingIntent.getService(
            this, medId.toInt() + 2, snoozeIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Time to take $medName")
            .setContentText("Dosage: $medDosage")
            .setContentIntent(openPendingIntent)
            .setAutoCancel(true)
            .setOngoing(true)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .addAction(0, "Snooze", snoozePending)
            .addAction(0, "Stop", stopPending)
            .build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Medication Alarms",
                NotificationManager.IMPORTANCE_HIGH
            )
            getSystemService(NotificationManager::class.java)
                .createNotificationChannel(channel)
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
