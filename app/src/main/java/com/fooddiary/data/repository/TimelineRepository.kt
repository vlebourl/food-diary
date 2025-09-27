package com.fooddiary.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.fooddiary.data.database.dao.FoodEntryDao
import com.fooddiary.data.database.dao.SymptomEventDao
import com.fooddiary.data.database.dao.CorrelationPatternDao
import com.fooddiary.data.database.entities.FoodEntry
import com.fooddiary.data.database.entities.SymptomEvent
import com.fooddiary.data.database.entities.CorrelationPattern
import com.fooddiary.data.models.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for timeline operations
 * Aggregates data from multiple DAOs to provide chronological view of entries
 */
@Singleton
class TimelineRepository @Inject constructor(
    private val foodEntryDao: FoodEntryDao,
    private val symptomEventDao: SymptomEventDao,
    private val correlationPatternDao: CorrelationPatternDao
) {

    /**
     * Get timeline entries aggregated from all DAOs
     * Returns Flow<List<TimelineEntry>> sorted by timestamp (most recent first)
     */
    fun getTimelineEntries(): Flow<List<TimelineEntry>> {
        return combine(
            foodEntryDao.getAll().map { entries -> entries.filter { !it.isDeleted } },
            symptomEventDao.getAll().map { entries -> entries.filter { !it.isDeleted } }
        ) { foodEntries, symptomEvents ->
            val timelineEntries = mutableListOf<TimelineEntry>()

            // Convert food entries to timeline entries
            timelineEntries.addAll(foodEntries.map { entry ->
                TimelineEntry.FoodEntry(
                    entryId = entry.id,
                    entryTimestamp = entry.timestamp,
                    mealType = entry.mealType,
                    foods = entry.foods,
                    portions = entry.portions,
                    notes = entry.notes,
                    deleted = entry.isDeleted
                )
            })

            // Convert symptom events to timeline entries
            timelineEntries.addAll(symptomEvents.map { event ->
                TimelineEntry.SymptomEntry(
                    eventId = event.id,
                    eventTimestamp = event.timestamp,
                    symptomType = event.symptomType,
                    severity = event.severity,
                    duration = event.duration,
                    notes = event.notes,
                    deleted = event.isDeleted
                )
            })

            // Sort by timestamp (most recent first)
            timelineEntries.sortedByDescending { it.timestamp }
        }
    }

    /**
     * Get paginated timeline entries
     * Returns PagingData for efficient loading of large datasets
     */
    fun getPagedTimeline(): Flow<PagingData<TimelineEntry>> {
        return combine(
            foodEntryDao.getAll(),
            symptomEventDao.getAll()
        ) { foodEntries, symptomEvents ->
            val timelineEntries = mutableListOf<TimelineEntry>()

            timelineEntries.addAll(foodEntries.map { entry ->
                TimelineEntry.FoodEntry(
                    entryId = entry.id,
                    entryTimestamp = entry.timestamp,
                    mealType = entry.mealType,
                    foods = entry.foods,
                    portions = entry.portions,
                    notes = entry.notes,
                    deleted = entry.isDeleted
                )
            })

            timelineEntries.addAll(symptomEvents.map { event ->
                TimelineEntry.SymptomEntry(
                    eventId = event.id,
                    eventTimestamp = event.timestamp,
                    symptomType = event.symptomType,
                    severity = event.severity,
                    duration = event.duration,
                    notes = event.notes,
                    deleted = event.isDeleted
                )
            })

            timelineEntries.sortedByDescending { it.timestamp }
        }.map { entries ->
            // Convert to PagingData using a simple approach
            androidx.paging.PagingData.from(entries)
        }
    }

    /**
     * Get timeline entries for a specific date
     */
    fun getEntriesForDate(date: LocalDate): Flow<List<TimelineEntry>> {
        return combine(
            foodEntryDao.getEntriesForDate(date),
            symptomEventDao.getEntriesForDate(date)
        ) { foodEntries, symptomEvents ->
            val timelineEntries = mutableListOf<TimelineEntry>()

            timelineEntries.addAll(foodEntries.map { entry ->
                TimelineEntry.FoodEntry(
                    entryId = entry.id,
                    entryTimestamp = entry.timestamp,
                    mealType = entry.mealType,
                    foods = entry.foods,
                    portions = entry.portions,
                    notes = entry.notes,
                    deleted = entry.isDeleted
                )
            })

            timelineEntries.addAll(symptomEvents.map { event ->
                TimelineEntry.SymptomEntry(
                    eventId = event.id,
                    eventTimestamp = event.timestamp,
                    symptomType = event.symptomType,
                    severity = event.severity,
                    duration = event.duration,
                    notes = event.notes,
                    deleted = event.isDeleted
                )
            })

            timelineEntries.sortedByDescending { it.timestamp }
        }
    }

    /**
     * Get timeline entries within a date range
     */
    fun getTimelineEntriesInRange(startDate: LocalDate, endDate: LocalDate): Flow<List<TimelineEntry>> {
        return combine(
            foodEntryDao.getEntriesInDateRange(startDate, endDate),
            symptomEventDao.getEntriesInDateRange(startDate, endDate)
        ) { foodEntries, symptomEvents ->
            val timelineEntries = mutableListOf<TimelineEntry>()

            timelineEntries.addAll(foodEntries.map { entry ->
                TimelineEntry.FoodEntry(
                    entryId = entry.id,
                    entryTimestamp = entry.timestamp,
                    mealType = entry.mealType,
                    foods = entry.foods,
                    portions = entry.portions,
                    notes = entry.notes,
                    deleted = entry.isDeleted
                )
            })

            timelineEntries.addAll(symptomEvents.map { event ->
                TimelineEntry.SymptomEntry(
                    eventId = event.id,
                    eventTimestamp = event.timestamp,
                    symptomType = event.symptomType,
                    severity = event.severity,
                    duration = event.duration,
                    notes = event.notes,
                    deleted = event.isDeleted
                )
            })

            timelineEntries.sortedByDescending { it.timestamp }
        }
    }

    /**
     * Get correlated entries linking symptoms to potential trigger foods
     */
    suspend fun getCorrelatedEntries(minConfidence: Float): Flow<List<CorrelatedTimelineEntry>> {
        val correlations = correlationPatternDao.getHighConfidenceCorrelations(minConfidence)

        return kotlinx.coroutines.flow.flowOf(
            correlations.mapNotNull { correlation ->
                val triggerFood = foodEntryDao.getById(correlation.foodId)
                val symptom = symptomEventDao.getById(correlation.symptomId)

                if (triggerFood != null && symptom != null) {
                    CorrelatedTimelineEntry(
                        triggerFood = TimelineEntry.FoodEntry(
                            entryId = triggerFood.id,
                            entryTimestamp = triggerFood.timestamp,
                            mealType = triggerFood.mealType,
                            foods = triggerFood.foods,
                            portions = triggerFood.portions,
                            notes = triggerFood.notes,
                            deleted = triggerFood.isDeleted
                        ),
                        resultingSymptom = TimelineEntry.SymptomEntry(
                            eventId = symptom.id,
                            eventTimestamp = symptom.timestamp,
                            symptomType = symptom.symptomType,
                            severity = symptom.severity,
                            duration = symptom.duration,
                            notes = symptom.notes,
                            deleted = symptom.isDeleted
                        ),
                        correlationStrength = correlation.correlationStrength,
                        confidenceLevel = correlation.confidenceLevel,
                        timeOffsetHours = correlation.timeOffsetHours,
                        occurrenceCount = correlation.occurrenceCount
                    )
                } else null
            }
        )
    }

    /**
     * Search timeline entries by query string
     */
    suspend fun searchTimelineEntries(query: String): Flow<List<TimelineEntry>> {
        val foodEntries = foodEntryDao.searchByFood(query)
        val symptomEvents = symptomEventDao.searchByNotes(query)

        return kotlinx.coroutines.flow.flowOf(
            buildList {
                addAll(foodEntries.map { entry ->
                    TimelineEntry.FoodEntry(
                        entryId = entry.id,
                        entryTimestamp = entry.timestamp,
                        mealType = entry.mealType,
                        foods = entry.foods,
                        portions = entry.portions,
                        notes = entry.notes,
                        deleted = entry.isDeleted
                    )
                })

                addAll(symptomEvents.map { event ->
                    TimelineEntry.SymptomEntry(
                        eventId = event.id,
                        eventTimestamp = event.timestamp,
                        symptomType = event.symptomType,
                        severity = event.severity,
                        duration = event.duration,
                        notes = event.notes,
                        deleted = event.isDeleted
                    )
                })
            }.sortedByDescending { it.timestamp }
        )
    }

    /**
     * Calculate timeline statistics
     */
    suspend fun calculateTimelineStats(): TimelineStats {
        val foodEntries = foodEntryDao.getAllSync()
        val symptomEvents = symptomEventDao.getAllSync()

        val totalEntries = foodEntries.size + symptomEvents.size
        val foodCount = foodEntries.size
        val symptomCount = symptomEvents.size

        // Calculate time span
        val allTimestamps = (foodEntries.map { it.timestamp } + symptomEvents.map { it.timestamp })
        val timeSpanDays = if (allTimestamps.isNotEmpty()) {
            val oldest = allTimestamps.minOrNull() ?: Instant.now()
            val newest = allTimestamps.maxOrNull() ?: Instant.now()
            java.time.Duration.between(oldest, newest).toDays()
        } else 0L

        // Calculate averages
        val averageEntriesPerDay = if (timeSpanDays > 0) {
            totalEntries.toFloat() / timeSpanDays
        } else 0f

        // Find most common types
        val mostCommonMealType = foodEntries
            .groupBy { it.mealType }
            .maxByOrNull { it.value.size }?.key

        val mostCommonSymptomType = symptomEvents
            .groupBy { it.symptomType }
            .maxByOrNull { it.value.size }?.key

        return TimelineStats(
            totalEntries = totalEntries,
            foodEntries = foodCount,
            symptomEvents = symptomCount,
            timeSpanDays = timeSpanDays,
            averageEntriesPerDay = averageEntriesPerDay,
            mostCommonMealType = mostCommonMealType,
            mostCommonSymptomType = mostCommonSymptomType
        )
    }
}

