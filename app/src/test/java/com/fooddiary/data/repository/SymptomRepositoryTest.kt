package com.fooddiary.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fooddiary.data.database.dao.SymptomEventDao
import com.fooddiary.data.database.dao.FoodEntryDao
import com.fooddiary.data.database.dao.CorrelationPatternDao
import com.fooddiary.data.database.dao.TriggerPatternDao
import com.fooddiary.data.database.entities.SymptomEvent
import com.fooddiary.data.database.entities.FoodEntry
import com.fooddiary.data.database.entities.CorrelationPattern
import com.fooddiary.data.database.entities.TriggerPattern
import com.fooddiary.data.models.*
import com.fooddiary.data.analysis.CorrelationAnalyzer
import io.mockk.*
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.temporal.ChronoUnit

/**
 * TDD Tests for SymptomRepository - THESE WILL FAIL INITIALLY
 *
 * Test coverage:
 * - Symptom event CRUD operations
 * - Correlation analysis with food entries
 * - Trigger pattern detection
 * - Severity trend tracking
 * - Symptom clustering and analysis
 * - Time-based symptom analysis
 */
class SymptomRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var symptomEventDao: SymptomEventDao

    @MockK
    private lateinit var foodEntryDao: FoodEntryDao

    @MockK
    private lateinit var correlationPatternDao: CorrelationPatternDao

    @MockK
    private lateinit var triggerPatternDao: TriggerPatternDao

    @MockK
    private lateinit var correlationAnalyzer: CorrelationAnalyzer

    private lateinit var repository: SymptomRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        // This will fail initially since SymptomRepository doesn't exist yet
        repository = SymptomRepository(
            symptomEventDao = symptomEventDao,
            foodEntryDao = foodEntryDao,
            correlationPatternDao = correlationPatternDao,
            triggerPatternDao = triggerPatternDao,
            correlationAnalyzer = correlationAnalyzer
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `logSymptom should validate severity and save event`() = runTest {
        // Arrange
        val symptomEvent = createTestSymptomEvent(severity = 7)
        val insertedId = 123L

        coEvery { symptomEventDao.insert(any()) } returns insertedId

        // Act
        val result = repository.logSymptom(
            symptomType = SymptomType.ABDOMINAL_PAIN,
            severity = 7,
            duration = Duration.ofHours(2),
            notes = "Sharp pain after lunch"
        )

        // Assert
        assertTrue("Should successfully log symptom", result.isSuccess)
        assertEquals("Should return inserted ID", insertedId, result.getOrNull())

        coVerify { symptomEventDao.insert(match { event ->
            event.symptomType == SymptomType.ABDOMINAL_PAIN &&
            event.severity == 7 &&
            event.duration == Duration.ofHours(2) &&
            event.notes == "Sharp pain after lunch"
        }) }
    }

    @Test
    fun `logSymptom should reject invalid severity levels`() = runTest {
        // Act & Assert - severity too low
        val lowResult = repository.logSymptom(
            symptomType = SymptomType.BLOATING,
            severity = 0,
            duration = null,
            notes = null
        )
        assertTrue("Should fail for severity 0", lowResult.isFailure)
        assertTrue("Should contain validation error",
            lowResult.exceptionOrNull()?.message?.contains("Severity must be between 1 and 10") == true)

        // Act & Assert - severity too high
        val highResult = repository.logSymptom(
            symptomType = SymptomType.BLOATING,
            severity = 11,
            duration = null,
            notes = null
        )
        assertTrue("Should fail for severity 11", highResult.isFailure)

        coVerify(exactly = 0) { symptomEventDao.insert(any()) }
    }

    @Test
    fun `updateSymptom should modify existing event with validation`() = runTest {
        // Arrange
        val originalEvent = createTestSymptomEvent(id = 1L, severity = 5)
        val updatedSeverity = 8
        val updatedNotes = "Pain has worsened"

        coEvery { symptomEventDao.getById(1L) } returns originalEvent
        coEvery { symptomEventDao.update(any()) } returns 1

        // Act
        val result = repository.updateSymptom(
            eventId = "1",
            severity = updatedSeverity,
            notes = updatedNotes
        )

        // Assert
        assertTrue("Should successfully update symptom", result.isSuccess)

        coVerify { symptomEventDao.update(match { event ->
            event.id == 1L &&
            event.severity == 8 &&
            event.notes == "Pain has worsened" &&
            event.modifiedAt.isAfter(originalEvent.modifiedAt)
        }) }
    }

    @Test
    fun `updateSymptom should fail for non-existent event`() = runTest {
        // Arrange
        coEvery { symptomEventDao.getById(999L) } returns null

        // Act
        val result = repository.updateSymptom(
            eventId = "999",
            severity = 5
        )

        // Assert
        assertTrue("Should fail for non-existent event", result.isFailure)
        assertEquals("Should return not found error",
            "Symptom event not found", result.exceptionOrNull()?.message)

        coVerify { symptomEventDao.getById(999L) }
        coVerify(exactly = 0) { symptomEventDao.update(any()) }
    }

    @Test
    fun `deleteSymptom should perform soft delete`() = runTest {
        // Arrange
        val eventId = "event-123"
        val existingEvent = createTestSymptomEvent(id = 123L)

        coEvery { symptomEventDao.getById(123L) } returns existingEvent
        coEvery { symptomEventDao.update(any()) } returns 1

        // Act
        val result = repository.deleteSymptom(eventId)

        // Assert
        assertTrue("Should successfully delete symptom", result.isSuccess)

        coVerify { symptomEventDao.update(match { event ->
            event.isDeleted && event.modifiedAt.isAfter(existingEvent.modifiedAt)
        }) }
    }

    @Test
    fun `findCorrelations should analyze symptoms against recent food entries`() = runTest {
        // Arrange
        val symptomEvent = createTestSymptomEvent(
            timestamp = Instant.now(),
            symptomType = SymptomType.ABDOMINAL_PAIN
        )
        val potentialTriggerFood = createTestFoodEntry(
            timestamp = Instant.now().minus(2, ChronoUnit.HOURS),
            foods = listOf("Dairy", "Gluten")
        )
        val correlationAnalysis = CorrelationAnalysisResult(
            correlations = listOf(
                FoodSymptomCorrelation(
                    foodId = potentialTriggerFood.id,
                    symptomId = symptomEvent.id,
                    correlationStrength = 0.82f,
                    timeOffsetHours = 2,
                    occurrenceCount = 3,
                    confidenceLevel = ConfidenceLevel.HIGH
                )
            ),
            analysisTimeWindow = Duration.ofHours(48)
        )

        coEvery { symptomEventDao.getById(any()) } returns symptomEvent
        coEvery { foodEntryDao.getEntriesInTimeWindow(any(), any()) } returns listOf(potentialTriggerFood)
        coEvery { correlationAnalyzer.analyzeCorrelations(any(), any()) } returns correlationAnalysis

        // Act
        val correlations = repository.findCorrelations(
            symptomEventId = "123",
            timeWindowHours = 48
        ).first()

        // Assert
        assertEquals("Should find correlations", 1, correlations.size)

        val correlation = correlations.first()
        assertEquals("Should have correct correlation strength", 0.82f, correlation.correlationStrength, 0.01f)
        assertEquals("Should have correct time offset", 2, correlation.timeOffsetHours)
        assertEquals("Should have high confidence", ConfidenceLevel.HIGH, correlation.confidenceLevel)

        coVerify { correlationAnalyzer.analyzeCorrelations(symptomEvent, listOf(potentialTriggerFood)) }
    }

    @Test
    fun `getSeverityTrends should calculate trends over time period`() = runTest {
        // Arrange
        val baseTime = Instant.now()
        val symptomEvents = listOf(
            createTestSymptomEvent(
                timestamp = baseTime.minus(7, ChronoUnit.DAYS),
                severity = 3,
                symptomType = SymptomType.BLOATING
            ),
            createTestSymptomEvent(
                timestamp = baseTime.minus(5, ChronoUnit.DAYS),
                severity = 6,
                symptomType = SymptomType.BLOATING
            ),
            createTestSymptomEvent(
                timestamp = baseTime.minus(2, ChronoUnit.DAYS),
                severity = 8,
                symptomType = SymptomType.BLOATING
            ),
            createTestSymptomEvent(
                timestamp = baseTime,
                severity = 4,
                symptomType = SymptomType.BLOATING
            )
        )

        coEvery { symptomEventDao.getEventsByTypeInRange(
            SymptomType.BLOATING, any(), any()
        ) } returns symptomEvents

        // Act
        val trends = repository.getSeverityTrends(
            symptomType = SymptomType.BLOATING,
            days = 7
        ).first()

        // Assert
        assertNotNull("Should provide trend analysis", trends)
        assertEquals("Should calculate average severity", 5.25f, trends.averageSeverity, 0.1f)
        assertEquals("Should identify trend direction", TrendDirection.DECREASING, trends.direction)
        assertTrue("Should detect peak severity", trends.peakSeverity == 8)
        assertEquals("Should count total events", 4, trends.totalEvents)

        coVerify { symptomEventDao.getEventsByTypeInRange(SymptomType.BLOATING, any(), any()) }
    }

    @Test
    fun `getSymptomPatterns should identify recurring patterns`() = runTest {
        // Arrange
        val dailySymptoms = (0..6).map { dayOffset ->
            createTestSymptomEvent(
                timestamp = Instant.now().minus(dayOffset.toLong(), ChronoUnit.DAYS)
                    .plus(14, ChronoUnit.HOURS), // Always around 2 PM
                symptomType = SymptomType.ABDOMINAL_PAIN,
                severity = if (dayOffset % 2 == 0) 7 else 3 // Every other day pattern
            )
        }

        coEvery { symptomEventDao.getEventsByTypeInRange(any(), any(), any()) } returns dailySymptoms

        // Act
        val patterns = repository.getSymptomPatterns(
            symptomType = SymptomType.ABDOMINAL_PAIN,
            days = 7
        ).first()

        // Assert
        assertNotNull("Should identify patterns", patterns)
        assertTrue("Should detect time-based pattern", patterns.hasTimePattern)
        assertEquals("Should identify peak hour", 14, patterns.peakHour)
        assertTrue("Should detect severity pattern", patterns.hasSeverityPattern)
        assertTrue("Should have pattern confidence > 0.5", patterns.patternConfidence > 0.5f)
    }

    @Test
    fun `trackSymptomDuration should handle ongoing and resolved symptoms`() = runTest {
        // Arrange
        val ongoingSymptom = createTestSymptomEvent(
            timestamp = Instant.now().minus(2, ChronoUnit.HOURS),
            duration = null // Still ongoing
        )
        val resolvedSymptom = createTestSymptomEvent(
            timestamp = Instant.now().minus(4, ChronoUnit.HOURS),
            duration = Duration.ofHours(1) // Resolved after 1 hour
        )

        coEvery { symptomEventDao.getActiveSymptoms() } returns flowOf(listOf(ongoingSymptom))
        coEvery { symptomEventDao.update(any()) } returns 1

        // Act
        val result = repository.trackSymptomDuration(
            eventId = ongoingSymptom.id.toString(),
            endTime = Instant.now()
        )

        // Assert
        assertTrue("Should successfully track duration", result.isSuccess)

        coVerify { symptomEventDao.update(match { event ->
            event.id == ongoingSymptom.id &&
            event.duration != null &&
            event.duration!!.toHours() == 2L // Should calculate 2 hour duration
        }) }
    }

    @Test
    fun `analyzeSymptomClusters should group related symptoms`() = runTest {
        // Arrange
        val relatedSymptoms = listOf(
            createTestSymptomEvent(
                timestamp = Instant.now(),
                symptomType = SymptomType.ABDOMINAL_PAIN
            ),
            createTestSymptomEvent(
                timestamp = Instant.now().plus(15, ChronoUnit.MINUTES),
                symptomType = SymptomType.BLOATING
            ),
            createTestSymptomEvent(
                timestamp = Instant.now().plus(30, ChronoUnit.MINUTES),
                symptomType = SymptomType.NAUSEA
            ),
            createTestSymptomEvent(
                timestamp = Instant.now().plus(4, ChronoUnit.HOURS), // Separate cluster
                symptomType = SymptomType.HEARTBURN
            )
        )

        coEvery { symptomEventDao.getEventsInTimeRange(any(), any()) } returns relatedSymptoms

        // Act
        val clusters = repository.analyzeSymptomClusters(
            timeWindow = Duration.ofHours(1),
            minClusterSize = 2
        ).first()

        // Assert
        assertEquals("Should identify clusters", 1, clusters.size)

        val cluster = clusters.first()
        assertEquals("Should group related symptoms", 3, cluster.symptoms.size)
        assertTrue("Should include pain", cluster.symptoms.any { it.symptomType == SymptomType.ABDOMINAL_PAIN })
        assertTrue("Should include bloating", cluster.symptoms.any { it.symptomType == SymptomType.BLOATING })
        assertTrue("Should include nausea", cluster.symptoms.any { it.symptomType == SymptomType.NAUSEA })
        assertFalse("Should not include heartburn", cluster.symptoms.any { it.symptomType == SymptomType.HEARTBURN })
    }

    @Test
    fun `predictSymptomLikelihood should estimate probability based on patterns`() = runTest {
        // Arrange
        val historicalData = listOf(
            createTestSymptomEvent(
                timestamp = Instant.now().minus(7, ChronoUnit.DAYS)
                    .plus(14, ChronoUnit.HOURS), // 2 PM
                symptomType = SymptomType.ABDOMINAL_PAIN
            ),
            createTestSymptomEvent(
                timestamp = Instant.now().minus(14, ChronoUnit.DAYS)
                    .plus(14, ChronoUnit.HOURS), // 2 PM
                symptomType = SymptomType.ABDOMINAL_PAIN
            )
        )

        coEvery { symptomEventDao.getEventsByTypeInRange(any(), any(), any()) } returns historicalData
        coEvery { correlationAnalyzer.calculateSymptomProbability(any(), any(), any()) } returns 0.75f

        val currentTime = Instant.now().plus(14, ChronoUnit.HOURS) // 2 PM today

        // Act
        val likelihood = repository.predictSymptomLikelihood(
            symptomType = SymptomType.ABDOMINAL_PAIN,
            atTime = currentTime,
            basedOnDays = 30
        )

        // Assert
        assertEquals("Should predict high likelihood", 0.75f, likelihood, 0.01f)

        coVerify { correlationAnalyzer.calculateSymptomProbability(
            SymptomType.ABDOMINAL_PAIN,
            currentTime,
            historicalData
        ) }
    }

    @Test
    fun `getSymptomsBySeverity should filter by severity threshold`() = runTest {
        // Arrange
        val allSymptoms = listOf(
            createTestSymptomEvent(severity = 3),
            createTestSymptomEvent(severity = 7),
            createTestSymptomEvent(severity = 9),
            createTestSymptomEvent(severity = 2)
        )

        coEvery { symptomEventDao.getBySeverityAbove(6) } returns flowOf(allSymptoms.filter { it.severity > 6 })

        // Act
        val severeSymptoms = repository.getSymptomsBySeverity(minSeverity = 6).first()

        // Assert
        assertEquals("Should filter by severity", 2, severeSymptoms.size)
        assertTrue("All symptoms should have severity > 6",
            severeSymptoms.all { it.severity > 6 })

        coVerify { symptomEventDao.getBySeverityAbove(6) }
    }

    // Test helper methods
    private fun createTestSymptomEvent(
        id: Long = kotlin.random.Random.nextLong(1, 1000),
        timestamp: Instant = Instant.now(),
        symptomType: SymptomType = SymptomType.ABDOMINAL_PAIN,
        severity: Int = 5,
        duration: Duration? = null,
        notes: String? = "Test symptom"
    ) = SymptomEvent(
        id = id,
        timestamp = timestamp,
        symptomType = symptomType,
        severity = severity,
        duration = duration,
        notes = notes
    )

    private fun createTestFoodEntry(
        id: Long = kotlin.random.Random.nextLong(1, 1000),
        timestamp: Instant = Instant.now(),
        foods: List<String> = listOf("Test Food")
    ) = FoodEntry(
        id = id,
        timestamp = timestamp,
        mealType = MealType.BREAKFAST,
        foods = foods,
        portions = foods.associateWith { "1 serving" },
        notes = null
    )
}

