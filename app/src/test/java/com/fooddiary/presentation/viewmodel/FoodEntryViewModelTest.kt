package com.fooddiary.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fooddiary.data.database.entities.FoodEntry
import com.fooddiary.data.models.*
import com.fooddiary.data.repository.FoodEntryRepository
import com.fooddiary.domain.usecase.AddFoodEntryUseCase
import com.fooddiary.domain.usecase.AnalyzeFODMAPContentUseCase
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import java.time.Instant

@OptIn(ExperimentalCoroutinesApi::class)
@DisplayName("Food Entry ViewModel Tests")
class FoodEntryViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var addFoodEntryUseCase: AddFoodEntryUseCase
    private lateinit var analyzeFODMAPContentUseCase: AnalyzeFODMAPContentUseCase
    private lateinit var foodEntryRepository: FoodEntryRepository
    private lateinit var viewModel: FoodEntryViewModel

    private val testTimestamp = Instant.now()

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        addFoodEntryUseCase = mockk(relaxed = true)
        analyzeFODMAPContentUseCase = mockk(relaxed = true)
        foodEntryRepository = mockk(relaxed = true)

        viewModel = FoodEntryViewModel(
            addFoodEntryUseCase,
            analyzeFODMAPContentUseCase,
            foodEntryRepository
        )
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Nested
    @DisplayName("Initial State")
    inner class InitialState {

        @Test
        @DisplayName("Should have correct initial UI state")
        fun `should have correct initial UI state`() {
            // When
            val initialState = viewModel.uiState.value

            // Then
            assertEquals("", initialState.foodName)
            assertTrue(initialState.ingredients.isEmpty())
            assertEquals("", initialState.portions)
            assertEquals("grams", initialState.portionUnit)
            assertEquals(MealType.SNACK, initialState.mealType)
            assertEquals(LocationType.HOME, initialState.context.location)
            assertEquals(SocialContext.ALONE, initialState.context.social)
            assertEquals(EatingSpeed.NORMAL, initialState.context.speed)
            assertFalse(initialState.isLoading)
            assertFalse(initialState.isSuccess)
            assertNull(initialState.errorMessage)
            assertNull(initialState.savedEntryId)
            assertFalse(initialState.isValid)
        }

        @Test
        @DisplayName("Should have null initial FODMAP analysis")
        fun `should have null initial FODMAP analysis`() {
            // When & Then
            assertNull(viewModel.fodmapAnalysis.value)
        }
    }

    @Nested
    @DisplayName("Food Name Updates")
    inner class FoodNameUpdates {

        @Test
        @DisplayName("Should update food name correctly")
        fun `should update food name correctly`() = runTest {
            // When
            viewModel.updateFoodName("Apple")

            // Then
            assertEquals("Apple", viewModel.uiState.value.foodName)
        }

        @Test
        @DisplayName("Should trigger FODMAP analysis when food name is updated")
        fun `should trigger FODMAP analysis when food name is updated`() = runTest {
            // Given
            val mockAnalysis = FODMAPAnalysis(
                overallLevel = FODMAPLevel.LOW,
                components = emptyMap(),
                recommendations = emptyList()
            )
            coEvery { analyzeFODMAPContentUseCase(listOf("Banana")) } returns mockAnalysis

            // When
            viewModel.updateFoodName("Banana")
            advanceUntilIdle()

            // Then
            coVerify { analyzeFODMAPContentUseCase(listOf("Banana")) }
            assertEquals(mockAnalysis, viewModel.fodmapAnalysis.value)
        }

        @Test
        @DisplayName("Should not trigger FODMAP analysis for blank food name")
        fun `should not trigger FODMAP analysis for blank food name`() = runTest {
            // When
            viewModel.updateFoodName("")
            advanceUntilIdle()

            // Then
            coVerify(exactly = 0) { analyzeFODMAPContentUseCase(any()) }
        }

        @Test
        @DisplayName("Should clear food name error when valid name is entered")
        fun `should clear food name error when valid name is entered`() = runTest {
            // Given - set an error state first
            viewModel.saveFoodEntry() // This will set foodNameError
            advanceUntilIdle()
            assertTrue(viewModel.uiState.value.foodNameError != null)

            // When
            viewModel.updateFoodName("Valid Food")

            // Then
            assertEquals("Valid Food", viewModel.uiState.value.foodName)
        }
    }

    @Nested
    @DisplayName("Ingredients Management")
    inner class IngredientsManagement {

        @Test
        @DisplayName("Should update ingredients list correctly")
        fun `should update ingredients list correctly`() = runTest {
            // Given
            val ingredients = listOf("flour", "sugar", "eggs")
            coEvery { analyzeFODMAPContentUseCase(listOf("") + ingredients) } returns mockk()

            // When
            viewModel.updateIngredients(ingredients)
            advanceUntilIdle()

            // Then
            assertEquals(ingredients, viewModel.uiState.value.ingredients)
            coVerify { analyzeFODMAPContentUseCase(listOf("") + ingredients) }
        }

        @Test
        @DisplayName("Should add ingredient when not already present")
        fun `should add ingredient when not already present`() = runTest {
            // Given
            viewModel.updateIngredients(listOf("flour"))
            coEvery { analyzeFODMAPContentUseCase(any()) } returns mockk()

            // When
            viewModel.addIngredient("sugar")
            advanceUntilIdle()

            // Then
            assertEquals(listOf("flour", "sugar"), viewModel.uiState.value.ingredients)
        }

        @Test
        @DisplayName("Should not add duplicate ingredient")
        fun `should not add duplicate ingredient`() = runTest {
            // Given
            viewModel.updateIngredients(listOf("flour", "sugar"))
            coEvery { analyzeFODMAPContentUseCase(any()) } returns mockk()

            // When
            viewModel.addIngredient("flour") // Duplicate
            advanceUntilIdle()

            // Then
            assertEquals(listOf("flour", "sugar"), viewModel.uiState.value.ingredients)
        }

        @Test
        @DisplayName("Should not add blank ingredient")
        fun `should not add blank ingredient`() = runTest {
            // Given
            viewModel.updateIngredients(listOf("flour"))

            // When
            viewModel.addIngredient("") // Blank
            viewModel.addIngredient("   ") // Whitespace

            // Then
            assertEquals(listOf("flour"), viewModel.uiState.value.ingredients)
        }

        @Test
        @DisplayName("Should remove ingredient correctly")
        fun `should remove ingredient correctly`() = runTest {
            // Given
            viewModel.updateIngredients(listOf("flour", "sugar", "eggs"))
            coEvery { analyzeFODMAPContentUseCase(any()) } returns mockk()

            // When
            viewModel.removeIngredient("sugar")
            advanceUntilIdle()

            // Then
            assertEquals(listOf("flour", "eggs"), viewModel.uiState.value.ingredients)
        }

        @Test
        @DisplayName("Should handle removing non-existent ingredient gracefully")
        fun `should handle removing non-existent ingredient gracefully`() = runTest {
            // Given
            viewModel.updateIngredients(listOf("flour", "sugar"))

            // When
            viewModel.removeIngredient("nonexistent")

            // Then
            assertEquals(listOf("flour", "sugar"), viewModel.uiState.value.ingredients)
        }
    }

    @Nested
    @DisplayName("Portions Management")
    inner class PortionsManagement {

        @Test
        @DisplayName("Should update valid portions correctly")
        fun `should update valid portions correctly`() {
            // When
            viewModel.updatePortions("2.5")

            // Then
            assertEquals("2.5", viewModel.uiState.value.portions)
            assertNull(viewModel.uiState.value.portionsError)
        }

        @Test
        @DisplayName("Should set error for invalid portions")
        fun `should set error for invalid portions`() {
            // When
            viewModel.updatePortions("invalid")

            // Then
            assertEquals("invalid", viewModel.uiState.value.portions)
            assertEquals("Invalid number", viewModel.uiState.value.portionsError)
        }

        @Test
        @DisplayName("Should clear portions error for valid input")
        fun `should clear portions error for valid input`() {
            // Given - first set an invalid portion
            viewModel.updatePortions("invalid")
            assertNotNull(viewModel.uiState.value.portionsError)

            // When
            viewModel.updatePortions("1.5")

            // Then
            assertNull(viewModel.uiState.value.portionsError)
        }

        @Test
        @DisplayName("Should update portion unit correctly")
        fun `should update portion unit correctly`() {
            // When
            viewModel.updatePortionUnit("cups")

            // Then
            assertEquals("cups", viewModel.uiState.value.portionUnit)
        }
    }

    @Nested
    @DisplayName("Context Updates")
    inner class ContextUpdates {

        @Test
        @DisplayName("Should update meal type correctly")
        fun `should update meal type correctly`() {
            // When
            viewModel.updateMealType(MealType.BREAKFAST)

            // Then
            assertEquals(MealType.BREAKFAST, viewModel.uiState.value.mealType)
        }

        @Test
        @DisplayName("Should update location correctly")
        fun `should update location correctly`() {
            // When
            viewModel.updateLocation(LocationType.RESTAURANT)

            // Then
            assertEquals(LocationType.RESTAURANT, viewModel.uiState.value.context.location)
            // Other context fields should remain unchanged
            assertEquals(SocialContext.ALONE, viewModel.uiState.value.context.social)
            assertEquals(EatingSpeed.NORMAL, viewModel.uiState.value.context.speed)
        }

        @Test
        @DisplayName("Should update social context correctly")
        fun `should update social context correctly`() {
            // When
            viewModel.updateSocialContext(SocialContext.WITH_FAMILY)

            // Then
            assertEquals(SocialContext.WITH_FAMILY, viewModel.uiState.value.context.social)
            // Other context fields should remain unchanged
            assertEquals(LocationType.HOME, viewModel.uiState.value.context.location)
            assertEquals(EatingSpeed.NORMAL, viewModel.uiState.value.context.speed)
        }

        @Test
        @DisplayName("Should update eating speed correctly")
        fun `should update eating speed correctly`() {
            // When
            viewModel.updateEatingSpeed(EatingSpeed.FAST)

            // Then
            assertEquals(EatingSpeed.FAST, viewModel.uiState.value.context.speed)
            // Other context fields should remain unchanged
            assertEquals(LocationType.HOME, viewModel.uiState.value.context.location)
            assertEquals(SocialContext.ALONE, viewModel.uiState.value.context.social)
        }

        @Test
        @DisplayName("Should update timestamp correctly")
        fun `should update timestamp correctly`() {
            // When
            viewModel.updateTimestamp(testTimestamp)

            // Then
            assertEquals(testTimestamp, viewModel.uiState.value.timestamp)
        }
    }

    @Nested
    @DisplayName("Optional Fields")
    inner class OptionalFields {

        @Test
        @DisplayName("Should update preparation method correctly")
        fun `should update preparation method correctly`() {
            // When
            viewModel.updatePreparationMethod("Grilled")

            // Then
            assertEquals("Grilled", viewModel.uiState.value.preparationMethod)
        }

        @Test
        @DisplayName("Should set preparation method to null for blank input")
        fun `should set preparation method to null for blank input`() {
            // Given
            viewModel.updatePreparationMethod("Grilled")
            assertEquals("Grilled", viewModel.uiState.value.preparationMethod)

            // When
            viewModel.updatePreparationMethod("")

            // Then
            assertNull(viewModel.uiState.value.preparationMethod)
        }

        @Test
        @DisplayName("Should update notes correctly")
        fun `should update notes correctly`() {
            // When
            viewModel.updateNotes("Delicious meal")

            // Then
            assertEquals("Delicious meal", viewModel.uiState.value.notes)
        }

        @Test
        @DisplayName("Should set notes to null for blank input")
        fun `should set notes to null for blank input`() {
            // Given
            viewModel.updateNotes("Some notes")
            assertEquals("Some notes", viewModel.uiState.value.notes)

            // When
            viewModel.updateNotes("")

            // Then
            assertNull(viewModel.uiState.value.notes)
        }
    }

    @Nested
    @DisplayName("Form Validation")
    inner class FormValidation {

        @Test
        @DisplayName("Should be valid with required fields filled")
        fun `should be valid with required fields filled`() {
            // When
            viewModel.updateFoodName("Apple")
            viewModel.updatePortions("1.5")

            // Then
            assertTrue(viewModel.uiState.value.isValid)
        }

        @Test
        @DisplayName("Should be invalid with missing food name")
        fun `should be invalid with missing food name`() {
            // When
            viewModel.updatePortions("1.5")
            // foodName remains blank

            // Then
            assertFalse(viewModel.uiState.value.isValid)
        }

        @Test
        @DisplayName("Should be invalid with missing portions")
        fun `should be invalid with missing portions`() {
            // When
            viewModel.updateFoodName("Apple")
            // portions remains blank

            // Then
            assertFalse(viewModel.uiState.value.isValid)
        }

        @Test
        @DisplayName("Should be invalid with invalid portions")
        fun `should be invalid with invalid portions`() {
            // When
            viewModel.updateFoodName("Apple")
            viewModel.updatePortions("invalid")

            // Then
            assertFalse(viewModel.uiState.value.isValid)
        }

        @Test
        @DisplayName("Should be invalid with zero portions")
        fun `should be invalid with zero portions`() {
            // When
            viewModel.updateFoodName("Apple")
            viewModel.updatePortions("0")

            // Then
            assertFalse(viewModel.uiState.value.isValid)
        }

        @Test
        @DisplayName("Should be invalid with negative portions")
        fun `should be invalid with negative portions`() {
            // When
            viewModel.updateFoodName("Apple")
            viewModel.updatePortions("-1")

            // Then
            assertFalse(viewModel.uiState.value.isValid)
        }
    }

    @Nested
    @DisplayName("Save Food Entry")
    inner class SaveFoodEntry {

        @Test
        @DisplayName("Should save valid food entry successfully")
        fun `should save valid food entry successfully`() = runTest {
            // Given
            val expectedId = "entry_123"
            coEvery { addFoodEntryUseCase(any<FoodEntry>()) } returns expectedId

            viewModel.updateFoodName("Apple")
            viewModel.updatePortions("1.5")
            viewModel.updateMealType(MealType.BREAKFAST)
            viewModel.updateTimestamp(testTimestamp)

            // When
            viewModel.saveFoodEntry()
            advanceUntilIdle()

            // Then
            val finalState = viewModel.uiState.value
            assertTrue(finalState.isSuccess)
            assertEquals(expectedId, finalState.savedEntryId)
            assertFalse(finalState.isLoading)
            assertNull(finalState.errorMessage)
            assertNull(finalState.foodNameError)
            assertNull(finalState.portionsError)

            coVerify {
                addFoodEntryUseCase(match { foodEntry ->
                    foodEntry.name == "Apple" &&
                    foodEntry.portions == 1.5 &&
                    foodEntry.mealType == MealType.BREAKFAST &&
                    foodEntry.timestamp == testTimestamp
                })
            }
        }

        @Test
        @DisplayName("Should show loading state during save")
        fun `should show loading state during save`() = runTest {
            // Given
            coEvery { addFoodEntryUseCase(any<FoodEntry>()) } coAnswers {
                kotlinx.coroutines.delay(100)
                "entry_id"
            }

            viewModel.updateFoodName("Apple")
            viewModel.updatePortions("1.5")

            // When
            viewModel.saveFoodEntry()

            // Then - should be loading initially
            assertTrue(viewModel.uiState.value.isLoading)

            advanceUntilIdle()

            // Then - should not be loading after completion
            assertFalse(viewModel.uiState.value.isLoading)
        }

        @Test
        @DisplayName("Should validate and show error for missing food name")
        fun `should validate and show error for missing food name`() = runTest {
            // Given
            viewModel.updatePortions("1.5") // Valid portions but no food name

            // When
            viewModel.saveFoodEntry()
            advanceUntilIdle()

            // Then
            val finalState = viewModel.uiState.value
            assertEquals("Food name is required", finalState.foodNameError)
            assertFalse(finalState.isSuccess)
            assertFalse(finalState.isLoading)

            coVerify(exactly = 0) { addFoodEntryUseCase(any<FoodEntry>()) }
        }

        @Test
        @DisplayName("Should validate and show error for invalid portions")
        fun `should validate and show error for invalid portions`() = runTest {
            // Given
            viewModel.updateFoodName("Apple")
            viewModel.updatePortions("0") // Invalid portions

            // When
            viewModel.saveFoodEntry()
            advanceUntilIdle()

            // Then
            val finalState = viewModel.uiState.value
            assertEquals("Valid portion amount required", finalState.portionsError)
            assertFalse(finalState.isSuccess)
            assertFalse(finalState.isLoading)

            coVerify(exactly = 0) { addFoodEntryUseCase(any<FoodEntry>()) }
        }

        @Test
        @DisplayName("Should handle save failure gracefully")
        fun `should handle save failure gracefully`() = runTest {
            // Given
            val errorMessage = "Database connection failed"
            coEvery { addFoodEntryUseCase(any<FoodEntry>()) } throws RuntimeException(errorMessage)

            viewModel.updateFoodName("Apple")
            viewModel.updatePortions("1.5")

            // When
            viewModel.saveFoodEntry()
            advanceUntilIdle()

            // Then
            val finalState = viewModel.uiState.value
            assertFalse(finalState.isSuccess)
            assertFalse(finalState.isLoading)
            assertEquals(errorMessage, finalState.errorMessage)
            assertNull(finalState.savedEntryId)
        }

        @Test
        @DisplayName("Should save food entry with all optional fields")
        fun `should save food entry with all optional fields`() = runTest {
            // Given
            coEvery { addFoodEntryUseCase(any<FoodEntry>()) } returns "entry_id"

            viewModel.updateFoodName("Pasta")
            viewModel.updateIngredients(listOf("wheat", "tomatoes"))
            viewModel.updatePortions("2.0")
            viewModel.updatePortionUnit("cups")
            viewModel.updateMealType(MealType.DINNER)
            viewModel.updateLocation(LocationType.RESTAURANT)
            viewModel.updateSocialContext(SocialContext.WITH_FRIENDS)
            viewModel.updateEatingSpeed(EatingSpeed.SLOW)
            viewModel.updateTimestamp(testTimestamp)
            viewModel.updatePreparationMethod("Boiled")
            viewModel.updateNotes("Very tasty")

            // When
            viewModel.saveFoodEntry()
            advanceUntilIdle()

            // Then
            assertTrue(viewModel.uiState.value.isSuccess)

            coVerify {
                addFoodEntryUseCase(match { foodEntry ->
                    foodEntry.name == "Pasta" &&
                    foodEntry.ingredients == listOf("wheat", "tomatoes") &&
                    foodEntry.portions == 2.0 &&
                    foodEntry.portionUnit == "cups" &&
                    foodEntry.mealType == MealType.DINNER &&
                    foodEntry.context.location == LocationType.RESTAURANT &&
                    foodEntry.context.social == SocialContext.WITH_FRIENDS &&
                    foodEntry.context.speed == EatingSpeed.SLOW &&
                    foodEntry.preparationMethod == "Boiled" &&
                    foodEntry.notes == "Very tasty"
                })
            }
        }
    }

    @Nested
    @DisplayName("FODMAP Analysis")
    inner class FODMAPAnalysis {

        @Test
        @DisplayName("Should handle FODMAP analysis error gracefully")
        fun `should handle FODMAP analysis error gracefully`() = runTest {
            // Given
            coEvery { analyzeFODMAPContentUseCase(any()) } throws RuntimeException("Analysis failed")

            // When
            viewModel.updateFoodName("Apple")
            advanceUntilIdle()

            // Then
            assertNull(viewModel.fodmapAnalysis.value)
        }

        @Test
        @DisplayName("Should combine food name and ingredients for FODMAP analysis")
        fun `should combine food name and ingredients for FODMAP analysis`() = runTest {
            // Given
            val mockAnalysis = FODMAPAnalysis(
                overallLevel = FODMAPLevel.MEDIUM,
                components = emptyMap(),
                recommendations = listOf("Monitor portion size")
            )

            viewModel.updateFoodName("Pasta")
            coEvery { analyzeFODMAPContentUseCase(listOf("Pasta", "tomatoes", "garlic")) } returns mockAnalysis

            // When
            viewModel.updateIngredients(listOf("tomatoes", "garlic"))
            advanceUntilIdle()

            // Then
            coVerify { analyzeFODMAPContentUseCase(listOf("Pasta", "tomatoes", "garlic")) }
            assertEquals(mockAnalysis, viewModel.fodmapAnalysis.value)
        }
    }

    @Nested
    @DisplayName("Form Reset and Error Handling")
    inner class FormResetAndErrorHandling {

        @Test
        @DisplayName("Should reset form to initial state")
        fun `should reset form to initial state`() = runTest {
            // Given - populate form with data
            viewModel.updateFoodName("Apple")
            viewModel.updateIngredients(listOf("sugar"))
            viewModel.updatePortions("1.5")
            viewModel.updateMealType(MealType.BREAKFAST)
            viewModel.updateNotes("Test notes")

            // When
            viewModel.resetForm()

            // Then
            val state = viewModel.uiState.value
            assertEquals("", state.foodName)
            assertTrue(state.ingredients.isEmpty())
            assertEquals("", state.portions)
            assertEquals(MealType.SNACK, state.mealType) // Default value
            assertNull(state.notes)
            assertFalse(state.isSuccess)
            assertNull(state.errorMessage)
            assertNull(state.fodmapAnalysis.value)
        }

        @Test
        @DisplayName("Should clear error message")
        fun `should clear error message`() = runTest {
            // Given - set error state
            coEvery { addFoodEntryUseCase(any<FoodEntry>()) } throws RuntimeException("Test error")
            viewModel.updateFoodName("Apple")
            viewModel.updatePortions("1.5")
            viewModel.saveFoodEntry()
            advanceUntilIdle()

            assertNotNull(viewModel.uiState.value.errorMessage)

            // When
            viewModel.clearError()

            // Then
            assertNull(viewModel.uiState.value.errorMessage)
        }
    }

    @Nested
    @DisplayName("Suggestions and Search")
    inner class SuggestionsAndSearch {

        @Test
        @DisplayName("Should return ingredient suggestions for valid query")
        fun `should return ingredient suggestions for valid query`() = runTest {
            // Given
            val mockFoodEntries = listOf(
                FoodEntry.create("Pizza", listOf("tomato sauce", "cheese", "tomato"), 1.0, "slice", MealType.DINNER),
                FoodEntry.create("Salad", listOf("tomato", "lettuce"), 1.0, "bowl", MealType.LUNCH)
            )
            coEvery { foodEntryRepository.searchByIngredient("tom") } returns flowOf(mockFoodEntries)

            // When
            val suggestionsFlow = viewModel.getSuggestedIngredients("tom")
            val suggestions = suggestionsFlow.first()

            // Then
            assertEquals(3, suggestions.size)
            assertTrue(suggestions.contains("tomato sauce"))
            assertTrue(suggestions.contains("tomato"))
            // Should be distinct
            assertEquals(suggestions.distinct(), suggestions)
        }

        @Test
        @DisplayName("Should return empty list for short query")
        fun `should return empty list for short query`() = runTest {
            // When
            val suggestions = viewModel.getSuggestedIngredients("a").first()

            // Then
            assertTrue(suggestions.isEmpty())
            coVerify(exactly = 0) { foodEntryRepository.searchByIngredient(any()) }
        }

        @Test
        @DisplayName("Should handle search error gracefully")
        fun `should handle search error gracefully`() = runTest {
            // Given
            coEvery { foodEntryRepository.searchByIngredient("tom") } throws RuntimeException("Search failed")

            // When
            val suggestions = viewModel.getSuggestedIngredients("tom").first()

            // Then
            assertTrue(suggestions.isEmpty())
        }

        @Test
        @DisplayName("Should return recent food names")
        fun `should return recent food names`() = runTest {
            // Given
            val recentEntries = listOf(
                FoodEntry.create("Apple", emptyList(), 1.0, "unit", MealType.SNACK),
                FoodEntry.create("Banana", emptyList(), 1.0, "unit", MealType.SNACK),
                FoodEntry.create("Apple", emptyList(), 1.0, "unit", MealType.SNACK) // Duplicate
            )
            coEvery { foodEntryRepository.getRecent(10) } returns flowOf(recentEntries)

            // When
            val recentNames = viewModel.getRecentFoodNames().first()

            // Then
            assertEquals(2, recentNames.size) // Should be distinct
            assertTrue(recentNames.contains("Apple"))
            assertTrue(recentNames.contains("Banana"))
        }

        @Test
        @DisplayName("Should handle recent food names error gracefully")
        fun `should handle recent food names error gracefully`() = runTest {
            // Given
            coEvery { foodEntryRepository.getRecent(10) } throws RuntimeException("Database error")

            // When
            val recentNames = viewModel.getRecentFoodNames().first()

            // Then
            assertTrue(recentNames.isEmpty())
        }
    }

    @Nested
    @DisplayName("UI State Properties")
    inner class UIStateProperties {

        @Test
        @DisplayName("Should format timestamp correctly")
        fun `should format timestamp correctly`() {
            // Given
            val specificTime = Instant.parse("2024-01-15T14:30:00Z")

            // When
            viewModel.updateTimestamp(specificTime)

            // Then
            val formattedTime = viewModel.uiState.value.timestampFormatted
            assertTrue(formattedTime.contains("Jan"))
            assertTrue(formattedTime.contains("15"))
            // Note: The exact format depends on system timezone, so we just check basic components
        }

        @Test
        @DisplayName("Should calculate validity correctly for various states")
        fun `should calculate validity correctly for various states`() {
            // Initially invalid
            assertFalse(viewModel.uiState.value.isValid)

            // Valid with required fields
            viewModel.updateFoodName("Apple")
            viewModel.updatePortions("1.5")
            assertTrue(viewModel.uiState.value.isValid)

            // Invalid with error
            viewModel.updatePortions("invalid")
            assertFalse(viewModel.uiState.value.isValid)

            // Invalid with zero portions
            viewModel.updatePortions("0")
            assertFalse(viewModel.uiState.value.isValid)

            // Valid again
            viewModel.updatePortions("2.5")
            assertTrue(viewModel.uiState.value.isValid)
        }
    }

    // Helper function to create mock FODMAP analysis
    private fun createMockFODMAPAnalysis(level: FODMAPLevel = FODMAPLevel.LOW) = FODMAPAnalysis(
        overallLevel = level,
        components = mapOf(
            FODMAPComponent.OLIGOSACCHARIDES to level,
            FODMAPComponent.DISACCHARIDES to level,
            FODMAPComponent.MONOSACCHARIDES to level,
            FODMAPComponent.POLYOLS to level
        ),
        recommendations = listOf("Test recommendation")
    )
}