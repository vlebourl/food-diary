package com.fooddiary.presentation.ui.timeline

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fooddiary.data.repository.FoodEntryRepository
import com.fooddiary.data.repository.SymptomEventRepository
import com.fooddiary.data.repository.BeverageEntryRepository
import com.fooddiary.data.repository.EnvironmentalContextRepository
import com.fooddiary.data.database.entities.FoodEntry
import com.fooddiary.data.database.entities.SymptomEvent
import com.fooddiary.data.database.entities.BeverageEntry
import com.fooddiary.data.models.*
import io.mockk.*
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.assertFalse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate
import java.time.Instant

/**
 * Comprehensive TDD unit tests for TimelineViewModel
 * Tests timeline data loading, state management, pagination, filtering, and search functionality
 *
 * THESE TESTS WILL FAIL initially because TimelineViewModel doesn't exist yet (TDD approach)
 */
@ExperimentalCoroutinesApi
class TimelineViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var foodEntryRepository: FoodEntryRepository

    @MockK
    private lateinit var symptomEventRepository: SymptomEventRepository

    @MockK
    private lateinit var beverageEntryRepository: BeverageEntryRepository

    @MockK
    private lateinit var environmentalContextRepository: EnvironmentalContextRepository

    private lateinit var timelineViewModel: TimelineViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)

        // Setup default mock behaviors
        every { foodEntryRepository.getAllByDateRange(any(), any()) } returns flowOf(emptyList())
        every { symptomEventRepository.getAllByDateRange(any(), any()) } returns flowOf(emptyList())
        every { beverageEntryRepository.getAllByDateRange(any(), any()) } returns flowOf(emptyList())
        every { environmentalContextRepository.getByDateRange(any(), any()) } returns flowOf(emptyList())

        timelineViewModel = TimelineViewModel(
            foodEntryRepository,
            symptomEventRepository,
            beverageEntryRepository,
            environmentalContextRepository
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `initial state should be loading with empty data`() {
        // Given - fresh ViewModel instance

        // When - accessing initial state
        val initialState = timelineViewModel.uiState.value

        // Then
        assertTrue("Initial state should be loading", initialState.isLoading)
        assertTrue("Initial timeline should be empty", initialState.timelineEntries.isEmpty())
        assertEquals("Error should be null initially", null, initialState.error)
        assertEquals("Selected date should be today", LocalDate.now(), initialState.selectedDate)
    }

    @Test
    fun `loadTimeline should update state to loading then success with data`() = runTest {
        // Given
        val testDate = LocalDate.of(2024, 1, 15)
        val mockFoodEntries = listOf(
            createMockFoodEntry("apple", testDate),
            createMockFoodEntry("bread", testDate)
        )
        val mockSymptomEvents = listOf(
            createMockSymptomEvent(SymptomType.BLOATING, testDate)
        )

        every { foodEntryRepository.getAllByDateRange(testDate, testDate) } returns flowOf(mockFoodEntries)
        every { symptomEventRepository.getAllByDateRange(testDate, testDate) } returns flowOf(mockSymptomEvents)
        every { beverageEntryRepository.getAllByDateRange(testDate, testDate) } returns flowOf(emptyList())
        every { environmentalContextRepository.getByDateRange(testDate, testDate) } returns flowOf(emptyList())

        // When
        timelineViewModel.loadTimeline(testDate)
        advanceUntilIdle()

        // Then
        val finalState = timelineViewModel.uiState.value
        assertFalse("Loading should be complete", finalState.isLoading)
        assertEquals("Timeline should contain 3 entries", 3, finalState.timelineEntries.size)
        assertEquals("Selected date should be updated", testDate, finalState.selectedDate)
        assertEquals("Error should be null", null, finalState.error)
    }

    @Test
    fun `loadTimeline should handle repository errors gracefully`() = runTest {
        // Given
        val testDate = LocalDate.of(2024, 1, 15)
        val testException = RuntimeException("Database error")

        every { foodEntryRepository.getAllByDateRange(testDate, testDate) } throws testException

        // When
        timelineViewModel.loadTimeline(testDate)
        advanceUntilIdle()

        // Then
        val finalState = timelineViewModel.uiState.value
        assertFalse("Loading should be complete", finalState.isLoading)
        assertTrue("Timeline should be empty on error", finalState.timelineEntries.isEmpty())
        assertEquals("Error should be set", "Database error", finalState.error)
    }

    @Test
    fun `refreshData should reload timeline data and clear error state`() = runTest {
        // Given - ViewModel in error state
        val testDate = LocalDate.now()
        timelineViewModel.loadTimeline(testDate)
        advanceUntilIdle()

        // Reset mocks for successful refresh
        every { foodEntryRepository.getAllByDateRange(any(), any()) } returns flowOf(
            listOf(createMockFoodEntry("refreshed_food", testDate))
        )
        every { symptomEventRepository.getAllByDateRange(any(), any()) } returns flowOf(emptyList())
        every { beverageEntryRepository.getAllByDateRange(any(), any()) } returns flowOf(emptyList())
        every { environmentalContextRepository.getByDateRange(any(), any()) } returns flowOf(emptyList())

        // When
        timelineViewModel.refreshData()
        advanceUntilIdle()

        // Then
        val finalState = timelineViewModel.uiState.value
        assertFalse("Loading should be complete", finalState.isLoading)
        assertEquals("Error should be cleared", null, finalState.error)
        assertEquals("Timeline should contain refreshed data", 1, finalState.timelineEntries.size)
    }

    @Test
    fun `loadMore should append additional data when pagination enabled`() = runTest {
        // Given - initial data loaded
        val testDate = LocalDate.now()
        val initialEntries = listOf(createMockFoodEntry("initial", testDate))
        val additionalEntries = listOf(createMockFoodEntry("additional", testDate))

        every { foodEntryRepository.getAllByDateRange(any(), any()) } returns
            flowOf(initialEntries) andThen flowOf(initialEntries + additionalEntries)
        every { symptomEventRepository.getAllByDateRange(any(), any()) } returns flowOf(emptyList())
        every { beverageEntryRepository.getAllByDateRange(any(), any()) } returns flowOf(emptyList())
        every { environmentalContextRepository.getByDateRange(any(), any()) } returns flowOf(emptyList())

        timelineViewModel.loadTimeline(testDate)
        advanceUntilIdle()

        // When
        timelineViewModel.loadMore()
        advanceUntilIdle()

        // Then
        val finalState = timelineViewModel.uiState.value
        assertEquals("Timeline should contain more entries", 2, finalState.timelineEntries.size)
        assertFalse("Loading should be complete", finalState.isLoading)
    }

    @Test
    fun `filterByDate should update timeline data for new date range`() = runTest {
        // Given
        val originalDate = LocalDate.of(2024, 1, 15)
        val newDate = LocalDate.of(2024, 1, 16)
        val newDateEntries = listOf(createMockFoodEntry("new_date_food", newDate))

        every { foodEntryRepository.getAllByDateRange(originalDate, originalDate) } returns flowOf(emptyList())
        every { foodEntryRepository.getAllByDateRange(newDate, newDate) } returns flowOf(newDateEntries)
        every { symptomEventRepository.getAllByDateRange(any(), any()) } returns flowOf(emptyList())
        every { beverageEntryRepository.getAllByDateRange(any(), any()) } returns flowOf(emptyList())
        every { environmentalContextRepository.getByDateRange(any(), any()) } returns flowOf(emptyList())

        // When
        timelineViewModel.filterByDate(newDate)
        advanceUntilIdle()

        // Then
        val finalState = timelineViewModel.uiState.value
        assertEquals("Selected date should be updated", newDate, finalState.selectedDate)
        assertEquals("Timeline should contain new date entries", 1, finalState.timelineEntries.size)
        assertFalse("Loading should be complete", finalState.isLoading)
    }

    @Test
    fun `searchEntries should filter timeline entries by query`() = runTest {
        // Given
        val testDate = LocalDate.now()
        val allEntries = listOf(
            createMockFoodEntry("apple pie", testDate),
            createMockFoodEntry("banana bread", testDate),
            createMockFoodEntry("coffee", testDate)
        )

        every { foodEntryRepository.getAllByDateRange(any(), any()) } returns flowOf(allEntries)
        every { symptomEventRepository.getAllByDateRange(any(), any()) } returns flowOf(emptyList())
        every { beverageEntryRepository.getAllByDateRange(any(), any()) } returns flowOf(emptyList())
        every { environmentalContextRepository.getByDateRange(any(), any()) } returns flowOf(emptyList())

        timelineViewModel.loadTimeline(testDate)
        advanceUntilIdle()

        // When
        timelineViewModel.searchEntries("apple")
        advanceUntilIdle()

        // Then
        val finalState = timelineViewModel.uiState.value
        assertEquals("Search should filter entries", 1, finalState.timelineEntries.size)
        assertEquals("Search query should be stored", "apple", finalState.searchQuery)
    }

    @Test
    fun `clearSearch should reset search query and restore full timeline`() = runTest {
        // Given - search active with filtered results
        val testDate = LocalDate.now()
        val allEntries = listOf(
            createMockFoodEntry("apple", testDate),
            createMockFoodEntry("banana", testDate)
        )

        every { foodEntryRepository.getAllByDateRange(any(), any()) } returns flowOf(allEntries)
        every { symptomEventRepository.getAllByDateRange(any(), any()) } returns flowOf(emptyList())
        every { beverageEntryRepository.getAllByDateRange(any(), any()) } returns flowOf(emptyList())
        every { environmentalContextRepository.getByDateRange(any(), any()) } returns flowOf(emptyList())

        timelineViewModel.loadTimeline(testDate)
        timelineViewModel.searchEntries("apple")
        advanceUntilIdle()

        // When
        timelineViewModel.clearSearch()
        advanceUntilIdle()

        // Then
        val finalState = timelineViewModel.uiState.value
        assertEquals("Search query should be cleared", "", finalState.searchQuery)
        assertEquals("All entries should be restored", 2, finalState.timelineEntries.size)
    }

    @Test
    fun `timeline entries should be sorted chronologically`() = runTest {
        // Given
        val testDate = LocalDate.now()
        val morningEntry = createMockFoodEntry("breakfast", testDate, 8)
        val eveningEntry = createMockFoodEntry("dinner", testDate, 20)
        val afternoonEntry = createMockFoodEntry("lunch", testDate, 14)

        every { foodEntryRepository.getAllByDateRange(any(), any()) } returns
            flowOf(listOf(eveningEntry, morningEntry, afternoonEntry))
        every { symptomEventRepository.getAllByDateRange(any(), any()) } returns flowOf(emptyList())
        every { beverageEntryRepository.getAllByDateRange(any(), any()) } returns flowOf(emptyList())
        every { environmentalContextRepository.getByDateRange(any(), any()) } returns flowOf(emptyList())

        // When
        timelineViewModel.loadTimeline(testDate)
        advanceUntilIdle()

        // Then
        val finalState = timelineViewModel.uiState.value
        val sortedEntries = finalState.timelineEntries
        assertEquals("First entry should be morning", "breakfast", (sortedEntries[0] as TimelineEntry.FoodEntry).name)
        assertEquals("Second entry should be afternoon", "lunch", (sortedEntries[1] as TimelineEntry.FoodEntry).name)
        assertEquals("Third entry should be evening", "dinner", (sortedEntries[2] as TimelineEntry.FoodEntry).name)
    }

    @Test
    fun `selectEntry should update selected entry in state`() {
        // Given
        val entryId = "test-entry-id"

        // When
        timelineViewModel.selectEntry(entryId)

        // Then
        val state = timelineViewModel.uiState.value
        assertEquals("Selected entry ID should be updated", entryId, state.selectedEntryId)
    }

    @Test
    fun `toggleEntryType filter should update visible entry types`() = runTest {
        // Given
        val testDate = LocalDate.now()
        val foodEntry = createMockFoodEntry("apple", testDate)
        val symptomEntry = createMockSymptomEvent(SymptomType.BLOATING, testDate)

        every { foodEntryRepository.getAllByDateRange(any(), any()) } returns flowOf(listOf(foodEntry))
        every { symptomEventRepository.getAllByDateRange(any(), any()) } returns flowOf(listOf(symptomEntry))
        every { beverageEntryRepository.getAllByDateRange(any(), any()) } returns flowOf(emptyList())
        every { environmentalContextRepository.getByDateRange(any(), any()) } returns flowOf(emptyList())

        timelineViewModel.loadTimeline(testDate)
        advanceUntilIdle()

        // When - hide symptom entries
        timelineViewModel.toggleEntryTypeFilter(TimelineEntryType.SYMPTOM, false)

        // Then
        val finalState = timelineViewModel.uiState.value
        assertEquals("Only food entries should be visible", 1, finalState.timelineEntries.size)
        assertTrue("Visible entry should be food type", finalState.timelineEntries[0] is TimelineEntry.FoodEntry)
    }

    // Helper methods for creating mock data
    private fun createMockFoodEntry(name: String, date: LocalDate, hour: Int = 12): FoodEntry {
        return FoodEntry(
            id = "food-${name}-${date}",
            timestamp = date.atTime(hour, 0).toInstant(java.time.ZoneOffset.UTC),
            mealType = MealType.LUNCH,
            foods = listOf(name),
            portions = listOf("1 serving"),
            notes = null,
            isDeleted = false
        )
    }

    private fun createMockSymptomEvent(type: SymptomType, date: LocalDate, hour: Int = 14): SymptomEvent {
        return SymptomEvent(
            id = "symptom-${type}-${date}",
            timestamp = date.atTime(hour, 0).toInstant(java.time.ZoneOffset.UTC),
            type = type,
            severity = 5,
            duration = null,
            triggerFoodId = null,
            notes = null,
            isDeleted = false
        )
    }
}

