package com.fooddiary.domain.usecase

import com.fooddiary.data.database.entities.FoodEntry
import com.fooddiary.data.models.FODMAPLevel
import com.fooddiary.data.repository.FoodEntryRepository
import com.fooddiary.data.repository.FODMAPRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.Instant

@DisplayName("Add Food Entry UseCase Tests")
class AddFoodEntryUseCaseTest {

    private lateinit var foodEntryRepository: FoodEntryRepository
    private lateinit var fodmapRepository: FODMAPRepository
    private lateinit var addFoodEntryUseCase: AddFoodEntryUseCase

    private val testTimestamp = Instant.now()

    @BeforeEach
    fun setup() {
        foodEntryRepository = mockk(relaxed = true)
        fodmapRepository = mockk(relaxed = true)
        addFoodEntryUseCase = AddFoodEntryUseCase(foodEntryRepository, fodmapRepository)
    }

    @Nested
    @DisplayName("Successful Food Entry Creation")
    inner class SuccessfulFoodEntryCreation {

        @Test
        @DisplayName("Should create basic food entry successfully")
        fun `should create basic food entry successfully`() = runTest {
            // Given
            val expectedId = 123L
            coEvery { foodEntryRepository.insert(any()) } returns expectedId

            // When
            val result = addFoodEntryUseCase.invoke(
                foodName = "Apple",
                portions = 1.5,
                portionUnit = "medium",
                mealType = "snack",
                ingredients = emptyList(),
                timestamp = testTimestamp
            )

            // Then
            assertTrue(result.isSuccess)
            assertEquals(expectedId, result.getOrNull())

            val capturedEntry = slot<FoodEntry>()
            coVerify { foodEntryRepository.insert(capture(capturedEntry)) }

            with(capturedEntry.captured) {
                assertEquals("Apple", foodName)
                assertEquals(1.5, portions)
                assertEquals("medium", portionUnit)
                assertEquals("snack", mealType)
                assertEquals(testTimestamp, timestamp)
                assertTrue(ingredients.isEmpty())
                assertNull(fodmapLevel)
            }
        }

        @Test
        @DisplayName("Should create food entry with ingredients and FODMAP analysis")
        fun `should create food entry with ingredients and FODMAP analysis`() = runTest {
            // Given
            val ingredients = listOf("wheat flour", "sugar", "butter")
            val expectedFodmapLevel = FODMAPLevel.HIGH
            val expectedId = 456L

            coEvery { fodmapRepository.analyzeFoodIngredients(ingredients) } returns expectedFodmapLevel
            coEvery { foodEntryRepository.insert(any()) } returns expectedId

            // When
            val result = addFoodEntryUseCase.invoke(
                foodName = "Bread",
                portions = 2.0,
                portionUnit = "slices",
                mealType = "breakfast",
                ingredients = ingredients,
                timestamp = testTimestamp
            )

            // Then
            assertTrue(result.isSuccess)
            assertEquals(expectedId, result.getOrNull())

            coVerify { fodmapRepository.analyzeFoodIngredients(ingredients) }

            val capturedEntry = slot<FoodEntry>()
            coVerify { foodEntryRepository.insert(capture(capturedEntry)) }

            with(capturedEntry.captured) {
                assertEquals("Bread", foodName)
                assertEquals(ingredients, this.ingredients)
                assertEquals(expectedFodmapLevel, fodmapLevel)
            }
        }

        @Test
        @DisplayName("Should create food entry with all optional parameters")
        fun `should create food entry with all optional parameters`() = runTest {
            // Given
            val expectedId = 789L
            coEvery { foodEntryRepository.insert(any()) } returns expectedId

            // When
            val result = addFoodEntryUseCase.invoke(
                foodName = "Pasta",
                portions = 1.0,
                portionUnit = "bowl",
                mealType = "dinner",
                ingredients = listOf("pasta", "tomato sauce"),
                timestamp = testTimestamp,
                location = "Home",
                socialContext = "Family dinner",
                eatingSpeed = "Normal",
                preparationMethod = "Cooked",
                notes = "Delicious meal"
            )

            // Then
            assertTrue(result.isSuccess)

            val capturedEntry = slot<FoodEntry>()
            coVerify { foodEntryRepository.insert(capture(capturedEntry)) }

            with(capturedEntry.captured) {
                assertEquals("Pasta", foodName)
                assertEquals("Home", location)
                assertEquals("Family dinner", socialContext)
                assertEquals("Normal", eatingSpeed)
                assertEquals("Cooked", preparationMethod)
                assertEquals("Delicious meal", notes)
            }
        }

        @Test
        @DisplayName("Should use current timestamp when not provided")
        fun `should use current timestamp when not provided`() = runTest {
            // Given
            val beforeCall = Instant.now()
            coEvery { foodEntryRepository.insert(any()) } returns 1L

            // When
            val result = addFoodEntryUseCase.invoke(
                foodName = "Test Food",
                portions = 1.0,
                portionUnit = "unit",
                mealType = "test",
                ingredients = emptyList()
                // timestamp not provided - should use default (Instant.now())
            )

            // Then
            val afterCall = Instant.now()
            assertTrue(result.isSuccess)

            val capturedEntry = slot<FoodEntry>()
            coVerify { foodEntryRepository.insert(capture(capturedEntry)) }

            // Timestamp should be between beforeCall and afterCall
            assertTrue(capturedEntry.captured.timestamp.isAfter(beforeCall.minusSeconds(1)))
            assertTrue(capturedEntry.captured.timestamp.isBefore(afterCall.plusSeconds(1)))
        }
    }

