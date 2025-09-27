package com.fooddiary.data.database.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fooddiary.data.database.FoodDiaryDatabase
import com.fooddiary.data.database.entities.UserPreferences
import com.fooddiary.data.database.entities.MeasurementUnit
import com.fooddiary.data.database.entities.ExportFormat
import com.fooddiary.data.database.entities.ReportTemplate
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.Instant
import java.time.LocalTime

@RunWith(AndroidJUnit4::class)
class UserPreferencesDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: FoodDiaryDatabase
    private lateinit var userPreferencesDao: UserPreferencesDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            FoodDiaryDatabase::class.java
        ).allowMainThreadQueries().build()

        userPreferencesDao = database.userPreferencesDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insert_shouldInsertUserPreferences() = runTest {
        // Arrange
        val preferences = createTestUserPreferences()

        // Act
        val insertedId = userPreferencesDao.insert(preferences)

        // Assert
        assertTrue("Inserted ID should be greater than 0", insertedId > 0)

        val retrieved = userPreferencesDao.get()
        assertNotNull("Retrieved preferences should not be null", retrieved)
        assertEquals("Correlation time window should match", preferences.correlationTimeWindowHours, retrieved!!.correlationTimeWindowHours)
        assertEquals("Measurement unit should match", preferences.measurementUnit, retrieved.measurementUnit)
        assertEquals("Notification enabled should match", preferences.notificationEnabled, retrieved.notificationEnabled)
    }

    @Test
    fun insert_shouldReplaceExistingPreferences_singleRowTable() = runTest {
        // Arrange - Single row table should replace existing record
        val firstPreferences = createTestUserPreferences(
            correlationTimeWindowHours = 2,
            measurementUnit = MeasurementUnit.METRIC
        )
        val secondPreferences = createTestUserPreferences(
            correlationTimeWindowHours = 4,
            measurementUnit = MeasurementUnit.IMPERIAL
        )

        // Act
        userPreferencesDao.insert(firstPreferences)
        userPreferencesDao.insert(secondPreferences)

        // Assert
        val retrieved = userPreferencesDao.get()
        assertNotNull("Should have preferences", retrieved)
        assertEquals("Should have the second preferences values", 4, retrieved!!.correlationTimeWindowHours)
        assertEquals("Should use imperial units", MeasurementUnit.IMPERIAL, retrieved.measurementUnit)

        // Verify only one row exists
        val allPreferences = userPreferencesDao.getAll()
        assertEquals("Should have exactly 1 row in single-row table", 1, allPreferences.size)
    }

    @Test
    fun update_shouldUpdateUserPreferences() = runTest {
        // Arrange
        val originalPreferences = createTestUserPreferences()
        userPreferencesDao.insert(originalPreferences)

        val updatedPreferences = originalPreferences.update(
            correlationTimeWindowHours = 6,
            notificationEnabled = false,
            dataRetentionMonths = 24,
            exportFormat = ExportFormat.CSV
        )

        // Act
        val updateCount = userPreferencesDao.update(updatedPreferences)

        // Assert
        assertEquals("Should update 1 preferences record", 1, updateCount)

        val retrieved = userPreferencesDao.get()
        assertNotNull("Updated preferences should exist", retrieved)
        assertEquals("Correlation time window should be updated", 6, retrieved!!.correlationTimeWindowHours)
        assertFalse("Notifications should be disabled", retrieved.notificationEnabled)
        assertEquals("Data retention should be updated", 24, retrieved.dataRetentionMonths)
        assertEquals("Export format should be updated", ExportFormat.CSV, retrieved.exportFormat)
    }

    @Test
    fun get_shouldReturnUserPreferences() = runTest {
        // Arrange
        val preferences = createTestUserPreferences(
            correlationTimeWindowHours = 5,
            measurementUnit = MeasurementUnit.IMPERIAL,
            notificationEnabled = false
        )
        userPreferencesDao.insert(preferences)

        // Act
        val retrieved = userPreferencesDao.get()

        // Assert
        assertNotNull("Retrieved preferences should not be null", retrieved)
        assertEquals("Correlation time window should match", 5, retrieved!!.correlationTimeWindowHours)
        assertEquals("Measurement unit should match", MeasurementUnit.IMPERIAL, retrieved.measurementUnit)
        assertFalse("Notification should be disabled", retrieved.notificationEnabled)
    }

    @Test
    fun get_shouldReturnNull_whenNoPreferencesExist() = runTest {
        // Act
        val retrieved = userPreferencesDao.get()

        // Assert
        assertNull("Should return null when no preferences exist", retrieved)
    }

    @Test
    fun getAll_shouldReturnAllPreferences_singleRowTable() = runTest {
        // Arrange
        val preferences = createTestUserPreferences()
        userPreferencesDao.insert(preferences)

        // Act
        val allPreferences = userPreferencesDao.getAll()

        // Assert
        assertEquals("Should return exactly 1 preference record", 1, allPreferences.size)
        assertEquals("Should match inserted preferences", preferences.correlationTimeWindowHours, allPreferences.first().correlationTimeWindowHours)
    }

    @Test
    fun getOrDefault_shouldReturnDefaultPreferences_whenNoneExist() = runTest {
        // Act
        val preferences = userPreferencesDao.getOrDefault()

        // Assert
        assertNotNull("Should return default preferences", preferences)
        assertEquals("Should have default correlation time window", 3, preferences.correlationTimeWindowHours)
        assertEquals("Should have default measurement unit", MeasurementUnit.METRIC, preferences.measurementUnit)
        assertTrue("Should have notifications enabled by default", preferences.notificationEnabled)
        assertEquals("Should have default data retention", 12, preferences.dataRetentionMonths)
        assertEquals("Should have default export format", ExportFormat.JSON, preferences.exportFormat)
        assertTrue("Should have privacy mode enabled", preferences.privacyMode)
    }

    @Test
    fun getOrDefault_shouldReturnExistingPreferences_whenTheyExist() = runTest {
        // Arrange
        val customPreferences = createTestUserPreferences(
            correlationTimeWindowHours = 8,
            measurementUnit = MeasurementUnit.IMPERIAL
        )
        userPreferencesDao.insert(customPreferences)

        // Act
        val preferences = userPreferencesDao.getOrDefault()

        // Assert
        assertNotNull("Should return existing preferences", preferences)
        assertEquals("Should return custom correlation time window", 8, preferences.correlationTimeWindowHours)
        assertEquals("Should return custom measurement unit", MeasurementUnit.IMPERIAL, preferences.measurementUnit)
    }

    @Test
    fun updatePreferences_shouldUpdateSpecificFields() = runTest {
        // Arrange
        val originalPreferences = createTestUserPreferences()
        userPreferencesDao.insert(originalPreferences)

        // Act
        val updateCount = userPreferencesDao.updatePreferences(
            correlationTimeWindowHours = 10,
            notificationEnabled = false,
            privacyMode = false
        )

        // Assert
        assertEquals("Should update 1 record", 1, updateCount)

        val updated = userPreferencesDao.get()
        assertNotNull("Updated preferences should exist", updated)
        assertEquals("Correlation time window should be updated", 10, updated!!.correlationTimeWindowHours)
        assertFalse("Notifications should be disabled", updated.notificationEnabled)
        assertFalse("Privacy mode should be disabled", updated.privacyMode)

        // Verify other fields remain unchanged
        assertEquals("Measurement unit should remain unchanged", originalPreferences.measurementUnit, updated.measurementUnit)
        assertEquals("Data retention should remain unchanged", originalPreferences.dataRetentionMonths, updated.dataRetentionMonths)
        assertEquals("Export format should remain unchanged", originalPreferences.exportFormat, updated.exportFormat)
    }

    @Test
    fun updateNotificationSettings_shouldUpdateNotificationRelatedFields() = runTest {
        // Arrange
        val preferences = createTestUserPreferences(
            notificationEnabled = false,
            notificationTime = null
        )
        userPreferencesDao.insert(preferences)

        val notificationTime = LocalTime.of(9, 30)

        // Act
        val updateCount = userPreferencesDao.updateNotificationSettings(
            enabled = true,
            time = notificationTime
        )

        // Assert
        assertEquals("Should update 1 record", 1, updateCount)

        val updated = userPreferencesDao.get()
        assertNotNull("Updated preferences should exist", updated)
        assertTrue("Notifications should be enabled", updated!!.notificationEnabled)
        assertEquals("Notification time should be updated", notificationTime, updated.notificationTime)
    }

    @Test
    fun updateCustomSymptomTypes_shouldUpdateSymptomTypesList() = runTest {
        // Arrange
        val preferences = createTestUserPreferences(customSymptomTypes = emptyList())
        userPreferencesDao.insert(preferences)

        val customSymptoms = listOf("Custom Headache", "Custom Fatigue", "Custom Mood")

        // Act
        val updateCount = userPreferencesDao.updateCustomSymptomTypes(customSymptoms)

        // Assert
        assertEquals("Should update 1 record", 1, updateCount)

        val updated = userPreferencesDao.get()
        assertNotNull("Updated preferences should exist", updated)
        assertEquals("Custom symptom types should be updated", customSymptoms, updated!!.customSymptomTypes)
    }

    @Test
    fun delete_shouldRemoveUserPreferences() = runTest {
        // Arrange
        val preferences = createTestUserPreferences()
        userPreferencesDao.insert(preferences)

        // Act
        val deleteCount = userPreferencesDao.delete(preferences)

        // Assert
        assertEquals("Should delete 1 record", 1, deleteCount)

        val retrieved = userPreferencesDao.get()
        assertNull("Preferences should not exist after deletion", retrieved)
    }

    @Test
    fun deleteAll_shouldRemoveAllPreferences() = runTest {
        // Arrange
        val preferences = createTestUserPreferences()
        userPreferencesDao.insert(preferences)

        // Act
        val deleteCount = userPreferencesDao.deleteAll()

        // Assert
        assertEquals("Should delete 1 record", 1, deleteCount)

        val retrieved = userPreferencesDao.get()
        assertNull("Preferences should not exist after deleteAll", retrieved)
    }

    @Test
    fun resetToDefaults_shouldRestoreDefaultPreferences() = runTest {
        // Arrange
        val customPreferences = createTestUserPreferences(
            correlationTimeWindowHours = 10,
            measurementUnit = MeasurementUnit.IMPERIAL,
            notificationEnabled = false,
            dataRetentionMonths = 6,
            exportFormat = ExportFormat.CSV
        )
        userPreferencesDao.insert(customPreferences)

        // Act
        val resetCount = userPreferencesDao.resetToDefaults()

        // Assert
        assertEquals("Should reset 1 record", 1, resetCount)

        val reset = userPreferencesDao.get()
        assertNotNull("Reset preferences should exist", reset)
        assertEquals("Should reset to default correlation time window", 3, reset!!.correlationTimeWindowHours)
        assertEquals("Should reset to default measurement unit", MeasurementUnit.METRIC, reset.measurementUnit)
        assertTrue("Should reset to default notification enabled", reset.notificationEnabled)
        assertEquals("Should reset to default data retention", 12, reset.dataRetentionMonths)
        assertEquals("Should reset to default export format", ExportFormat.JSON, reset.exportFormat)
        assertTrue("Should reset to default privacy mode", reset.privacyMode)
    }

    @Test
    fun entity_validation_shouldEnforceConstraints() {
        // Test entity validation rules

        // Valid preferences should work
        val validPreferences = createTestUserPreferences()
        assertNotNull("Valid preferences should be created", validPreferences)

        // Test correlation time window constraints
        try {
            createTestUserPreferences(correlationTimeWindowHours = 0)
            fail("Should throw exception for correlation time window < 1")
        } catch (e: IllegalArgumentException) {
            assertTrue("Should contain correlation time window validation message",
                e.message?.contains("Correlation time window must be between 1-48 hours") == true)
        }

        try {
            createTestUserPreferences(correlationTimeWindowHours = 50)
            fail("Should throw exception for correlation time window > 48")
        } catch (e: IllegalArgumentException) {
            assertTrue("Should contain correlation time window validation message",
                e.message?.contains("Correlation time window must be between 1-48 hours") == true)
        }

        // Test data retention constraints
        try {
            createTestUserPreferences(dataRetentionMonths = 0)
            fail("Should throw exception for data retention < 1")
        } catch (e: IllegalArgumentException) {
            assertTrue("Should contain data retention validation message",
                e.message?.contains("Data retention must be between 1-60 months") == true)
        }

        try {
            createTestUserPreferences(dataRetentionMonths = 61)
            fail("Should throw exception for data retention > 60")
        } catch (e: IllegalArgumentException) {
            assertTrue("Should contain data retention validation message",
                e.message?.contains("Data retention must be between 1-60 months") == true)
        }

        // Test custom symptom types constraint
        try {
            val tooManySymptoms = (1..21).map { "Symptom $it" }
            createTestUserPreferences(customSymptomTypes = tooManySymptoms)
            fail("Should throw exception for > 20 custom symptom types")
        } catch (e: IllegalArgumentException) {
            assertTrue("Should contain custom symptom types validation message",
                e.message?.contains("Maximum 20 custom symptom types allowed") == true)
        }
    }

    @Test
    fun entity_createDefault_shouldCreateValidDefaultPreferences() {
        // Act
        val defaultPreferences = UserPreferences.createDefault()

        // Assert
        assertNotNull("Default preferences should be created", defaultPreferences)
        assertEquals("Should have ID 1 for single row table", 1L, defaultPreferences.id)
        assertEquals("Should have default correlation time window", 3, defaultPreferences.correlationTimeWindowHours)
        assertEquals("Should have default measurement unit", MeasurementUnit.METRIC, defaultPreferences.measurementUnit)
        assertTrue("Should have notifications enabled by default", defaultPreferences.notificationEnabled)
        assertNull("Should have no default notification time", defaultPreferences.notificationTime)
        assertEquals("Should have default data retention", 12, defaultPreferences.dataRetentionMonths)
        assertEquals("Should have default export format", ExportFormat.JSON, defaultPreferences.exportFormat)
        assertEquals("Should have empty custom symptom types", emptyList<String>(), defaultPreferences.customSymptomTypes)
        assertFalse("Should have trigger alerts disabled by default", defaultPreferences.triggerAlertEnabled)
        assertEquals("Should have default report template", ReportTemplate.BASIC, defaultPreferences.reportTemplate)
        assertTrue("Should have privacy mode enabled by default", defaultPreferences.privacyMode)
    }

    @Test
    fun entity_updateMethod_shouldUpdateFieldsCorrectly() {
        // Arrange
        val preferences = createTestUserPreferences()
        val newCorrelationTimeWindow = 8
        val newMeasurementUnit = MeasurementUnit.IMPERIAL
        val newNotificationEnabled = false
        val newNotificationTime = LocalTime.of(10, 0)
        val newDataRetention = 18
        val newExportFormat = ExportFormat.CSV
        val newCustomSymptoms = listOf("Custom Nausea", "Custom Bloating")
        val newTriggerAlert = true
        val newReportTemplate = ReportTemplate.MEDICAL
        val newPrivacyMode = false

        // Act
        val updatedPreferences = preferences.update(
            correlationTimeWindowHours = newCorrelationTimeWindow,
            measurementUnit = newMeasurementUnit,
            notificationEnabled = newNotificationEnabled,
            notificationTime = newNotificationTime,
            dataRetentionMonths = newDataRetention,
            exportFormat = newExportFormat,
            customSymptomTypes = newCustomSymptoms,
            triggerAlertEnabled = newTriggerAlert,
            reportTemplate = newReportTemplate,
            privacyMode = newPrivacyMode
        )

        // Assert
        assertEquals("Correlation time window should be updated", newCorrelationTimeWindow, updatedPreferences.correlationTimeWindowHours)
        assertEquals("Measurement unit should be updated", newMeasurementUnit, updatedPreferences.measurementUnit)
        assertEquals("Notification enabled should be updated", newNotificationEnabled, updatedPreferences.notificationEnabled)
        assertEquals("Notification time should be updated", newNotificationTime, updatedPreferences.notificationTime)
        assertEquals("Data retention should be updated", newDataRetention, updatedPreferences.dataRetentionMonths)
        assertEquals("Export format should be updated", newExportFormat, updatedPreferences.exportFormat)
        assertEquals("Custom symptom types should be updated", newCustomSymptoms, updatedPreferences.customSymptomTypes)
        assertEquals("Trigger alert should be updated", newTriggerAlert, updatedPreferences.triggerAlertEnabled)
        assertEquals("Report template should be updated", newReportTemplate, updatedPreferences.reportTemplate)
        assertEquals("Privacy mode should be updated", newPrivacyMode, updatedPreferences.privacyMode)
        assertTrue("Modified time should be updated", updatedPreferences.modifiedAt.isAfter(preferences.modifiedAt))
        assertEquals("ID should remain unchanged", preferences.id, updatedPreferences.id)
        assertEquals("Created time should remain unchanged", preferences.createdAt, updatedPreferences.createdAt)
    }

    @Test
    fun entity_updateMethod_shouldPreserveUnchangedFields() {
        // Arrange
        val preferences = createTestUserPreferences(
            correlationTimeWindowHours = 5,
            measurementUnit = MeasurementUnit.IMPERIAL,
            notificationEnabled = false
        )

        // Act - Only update one field
        val updatedPreferences = preferences.update(
            correlationTimeWindowHours = 7
        )

        // Assert
        assertEquals("Correlation time window should be updated", 7, updatedPreferences.correlationTimeWindowHours)
        assertEquals("Measurement unit should remain unchanged", MeasurementUnit.IMPERIAL, updatedPreferences.measurementUnit)
        assertFalse("Notification enabled should remain unchanged", updatedPreferences.notificationEnabled)
        assertEquals("All other fields should remain the same", preferences.dataRetentionMonths, updatedPreferences.dataRetentionMonths)
        assertEquals("Export format should remain the same", preferences.exportFormat, updatedPreferences.exportFormat)
    }

    @Test
    fun singleRowTable_shouldMaintainOnlyOneRecord() = runTest {
        // Arrange - Insert multiple preferences with same ID (should replace)
        val prefs1 = createTestUserPreferences(correlationTimeWindowHours = 2)
        val prefs2 = createTestUserPreferences(correlationTimeWindowHours = 4)
        val prefs3 = createTestUserPreferences(correlationTimeWindowHours = 6)

        // Act
        userPreferencesDao.insert(prefs1)
        userPreferencesDao.insert(prefs2)
        userPreferencesDao.insert(prefs3)

        // Assert
        val allPreferences = userPreferencesDao.getAll()
        assertEquals("Should maintain only 1 record in single-row table", 1, allPreferences.size)
        assertEquals("Should have the latest preferences", 6, allPreferences.first().correlationTimeWindowHours)

        val retrieved = userPreferencesDao.get()
        assertNotNull("Should be able to get the preferences", retrieved)
        assertEquals("Should have the latest correlation time window", 6, retrieved!!.correlationTimeWindowHours)
    }

    private fun createTestUserPreferences(
        correlationTimeWindowHours: Int = 3,
        measurementUnit: MeasurementUnit = MeasurementUnit.METRIC,
        notificationEnabled: Boolean = true,
        notificationTime: LocalTime? = null,
        dataRetentionMonths: Int = 12,
        exportFormat: ExportFormat = ExportFormat.JSON,
        customSymptomTypes: List<String> = emptyList(),
        triggerAlertEnabled: Boolean = false,
        reportTemplate: ReportTemplate = ReportTemplate.BASIC,
        privacyMode: Boolean = true,
        createdAt: Instant = Instant.now(),
        modifiedAt: Instant = Instant.now()
    ) = UserPreferences(
        id = 1L, // Single row table always uses ID 1
        correlationTimeWindowHours = correlationTimeWindowHours,
        measurementUnit = measurementUnit,
        notificationEnabled = notificationEnabled,
        notificationTime = notificationTime,
        dataRetentionMonths = dataRetentionMonths,
        exportFormat = exportFormat,
        customSymptomTypes = customSymptomTypes,
        triggerAlertEnabled = triggerAlertEnabled,
        reportTemplate = reportTemplate,
        privacyMode = privacyMode,
        createdAt = createdAt,
        modifiedAt = modifiedAt
    )
}