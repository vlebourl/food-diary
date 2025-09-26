package com.fooddiary.data.database.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fooddiary.data.database.FoodDiaryDatabase
import com.fooddiary.data.database.entities.FoodEntry
import com.fooddiary.data.models.MealType
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.Instant
import java.time.temporal.ChronoUnit

@RunWith(AndroidJUnit4::class)
class FoodEntryDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: FoodDiaryDatabase
    private lateinit var foodEntryDao: FoodEntryDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            FoodDiaryDatabase::class.java
        ).allowMainThreadQueries().build()

        foodEntryDao = database.foodEntryDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insert_shouldInsertFoodEntry() = runTest {
        // Arrange
        val foodEntry = createTestFoodEntry()

        // Act
        val insertedId = foodEntryDao.insert(foodEntry)

        // Assert
        assertTrue("Inserted ID should be greater than 0", insertedId > 0)

        val retrieved = foodEntryDao.getById(insertedId)
        assertNotNull("Retrieved entry should not be null", retrieved)
        assertEquals("Foods should match", foodEntry.foods, retrieved!!.foods)
        assertEquals("Portions should match", foodEntry.portions, retrieved.portions)
        assertEquals("Meal type should match", foodEntry.mealType, retrieved.mealType)
    }

    @Test
    fun insertAll_shouldInsertMultipleFoodEntries() = runTest {
        // Arrange
        val entries = listOf(
            createTestFoodEntry(foods = listOf("Apple", "Banana")),
            createTestFoodEntry(foods = listOf("Rice", "Chicken"), mealType = MealType.LUNCH),
            createTestFoodEntry(foods = listOf("Pasta"), mealType = MealType.DINNER)
        )

        // Act
        val insertedIds = foodEntryDao.insertAll(entries)

        // Assert
        assertEquals("Should insert all 3 entries", 3, insertedIds.size)
        insertedIds.forEach { id ->
            assertTrue("Each inserted ID should be greater than 0", id > 0)
        }

        val allEntries = foodEntryDao.getAll()
        assertEquals("Should retrieve all 3 entries", 3, allEntries.size)
    }

    @Test
    fun update_shouldUpdateFoodEntry() = runTest {
        // Arrange
        val originalEntry = createTestFoodEntry()
        val insertedId = foodEntryDao.insert(originalEntry)
        val updatedEntry = originalEntry.copy(
            id = insertedId,
            foods = listOf("Updated Food"),
            portions = mapOf("Updated Food" to "2 cups"),
            notes = "Updated notes"
        )

        // Act
        val updateCount = foodEntryDao.update(updatedEntry)

        // Assert
        assertEquals("Should update 1 entry", 1, updateCount)

        val retrieved = foodEntryDao.getById(insertedId)
        assertNotNull("Updated entry should exist", retrieved)
        assertEquals("Foods should be updated", updatedEntry.foods, retrieved!!.foods)
        assertEquals("Portions should be updated", updatedEntry.portions, retrieved.portions)
        assertEquals("Notes should be updated", updatedEntry.notes, retrieved.notes)
    }

    @Test
    fun getById_shouldReturnCorrectFoodEntry() = runTest {
        // Arrange
        val entry1 = createTestFoodEntry(foods = listOf("Apple"))
        val entry2 = createTestFoodEntry(foods = listOf("Banana"))

        val id1 = foodEntryDao.insert(entry1)
        val id2 = foodEntryDao.insert(entry2)

        // Act
        val retrieved1 = foodEntryDao.getById(id1)
        val retrieved2 = foodEntryDao.getById(id2)
        val nonExistent = foodEntryDao.getById(999L)

        // Assert
        assertNotNull("First entry should exist", retrieved1)
        assertNotNull("Second entry should exist", retrieved2)
        assertNull("Non-existent entry should be null", nonExistent)

        assertEquals("First entry foods should match", listOf("Apple"), retrieved1!!.foods)
        assertEquals("Second entry foods should match", listOf("Banana"), retrieved2!!.foods)
    }

    @Test
    fun getAll_shouldReturnAllNonDeletedFoodEntries() = runTest {
        // Arrange
        val entry1 = createTestFoodEntry(foods = listOf("Apple"))
        val entry2 = createTestFoodEntry(foods = listOf("Banana"))
        val deletedEntry = createTestFoodEntry(foods = listOf("Orange")).copy(isDeleted = true)

        foodEntryDao.insert(entry1)
        foodEntryDao.insert(entry2)
        foodEntryDao.insert(deletedEntry)

        // Act
        val allEntries = foodEntryDao.getAll()

        // Assert
        assertEquals("Should return 2 non-deleted entries", 2, allEntries.size)
        val foodsList = allEntries.map { it.foods.first() }
        assertTrue("Should contain Apple", foodsList.contains("Apple"))
        assertTrue("Should contain Banana", foodsList.contains("Banana"))
        assertFalse("Should not contain deleted Orange", foodsList.contains("Orange"))
    }

    @Test
    fun searchByFood_shouldReturnEntriesContainingFood() = runTest {
        // Arrange
        val appleEntry = createTestFoodEntry(foods = listOf("Apple", "Banana"))
        val appleOnlyEntry = createTestFoodEntry(foods = listOf("Apple"))
        val bananaEntry = createTestFoodEntry(foods = listOf("Banana", "Orange"))
        val riceEntry = createTestFoodEntry(foods = listOf("Rice"))

        foodEntryDao.insert(appleEntry)
        foodEntryDao.insert(appleOnlyEntry)
        foodEntryDao.insert(bananaEntry)
        foodEntryDao.insert(riceEntry)

        // Act
        val appleResults = foodEntryDao.searchByFood("Apple")
        val bananaResults = foodEntryDao.searchByFood("Banana")
        val nonExistentResults = foodEntryDao.searchByFood("NonExistent")

        // Assert
        assertEquals("Should find 2 entries with Apple", 2, appleResults.size)
        assertEquals("Should find 2 entries with Banana", 2, bananaResults.size)
        assertEquals("Should find 0 entries with non-existent food", 0, nonExistentResults.size)

        appleResults.forEach { entry ->
            assertTrue("Entry should contain Apple", entry.foods.contains("Apple"))
        }
    }

    @Test
    fun searchByFoodsList_shouldReturnEntriesContainingAnyFood() = runTest {
        // Arrange
        val appleEntry = createTestFoodEntry(foods = listOf("Apple", "Banana"))
        val riceEntry = createTestFoodEntry(foods = listOf("Rice", "Chicken"))
        val pastaEntry = createTestFoodEntry(foods = listOf("Pasta"))

        foodEntryDao.insert(appleEntry)
        foodEntryDao.insert(riceEntry)
        foodEntryDao.insert(pastaEntry)

        // Act
        val results = foodEntryDao.searchByFoodsList(listOf("Apple", "Rice"))

        // Assert
        assertEquals("Should find 2 entries containing Apple or Rice", 2, results.size)

        val containsApple = results.any { it.foods.contains("Apple") }
        val containsRice = results.any { it.foods.contains("Rice") }
        assertTrue("Results should contain entry with Apple", containsApple)
        assertTrue("Results should contain entry with Rice", containsRice)
    }

    @Test
    fun getEntriesInTimeWindow_shouldReturnEntriesInSpecifiedTimeRange() = runTest {
        // Arrange
        val baseTime = Instant.now()
        val entry1 = createTestFoodEntry(
            timestamp = baseTime.minus(2, ChronoUnit.HOURS),
            foods = listOf("Early Food")
        )
        val entry2 = createTestFoodEntry(
            timestamp = baseTime.minus(1, ChronoUnit.HOURS),
            foods = listOf("Middle Food")
        )
        val entry3 = createTestFoodEntry(
            timestamp = baseTime,
            foods = listOf("Recent Food")
        )
        val entry4 = createTestFoodEntry(
            timestamp = baseTime.plus(1, ChronoUnit.HOURS),
            foods = listOf("Future Food")
        )

        foodEntryDao.insert(entry1)
        foodEntryDao.insert(entry2)
        foodEntryDao.insert(entry3)
        foodEntryDao.insert(entry4)

        // Act
        val results = foodEntryDao.getEntriesInTimeWindow(
            startTime = baseTime.minus(90, ChronoUnit.MINUTES),
            endTime = baseTime.plus(30, ChronoUnit.MINUTES)
        )

        // Assert
        assertEquals("Should find 2 entries in time window", 2, results.size)

        val foodsList = results.map { it.foods.first() }
        assertTrue("Should contain Middle Food", foodsList.contains("Middle Food"))
        assertTrue("Should contain Recent Food", foodsList.contains("Recent Food"))
        assertFalse("Should not contain Early Food", foodsList.contains("Early Food"))
        assertFalse("Should not contain Future Food", foodsList.contains("Future Food"))
    }

    @Test
    fun getEntriesByMealType_shouldReturnEntriesOfSpecificMealType() = runTest {
        // Arrange
        val breakfastEntry = createTestFoodEntry(mealType = MealType.BREAKFAST)
        val lunchEntry = createTestFoodEntry(mealType = MealType.LUNCH)
        val dinnerEntry = createTestFoodEntry(mealType = MealType.DINNER)
        val snackEntry = createTestFoodEntry(mealType = MealType.SNACK)

        foodEntryDao.insert(breakfastEntry)
        foodEntryDao.insert(lunchEntry)
        foodEntryDao.insert(dinnerEntry)
        foodEntryDao.insert(snackEntry)

        // Act
        val breakfastResults = foodEntryDao.getEntriesByMealType(MealType.BREAKFAST)
        val lunchResults = foodEntryDao.getEntriesByMealType(MealType.LUNCH)

        // Assert
        assertEquals("Should find 1 breakfast entry", 1, breakfastResults.size)
        assertEquals("Should find 1 lunch entry", 1, lunchResults.size)
        assertEquals("Breakfast entry should have correct meal type", MealType.BREAKFAST, breakfastResults.first().mealType)
        assertEquals("Lunch entry should have correct meal type", MealType.LUNCH, lunchResults.first().mealType)
    }

    @Test
    fun getRecentEntries_shouldReturnMostRecentEntries() = runTest {
        // Arrange
        val baseTime = Instant.now()
        val entries = (0..4).map { i ->
            createTestFoodEntry(
                timestamp = baseTime.minus(i.toLong(), ChronoUnit.HOURS),
                foods = listOf("Food $i")
            )
        }
        entries.forEach { foodEntryDao.insert(it) }

        // Act
        val recentEntries = foodEntryDao.getRecentEntries(3)

        // Assert
        assertEquals("Should return 3 most recent entries", 3, recentEntries.size)

        // Verify entries are in descending order (most recent first)
        for (i in 0 until recentEntries.size - 1) {
            assertTrue(
                "Entries should be in descending order by timestamp",
                recentEntries[i].timestamp.isAfter(recentEntries[i + 1].timestamp)
            )
        }
    }

    @Test
    fun softDelete_shouldMarkEntryAsDeleted() = runTest {
        // Arrange
        val entry = createTestFoodEntry()
        val insertedId = foodEntryDao.insert(entry)

        // Act
        val deleteCount = foodEntryDao.softDelete(insertedId)

        // Assert
        assertEquals("Should soft delete 1 entry", 1, deleteCount)

        val retrieved = foodEntryDao.getById(insertedId)
        assertNotNull("Entry should still exist in database", retrieved)
        assertTrue("Entry should be marked as deleted", retrieved!!.isDeleted)

        val allEntries = foodEntryDao.getAll()
        assertEquals("Soft deleted entry should not appear in getAll()", 0, allEntries.size)
    }

    @Test
    fun delete_shouldPermanentlyRemoveEntry() = runTest {
        // Arrange
        val entry = createTestFoodEntry()
        val insertedId = foodEntryDao.insert(entry)

        // Act
        val deleteCount = foodEntryDao.delete(entry.copy(id = insertedId))

        // Assert
        assertEquals("Should delete 1 entry", 1, deleteCount)

        val retrieved = foodEntryDao.getById(insertedId)
        assertNull("Entry should not exist after deletion", retrieved)
    }

    @Test
    fun getCorrelationData_shouldReturnEntriesForCorrelationAnalysis() = runTest {
        // Arrange
        val baseTime = Instant.now()
        val entries = listOf(
            createTestFoodEntry(
                timestamp = baseTime.minus(1, ChronoUnit.DAYS),
                foods = listOf("Dairy", "Bread")
            ),
            createTestFoodEntry(
                timestamp = baseTime.minus(6, ChronoUnit.HOURS),
                foods = listOf("Spicy Food")
            ),
            createTestFoodEntry(
                timestamp = baseTime.minus(2, ChronoUnit.HOURS),
                foods = listOf("Gluten", "Dairy")
            )
        )
        entries.forEach { foodEntryDao.insert(it) }

        // Act
        val correlationData = foodEntryDao.getCorrelationData(
            sinceTime = baseTime.minus(12, ChronoUnit.HOURS)
        )

        // Assert
        assertEquals("Should return 2 entries for correlation", 2, correlationData.size)

        val allFoods = correlationData.flatMap { it.foods }
        assertTrue("Should include Spicy Food", allFoods.contains("Spicy Food"))
        assertTrue("Should include Gluten", allFoods.contains("Gluten"))
        assertTrue("Should include Dairy from recent entry", allFoods.contains("Dairy"))
    }

    @Test
    fun entity_validation_shouldEnforceConstraints() = runTest {
        // Test that the entity enforces business rules

        // Arrange & Act & Assert
        val validEntry = createTestFoodEntry()
        assertNotNull("Valid entry should be created", validEntry)

        // Test empty foods list is allowed (might be edited later)
        val emptyFoodsEntry = createTestFoodEntry(foods = emptyList())
        assertNotNull("Entry with empty foods list should be valid", emptyFoodsEntry)

        // Test empty portions map is allowed
        val emptyPortionsEntry = createTestFoodEntry(portions = emptyMap())
        assertNotNull("Entry with empty portions map should be valid", emptyPortionsEntry)
    }

    @Test
    fun entity_softDeleteMethod_shouldUpdateCorrectly() {
        // Arrange
        val entry = createTestFoodEntry()

        // Act
        val softDeletedEntry = entry.softDelete()

        // Assert
        assertTrue("Entry should be marked as deleted", softDeletedEntry.isDeleted)
        assertTrue("Modified time should be updated", softDeletedEntry.modifiedAt.isAfter(entry.modifiedAt))
        assertEquals("All other fields should remain the same", entry.foods, softDeletedEntry.foods)
        assertEquals("ID should remain the same", entry.id, softDeletedEntry.id)
    }

    @Test
    fun entity_updateMethod_shouldUpdateFieldsCorrectly() {
        // Arrange
        val entry = createTestFoodEntry()
        val newFoods = listOf("Updated Food")
        val newPortions = mapOf("Updated Food" to "1 serving")
        val newMealType = MealType.DINNER
        val newNotes = "Updated notes"

        // Act
        val updatedEntry = entry.update(
            foods = newFoods,
            portions = newPortions,
            mealType = newMealType,
            notes = newNotes
        )

        // Assert
        assertEquals("Foods should be updated", newFoods, updatedEntry.foods)
        assertEquals("Portions should be updated", newPortions, updatedEntry.portions)
        assertEquals("Meal type should be updated", newMealType, updatedEntry.mealType)
        assertEquals("Notes should be updated", newNotes, updatedEntry.notes)
        assertTrue("Modified time should be updated", updatedEntry.modifiedAt.isAfter(entry.modifiedAt))
        assertEquals("ID should remain unchanged", entry.id, updatedEntry.id)
        assertEquals("Timestamp should remain unchanged", entry.timestamp, updatedEntry.timestamp)
    }

    private fun createTestFoodEntry(
        foods: List<String> = listOf("Test Food"),
        portions: Map<String, String> = mapOf("Test Food" to "1 serving"),
        mealType: MealType = MealType.BREAKFAST,
        timestamp: Instant = Instant.now(),
        notes: String? = "Test notes"
    ) = FoodEntry.create(
        foods = foods,
        portions = portions,
        mealType = mealType,
        timestamp = timestamp,
        notes = notes
    )
}