    @Nested
    @DisplayName("Input Validation")
    inner class InputValidation {

        @Test
        @DisplayName("Should fail when food name is empty")
        fun `should fail when food name is empty`() = runTest {
            // When
            val result = addFoodEntryUseCase.invoke(
                foodName = "",
                portions = 1.0,
                portionUnit = "unit",
                mealType = "test",
                ingredients = emptyList()
            )

            // Then
            assertTrue(result.isFailure)
            val exception = result.exceptionOrNull()
            assertInstanceOf(IllegalArgumentException::class.java, exception)
            assertEquals("Food name cannot be empty", exception?.message)

            coVerify(exactly = 0) { foodEntryRepository.insert(any()) }
        }

        @Test
        @DisplayName("Should fail when food name is blank")
        fun `should fail when food name is blank`() = runTest {
            // When
            val result = addFoodEntryUseCase.invoke(
                foodName = "   ",
                portions = 1.0,
                portionUnit = "unit",
                mealType = "test",
                ingredients = emptyList()
            )

            // Then
            assertTrue(result.isFailure)
            assertInstanceOf(IllegalArgumentException::class.java, result.exceptionOrNull())
            assertEquals("Food name cannot be empty", result.exceptionOrNull()?.message)
        }

        @Test
        @DisplayName("Should fail when portions are zero")
        fun `should fail when portions are zero`() = runTest {
            // When
            val result = addFoodEntryUseCase.invoke(
                foodName = "Test Food",
                portions = 0.0,
                portionUnit = "unit",
                mealType = "test",
                ingredients = emptyList()
            )

            // Then
            assertTrue(result.isFailure)
            assertInstanceOf(IllegalArgumentException::class.java, result.exceptionOrNull())
            assertEquals("Portions must be greater than 0", result.exceptionOrNull()?.message)
        }

        @Test
        @DisplayName("Should fail when portions are negative")
        fun `should fail when portions are negative`() = runTest {
            // When
            val result = addFoodEntryUseCase.invoke(
                foodName = "Test Food",
                portions = -1.0,
                portionUnit = "unit",
                mealType = "test",
                ingredients = emptyList()
            )

            // Then
            assertTrue(result.isFailure)
            assertInstanceOf(IllegalArgumentException::class.java, result.exceptionOrNull())
            assertEquals("Portions must be greater than 0", result.exceptionOrNull()?.message)
        }

        @Test
        @DisplayName("Should fail when meal type is empty")
        fun `should fail when meal type is empty`() = runTest {
            // When
            val result = addFoodEntryUseCase.invoke(
                foodName = "Test Food",
                portions = 1.0,
                portionUnit = "unit",
                mealType = "",
                ingredients = emptyList()
            )

            // Then
            assertTrue(result.isFailure)
            assertInstanceOf(IllegalArgumentException::class.java, result.exceptionOrNull())
            assertEquals("Meal type cannot be empty", result.exceptionOrNull()?.message)
        }

        @Test
        @DisplayName("Should accept valid edge case values")
        fun `should accept valid edge case values`() = runTest {
            // Given
            coEvery { foodEntryRepository.insert(any()) } returns 1L

            // When
            val result = addFoodEntryUseCase.invoke(
                foodName = "A", // Single character
                portions = 0.01, // Very small portion
                portionUnit = "g",
                mealType = "S", // Single character meal type
                ingredients = emptyList()
            )

            // Then
            assertTrue(result.isSuccess)
            coVerify { foodEntryRepository.insert(any()) }
        }
    }

