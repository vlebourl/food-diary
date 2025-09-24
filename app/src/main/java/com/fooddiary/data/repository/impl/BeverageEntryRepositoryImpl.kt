package com.fooddiary.data.repository.impl

import com.fooddiary.data.database.dao.BeverageEntryDao
import com.fooddiary.data.database.entities.BeverageEntry
import com.fooddiary.data.repository.BeverageEntryRepository
import kotlinx.coroutines.flow.Flow
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BeverageEntryRepositoryImpl @Inject constructor(
    private val beverageEntryDao: BeverageEntryDao
) : BeverageEntryRepository {

    override suspend fun insert(beverageEntry: BeverageEntry): Long {
        return beverageEntryDao.insert(beverageEntry)
    }

    override suspend fun update(beverageEntry: BeverageEntry) {
        beverageEntryDao.update(beverageEntry)
    }

    override suspend fun delete(beverageEntry: BeverageEntry) {
        beverageEntryDao.delete(beverageEntry)
    }

    override suspend fun getById(id: Long): BeverageEntry? {
        return beverageEntryDao.getById(id)
    }

    override fun getAll(): Flow<List<BeverageEntry>> {
        return beverageEntryDao.getAll()
    }

    override fun getByDateRange(startDate: Instant, endDate: Instant): Flow<List<BeverageEntry>> {
        return beverageEntryDao.getByDateRange(startDate, endDate)
    }

    override fun getByCaffeineContent(minCaffeine: Double, maxCaffeine: Double): Flow<List<BeverageEntry>> {
        return beverageEntryDao.getByCaffeineContent(minCaffeine, maxCaffeine)
    }

    override fun getAlcoholicBeverages(): Flow<List<BeverageEntry>> {
        return beverageEntryDao.getAlcoholicBeverages()
    }

    override fun searchByBeverageName(query: String): Flow<List<BeverageEntry>> {
        return beverageEntryDao.searchByBeverageName("%$query%")
    }

    override suspend fun getTotalCaffeineByDate(date: Instant): Double {
        return beverageEntryDao.getTotalCaffeineByDate(date) ?: 0.0
    }

    override suspend fun getTotalAlcoholByDate(date: Instant): Double {
        return beverageEntryDao.getTotalAlcoholByDate(date) ?: 0.0
    }

    override fun getRecentEntries(limit: Int): Flow<List<BeverageEntry>> {
        return beverageEntryDao.getRecentEntries(limit)
    }

    override suspend fun deleteOlderThan(date: Instant): Int {
        return beverageEntryDao.deleteOlderThan(date)
    }

    override suspend fun getFrequentBeverages(limit: Int): List<String> {
        return beverageEntryDao.getFrequentBeverages(limit)
    }

    override suspend fun deleteAll() {
        beverageEntryDao.deleteAll()
    }
}