package com.fooddiary.data.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Enhanced SymptomEvent schema migration
        // Drop old table and recreate with new simplified structure
        database.execSQL("DROP TABLE IF EXISTS symptom_events_old")
        database.execSQL("ALTER TABLE symptom_events RENAME TO symptom_events_old")

        // Create new table with simplified structure
        database.execSQL("""
            CREATE TABLE IF NOT EXISTS symptom_events (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                timestamp INTEGER NOT NULL,
                symptomType TEXT NOT NULL,
                severity INTEGER NOT NULL,
                duration INTEGER,
                notes TEXT,
                isDeleted INTEGER NOT NULL DEFAULT 0,
                createdAt INTEGER NOT NULL,
                modifiedAt INTEGER NOT NULL
            )
        """)

        // Migrate data from old structure to new structure
        database.execSQL("""
            INSERT INTO symptom_events (id, timestamp, symptomType, severity, duration, notes, isDeleted, createdAt, modifiedAt)
            SELECT
                CASE WHEN id GLOB '*-*-*-*-*' THEN
                    ABS(RANDOM() % 1000000) + 1
                ELSE
                    CAST(id AS INTEGER)
                END,
                timestamp,
                type,
                severity,
                CASE WHEN duration IS NOT NULL THEN duration * 60 ELSE NULL END, -- Convert minutes to seconds for Duration
                notes,
                isDeleted,
                createdAt,
                COALESCE(modifiedAt, createdAt)
            FROM symptom_events_old
            WHERE severity BETWEEN 1 AND 10 -- Ensure valid severity range
        """)

        // Drop old table
        database.execSQL("DROP TABLE symptom_events_old")

        // Create indexes for performance
        database.execSQL("CREATE INDEX IF NOT EXISTS index_symptom_events_timestamp ON symptom_events(timestamp)")
        database.execSQL("CREATE INDEX IF NOT EXISTS index_symptom_events_symptomType ON symptom_events(symptomType)")
        database.execSQL("CREATE INDEX IF NOT EXISTS index_symptom_events_isDeleted ON symptom_events(isDeleted)")

        // Create new correlation_patterns table
        database.execSQL("""
            CREATE TABLE IF NOT EXISTS correlation_patterns (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                foodEntryId INTEGER NOT NULL,
                symptomEventId INTEGER NOT NULL,
                timeOffset INTEGER NOT NULL,
                confidenceScore REAL NOT NULL,
                correlationType TEXT NOT NULL,
                calculatedAt INTEGER NOT NULL,
                FOREIGN KEY(foodEntryId) REFERENCES food_entries(id) ON DELETE CASCADE,
                FOREIGN KEY(symptomEventId) REFERENCES symptom_events(id) ON DELETE CASCADE
            )
        """)

        // Create indexes for correlation patterns
        database.execSQL("CREATE INDEX IF NOT EXISTS index_correlation_patterns_foodEntryId ON correlation_patterns(foodEntryId)")
        database.execSQL("CREATE INDEX IF NOT EXISTS index_correlation_patterns_symptomEventId ON correlation_patterns(symptomEventId)")
        database.execSQL("CREATE INDEX IF NOT EXISTS index_correlation_patterns_timeOffset ON correlation_patterns(timeOffset)")
        database.execSQL("CREATE INDEX IF NOT EXISTS index_correlation_patterns_confidenceScore ON correlation_patterns(confidenceScore)")

        // Create report_data table
        database.execSQL("""
            CREATE TABLE IF NOT EXISTS report_data (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                reportType TEXT NOT NULL,
                startDate INTEGER NOT NULL,
                endDate INTEGER NOT NULL,
                totalEntries INTEGER NOT NULL,
                totalSymptoms INTEGER NOT NULL,
                topTriggerFoods TEXT NOT NULL,
                symptomFrequency TEXT NOT NULL,
                averageSeverity REAL NOT NULL,
                reportData TEXT NOT NULL,
                generatedAt INTEGER NOT NULL
            )
        """)

        // Create user_preferences table
        database.execSQL("""
            CREATE TABLE IF NOT EXISTS user_preferences (
                id INTEGER PRIMARY KEY NOT NULL DEFAULT 1,
                correlationTimeWindowHours INTEGER NOT NULL DEFAULT 3,
                measurementUnit TEXT NOT NULL DEFAULT 'METRIC',
                notificationEnabled INTEGER NOT NULL DEFAULT 1,
                notificationTime INTEGER,
                dataRetentionMonths INTEGER NOT NULL DEFAULT 12,
                exportFormat TEXT NOT NULL DEFAULT 'JSON',
                customSymptomTypes TEXT NOT NULL DEFAULT '[]',
                triggerAlertEnabled INTEGER NOT NULL DEFAULT 0,
                reportTemplate TEXT NOT NULL DEFAULT 'BASIC',
                privacyMode INTEGER NOT NULL DEFAULT 1,
                createdAt INTEGER NOT NULL,
                modifiedAt INTEGER NOT NULL
            )
        """)

        // Insert default preferences
        database.execSQL("""
            INSERT OR IGNORE INTO user_preferences (id, createdAt, modifiedAt)
            VALUES (1, ${System.currentTimeMillis() / 1000}, ${System.currentTimeMillis() / 1000})
        """)
    }
}