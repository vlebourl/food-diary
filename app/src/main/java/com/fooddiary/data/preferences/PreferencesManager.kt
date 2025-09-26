package com.fooddiary.data.preferences

import com.fooddiary.data.database.entities.UserPreferences
import com.fooddiary.data.database.entities.MeasurementUnit
import com.fooddiary.data.database.entities.ExportFormat
import com.fooddiary.data.database.entities.ReportTemplate
import java.time.LocalTime
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Manages user preferences with validation and defaults
 */
@Singleton
class PreferencesManager @Inject constructor() {

    /**
     * Apply default values for missing preferences
     */
    fun applyDefaults(preferences: UserPreferences?): UserPreferences {
        return preferences ?: UserPreferences.createDefault()
    }

    /**
     * Validate user preferences
     */
    fun validatePreferences(preferences: UserPreferences): ValidationResult {
        val errors = mutableListOf<String>()

        // Validate correlation time window
        if (preferences.correlationTimeWindowHours !in 1..48) {
            errors.add("Correlation time window must be between 1 and 48 hours")
        }

        // Validate data retention
        if (preferences.dataRetentionMonths !in 1..60) {
            errors.add("Data retention must be between 1 and 60 months")
        }

        // Validate custom symptom types
        if (preferences.customSymptomTypes.size > 20) {
            errors.add("Maximum 20 custom symptom types allowed")
        }

        preferences.customSymptomTypes.forEach { symptomType ->
            if (symptomType.isBlank() || symptomType.length > 50) {
                errors.add("Custom symptom type must be 1-50 characters")
            }
        }

        // Validate notification time
        preferences.notificationTime?.let { time ->
            if (time.hour !in 0..23 || time.minute !in 0..59) {
                errors.add("Invalid notification time")
            }
        }

        return if (errors.isEmpty()) {
            ValidationResult.Success
        } else {
            ValidationResult.Error(errors.joinToString("; "))
        }
    }

    /**
     * Merge preferences with updates
     */
    fun mergePreferences(
        current: UserPreferences,
        updates: PreferenceUpdates
    ): UserPreferences {
        return current.update(
            correlationTimeWindowHours = updates.correlationTimeWindowHours,
            measurementUnit = updates.measurementUnit,
            notificationEnabled = updates.notificationEnabled,
            notificationTime = updates.notificationTime,
            dataRetentionMonths = updates.dataRetentionMonths,
            exportFormat = updates.exportFormat,
            customSymptomTypes = updates.customSymptomTypes,
            triggerAlertEnabled = updates.triggerAlertEnabled,
            reportTemplate = updates.reportTemplate,
            privacyMode = updates.privacyMode
        )
    }

    /**
     * Create notification schedule based on preferences
     */
    fun createNotificationSchedule(preferences: UserPreferences): NotificationSchedule? {
        if (!preferences.notificationEnabled || preferences.notificationTime == null) {
            return null
        }

        return NotificationSchedule(
            enabled = true,
            time = preferences.notificationTime,
            reminderTypes = listOf(
                NotificationType.FOOD_LOG_REMINDER,
                NotificationType.SYMPTOM_CHECK
            )
        )
    }

    /**
     * Get privacy settings
     */
    fun getPrivacySettings(preferences: UserPreferences): PrivacySettings {
        return PrivacySettings(
            privacyModeEnabled = preferences.privacyMode,
            dataRetentionMonths = preferences.dataRetentionMonths,
            allowAnalytics = !preferences.privacyMode,
            allowCrashReporting = !preferences.privacyMode
        )
    }

    /**
     * Create default reminder settings
     */
    fun createDefaultReminderSettings(): ReminderSettings {
        return ReminderSettings(
            mealReminders = listOf(
                MealReminder(
                    mealType = com.fooddiary.data.models.MealType.BREAKFAST,
                    enabled = true,
                    time = LocalTime.of(8, 0)
                ),
                MealReminder(
                    mealType = com.fooddiary.data.models.MealType.LUNCH,
                    enabled = true,
                    time = LocalTime.of(12, 30)
                ),
                MealReminder(
                    mealType = com.fooddiary.data.models.MealType.DINNER,
                    enabled = true,
                    time = LocalTime.of(19, 0)
                )
            ),
            symptomCheckReminder = SymptomCheckReminder(
                enabled = true,
                time = LocalTime.of(21, 0),
                frequency = ReminderFrequency.DAILY
            ),
            triggerAlerts = TriggerAlerts(
                enabled = false,
                minimumConfidence = 0.7f,
                notifyOnHighRisk = true
            )
        )
    }

    /**
     * Create default privacy settings
     */
    fun createDefaultPrivacySettings(): PrivacySettings {
        return PrivacySettings(
            privacyModeEnabled = true,
            dataRetentionMonths = 12,
            allowAnalytics = false,
            allowCrashReporting = false
        )
    }

