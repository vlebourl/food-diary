package com.fooddiary.presentation.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fooddiary.data.repository.SettingsRepository
import com.fooddiary.data.database.entities.UserPreference
import com.fooddiary.data.models.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

/**
 * ViewModel for Settings screen with preferences management
 * Manages user settings, notifications, privacy controls, and data backup/restore
 */
@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        loadSettings()
    }

    /**
     * Load all user preferences
     */
    fun loadSettings() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)

                settingsRepository.getAllPreferences().collect { preferences ->
                    val settingsMap = preferences.associateBy { it.key }

                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        notificationsEnabled = settingsMap["notifications_enabled"]?.boolValue ?: true,
                        reminderTime = settingsMap["reminder_time"]?.stringValue?.let {
                            LocalTime.parse(it)
                        } ?: LocalTime.of(18, 0),
                        reminderFrequency = ReminderFrequency.valueOf(
                            settingsMap["reminder_frequency"]?.stringValue ?: ReminderFrequency.DAILY.name
                        ),
                        darkMode = settingsMap["dark_mode"]?.boolValue ?: false,
                        privacyMode = settingsMap["privacy_mode"]?.boolValue ?: false,
                        autoBackup = settingsMap["auto_backup"]?.boolValue ?: true,
                        backupFrequency = BackupFrequency.valueOf(
                            settingsMap["backup_frequency"]?.stringValue ?: BackupFrequency.WEEKLY.name
                        ),
                        dataRetention = DataRetentionPeriod.valueOf(
                            settingsMap["data_retention"]?.stringValue ?: DataRetentionPeriod.ONE_YEAR.name
                        ),
                        language = settingsMap["language"]?.stringValue ?: "en",
                        units = UnitSystem.valueOf(
                            settingsMap["units"]?.stringValue ?: UnitSystem.METRIC.name
                        ),
                        firstDayOfWeek = WeekStartDay.valueOf(
                            settingsMap["first_day_of_week"]?.stringValue ?: WeekStartDay.MONDAY.name
                        )
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Failed to load settings: ${e.message}"
                )
            }
        }
    }

    /**
     * Update a specific preference
     */
    fun updatePreference(key: String, value: Any) {
        viewModelScope.launch {
            try {
                val preference = when (value) {
                    is Boolean -> UserPreference(key, boolValue = value)
                    is String -> UserPreference(key, stringValue = value)
                    is Int -> UserPreference(key, intValue = value)
                    is Float -> UserPreference(key, floatValue = value)
                    else -> return@launch
                }

                settingsRepository.updatePreference(preference)

                // Update UI state immediately for better UX
                updateUiStateForKey(key, value)

                _uiState.value = _uiState.value.copy(
                    message = "Setting updated successfully"
                )

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to update setting: ${e.message}"
                )
            }
        }
    }

    /**
     * Toggle notifications on/off
     */
    fun toggleNotifications(enabled: Boolean) {
        updatePreference("notifications_enabled", enabled)

        if (enabled) {
            scheduleNotifications()
        } else {
            cancelNotifications()
        }
    }

    /**
     * Update reminder time
     */
    fun updateReminderTime(time: LocalTime) {
        updatePreference("reminder_time", time.toString())

        if (_uiState.value.notificationsEnabled) {
            scheduleNotifications()
        }
    }

    /**
     * Update reminder frequency
     */
    fun updateReminderFrequency(frequency: ReminderFrequency) {
        updatePreference("reminder_frequency", frequency.name)

        if (_uiState.value.notificationsEnabled) {
            scheduleNotifications()
        }
    }

    /**
     * Toggle dark mode
     */
    fun toggleDarkMode(enabled: Boolean) {
        updatePreference("dark_mode", enabled)
    }

    /**
     * Toggle privacy mode
     */
    fun togglePrivacyMode(enabled: Boolean) {
        updatePreference("privacy_mode", enabled)
    }

    /**
     * Update language
     */
    fun updateLanguage(language: String) {
        updatePreference("language", language)
    }

    /**
     * Update unit system
     */
    fun updateUnitSystem(units: UnitSystem) {
        updatePreference("units", units.name)
    }

    /**
     * Update first day of week
     */
    fun updateFirstDayOfWeek(day: WeekStartDay) {
        updatePreference("first_day_of_week", day.name)
    }

    /**
     * Toggle auto backup
     */
    fun toggleAutoBackup(enabled: Boolean) {
        updatePreference("auto_backup", enabled)

        if (enabled) {
            scheduleNextBackup()
        }
    }

    /**
     * Update backup frequency
     */
    fun updateBackupFrequency(frequency: BackupFrequency) {
        updatePreference("backup_frequency", frequency.name)

        if (_uiState.value.autoBackup) {
            scheduleNextBackup()
        }
    }

    /**
     * Update data retention period
     */
    fun updateDataRetention(period: DataRetentionPeriod) {
        updatePreference("data_retention", period.name)
    }

    /**
     * Manually trigger backup
     */
    fun backupSettings() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isBackingUp = true)

                val backupResult = settingsRepository.createBackup()

                _uiState.value = _uiState.value.copy(
                    isBackingUp = false,
                    lastBackupDate = backupResult.timestamp,
                    message = "Backup completed successfully"
                )

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isBackingUp = false,
                    error = "Backup failed: ${e.message}"
                )
            }
        }
    }

    /**
     * Restore settings from backup
     */
    fun restoreSettings(backupId: String) {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isRestoring = true)

                settingsRepository.restoreFromBackup(backupId)

                _uiState.value = _uiState.value.copy(
                    isRestoring = false,
                    message = "Settings restored successfully"
                )

                // Reload settings after restore
                loadSettings()

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isRestoring = false,
                    error = "Restore failed: ${e.message}"
                )
            }
        }
    }

    /**
     * Schedule notification reminders
     */
    fun scheduleNotification(type: NotificationType, time: LocalTime, frequency: ReminderFrequency) {
        viewModelScope.launch {
            try {
                settingsRepository.scheduleNotification(type, time, frequency)

                _uiState.value = _uiState.value.copy(
                    message = "Notification scheduled successfully"
                )

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to schedule notification: ${e.message}"
                )
            }
        }
    }

    /**
     * Cancel all notifications
     */
    fun cancelAllNotifications() {
        viewModelScope.launch {
            try {
                settingsRepository.cancelAllNotifications()

                _uiState.value = _uiState.value.copy(
                    message = "All notifications cancelled"
                )

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to cancel notifications: ${e.message}"
                )
            }
        }
    }

    /**
     * Reset all settings to defaults
     */
    fun resetDefaults() {
        viewModelScope.launch {
            try {
                settingsRepository.resetToDefaults()

                _uiState.value = SettingsUiState() // Reset to default UI state

                _uiState.value = _uiState.value.copy(
                    message = "Settings reset to defaults"
                )

                loadSettings() // Reload settings

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to reset settings: ${e.message}"
                )
            }
        }
    }

    /**
     * Export settings
     */
    fun exportSettings(): String? {
        return try {
            val settings = _uiState.value
            settingsRepository.exportSettings(settings)
        } catch (e: Exception) {
            _uiState.value = _uiState.value.copy(
                error = "Failed to export settings: ${e.message}"
            )
            null
        }
    }

    /**
     * Import settings
     */
    fun importSettings(settingsData: String) {
        viewModelScope.launch {
            try {
                settingsRepository.importSettings(settingsData)

                _uiState.value = _uiState.value.copy(
                    message = "Settings imported successfully"
                )

                loadSettings()

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to import settings: ${e.message}"
                )
            }
        }
    }

    /**
     * Clear error or message
     */
    fun clearMessage() {
        _uiState.value = _uiState.value.copy(error = null, message = null)
    }

    /**
     * Schedule notifications based on current settings
     */
    private fun scheduleNotifications() {
        val state = _uiState.value
        scheduleNotification(
            NotificationType.FOOD_REMINDER,
            state.reminderTime,
            state.reminderFrequency
        )
    }

    /**
     * Cancel all notifications
     */
    private fun cancelNotifications() {
        cancelAllNotifications()
    }

    /**
     * Schedule next backup
     */
    private fun scheduleNextBackup() {
        viewModelScope.launch {
            try {
                settingsRepository.scheduleNextBackup(_uiState.value.backupFrequency)
            } catch (e: Exception) {
                // Silently fail backup scheduling
            }
        }
    }

    /**
     * Update UI state immediately for better UX
     */
    private fun updateUiStateForKey(key: String, value: Any) {
        val currentState = _uiState.value

        _uiState.value = when (key) {
            "notifications_enabled" -> currentState.copy(notificationsEnabled = value as Boolean)
            "reminder_time" -> currentState.copy(reminderTime = LocalTime.parse(value as String))
            "reminder_frequency" -> currentState.copy(reminderFrequency = ReminderFrequency.valueOf(value as String))
            "dark_mode" -> currentState.copy(darkMode = value as Boolean)
            "privacy_mode" -> currentState.copy(privacyMode = value as Boolean)
            "auto_backup" -> currentState.copy(autoBackup = value as Boolean)
            "backup_frequency" -> currentState.copy(backupFrequency = BackupFrequency.valueOf(value as String))
            "data_retention" -> currentState.copy(dataRetention = DataRetentionPeriod.valueOf(value as String))
            "language" -> currentState.copy(language = value as String)
            "units" -> currentState.copy(units = UnitSystem.valueOf(value as String))
            "first_day_of_week" -> currentState.copy(firstDayOfWeek = WeekStartDay.valueOf(value as String))
            else -> currentState
        }
    }
}

