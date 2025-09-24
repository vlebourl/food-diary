package com.fooddiary.data.repository.impl;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000e\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\tJ\u000e\u0010\n\u001a\u00020\u0006H\u0096@\u00a2\u0006\u0002\u0010\u000bJ\u001e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u0010H\u0096@\u00a2\u0006\u0002\u0010\u0011J\u0014\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00140\u0013H\u0016J\u001c\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00140\u00132\u0006\u0010\u0016\u001a\u00020\u0010H\u0016J\u001c\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00140\u00132\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\u0018\u0010\u001a\u001a\u0004\u0018\u00010\b2\u0006\u0010\u001b\u001a\u00020\u001cH\u0096@\u00a2\u0006\u0002\u0010\u001dJ\u001c\u0010\u001e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00140\u00132\u0006\u0010\u001f\u001a\u00020 H\u0016J\u000e\u0010!\u001a\u00020\rH\u0096@\u00a2\u0006\u0002\u0010\u000bJ\u0014\u0010\"\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00140\u0013H\u0016J\u000e\u0010#\u001a\u00020\rH\u0096@\u00a2\u0006\u0002\u0010\u000bJ\u0014\u0010$\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00140\u0013H\u0016J\u0016\u0010%\u001a\u00020\u001c2\u0006\u0010\u0007\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\tJ\u0016\u0010&\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\tJ\u001e\u0010\'\u001a\u00020\u00062\u0006\u0010(\u001a\u00020\u001c2\u0006\u0010)\u001a\u00020\u0010H\u0096@\u00a2\u0006\u0002\u0010*J\u001e\u0010+\u001a\u00020\u00062\u0006\u0010(\u001a\u00020\u001c2\u0006\u0010,\u001a\u00020\rH\u0096@\u00a2\u0006\u0002\u0010-R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006."}, d2 = {"Lcom/fooddiary/data/repository/impl/TriggerPatternRepositoryImpl;", "", "triggerPatternDao", "Lcom/fooddiary/data/database/dao/TriggerPatternDao;", "(Lcom/fooddiary/data/database/dao/TriggerPatternDao;)V", "delete", "", "pattern", "Lcom/fooddiary/data/database/entities/TriggerPattern;", "(Lcom/fooddiary/data/database/entities/TriggerPattern;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteAll", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteWeakPatterns", "", "minOccurrences", "minCorrelation", "", "(IFLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAll", "Lkotlinx/coroutines/flow/Flow;", "", "getByCorrelationStrength", "minStrength", "getByFood", "foodName", "", "getById", "id", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getBySymptomType", "symptomType", "Lcom/fooddiary/data/models/SymptomType;", "getCount", "getHighConfidence", "getSignificantCount", "getStatisticallySignificant", "insert", "update", "updateConfidence", "patternId", "newConfidence", "(JFLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateOccurrences", "newOccurrences", "(JILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class TriggerPatternRepositoryImpl {
    @org.jetbrains.annotations.NotNull()
    private final com.fooddiary.data.database.dao.TriggerPatternDao triggerPatternDao = null;
    
    @javax.inject.Inject()
    public TriggerPatternRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.database.dao.TriggerPatternDao triggerPatternDao) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.database.entities.TriggerPattern pattern, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object update(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.database.entities.TriggerPattern pattern, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object delete(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.database.entities.TriggerPattern pattern, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.fooddiary.data.database.entities.TriggerPattern> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.fooddiary.data.database.entities.TriggerPattern>> getAll() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.fooddiary.data.database.entities.TriggerPattern>> getBySymptomType(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.SymptomType symptomType) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.fooddiary.data.database.entities.TriggerPattern>> getByFood(@org.jetbrains.annotations.NotNull()
    java.lang.String foodName) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.fooddiary.data.database.entities.TriggerPattern>> getStatisticallySignificant() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.fooddiary.data.database.entities.TriggerPattern>> getHighConfidence() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.fooddiary.data.database.entities.TriggerPattern>> getByCorrelationStrength(float minStrength) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object updateConfidence(long patternId, float newConfidence, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object updateOccurrences(long patternId, int newOccurrences, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getSignificantCount(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getCount(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object deleteWeakPatterns(int minOccurrences, float minCorrelation, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object deleteAll(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}