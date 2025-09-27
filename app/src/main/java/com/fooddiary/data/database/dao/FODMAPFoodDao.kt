package com.fooddiary.data.database.dao

import androidx.room.*
import com.fooddiary.data.database.entities.FODMAPFood
import com.fooddiary.data.models.FODMAPLevel
import com.fooddiary.data.models.FoodCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface FODMAPFoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(food: FODMAPFood): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(foods: List<FODMAPFood>): List<Long>

    @Update
    suspend fun update(food: FODMAPFood): Int

    @Delete
    suspend fun delete(food: FODMAPFood): Int

    @Query("SELECT * FROM fodmap_foods WHERE id = :id")
    suspend fun getById(id: String): FODMAPFood?

    @Query("SELECT * FROM fodmap_foods ORDER BY name ASC")
    suspend fun getAll(): List<FODMAPFood>

    @Query("SELECT * FROM fodmap_foods ORDER BY name ASC")
    fun getAllFlow(): Flow<List<FODMAPFood>>

    @Query(
        """
        SELECT * FROM fodmap_foods
        WHERE name LIKE '%' || :name || '%'
        ORDER BY name ASC
    """
    )
    suspend fun searchByName(name: String): List<FODMAPFood>

    @Query(
        """
        SELECT * FROM fodmap_foods
        WHERE overallLevel = :level
        ORDER BY name ASC
    """
    )
    suspend fun getByFODMAPLevel(level: FODMAPLevel): List<FODMAPFood>

    @Query(
        """
        SELECT * FROM fodmap_foods
        WHERE category = :category
        ORDER BY name ASC
    """
    )
    suspend fun getByCategory(category: FoodCategory): List<FODMAPFood>

    @Query(
        """
        SELECT * FROM fodmap_foods
        WHERE overallLevel = 'HIGH'
        ORDER BY name ASC
    """
    )
    suspend fun getHighFODMAPFoods(): List<FODMAPFood>

    @Query(
        """
        SELECT * FROM fodmap_foods
        WHERE overallLevel = 'LOW'
        ORDER BY name ASC
    """
    )
    suspend fun getLowFODMAPFoods(): List<FODMAPFood>

    @Query(
        """
        SELECT * FROM fodmap_foods
        WHERE name LIKE '%' || :query || '%'
        OR EXISTS (
            SELECT 1 FROM json_each(aliases)
            WHERE json_each.value LIKE '%' || :query || '%'
        )
        ORDER BY
            CASE WHEN name LIKE :query || '%' THEN 1
                 WHEN name LIKE '%' || :query || '%' THEN 2
                 ELSE 3
            END,
            name ASC
    """
    )
    suspend fun searchFoods(query: String): List<FODMAPFood>

    @Query(
        """
        SELECT * FROM fodmap_foods
        WHERE (oligosaccharides = :level OR disaccharides = :level OR monosaccharides = :level OR polyols = :level)
        ORDER BY name ASC
    """
    )
    suspend fun getBySpecificFODMAPComponent(level: FODMAPLevel): List<FODMAPFood>

    @Query(
        """
        SELECT COUNT(*) FROM fodmap_foods
        WHERE overallLevel = :level
    """
    )
    suspend fun getCountByLevel(level: FODMAPLevel): Int

    @Query("DELETE FROM fodmap_foods")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM fodmap_foods")
    suspend fun getCount(): Int

    /**
     * Initialize database with default FODMAP foods
     */
    suspend fun initializeWithDefaults() {
        insertAll(FODMAPFood.getAllFODMAPFoods())
    }
}