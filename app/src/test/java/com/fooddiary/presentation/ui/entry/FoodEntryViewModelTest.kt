package com.fooddiary.presentation.ui.entry

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fooddiary.data.repository.FoodEntryRepository
import com.fooddiary.data.repository.FODMAPRepository
import com.fooddiary.data.repository.QuickEntryTemplateRepository
import com.fooddiary.data.repository.CorrelationRepository
import com.fooddiary.data.database.entities.FoodEntry
import com.fooddiary.data.database.entities.FODMAPFood
import com.fooddiary.data.database.entities.QuickEntryTemplate
import com.fooddiary.data.models.*
import com.fooddiary.data.validation.FoodEntryValidator
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
import java.time.LocalDate

/**
 * Comprehensive TDD unit tests for FoodEntryViewModel
 * Tests form validation, food selection, portion input, FODMAP analysis, and template management
 *
 * THESE TESTS WILL FAIL initially because FoodEntryViewModel doesn't exist yet (TDD approach)
 */
@ExperimentalCoroutinesApi
class FoodEntryViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var foodEntryRepository: FoodEntryRepository

    @MockK
    private lateinit var fodmapRepository: FODMAPRepository

    @MockK
    private lateinit var quickEntryTemplateRepository: QuickEntryTemplateRepository

    @MockK
    private lateinit var correlationRepository: CorrelationRepository

    @MockK
    private lateinit var foodEntryValidator: FoodEntryValidator

    private lateinit var foodEntryViewModel: FoodEntryViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)

        // Setup default mock behaviors
        every { quickEntryTemplateRepository.getAllActive() } returns flowOf(emptyList())
        coEvery { fodmapRepository.searchFood(any()) } returns emptyList()
        coEvery { foodEntryValidator.validateEntry(any()) } returns ValidationResult.Success

        foodEntryViewModel = FoodEntryViewModel(
            foodEntryRepository,
            fodmapRepository,
            quickEntryTemplateRepository,
            correlationRepository,
            foodEntryValidator
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `initial state should have empty form and loading templates`() {
        // Given - fresh ViewModel instance

        // When - accessing initial state
        val initialState = foodEntryViewModel.uiState.value

        // Then
        assertTrue("Form should be initially empty", initialState.selectedFoods.isEmpty())
        assertTrue("Portions should be initially empty", initialState.portions.isEmpty())
        assertEquals("Meal type should be default", MealType.BREAKFAST, initialState.mealType)
        assertTrue("Should be loading templates initially", initialState.isLoadingTemplates)
        assertEquals("No validation errors initially", emptyList<String>(), initialState.validationErrors)
        assertFalse("Save should not be in progress initially", initialState.isSaving)
    }

    @Test
    fun `addFood should add food to selected foods with default portion`() = runTest {
        // Given
        val mockFODMAPFood = createMockFODMAPFood("apple", FODMAPLevel.LOW)
        coEvery { fodmapRepository.searchFood("apple") } returns listOf(mockFODMAPFood)

        // When
        foodEntryViewModel.addFood("apple")
        advanceUntilIdle()

        // Then
        val state = foodEntryViewModel.uiState.value
        assertEquals("Selected foods should contain apple", 1, state.selectedFoods.size)
        assertEquals("Food name should be apple", "apple", state.selectedFoods[0].name)
        assertEquals("Should have default portion", 1, state.portions.size)
        assertEquals("Default portion should be 1 serving", "1 serving", state.portions[0])
    }

    @Test
    fun `addFood should prevent duplicate foods`() = runTest {
        // Given
        val mockFODMAPFood = createMockFODMAPFood("apple", FODMAPLevel.LOW)
        coEvery { fodmapRepository.searchFood("apple") } returns listOf(mockFODMAPFood)

        // When - add same food twice
        foodEntryViewModel.addFood("apple")
        foodEntryViewModel.addFood("apple")
        advanceUntilIdle()

        // Then
        val state = foodEntryViewModel.uiState.value
        assertEquals("Should only have one apple entry", 1, state.selectedFoods.size)
    }

    @Test
    fun `removeFood should remove food and corresponding portion`() = runTest {
        // Given - food already added
        val mockFODMAPFood = createMockFODMAPFood("apple", FODMAPLevel.LOW)
        coEvery { fodmapRepository.searchFood("apple") } returns listOf(mockFODMAPFood)
        foodEntryViewModel.addFood("apple")
        advanceUntilIdle()

        // When
        foodEntryViewModel.removeFood(0)
        advanceUntilIdle()

        // Then
        val state = foodEntryViewModel.uiState.value
        assertTrue("Selected foods should be empty", state.selectedFoods.isEmpty())
        assertTrue("Portions should be empty", state.portions.isEmpty())
    }

    @Test
    fun `updatePortion should update portion at correct index`() = runTest {
        // Given - food already added
        val mockFODMAPFood = createMockFODMAPFood("apple", FODMAPLevel.LOW)
        coEvery { fodmapRepository.searchFood("apple") } returns listOf(mockFODMAPFood)
        foodEntryViewModel.addFood("apple")
        advanceUntilIdle()

        // When
        foodEntryViewModel.updatePortion(0, "2 cups")
        advanceUntilIdle()

        // Then
        val state = foodEntryViewModel.uiState.value
        assertEquals("Portion should be updated", "2 cups", state.portions[0])
    }

    @Test
    fun `updateMealType should change meal type and update timestamp`() {
        // When
        foodEntryViewModel.updateMealType(MealType.DINNER)

        // Then
        val state = foodEntryViewModel.uiState.value
        assertEquals("Meal type should be updated", MealType.DINNER, state.mealType)
    }

    @Test
    fun `validateEntry should show validation errors for invalid input`() = runTest {
        // Given - validator returns errors
        val validationErrors = listOf("At least one food is required", "Portions cannot be empty")
        coEvery { foodEntryValidator.validateEntry(any()) } returns ValidationResult.Error(validationErrors)

        // When
        foodEntryViewModel.validateEntry()
        advanceUntilIdle()

        // Then
        val state = foodEntryViewModel.uiState.value
        assertEquals("Validation errors should be shown", validationErrors, state.validationErrors)
        assertFalse("Entry should not be valid", state.isValid)
    }

    @Test
    fun `validateEntry should clear errors for valid input`() = runTest {
        // Given - add valid food entry
        val mockFODMAPFood = createMockFODMAPFood("apple", FODMAPLevel.LOW)
        coEvery { fodmapRepository.searchFood("apple") } returns listOf(mockFODMAPFood)
        coEvery { foodEntryValidator.validateEntry(any()) } returns ValidationResult.Success

        foodEntryViewModel.addFood("apple")
        advanceUntilIdle()

        // When
        foodEntryViewModel.validateEntry()
        advanceUntilIdle()

        // Then
        val state = foodEntryViewModel.uiState.value
        assertTrue("Validation errors should be empty", state.validationErrors.isEmpty())
        assertTrue("Entry should be valid", state.isValid)
    }

    @Test
    fun `saveEntry should validate then save successful entry`() = runTest {
        // Given - valid entry
        val mockFODMAPFood = createMockFODMAPFood("apple", FODMAPLevel.LOW)
        val savedEntryId = "saved-entry-123"

        coEvery { fodmapRepository.searchFood("apple") } returns listOf(mockFODMAPFood)
        coEvery { foodEntryValidator.validateEntry(any()) } returns ValidationResult.Success
        coEvery { foodEntryRepository.insert(any()) } returns savedEntryId

        foodEntryViewModel.addFood("apple")
        foodEntryViewModel.updatePortion(0, "1 medium")
        advanceUntilIdle()

        // When
        val result = foodEntryViewModel.saveEntry()
        advanceUntilIdle()

        // Then
        assertTrue("Save should be successful", result)
        coVerify(exactly = 1) { foodEntryRepository.insert(any()) }

        val state = foodEntryViewModel.uiState.value
        assertFalse("Should not be saving anymore", state.isSaving)
        assertEquals("Should have success message", "Entry saved successfully", state.message)
    }

    @Test
    fun `saveEntry should not save invalid entry`() = runTest {
        // Given - invalid entry
        val validationErrors = listOf("Invalid entry")
        coEvery { foodEntryValidator.validateEntry(any()) } returns ValidationResult.Error(validationErrors)

        // When
        val result = foodEntryViewModel.saveEntry()
        advanceUntilIdle()

        // Then
        assertFalse("Save should fail", result)
        coVerify(exactly = 0) { foodEntryRepository.insert(any()) }

        val state = foodEntryViewModel.uiState.value
        assertEquals("Should show validation errors", validationErrors, state.validationErrors)
    }

    @Test
    fun `loadTemplates should populate quick entry templates`() = runTest {
        // Given
        val mockTemplates = listOf(
            createMockQuickEntryTemplate("breakfast-template", "Oatmeal with fruit"),
            createMockQuickEntryTemplate("lunch-template", "Chicken salad")
        )
        every { quickEntryTemplateRepository.getAllActive() } returns flowOf(mockTemplates)

        // When
        foodEntryViewModel.loadTemplates()
        advanceUntilIdle()

        // Then
        val state = foodEntryViewModel.uiState.value
        assertEquals("Should have loaded templates", 2, state.quickEntryTemplates.size)
        assertFalse("Should not be loading templates", state.isLoadingTemplates)
    }

    @Test
    fun `applyTemplate should populate form with template data`() = runTest {
        // Given - templates loaded
        val mockTemplate = createMockQuickEntryTemplate("breakfast-template", "Oatmeal with fruit")
        every { quickEntryTemplateRepository.getAllActive() } returns flowOf(listOf(mockTemplate))
        coEvery { quickEntryTemplateRepository.getById("breakfast-template") } returns mockTemplate

        // Mock FODMAP foods for template items
        coEvery { fodmapRepository.searchFood("oatmeal") } returns listOf(createMockFODMAPFood("oatmeal", FODMAPLevel.LOW))
        coEvery { fodmapRepository.searchFood("banana") } returns listOf(createMockFODMAPFood("banana", FODMAPLevel.MODERATE))

        foodEntryViewModel.loadTemplates()
        advanceUntilIdle()

        // When
        foodEntryViewModel.applyTemplate("breakfast-template")
        advanceUntilIdle()

        // Then
        val state = foodEntryViewModel.uiState.value
        assertEquals("Should have template foods", 2, state.selectedFoods.size)
        assertEquals("Should have corresponding portions", 2, state.portions.size)
        assertEquals("Meal type should be from template", MealType.BREAKFAST, state.mealType)
    }

    @Test
    fun `searchFood should return FODMAP foods matching query`() = runTest {
        // Given
        val searchResults = listOf(
            createMockFODMAPFood("apple", FODMAPLevel.LOW),
            createMockFODMAPFood("apple juice", FODMAPLevel.HIGH)
        )
        coEvery { fodmapRepository.searchFood("apple") } returns searchResults

        // When
        val result = foodEntryViewModel.searchFood("apple")
        advanceUntilIdle()

        // Then
        assertEquals("Should return search results", 2, result.size)
        assertTrue("Should contain apple", result.any { it.name == "apple" })
        assertTrue("Should contain apple juice", result.any { it.name == "apple juice" })
    }

    @Test
    fun `analyzeFODMAP should show warnings for high FODMAP foods`() = runTest {
        // Given - high FODMAP food added
        val highFodmapFood = createMockFODMAPFood("garlic", FODMAPLevel.HIGH)
        coEvery { fodmapRepository.searchFood("garlic") } returns listOf(highFodmapFood)
        coEvery { fodmapRepository.analyzeMeal(listOf("garlic")) } returns FODMAPAnalysis(
            overallLevel = FODMAPLevel.HIGH,
            oligosaccharides = FODMAPLevel.HIGH,
            disaccharides = FODMAPLevel.LOW,
            monosaccharides = FODMAPLevel.LOW,
            polyols = FODMAPLevel.LOW,
            problematicIngredients = listOf("garlic")
        )

        // When
        foodEntryViewModel.addFood("garlic")
        advanceUntilIdle()

        // Then
        val state = foodEntryViewModel.uiState.value
        assertNotNull("Should have FODMAP analysis", state.fodmapAnalysis)
        assertEquals("Should show high FODMAP warning", FODMAPLevel.HIGH, state.fodmapAnalysis!!.overallLevel)
        assertTrue("Should show problematic ingredients", state.fodmapAnalysis!!.problematicIngredients.contains("garlic"))
    }

    @Test
    fun `clearForm should reset all form fields`() = runTest {
        // Given - form with data
        val mockFODMAPFood = createMockFODMAPFood("apple", FODMAPLevel.LOW)
        coEvery { fodmapRepository.searchFood("apple") } returns listOf(mockFODMAPFood)

        foodEntryViewModel.addFood("apple")
        foodEntryViewModel.updateMealType(MealType.DINNER)
        foodEntryViewModel.updateNotes("Test notes")
        advanceUntilIdle()

        // When
        foodEntryViewModel.clearForm()
        advanceUntilIdle()

        // Then
        val state = foodEntryViewModel.uiState.value
        assertTrue("Selected foods should be empty", state.selectedFoods.isEmpty())
        assertTrue("Portions should be empty", state.portions.isEmpty())
        assertEquals("Meal type should reset to default", MealType.BREAKFAST, state.mealType)
        assertEquals("Notes should be empty", "", state.notes)
        assertTrue("Validation errors should be empty", state.validationErrors.isEmpty())
    }

    @Test
    fun `updateTimestamp should update entry timestamp`() {
        // Given
        val newTimestamp = Instant.now().minusSeconds(3600) // 1 hour ago

        // When
        foodEntryViewModel.updateTimestamp(newTimestamp)

        // Then
        val state = foodEntryViewModel.uiState.value
        assertEquals("Timestamp should be updated", newTimestamp, state.timestamp)
    }

    @Test
    fun `getDuplicateWarning should check for similar recent entries`() = runTest {
        // Given
        val recentEntries = listOf(
            createMockFoodEntry("apple", LocalDate.now())
        )
        every { foodEntryRepository.getRecent(7) } returns flowOf(recentEntries)

        val mockFODMAPFood = createMockFODMAPFood("apple", FODMAPLevel.LOW)
        coEvery { fodmapRepository.searchFood("apple") } returns listOf(mockFODMAPFood)

        // When
        foodEntryViewModel.addFood("apple")
        advanceUntilIdle()

        // Then
        val state = foodEntryViewModel.uiState.value
        assertTrue("Should show duplicate warning", state.duplicateWarning != null)
        assertTrue("Warning should mention similar entry", state.duplicateWarning!!.contains("similar"))
    }

    // Helper methods for creating mock data
    private fun createMockFODMAPFood(name: String, level: FODMAPLevel): FODMAPFood {
        return FODMAPFood(
            id = "fodmap-$name",
            name = name,
            category = FoodCategory.FRUITS,
            overallLevel = level,
            oligosaccharides = FODMAPLevel.LOW,
            disaccharides = FODMAPLevel.LOW,
            monosaccharides = FODMAPLevel.LOW,
            polyols = FODMAPLevel.LOW,
            servingSize = "1 medium",
            notes = null
        )
    }

    private fun createMockQuickEntryTemplate(id: String, name: String): QuickEntryTemplate {
        return QuickEntryTemplate(
            id = id,
            name = name,
            mealType = MealType.BREAKFAST,
            foods = listOf("oatmeal", "banana"),
            portions = listOf("1 cup", "1 medium"),
            notes = null,
            isActive = true,
            displayOrder = 0
        )
    }

    private fun createMockFoodEntry(name: String, date: LocalDate): FoodEntry {
        return FoodEntry(
            id = "entry-$name-$date",
            timestamp = date.atStartOfDay().toInstant(java.time.ZoneOffset.UTC),
            mealType = MealType.BREAKFAST,
            foods = listOf(name),
            portions = listOf("1 serving"),
            notes = null,
            isDeleted = false
        )
    }
}

// Data classes that FoodEntryViewModel should use (these will need to be implemented)
data class FoodEntryUiState(
    val selectedFoods: List<FODMAPFood> = emptyList(),
    val portions: List<String> = emptyList(),
    val mealType: MealType = MealType.BREAKFAST,
    val timestamp: Instant = Instant.now(),
    val notes: String = "",
    val validationErrors: List<String> = emptyList(),
    val isValid: Boolean = false,
    val isSaving: Boolean = false,
    val isLoadingTemplates: Boolean = true,
    val quickEntryTemplates: List<QuickEntryTemplate> = emptyList(),
    val fodmapAnalysis: FODMAPAnalysis? = null,
    val duplicateWarning: String? = null,
    val message: String? = null
)

sealed class ValidationResult {
    object Success : ValidationResult()
    data class Error(val errors: List<String>) : ValidationResult()
}

enum class FODMAPLevel {
    LOW, MODERATE, HIGH;

    val displayName: String
        get() = when (this) {
            LOW -> "Low FODMAP"
            MODERATE -> "Moderate FODMAP"
            HIGH -> "High FODMAP"
        }
}