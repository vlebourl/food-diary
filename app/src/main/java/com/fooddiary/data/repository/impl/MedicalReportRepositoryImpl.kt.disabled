package com.fooddiary.data.repository.impl

import com.fooddiary.data.database.dao.MedicalReportDao
import com.fooddiary.data.database.entities.MedicalReport
import com.fooddiary.data.repository.MedicalReportRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MedicalReportRepositoryImpl @Inject constructor(
    private val medicalReportDao: MedicalReportDao
) : MedicalReportRepository {

    override suspend fun insert(report: MedicalReport): Long {
        return medicalReportDao.insert(report)
    }

    override suspend fun update(report: MedicalReport) {
        medicalReportDao.update(report)
    }

    override suspend fun delete(report: MedicalReport) {
        medicalReportDao.delete(report)
    }

    override suspend fun getById(id: Long): MedicalReport? {
        return medicalReportDao.getById(id)
    }

    override fun getAll(): Flow<List<MedicalReport>> {
        return medicalReportDao.getAll()
    }

    override fun getByDateRange(startDate: LocalDate, endDate: LocalDate): Flow<List<MedicalReport>> {
        return medicalReportDao.getByDateRange(startDate, endDate)
    }

    override fun getRecentReports(limit: Int): Flow<List<MedicalReport>> {
        return medicalReportDao.getRecentReports(limit)
    }

    override suspend fun markAsSent(reportId: Long, sentDate: LocalDate) {
        medicalReportDao.markAsSent(reportId, sentDate)
    }

    override suspend fun updateExportPath(reportId: Long, exportPath: String) {
        medicalReportDao.updateExportPath(reportId, exportPath)
    }

    override suspend fun deleteOlderThan(date: LocalDate): Int {
        return medicalReportDao.deleteOlderThan(date)
    }

    override suspend fun deleteAll() {
        medicalReportDao.deleteAll()
    }
}