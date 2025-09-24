package com.fooddiary.data.repository.impl;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010$\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\tJ\u000e\u0010\n\u001a\u00020\u0006H\u0096@\u00a2\u0006\u0002\u0010\u000bJ\u0016\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0096@\u00a2\u0006\u0002\u0010\u0010J\u0014\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00130\u0012H\u0016J\u0018\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0096@\u00a2\u0006\u0002\u0010\u0018J\u0014\u0010\u0019\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00130\u0012H\u0016J$\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00130\u00122\u0006\u0010\u001b\u001a\u00020\u000f2\u0006\u0010\u001c\u001a\u00020\u000fH\u0016J\u0018\u0010\u001d\u001a\u0004\u0018\u00010\b2\u0006\u0010\u001e\u001a\u00020\u001fH\u0096@\u00a2\u0006\u0002\u0010 J$\u0010!\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00130\u00122\u0006\u0010\"\u001a\u00020\r2\u0006\u0010#\u001a\u00020\rH\u0016J\u001c\u0010$\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00130\u00122\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J$\u0010%\u001a\b\u0012\u0004\u0012\u00020\r0\u00122\u0006\u0010\u001b\u001a\u00020&2\u0006\u0010\u001c\u001a\u00020&H\u0096@\u00a2\u0006\u0002\u0010\'J\u0010\u0010(\u001a\u0004\u0018\u00010\u0017H\u0096@\u00a2\u0006\u0002\u0010\u000bJ\u001c\u0010)\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00130\u00122\u0006\u0010*\u001a\u00020\rH\u0016J\u001a\u0010+\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\r0,0\u0012H\u0016J$\u0010-\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00130\u00122\u0006\u0010.\u001a\u00020\u001f2\u0006\u0010/\u001a\u00020\rH\u0016J\u0016\u00100\u001a\u00020\u001f2\u0006\u0010\u0007\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\tJ\u0016\u00101\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\tR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00062"}, d2 = {"Lcom/fooddiary/data/repository/impl/SymptomEventRepositoryImpl;", "", "symptomEventDao", "Lcom/fooddiary/data/database/dao/SymptomEventDao;", "(Lcom/fooddiary/data/database/dao/SymptomEventDao;)V", "delete", "", "symptomEvent", "Lcom/fooddiary/data/database/entities/SymptomEvent;", "(Lcom/fooddiary/data/database/entities/SymptomEvent;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteAll", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteOlderThan", "", "date", "Ljava/time/Instant;", "(Ljava/time/Instant;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAll", "Lkotlinx/coroutines/flow/Flow;", "", "getAverageSeverityByType", "", "symptomType", "Lcom/fooddiary/data/models/SymptomType;", "(Lcom/fooddiary/data/models/SymptomType;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getBristolStoolEvents", "getByDateRange", "startDate", "endDate", "getById", "id", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getBySeverityRange", "minSeverity", "maxSeverity", "getBySymptomType", "getCountByDateRange", "", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMostFrequentSymptom", "getRecentSymptoms", "limit", "getSymptomFrequency", "", "getWithinTimeWindowOfFood", "foodEntryId", "windowMinutes", "insert", "update", "app_debug"})
public final class SymptomEventRepositoryImpl {
    @org.jetbrains.annotations.NotNull()
    private final com.fooddiary.data.database.dao.SymptomEventDao symptomEventDao = null;
    
    @javax.inject.Inject()
    public SymptomEventRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.database.dao.SymptomEventDao symptomEventDao) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.database.entities.SymptomEvent symptomEvent, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object update(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.database.entities.SymptomEvent symptomEvent, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object delete(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.database.entities.SymptomEvent symptomEvent, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.fooddiary.data.database.entities.SymptomEvent> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.fooddiary.data.database.entities.SymptomEvent>> getAll() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.fooddiary.data.database.entities.SymptomEvent>> getByDateRange(@org.jetbrains.annotations.NotNull()
    java.time.Instant startDate, @org.jetbrains.annotations.NotNull()
    java.time.Instant endDate) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.fooddiary.data.database.entities.SymptomEvent>> getBySymptomType(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.SymptomType symptomType) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.fooddiary.data.database.entities.SymptomEvent>> getBySeverityRange(int minSeverity, int maxSeverity) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.fooddiary.data.database.entities.SymptomEvent>> getWithinTimeWindowOfFood(long foodEntryId, int windowMinutes) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.fooddiary.data.database.entities.SymptomEvent>> getRecentSymptoms(int limit) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getAverageSeverityByType(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.SymptomType symptomType, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Double> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.Map<com.fooddiary.data.models.SymptomType, java.lang.Integer>> getSymptomFrequency() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getCountByDateRange(@org.jetbrains.annotations.NotNull()
    java.lang.String startDate, @org.jetbrains.annotations.NotNull()
    java.lang.String endDate, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<java.lang.Integer>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object deleteOlderThan(@org.jetbrains.annotations.NotNull()
    java.time.Instant date, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.fooddiary.data.database.entities.SymptomEvent>> getBristolStoolEvents() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getMostFrequentSymptom(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.fooddiary.data.models.SymptomType> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object deleteAll(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}