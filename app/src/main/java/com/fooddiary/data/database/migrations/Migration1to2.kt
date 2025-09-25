package com.fooddiary.data.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Enhanced FoodEntry schema migration
        // Drop old table and recreate with new structure
        database.execSQL("DROP TABLE IF EXISTS food_entries_old")
        database.execSQL("ALTER TABLE food_entries RENAME TO food_entries_old")

        // Create new table with enhanced structure
        database.execSQL("""
            CREATE TABLE IF NOT EXISTS food_entries (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                timestamp INTEGER NOT NULL,
                mealType TEXT NOT NULL,
                foods TEXT NOT NULL,
                portions TEXT NOT NULL,
                notes TEXT,
                isDeleted INTEGER NOT NULL DEFAULT 0,
                createdAt INTEGER NOT NULL,
                modifiedAt INTEGER NOT NULL
            )
        """)

        // Migrate data from old structure to new structure
        database.execSQL("""
            INSERT INTO food_entries (id, timestamp, mealType, foods, portions, notes, isDeleted, createdAt, modifiedAt)
            SELECT
                CASE WHEN id GLOB '*-*-*-*-*' THEN
                    ABS(RANDOM() % 1000000) + 1
                ELSE
                    CAST(id AS INTEGER)
                END,
                timestamp,
                mealType,
                CASE WHEN ingredients IS NOT NULL THEN ingredients ELSE name END,
                CASE WHEN portions IS NOT NULL AND portionUnit IS NOT NULL
                     THEN '{"' || COALESCE(name, 'food') || '":"' || CAST(portions AS TEXT) || ' ' || portionUnit || '"}'
                     ELSE '{}' END,
                notes,
                isDeleted,
                createdAt,
                COALESCE(modifiedAt, createdAt)
            FROM food_entries_old
        """)

        // Drop old table
        database.execSQL("DROP TABLE food_entries_old")

        // Create indexes for performance
        database.execSQL("CREATE INDEX IF NOT EXISTS index_food_entries_timestamp ON food_entries(timestamp)")
        database.execSQL("CREATE INDEX IF NOT EXISTS index_food_entries_isDeleted ON food_entries(isDeleted)")
    }
}