package com.laszlo.meditaken.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import java.util.Calendar

object AlarmScheduler {

    val dayCodeToCalendar = mapOf(
        "MON" to Calendar.MONDAY,
        "TUE" to Calendar.TUESDAY,
        "WED" to Calendar.WEDNESDAY,
        "THU" to Calendar.THURSDAY,
        "FRI" to Calendar.FRIDAY,
        "SAT" to Calendar.SATURDAY,
        "SUN" to Calendar.SUNDAY
    )

    // Normalize day strings so scheduler always receives valid values
    private fun normalizeDays(days: String): List<String> {
        if (days == "ALL") return dayCodeToCalendar.keys.toList()

        val cleaned = days.split(",")
            .map { it.trim().uppercase() }
            .filter { it.isNotEmpty() && dayCodeToCalendar.containsKey(it) }

        return if (cleaned.isEmpty()) {
            dayCodeToCalendar.keys.toList()
        } else {
            cleaned
        }
    }

    fun scheduleMedicationAlarms(
            context: Context,
            medId: Long,
            name: String,
            times: List<String>,
            days: String,
            dosage: String
        ) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (!alarmManager.canScheduleExactAlarms()) {
                    val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                    context.startActivity(intent)
                    return
                }
            }

        val selectedDays = normalizeDays(days)
        Log.d("ALARM_DEBUG", "Scheduling alarms for days: $selectedDays")

        selectedDays.forEachIndexed { dayIndex, dayCode ->
            val calendarDay = dayCodeToCalendar[dayCode] ?: return@forEachIndexed

            times.forEachIndexed { timeIndex, time ->
                val parts = time.trim().split(":")
                if (parts.size < 2) return@forEachIndexed

                val hour = parts[0].toIntOrNull() ?: return@forEachIndexed
                val minute = parts[1].take(2).toIntOrNull() ?: return@forEachIndexed

                val triggerTime = calculateNextTrigger(calendarDay, hour, minute)

                Log.d("ALARM_DEBUG", "Scheduling $dayCode at $hour:$minute → $triggerTime")

                val requestCode = (medId.hashCode() * 1000) + (dayIndex * 100) + timeIndex

                val intent = Intent(context, AlarmReceiver::class.java).apply {
                    putExtra(AlarmActions.EXTRA_MED_ID, medId)
                    putExtra("med_name", name)
                    putExtra("med_dosage", dosage)
                    putExtra("day_code", dayCode)
                    putExtra("time", time)
                }

                val pendingIntent = PendingIntent.getBroadcast(
                    context,
                    requestCode,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )

                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    triggerTime,
                    pendingIntent
                )
            }
        }
    }

    fun cancelMedicationAlarms(
        context: Context,
        medId: Long,
        times: List<String>,
        days: String
    ) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val selectedDays = normalizeDays(days)
        Log.d("ALARM_DEBUG", "Cancelling alarms for days: $selectedDays")

        selectedDays.forEachIndexed { dayIndex, _ ->
            times.forEachIndexed { timeIndex, _ ->
                val requestCode = (medId.hashCode() * 1000) + (dayIndex * 100) + timeIndex
                val intent = Intent(context, AlarmReceiver::class.java)
                val pendingIntent = PendingIntent.getBroadcast(
                    context,
                    requestCode,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
                alarmManager.cancel(pendingIntent)
            }
        }
    }

    fun snooze(
        context: Context,
        medId: Long,
        minutes: Int = 10
    ) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val triggerTime = Calendar.getInstance().apply {
            add(Calendar.MINUTE, minutes)
        }.timeInMillis

        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(AlarmActions.EXTRA_MED_ID, medId)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            medId.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerTime,
            pendingIntent
        )
    }

    // Weekly logic and safety buffer
    private fun calculateNextTrigger(dayOfWeek: Int, hour: Int, minute: Int): Long {
        val now = Calendar.getInstance()

        val cal = Calendar.getInstance().apply {
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
        }

        // Safety Buffer
        // If the alarm time is already passed by a few seconds,
        // push it forward so it still fires today.
        if (cal.timeInMillis <= now.timeInMillis + 5000) {
            cal.add(Calendar.MINUTE, 1)
        }

        val today = now.get(Calendar.DAY_OF_WEEK)

        return if (today == dayOfWeek) {
            if (cal.timeInMillis > now.timeInMillis) {
                cal.timeInMillis
            } else {
                cal.add(Calendar.WEEK_OF_YEAR, 1)
                cal.timeInMillis
            }
        } else {
            cal.set(Calendar.DAY_OF_WEEK, dayOfWeek)
            if (cal.timeInMillis <= now.timeInMillis) {
                cal.add(Calendar.WEEK_OF_YEAR, 1)
            }
            cal.timeInMillis
        }
    }
}
