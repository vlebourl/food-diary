package com.fooddiary.domain.usecase;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\t\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B/\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\bJ@\u0010\n\u001a\u00020\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\r2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\r2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\rH\u0002J$\u0010\u0015\u001a\u00020\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\rH\u0002J@\u0010\u0016\u001a\u00020\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\r2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\r2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\rH\u0002J$\u0010\u0017\u001a\u00020\u000b2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\r2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\rH\u0002J4\u0010\u0018\u001a\u00020\u000b2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001a2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\r2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\rH\u0002J4\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001d2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u001f\u001a\u00020 H\u0086B\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b!\u0010\"R\u0010\u0010\u0006\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\tR\u0010\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\tR\u0010\u0010\u0007\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\tR\u0010\u0010\u0004\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\tR\u0010\u0010\u0005\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\t\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006#"}, d2 = {"Lcom/fooddiary/domain/usecase/GenerateReportUseCase;", "", "foodEntryRepository", "error/NonExistentClass", "symptomEventRepository", "triggerPatternRepository", "environmentalContextRepository", "medicalReportRepository", "(Lerror/NonExistentClass;Lerror/NonExistentClass;Lerror/NonExistentClass;Lerror/NonExistentClass;Lerror/NonExistentClass;)V", "Lerror/NonExistentClass;", "generateComprehensiveReport", "", "foodEntries", "", "Lcom/fooddiary/data/database/entities/FoodEntry;", "symptomEvents", "Lcom/fooddiary/data/database/entities/SymptomEvent;", "patterns", "Lcom/fooddiary/data/database/entities/TriggerPattern;", "contexts", "Lcom/fooddiary/data/database/entities/EnvironmentalContext;", "generateFoodPatternsReport", "generateMedicalSummaryReport", "generateSymptomSummaryReport", "generateWeeklyOverviewReport", "startDate", "Ljava/time/LocalDate;", "endDate", "invoke", "Lkotlin/Result;", "Lcom/fooddiary/presentation/viewmodel/ReportData;", "reportType", "Lcom/fooddiary/presentation/viewmodel/ReportType;", "invoke-BWLJW6A", "(Ljava/time/LocalDate;Ljava/time/LocalDate;Lcom/fooddiary/presentation/viewmodel/ReportType;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class GenerateReportUseCase {
    @org.jetbrains.annotations.NotNull()
    private final error.NonExistentClass foodEntryRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final error.NonExistentClass symptomEventRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final error.NonExistentClass triggerPatternRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final error.NonExistentClass environmentalContextRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final error.NonExistentClass medicalReportRepository = null;
    
    @javax.inject.Inject()
    public GenerateReportUseCase(@org.jetbrains.annotations.NotNull()
    error.NonExistentClass foodEntryRepository, @org.jetbrains.annotations.NotNull()
    error.NonExistentClass symptomEventRepository, @org.jetbrains.annotations.NotNull()
    error.NonExistentClass triggerPatternRepository, @org.jetbrains.annotations.NotNull()
    error.NonExistentClass environmentalContextRepository, @org.jetbrains.annotations.NotNull()
    error.NonExistentClass medicalReportRepository) {
        super();
    }
    
    private final java.lang.String generateComprehensiveReport(java.util.List<com.fooddiary.data.database.entities.FoodEntry> foodEntries, java.util.List<com.fooddiary.data.database.entities.SymptomEvent> symptomEvents, java.util.List<com.fooddiary.data.database.entities.TriggerPattern> patterns, java.util.List<com.fooddiary.data.database.entities.EnvironmentalContext> contexts) {
        return null;
    }
    
    private final java.lang.String generateFoodPatternsReport(java.util.List<com.fooddiary.data.database.entities.FoodEntry> foodEntries, java.util.List<com.fooddiary.data.database.entities.TriggerPattern> patterns) {
        return null;
    }
    
    private final java.lang.String generateSymptomSummaryReport(java.util.List<com.fooddiary.data.database.entities.SymptomEvent> symptomEvents, java.util.List<com.fooddiary.data.database.entities.TriggerPattern> patterns) {
        return null;
    }
    
    private final java.lang.String generateWeeklyOverviewReport(java.time.LocalDate startDate, java.time.LocalDate endDate, java.util.List<com.fooddiary.data.database.entities.FoodEntry> foodEntries, java.util.List<com.fooddiary.data.database.entities.SymptomEvent> symptomEvents) {
        return null;
    }
    
    private final java.lang.String generateMedicalSummaryReport(java.util.List<com.fooddiary.data.database.entities.FoodEntry> foodEntries, java.util.List<com.fooddiary.data.database.entities.SymptomEvent> symptomEvents, java.util.List<com.fooddiary.data.database.entities.TriggerPattern> patterns, java.util.List<com.fooddiary.data.database.entities.EnvironmentalContext> contexts) {
        return null;
    }
}