/**
 * UI State for Settings screen
 */
data class SettingsUiState(
    val isLoading: Boolean = false,
    val isBackingUp: Boolean = false,
    val isRestoring: Boolean = false,

    // Notification settings
    val notificationsEnabled: Boolean = true,
    val reminderTime: LocalTime = LocalTime.of(18, 0),
    val reminderFrequency: ReminderFrequency = ReminderFrequency.DAILY,

    // Appearance settings
    val darkMode: Boolean = false,
    val language: String = "en",
    val units: UnitSystem = UnitSystem.METRIC,
    val firstDayOfWeek: WeekStartDay = WeekStartDay.MONDAY,

    // Privacy settings
    val privacyMode: Boolean = false,
    val dataRetention: DataRetentionPeriod = DataRetentionPeriod.ONE_YEAR,

    // Backup settings
    val autoBackup: Boolean = true,
    val backupFrequency: BackupFrequency = BackupFrequency.WEEKLY,
    val lastBackupDate: java.time.Instant? = null,

    val message: String? = null,
    val error: String? = null
)

/**
 * Reminder frequency options
 */
enum class ReminderFrequency {
    DAILY,
    WEEKLY,
    CUSTOM;

    val displayName: String
        get() = when (this) {
            DAILY -> "Daily"
            WEEKLY -> "Weekly"
            CUSTOM -> "Custom"
        }
}

