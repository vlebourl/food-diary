package com.fooddiary.validation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fooddiary.data.repository.FoodEntryRepository
import com.fooddiary.data.database.entities.FoodEntry
import com.fooddiary.data.models.*
import com.fooddiary.data.validation.FoodEntryValidator
import com.fooddiary.data.validation.DuplicateDetectionService
import com.fooddiary.presentation.ui.entry.FoodEntryViewModel
import io.mockk.*
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.Instant
import java.time.temporal.ChronoUnit

/**
 * T057: Test duplicate food entry prevention within 1-minute window
 * Tests duplicate detection logic and validation error messages
 */
@ExperimentalCoroutinesApi
class DuplicateEntryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var foodEntryRepository: FoodEntryRepository

    @MockK
    private lateinit var foodEntryValidator: FoodEntryValidator

    @MockK
    private lateinit var duplicateDetectionService: DuplicateDetectionService

    private lateinit var foodEntryViewModel: FoodEntryViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)

        // Default mock behaviors
        every { foodEntryRepository.getRecent(any()) } returns flowOf(emptyList())
        coEvery { foodEntryValidator.validateEntry(any()) } returns ValidationResult.Success
        coEvery { duplicateDetectionService.checkForDuplicates(any(), any()) } returns DuplicateCheckResult.NoDuplicates

        foodEntryViewModel = FoodEntryViewModel(
            foodEntryRepository,
            foodEntryValidator,
            duplicateDetectionService
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `should detect duplicate entry within 1-minute window`() = runTest {
        // Given - existing entry just created
        val baseTime = Instant.now()
        val existingEntry = createTestFoodEntry(
            timestamp = baseTime,
            foods = listOf("Apple", "Banana"),
            portions = listOf("1 medium", "1 large")
        )

        // New entry 30 seconds later with same foods
        val duplicateEntry = createTestFoodEntry(
            timestamp = baseTime.plus(30, ChronoUnit.SECONDS),
            foods = listOf("Apple", "Banana"),
            portions = listOf("1 medium", "1 large")
        )

        coEvery { duplicateDetectionService.checkForDuplicates(duplicateEntry, 60) } returns
            DuplicateCheckResult.DuplicateFound(
                existingEntry = existingEntry,
                timeDifference = 30,
                similarity = 1.0f
            )

        // When - attempting to save duplicate entry
        foodEntryViewModel.addFood("Apple")
        foodEntryViewModel.addFood("Banana")
        foodEntryViewModel.updatePortion(0, "1 medium")
        foodEntryViewModel.updatePortion(1, "1 large")
        foodEntryViewModel.updateTimestamp(duplicateEntry.timestamp)

        val result = foodEntryViewModel.saveEntry()
        advanceUntilIdle()

        // Then
        assertFalse("Should not save duplicate entry", result)
        val state = foodEntryViewModel.uiState.value
        assertTrue("Should show duplicate warning", state.showDuplicateWarning)
        assertEquals("Should show appropriate message",
            "Similar entry found 30 seconds ago. Are you sure you want to create another entry?",
            state.duplicateWarningMessage)
        assertFalse("Should not be saved", state.isEntrySaved)
    }

    @Test
    fun `should allow entry exactly 1 minute after previous entry`() = runTest {
        // Given - existing entry exactly 1 minute ago
        val baseTime = Instant.now()
        val existingEntry = createTestFoodEntry(
            timestamp = baseTime.minus(60, ChronoUnit.SECONDS),
            foods = listOf("Apple")
        )

        val newEntry = createTestFoodEntry(
            timestamp = baseTime,
            foods = listOf("Apple")
        )

        coEvery { duplicateDetectionService.checkForDuplicates(newEntry, 60) } returns
            DuplicateCheckResult.NoDuplicates

        coEvery { foodEntryRepository.insert(any()) } returns "entry-123"

        // When - saving entry at 1-minute boundary
        foodEntryViewModel.addFood("Apple")
        foodEntryViewModel.updateTimestamp(newEntry.timestamp)

        val result = foodEntryViewModel.saveEntry()
        advanceUntilIdle()

        // Then
        assertTrue("Should allow entry at 1-minute boundary", result)
        val state = foodEntryViewModel.uiState.value
        assertFalse("Should not show duplicate warning", state.showDuplicateWarning)
        assertTrue("Should be saved successfully", state.isEntrySaved)
    }

    @Test
    fun `should detect duplicate at exactly 59 seconds`() = runTest {
        // Given - existing entry 59 seconds ago
        val baseTime = Instant.now()
        val existingEntry = createTestFoodEntry(
            timestamp = baseTime.minus(59, ChronoUnit.SECONDS),
            foods = listOf("Toast", "Butter")
        )

        val duplicateEntry = createTestFoodEntry(
            timestamp = baseTime,
            foods = listOf("Toast", "Butter")
        )

        coEvery { duplicateDetectionService.checkForDuplicates(duplicateEntry, 60) } returns
            DuplicateCheckResult.DuplicateFound(
                existingEntry = existingEntry,
                timeDifference = 59,
                similarity = 1.0f
            )

        // When - saving entry at 59-second mark
        foodEntryViewModel.addFood("Toast")
        foodEntryViewModel.addFood("Butter")
        foodEntryViewModel.updateTimestamp(duplicateEntry.timestamp)

        val result = foodEntryViewModel.saveEntry()
        advanceUntilIdle()

        // Then
        assertFalse("Should detect duplicate at 59 seconds", result)
        val state = foodEntryViewModel.uiState.value
        assertTrue("Should show duplicate warning", state.showDuplicateWarning)
        assertEquals("Should show time difference",
            "Similar entry found 59 seconds ago. Are you sure you want to create another entry?",
            state.duplicateWarningMessage)
    }

    @Test
    fun `should show validation error message for duplicate prevention`() = runTest {
        // Given - duplicate detection enabled in validator
        val duplicateEntry = createTestFoodEntry(foods = listOf("Coffee"))

        coEvery { foodEntryValidator.validateEntry(any()) } returns
            ValidationResult.Error(listOf("Duplicate entry detected within 1-minute window"))

        // When - attempting to save duplicate
        foodEntryViewModel.addFood("Coffee")
        val result = foodEntryViewModel.saveEntry()
        advanceUntilIdle()

        // Then
        assertFalse("Should not save entry", result)
        val state = foodEntryViewModel.uiState.value
        assertTrue("Should show validation errors", state.validationErrors.isNotEmpty())
        assertEquals("Should show duplicate error message",
            "Duplicate entry detected within 1-minute window",
            state.validationErrors.first())
    }

    @Test
    fun `should handle partial food similarity for duplicate detection`() = runTest {
        // Given - entry with similar but not identical foods
        val baseTime = Instant.now()
        val existingEntry = createTestFoodEntry(
            timestamp = baseTime.minus(30, ChronoUnit.SECONDS),
            foods = listOf("Apple", "Banana", "Orange")
        )

        val similarEntry = createTestFoodEntry(
            timestamp = baseTime,
            foods = listOf("Apple", "Banana") // 2 out of 3 foods match
        )

        coEvery { duplicateDetectionService.checkForDuplicates(similarEntry, 60) } returns
            DuplicateCheckResult.SimilarFound(
                existingEntry = existingEntry,
                timeDifference = 30,
                similarity = 0.67f // 2/3 similarity
            )

        // When - saving partially similar entry
        foodEntryViewModel.addFood("Apple")
        foodEntryViewModel.addFood("Banana")
        foodEntryViewModel.updateTimestamp(similarEntry.timestamp)

        val result = foodEntryViewModel.saveEntry()
        advanceUntilIdle()

        // Then
        val state = foodEntryViewModel.uiState.value
        assertTrue("Should show similarity warning", state.showSimilarityWarning)
        assertEquals("Should show similarity percentage",
            "Entry with 67% similar foods found 30 seconds ago. Continue anyway?",
            state.similarityWarningMessage)
        assertTrue("Should allow user to proceed", state.allowProceedWithSimilar)
    }

    @Test
    fun `should allow forcing duplicate save with user confirmation`() = runTest {
        // Given - duplicate detected but user wants to proceed
        val duplicateEntry = createTestFoodEntry(foods = listOf("Water"))

        coEvery { duplicateDetectionService.checkForDuplicates(any(), any()) } returns
            DuplicateCheckResult.DuplicateFound(
                existingEntry = createTestFoodEntry(foods = listOf("Water")),
                timeDifference = 45,
                similarity = 1.0f
            )

        coEvery { foodEntryRepository.insert(any()) } returns "forced-entry-123"

        // When - user confirms they want to save duplicate
        foodEntryViewModel.addFood("Water")
        foodEntryViewModel.saveEntry() // First attempt shows warning
        advanceUntilIdle()

        foodEntryViewModel.confirmForceDuplicate() // User confirms
        advanceUntilIdle()

        // Then
        val state = foodEntryViewModel.uiState.value
        assertTrue("Should save entry after confirmation", state.isEntrySaved)
        assertEquals("Should show confirmation message",
            "Entry saved successfully (duplicate confirmed)",
            state.successMessage)
        coVerify { foodEntryRepository.insert(any()) }
    }

    @Test
    fun `should check different meal types for duplicate detection`() = runTest {
        // Given - same foods but different meal types within 1 minute
        val baseTime = Instant.now()
        val breakfastEntry = createTestFoodEntry(
            timestamp = baseTime.minus(30, ChronoUnit.SECONDS),
            foods = listOf("Cereal"),
            mealType = MealType.BREAKFAST
        )

        val snackEntry = createTestFoodEntry(
            timestamp = baseTime,
            foods = listOf("Cereal"),
            mealType = MealType.SNACK
        )

        coEvery { duplicateDetectionService.checkForDuplicates(snackEntry, 60) } returns
            DuplicateCheckResult.DifferentMealType(
                existingEntry = breakfastEntry,
                timeDifference = 30,
                similarity = 1.0f
            )

        // When - saving same food but different meal type
        foodEntryViewModel.addFood("Cereal")
        foodEntryViewModel.updateMealType(MealType.SNACK)
        foodEntryViewModel.updateTimestamp(snackEntry.timestamp)

        val result = foodEntryViewModel.saveEntry()
        advanceUntilIdle()

        // Then
        val state = foodEntryViewModel.uiState.value
        assertTrue("Should show meal type difference warning", state.showMealTypeWarning)
        assertEquals("Should explain meal type difference",
            "Similar food logged as Breakfast 30 seconds ago. Logging as Snack now?",
            state.mealTypeWarningMessage)
        assertTrue("Should allow saving with different meal type", state.allowDifferentMealType)
    }

    @Test
    fun `should handle portion size differences in duplicate detection`() = runTest {
        // Given - same foods but different portions
        val baseTime = Instant.now()
        val existingEntry = createTestFoodEntry(
            timestamp = baseTime.minus(45, ChronoUnit.SECONDS),
            foods = listOf("Rice"),
            portions = listOf("1 cup")
        )

        val differentPortionEntry = createTestFoodEntry(
            timestamp = baseTime,
            foods = listOf("Rice"),
            portions = listOf("2 cups")
        )

        coEvery { duplicateDetectionService.checkForDuplicates(differentPortionEntry, 60) } returns
            DuplicateCheckResult.DifferentPortion(
                existingEntry = existingEntry,
                timeDifference = 45,
                similarity = 1.0f,
                portionDifference = "1 cup vs 2 cups"
            )

        // When - saving same food with different portion
        foodEntryViewModel.addFood("Rice")
        foodEntryViewModel.updatePortion(0, "2 cups")
        foodEntryViewModel.updateTimestamp(differentPortionEntry.timestamp)

        val result = foodEntryViewModel.saveEntry()
        advanceUntilIdle()

        // Then
        val state = foodEntryViewModel.uiState.value
        assertTrue("Should show portion difference warning", state.showPortionWarning)
        assertEquals("Should show portion comparison",
            "Rice logged 45 seconds ago with different portion (1 cup vs 2 cups). Continue?",
            state.portionWarningMessage)
        assertTrue("Should allow saving with different portion", state.allowDifferentPortion)
    }

    @Test
    fun `should respect custom time window for duplicate detection`() = runTest {
        // Given - custom 2-minute window for duplicate detection
        val customWindow = 120 // 2 minutes
        val baseTime = Instant.now()
        val existingEntry = createTestFoodEntry(
            timestamp = baseTime.minus(90, ChronoUnit.SECONDS), // 1.5 minutes ago
            foods = listOf("Sandwich")
        )

        val newEntry = createTestFoodEntry(
            timestamp = baseTime,
            foods = listOf("Sandwich")
        )

        coEvery { duplicateDetectionService.checkForDuplicates(newEntry, customWindow) } returns
            DuplicateCheckResult.DuplicateFound(
                existingEntry = existingEntry,
                timeDifference = 90,
                similarity = 1.0f
            )

        // When - using custom time window
        foodEntryViewModel.setDuplicateDetectionWindow(customWindow)
        foodEntryViewModel.addFood("Sandwich")
        foodEntryViewModel.updateTimestamp(newEntry.timestamp)

        val result = foodEntryViewModel.saveEntry()
        advanceUntilIdle()

        // Then
        assertFalse("Should detect duplicate within custom window", result)
        val state = foodEntryViewModel.uiState.value
        assertTrue("Should show duplicate warning", state.showDuplicateWarning)
        assertEquals("Should show custom time difference",
            "Similar entry found 90 seconds ago. Are you sure you want to create another entry?",
            state.duplicateWarningMessage)
    }

    // Helper methods
    private fun createTestFoodEntry(
        id: String = "test-entry",
        timestamp: Instant = Instant.now(),
        foods: List<String> = listOf("Test Food"),
        portions: List<String> = listOf("1 serving"),
        mealType: MealType = MealType.BREAKFAST
    ) = FoodEntry(
        id = id,
        timestamp = timestamp,
        mealType = mealType,
        foods = foods,
        portions = portions,
        notes = null,
        isDeleted = false
    )
}

