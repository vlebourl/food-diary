package com.fooddiary.data.repository.impl

import com.fooddiary.data.database.dao.FoodEntryDao
import com.fooddiary.data.database.entities.FoodEntry
import com.fooddiary.data.repository.FoodEntryRepository
import kotlinx.coroutines.flow.Flow
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodEntryRepositoryImpl @Inject constructor(
    private val foodEntryDao: FoodEntryDao
) : FoodEntryRepository {

    override suspend fun insert(foodEntry: FoodEntry): Long {
        return foodEntryDao.insert(foodEntry)
    }

    override suspend fun update(foodEntry: FoodEntry) {
        foodEntryDao.update(foodEntry)
    }

    override suspend fun delete(foodEntry: FoodEntry) {
        foodEntryDao.delete(foodEntry)
    }

    override suspend fun getById(id: Long): FoodEntry? {
        return foodEntryDao.getById(id)
    }

    override fun getAll(): Flow<List<FoodEntry>> {
        return foodEntryDao.getAll()
    }

    override fun getByDateRange(startDate: Instant, endDate: Instant): Flow<List<FoodEntry>> {
        return foodEntryDao.getByDateRange(startDate, endDate)
    }

    override fun getByMealType(mealType: String): Flow<List<FoodEntry>> {
        return foodEntryDao.getByMealType(mealType)
    }

    override fun searchByFoodName(query: String): Flow<List<FoodEntry>> {
        return foodEntryDao.searchByFoodName("%$query%")
    }

    override fun getRecentEntries(limit: Int): Flow<List<FoodEntry>> {
        return foodEntryDao.getRecentEntries(limit)
    }

    override suspend fun getCountByDateRange(startDate: String, endDate: String): Flow<Int> {
        val start = Instant.parse(startDate)
        val end = Instant.parse(endDate)
        return foodEntryDao.getCountByDateRange(start, end)
    }

    override suspend fun deleteOlderThan(date: Instant): Int {
        return foodEntryDao.deleteOlderThan(date)
    }

    override suspend fun getFrequentFoods(limit: Int): List<String> {
        return foodEntryDao.getFrequentFoods(limit)
    }

    override fun getWithIngredient(ingredient: String): Flow<List<FoodEntry>> {
        return foodEntryDao.getWithIngredient("%$ingredient%")
    }

    override suspend fun deleteAll() {
        foodEntryDao.deleteAll()
    }
}