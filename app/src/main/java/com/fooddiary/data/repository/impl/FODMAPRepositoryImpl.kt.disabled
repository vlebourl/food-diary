package com.fooddiary.data.repository.impl

import com.fooddiary.data.database.dao.FODMAPFoodDao
import com.fooddiary.data.database.entities.FODMAPFood
import com.fooddiary.data.models.FODMAPLevel
import com.fooddiary.data.repository.FODMAPRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FODMAPRepositoryImpl @Inject constructor(
    private val fodmapFoodDao: FODMAPFoodDao
) : FODMAPRepository {

    override suspend fun insert(food: FODMAPFood): Long {
        return fodmapFoodDao.insert(food)
    }

    override suspend fun insertAll(foods: List<FODMAPFood>) {
        fodmapFoodDao.insertAll(foods)
    }

    override suspend fun update(food: FODMAPFood) {
        fodmapFoodDao.update(food)
    }

    override suspend fun delete(food: FODMAPFood) {
        fodmapFoodDao.delete(food)
    }

    override suspend fun getByName(foodName: String): FODMAPFood? {
        return fodmapFoodDao.getByName(foodName)
    }

    override fun searchByName(query: String): Flow<List<FODMAPFood>> {
        return fodmapFoodDao.searchByName("%$query%")
    }

    override fun getAll(): Flow<List<FODMAPFood>> {
        return fodmapFoodDao.getAll()
    }

    override fun getByOverallLevel(level: FODMAPLevel): Flow<List<FODMAPFood>> {
        return fodmapFoodDao.getByOverallLevel(level)
    }

    override fun getHighFODMAPFoods(): Flow<List<FODMAPFood>> {
        return fodmapFoodDao.getHighFODMAPFoods()
    }

    override fun getLowFODMAPFoods(): Flow<List<FODMAPFood>> {
        return fodmapFoodDao.getLowFODMAPFoods()
    }

    override fun getByCategory(category: String): Flow<List<FODMAPFood>> {
        return fodmapFoodDao.getByCategory(category)
    }

    override suspend fun getCategories(): List<String> {
        return fodmapFoodDao.getCategories()
    }

    override suspend fun analyzeFoodIngredients(ingredients: List<String>): FODMAPLevel {
        var highCount = 0
        var mediumCount = 0

        for (ingredient in ingredients) {
            val fodmapFood = fodmapFoodDao.getByName(ingredient)
            when (fodmapFood?.overallLevel) {
                FODMAPLevel.HIGH -> highCount++
                FODMAPLevel.MEDIUM -> mediumCount++
                else -> {} // LOW or null
            }
        }

        return when {
            highCount > 0 -> FODMAPLevel.HIGH
            mediumCount > 0 -> FODMAPLevel.MEDIUM
            else -> FODMAPLevel.LOW
        }
    }

    override suspend fun deleteAll() {
        fodmapFoodDao.deleteAll()
    }
}