    @Nested
    @DisplayName("FODMAP Integration")
    inner class FODMAPIntegration {

        @Test
        @DisplayName("Should not analyze FODMAP when no ingredients provided")
        fun `should not analyze FODMAP when no ingredients provided`() = runTest {
            // Given
            coEvery { foodEntryRepository.insert(any()) } returns 1L

            // When
            val result = addFoodEntryUseCase.invoke(
                foodName = "Simple Food",
                portions = 1.0,
                portionUnit = "unit",
                mealType = "test",
                ingredients = emptyList()
            )

            // Then
            assertTrue(result.isSuccess)
            coVerify(exactly = 0) { fodmapRepository.analyzeFoodIngredients(any()) }

            val capturedEntry = slot<FoodEntry>()
            coVerify { foodEntryRepository.insert(capture(capturedEntry)) }
            assertNull(capturedEntry.captured.fodmapLevel)
        }

        @Test
        @DisplayName("Should handle FODMAP analysis failure gracefully")
        fun `should handle FODMAP analysis failure gracefully`() = runTest {
            // Given
            val ingredients = listOf("test ingredient")
            coEvery { fodmapRepository.analyzeFoodIngredients(ingredients) } throws RuntimeException("FODMAP analysis failed")
            coEvery { foodEntryRepository.insert(any()) } returns 1L

            // When
            val result = addFoodEntryUseCase.invoke(
                foodName = "Test Food",
                portions = 1.0,
                portionUnit = "unit",
                mealType = "test",
                ingredients = ingredients
            )

            // Then
            assertTrue(result.isFailure)
            assertEquals("FODMAP analysis failed", result.exceptionOrNull()?.message)
            coVerify(exactly = 0) { foodEntryRepository.insert(any()) }
        }

        @Test
        @DisplayName("Should include FODMAP level in entry when ingredients are analyzed")
        fun `should include FODMAP level in entry when ingredients are analyzed`() = runTest {
            // Given
            val ingredients = listOf("wheat", "onion")
            coEvery { fodmapRepository.analyzeFoodIngredients(ingredients) } returns FODMAPLevel.HIGH
            coEvery { foodEntryRepository.insert(any()) } returns 1L

            // When
            val result = addFoodEntryUseCase.invoke(
                foodName = "Bread with Onion",
                portions = 1.0,
                portionUnit = "slice",
                mealType = "breakfast",
                ingredients = ingredients
            )

            // Then
            assertTrue(result.isSuccess)

            val capturedEntry = slot<FoodEntry>()
            coVerify { foodEntryRepository.insert(capture(capturedEntry)) }
            assertEquals(FODMAPLevel.HIGH, capturedEntry.captured.fodmapLevel)
        }
    }

    @Nested
    @DisplayName("Error Handling")
    inner class ErrorHandling {

        @Test
        @DisplayName("Should handle repository insertion failure")
        fun `should handle repository insertion failure`() = runTest {
            // Given
            coEvery { foodEntryRepository.insert(any()) } throws RuntimeException("Database error")

            // When
            val result = addFoodEntryUseCase.invoke(
                foodName = "Test Food",
                portions = 1.0,
                portionUnit = "unit",
                mealType = "test",
                ingredients = emptyList()
            )

            // Then
            assertTrue(result.isFailure)
            assertEquals("Database error", result.exceptionOrNull()?.message)
        }

        @Test
        @DisplayName("Should wrap unexpected exceptions in Result.failure")
        fun `should wrap unexpected exceptions in Result failure`() = runTest {
            // Given
            coEvery { foodEntryRepository.insert(any()) } throws OutOfMemoryError("System error")

            // When
            val result = addFoodEntryUseCase.invoke(
                foodName = "Test Food",
                portions = 1.0,
                portionUnit = "unit",
                mealType = "test",
                ingredients = emptyList()
            )

            // Then
            assertTrue(result.isFailure)
            assertInstanceOf(OutOfMemoryError::class.java, result.exceptionOrNull())
        }

        @Test
        @DisplayName("Should handle null values appropriately")
        fun `should handle null values appropriately`() = runTest {
            // Given
            coEvery { foodEntryRepository.insert(any()) } returns 1L

            // When
            val result = addFoodEntryUseCase.invoke(
                foodName = "Test Food",
                portions = 1.0,
                portionUnit = "unit",
                mealType = "test",
                ingredients = emptyList(),
                location = null,
                socialContext = null,
                eatingSpeed = null,
                preparationMethod = null,
                notes = null
            )

            // Then
            assertTrue(result.isSuccess)

            val capturedEntry = slot<FoodEntry>()
            coVerify { foodEntryRepository.insert(capture(capturedEntry)) }

            with(capturedEntry.captured) {
                assertNull(location)
                assertNull(socialContext)
                assertNull(eatingSpeed)
                assertNull(preparationMethod)
                assertNull(notes)
            }
        }
    }