// Supporting classes for duplicate detection
sealed class DuplicateCheckResult {
    object NoDuplicates : DuplicateCheckResult()

    data class DuplicateFound(
        val existingEntry: FoodEntry,
        val timeDifference: Long, // seconds
        val similarity: Float
    ) : DuplicateCheckResult()

    data class SimilarFound(
        val existingEntry: FoodEntry,
        val timeDifference: Long,
        val similarity: Float
    ) : DuplicateCheckResult()

    data class DifferentMealType(
        val existingEntry: FoodEntry,
        val timeDifference: Long,
        val similarity: Float
    ) : DuplicateCheckResult()

    data class DifferentPortion(
        val existingEntry: FoodEntry,
        val timeDifference: Long,
        val similarity: Float,
        val portionDifference: String
    ) : DuplicateCheckResult()
}

// Extended UI state for FoodEntryViewModel
data class FoodEntryUiState(
    val selectedFoods: List<String> = emptyList(),
    val portions: List<String> = emptyList(),
    val mealType: MealType = MealType.BREAKFAST,
    val timestamp: Instant = Instant.now(),
    val validationErrors: List<String> = emptyList(),
    val isEntrySaved: Boolean = false,
    val showDuplicateWarning: Boolean = false,
    val duplicateWarningMessage: String? = null,
    val showSimilarityWarning: Boolean = false,
    val similarityWarningMessage: String? = null,
    val allowProceedWithSimilar: Boolean = false,
    val showMealTypeWarning: Boolean = false,
    val mealTypeWarningMessage: String? = null,
    val allowDifferentMealType: Boolean = false,
    val showPortionWarning: Boolean = false,
    val portionWarningMessage: String? = null,
    val allowDifferentPortion: Boolean = false,
    val successMessage: String? = null
)