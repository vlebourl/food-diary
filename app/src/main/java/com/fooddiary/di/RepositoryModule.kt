package com.fooddiary.di

import com.fooddiary.data.repository.*
import com.fooddiary.data.repository.impl.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindFoodEntryRepository(
        foodEntryRepositoryImpl: FoodEntryRepositoryImpl
    ): FoodEntryRepository

    @Binds
    @Singleton
    abstract fun bindBeverageEntryRepository(
        beverageEntryRepositoryImpl: BeverageEntryRepositoryImpl
    ): BeverageEntryRepository

    @Binds
    @Singleton
    abstract fun bindSymptomEventRepository(
        symptomEventRepositoryImpl: SymptomEventRepositoryImpl
    ): SymptomEventRepository

    @Binds
    @Singleton
    abstract fun bindEnvironmentalContextRepository(
        environmentalContextRepositoryImpl: EnvironmentalContextRepositoryImpl
    ): EnvironmentalContextRepository

    @Binds
    @Singleton
    abstract fun bindTriggerPatternRepository(
        triggerPatternRepositoryImpl: TriggerPatternRepositoryImpl
    ): TriggerPatternRepository

    @Binds
    @Singleton
    abstract fun bindQuickEntryTemplateRepository(
        quickEntryTemplateRepositoryImpl: QuickEntryTemplateRepositoryImpl
    ): QuickEntryTemplateRepository

    @Binds
    @Singleton
    abstract fun bindEliminationProtocolRepository(
        eliminationProtocolRepositoryImpl: EliminationProtocolRepositoryImpl
    ): EliminationProtocolRepository

    @Binds
    @Singleton
    abstract fun bindMedicalReportRepository(
        medicalReportRepositoryImpl: MedicalReportRepositoryImpl
    ): MedicalReportRepository

    @Binds
    @Singleton
    abstract fun bindUserPreferencesRepository(
        userPreferencesRepositoryImpl: UserPreferencesRepositoryImpl
    ): UserPreferencesRepository

    @Binds
    @Singleton
    abstract fun bindFODMAPRepository(
        fodmapRepositoryImpl: FODMAPRepositoryImpl
    ): FODMAPRepository

    @Binds
    @Singleton
    abstract fun bindDatabaseRepository(
        databaseRepositoryImpl: DatabaseRepositoryImpl
    ): DatabaseRepository
}