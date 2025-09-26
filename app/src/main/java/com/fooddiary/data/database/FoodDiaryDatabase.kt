package com.fooddiary.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fooddiary.data.database.converters.*
import com.fooddiary.data.database.dao.*
import com.fooddiary.data.database.entities.*

@Database(
    entities = [
        FoodEntry::class,
        BeverageEntry::class,
        SymptomEvent::class,
        EnvironmentalContext::class,
        QuickEntryTemplate::class,
        EliminationProtocol::class,
        TriggerPattern::class,
        MedicalReport::class,
        CorrelationPattern::class,
        ReportData::class,
        UserPreferences::class,
    ],
    version = 3,
    exportSchema = true,
)
@TypeConverters(
    InstantConverter::class,
    LocalDateConverter::class,
    LocalTimeConverter::class,
    DurationConverter::class,
    StringListConverter::class,
    StringMapConverter::class,
    ConsumptionContextConverter::class,
)
abstract class FoodDiaryDatabase : RoomDatabase() {

    abstract fun foodEntryDao(): FoodEntryDao
    abstract fun beverageEntryDao(): BeverageEntryDao
    abstract fun symptomEventDao(): SymptomEventDao
    abstract fun environmentalContextDao(): EnvironmentalContextDao
    abstract fun quickEntryTemplateDao(): QuickEntryTemplateDao
    abstract fun eliminationProtocolDao(): EliminationProtocolDao
    abstract fun triggerPatternDao(): TriggerPatternDao
    abstract fun medicalReportDao(): MedicalReportDao
    abstract fun correlationPatternDao(): CorrelationPatternDao
    abstract fun userPreferencesDao(): UserPreferencesDao

    companion object {
        const val DATABASE_NAME = "food_diary_database"
        const val LATEST_VERSION = 3
    }
}
