package com.fooddiary.di;

@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0011\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0015\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004H\'\u00a2\u0006\u0002\u0010\u0006J\u0015\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0004H\'\u00a2\u0006\u0002\u0010\u0006J\u0015\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u0004H\'\u00a2\u0006\u0002\u0010\u0006J\u0015\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0004H\'\u00a2\u0006\u0002\u0010\u000eJ\u0015\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0004H\'\u00a2\u0006\u0002\u0010\u0006J\u0015\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u0004H\'\u00a2\u0006\u0002\u0010\u0006J\u0015\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\u0004H\'\u00a2\u0006\u0002\u0010\u0006J\u0015\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u0004H\'\u00a2\u0006\u0002\u0010\u0006J\u0015\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0004H\'\u00a2\u0006\u0002\u0010\u001aJ\u0015\u0010\u001b\u001a\u00020\u00042\u0006\u0010\u001c\u001a\u00020\u0004H\'\u00a2\u0006\u0002\u0010\u0006J\u0015\u0010\u001d\u001a\u00020\u00042\u0006\u0010\u001e\u001a\u00020\u0004H\'\u00a2\u0006\u0002\u0010\u0006J\u0015\u0010\u001f\u001a\u00020\u00042\u0006\u0010 \u001a\u00020\u0004H\'\u00a2\u0006\u0002\u0010\u0006J\u0015\u0010!\u001a\u00020\u00042\u0006\u0010\"\u001a\u00020\u0004H\'\u00a2\u0006\u0002\u0010\u0006J\u0015\u0010#\u001a\u00020\u00042\u0006\u0010$\u001a\u00020\u0004H\'\u00a2\u0006\u0002\u0010\u0006J\u0015\u0010%\u001a\u00020\u00042\u0006\u0010&\u001a\u00020\u0004H\'\u00a2\u0006\u0002\u0010\u0006J\u0015\u0010\'\u001a\u00020\u00042\u0006\u0010(\u001a\u00020\u0004H\'\u00a2\u0006\u0002\u0010\u0006\u00a8\u0006)"}, d2 = {"Lcom/fooddiary/di/UseCaseModule;", "", "()V", "bindAnalyzeFODMAPUseCase", "error/NonExistentClass", "analyzeFODMAPUseCaseImpl", "(Lerror/NonExistentClass;)Lerror/NonExistentClass;", "bindAnalyzeNutritionUseCase", "analyzeNutritionUseCaseImpl", "bindCalculateCorrelationStatisticsUseCase", "calculateCorrelationStatisticsUseCaseImpl", "bindCalculateCorrelationsUseCase", "Lcom/fooddiary/domain/usecase/CalculateCorrelationsUseCase;", "calculateCorrelationsUseCaseImpl", "(Lerror/NonExistentClass;)Lcom/fooddiary/domain/usecase/CalculateCorrelationsUseCase;", "bindCreateEliminationProtocolUseCase", "createEliminationProtocolUseCaseImpl", "bindDetectPatternAnomaliesUseCase", "detectPatternAnomaliesUseCaseImpl", "bindExportDataUseCase", "exportDataUseCaseImpl", "bindGenerateInsightsUseCase", "generateInsightsUseCaseImpl", "bindGenerateReportUseCase", "Lcom/fooddiary/domain/usecase/GenerateReportUseCase;", "generateReportUseCaseImpl", "(Lerror/NonExistentClass;)Lcom/fooddiary/domain/usecase/GenerateReportUseCase;", "bindImportDataUseCase", "importDataUseCaseImpl", "bindProcessVoiceInputUseCase", "processVoiceInputUseCaseImpl", "bindSaveFoodEntryUseCase", "saveFoodEntryUseCaseImpl", "bindSaveSymptomEventUseCase", "saveSymptomEventUseCaseImpl", "bindSyncDataUseCase", "syncDataUseCaseImpl", "bindValidateDataIntegrityUseCase", "validateDataIntegrityUseCaseImpl", "bindValidateSymptomDataUseCase", "validateSymptomDataUseCaseImpl", "app_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public abstract class UseCaseModule {
    
    public UseCaseModule() {
        super();
    }
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract error.NonExistentClass bindAnalyzeFODMAPUseCase(@org.jetbrains.annotations.NotNull()
    error.NonExistentClass analyzeFODMAPUseCaseImpl);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract com.fooddiary.domain.usecase.CalculateCorrelationsUseCase bindCalculateCorrelationsUseCase(@org.jetbrains.annotations.NotNull()
    error.NonExistentClass calculateCorrelationsUseCaseImpl);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract error.NonExistentClass bindCreateEliminationProtocolUseCase(@org.jetbrains.annotations.NotNull()
    error.NonExistentClass createEliminationProtocolUseCaseImpl);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract com.fooddiary.domain.usecase.GenerateReportUseCase bindGenerateReportUseCase(@org.jetbrains.annotations.NotNull()
    error.NonExistentClass generateReportUseCaseImpl);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract error.NonExistentClass bindValidateDataIntegrityUseCase(@org.jetbrains.annotations.NotNull()
    error.NonExistentClass validateDataIntegrityUseCaseImpl);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract error.NonExistentClass bindSaveFoodEntryUseCase(@org.jetbrains.annotations.NotNull()
    error.NonExistentClass saveFoodEntryUseCaseImpl);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract error.NonExistentClass bindSaveSymptomEventUseCase(@org.jetbrains.annotations.NotNull()
    error.NonExistentClass saveSymptomEventUseCaseImpl);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract error.NonExistentClass bindAnalyzeNutritionUseCase(@org.jetbrains.annotations.NotNull()
    error.NonExistentClass analyzeNutritionUseCaseImpl);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract error.NonExistentClass bindProcessVoiceInputUseCase(@org.jetbrains.annotations.NotNull()
    error.NonExistentClass processVoiceInputUseCaseImpl);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract error.NonExistentClass bindSyncDataUseCase(@org.jetbrains.annotations.NotNull()
    error.NonExistentClass syncDataUseCaseImpl);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract error.NonExistentClass bindExportDataUseCase(@org.jetbrains.annotations.NotNull()
    error.NonExistentClass exportDataUseCaseImpl);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract error.NonExistentClass bindImportDataUseCase(@org.jetbrains.annotations.NotNull()
    error.NonExistentClass importDataUseCaseImpl);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract error.NonExistentClass bindDetectPatternAnomaliesUseCase(@org.jetbrains.annotations.NotNull()
    error.NonExistentClass detectPatternAnomaliesUseCaseImpl);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract error.NonExistentClass bindGenerateInsightsUseCase(@org.jetbrains.annotations.NotNull()
    error.NonExistentClass generateInsightsUseCaseImpl);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract error.NonExistentClass bindValidateSymptomDataUseCase(@org.jetbrains.annotations.NotNull()
    error.NonExistentClass validateSymptomDataUseCaseImpl);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract error.NonExistentClass bindCalculateCorrelationStatisticsUseCase(@org.jetbrains.annotations.NotNull()
    error.NonExistentClass calculateCorrelationStatisticsUseCaseImpl);
}