// Data classes that TimelineViewModel should use (these will need to be implemented)
data class TimelineUiState(
    val isLoading: Boolean = false,
    val timelineEntries: List<TimelineEntry> = emptyList(),
    val selectedDate: LocalDate = LocalDate.now(),
    val selectedEntryId: String? = null,
    val searchQuery: String = "",
    val visibleEntryTypes: Set<TimelineEntryType> = setOf(
        TimelineEntryType.FOOD,
        TimelineEntryType.SYMPTOM,
        TimelineEntryType.BEVERAGE,
        TimelineEntryType.ENVIRONMENTAL
    ),
    val error: String? = null
)

sealed class TimelineEntry {
    data class FoodEntry(
        val id: String,
        val name: String,
        val timestamp: Instant,
        val mealType: MealType,
        val foods: List<String>,
        val portions: List<String>
    ) : TimelineEntry()

    data class SymptomEntry(
        val id: String,
        val timestamp: Instant,
        val type: SymptomType,
        val severity: Int,
        val duration: String?
    ) : TimelineEntry()

    data class BeverageEntry(
        val id: String,
        val timestamp: Instant,
        val name: String,
        val volume: Float,
        val caffeineContent: Float?
    ) : TimelineEntry()

    data class EnvironmentalEntry(
        val id: String,
        val date: LocalDate,
        val stressLevel: Int?,
        val sleepQuality: Int?,
        val exerciseMinutes: Int?
    ) : TimelineEntry()
}

enum class TimelineEntryType {
    FOOD,
    SYMPTOM,
    BEVERAGE,
    ENVIRONMENTAL
}