/**
 * Supporting data classes that would be defined in the repository
 */
data class FoodSymptomCorrelation(
    val foodId: Long,
    val symptomId: Long,
    val correlationStrength: Float,
    val timeOffsetHours: Int,
    val occurrenceCount: Int,
    val confidenceLevel: ConfidenceLevel
)

data class CorrelationAnalysisResult(
    val correlations: List<FoodSymptomCorrelation>,
    val analysisTimeWindow: Duration
)

data class SymptomTrend(
    val symptomType: SymptomType,
    val averageSeverity: Float,
    val direction: TrendDirection,
    val peakSeverity: Int,
    val lowestSeverity: Int,
    val totalEvents: Int,
    val timespan: Duration
)

data class SymptomPattern(
    val symptomType: SymptomType,
    val hasTimePattern: Boolean,
    val peakHour: Int?,
    val hasSeverityPattern: Boolean,
    val patternConfidence: Float,
    val recommendedActions: List<String>
)

data class SymptomCluster(
    val id: String,
    val symptoms: List<SymptomEvent>,
    val startTime: Instant,
    val duration: Duration,
    val averageSeverity: Float,
    val dominantType: SymptomType
)

enum class TrendDirection {
    INCREASING, DECREASING, STABLE, FLUCTUATING
}

enum class ConfidenceLevel {
    LOW, MEDIUM, HIGH, VERY_HIGH
}