package com.fooddiary.presentation.ui.settings

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fooddiary.data.repository.UserPreferencesRepository
import com.fooddiary.data.repository.BackupRestoreRepository
import com.fooddiary.data.repository.NotificationRepository
import com.fooddiary.data.database.entities.UserPreferences
import com.fooddiary.data.database.entities.NotificationSettings
import com.fooddiary.data.models.*
import io.mockk.*
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.Instant
import java.time.LocalTime

/**
 * Comprehensive TDD unit tests for SettingsViewModel
 * Tests preferences management, backup/restore functionality, notification scheduling, and data sync
 *
 * THESE TESTS WILL FAIL initially because SettingsViewModel doesn't exist yet (TDD approach)
 */
@ExperimentalCoroutinesApi
class SettingsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var userPreferencesRepository: UserPreferencesRepository

    @MockK
    private lateinit var backupRestoreRepository: BackupRestoreRepository

    @MockK
    private lateinit var notificationRepository: NotificationRepository

    private lateinit var settingsViewModel: SettingsViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)

        // Setup default mock behaviors
        every { userPreferencesRepository.getUserPreferences() } returns flowOf(createDefaultUserPreferences())
        every { notificationRepository.getNotificationSettings() } returns flowOf(createDefaultNotificationSettings())
        coEvery { userPreferencesRepository.updatePreference(any(), any()) } just Runs
        coEvery { backupRestoreRepository.createBackup() } returns BackupResult.Success("/backup/path.db")

        settingsViewModel = SettingsViewModel(
            userPreferencesRepository,
            backupRestoreRepository,
            notificationRepository
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `initial state should load user preferences and notification settings`() = runTest {
        // Given - fresh ViewModel instance

        // When - accessing initial state after initialization
        advanceUntilIdle()
        val initialState = settingsViewModel.uiState.value

        // Then
        assertFalse("Should not be loading after initialization", initialState.isLoading)
        assertNotNull("User preferences should be loaded", initialState.userPreferences)
        assertNotNull("Notification settings should be loaded", initialState.notificationSettings)
        assertEquals("Default theme should be SYSTEM", ThemePreference.SYSTEM, initialState.userPreferences.themePreference)
        assertEquals("Error should be null", null, initialState.error)
    }

    @Test
    fun `updatePreference should save preference and update state`() = runTest {
        // Given
        val prefKey = "reminder_notifications"
        val newValue = true

        coEvery { userPreferencesRepository.updatePreference(prefKey, newValue) } just Runs
        every { userPreferencesRepository.getUserPreferences() } returns
            flowOf(createDefaultUserPreferences().copy(reminderNotifications = true))

        // When
        settingsViewModel.updatePreference(prefKey, newValue)
        advanceUntilIdle()

        // Then
        coVerify(exactly = 1) { userPreferencesRepository.updatePreference(prefKey, newValue) }

        val finalState = settingsViewModel.uiState.value
        assertTrue("Reminder notifications should be enabled", finalState.userPreferences.reminderNotifications)
        assertEquals("Should show success message", "Setting updated successfully", finalState.message)
    }

    @Test
    fun `updatePreference should handle update errors gracefully`() = runTest {
        // Given
        val prefKey = "invalid_preference"
        val newValue = "invalid_value"
        val testException = RuntimeException("Invalid preference")

        coEvery { userPreferencesRepository.updatePreference(prefKey, newValue) } throws testException

        // When
        settingsViewModel.updatePreference(prefKey, newValue)
        advanceUntilIdle()

        // Then
        val finalState = settingsViewModel.uiState.value
        assertEquals("Should show error message", "Invalid preference", finalState.error)
    }

    @Test
    fun `backupSettings should create backup and return file path`() = runTest {
        // Given
        val expectedBackupPath = "/storage/backup/food_diary_backup_20240115.db"
        coEvery { backupRestoreRepository.createBackup() } returns BackupResult.Success(expectedBackupPath)

        // When
        val result = settingsViewModel.backupSettings()
        advanceUntilIdle()

        // Then
        assertTrue("Backup should be successful", result)
        coVerify(exactly = 1) { backupRestoreRepository.createBackup() }

        val finalState = settingsViewModel.uiState.value
        assertFalse("Should not be backing up anymore", finalState.isBackingUp)
        assertEquals("Should show backup success message", "Backup created successfully", finalState.message)
        assertEquals("Last backup path should be updated", expectedBackupPath, finalState.lastBackupPath)
    }

    @Test
    fun `backupSettings should handle backup failures`() = runTest {
        // Given
        val backupError = "Insufficient storage space"
        coEvery { backupRestoreRepository.createBackup() } returns BackupResult.Error(backupError)

        // When
        val result = settingsViewModel.backupSettings()
        advanceUntilIdle()

        // Then
        assertFalse("Backup should fail", result)

        val finalState = settingsViewModel.uiState.value
        assertFalse("Should not be backing up", finalState.isBackingUp)
        assertEquals("Should show backup error", backupError, finalState.error)
    }

    @Test
    fun `restoreSettings should restore from backup file and update state`() = runTest {
        // Given
        val backupFilePath = "/storage/backup/food_diary_backup.db"
        coEvery { backupRestoreRepository.restoreFromBackup(backupFilePath) } returns RestoreResult.Success

        // When
        val result = settingsViewModel.restoreSettings(backupFilePath)
        advanceUntilIdle()

        // Then
        assertTrue("Restore should be successful", result)
        coVerify(exactly = 1) { backupRestoreRepository.restoreFromBackup(backupFilePath) }

        val finalState = settingsViewModel.uiState.value
        assertFalse("Should not be restoring anymore", finalState.isRestoring)
        assertEquals("Should show restore success message", "Settings restored successfully", finalState.message)
    }

    @Test
    fun `restoreSettings should handle restore failures`() = runTest {
        // Given
        val backupFilePath = "/invalid/backup/path.db"
        val restoreError = "Backup file not found or corrupted"
        coEvery { backupRestoreRepository.restoreFromBackup(backupFilePath) } returns
            RestoreResult.Error(restoreError)

        // When
        val result = settingsViewModel.restoreSettings(backupFilePath)
        advanceUntilIdle()

        // Then
        assertFalse("Restore should fail", result)

        val finalState = settingsViewModel.uiState.value
        assertFalse("Should not be restoring", finalState.isRestoring)
        assertEquals("Should show restore error", restoreError, finalState.error)
    }

    @Test
    fun `scheduleNotification should create notification with specified parameters`() = runTest {
        // Given
        val notificationType = NotificationType.MEAL_REMINDER
        val scheduledTime = LocalTime.of(12, 30)
        val message = "Time for lunch!"

        coEvery {
            notificationRepository.scheduleNotification(notificationType, scheduledTime, message)
        } returns true

        // When
        val result = settingsViewModel.scheduleNotification(notificationType, scheduledTime, message)
        advanceUntilIdle()

        // Then
        assertTrue("Notification scheduling should be successful", result)
        coVerify(exactly = 1) {
            notificationRepository.scheduleNotification(notificationType, scheduledTime, message)
        }

        val finalState = settingsViewModel.uiState.value
        assertEquals("Should show scheduling success message", "Notification scheduled successfully", finalState.message)
    }

    @Test
    fun `scheduleNotification should handle scheduling failures`() = runTest {
        // Given
        val notificationType = NotificationType.SYMPTOM_REMINDER
        val scheduledTime = LocalTime.of(20, 0)
        val message = "Track your symptoms"

        coEvery {
            notificationRepository.scheduleNotification(notificationType, scheduledTime, message)
        } returns false

        // When
        val result = settingsViewModel.scheduleNotification(notificationType, scheduledTime, message)
        advanceUntilIdle()

        // Then
        assertFalse("Notification scheduling should fail", result)

        val finalState = settingsViewModel.uiState.value
        assertEquals("Should show scheduling error", "Failed to schedule notification", finalState.error)
    }

    @Test
    fun `resetDefaults should restore all preferences to default values`() = runTest {
        // Given - modified preferences
        val modifiedPreferences = createDefaultUserPreferences().copy(
            themePreference = ThemePreference.DARK,
            reminderNotifications = false,
            dataSync = false
        )
        every { userPreferencesRepository.getUserPreferences() } returns flowOf(modifiedPreferences)

        coEvery { userPreferencesRepository.resetToDefaults() } just Runs
        every { userPreferencesRepository.getUserPreferences() } returns
            flowOf(createDefaultUserPreferences()) andThen flowOf(createDefaultUserPreferences())

        // When
        settingsViewModel.resetDefaults()
        advanceUntilIdle()

        // Then
        coVerify(exactly = 1) { userPreferencesRepository.resetToDefaults() }

        val finalState = settingsViewModel.uiState.value
        assertEquals("Theme should be reset to system", ThemePreference.SYSTEM, finalState.userPreferences.themePreference)
        assertTrue("Reminder notifications should be enabled", finalState.userPreferences.reminderNotifications)
        assertTrue("Data sync should be enabled", finalState.userPreferences.dataSync)
        assertEquals("Should show reset success message", "Settings reset to defaults", finalState.message)
    }

    @Test
    fun `updateTheme should change theme preference`() = runTest {
        // Given
        val newTheme = ThemePreference.DARK

        coEvery { userPreferencesRepository.updatePreference("theme_preference", newTheme) } just Runs
        every { userPreferencesRepository.getUserPreferences() } returns
            flowOf(createDefaultUserPreferences().copy(themePreference = newTheme))

        // When
        settingsViewModel.updateTheme(newTheme)
        advanceUntilIdle()

        // Then
        val finalState = settingsViewModel.uiState.value
        assertEquals("Theme should be updated", newTheme, finalState.userPreferences.themePreference)
    }

    @Test
    fun `toggleDataSync should enable or disable cloud synchronization`() = runTest {
        // Given
        val syncEnabled = true

        coEvery { userPreferencesRepository.updatePreference("data_sync", syncEnabled) } just Runs
        every { userPreferencesRepository.getUserPreferences() } returns
            flowOf(createDefaultUserPreferences().copy(dataSync = syncEnabled))

        // When
        settingsViewModel.toggleDataSync(syncEnabled)
        advanceUntilIdle()

        // Then
        val finalState = settingsViewModel.uiState.value
        assertTrue("Data sync should be enabled", finalState.userPreferences.dataSync)
    }

    @Test
    fun `updatePrivacySettings should modify privacy preferences`() = runTest {
        // Given
        val newPrivacySettings = PrivacySettings(
            anonymousAnalytics = false,
            crashReporting = false,
            dataSharing = false
        )

        coEvery { userPreferencesRepository.updatePrivacySettings(newPrivacySettings) } just Runs
        every { userPreferencesRepository.getUserPreferences() } returns
            flowOf(createDefaultUserPreferences().copy(privacySettings = newPrivacySettings))

        // When
        settingsViewModel.updatePrivacySettings(newPrivacySettings)
        advanceUntilIdle()

        // Then
        val finalState = settingsViewModel.uiState.value
        assertEquals("Privacy settings should be updated", newPrivacySettings, finalState.userPreferences.privacySettings)
    }

    @Test
    fun `exportSettings should create settings export file`() = runTest {
        // Given
        val exportPath = "/storage/exports/settings_export.json"
        coEvery { backupRestoreRepository.exportSettings() } returns exportPath

        // When
        val result = settingsViewModel.exportSettings()
        advanceUntilIdle()

        // Then
        assertEquals("Should return export file path", exportPath, result)

        val finalState = settingsViewModel.uiState.value
        assertEquals("Should show export success message", "Settings exported successfully", finalState.message)
    }

    @Test
    fun `importSettings should restore settings from import file`() = runTest {
        // Given
        val importPath = "/storage/downloads/settings_import.json"
        coEvery { backupRestoreRepository.importSettings(importPath) } returns ImportResult.Success

        // When
        val result = settingsViewModel.importSettings(importPath)
        advanceUntilIdle()

        // Then
        assertTrue("Import should be successful", result)

        val finalState = settingsViewModel.uiState.value
        assertEquals("Should show import success message", "Settings imported successfully", finalState.message)
    }

    @Test
    fun `clearCache should remove cached data and update storage info`() = runTest {
        // Given
        val clearedBytes = 50 * 1024 * 1024L // 50 MB
        coEvery { backupRestoreRepository.clearCache() } returns clearedBytes

        // When
        val result = settingsViewModel.clearCache()
        advanceUntilIdle()

        // Then
        assertEquals("Should return cleared bytes", clearedBytes, result)

        val finalState = settingsViewModel.uiState.value
        assertEquals("Should show cache cleared message", "Cache cleared (50 MB freed)", finalState.message)
        assertTrue("Storage info should be updated", finalState.storageInfo.availableSpace > 0)
    }

    @Test
    fun `getStorageInfo should return current storage usage statistics`() = runTest {
        // Given
        val mockStorageInfo = StorageInfo(
            totalSpace = 32 * 1024 * 1024 * 1024L, // 32 GB
            usedSpace = 256 * 1024 * 1024L,        // 256 MB
            availableSpace = 31 * 1024 * 1024 * 1024L, // ~31 GB
            appDataSize = 128 * 1024 * 1024L,      // 128 MB
            cacheSize = 64 * 1024 * 1024L          // 64 MB
        )

        coEvery { backupRestoreRepository.getStorageInfo() } returns mockStorageInfo

        // When
        settingsViewModel.refreshStorageInfo()
        advanceUntilIdle()

        // Then
        val finalState = settingsViewModel.uiState.value
        assertEquals("Storage info should be updated", mockStorageInfo, finalState.storageInfo)
    }

    @Test
    fun `validateBackupFile should check if backup file is valid`() = runTest {
        // Given
        val validBackupPath = "/storage/backups/valid_backup.db"
        val invalidBackupPath = "/storage/backups/corrupted_backup.db"

        coEvery { backupRestoreRepository.validateBackupFile(validBackupPath) } returns ValidationResult.Valid
        coEvery { backupRestoreRepository.validateBackupFile(invalidBackupPath) } returns
            ValidationResult.Invalid("Backup file is corrupted")

        // When & Then - valid file
        val validResult = settingsViewModel.validateBackupFile(validBackupPath)
        assertTrue("Valid backup should pass validation", validResult)

        // When & Then - invalid file
        val invalidResult = settingsViewModel.validateBackupFile(invalidBackupPath)
        assertFalse("Invalid backup should fail validation", invalidResult)

        val finalState = settingsViewModel.uiState.value
        assertEquals("Should show validation error", "Backup file is corrupted", finalState.error)
    }

    @Test
    fun `clearError should reset error state`() {
        // Given - error state
        settingsViewModel.updatePreference("invalid_key", "invalid_value")

        // When
        settingsViewModel.clearError()

        // Then
        val state = settingsViewModel.uiState.value
        assertEquals("Error should be cleared", null, state.error)
    }

    @Test
    fun `clearMessage should reset message state`() = runTest {
        // Given - success message
        settingsViewModel.updatePreference("reminder_notifications", true)
        advanceUntilIdle()

        // When
        settingsViewModel.clearMessage()

        // Then
        val state = settingsViewModel.uiState.value
        assertEquals("Message should be cleared", null, state.message)
    }

    // Helper methods for creating mock data
    private fun createDefaultUserPreferences(): UserPreferences {
        return UserPreferences(
            id = "default-prefs",
            themePreference = ThemePreference.SYSTEM,
            reminderNotifications = true,
            dataSync = true,
            privacySettings = PrivacySettings(
                anonymousAnalytics = true,
                crashReporting = true,
                dataSharing = false
            ),
            measurementUnits = MeasurementUnits.METRIC,
            defaultMealReminders = listOf(
                LocalTime.of(8, 0),   // Breakfast
                LocalTime.of(12, 30), // Lunch
                LocalTime.of(19, 0)   // Dinner
            ),
            symptomReminderFrequency = ReminderFrequency.DAILY,
            autoBackup = true,
            autoBackupFrequency = BackupFrequency.WEEKLY,
            lastModified = Instant.now()
        )
    }

    private fun createDefaultNotificationSettings(): NotificationSettings {
        return NotificationSettings(
            id = "default-notifications",
            mealReminders = true,
            symptomReminders = true,
            correlationAlerts = true,
            insightNotifications = true,
            quietHoursStart = LocalTime.of(22, 0),
            quietHoursEnd = LocalTime.of(7, 0),
            notificationSound = "default",
            vibrationEnabled = true,
            lastModified = Instant.now()
        )
    }
}

