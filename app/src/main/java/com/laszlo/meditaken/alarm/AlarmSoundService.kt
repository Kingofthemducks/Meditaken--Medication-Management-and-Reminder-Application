package com.laszlo.meditaken.alarm

import android.app.*
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.laszlo.meditaken.R

class AlarmSoundService : Service() {

    companion object {
        private const val CHANNEL_ID = "med_alarm"
    }

    private val players = mutableMapOf<Int, MediaPlayer>()

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val medId = intent?.getIntExtra(AlarmActions.EXTRA_MED_ID, -1) ?: return START_NOT_STICKY

        when (intent.action) {
            AlarmActions.ACTION_START -> startAlarm(medId)
            AlarmActions.ACTION_STOP -> stopAlarm(medId)
            AlarmActions.ACTION_SNOOZE -> snoozeAlarm(medId)
        }

        return START_NOT_STICKY
    }

    private fun startAlarm(medId: Int) {
        if (players.containsKey(medId)) return

        val player = MediaPlayer.create(this, R.raw.default_alarm)
        player.isLooping = true
        player.start()

        players[medId] = player
        startForeground(medId, buildNotification(medId))
    }

    private fun stopAlarm(medId: Int) {
        players[medId]?.apply {
            stop()
            release()
        }
        players.remove(medId)

        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    private fun snoozeAlarm(medId: Int) {
        stopAlarm(medId)
        AlarmScheduler.snooze(this, medId, 10)
    }

    private fun buildNotification(medId: Int): Notification {
        val stopIntent = Intent(this, AlarmSoundService::class.java).apply {
            action = AlarmActions.ACTION_STOP
            putExtra(AlarmActions.EXTRA_MED_ID, medId)
        }

        val snoozeIntent = Intent(this, AlarmSoundService::class.java).apply {
            action = AlarmActions.ACTION_SNOOZE
            putExtra(AlarmActions.EXTRA_MED_ID, medId)
        }

        val stopPending = PendingIntent.getService(
            this, medId + 1, stopIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val snoozePending = PendingIntent.getService(
            this, medId + 2, snoozeIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Medication Reminder")
            .setContentText("Time to take your medication")
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setOngoing(true)
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
