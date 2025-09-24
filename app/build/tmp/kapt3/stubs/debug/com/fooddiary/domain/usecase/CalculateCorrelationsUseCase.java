package com.fooddiary.domain.usecase;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0007\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0006J\u0018\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\tH\u0002J<\u0010\r\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u00132\u0006\u0010\u0017\u001a\u00020\u000bH\u0002J\u0018\u0010\u0018\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\tH\u0002JP\u0010\u0019\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001b0\u00130\u001a2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001d2\b\b\u0002\u0010\u001f\u001a\u00020\u000b2\b\b\u0002\u0010 \u001a\u00020\t2\b\b\u0002\u0010\u0017\u001a\u00020\u000bH\u0086B\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b!\u0010\"J\u0018\u0010#\u001a\u00020$2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\tH\u0002R\u0010\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0007R\u0010\u0010\u0004\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0007R\u0010\u0010\u0005\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0007\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006%"}, d2 = {"Lcom/fooddiary/domain/usecase/CalculateCorrelationsUseCase;", "", "foodEntryRepository", "error/NonExistentClass", "symptomEventRepository", "triggerPatternRepository", "(Lerror/NonExistentClass;Lerror/NonExistentClass;Lerror/NonExistentClass;)V", "Lerror/NonExistentClass;", "calculateConfidenceLevel", "", "occurrences", "", "correlation", "calculateCorrelationStrength", "foodName", "", "symptomType", "Lcom/fooddiary/data/models/SymptomType;", "allFoodEntries", "", "Lcom/fooddiary/data/database/entities/FoodEntry;", "allSymptoms", "Lcom/fooddiary/data/database/entities/SymptomEvent;", "maxTimeWindowHours", "calculatePValue", "invoke", "Lkotlin/Result;", "Lcom/fooddiary/data/database/entities/TriggerPattern;", "startDate", "Ljava/time/LocalDate;", "endDate", "minOccurrences", "minCorrelation", "invoke-hUnOzRk", "(Ljava/time/LocalDate;Ljava/time/LocalDate;IFILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "isStatisticallySignificant", "", "app_debug"})
public final class CalculateCorrelationsUseCase {
    @org.jetbrains.annotations.NotNull()
    private final error.NonExistentClass foodEntryRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final error.NonExistentClass symptomEventRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final error.NonExistentClass triggerPatternRepository = null;
    
    @javax.inject.Inject()
    public CalculateCorrelationsUseCase(@org.jetbrains.annotations.NotNull()
    error.NonExistentClass foodEntryRepository, @org.jetbrains.annotations.NotNull()
    error.NonExistentClass symptomEventRepository, @org.jetbrains.annotations.NotNull()
    error.NonExistentClass triggerPatternRepository) {
        super();
    }
    
    private final float calculateCorrelationStrength(java.lang.String foodName, com.fooddiary.data.models.SymptomType symptomType, java.util.List<com.fooddiary.data.database.entities.FoodEntry> allFoodEntries, java.util.List<com.fooddiary.data.database.entities.SymptomEvent> allSymptoms, int maxTimeWindowHours) {
        return 0.0F;
    }
    
    private final float calculateConfidenceLevel(int occurrences, float correlation) {
        return 0.0F;
    }
    
    private final boolean isStatisticallySignificant(int occurrences, float correlation) {
        return false;
    }
    
    private final float calculatePValue(int occurrences, float correlation) {
        return 0.0F;
    }
}