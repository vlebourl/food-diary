package com.fooddiary.services

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.fooddiary.R
import com.fooddiary.data.preferences.UserPreferences
import com.fooddiary.presentation.MainActivity
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

class ReminderService {

    companion object {
        const val MEAL_REMINDER_CHANNEL_ID = "meal_reminder_channel"
        const val SYMPTOM_REMINDER_CHANNEL_ID = "symptom_reminder_channel"
        const val MEAL_REMINDER_ID = 1001
        const val SYMPTOM_REMINDER_ID = 1002
        const val MEAL_REMINDER_REQUEST_CODE = 2001
        const val SYMPTOM_REMINDER_REQUEST_CODE = 2002

        fun createNotificationChannels(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                // Meal Reminder Channel
                val mealChannel = NotificationChannel(
                    MEAL_REMINDER_CHANNEL_ID,
                    "Meal Reminders",
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    description = "Reminders to log meals"
                    enableVibration(true)
                }

                // Symptom Reminder Channel
                val symptomChannel = NotificationChannel(
                    SYMPTOM_REMINDER_CHANNEL_ID,
                    "Symptom Reminders",
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    description = "Reminders to track symptoms"
                    enableVibration(true)
                }

                notificationManager.createNotificationChannels(listOf(mealChannel, symptomChannel))
            }
        }

        fun scheduleMealReminder(context: Context, time: LocalTime) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val intent = Intent(context, MealReminderReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                MEAL_REMINDER_REQUEST_CODE,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            // Calculate next occurrence of the reminder time
            val calendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, time.hour)
                set(Calendar.MINUTE, time.minute)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)

                // If the time has passed for today, schedule for tomorrow
                if (timeInMillis <= System.currentTimeMillis()) {
                    add(Calendar.DAY_OF_MONTH, 1)
                }
            }

            // Set repeating alarm
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
        }

        fun scheduleSymptomReminder(context: Context, time: LocalTime) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val intent = Intent(context, SymptomReminderReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                SYMPTOM_REMINDER_REQUEST_CODE,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val calendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, time.hour)
                set(Calendar.MINUTE, time.minute)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)

                if (timeInMillis <= System.currentTimeMillis()) {
                    add(Calendar.DAY_OF_MONTH, 1)
                }
            }

            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
        }

        fun cancelMealReminder(context: Context) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, MealReminderReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                MEAL_REMINDER_REQUEST_CODE,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            alarmManager.cancel(pendingIntent)
        }

        fun cancelSymptomReminder(context: Context) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, SymptomReminderReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                SYMPTOM_REMINDER_REQUEST_CODE,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            alarmManager.cancel(pendingIntent)
        }
    }
}

class MealReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        showMealReminderNotification(context)
    }

    private fun showMealReminderNotification(context: Context) {
        val notificationIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("navigate_to", "food_entry")
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, ReminderService.MEAL_REMINDER_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_restaurant)
            .setContentTitle("Time to log your meal!")
            .setContentText("Don't forget to record what you're eating")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .addAction(
                R.drawable.ic_add,
                "Log Meal",
                pendingIntent
            )
            .build()

        NotificationManagerCompat.from(context).notify(ReminderService.MEAL_REMINDER_ID, notification)
    }
}

class SymptomReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        showSymptomReminderNotification(context)
    }

    private fun showSymptomReminderNotification(context: Context) {
        val notificationIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("navigate_to", "symptom_entry")
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, ReminderService.SYMPTOM_REMINDER_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_health)
            .setContentTitle("How are you feeling?")
            .setContentText("Track any symptoms you're experiencing")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .addAction(
                R.drawable.ic_add,
                "Log Symptoms",
                pendingIntent
            )
            .build()

        NotificationManagerCompat.from(context).notify(ReminderService.SYMPTOM_REMINDER_ID, notification)
    }
}

@HiltWorker
class ReminderSetupWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val userPreferences: UserPreferences
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            // Create notification channels
            ReminderService.createNotificationChannels(applicationContext)

            // Check if notifications are enabled
            val notificationsEnabled = userPreferences.notificationsEnabled.first()
            if (!notificationsEnabled) {
                return Result.success()
            }

            // Schedule meal reminders
            val mealReminderTime = userPreferences.mealReminderTime.first()
            if (mealReminderTime.isNotEmpty()) {
                val time = LocalTime.parse(mealReminderTime, DateTimeFormatter.ISO_LOCAL_TIME)
                ReminderService.scheduleMealReminder(applicationContext, time)
            }

            // Schedule symptom reminders
            val symptomReminderTime = userPreferences.symptomReminderTime.first()
            if (symptomReminderTime.isNotEmpty()) {
                val time = LocalTime.parse(symptomReminderTime, DateTimeFormatter.ISO_LOCAL_TIME)
                ReminderService.scheduleSymptomReminder(applicationContext, time)
            }

            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    companion object {
        fun enqueue(context: Context) {
            val workRequest = OneTimeWorkRequestBuilder<ReminderSetupWorker>()
                .build()

            WorkManager.getInstance(context).enqueueUniqueWork(
                "reminder_setup",
                ExistingWorkPolicy.REPLACE,
                workRequest
            )
        }
    }
}