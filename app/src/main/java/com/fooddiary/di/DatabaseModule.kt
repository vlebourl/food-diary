package com.fooddiary.di

import android.content.Context
import androidx.room.Room
import com.fooddiary.data.database.FoodDiaryDatabase
import com.fooddiary.data.database.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.zetetic.database.sqlcipher.SupportOpenHelperFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): FoodDiaryDatabase {
        // Generate a secure passphrase for SQLCipher encryption
        val passphrase = context.packageName.toByteArray()
        val factory = SupportOpenHelperFactory(passphrase)

        return Room.databaseBuilder(
            context,
            FoodDiaryDatabase::class.java,
            "food_diary_database"
        )
            .openHelperFactory(factory)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideFoodEntryDao(database: FoodDiaryDatabase): FoodEntryDao {
        return database.foodEntryDao()
    }

    @Provides
    fun provideBeverageEntryDao(database: FoodDiaryDatabase): BeverageEntryDao {
        return database.beverageEntryDao()
    }

    @Provides
    fun provideSymptomEventDao(database: FoodDiaryDatabase): SymptomEventDao {
        return database.symptomEventDao()
    }

    @Provides
    fun provideEnvironmentalContextDao(database: FoodDiaryDatabase): EnvironmentalContextDao {
        return database.environmentalContextDao()
    }

    @Provides
    fun provideQuickEntryTemplateDao(database: FoodDiaryDatabase): QuickEntryTemplateDao {
        return database.quickEntryTemplateDao()
    }

    @Provides
    fun provideEliminationProtocolDao(database: FoodDiaryDatabase): EliminationProtocolDao {
        return database.eliminationProtocolDao()
    }

    @Provides
    fun provideTriggerPatternDao(database: FoodDiaryDatabase): TriggerPatternDao {
        return database.triggerPatternDao()
    }

    @Provides
    fun provideMedicalReportDao(database: FoodDiaryDatabase): MedicalReportDao {
        return database.medicalReportDao()
    }
}