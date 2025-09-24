package com.fooddiary.di;

@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0015\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\'\u00a2\u0006\u0002\u0010\u0007J\u0015\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\nH\'\u00a2\u0006\u0002\u0010\u000bJ\u0015\u0010\f\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000eH\'\u00a2\u0006\u0002\u0010\u000fJ\u0015\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u0012H\'\u00a2\u0006\u0002\u0010\u0013J\u0015\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u0016H\'\u00a2\u0006\u0002\u0010\u0017J\u0015\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0019\u001a\u00020\u001aH\'\u00a2\u0006\u0002\u0010\u001bJ\u0015\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u001d\u001a\u00020\u001eH\'\u00a2\u0006\u0002\u0010\u001fJ\u0015\u0010 \u001a\u00020\u00042\u0006\u0010!\u001a\u00020\"H\'\u00a2\u0006\u0002\u0010#J\u0015\u0010$\u001a\u00020\u00042\u0006\u0010%\u001a\u00020&H\'\u00a2\u0006\u0002\u0010\'J\u0015\u0010(\u001a\u00020\u00042\u0006\u0010)\u001a\u00020*H\'\u00a2\u0006\u0002\u0010+J\u0015\u0010,\u001a\u00020\u00042\u0006\u0010-\u001a\u00020.H\'\u00a2\u0006\u0002\u0010/\u00a8\u00060"}, d2 = {"Lcom/fooddiary/di/RepositoryModule;", "", "()V", "bindBeverageEntryRepository", "error/NonExistentClass", "beverageEntryRepositoryImpl", "Lcom/fooddiary/data/repository/impl/BeverageEntryRepositoryImpl;", "(Lcom/fooddiary/data/repository/impl/BeverageEntryRepositoryImpl;)Lerror/NonExistentClass;", "bindDatabaseRepository", "databaseRepositoryImpl", "Lcom/fooddiary/data/repository/impl/DatabaseRepositoryImpl;", "(Lcom/fooddiary/data/repository/impl/DatabaseRepositoryImpl;)Lerror/NonExistentClass;", "bindEliminationProtocolRepository", "eliminationProtocolRepositoryImpl", "Lcom/fooddiary/data/repository/impl/EliminationProtocolRepositoryImpl;", "(Lcom/fooddiary/data/repository/impl/EliminationProtocolRepositoryImpl;)Lerror/NonExistentClass;", "bindEnvironmentalContextRepository", "environmentalContextRepositoryImpl", "Lcom/fooddiary/data/repository/impl/EnvironmentalContextRepositoryImpl;", "(Lcom/fooddiary/data/repository/impl/EnvironmentalContextRepositoryImpl;)Lerror/NonExistentClass;", "bindFODMAPRepository", "fodmapRepositoryImpl", "Lcom/fooddiary/data/repository/impl/FODMAPRepositoryImpl;", "(Lcom/fooddiary/data/repository/impl/FODMAPRepositoryImpl;)Lerror/NonExistentClass;", "bindFoodEntryRepository", "foodEntryRepositoryImpl", "Lcom/fooddiary/data/repository/impl/FoodEntryRepositoryImpl;", "(Lcom/fooddiary/data/repository/impl/FoodEntryRepositoryImpl;)Lerror/NonExistentClass;", "bindMedicalReportRepository", "medicalReportRepositoryImpl", "Lcom/fooddiary/data/repository/impl/MedicalReportRepositoryImpl;", "(Lcom/fooddiary/data/repository/impl/MedicalReportRepositoryImpl;)Lerror/NonExistentClass;", "bindQuickEntryTemplateRepository", "quickEntryTemplateRepositoryImpl", "Lcom/fooddiary/data/repository/impl/QuickEntryTemplateRepositoryImpl;", "(Lcom/fooddiary/data/repository/impl/QuickEntryTemplateRepositoryImpl;)Lerror/NonExistentClass;", "bindSymptomEventRepository", "symptomEventRepositoryImpl", "Lcom/fooddiary/data/repository/impl/SymptomEventRepositoryImpl;", "(Lcom/fooddiary/data/repository/impl/SymptomEventRepositoryImpl;)Lerror/NonExistentClass;", "bindTriggerPatternRepository", "triggerPatternRepositoryImpl", "Lcom/fooddiary/data/repository/impl/TriggerPatternRepositoryImpl;", "(Lcom/fooddiary/data/repository/impl/TriggerPatternRepositoryImpl;)Lerror/NonExistentClass;", "bindUserPreferencesRepository", "userPreferencesRepositoryImpl", "Lcom/fooddiary/data/repository/impl/UserPreferencesRepositoryImpl;", "(Lcom/fooddiary/data/repository/impl/UserPreferencesRepositoryImpl;)Lerror/NonExistentClass;", "app_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public abstract class RepositoryModule {
    
    public RepositoryModule() {
        super();
    }
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract error.NonExistentClass bindFoodEntryRepository(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.repository.impl.FoodEntryRepositoryImpl foodEntryRepositoryImpl);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract error.NonExistentClass bindBeverageEntryRepository(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.repository.impl.BeverageEntryRepositoryImpl beverageEntryRepositoryImpl);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract error.NonExistentClass bindSymptomEventRepository(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.repository.impl.SymptomEventRepositoryImpl symptomEventRepositoryImpl);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract error.NonExistentClass bindEnvironmentalContextRepository(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.repository.impl.EnvironmentalContextRepositoryImpl environmentalContextRepositoryImpl);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract error.NonExistentClass bindTriggerPatternRepository(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.repository.impl.TriggerPatternRepositoryImpl triggerPatternRepositoryImpl);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract error.NonExistentClass bindQuickEntryTemplateRepository(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.repository.impl.QuickEntryTemplateRepositoryImpl quickEntryTemplateRepositoryImpl);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract error.NonExistentClass bindEliminationProtocolRepository(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.repository.impl.EliminationProtocolRepositoryImpl eliminationProtocolRepositoryImpl);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract error.NonExistentClass bindMedicalReportRepository(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.repository.impl.MedicalReportRepositoryImpl medicalReportRepositoryImpl);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract error.NonExistentClass bindUserPreferencesRepository(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.repository.impl.UserPreferencesRepositoryImpl userPreferencesRepositoryImpl);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract error.NonExistentClass bindFODMAPRepository(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.repository.impl.FODMAPRepositoryImpl fodmapRepositoryImpl);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract error.NonExistentClass bindDatabaseRepository(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.repository.impl.DatabaseRepositoryImpl databaseRepositoryImpl);
}