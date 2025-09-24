package com.fooddiary.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fooddiary.data.preferences.UserPreferences
import com.fooddiary.data.repository.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    // Settings flows from preferences
    val notificationsEnabled = userPreferences.notificationsEnabled
    val mealReminderTime = userPreferences.mealReminderTime
    val symptomReminderTime = userPreferences.symptomReminderTime
    val darkModeEnabled = userPreferences.darkModeEnabled
    val dataRetentionDays = userPreferences.dataRetentionDays
    val exportFormat = userPreferences.exportFormat
    val medicalMode = userPreferences.medicalMode
    val showFodmapInfo = userPreferences.showFodmapInfo
    val biometricAuthEnabled = userPreferences.biometricAuthEnabled

    init {
        loadSettings()
        observePreferences()
    }

    private fun loadSettings() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true)

                // Load database statistics
                val totalEntries = databaseRepository.getTotalFoodEntries()
                val totalSymptoms = databaseRepository.getTotalSymptomEvents()
                val databaseSize = databaseRepository.getDatabaseSize()
                val oldestEntryDate = databaseRepository.getOldestEntryDate()

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    totalFoodEntries = totalEntries,
                    totalSymptomEvents = totalSymptoms,
                    databaseSizeMB = databaseSize,
                    oldestEntryDate = oldestEntryDate
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Failed to load settings"
                )
            }
        }
    }

    private fun observePreferences() {
        viewModelScope.launch {
            combine(
                notificationsEnabled,
                darkModeEnabled,
                medicalMode,
                showFodmapInfo,
                biometricAuthEnabled
            ) { notifications, darkMode, medical, fodmap, biometric ->
                SettingsState(notifications, darkMode, medical, fodmap, biometric)
            }.collect { state ->
                _uiState.value = _uiState.value.copy(
                    notificationsEnabled = state.notifications,
                    darkModeEnabled = state.darkMode,
                    medicalMode = state.medical,
                    showFodmapInfo = state.fodmap,
                    biometricAuthEnabled = state.biometric
                )
            }
        }
    }

    fun updateNotificationsEnabled(enabled: Boolean) {
        viewModelScope.launch {
            userPreferences.setNotificationsEnabled(enabled)
        }
    }

    fun updateMealReminderTime(time: LocalTime) {
        viewModelScope.launch {
            userPreferences.setMealReminderTime(time.toString())
        }
    }

    fun updateSymptomReminderTime(time: LocalTime) {
        viewModelScope.launch {
            userPreferences.setSymptomReminderTime(time.toString())
        }
    }

    fun updateDarkModeEnabled(enabled: Boolean) {
        viewModelScope.launch {
            userPreferences.setDarkModeEnabled(enabled)
        }
    }

    fun updateDataRetentionDays(days: Int) {
        viewModelScope.launch {
            userPreferences.setDataRetentionDays(days)
        }
    }

    fun updateExportFormat(format: String) {
        viewModelScope.launch {
            userPreferences.setExportFormat(format)
        }
    }

    fun updateMedicalMode(enabled: Boolean) {
        viewModelScope.launch {
            userPreferences.setMedicalMode(enabled)
        }
    }

    fun updateShowFodmapInfo(enabled: Boolean) {
        viewModelScope.launch {
            userPreferences.setShowFodmapInfo(enabled)
        }
    }

    fun updateBiometricAuthEnabled(enabled: Boolean) {
        viewModelScope.launch {
            userPreferences.setBiometricAuthEnabled(enabled)
        }
    }

    fun exportAllData() {
        _uiState.value = _uiState.value.copy(isExporting = true)

        viewModelScope.launch {
            try {
                val exportResult = databaseRepository.exportAllData()
                _uiState.value = _uiState.value.copy(
                    isExporting = false,
                    exportFilePath = exportResult.filePath,
                    successMessage = "Data exported successfully"
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isExporting = false,
                    errorMessage = e.message ?: "Failed to export data"
                )
            }
        }
    }

    fun importData(filePath: String) {
        _uiState.value = _uiState.value.copy(isImporting = true)

        viewModelScope.launch {
            try {
                val importResult = databaseRepository.importData(filePath)
                _uiState.value = _uiState.value.copy(
                    isImporting = false,
                    successMessage = "Imported ${importResult.entriesCount} entries successfully"
                )
                // Reload settings to reflect new data
                loadSettings()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isImporting = false,
                    errorMessage = e.message ?: "Failed to import data"
                )
            }
        }
    }

    fun clearAllData() {
        _uiState.value = _uiState.value.copy(isClearing = true)

        viewModelScope.launch {
            try {
                databaseRepository.clearAllData()
                _uiState.value = _uiState.value.copy(
                    isClearing = false,
                    successMessage = "All data cleared successfully",
                    totalFoodEntries = 0,
                    totalSymptomEvents = 0,
                    databaseSizeMB = 0.0,
                    oldestEntryDate = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isClearing = false,
                    errorMessage = e.message ?: "Failed to clear data"
                )
            }
        }
    }

    fun optimizeDatabase() {
        _uiState.value = _uiState.value.copy(isOptimizing = true)

        viewModelScope.launch {
            try {
                val result = databaseRepository.optimizeDatabase()
                _uiState.value = _uiState.value.copy(
                    isOptimizing = false,
                    successMessage = "Database optimized. Saved ${result.spaceSavedMB} MB"
                )
                // Reload to get new database size
                loadSettings()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isOptimizing = false,
                    errorMessage = e.message ?: "Failed to optimize database"
                )
            }
        }
    }

    fun cleanupOldData() {
        viewModelScope.launch {
            try {
                val retentionDays = dataRetentionDays.first()
                val result = databaseRepository.cleanupDataOlderThan(retentionDays)
                _uiState.value = _uiState.value.copy(
                    successMessage = "Cleaned up ${result.deletedEntries} old entries"
                )
                // Reload to reflect changes
                loadSettings()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = e.message ?: "Failed to cleanup old data"
                )
            }
        }
    }

    fun testNotifications() {
        viewModelScope.launch {
            try {
                // Send test notification
                _uiState.value = _uiState.value.copy(
                    successMessage = "Test notification sent"
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Failed to send test notification"
                )
            }
        }
    }

    fun resetAllSettings() {
        viewModelScope.launch {
            try {
                userPreferences.resetToDefaults()
                _uiState.value = _uiState.value.copy(
                    successMessage = "Settings reset to defaults"
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Failed to reset settings"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(
            errorMessage = null,
            successMessage = null
        )
    }

    fun getAppVersion(): String {
        return "1.0.0" // This would be loaded from BuildConfig
    }

    fun getAppBuildNumber(): String {
        return "1" // This would be loaded from BuildConfig
    }

    fun getDatabaseVersion(): Int {
        return databaseRepository.getDatabaseVersion()
    }
}

data class SettingsUiState(
    val isLoading: Boolean = false,
    val isExporting: Boolean = false,
    val isImporting: Boolean = false,
    val isClearing: Boolean = false,
    val isOptimizing: Boolean = false,
    val notificationsEnabled: Boolean = true,
    val darkModeEnabled: Boolean = false,
    val medicalMode: Boolean = false,
    val showFodmapInfo: Boolean = true,
    val biometricAuthEnabled: Boolean = false,
    val totalFoodEntries: Int = 0,
    val totalSymptomEvents: Int = 0,
    val databaseSizeMB: Double = 0.0,
    val oldestEntryDate: String? = null,
    val exportFilePath: String? = null,
    val successMessage: String? = null,
    val errorMessage: String? = null
) {
    val hasData: Boolean
        get() = totalFoodEntries > 0 || totalSymptomEvents > 0

    val databaseSizeFormatted: String
        get() = if (databaseSizeMB < 1.0) {
            "${(databaseSizeMB * 1024).toInt()} KB"
        } else {
            "${"%.1f".format(databaseSizeMB)} MB"
        }

    val totalEntriesFormatted: String
        get() = "${totalFoodEntries + totalSymptomEvents} total entries"
}

private data class SettingsState(
    val notifications: Boolean,
    val darkMode: Boolean,
    val medical: Boolean,
    val fodmap: Boolean,
    val biometric: Boolean
)

enum class DataRetentionOption(val days: Int, val displayName: String) {
    ONE_MONTH(30, "1 Month"),
    THREE_MONTHS(90, "3 Months"),
    SIX_MONTHS(180, "6 Months"),
    ONE_YEAR(365, "1 Year"),
    FOREVER(-1, "Keep Forever")
}

enum class ExportFormatOption(val format: String, val displayName: String) {
    CSV("csv", "CSV Spreadsheet"),
    JSON("json", "JSON Data"),
    PDF("pdf", "PDF Report"),
    TEXT("txt", "Text Summary")
}

data class ExportResult(
    val filePath: String,
    val success: Boolean
)

data class ImportResult(
    val entriesCount: Int,
    val success: Boolean
)

data class OptimizationResult(
    val spaceSavedMB: Double,
    val success: Boolean
)

data class CleanupResult(
    val deletedEntries: Int,
    val success: Boolean
)