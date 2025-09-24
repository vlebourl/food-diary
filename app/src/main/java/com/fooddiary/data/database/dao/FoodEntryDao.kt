package com.fooddiary.data.database.dao

import androidx.room.*
import com.fooddiary.data.database.entities.FoodEntry
import kotlinx.coroutines.flow.Flow
import java.time.Instant
import java.time.LocalDate

@Dao
interface FoodEntryDao {

    @Insert
    suspend fun insert(entry: FoodEntry): Long

    @Update
    suspend fun update(entry: FoodEntry)

    @Query("SELECT * FROM food_entries WHERE id = :id AND isDeleted = 0")
    suspend fun getById(id: String): FoodEntry?

    @Query("SELECT * FROM food_entries WHERE isDeleted = 0 ORDER BY timestamp DESC")
    fun getAll(): Flow<List<FoodEntry>>

    @Query("""
        SELECT * FROM food_entries
        WHERE DATE(timestamp, 'unixepoch') BETWEEN :startDate AND :endDate
        AND isDeleted = 0
        ORDER BY timestamp DESC
    """)
    fun getAllByDateRange(startDate: String, endDate: String): Flow<List<FoodEntry>>

    @Query("SELECT * FROM food_entries WHERE isDeleted = 0 ORDER BY timestamp DESC LIMIT :limit")
    fun getRecent(limit: Int): Flow<List<FoodEntry>>

    @Query("""
        SELECT * FROM food_entries
        WHERE name LIKE '%' || :query || '%'
        AND isDeleted = 0
        ORDER BY timestamp DESC
    """)
    fun searchByName(query: String): Flow<List<FoodEntry>>

    @Query("""
        SELECT * FROM food_entries
        WHERE isDeleted = 0
        GROUP BY name
        ORDER BY COUNT(*) DESC
        LIMIT :limit
    """)
    suspend fun getMostFrequent(limit: Int): List<FoodEntry>

    @Query("""
        SELECT * FROM food_entries
        WHERE ingredients LIKE '%' || :ingredient || '%'
        AND isDeleted = 0
        ORDER BY timestamp DESC
    """)
    fun searchByIngredient(ingredient: String): Flow<List<FoodEntry>>

    @Query("""
        SELECT DISTINCT name FROM food_entries
        WHERE isDeleted = 0
        ORDER BY name ASC
    """)
    suspend fun getAllFoodNames(): List<String>

    @Query("""
        SELECT DISTINCT ingredients FROM food_entries
        WHERE isDeleted = 0 AND ingredients IS NOT NULL
    """)
    suspend fun getAllIngredients(): List<String>

    @Query("""
        UPDATE food_entries
        SET isDeleted = 1, deletedAt = :deletedAt, modifiedAt = :modifiedAt
        WHERE id = :id
    """)
    suspend fun softDelete(id: String, deletedAt: Instant, modifiedAt: Instant)

    @Query("DELETE FROM food_entries WHERE id = :id")
    suspend fun hardDelete(id: String)

    @Query("DELETE FROM food_entries")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM food_entries WHERE isDeleted = 0")
    suspend fun getCount(): Int

    @Query("""
        SELECT COUNT(*) FROM food_entries
        WHERE DATE(timestamp, 'unixepoch') = :date
        AND isDeleted = 0
    """)
    suspend fun getCountByDate(date: String): Int

    @Query("""
        SELECT * FROM food_entries
        WHERE timestamp BETWEEN :startTime AND :endTime
        AND isDeleted = 0
        ORDER BY timestamp ASC
    """)
    suspend fun getEntriesInTimeWindow(startTime: Instant, endTime: Instant): List<FoodEntry>
}