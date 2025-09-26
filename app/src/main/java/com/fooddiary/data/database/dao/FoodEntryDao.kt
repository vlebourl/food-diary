package com.fooddiary.data.database.dao

import androidx.room.*
import com.fooddiary.data.database.entities.FoodEntry
import com.fooddiary.data.models.MealType
import java.time.Instant
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodEntryDao {

    @Insert
    suspend fun insert(entry: FoodEntry): Long

    @Insert
    suspend fun insertAll(entries: List<FoodEntry>): List<Long>

    @Update
    suspend fun update(entry: FoodEntry): Int

    @Delete
    suspend fun delete(entry: FoodEntry): Int

    @Query("SELECT * FROM food_entries WHERE id = :id AND isDeleted = 0")
    suspend fun getById(id: Long): FoodEntry?

    @Query("SELECT * FROM food_entries WHERE isDeleted = 0 ORDER BY timestamp DESC")
    fun getAll(): Flow<List<FoodEntry>>

    @Query("SELECT * FROM food_entries WHERE isDeleted = 0 ORDER BY timestamp DESC")
    suspend fun getAllSync(): List<FoodEntry>

    @Query(
        """
        SELECT * FROM food_entries
        WHERE DATE(timestamp, 'unixepoch') BETWEEN :startDate AND :endDate
        AND isDeleted = 0
        ORDER BY timestamp DESC
    """,
    )
    fun getAllByDateRange(startDate: String, endDate: String): Flow<List<FoodEntry>>

    @Query("SELECT * FROM food_entries WHERE isDeleted = 0 ORDER BY timestamp DESC LIMIT :limit")
    fun getRecent(limit: Int): Flow<List<FoodEntry>>


    @Query("DELETE FROM food_entries")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM food_entries WHERE isDeleted = 0")
    suspend fun getCount(): Int

    @Query(
        """
        SELECT COUNT(*) FROM food_entries
        WHERE DATE(timestamp, 'unixepoch') = :date
        AND isDeleted = 0
    """,
    )
    suspend fun getCountByDate(date: String): Int

    @Query(
        """
        SELECT * FROM food_entries
        WHERE timestamp BETWEEN :startTime AND :endTime
        AND isDeleted = 0
        ORDER BY timestamp ASC
    """,
    )
    suspend fun getEntriesInTimeWindow(startTime: Instant, endTime: Instant): List<FoodEntry>

    @Query(
        """
        SELECT * FROM food_entries fe
        WHERE EXISTS (
            SELECT 1 FROM json_each(fe.foods)
            WHERE json_each.value = :food
        )
        AND fe.isDeleted = 0
        ORDER BY fe.timestamp DESC
    """
    )
    suspend fun searchByFood(food: String): List<FoodEntry>

    @Query(
        """
        SELECT * FROM food_entries fe
        WHERE EXISTS (
            SELECT 1 FROM json_each(fe.foods) foods
            WHERE foods.value IN (:foodsList)
        )
        AND fe.isDeleted = 0
        ORDER BY fe.timestamp DESC
    """
    )
    suspend fun searchByFoodsList(foodsList: List<String>): List<FoodEntry>

    @Query(
        """
        SELECT * FROM food_entries
        WHERE mealType = :mealType
        AND isDeleted = 0
        ORDER BY timestamp DESC
    """
    )
    suspend fun getEntriesByMealType(mealType: MealType): List<FoodEntry>

    @Query(
        """
        SELECT * FROM food_entries
        WHERE isDeleted = 0
        ORDER BY timestamp DESC
        LIMIT :limit
    """
    )
    suspend fun getRecentEntries(limit: Int): List<FoodEntry>

    @Query(
        """
        UPDATE food_entries
        SET isDeleted = 1, modifiedAt = :currentTime
        WHERE id = :id
    """
    )
    suspend fun softDelete(id: Long, currentTime: Instant): Int

    @Query(
        """
        SELECT * FROM food_entries
        WHERE timestamp >= :sinceTime
        AND isDeleted = 0
        ORDER BY timestamp DESC
    """
    )
    suspend fun getCorrelationData(sinceTime: Instant): List<FoodEntry>

    @Query(
        """
        SELECT * FROM food_entries
        WHERE DATE(timestamp, 'unixepoch') = :date
        AND isDeleted = 0
        ORDER BY timestamp DESC
    """
    )
    suspend fun getEntriesForDate(date: java.time.LocalDate): List<FoodEntry>

    @Query(
        """
        SELECT * FROM food_entries
        WHERE DATE(timestamp, 'unixepoch') BETWEEN :startDate AND :endDate
        AND isDeleted = 0
        ORDER BY timestamp DESC
    """
    )
    suspend fun getEntriesInDateRange(startDate: java.time.LocalDate, endDate: java.time.LocalDate): List<FoodEntry>

    @Query(
        """
        SELECT * FROM food_entries
        WHERE isDeleted = 0
        ORDER BY timestamp DESC
    """
    )
    suspend fun getAllPaged(): kotlinx.coroutines.flow.Flow<List<FoodEntry>>

    @Query(
        """
        SELECT fe.* FROM food_entries fe,
        (SELECT food, COUNT(*) as frequency
         FROM (SELECT value as food FROM json_each(foods) JOIN food_entries ON json_extract(foods, '$') = json_extract(fe.foods, '$'))
         GROUP BY food
         ORDER BY frequency DESC
         LIMIT :limit) frequent_foods
        WHERE fe.isDeleted = 0
        AND EXISTS (
            SELECT 1 FROM json_each(fe.foods)
            WHERE json_each.value = frequent_foods.food
        )
        ORDER BY fe.timestamp DESC
    """
    )
    suspend fun getMostFrequentFoods(limit: Int): List<FoodEntry>
}
