package com.fooddiary.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import com.fooddiary.data.database.dao.FoodEntryDao
import com.fooddiary.data.database.dao.SymptomEventDao
import com.fooddiary.data.database.dao.CorrelationPatternDao
import com.fooddiary.data.database.entities.FoodEntry
import com.fooddiary.data.database.entities.SymptomEvent
import com.fooddiary.data.database.entities.CorrelationPattern
import com.fooddiary.data.models.*
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
import java.time.LocalDate
import java.time.temporal.ChronoUnit

/**
 * TDD Tests for TimelineRepository - THESE WILL FAIL INITIALLY
 *
 * Test coverage:
 * - Timeline entry aggregation from multiple DAOs
 * - Pagination with proper ordering
 * - Date range filtering
 * - Correlation display and linking
 * - Timeline entry filtering and searching
 * - Data transformation and mapping
 */
class TimelineRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var foodEntryDao: FoodEntryDao

    @MockK
    private lateinit var symptomEventDao: SymptomEventDao

    @MockK
    private lateinit var correlationPatternDao: CorrelationPatternDao

    private lateinit var repository: TimelineRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        // This will fail initially since TimelineRepository doesn't exist yet
        repository = TimelineRepository(
            foodEntryDao = foodEntryDao,
            symptomEventDao = symptomEventDao,
            correlationPatternDao = correlationPatternDao
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getTimelineEntries should aggregate and sort entries from multiple DAOs`() = runTest {
        // Arrange
        val baseTime = Instant.now()
        val foodEntry = createTestFoodEntry(timestamp = baseTime.minus(1, ChronoUnit.HOURS))
        val symptomEvent = createTestSymptomEvent(timestamp = baseTime.minus(30, ChronoUnit.MINUTES))

        coEvery { foodEntryDao.getAll() } returns listOf(foodEntry)
        coEvery { symptomEventDao.getAll() } returns listOf(symptomEvent)
        coEvery { correlationPatternDao.getActiveCorrelations() } returns emptyList()

        // Act
        val timeline = repository.getTimelineEntries().first()

        // Assert
        assertEquals("Should have 2 timeline entries", 2, timeline.size)

        // Verify chronological ordering (most recent first)
        assertTrue("Symptom entry should come before food entry",
            timeline[0].timestamp.isAfter(timeline[1].timestamp))

        // Verify entry types
        assertTrue("First entry should be symptom", timeline[0] is TimelineEntry.SymptomEntry)
        assertTrue("Second entry should be food", timeline[1] is TimelineEntry.FoodEntry)

        // Verify DAO interactions
        coVerify { foodEntryDao.getAll() }
        coVerify { symptomEventDao.getAll() }
        coVerify { correlationPatternDao.getActiveCorrelations() }
    }

    @Test
    fun `getPagedTimeline should return properly paginated results`() = runTest {
        // Arrange
        val entries = (0..49).map { i ->
            createTestFoodEntry(
                timestamp = Instant.now().minus(i.toLong(), ChronoUnit.HOURS),
                foods = listOf("Food $i")
            )
        }

        coEvery { foodEntryDao.getAllPaged() } returns flowOf(entries)
        coEvery { symptomEventDao.getAllPaged() } returns flowOf(emptyList())
        coEvery { correlationPatternDao.getActiveCorrelations() } returns emptyList()

        // Act
        val pagedTimeline: PagingData<TimelineEntry> = repository.getPagedTimeline().first()
        val snapshot = pagedTimeline.asSnapshot()

        // Assert
        assertTrue("Should have paginated entries", snapshot.isNotEmpty())
        assertEquals("Should maintain chronological order",
            "Food 0", (snapshot.first() as TimelineEntry.FoodEntry).foods.first())

        // Verify entries are properly transformed
        snapshot.forEach { entry ->
            assertTrue("All entries should be properly typed",
                entry is TimelineEntry.FoodEntry || entry is TimelineEntry.SymptomEntry)
        }
    }

    @Test
    fun `getEntriesForDate should filter by specific date`() = runTest {
        // Arrange
        val targetDate = LocalDate.now()
        val todayEntry = createTestFoodEntry(timestamp = targetDate.atStartOfDay().toInstant())
        val yesterdayEntry = createTestFoodEntry(
            timestamp = targetDate.minusDays(1).atStartOfDay().toInstant()
        )

        coEvery { foodEntryDao.getEntriesForDate(targetDate) } returns listOf(todayEntry)
        coEvery { symptomEventDao.getEntriesForDate(targetDate) } returns emptyList()
        coEvery { correlationPatternDao.getCorrelationsForDate(targetDate) } returns emptyList()

        // Act
        val entries = repository.getEntriesForDate(targetDate).first()

        // Assert
        assertEquals("Should return only entries for target date", 1, entries.size)
        assertTrue("Entry should be from target date",
            entries.first().timestamp.toString().contains(targetDate.toString()))

        coVerify { foodEntryDao.getEntriesForDate(targetDate) }
        coVerify { symptomEventDao.getEntriesForDate(targetDate) }
    }

    @Test
    fun `getCorrelatedEntries should link symptoms with potential trigger foods`() = runTest {
        // Arrange
        val baseTime = Instant.now()
        val triggerFood = createTestFoodEntry(
            timestamp = baseTime.minus(2, ChronoUnit.HOURS),
            foods = listOf("Dairy")
        )
        val symptom = createTestSymptomEvent(
            timestamp = baseTime,
            symptomType = SymptomType.ABDOMINAL_PAIN
        )
        val correlation = CorrelationPattern(
            id = 1L,
            foodId = triggerFood.id,
            symptomId = symptom.id,
            correlationStrength = 0.85f,
            confidenceLevel = ConfidenceLevel.HIGH,
            timeOffsetHours = 2,
            occurrenceCount = 5,
            isActive = true
        )

        coEvery { correlationPatternDao.getHighConfidenceCorrelations(any()) } returns listOf(correlation)
        coEvery { foodEntryDao.getById(triggerFood.id) } returns triggerFood
        coEvery { symptomEventDao.getById(symptom.id) } returns symptom

        // Act
        val correlatedEntries = repository.getCorrelatedEntries(minConfidence = 0.8f).first()

        // Assert
        assertFalse("Should have correlated entries", correlatedEntries.isEmpty())

        val correlatedEntry = correlatedEntries.first()
        assertEquals("Should have high correlation strength",
            0.85f, correlatedEntry.correlationStrength, 0.01f)
        assertEquals("Should link correct food and symptom",
            "Dairy", correlatedEntry.triggerFood.foods.first())
        assertEquals("Should have correct time offset",
            2, correlatedEntry.timeOffsetHours)

        coVerify { correlationPatternDao.getHighConfidenceCorrelations(0.8f) }
    }

    @Test
    fun `getTimelineEntries should handle empty DAOs gracefully`() = runTest {
        // Arrange
        coEvery { foodEntryDao.getAll() } returns emptyList()
        coEvery { symptomEventDao.getAll() } returns emptyList()
        coEvery { correlationPatternDao.getActiveCorrelations() } returns emptyList()

        // Act
        val timeline = repository.getTimelineEntries().first()

        // Assert
        assertTrue("Timeline should be empty when no data exists", timeline.isEmpty())

        coVerify { foodEntryDao.getAll() }
        coVerify { symptomEventDao.getAll() }
    }

    @Test
    fun `getTimelineEntries should filter deleted entries`() = runTest {
        // Arrange
        val activeEntry = createTestFoodEntry(isDeleted = false)
        val deletedEntry = createTestFoodEntry(isDeleted = true)

        coEvery { foodEntryDao.getAll() } returns listOf(activeEntry) // DAO should filter deleted
        coEvery { symptomEventDao.getAll() } returns emptyList()
        coEvery { correlationPatternDao.getActiveCorrelations() } returns emptyList()

        // Act
        val timeline = repository.getTimelineEntries().first()

        // Assert
        assertEquals("Should only include active entries", 1, timeline.size)
        val entry = timeline.first() as TimelineEntry.FoodEntry
        assertFalse("Entry should not be deleted", entry.isDeleted)
    }

    @Test
    fun `getTimelineEntriesInRange should respect date boundaries`() = runTest {
        // Arrange
        val startDate = LocalDate.now().minusDays(7)
        val endDate = LocalDate.now()
        val inRangeEntry = createTestFoodEntry(
            timestamp = startDate.plusDays(3).atStartOfDay().toInstant()
        )

        coEvery { foodEntryDao.getEntriesInDateRange(startDate, endDate) } returns listOf(inRangeEntry)
        coEvery { symptomEventDao.getEntriesInDateRange(startDate, endDate) } returns emptyList()

        // Act
        val entries = repository.getTimelineEntriesInRange(startDate, endDate).first()

        // Assert
        assertEquals("Should return entries within range", 1, entries.size)

        val entryDate = entries.first().timestamp.atZone(java.time.ZoneId.systemDefault()).toLocalDate()
        assertFalse("Entry should be within start boundary", entryDate.isBefore(startDate))
        assertFalse("Entry should be within end boundary", entryDate.isAfter(endDate))

        coVerify { foodEntryDao.getEntriesInDateRange(startDate, endDate) }
        coVerify { symptomEventDao.getEntriesInDateRange(startDate, endDate) }
    }

    @Test
    fun `searchTimelineEntries should filter by search query`() = runTest {
        // Arrange
        val searchQuery = "dairy"
        val matchingEntry = createTestFoodEntry(foods = listOf("Milk", "Cheese"))
        val nonMatchingEntry = createTestFoodEntry(foods = listOf("Apple"))

        coEvery { foodEntryDao.searchByFood(searchQuery) } returns listOf(matchingEntry)
        coEvery { symptomEventDao.searchByNotes(searchQuery) } returns emptyList()

        // Act
        val searchResults = repository.searchTimelineEntries(searchQuery).first()

        // Assert
        assertEquals("Should return only matching entries", 1, searchResults.size)

        val entry = searchResults.first() as TimelineEntry.FoodEntry
        assertTrue("Entry should contain dairy products",
            entry.foods.any { it.lowercase().contains("cheese") || it.lowercase().contains("milk") })

        coVerify { foodEntryDao.searchByFood(searchQuery) }
        coVerify { symptomEventDao.searchByNotes(searchQuery) }
    }

    @Test
    fun `calculateTimelineStats should provide summary statistics`() = runTest {
        // Arrange
        val entries = listOf(
            createTestFoodEntry(timestamp = Instant.now().minus(1, ChronoUnit.HOURS)),
            createTestSymptomEvent(timestamp = Instant.now())
        )

        coEvery { foodEntryDao.getAll() } returns listOf(entries[0] as FoodEntry)
        coEvery { symptomEventDao.getAll() } returns listOf(entries[1] as SymptomEvent)
        coEvery { correlationPatternDao.getActiveCorrelations() } returns emptyList()

        // Act
        val stats = repository.calculateTimelineStats()

        // Assert
        assertNotNull("Stats should not be null", stats)
        assertEquals("Should count total entries correctly", 2, stats.totalEntries)
        assertEquals("Should count food entries", 1, stats.foodEntries)
        assertEquals("Should count symptom events", 1, stats.symptomEvents)
        assertTrue("Should calculate time span", stats.timeSpanDays >= 0)
    }

    // Test helper methods
    private fun createTestFoodEntry(
        timestamp: Instant = Instant.now(),
        foods: List<String> = listOf("Test Food"),
        isDeleted: Boolean = false
    ) = FoodEntry(
        id = kotlin.random.Random.nextLong(1, 1000),
        timestamp = timestamp,
        mealType = MealType.BREAKFAST,
        foods = foods,
        portions = foods.associateWith { "1 serving" },
        notes = "Test notes",
        isDeleted = isDeleted
    )

    private fun createTestSymptomEvent(
        timestamp: Instant = Instant.now(),
        symptomType: SymptomType = SymptomType.ABDOMINAL_PAIN
    ) = SymptomEvent(
        id = kotlin.random.Random.nextLong(1, 1000),
        timestamp = timestamp,
        symptomType = symptomType,
        severity = 5,
        duration = null,
        notes = "Test symptom"
    )
}

/**
 * Timeline entry sealed class - this would be defined in the repository
 */
sealed class TimelineEntry(
    val id: Long,
    val timestamp: Instant,
    val isDeleted: Boolean = false
) {
    data class FoodEntry(
        val entryId: Long,
        val entryTimestamp: Instant,
        val mealType: MealType,
        val foods: List<String>,
        val portions: Map<String, String>,
        val notes: String?,
        val deleted: Boolean = false
    ) : TimelineEntry(entryId, entryTimestamp, deleted)

    data class SymptomEntry(
        val eventId: Long,
        val eventTimestamp: Instant,
        val symptomType: SymptomType,
        val severity: Int,
        val duration: java.time.Duration?,
        val notes: String?,
        val deleted: Boolean = false
    ) : TimelineEntry(eventId, eventTimestamp, deleted)
}

/**
 * Correlated entry data class for linking symptoms to potential triggers
 */
data class CorrelatedTimelineEntry(
    val triggerFood: TimelineEntry.FoodEntry,
    val resultingSymptom: TimelineEntry.SymptomEntry,
    val correlationStrength: Float,
    val confidenceLevel: ConfidenceLevel,
    val timeOffsetHours: Int,
    val occurrenceCount: Int
)

/**
 * Timeline statistics data class
 */
data class TimelineStats(
    val totalEntries: Int,
    val foodEntries: Int,
    val symptomEvents: Int,
    val timeSpanDays: Long,
    val averageEntriesPerDay: Float,
    val mostCommonMealType: MealType?,
    val mostCommonSymptomType: SymptomType?
)