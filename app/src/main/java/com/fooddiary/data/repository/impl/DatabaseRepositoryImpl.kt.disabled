package com.fooddiary.data.repository.impl

import android.content.Context
import com.fooddiary.data.database.FoodDiaryDatabase
import com.fooddiary.data.repository.DatabaseRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val database: FoodDiaryDatabase
) : DatabaseRepository {

    override suspend fun getTotalFoodEntries(): Int = withContext(Dispatchers.IO) {
        database.foodEntryDao().getCount()
    }

    override suspend fun getTotalSymptomEvents(): Int = withContext(Dispatchers.IO) {
        database.symptomEventDao().getCount()
    }

    override suspend fun getDatabaseSize(): Double = withContext(Dispatchers.IO) {
        val dbPath = context.getDatabasePath(database.openHelper.databaseName)
        if (dbPath.exists()) {
            dbPath.length().toDouble() / (1024 * 1024) // Convert to MB
        } else {
            0.0
        }
    }

    override suspend fun getOldestEntryDate(): String? = withContext(Dispatchers.IO) {
        database.foodEntryDao().getOldestEntryDate()?.toString()
    }

    override suspend fun exportAllData(): ExportResult = withContext(Dispatchers.IO) {
        try {
            val exportDir = File(context.getExternalFilesDir(null), "exports")
            if (!exportDir.exists()) {
                exportDir.mkdirs()
            }

            val exportFile = File(exportDir, "food_diary_export_${System.currentTimeMillis()}.json")

            // TODO: Implement actual export logic
            // This would serialize all database tables to JSON

            ExportResult(
                filePath = exportFile.absolutePath,
                success = true
            )
        } catch (e: Exception) {
            ExportResult(
                filePath = "",
                success = false
            )
        }
    }

    override suspend fun importData(filePath: String): ImportResult = withContext(Dispatchers.IO) {
        try {
            // TODO: Implement actual import logic
            // This would parse JSON and insert into database

            ImportResult(
                entriesCount = 0,
                success = true
            )
        } catch (e: Exception) {
            ImportResult(
                entriesCount = 0,
                success = false
            )
        }
    }

    override suspend fun clearAllData() = withContext(Dispatchers.IO) {
        database.clearAllTables()
    }

    override suspend fun optimizeDatabase(): OptimizationResult = withContext(Dispatchers.IO) {
        try {
            val sizeBefore = getDatabaseSize()

            // Run VACUUM command to optimize database
            database.compileStatement("VACUUM").execute()

            val sizeAfter = getDatabaseSize()
            val spaceSaved = sizeBefore - sizeAfter

            OptimizationResult(
                spaceSavedMB = if (spaceSaved > 0) spaceSaved else 0.0,
                success = true
            )
        } catch (e: Exception) {
            OptimizationResult(
                spaceSavedMB = 0.0,
                success = false
            )
        }
    }

    override suspend fun cleanupDataOlderThan(days: Int): CleanupResult = withContext(Dispatchers.IO) {
        if (days < 0) {
            // Keep forever
            return@withContext CleanupResult(0, true)
        }

        try {
            val cutoffDate = LocalDate.now().minusDays(days.toLong())
            var deletedCount = 0

            // Delete old food entries
            deletedCount += database.foodEntryDao().deleteOlderThan(
                java.time.Instant.from(cutoffDate.atStartOfDay(java.time.ZoneId.systemDefault()))
            )

            // Delete old symptom events
            deletedCount += database.symptomEventDao().deleteOlderThan(
                java.time.Instant.from(cutoffDate.atStartOfDay(java.time.ZoneId.systemDefault()))
            )

            // Delete old environmental contexts
            deletedCount += database.environmentalContextDao().deleteOlderThan(cutoffDate)

            CleanupResult(
                deletedEntries = deletedCount,
                success = true
            )
        } catch (e: Exception) {
            CleanupResult(
                deletedEntries = 0,
                success = false
            )
        }
    }

    override fun getDatabaseVersion(): Int {
        return FoodDiaryDatabase.DATABASE_VERSION
    }
}

data class ExportResult(
    val filePath: String,
    val success: Boolean
)

data class ImportResult(
    val entriesCount: Int,
    val success: Boolean
)

data class OptimizationResult(
    val spaceSavedMB: Double,
    val success: Boolean
)

data class CleanupResult(
    val deletedEntries: Int,
    val success: Boolean
)