/**
 * Timeline entry sealed class representing different types of timeline entries
 */
sealed class TimelineEntry(
    val id: Long,
    val timestamp: Instant,
    val isDeleted: Boolean = false
) {
    data class FoodEntry(
        val entryId: Long,
        val entryTimestamp: Instant,
        val mealType: MealType,
        val foods: List<String>,
        val portions: Map<String, String>,
        val notes: String?,
        val deleted: Boolean = false
    ) : TimelineEntry(entryId, entryTimestamp, deleted)

    data class SymptomEntry(
        val eventId: Long,
        val eventTimestamp: Instant,
        val symptomType: SymptomType,
        val severity: Int,
        val duration: java.time.Duration?,
        val notes: String?,
        val deleted: Boolean = false
    ) : TimelineEntry(eventId, eventTimestamp, deleted)
}

/**
 * Correlated entry data class for linking symptoms to potential triggers
 */
data class CorrelatedTimelineEntry(
    val triggerFood: TimelineEntry.FoodEntry,
    val resultingSymptom: TimelineEntry.SymptomEntry,
    val correlationStrength: Float,
    val confidenceLevel: ConfidenceLevel,
    val timeOffsetHours: Int,
    val occurrenceCount: Int
)

/**
 * Timeline statistics data class
 */
data class TimelineStats(
    val totalEntries: Int,
    val foodEntries: Int,
    val symptomEvents: Int,
    val timeSpanDays: Long,
    val averageEntriesPerDay: Float,
    val mostCommonMealType: MealType?,
    val mostCommonSymptomType: SymptomType?
)