/**
 * Backup frequency options
 */
enum class BackupFrequency {
    DAILY,
    WEEKLY,
    MONTHLY;

    val displayName: String
        get() = when (this) {
            DAILY -> "Daily"
            WEEKLY -> "Weekly"
            MONTHLY -> "Monthly"
        }
}

/**
 * Data retention periods
 */
enum class DataRetentionPeriod {
    THREE_MONTHS,
    SIX_MONTHS,
    ONE_YEAR,
    FOREVER;

    val displayName: String
        get() = when (this) {
            THREE_MONTHS -> "3 Months"
            SIX_MONTHS -> "6 Months"
            ONE_YEAR -> "1 Year"
            FOREVER -> "Forever"
        }
}

/**
 * Unit system options
 */
enum class UnitSystem {
    METRIC,
    IMPERIAL;

    val displayName: String
        get() = when (this) {
            METRIC -> "Metric"
            IMPERIAL -> "Imperial"
        }
}

/**
 * Week start day options
 */
enum class WeekStartDay {
    SUNDAY,
    MONDAY;

    val displayName: String
        get() = when (this) {
            SUNDAY -> "Sunday"
            MONDAY -> "Monday"
        }
}

/**
 * Notification types
 */
enum class NotificationType {
    FOOD_REMINDER,
    SYMPTOM_REMINDER,
    MEDICATION_REMINDER;

    val displayName: String
        get() = when (this) {
            FOOD_REMINDER -> "Food Entry Reminder"
            SYMPTOM_REMINDER -> "Symptom Check Reminder"
            MEDICATION_REMINDER -> "Medication Reminder"
        }
}

/**
 * Backup result data class
 */
data class BackupResult(
    val timestamp: java.time.Instant,
    val size: Long,
    val filePath: String
)