    /**
     * Get feature flags based on preferences
     */
    fun getFeatureFlags(preferences: UserPreferences): Map<String, Boolean> {
        return mapOf(
            "advanced_analytics" to !preferences.privacyMode,
            "trigger_alerts" to preferences.triggerAlertEnabled,
            "custom_symptoms" to preferences.customSymptomTypes.isNotEmpty(),
            "data_export" to true,
            "report_generation" to true,
            "correlation_analysis" to true
        )
    }

    /**
     * Validate and sanitize custom symptom types
     */
    fun validateCustomSymptomTypes(symptomTypes: List<String>): List<String> {
        return symptomTypes
            .filter { it.isNotBlank() }
            .map { it.trim() }
            .filter { it.length <= 50 }
            .distinct()
            .take(20) // Maximum 20 custom types
    }

    /**
     * Convert preferences to export format
     */
    fun exportPreferences(preferences: UserPreferences): Map<String, Any> {
        return mapOf(
            "correlation_time_window_hours" to preferences.correlationTimeWindowHours,
            "measurement_unit" to preferences.measurementUnit.name,
            "notification_enabled" to preferences.notificationEnabled,
            "notification_time" to (preferences.notificationTime?.toString() ?: ""),
            "data_retention_months" to preferences.dataRetentionMonths,
            "export_format" to preferences.exportFormat.name,
            "custom_symptom_types" to preferences.customSymptomTypes,
            "trigger_alert_enabled" to preferences.triggerAlertEnabled,
            "report_template" to preferences.reportTemplate.name,
            "privacy_mode" to preferences.privacyMode
        )
    }

    /**
     * Import preferences from export format
     */
    fun importPreferences(data: Map<String, Any>): UserPreferences {
        return UserPreferences(
            correlationTimeWindowHours = (data["correlation_time_window_hours"] as? Int) ?: 3,
            measurementUnit = try {
                MeasurementUnit.valueOf(data["measurement_unit"] as? String ?: "METRIC")
            } catch (e: Exception) {
                MeasurementUnit.METRIC
            },
            notificationEnabled = (data["notification_enabled"] as? Boolean) ?: true,
            notificationTime = (data["notification_time"] as? String)?.let {
                if (it.isNotBlank()) LocalTime.parse(it) else null
            },
            dataRetentionMonths = (data["data_retention_months"] as? Int) ?: 12,
            exportFormat = try {
                ExportFormat.valueOf(data["export_format"] as? String ?: "JSON")
            } catch (e: Exception) {
                ExportFormat.JSON
            },
            customSymptomTypes = (data["custom_symptom_types"] as? List<String>) ?: emptyList(),
            triggerAlertEnabled = (data["trigger_alert_enabled"] as? Boolean) ?: false,
            reportTemplate = try {
                ReportTemplate.valueOf(data["report_template"] as? String ?: "BASIC")
            } catch (e: Exception) {
                ReportTemplate.BASIC
            },
            privacyMode = (data["privacy_mode"] as? Boolean) ?: true
        )
    }
}

// Supporting data classes

data class PreferenceUpdates(
    val correlationTimeWindowHours: Int? = null,
    val measurementUnit: MeasurementUnit? = null,
    val notificationEnabled: Boolean? = null,
    val notificationTime: LocalTime? = null,
    val dataRetentionMonths: Int? = null,
    val exportFormat: ExportFormat? = null,
    val customSymptomTypes: List<String>? = null,
    val triggerAlertEnabled: Boolean? = null,
    val reportTemplate: ReportTemplate? = null,
    val privacyMode: Boolean? = null
)

data class NotificationSchedule(
    val enabled: Boolean,
    val time: LocalTime,
    val reminderTypes: List<NotificationType>
)

data class ReminderSettings(
    val mealReminders: List<MealReminder>,
    val symptomCheckReminder: SymptomCheckReminder,
    val triggerAlerts: TriggerAlerts
)

data class MealReminder(
    val mealType: com.fooddiary.data.models.MealType,
    val enabled: Boolean,
    val time: LocalTime
)

data class SymptomCheckReminder(
    val enabled: Boolean,
    val time: LocalTime,
    val frequency: ReminderFrequency
)

data class TriggerAlerts(
    val enabled: Boolean,
    val minimumConfidence: Float,
    val notifyOnHighRisk: Boolean
)

data class PrivacySettings(
    val privacyModeEnabled: Boolean,
    val dataRetentionMonths: Int,
    val allowAnalytics: Boolean,
    val allowCrashReporting: Boolean
)

enum class NotificationType {
    FOOD_LOG_REMINDER,
    SYMPTOM_CHECK,
    TRIGGER_ALERT,
    REPORT_READY
}

enum class ReminderFrequency {
    DAILY,
    WEEKLY,
    CUSTOM
}

enum class AppTheme {
    LIGHT,
    DARK,
    SYSTEM
}

sealed class ValidationResult {
    object Success : ValidationResult()
    data class Error(val message: String) : ValidationResult()
}