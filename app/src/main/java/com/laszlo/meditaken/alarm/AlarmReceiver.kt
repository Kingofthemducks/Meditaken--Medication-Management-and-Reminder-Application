package com.laszlo.meditaken.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.laszlo.meditaken.alarm.AlarmScheduler.dayCodeToCalendar

// Triggered when an alarm fires
class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val medId = intent.getLongExtra(AlarmActions.EXTRA_MED_ID, -1L)
        if (medId == -1L) return

        val medName = intent.getStringExtra("med_name") ?: "Medication"
        val medDosage = intent.getStringExtra("med_dosage") ?: ""
        val dayCode = intent.getStringExtra("day_code")
        val time = intent.getStringExtra("time")

        // Start alarm sound service
        val serviceIntent = Intent(context, AlarmSoundService::class.java).apply {
            action = AlarmActions.ACTION_START
            putExtra(AlarmActions.EXTRA_MED_ID, medId)
            putExtra("med_name", medName)
            putExtra("med_dosage", medDosage)
        }
        context.startForegroundService(serviceIntent)

        // Reschedule for next week
        if (dayCode != null && time != null) {
            val calendarDay = dayCodeToCalendar[dayCode] ?: return

            val parts = time.split(":")
            val hour = parts.getOrNull(0)?.toIntOrNull() ?: return
            val minute = parts.getOrNull(1)?.take(2)?.toIntOrNull() ?: return

            val triggerTime = java.util.Calendar.getInstance().apply {
                set(java.util.Calendar.SECOND, 0)
                set(java.util.Calendar.MILLISECOND, 0)
                set(java.util.Calendar.HOUR_OF_DAY, hour)
                set(java.util.Calendar.MINUTE, minute)
                set(java.util.Calendar.DAY_OF_WEEK, calendarDay)
                add(java.util.Calendar.WEEK_OF_YEAR, 1)
            }.timeInMillis

            val alarmManager =
                context.getSystemService(Context.ALARM_SERVICE) as android.app.AlarmManager

            val requestCode = (medId.hashCode() * 1000) + (calendarDay * 100) + hour + minute

            val newIntent = Intent(context, AlarmReceiver::class.java).apply {
                putExtra(AlarmActions.EXTRA_MED_ID, medId)
                putExtra("med_name", medName)
                putExtra("med_dosage", medDosage)
                putExtra("day_code", dayCode)
                putExtra("time", time)
            }

            val pendingIntent = android.app.PendingIntent.getBroadcast(
                context,
                requestCode,
                newIntent,
                android.app.PendingIntent.FLAG_UPDATE_CURRENT or android.app.PendingIntent.FLAG_IMMUTABLE
            )

            // Schedule an exact alarm that wakes the device, even in Doze mode
            alarmManager.setExactAndAllowWhileIdle(
                android.app.AlarmManager.RTC_WAKEUP,
                triggerTime,
                pendingIntent
            )
        }
    }
}
