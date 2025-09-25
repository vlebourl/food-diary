package com.fooddiary.data.repository.impl

import com.fooddiary.data.database.dao.EnvironmentalContextDao
import com.fooddiary.data.database.entities.EnvironmentalContext
import com.fooddiary.data.repository.EnvironmentalContextRepository
import kotlinx.coroutines.flow.Flow
import java.time.Instant
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EnvironmentalContextRepositoryImpl @Inject constructor(
    private val environmentalContextDao: EnvironmentalContextDao
) : EnvironmentalContextRepository {

    override suspend fun insert(context: EnvironmentalContext): Long {
        return environmentalContextDao.insert(context)
    }

    override suspend fun update(context: EnvironmentalContext) {
        environmentalContextDao.update(context)
    }

    override suspend fun delete(context: EnvironmentalContext) {
        environmentalContextDao.delete(context)
    }

    override suspend fun getById(id: Long): EnvironmentalContext? {
        return environmentalContextDao.getById(id)
    }

    override suspend fun getByDate(date: LocalDate): EnvironmentalContext? {
        return environmentalContextDao.getByDate(date)
    }

    override fun getAll(): Flow<List<EnvironmentalContext>> {
        return environmentalContextDao.getAll()
    }

    override fun getByDateRange(startDate: LocalDate, endDate: LocalDate): Flow<List<EnvironmentalContext>> {
        return environmentalContextDao.getByDateRange(startDate, endDate)
    }

    override fun getHighStressDays(minStressLevel: Int): Flow<List<EnvironmentalContext>> {
        return environmentalContextDao.getHighStressDays(minStressLevel)
    }

    override fun getPoorSleepDays(maxSleepHours: Double): Flow<List<EnvironmentalContext>> {
        return environmentalContextDao.getPoorSleepDays(maxSleepHours)
    }

    override fun getHighExerciseDays(minExerciseMinutes: Int): Flow<List<EnvironmentalContext>> {
        return environmentalContextDao.getHighExerciseDays(minExerciseMinutes)
    }

    override suspend fun getAverageStressLevel(): Double? {
        return environmentalContextDao.getAverageStressLevel()
    }

    override suspend fun getAverageSleepHours(): Double? {
        return environmentalContextDao.getAverageSleepHours()
    }

    override suspend fun getAverageExerciseMinutes(): Double? {
        return environmentalContextDao.getAverageExerciseMinutes()
    }

    override fun getMenstruationDays(): Flow<List<EnvironmentalContext>> {
        return environmentalContextDao.getMenstruationDays()
    }

    override fun getTravelDays(): Flow<List<EnvironmentalContext>> {
        return environmentalContextDao.getTravelDays()
    }

    override suspend fun deleteOlderThan(date: LocalDate): Int {
        return environmentalContextDao.deleteOlderThan(date)
    }

    override suspend fun deleteAll() {
        environmentalContextDao.deleteAll()
    }
}