    @Nested
    @DisplayName("Edge Cases")
    inner class EdgeCases {

        @Test
        @DisplayName("Should handle very large portions")
        fun `should handle very large portions`() = runTest {
            // Given
            coEvery { foodEntryRepository.insert(any()) } returns 1L

            // When
            val result = addFoodEntryUseCase.invoke(
                foodName = "Large Meal",
                portions = 999.99,
                portionUnit = "servings",
                mealType = "feast",
                ingredients = emptyList()
            )

            // Then
            assertTrue(result.isSuccess)

            val capturedEntry = slot<FoodEntry>()
            coVerify { foodEntryRepository.insert(capture(capturedEntry)) }
            assertEquals(999.99, capturedEntry.captured.portions)
        }

        @Test
        @DisplayName("Should handle very small portions")
        fun `should handle very small portions`() = runTest {
            // Given
            coEvery { foodEntryRepository.insert(any()) } returns 1L

            // When
            val result = addFoodEntryUseCase.invoke(
                foodName = "Tiny Taste",
                portions = 0.001,
                portionUnit = "grams",
                mealType = "sample",
                ingredients = emptyList()
            )

            // Then
            assertTrue(result.isSuccess)

            val capturedEntry = slot<FoodEntry>()
            coVerify { foodEntryRepository.insert(capture(capturedEntry)) }
            assertEquals(0.001, capturedEntry.captured.portions)
        }

        @Test
        @DisplayName("Should handle long ingredient lists")
        fun `should handle long ingredient lists`() = runTest {
            // Given
            val manyIngredients = (1..100).map { "ingredient_$it" }
            coEvery { fodmapRepository.analyzeFoodIngredients(manyIngredients) } returns FODMAPLevel.MEDIUM
            coEvery { foodEntryRepository.insert(any()) } returns 1L

            // When
            val result = addFoodEntryUseCase.invoke(
                foodName = "Complex Recipe",
                portions = 1.0,
                portionUnit = "serving",
                mealType = "dinner",
                ingredients = manyIngredients
            )

            // Then
            assertTrue(result.isSuccess)

            val capturedEntry = slot<FoodEntry>()
            coVerify { foodEntryRepository.insert(capture(capturedEntry)) }
            assertEquals(manyIngredients, capturedEntry.captured.ingredients)
            assertEquals(FODMAPLevel.MEDIUM, capturedEntry.captured.fodmapLevel)
        }

        @Test
        @DisplayName("Should handle special characters in food names and notes")
        fun `should handle special characters in food names and notes`() = runTest {
            // Given
            coEvery { foodEntryRepository.insert(any()) } returns 1L

            // When
            val result = addFoodEntryUseCase.invoke(
                foodName = "Café & Crêpe with 日本語",
                portions = 1.0,
                portionUnit = "serving",
                mealType = "café visit",
                ingredients = listOf("🥖 bread", "🧀 cheese"),
                notes = "Très délicieux! 😋 Rating: 5⭐"
            )

            // Then
            assertTrue(result.isSuccess)

            val capturedEntry = slot<FoodEntry>()
            coVerify { foodEntryRepository.insert(capture(capturedEntry)) }

            with(capturedEntry.captured) {
                assertEquals("Café & Crêpe with 日本語", foodName)
                assertEquals("café visit", mealType)
                assertEquals("Très délicieux! 😋 Rating: 5⭐", notes)
                assertEquals(listOf("🥖 bread", "🧀 cheese"), ingredients)
            }
        }
    }
}