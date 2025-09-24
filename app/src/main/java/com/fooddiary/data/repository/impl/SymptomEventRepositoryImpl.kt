package com.fooddiary.data.repository.impl

import com.fooddiary.data.database.dao.SymptomEventDao
import com.fooddiary.data.database.entities.SymptomEvent
import com.fooddiary.data.models.SymptomType
import com.fooddiary.data.repository.SymptomEventRepository
import kotlinx.coroutines.flow.Flow
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SymptomEventRepositoryImpl @Inject constructor(
    private val symptomEventDao: SymptomEventDao
) : SymptomEventRepository {

    override suspend fun insert(symptomEvent: SymptomEvent): Long {
        return symptomEventDao.insert(symptomEvent)
    }

    override suspend fun update(symptomEvent: SymptomEvent) {
        symptomEventDao.update(symptomEvent)
    }

    override suspend fun delete(symptomEvent: SymptomEvent) {
        symptomEventDao.delete(symptomEvent)
    }

    override suspend fun getById(id: Long): SymptomEvent? {
        return symptomEventDao.getById(id)
    }

    override fun getAll(): Flow<List<SymptomEvent>> {
        return symptomEventDao.getAll()
    }

    override fun getByDateRange(startDate: Instant, endDate: Instant): Flow<List<SymptomEvent>> {
        return symptomEventDao.getByDateRange(startDate, endDate)
    }

    override fun getBySymptomType(symptomType: SymptomType): Flow<List<SymptomEvent>> {
        return symptomEventDao.getBySymptomType(symptomType)
    }

    override fun getBySeverityRange(minSeverity: Int, maxSeverity: Int): Flow<List<SymptomEvent>> {
        return symptomEventDao.getBySeverityRange(minSeverity, maxSeverity)
    }

    override fun getWithinTimeWindowOfFood(foodEntryId: Long, windowMinutes: Int): Flow<List<SymptomEvent>> {
        return symptomEventDao.getWithinTimeWindowOfFood(foodEntryId, windowMinutes)
    }

    override fun getRecentSymptoms(limit: Int): Flow<List<SymptomEvent>> {
        return symptomEventDao.getRecentSymptoms(limit)
    }

    override suspend fun getAverageSeverityByType(symptomType: SymptomType): Double? {
        return symptomEventDao.getAverageSeverityByType(symptomType)
    }

    override fun getSymptomFrequency(): Flow<Map<SymptomType, Int>> {
        return symptomEventDao.getSymptomFrequency()
    }

    override suspend fun getCountByDateRange(startDate: String, endDate: String): Flow<Int> {
        val start = Instant.parse(startDate)
        val end = Instant.parse(endDate)
        return symptomEventDao.getCountByDateRange(start, end)
    }

    override suspend fun deleteOlderThan(date: Instant): Int {
        return symptomEventDao.deleteOlderThan(date)
    }

    override fun getBristolStoolEvents(): Flow<List<SymptomEvent>> {
        return symptomEventDao.getBristolStoolEvents()
    }

    override suspend fun getMostFrequentSymptom(): SymptomType? {
        return symptomEventDao.getMostFrequentSymptom()
    }

    override suspend fun deleteAll() {
        symptomEventDao.deleteAll()
    }
}