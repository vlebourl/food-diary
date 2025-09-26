package com.fooddiary.data.repository

import com.fooddiary.data.database.dao.UserPreferencesDao
import com.fooddiary.data.database.entities.UserPreferences
import com.fooddiary.data.database.entities.MeasurementUnit
import com.fooddiary.data.database.entities.ExportFormat
import com.fooddiary.data.database.entities.ReportTemplate
import com.fooddiary.data.preferences.PreferencesManager
import com.fooddiary.data.preferences.SettingsBackupManager
import com.fooddiary.data.preferences.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Instant
import java.time.LocalTime
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for user preferences and settings management
 */
@Singleton
class SettingsRepository @Inject constructor(
    private val userPreferencesDao: UserPreferencesDao,
    private val preferencesManager: PreferencesManager,
    private val backupManager: SettingsBackupManager
) {

    /**
     * Get user preferences with defaults for missing values
     */
    suspend fun getPreferences(userId: String = "default"): UserPreferences {
        val storedPreferences = userPreferencesDao.getByUserId(userId)
        return preferencesManager.applyDefaults(storedPreferences)
    }

    /**
     * Get preferences as Flow for reactive UI
     */
    fun getPreferencesFlow(userId: String = "default"): Flow<UserPreferences> {
        return userPreferencesDao.getByUserIdFlow(userId).map { preferences ->
            preferencesManager.applyDefaults(preferences)
        }
    }

    /**
     * Update user preferences with validation
     */
    suspend fun updatePreferences(
        userId: String = "default",
        updates: PreferenceUpdates
    ): Result<UserPreferences> {
        return try {
            val currentPreferences = getPreferences(userId)
            val updatedPreferences = preferencesManager.mergePreferences(currentPreferences, updates)

            val validationResult = preferencesManager.validatePreferences(updatedPreferences)
            when (validationResult) {
                is ValidationResult.Success -> {
                    val savedPreferences = if (currentPreferences.id == 0L) {
                        // Insert new preferences
                        val newId = userPreferencesDao.insert(updatedPreferences)
                        updatedPreferences.copy(id = newId)
                    } else {
                        // Update existing preferences
                        userPreferencesDao.update(updatedPreferences)
                        updatedPreferences
                    }
                    Result.success(savedPreferences)
                }
                is ValidationResult.Error -> Result.failure(Exception(validationResult.message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Reset preferences to defaults
     */
    suspend fun resetDefaults(userId: String = "default"): Result<UserPreferences> {
        return try {
            val defaultPreferences = UserPreferences.createDefault()
            val existingPreferences = userPreferencesDao.getByUserId(userId)

            val resetPreferences = if (existingPreferences != null) {
                defaultPreferences.copy(id = existingPreferences.id, modifiedAt = Instant.now())
            } else {
                defaultPreferences
            }

            if (existingPreferences != null) {
                userPreferencesDao.update(resetPreferences)
            } else {
                val newId = userPreferencesDao.insert(resetPreferences)
                resetPreferences.copy(id = newId)
            }

            Result.success(resetPreferences)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Export settings to file
     */
    suspend fun exportSettings(
        userId: String = "default",
        filePath: String
    ): Result<String> {
        return try {
            val preferences = getPreferences(userId)
            val backupInfo = backupManager.createBackup(preferences, filePath).getOrThrow()
            Result.success(backupInfo.filePath)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Import settings from file
     */
    suspend fun importSettings(
        filePath: String,
        userId: String = "default"
    ): Result<UserPreferences> {
        return try {
            val importedPreferences = backupManager.restoreFromBackup(filePath).getOrThrow()

            // Update with imported preferences
            val updates = PreferenceUpdates(
                correlationTimeWindowHours = importedPreferences.correlationTimeWindowHours,
                measurementUnit = importedPreferences.measurementUnit,
                notificationEnabled = importedPreferences.notificationEnabled,
                notificationTime = importedPreferences.notificationTime,
                dataRetentionMonths = importedPreferences.dataRetentionMonths,
                exportFormat = importedPreferences.exportFormat,
                customSymptomTypes = importedPreferences.customSymptomTypes,
                triggerAlertEnabled = importedPreferences.triggerAlertEnabled,
                reportTemplate = importedPreferences.reportTemplate,
                privacyMode = importedPreferences.privacyMode
            )

            updatePreferences(userId, updates)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Schedule notification based on preferences
     */
    suspend fun scheduleNotification(
        userId: String = "default",
        notificationType: NotificationType,
        enabled: Boolean,
        time: LocalTime? = null
    ): Result<Unit> {
        return try {
            val currentPreferences = getPreferences(userId)
            val updates = when (notificationType) {
                NotificationType.FOOD_LOG_REMINDER,
                NotificationType.SYMPTOM_CHECK -> PreferenceUpdates(
                    notificationEnabled = enabled,
                    notificationTime = time ?: currentPreferences.notificationTime
                )
                NotificationType.TRIGGER_ALERT -> PreferenceUpdates(
                    triggerAlertEnabled = enabled
                )
                NotificationType.REPORT_READY -> PreferenceUpdates(
                    // Would handle report notifications separately
                    notificationEnabled = enabled
                )
            }

            updatePreferences(userId, updates).map { Unit }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Update privacy settings
     */
    suspend fun updatePrivacy(
        userId: String = "default",
        privacyMode: Boolean,
        dataRetentionMonths: Int? = null
    ): Result<PrivacySettings> {
        return try {
            val updates = PreferenceUpdates(
                privacyMode = privacyMode,
                dataRetentionMonths = dataRetentionMonths
            )

            val updatedPreferences = updatePreferences(userId, updates).getOrThrow()
            val privacySettings = preferencesManager.getPrivacySettings(updatedPreferences)
            Result.success(privacySettings)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Manage cache settings
     */
    suspend fun manageCaches(
        userId: String = "default",
        action: CacheAction
    ): Result<String> {
        return try {
            when (action) {
                CacheAction.CLEAR_ALL -> {
                    // Would clear application caches
                    Result.success("All caches cleared")
                }
                CacheAction.CLEAR_IMAGES -> {
                    // Would clear image caches
                    Result.success("Image cache cleared")
                }
                CacheAction.CLEAR_DATA -> {
                    // Would clear temporary data
                    Result.success("Data cache cleared")
                }
                CacheAction.OPTIMIZE -> {
                    // Would optimize cache storage
                    Result.success("Cache optimized")
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get notification settings
     */
    suspend fun getNotificationSettings(userId: String = "default"): NotificationSettings {
        val preferences = getPreferences(userId)
        val schedule = preferencesManager.createNotificationSchedule(preferences)

        return NotificationSettings(
            enabled = preferences.notificationEnabled,
            time = preferences.notificationTime,
            triggerAlertsEnabled = preferences.triggerAlertEnabled,
            reminderSettings = preferencesManager.createDefaultReminderSettings(),
            schedule = schedule
        )
    }

    /**
     * Get feature flags based on current preferences
     */
    suspend fun getFeatureFlags(userId: String = "default"): Map<String, Boolean> {
        val preferences = getPreferences(userId)
        return preferencesManager.getFeatureFlags(preferences)
    }

    /**
     * Add custom symptom type
     */
    suspend fun addCustomSymptomType(
        userId: String = "default",
        symptomType: String
    ): Result<List<String>> {
        return try {
            val currentPreferences = getPreferences(userId)
            val currentTypes = currentPreferences.customSymptomTypes.toMutableList()

            if (currentTypes.contains(symptomType.trim())) {
                return Result.failure(Exception("Symptom type already exists"))
            }

            if (currentTypes.size >= 20) {
                return Result.failure(Exception("Maximum 20 custom symptom types allowed"))
            }

            currentTypes.add(symptomType.trim())
            val validatedTypes = preferencesManager.validateCustomSymptomTypes(currentTypes)

            val updates = PreferenceUpdates(customSymptomTypes = validatedTypes)
            updatePreferences(userId, updates).map { validatedTypes }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Remove custom symptom type
     */
    suspend fun removeCustomSymptomType(
        userId: String = "default",
        symptomType: String
    ): Result<List<String>> {
        return try {
            val currentPreferences = getPreferences(userId)
            val updatedTypes = currentPreferences.customSymptomTypes
                .filter { it != symptomType.trim() }

            val updates = PreferenceUpdates(customSymptomTypes = updatedTypes)
            updatePreferences(userId, updates).map { updatedTypes }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Get app theme preference
     */
    suspend fun getTheme(userId: String = "default"): AppTheme {
        // For now, return system theme - would be stored in preferences
        return AppTheme.SYSTEM
    }

    /**
     * Update app theme
     */
    suspend fun updateTheme(
        userId: String = "default",
        theme: AppTheme
    ): Result<AppTheme> {
        return try {
            // Would update theme preference
            // For now, just return the theme
            Result.success(theme)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Create settings backup
     */
    suspend fun createBackup(
        userId: String = "default",
        filePath: String
    ): Result<BackupInfo> {
        return try {
            val preferences = getPreferences(userId)
            backupManager.createBackup(preferences, filePath)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * List available backups
     */
    suspend fun listBackups(backupDir: String): List<BackupInfo> {
        return backupManager.listBackups(backupDir)
    }

    /**
     * Delete backup
     */
    suspend fun deleteBackup(filePath: String): Result<Unit> {
        return backupManager.deleteBackup(filePath)
    }

    /**
     * Get storage usage statistics
     */
    suspend fun getStorageUsage(): StorageUsage {
        return StorageUsage(
            totalUsed = 0L, // Would calculate actual usage
            preferences = 1024L, // Preferences size
            cache = 0L, // Cache size
            reports = 0L, // Reports size
            exports = 0L // Exports size
        )
    }

    /**
     * Validate settings integrity
     */
    suspend fun validateSettings(userId: String = "default"): SettingsValidation {
        return try {
            val preferences = getPreferences(userId)
            val validationResult = preferencesManager.validatePreferences(preferences)

            when (validationResult) {
                is ValidationResult.Success -> SettingsValidation(
                    isValid = true,
                    errors = emptyList(),
                    warnings = emptyList()
                )
                is ValidationResult.Error -> SettingsValidation(
                    isValid = false,
                    errors = listOf(validationResult.message),
                    warnings = emptyList()
                )
            }
        } catch (e: Exception) {
            SettingsValidation(
                isValid = false,
                errors = listOf(e.message ?: "Unknown error"),
                warnings = emptyList()
            )
        }
    }
}

// Supporting data classes and enums

data class NotificationSettings(
    val enabled: Boolean,
    val time: LocalTime?,
    val triggerAlertsEnabled: Boolean,
    val reminderSettings: ReminderSettings,
    val schedule: NotificationSchedule?
)

data class StorageUsage(
    val totalUsed: Long,
    val preferences: Long,
    val cache: Long,
    val reports: Long,
    val exports: Long
)

data class SettingsValidation(
    val isValid: Boolean,
    val errors: List<String>,
    val warnings: List<String>
)

enum class CacheAction {
    CLEAR_ALL,
    CLEAR_IMAGES,
    CLEAR_DATA,
    OPTIMIZE
}