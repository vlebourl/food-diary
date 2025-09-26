package com.fooddiary.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fooddiary.data.database.dao.FoodEntryDao
import com.fooddiary.data.database.dao.FODMAPFoodDao
import com.fooddiary.data.database.dao.QuickEntryTemplateDao
import com.fooddiary.data.database.entities.FoodEntry
import com.fooddiary.data.database.entities.FODMAPFood
import com.fooddiary.data.database.entities.QuickEntryTemplate
import com.fooddiary.data.models.*
import com.fooddiary.data.validation.FoodEntryValidator
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
import java.time.Instant
import java.time.LocalDate
import java.time.temporal.ChronoUnit

/**
 * TDD Tests for FoodEntryRepository - THESE WILL FAIL INITIALLY
 *
 * Test coverage:
 * - CRUD operations with business logic
 * - Data validation and constraint checking
 * - Food search and filtering
 * - FODMAP analysis integration
 * - Quick entry template management
 * - Duplicate detection and merging
 * - Nutritional analysis
 */
class FoodEntryRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var foodEntryDao: FoodEntryDao

    @MockK
    private lateinit var fodmapFoodDao: FODMAPFoodDao

    @MockK
    private lateinit var quickEntryTemplateDao: QuickEntryTemplateDao

    @MockK
    private lateinit var foodEntryValidator: FoodEntryValidator

    private lateinit var repository: FoodEntryRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        // This will fail initially since FoodEntryRepository doesn't exist yet
        repository = FoodEntryRepository(
            foodEntryDao = foodEntryDao,
            fodmapFoodDao = fodmapFoodDao,
            quickEntryTemplateDao = quickEntryTemplateDao,
            validator = foodEntryValidator
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `addFoodEntry should validate entry before saving`() = runTest {
        // Arrange
        val validEntry = createTestFoodEntry()
        val validationResult = ValidationResult.Success
        val insertedId = 123L

        every { foodEntryValidator.validate(any()) } returns validationResult
        coEvery { foodEntryDao.insert(any()) } returns insertedId

        // Act
        val result = repository.addFoodEntry(validEntry)

        // Assert
        assertTrue("Should return success result", result.isSuccess)
        assertEquals("Should return inserted ID", insertedId, result.getOrNull())

        verify { foodEntryValidator.validate(validEntry) }
        coVerify { foodEntryDao.insert(validEntry) }
    }

    @Test
    fun `addFoodEntry should reject invalid entry`() = runTest {
        // Arrange
        val invalidEntry = createTestFoodEntry(foods = emptyList()) // Invalid: no foods
        val validationResult = ValidationResult.Error("Foods list cannot be empty")

        every { foodEntryValidator.validate(any()) } returns validationResult
        coEvery { foodEntryDao.insert(any()) } returns 0L

        // Act
        val result = repository.addFoodEntry(invalidEntry)

        // Assert
        assertTrue("Should return failure result", result.isFailure)
        assertEquals("Should return validation error",
            "Foods list cannot be empty", result.exceptionOrNull()?.message)

        verify { foodEntryValidator.validate(invalidEntry) }
        coVerify(exactly = 0) { foodEntryDao.insert(any()) }
    }

    @Test
    fun `updateFoodEntry should validate changes and update modifiedAt`() = runTest {
        // Arrange
        val originalEntry = createTestFoodEntry(id = 1L)
        val updatedEntry = originalEntry.copy(foods = listOf("Updated Food"))
        val validationResult = ValidationResult.Success

        every { foodEntryValidator.validate(any()) } returns validationResult
        coEvery { foodEntryDao.getById(1L) } returns originalEntry
        coEvery { foodEntryDao.update(any()) } returns 1

        // Act
        val result = repository.updateFoodEntry(updatedEntry)

        // Assert
        assertTrue("Should return success result", result.isSuccess)

        verify { foodEntryValidator.validate(updatedEntry) }
        coVerify { foodEntryDao.update(match { entry ->
            entry.foods == listOf("Updated Food") &&
            entry.modifiedAt.isAfter(originalEntry.modifiedAt)
        }) }
    }

    @Test
    fun `updateFoodEntry should fail for non-existent entry`() = runTest {
        // Arrange
        val nonExistentEntry = createTestFoodEntry(id = 999L)

        coEvery { foodEntryDao.getById(999L) } returns null

        // Act
        val result = repository.updateFoodEntry(nonExistentEntry)

        // Assert
        assertTrue("Should return failure result", result.isFailure)
        assertEquals("Should return not found error",
            "Food entry not found", result.exceptionOrNull()?.message)

        coVerify { foodEntryDao.getById(999L) }
        coVerify(exactly = 0) { foodEntryDao.update(any()) }
    }

    @Test
    fun `deleteFoodEntry should perform soft delete with validation`() = runTest {
        // Arrange
        val entryId = "entry-123"
        val existingEntry = createTestFoodEntry(id = 123L)

        coEvery { foodEntryDao.getById(123L) } returns existingEntry
        coEvery { foodEntryDao.update(any()) } returns 1

        // Act
        val result = repository.deleteFoodEntry(entryId)

        // Assert
        assertTrue("Should return success result", result.isSuccess)

        coVerify { foodEntryDao.update(match { entry ->
            entry.isDeleted && entry.modifiedAt.isAfter(existingEntry.modifiedAt)
        }) }
    }

    @Test
    fun `searchFoods should return relevant entries with FODMAP analysis`() = runTest {
        // Arrange
        val searchQuery = "dairy"
        val foodEntries = listOf(
            createTestFoodEntry(foods = listOf("Milk", "Cheese")),
            createTestFoodEntry(foods = listOf("Lactose-free Milk"))
        )
        val fodmapFood = FODMAPFood(
            id = 1L,
            name = "Milk",
            category = FoodCategory.DAIRY,
            oligosaccharides = FODMAPLevel.LOW,
            disaccharides = FODMAPLevel.HIGH,
            monosaccharides = FODMAPLevel.LOW,
            polyols = FODMAPLevel.LOW,
            overallLevel = FODMAPLevel.HIGH,
            servingSize = "250ml",
            notes = "High in lactose"
        )

        coEvery { foodEntryDao.searchByFood(searchQuery) } returns foodEntries
        coEvery { fodmapFoodDao.searchByName("Milk") } returns listOf(fodmapFood)
        coEvery { fodmapFoodDao.searchByName("Cheese") } returns emptyList()
        coEvery { fodmapFoodDao.searchByName("Lactose-free Milk") } returns emptyList()

        // Act
        val searchResults = repository.searchFoods(searchQuery).first()

        // Assert
        assertEquals("Should return search results", 2, searchResults.size)

        val milkResult = searchResults.find { it.entry.foods.contains("Milk") }
        assertNotNull("Should include milk entry", milkResult)
        assertNotNull("Should include FODMAP analysis for milk", milkResult?.fodmapAnalysis)
        assertEquals("Should have high FODMAP level",
            FODMAPLevel.HIGH, milkResult?.fodmapAnalysis?.overallLevel)

        coVerify { foodEntryDao.searchByFood(searchQuery) }
        coVerify { fodmapFoodDao.searchByName("Milk") }
    }

    @Test
    fun `validateEntry should check food combinations and portions`() = runTest {
        // Arrange
        val entryWithMismatchedPortions = createTestFoodEntry(
            foods = listOf("Apple", "Banana"),
            portions = mapOf("Apple" to "1 medium") // Missing banana portion
        )

        every { foodEntryValidator.validate(any()) } returns ValidationResult.Error(
            "All foods must have corresponding portions"
        )

        // Act
        val result = repository.validateEntry(entryWithMismatchedPortions)

        // Assert
        assertFalse("Should be invalid", result.isValid)
        assertEquals("Should return validation error",
            "All foods must have corresponding portions", result.errorMessage)

        verify { foodEntryValidator.validate(entryWithMismatchedPortions) }
    }

    @Test
    fun `validateEntry should pass for valid entry`() = runTest {
        // Arrange
        val validEntry = createTestFoodEntry()
        every { foodEntryValidator.validate(any()) } returns ValidationResult.Success

        // Act
        val result = repository.validateEntry(validEntry)

        // Assert
        assertTrue("Should be valid", result.isValid)
        assertNull("Should have no error message", result.errorMessage)
    }

    @Test
    fun `getMostFrequent should return frequently consumed foods`() = runTest {
        // Arrange
        val frequentFoods = listOf(
            createTestFoodEntry(foods = listOf("Rice")),
            createTestFoodEntry(foods = listOf("Chicken")),
            createTestFoodEntry(foods = listOf("Broccoli"))
        )

        coEvery { foodEntryDao.getMostFrequentFoods(5) } returns frequentFoods

        // Act
        val result = repository.getMostFrequent(5)

        // Assert
        assertEquals("Should return frequent foods", 3, result.size)
        assertTrue("Should contain Rice", result.any { it.foods.contains("Rice") })
        assertTrue("Should contain Chicken", result.any { it.foods.contains("Chicken") })

        coVerify { foodEntryDao.getMostFrequentFoods(5) }
    }

    @Test
    fun `getEntriesForDateRange should filter by date range`() = runTest {
        // Arrange
        val startDate = LocalDate.now().minusDays(7)
        val endDate = LocalDate.now()
        val entries = listOf(
            createTestFoodEntry(timestamp = startDate.atStartOfDay().toInstant()),
            createTestFoodEntry(timestamp = endDate.atTime(12, 0).toInstant())
        )

        coEvery { foodEntryDao.getEntriesInDateRange(startDate, endDate) } returns flowOf(entries)

        // Act
        val result = repository.getEntriesForDateRange(startDate, endDate).first()

        // Assert
        assertEquals("Should return entries in range", 2, result.size)

        coVerify { foodEntryDao.getEntriesInDateRange(startDate, endDate) }
    }

    @Test
    fun `analyzeNutritionalContent should provide nutritional breakdown`() = runTest {
        // Arrange
        val entry = createTestFoodEntry(
            foods = listOf("Brown Rice", "Grilled Chicken", "Steamed Broccoli"),
            portions = mapOf(
                "Brown Rice" to "1 cup",
                "Grilled Chicken" to "150g",
                "Steamed Broccoli" to "1 cup"
            )
        )

        // Act
        val analysis = repository.analyzeNutritionalContent(entry)

        // Assert
        assertNotNull("Should provide nutritional analysis", analysis)
        assertTrue("Should estimate calories", analysis.estimatedCalories > 0)
        assertTrue("Should identify macronutrients", analysis.macronutrients.isNotEmpty())
        assertFalse("Should identify potential allergens", analysis.potentialAllergens.isEmpty())
    }

    @Test
    fun `detectDuplicateEntries should identify similar entries in time window`() = runTest {
        // Arrange
        val baseTime = Instant.now()
        val entry1 = createTestFoodEntry(
            timestamp = baseTime,
            foods = listOf("Apple", "Banana")
        )
        val possibleDuplicate = createTestFoodEntry(
            timestamp = baseTime.plus(5, ChronoUnit.MINUTES),
            foods = listOf("Apple", "Banana")
        )

        coEvery { foodEntryDao.getEntriesInTimeWindow(
            startTime = any(),
            endTime = any()
        ) } returns listOf(possibleDuplicate)

        // Act
        val duplicates = repository.detectDuplicateEntries(entry1, timeWindowMinutes = 15)

        // Assert
        assertEquals("Should find potential duplicates", 1, duplicates.size)
        assertEquals("Should identify similar food combination",
            listOf("Apple", "Banana"), duplicates.first().foods)

        coVerify { foodEntryDao.getEntriesInTimeWindow(
            startTime = any(),
            endTime = any()
        ) }
    }

    @Test
    fun `createFromTemplate should generate entry from quick template`() = runTest {
        // Arrange
        val templateId = "template-123"
        val template = QuickEntryTemplate(
            id = 123L,
            name = "Breakfast Template",
            foods = listOf("Oatmeal", "Banana", "Almond Milk"),
            defaultPortions = mapOf(
                "Oatmeal" to "1 cup",
                "Banana" to "1 medium",
                "Almond Milk" to "250ml"
            ),
            mealType = MealType.BREAKFAST,
            isActive = true
        )

        coEvery { quickEntryTemplateDao.getById(123L) } returns template
        every { foodEntryValidator.validate(any()) } returns ValidationResult.Success
        coEvery { foodEntryDao.insert(any()) } returns 456L

        // Act
        val result = repository.createFromTemplate(templateId, MealType.BREAKFAST)

        // Assert
        assertTrue("Should create entry from template", result.isSuccess)
        assertEquals("Should return new entry ID", 456L, result.getOrNull())

        coVerify { quickEntryTemplateDao.getById(123L) }
        coVerify { foodEntryDao.insert(match { entry ->
            entry.foods == template.foods &&
            entry.mealType == MealType.BREAKFAST &&
            entry.portions == template.defaultPortions
        }) }
    }

    @Test
    fun `batchInsert should validate and insert multiple entries atomically`() = runTest {
        // Arrange
        val entries = listOf(
            createTestFoodEntry(foods = listOf("Breakfast Item")),
            createTestFoodEntry(foods = listOf("Lunch Item")),
            createTestFoodEntry(foods = listOf("Dinner Item"))
        )
        val insertedIds = listOf(1L, 2L, 3L)

        every { foodEntryValidator.validate(any()) } returns ValidationResult.Success
        coEvery { foodEntryDao.insertAll(any()) } returns insertedIds

        // Act
        val result = repository.batchInsert(entries)

        // Assert
        assertTrue("Should succeed batch insert", result.isSuccess)
        assertEquals("Should return all inserted IDs", insertedIds, result.getOrNull())

        verify(exactly = 3) { foodEntryValidator.validate(any()) }
        coVerify { foodEntryDao.insertAll(entries) }
    }

    @Test
    fun `batchInsert should fail if any entry is invalid`() = runTest {
        // Arrange
        val validEntry = createTestFoodEntry(foods = listOf("Valid Food"))
        val invalidEntry = createTestFoodEntry(foods = emptyList())
        val entries = listOf(validEntry, invalidEntry)

        every { foodEntryValidator.validate(validEntry) } returns ValidationResult.Success
        every { foodEntryValidator.validate(invalidEntry) } returns ValidationResult.Error("Empty foods list")

        // Act
        val result = repository.batchInsert(entries)

        // Assert
        assertTrue("Should fail batch insert", result.isFailure)
        assertTrue("Should contain validation error",
            result.exceptionOrNull()?.message?.contains("Empty foods list") == true)

        coVerify(exactly = 0) { foodEntryDao.insertAll(any()) }
    }

    // Test helper methods
    private fun createTestFoodEntry(
        id: Long = 0L,
        timestamp: Instant = Instant.now(),
        foods: List<String> = listOf("Test Food"),
        portions: Map<String, String> = mapOf("Test Food" to "1 serving"),
        mealType: MealType = MealType.BREAKFAST,
        isDeleted: Boolean = false
    ) = FoodEntry(
        id = id,
        timestamp = timestamp,
        mealType = mealType,
        foods = foods,
        portions = portions,
        notes = "Test notes",
        isDeleted = isDeleted
    )
}

/**
 * Supporting data classes that would be defined in the repository
 */
data class FoodSearchResult(
    val entry: FoodEntry,
    val fodmapAnalysis: FODMAPAnalysis?,
    val nutritionalInfo: NutritionalInfo?,
    val relevanceScore: Float
)

data class NutritionalInfo(
    val estimatedCalories: Int,
    val macronutrients: Map<String, Float>, // protein, carbs, fat percentages
    val potentialAllergens: List<String>,
    val fodmapRisk: FODMAPLevel
)

data class FODMAPAnalysis(
    val overallLevel: FODMAPLevel,
    val oligosaccharides: FODMAPLevel,
    val disaccharides: FODMAPLevel,
    val monosaccharides: FODMAPLevel,
    val polyols: FODMAPLevel,
    val riskFoods: List<String>
)

sealed class ValidationResult {
    object Success : ValidationResult()
    data class Error(val message: String) : ValidationResult()
}

data class EntryValidationResult(
    val isValid: Boolean,
    val errorMessage: String? = null,
    val warnings: List<String> = emptyList()
)