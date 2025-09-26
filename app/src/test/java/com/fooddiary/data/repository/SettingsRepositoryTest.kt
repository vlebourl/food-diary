package com.fooddiary.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fooddiary.data.database.dao.UserPreferencesDao
import com.fooddiary.data.database.entities.UserPreferences
import com.fooddiary.data.models.*
import com.fooddiary.data.preferences.PreferencesManager
import com.fooddiary.data.backup.SettingsBackupManager
import io.mockk.*
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.Instant
import java.time.LocalTime
import java.time.ZoneId

/**
 * TDD Tests for SettingsRepository - THESE WILL FAIL INITIALLY
 *
 * Test coverage:
 * - User preferences management and persistence
 * - Settings validation and defaults
 * - Notification and reminder settings
 * - Privacy and data settings
 * - Backup and restore functionality
 * - Theme and UI preferences
 */
class SettingsRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var userPreferencesDao: UserPreferencesDao

    @MockK
    private lateinit var preferencesManager: PreferencesManager

    @MockK
    private lateinit var backupManager: SettingsBackupManager

    private lateinit var repository: SettingsRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        // This will fail initially since SettingsRepository doesn't exist yet
        repository = SettingsRepository(
            userPreferencesDao = userPreferencesDao,
            preferencesManager = preferencesManager,
            backupManager = backupManager
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getPreferences should return user preferences with defaults for missing values`() = runTest {
        // Arrange
        val storedPreferences = UserPreferences(
            id = 1L,
            userId = "user-123",
            theme = AppTheme.DARK,
            language = "en",
            timezone = ZoneId.systemDefault().id,
            notificationsEnabled = true,
            reminderSettings = createDefaultReminderSettings(),
            privacySettings = createDefaultPrivacySettings(),
            dataRetentionDays = 365,
            autoBackupEnabled = false,
            createdAt = Instant.now(),
            modifiedAt = Instant.now()
        )

        coEvery { userPreferencesDao.getByUserId("user-123") } returns storedPreferences
        every { preferencesManager.applyDefaults(any()) } returns storedPreferences

        // Act
        val preferences = repository.getPreferences("user-123").first()

        // Assert
        assertNotNull("Should return preferences", preferences)
        assertEquals("Should have correct theme", AppTheme.DARK, preferences.theme)
        assertEquals("Should have notifications enabled", true, preferences.notificationsEnabled)
        assertEquals("Should have default data retention", 365, preferences.dataRetentionDays)
        assertNotNull("Should have reminder settings", preferences.reminderSettings)
        assertNotNull("Should have privacy settings", preferences.privacySettings)

        coVerify { userPreferencesDao.getByUserId("user-123") }
        verify { preferencesManager.applyDefaults(storedPreferences) }
    }

    @Test
    fun `getPreferences should create defaults for new user`() = runTest {
        // Arrange
        val userId = "new-user-456"
        val defaultPreferences = UserPreferences(
            id = 0L,
            userId = userId,
            theme = AppTheme.SYSTEM,
            language = "en",
            timezone = ZoneId.systemDefault().id,
            notificationsEnabled = true,
            reminderSettings = createDefaultReminderSettings(),
            privacySettings = createDefaultPrivacySettings(),
            dataRetentionDays = 365,
            autoBackupEnabled = true,
            createdAt = Instant.now(),
            modifiedAt = Instant.now()
        )

        coEvery { userPreferencesDao.getByUserId(userId) } returns null
        every { preferencesManager.createDefaultPreferences(userId) } returns defaultPreferences
        coEvery { userPreferencesDao.insert(any()) } returns 123L

        // Act
        val preferences = repository.getPreferences(userId).first()

        // Assert
        assertEquals("Should create default theme", AppTheme.SYSTEM, preferences.theme)
        assertEquals("Should enable notifications by default", true, preferences.notificationsEnabled)
        assertEquals("Should enable auto backup by default", true, preferences.autoBackupEnabled)

        verify { preferencesManager.createDefaultPreferences(userId) }
        coVerify { userPreferencesDao.insert(defaultPreferences) }
    }

    @Test
    fun `updatePreferences should validate and save changes`() = runTest {
        // Arrange
        val userId = "user-123"
        val existingPreferences = createTestUserPreferences(userId)
        val updatedSettings = PreferencesUpdate(
            theme = AppTheme.LIGHT,
            notificationsEnabled = false,
            reminderSettings = ReminderSettings(
                mealReminders = MealReminderSettings(
                    enabled = true,
                    breakfastTime = LocalTime.of(8, 0),
                    lunchTime = LocalTime.of(12, 30),
                    dinnerTime = LocalTime.of(18, 0)
                ),
                symptomCheckIns = SymptomReminderSettings(
                    enabled = true,
                    frequency = ReminderFrequency.TWICE_DAILY,
                    times = listOf(LocalTime.of(14, 0), LocalTime.of(20, 0))
                )
            )
        )

        val validationResult = ValidationResult.Success
        val updatedPreferences = existingPreferences.copy(
            theme = updatedSettings.theme!!,
            notificationsEnabled = updatedSettings.notificationsEnabled!!,
            reminderSettings = updatedSettings.reminderSettings!!,
            modifiedAt = Instant.now()
        )

        coEvery { userPreferencesDao.getByUserId(userId) } returns existingPreferences
        every { preferencesManager.validatePreferences(any()) } returns validationResult
        coEvery { userPreferencesDao.update(any()) } returns 1

        // Act
        val result = repository.updatePreferences(userId, updatedSettings)

        // Assert
        assertTrue("Should successfully update preferences", result.isSuccess)

        coVerify { userPreferencesDao.update(match { prefs ->
            prefs.theme == AppTheme.LIGHT &&
            !prefs.notificationsEnabled &&
            prefs.reminderSettings.mealReminders.enabled &&
            prefs.modifiedAt.isAfter(existingPreferences.modifiedAt)
        }) }

        verify { preferencesManager.validatePreferences(any()) }
    }

    @Test
    fun `updatePreferences should reject invalid settings`() = runTest {
        // Arrange
        val userId = "user-123"
        val existingPreferences = createTestUserPreferences(userId)
        val invalidUpdate = PreferencesUpdate(
            dataRetentionDays = -1, // Invalid: negative retention period
            reminderSettings = ReminderSettings(
                mealReminders = MealReminderSettings(
                    enabled = true,
                    breakfastTime = LocalTime.of(25, 0), // Invalid: hour > 24
                    lunchTime = LocalTime.of(12, 0),
                    dinnerTime = LocalTime.of(18, 0)
                )
            )
        )

        val validationResult = ValidationResult.Error("Data retention days must be positive")

        coEvery { userPreferencesDao.getByUserId(userId) } returns existingPreferences
        every { preferencesManager.validatePreferences(any()) } returns validationResult

        // Act
        val result = repository.updatePreferences(userId, invalidUpdate)

        // Assert
        assertTrue("Should fail for invalid settings", result.isFailure)
        assertEquals("Should return validation error",
            "Data retention days must be positive", result.exceptionOrNull()?.message)

        coVerify(exactly = 0) { userPreferencesDao.update(any()) }
    }

    @Test
    fun `resetDefaults should restore factory settings`() = runTest {
        // Arrange
        val userId = "user-123"
        val existingPreferences = createTestUserPreferences(userId)
        val factoryDefaults = UserPreferences(
            id = existingPreferences.id,
            userId = userId,
            theme = AppTheme.SYSTEM,
            language = "en",
            timezone = ZoneId.systemDefault().id,
            notificationsEnabled = true,
            reminderSettings = createDefaultReminderSettings(),
            privacySettings = createDefaultPrivacySettings(),
            dataRetentionDays = 365,
            autoBackupEnabled = true,
            createdAt = existingPreferences.createdAt,
            modifiedAt = Instant.now()
        )

        coEvery { userPreferencesDao.getByUserId(userId) } returns existingPreferences
        every { preferencesManager.getFactoryDefaults(userId) } returns factoryDefaults
        coEvery { userPreferencesDao.update(any()) } returns 1

        // Act
        val result = repository.resetDefaults(userId)

        // Assert
        assertTrue("Should successfully reset to defaults", result.isSuccess)

        coVerify { userPreferencesDao.update(match { prefs ->
            prefs.theme == AppTheme.SYSTEM &&
            prefs.notificationsEnabled &&
            prefs.dataRetentionDays == 365 &&
            prefs.autoBackupEnabled &&
            prefs.modifiedAt.isAfter(existingPreferences.modifiedAt)
        }) }

        verify { preferencesManager.getFactoryDefaults(userId) }
    }

    @Test
    fun `exportSettings should create settings backup`() = runTest {
        // Arrange
        val userId = "user-123"
        val userPreferences = createTestUserPreferences(userId)
        val settingsBackup = SettingsBackup(
            version = "1.0",
            exportedAt = Instant.now(),
            userId = userId,
            preferences = userPreferences.toExportFormat(),
            customizations = mapOf(
                "quick_entries" to "['Breakfast Template', 'Lunch Template']",
                "favorite_foods" to "['Apple', 'Rice', 'Chicken']"
            )
        )
        val backupPath = "/backups/settings_user-123.json"

        coEvery { userPreferencesDao.getByUserId(userId) } returns userPreferences
        coEvery { backupManager.createBackup(any()) } returns settingsBackup
        coEvery { backupManager.saveBackup(any()) } returns backupPath

        // Act
        val result = repository.exportSettings(userId)

        // Assert
        assertTrue("Should successfully export settings", result.isSuccess)
        assertEquals("Should return backup file path", backupPath, result.getOrNull())

        coVerify { backupManager.createBackup(userPreferences) }
        coVerify { backupManager.saveBackup(settingsBackup) }
    }

    @Test
    fun `importSettings should restore from backup with validation`() = runTest {
        // Arrange
        val userId = "user-123"
        val backupPath = "/backups/settings_backup.json"
        val settingsBackup = SettingsBackup(
            version = "1.0",
            exportedAt = Instant.now().minusSeconds(3600),
            userId = userId,
            preferences = PreferencesExport(
                theme = "DARK",
                language = "en",
                notificationsEnabled = true,
                dataRetentionDays = 180
            ),
            customizations = mapOf()
        )

        val restoredPreferences = UserPreferences(
            id = 0L,
            userId = userId,
            theme = AppTheme.DARK,
            language = "en",
            timezone = ZoneId.systemDefault().id,
            notificationsEnabled = true,
            reminderSettings = createDefaultReminderSettings(),
            privacySettings = createDefaultPrivacySettings(),
            dataRetentionDays = 180,
            autoBackupEnabled = true,
            createdAt = Instant.now(),
            modifiedAt = Instant.now()
        )

        val validationResult = ValidationResult.Success

        coEvery { backupManager.loadBackup(backupPath) } returns settingsBackup
        every { backupManager.validateBackup(settingsBackup) } returns validationResult
        every { preferencesManager.fromExportFormat(any()) } returns restoredPreferences
        coEvery { userPreferencesDao.getByUserId(userId) } returns null
        coEvery { userPreferencesDao.insert(any()) } returns 456L

        // Act
        val result = repository.importSettings(userId, backupPath)

        // Assert
        assertTrue("Should successfully import settings", result.isSuccess)

        verify { backupManager.validateBackup(settingsBackup) }
        verify { preferencesManager.fromExportFormat(settingsBackup.preferences) }
        coVerify { userPreferencesDao.insert(restoredPreferences) }
    }

    @Test
    fun `importSettings should reject invalid backup`() = runTest {
        // Arrange
        val userId = "user-123"
        val corruptBackupPath = "/backups/corrupt_backup.json"
        val corruptBackup = SettingsBackup(
            version = "0.5", // Unsupported version
            exportedAt = Instant.now().minusSeconds(3600),
            userId = "different-user", // Different user
            preferences = PreferencesExport(
                theme = "INVALID_THEME",
                language = "",
                notificationsEnabled = true,
                dataRetentionDays = -50 // Invalid
            ),
            customizations = mapOf()
        )

        val validationResult = ValidationResult.Error("Incompatible backup version and invalid data")

        coEvery { backupManager.loadBackup(corruptBackupPath) } returns corruptBackup
        every { backupManager.validateBackup(corruptBackup) } returns validationResult

        // Act
        val result = repository.importSettings(userId, corruptBackupPath)

        // Assert
        assertTrue("Should fail for invalid backup", result.isFailure)
        assertTrue("Should contain validation error",
            result.exceptionOrNull()?.message?.contains("Incompatible backup version") == true)

        verify { backupManager.validateBackup(corruptBackup) }
        coVerify(exactly = 0) { userPreferencesDao.insert(any()) }
    }

    @Test
    fun `updateNotificationSettings should manage notification preferences`() = runTest {
        // Arrange
        val userId = "user-123"
        val existingPreferences = createTestUserPreferences(userId)
        val notificationUpdate = NotificationPreferencesUpdate(
            enabled = true,
            mealReminders = MealReminderUpdate(
                enabled = true,
                breakfastTime = LocalTime.of(7, 30),
                lunchTime = LocalTime.of(12, 0),
                dinnerTime = LocalTime.of(19, 0),
                snackReminders = false
            ),
            symptomCheckIns = SymptomReminderUpdate(
                enabled = true,
                frequency = ReminderFrequency.THREE_TIMES_DAILY,
                customTimes = listOf(
                    LocalTime.of(10, 0),
                    LocalTime.of(16, 0),
                    LocalTime.of(22, 0)
                )
            ),
            medicationReminders = MedicationReminderUpdate(
                enabled = false
            )
        )

        coEvery { userPreferencesDao.getByUserId(userId) } returns existingPreferences
        coEvery { userPreferencesDao.update(any()) } returns 1

        // Act
        val result = repository.updateNotificationSettings(userId, notificationUpdate)

        // Assert
        assertTrue("Should successfully update notification settings", result.isSuccess)

        coVerify { userPreferencesDao.update(match { prefs ->
            prefs.notificationsEnabled &&
            prefs.reminderSettings.mealReminders.breakfastTime == LocalTime.of(7, 30) &&
            prefs.reminderSettings.symptomCheckIns.frequency == ReminderFrequency.THREE_TIMES_DAILY &&
            prefs.reminderSettings.symptomCheckIns.times.size == 3
        }) }
    }

    @Test
    fun `updatePrivacySettings should manage data privacy preferences`() = runTest {
        // Arrange
        val userId = "user-123"
        val existingPreferences = createTestUserPreferences(userId)
        val privacyUpdate = PrivacySettingsUpdate(
            dataCollection = DataCollectionSettings(
                analytics = false,
                crashReporting = true,
                performanceMetrics = false
            ),
            sharing = DataSharingSettings(
                allowResearchParticipation = false,
                anonymousDataSharing = false
            ),
            retention = DataRetentionSettings(
                retentionPeriodDays = 180,
                autoDeleteAfterRetention = true,
                deleteOnAccountClosure = true
            ),
            backup = BackupSettings(
                cloudBackupEnabled = false,
                localBackupEnabled = true,
                encryptBackups = true
            )
        )

        coEvery { userPreferencesDao.getByUserId(userId) } returns existingPreferences
        coEvery { userPreferencesDao.update(any()) } returns 1

        // Act
        val result = repository.updatePrivacySettings(userId, privacyUpdate)

        // Assert
        assertTrue("Should successfully update privacy settings", result.isSuccess)

        coVerify { userPreferencesDao.update(match { prefs ->
            !prefs.privacySettings.dataCollection.analytics &&
            prefs.privacySettings.dataCollection.crashReporting &&
            prefs.dataRetentionDays == 180 &&
            !prefs.privacySettings.sharing.allowResearchParticipation &&
            prefs.privacySettings.backup.encryptBackups
        }) }
    }

    // Test helper methods
    private fun createTestUserPreferences(userId: String) = UserPreferences(
        id = 1L,
        userId = userId,
        theme = AppTheme.SYSTEM,
        language = "en",
        timezone = ZoneId.systemDefault().id,
        notificationsEnabled = true,
        reminderSettings = createDefaultReminderSettings(),
        privacySettings = createDefaultPrivacySettings(),
        dataRetentionDays = 365,
        autoBackupEnabled = true,
        createdAt = Instant.now().minusSeconds(3600),
        modifiedAt = Instant.now().minusSeconds(1800)
    )

    private fun createDefaultReminderSettings() = ReminderSettings(
        mealReminders = MealReminderSettings(
            enabled = true,
            breakfastTime = LocalTime.of(8, 0),
            lunchTime = LocalTime.of(12, 0),
            dinnerTime = LocalTime.of(18, 0),
            snackReminders = false
        ),
        symptomCheckIns = SymptomReminderSettings(
            enabled = false,
            frequency = ReminderFrequency.TWICE_DAILY,
            times = listOf(LocalTime.of(14, 0), LocalTime.of(20, 0))
        ),
        medicationReminders = MedicationReminderSettings(
            enabled = false,
            medications = emptyList()
        )
    )

    private fun createDefaultPrivacySettings() = PrivacySettings(
        dataCollection = DataCollectionSettings(
            analytics = true,
            crashReporting = true,
            performanceMetrics = true
        ),
        sharing = DataSharingSettings(
            allowResearchParticipation = false,
            anonymousDataSharing = false
        ),
        retention = DataRetentionSettings(
            retentionPeriodDays = 365,
            autoDeleteAfterRetention = false,
            deleteOnAccountClosure = true
        ),
        backup = BackupSettings(
            cloudBackupEnabled = true,
            localBackupEnabled = true,
            encryptBackups = true
        )
    )
}

/**
 * Supporting data classes that would be defined in the repository
 */
data class PreferencesUpdate(
    val theme: AppTheme? = null,
    val language: String? = null,
    val timezone: String? = null,
    val notificationsEnabled: Boolean? = null,
    val reminderSettings: ReminderSettings? = null,
    val privacySettings: PrivacySettings? = null,
    val dataRetentionDays: Int? = null,
    val autoBackupEnabled: Boolean? = null
)

data class ReminderSettings(
    val mealReminders: MealReminderSettings,
    val symptomCheckIns: SymptomReminderSettings,
    val medicationReminders: MedicationReminderSettings
)

data class MealReminderSettings(
    val enabled: Boolean,
    val breakfastTime: LocalTime,
    val lunchTime: LocalTime,
    val dinnerTime: LocalTime,
    val snackReminders: Boolean = false
)

data class SymptomReminderSettings(
    val enabled: Boolean,
    val frequency: ReminderFrequency,
    val times: List<LocalTime>
)

data class MedicationReminderSettings(
    val enabled: Boolean,
    val medications: List<MedicationReminder> = emptyList()
)

data class MedicationReminder(
    val name: String,
    val dosage: String,
    val times: List<LocalTime>
)

data class PrivacySettings(
    val dataCollection: DataCollectionSettings,
    val sharing: DataSharingSettings,
    val retention: DataRetentionSettings,
    val backup: BackupSettings
)

data class DataCollectionSettings(
    val analytics: Boolean,
    val crashReporting: Boolean,
    val performanceMetrics: Boolean
)

data class DataSharingSettings(
    val allowResearchParticipation: Boolean,
    val anonymousDataSharing: Boolean
)

data class DataRetentionSettings(
    val retentionPeriodDays: Int,
    val autoDeleteAfterRetention: Boolean,
    val deleteOnAccountClosure: Boolean
)

data class BackupSettings(
    val cloudBackupEnabled: Boolean,
    val localBackupEnabled: Boolean,
    val encryptBackups: Boolean
)

data class SettingsBackup(
    val version: String,
    val exportedAt: Instant,
    val userId: String,
    val preferences: PreferencesExport,
    val customizations: Map<String, String>
)

data class PreferencesExport(
    val theme: String,
    val language: String,
    val notificationsEnabled: Boolean,
    val dataRetentionDays: Int
)

data class NotificationPreferencesUpdate(
    val enabled: Boolean,
    val mealReminders: MealReminderUpdate? = null,
    val symptomCheckIns: SymptomReminderUpdate? = null,
    val medicationReminders: MedicationReminderUpdate? = null
)

data class MealReminderUpdate(
    val enabled: Boolean,
    val breakfastTime: LocalTime? = null,
    val lunchTime: LocalTime? = null,
    val dinnerTime: LocalTime? = null,
    val snackReminders: Boolean = false
)

data class SymptomReminderUpdate(
    val enabled: Boolean,
    val frequency: ReminderFrequency? = null,
    val customTimes: List<LocalTime>? = null
)

data class MedicationReminderUpdate(
    val enabled: Boolean,
    val medications: List<MedicationReminder>? = null
)

data class PrivacySettingsUpdate(
    val dataCollection: DataCollectionSettings? = null,
    val sharing: DataSharingSettings? = null,
    val retention: DataRetentionSettings? = null,
    val backup: BackupSettings? = null
)

// Extension function for preferences export
fun UserPreferences.toExportFormat() = PreferencesExport(
    theme = theme.name,
    language = language,
    notificationsEnabled = notificationsEnabled,
    dataRetentionDays = dataRetentionDays
)

enum class AppTheme {
    LIGHT, DARK, SYSTEM
}

enum class ReminderFrequency {
    ONCE_DAILY, TWICE_DAILY, THREE_TIMES_DAILY, CUSTOM
}

sealed class ValidationResult {
    object Success : ValidationResult()
    data class Error(val message: String) : ValidationResult()
}