// Data classes that SettingsViewModel should use (these will need to be implemented)
data class SettingsUiState(
    val isLoading: Boolean = true,
    val userPreferences: UserPreferences = UserPreferences(),
    val notificationSettings: NotificationSettings = NotificationSettings(),
    val isBackingUp: Boolean = false,
    val isRestoring: Boolean = false,
    val lastBackupPath: String? = null,
    val lastBackupTime: Instant? = null,
    val storageInfo: StorageInfo = StorageInfo(),
    val error: String? = null,
    val message: String? = null
)

enum class ThemePreference {
    SYSTEM, LIGHT, DARK;

    val displayName: String
        get() = when (this) {
            SYSTEM -> "Follow System"
            LIGHT -> "Light Theme"
            DARK -> "Dark Theme"
        }
}

enum class MeasurementUnits {
    METRIC, IMPERIAL;

    val displayName: String
        get() = when (this) {
            METRIC -> "Metric (ml, g)"
            IMPERIAL -> "Imperial (fl oz, oz)"
        }
}

enum class ReminderFrequency {
    NEVER, DAILY, WEEKLY;

    val displayName: String
        get() = when (this) {
            NEVER -> "Never"
            DAILY -> "Daily"
            WEEKLY -> "Weekly"
        }
}

enum class BackupFrequency {
    DAILY, WEEKLY, MONTHLY;

    val displayName: String
        get() = when (this) {
            DAILY -> "Daily"
            WEEKLY -> "Weekly"
            MONTHLY -> "Monthly"
        }
}

enum class NotificationType {
    MEAL_REMINDER, SYMPTOM_REMINDER, CORRELATION_ALERT, INSIGHT_NOTIFICATION
}

data class PrivacySettings(
    val anonymousAnalytics: Boolean = true,
    val crashReporting: Boolean = true,
    val dataSharing: Boolean = false
)

data class StorageInfo(
    val totalSpace: Long = 0L,
    val usedSpace: Long = 0L,
    val availableSpace: Long = 0L,
    val appDataSize: Long = 0L,
    val cacheSize: Long = 0L
)

sealed class BackupResult {
    data class Success(val filePath: String) : BackupResult()
    data class Error(val message: String) : BackupResult()
}

sealed class RestoreResult {
    object Success : RestoreResult()
    data class Error(val message: String) : RestoreResult()
}

sealed class ImportResult {
    object Success : ImportResult()
    data class Error(val message: String) : ImportResult()
}

sealed class ValidationResult {
    object Valid : ValidationResult()
    data class Invalid(val reason: String) : ValidationResult()
}