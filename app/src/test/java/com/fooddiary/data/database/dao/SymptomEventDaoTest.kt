package com.fooddiary.data.database.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fooddiary.data.database.FoodDiaryDatabase
import com.fooddiary.data.database.entities.SymptomEvent
import com.fooddiary.data.models.SymptomType
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit

@RunWith(AndroidJUnit4::class)
class SymptomEventDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: FoodDiaryDatabase
    private lateinit var symptomEventDao: SymptomEventDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            FoodDiaryDatabase::class.java
        ).allowMainThreadQueries().build()

        symptomEventDao = database.symptomEventDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insert_shouldInsertSymptomEvent() = runTest {
        // Arrange
        val symptomEvent = createTestSymptomEvent()

        // Act
        val insertedId = symptomEventDao.insert(symptomEvent)

        // Assert
        assertTrue("Inserted ID should be greater than 0", insertedId > 0)

        val retrieved = symptomEventDao.getById(insertedId)
        assertNotNull("Retrieved symptom should not be null", retrieved)
        assertEquals("Symptom type should match", symptomEvent.symptomType, retrieved!!.symptomType)
        assertEquals("Severity should match", symptomEvent.severity, retrieved.severity)
        assertEquals("Duration should match", symptomEvent.duration, retrieved.duration)
    }

    @Test
    fun insertAll_shouldInsertMultipleSymptomEvents() = runTest {
        // Arrange
        val symptoms = listOf(
            createTestSymptomEvent(symptomType = SymptomType.ABDOMINAL_PAIN, severity = 5),
            createTestSymptomEvent(symptomType = SymptomType.BLOATING, severity = 7),
            createTestSymptomEvent(symptomType = SymptomType.DIARRHEA, severity = 8)
        )

        // Act
        val insertedIds = symptomEventDao.insertAll(symptoms)

        // Assert
        assertEquals("Should insert all 3 symptoms", 3, insertedIds.size)
        insertedIds.forEach { id ->
            assertTrue("Each inserted ID should be greater than 0", id > 0)
        }

        val allSymptoms = symptomEventDao.getAll()
        assertEquals("Should retrieve all 3 symptoms", 3, allSymptoms.size)
    }

    @Test
    fun update_shouldUpdateSymptomEvent() = runTest {
        // Arrange
        val originalSymptom = createTestSymptomEvent()
        val insertedId = symptomEventDao.insert(originalSymptom)
        val updatedSymptom = originalSymptom.copy(
            id = insertedId,
            severity = 8,
            duration = Duration.ofMinutes(45),
            notes = "Updated notes"
        )

        // Act
        val updateCount = symptomEventDao.update(updatedSymptom)

        // Assert
        assertEquals("Should update 1 symptom", 1, updateCount)

        val retrieved = symptomEventDao.getById(insertedId)
        assertNotNull("Updated symptom should exist", retrieved)
        assertEquals("Severity should be updated", 8, retrieved!!.severity)
        assertEquals("Duration should be updated", Duration.ofMinutes(45), retrieved.duration)
        assertEquals("Notes should be updated", "Updated notes", retrieved.notes)
    }

    @Test
    fun getById_shouldReturnCorrectSymptomEvent() = runTest {
        // Arrange
        val symptom1 = createTestSymptomEvent(symptomType = SymptomType.NAUSEA)
        val symptom2 = createTestSymptomEvent(symptomType = SymptomType.HEARTBURN)

        val id1 = symptomEventDao.insert(symptom1)
        val id2 = symptomEventDao.insert(symptom2)

        // Act
        val retrieved1 = symptomEventDao.getById(id1)
        val retrieved2 = symptomEventDao.getById(id2)
        val nonExistent = symptomEventDao.getById(999L)

        // Assert
        assertNotNull("First symptom should exist", retrieved1)
        assertNotNull("Second symptom should exist", retrieved2)
        assertNull("Non-existent symptom should be null", nonExistent)

        assertEquals("First symptom type should match", SymptomType.NAUSEA, retrieved1!!.symptomType)
        assertEquals("Second symptom type should match", SymptomType.HEARTBURN, retrieved2!!.symptomType)
    }

    @Test
    fun getAll_shouldReturnAllNonDeletedSymptomEvents() = runTest {
        // Arrange
        val symptom1 = createTestSymptomEvent(symptomType = SymptomType.CONSTIPATION)
        val symptom2 = createTestSymptomEvent(symptomType = SymptomType.GAS)
        val deletedSymptom = createTestSymptomEvent(symptomType = SymptomType.CRAMPING).copy(isDeleted = true)

        symptomEventDao.insert(symptom1)
        symptomEventDao.insert(symptom2)
        symptomEventDao.insert(deletedSymptom)

        // Act
        val allSymptoms = symptomEventDao.getAll()

        // Assert
        assertEquals("Should return 2 non-deleted symptoms", 2, allSymptoms.size)
        val symptomTypes = allSymptoms.map { it.symptomType }
        assertTrue("Should contain constipation", symptomTypes.contains(SymptomType.CONSTIPATION))
        assertTrue("Should contain gas", symptomTypes.contains(SymptomType.GAS))
        assertFalse("Should not contain deleted cramping", symptomTypes.contains(SymptomType.CRAMPING))
    }

    @Test
    fun getBySymptomType_shouldReturnSymptomsOfSpecificType() = runTest {
        // Arrange
        val abdominalPain1 = createTestSymptomEvent(symptomType = SymptomType.ABDOMINAL_PAIN, severity = 4)
        val abdominalPain2 = createTestSymptomEvent(symptomType = SymptomType.ABDOMINAL_PAIN, severity = 7)
        val bloating = createTestSymptomEvent(symptomType = SymptomType.BLOATING, severity = 5)

        symptomEventDao.insert(abdominalPain1)
        symptomEventDao.insert(abdominalPain2)
        symptomEventDao.insert(bloating)

        // Act
        val painResults = symptomEventDao.getBySymptomType(SymptomType.ABDOMINAL_PAIN)
        val bloatingResults = symptomEventDao.getBySymptomType(SymptomType.BLOATING)
        val nauseavResults = symptomEventDao.getBySymptomType(SymptomType.NAUSEA)

        // Assert
        assertEquals("Should find 2 abdominal pain symptoms", 2, painResults.size)
        assertEquals("Should find 1 bloating symptom", 1, bloatingResults.size)
        assertEquals("Should find 0 nausea symptoms", 0, nauseavResults.size)

        painResults.forEach { symptom ->
            assertEquals("All results should be abdominal pain", SymptomType.ABDOMINAL_PAIN, symptom.symptomType)
        }
    }

    @Test
    fun getInTimeRange_shouldReturnSymptomsInSpecifiedTimeRange() = runTest {
        // Arrange
        val baseTime = Instant.now()
        val symptom1 = createTestSymptomEvent(
            timestamp = baseTime.minus(3, ChronoUnit.HOURS),
            symptomType = SymptomType.EARLY_SYMPTOM
        )
        val symptom2 = createTestSymptomEvent(
            timestamp = baseTime.minus(1, ChronoUnit.HOURS),
            symptomType = SymptomType.MIDDLE_SYMPTOM
        )
        val symptom3 = createTestSymptomEvent(
            timestamp = baseTime,
            symptomType = SymptomType.RECENT_SYMPTOM
        )
        val symptom4 = createTestSymptomEvent(
            timestamp = baseTime.plus(1, ChronoUnit.HOURS),
            symptomType = SymptomType.FUTURE_SYMPTOM
        )

        symptomEventDao.insert(symptom1)
        symptomEventDao.insert(symptom2)
        symptomEventDao.insert(symptom3)
        symptomEventDao.insert(symptom4)

        // Act
        val results = symptomEventDao.getInTimeRange(
            startTime = baseTime.minus(2, ChronoUnit.HOURS),
            endTime = baseTime.plus(30, ChronoUnit.MINUTES)
        )

        // Assert
        assertEquals("Should find 2 symptoms in time range", 2, results.size)

        val symptomTypes = results.map { it.symptomType }
        assertTrue("Should contain middle symptom", symptomTypes.contains(SymptomType.MIDDLE_SYMPTOM))
        assertTrue("Should contain recent symptom", symptomTypes.contains(SymptomType.RECENT_SYMPTOM))
        assertFalse("Should not contain early symptom", symptomTypes.contains(SymptomType.EARLY_SYMPTOM))
        assertFalse("Should not contain future symptom", symptomTypes.contains(SymptomType.FUTURE_SYMPTOM))
    }

    @Test
    fun getBySeverityRange_shouldReturnSymptomsWithinSeverityRange() = runTest {
        // Arrange
        val mildSymptom = createTestSymptomEvent(severity = 2)
        val moderateSymptom = createTestSymptomEvent(severity = 5)
        val severeSymptom = createTestSymptomEvent(severity = 9)
        val veryMildSymptom = createTestSymptomEvent(severity = 1)

        symptomEventDao.insert(mildSymptom)
        symptomEventDao.insert(moderateSymptom)
        symptomEventDao.insert(severeSymptom)
        symptomEventDao.insert(veryMildSymptom)

        // Act
        val moderateToSevere = symptomEventDao.getBySeverityRange(5, 8)
        val mildRange = symptomEventDao.getBySeverityRange(1, 3)

        // Assert
        assertEquals("Should find 1 symptom in moderate-severe range", 1, moderateToSevere.size)
        assertEquals("Moderate symptom should have severity 5", 5, moderateToSevere.first().severity)

        assertEquals("Should find 2 symptoms in mild range", 2, mildRange.size)
        mildRange.forEach { symptom ->
            assertTrue("All results should be in mild range", symptom.severity in 1..3)
        }
    }

    @Test
    fun getRecentSymptoms_shouldReturnMostRecentSymptoms() = runTest {
        // Arrange
        val baseTime = Instant.now()
        val symptoms = (0..4).map { i ->
            createTestSymptomEvent(
                timestamp = baseTime.minus(i.toLong(), ChronoUnit.HOURS),
                symptomType = SymptomType.ABDOMINAL_PAIN,
                severity = i + 1
            )
        }
        symptoms.forEach { symptomEventDao.insert(it) }

        // Act
        val recentSymptoms = symptomEventDao.getRecentSymptoms(3)

        // Assert
        assertEquals("Should return 3 most recent symptoms", 3, recentSymptoms.size)

        // Verify symptoms are in descending order (most recent first)
        for (i in 0 until recentSymptoms.size - 1) {
            assertTrue(
                "Symptoms should be in descending order by timestamp",
                recentSymptoms[i].timestamp.isAfter(recentSymptoms[i + 1].timestamp)
            )
        }
    }

    @Test
    fun getSymptomsByDuration_shouldReturnSymptomsWithSpecificDuration() = runTest {
        // Arrange
        val shortSymptom = createTestSymptomEvent(duration = Duration.ofMinutes(15))
        val mediumSymptom = createTestSymptomEvent(duration = Duration.ofMinutes(45))
        val longSymptom = createTestSymptomEvent(duration = Duration.ofHours(2))
        val unknownDurationSymptom = createTestSymptomEvent(duration = null)

        symptomEventDao.insert(shortSymptom)
        symptomEventDao.insert(mediumSymptom)
        symptomEventDao.insert(longSymptom)
        symptomEventDao.insert(unknownDurationSymptom)

        // Act
        val shortDuration = symptomEventDao.getSymptomsByDuration(
            minDuration = Duration.ofMinutes(10),
            maxDuration = Duration.ofMinutes(20)
        )
        val longDuration = symptomEventDao.getSymptomsByDuration(
            minDuration = Duration.ofHours(1),
            maxDuration = Duration.ofHours(3)
        )

        // Assert
        assertEquals("Should find 1 short duration symptom", 1, shortDuration.size)
        assertEquals("Short symptom duration should be 15 minutes", Duration.ofMinutes(15), shortDuration.first().duration)

        assertEquals("Should find 1 long duration symptom", 1, longDuration.size)
        assertEquals("Long symptom duration should be 2 hours", Duration.ofHours(2), longDuration.first().duration)
    }

    @Test
    fun softDelete_shouldMarkSymptomAsDeleted() = runTest {
        // Arrange
        val symptom = createTestSymptomEvent()
        val insertedId = symptomEventDao.insert(symptom)

        // Act
        val deleteCount = symptomEventDao.softDelete(insertedId)

        // Assert
        assertEquals("Should soft delete 1 symptom", 1, deleteCount)

        val retrieved = symptomEventDao.getById(insertedId)
        assertNotNull("Symptom should still exist in database", retrieved)
        assertTrue("Symptom should be marked as deleted", retrieved!!.isDeleted)

        val allSymptoms = symptomEventDao.getAll()
        assertEquals("Soft deleted symptom should not appear in getAll()", 0, allSymptoms.size)
    }

    @Test
    fun delete_shouldPermanentlyRemoveSymptom() = runTest {
        // Arrange
        val symptom = createTestSymptomEvent()
        val insertedId = symptomEventDao.insert(symptom)

        // Act
        val deleteCount = symptomEventDao.delete(symptom.copy(id = insertedId))

        // Assert
        assertEquals("Should delete 1 symptom", 1, deleteCount)

        val retrieved = symptomEventDao.getById(insertedId)
        assertNull("Symptom should not exist after deletion", retrieved)
    }

    @Test
    fun getCorrelationData_shouldReturnSymptomsForCorrelationAnalysis() = runTest {
        // Arrange
        val baseTime = Instant.now()
        val symptoms = listOf(
            createTestSymptomEvent(
                timestamp = baseTime.minus(1, ChronoUnit.DAYS),
                symptomType = SymptomType.ABDOMINAL_PAIN
            ),
            createTestSymptomEvent(
                timestamp = baseTime.minus(3, ChronoUnit.HOURS),
                symptomType = SymptomType.BLOATING
            ),
            createTestSymptomEvent(
                timestamp = baseTime.minus(1, ChronoUnit.HOURS),
                symptomType = SymptomType.DIARRHEA
            )
        )
        symptoms.forEach { symptomEventDao.insert(it) }

        // Act
        val correlationData = symptomEventDao.getCorrelationData(
            sinceTime = baseTime.minus(8, ChronoUnit.HOURS)
        )

        // Assert
        assertEquals("Should return 2 symptoms for correlation", 2, correlationData.size)

        val symptomTypes = correlationData.map { it.symptomType }
        assertTrue("Should include bloating", symptomTypes.contains(SymptomType.BLOATING))
        assertTrue("Should include diarrhea", symptomTypes.contains(SymptomType.DIARRHEA))
        assertFalse("Should not include old abdominal pain", symptomTypes.contains(SymptomType.ABDOMINAL_PAIN))
    }

    @Test
    fun entity_validation_shouldEnforceSeverityConstraints() {
        // Test entity validation rules

        // Valid severity values should work
        val validSymptom = createTestSymptomEvent(severity = 5)
        assertNotNull("Valid symptom should be created", validSymptom)

        // Test boundary values
        val minSeverity = createTestSymptomEvent(severity = 1)
        val maxSeverity = createTestSymptomEvent(severity = 10)
        assertNotNull("Minimum severity should be valid", minSeverity)
        assertNotNull("Maximum severity should be valid", maxSeverity)

        // Invalid severity should throw exception
        try {
            createTestSymptomEvent(severity = 0)
            fail("Should throw exception for severity below 1")
        } catch (e: IllegalArgumentException) {
            assertTrue("Should contain severity validation message",
                e.message?.contains("Severity must be between 1 and 10") == true)
        }

        try {
            createTestSymptomEvent(severity = 11)
            fail("Should throw exception for severity above 10")
        } catch (e: IllegalArgumentException) {
            assertTrue("Should contain severity validation message",
                e.message?.contains("Severity must be between 1 and 10") == true)
        }
    }

    @Test
    fun entity_validation_shouldEnforceDurationConstraints() {
        // Valid duration should work
        val validSymptom = createTestSymptomEvent(duration = Duration.ofMinutes(30))
        assertNotNull("Valid symptom with duration should be created", validSymptom)

        // Null duration should be allowed
        val noDurationSymptom = createTestSymptomEvent(duration = null)
        assertNotNull("Symptom without duration should be valid", noDurationSymptom)

        // Zero duration should be allowed
        val zeroDurationSymptom = createTestSymptomEvent(duration = Duration.ZERO)
        assertNotNull("Symptom with zero duration should be valid", zeroDurationSymptom)

        // Negative duration should throw exception
        try {
            createTestSymptomEvent(duration = Duration.ofMinutes(-10))
            fail("Should throw exception for negative duration")
        } catch (e: IllegalArgumentException) {
            assertTrue("Should contain duration validation message",
                e.message?.contains("Duration must be non-negative") == true)
        }
    }

    @Test
    fun entity_softDeleteMethod_shouldUpdateCorrectly() {
        // Arrange
        val symptom = createTestSymptomEvent()

        // Act
        val softDeletedSymptom = symptom.softDelete()

        // Assert
        assertTrue("Symptom should be marked as deleted", softDeletedSymptom.isDeleted)
        assertTrue("Modified time should be updated", softDeletedSymptom.modifiedAt.isAfter(symptom.modifiedAt))
        assertEquals("All other fields should remain the same", symptom.symptomType, softDeletedSymptom.symptomType)
        assertEquals("Severity should remain the same", symptom.severity, softDeletedSymptom.severity)
        assertEquals("ID should remain the same", symptom.id, softDeletedSymptom.id)
    }

    @Test
    fun entity_updateMethod_shouldUpdateFieldsCorrectly() {
        // Arrange
        val symptom = createTestSymptomEvent()
        val newSymptomType = SymptomType.NAUSEA
        val newSeverity = 8
        val newDuration = Duration.ofHours(2)
        val newNotes = "Updated notes"

        // Act
        val updatedSymptom = symptom.update(
            symptomType = newSymptomType,
            severity = newSeverity,
            duration = newDuration,
            notes = newNotes
        )

        // Assert
        assertEquals("Symptom type should be updated", newSymptomType, updatedSymptom.symptomType)
        assertEquals("Severity should be updated", newSeverity, updatedSymptom.severity)
        assertEquals("Duration should be updated", newDuration, updatedSymptom.duration)
        assertEquals("Notes should be updated", newNotes, updatedSymptom.notes)
        assertTrue("Modified time should be updated", updatedSymptom.modifiedAt.isAfter(symptom.modifiedAt))
        assertEquals("ID should remain unchanged", symptom.id, updatedSymptom.id)
        assertEquals("Timestamp should remain unchanged", symptom.timestamp, updatedSymptom.timestamp)
    }

    private fun createTestSymptomEvent(
        symptomType: SymptomType = SymptomType.ABDOMINAL_PAIN,
        severity: Int = 5,
        timestamp: Instant = Instant.now(),
        duration: Duration? = Duration.ofMinutes(30),
        notes: String? = "Test symptom notes"
    ) = SymptomEvent.create(
        symptomType = symptomType,
        severity = severity,
        timestamp = timestamp,
        duration = duration,
        notes = notes
    )
}

// Extension for testing - these would need to be added to the actual SymptomType enum
private val SymptomType.Companion.EARLY_SYMPTOM: SymptomType
    get() = SymptomType.ABDOMINAL_PAIN

private val SymptomType.Companion.MIDDLE_SYMPTOM: SymptomType
    get() = SymptomType.BLOATING

private val SymptomType.Companion.RECENT_SYMPTOM: SymptomType
    get() = SymptomType.GAS

private val SymptomType.Companion.FUTURE_SYMPTOM: SymptomType
    get() = SymptomType.NAUSEA