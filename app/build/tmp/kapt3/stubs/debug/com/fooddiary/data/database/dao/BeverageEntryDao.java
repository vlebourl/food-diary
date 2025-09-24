package com.fooddiary.data.database.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\bg\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0014\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006H\'J\u0014\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006H\'J$\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u00062\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\fH\'J\u0018\u0010\u000e\u001a\u0004\u0018\u00010\b2\u0006\u0010\u000f\u001a\u00020\fH\u00a7@\u00a2\u0006\u0002\u0010\u0010J\u0014\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006H\'J\u0016\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u00062\u0006\u0010\u0014\u001a\u00020\fH\'J\u000e\u0010\u0015\u001a\u00020\u0016H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00130\u00062\u0006\u0010\u0014\u001a\u00020\fH\'J\u0016\u0010\u0018\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\fH\u00a7@\u00a2\u0006\u0002\u0010\u0010J\u0016\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\u001cJ&\u0010\u001d\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\f2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u001fH\u00a7@\u00a2\u0006\u0002\u0010!J\u0016\u0010\"\u001a\u00020\u00032\u0006\u0010\u001b\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010\u001c\u00a8\u0006#"}, d2 = {"Lcom/fooddiary/data/database/dao/BeverageEntryDao;", "", "deleteAll", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAlcoholicBeverages", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/fooddiary/data/database/entities/BeverageEntry;", "getAll", "getAllByDateRange", "startDate", "", "endDate", "getById", "id", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getCaffeinatedBeverages", "getCaffeineIntake", "", "date", "getCount", "", "getHydration", "hardDelete", "insert", "", "entry", "(Lcom/fooddiary/data/database/entities/BeverageEntry;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "softDelete", "deletedAt", "Ljava/time/Instant;", "modifiedAt", "(Ljava/lang/String;Ljava/time/Instant;Ljava/time/Instant;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "update", "app_debug"})
@androidx.room.Dao()
public abstract interface BeverageEntryDao {
    
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.database.entities.BeverageEntry entry, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object update(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.database.entities.BeverageEntry entry, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM beverage_entries WHERE id = :id AND isDeleted = 0")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getById(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.fooddiary.data.database.entities.BeverageEntry> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM beverage_entries WHERE isDeleted = 0 ORDER BY timestamp DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.fooddiary.data.database.entities.BeverageEntry>> getAll();
    
    @androidx.room.Query(value = "\n        SELECT * FROM beverage_entries\n        WHERE DATE(timestamp, \'unixepoch\') BETWEEN :startDate AND :endDate\n        AND isDeleted = 0\n        ORDER BY timestamp DESC\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.fooddiary.data.database.entities.BeverageEntry>> getAllByDateRange(@org.jetbrains.annotations.NotNull()
    java.lang.String startDate, @org.jetbrains.annotations.NotNull()
    java.lang.String endDate);
    
    @androidx.room.Query(value = "\n        SELECT COALESCE(SUM(caffeineContent * volume / 1000.0), 0) as totalCaffeine\n        FROM beverage_entries\n        WHERE DATE(timestamp, \'unixepoch\') = :date\n        AND caffeineContent IS NOT NULL\n        AND isDeleted = 0\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Float> getCaffeineIntake(@org.jetbrains.annotations.NotNull()
    java.lang.String date);
    
    @androidx.room.Query(value = "\n        SELECT COALESCE(SUM(CASE\n            WHEN volumeUnit = \'L\' THEN volume * 1000\n            WHEN volumeUnit = \'OZ\' THEN volume * 29.5735\n            WHEN volumeUnit = \'CUP\' THEN volume * 240\n            ELSE volume\n        END), 0) as totalHydration\n        FROM beverage_entries\n        WHERE DATE(timestamp, \'unixepoch\') = :date\n        AND isDeleted = 0\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Float> getHydration(@org.jetbrains.annotations.NotNull()
    java.lang.String date);
    
    @androidx.room.Query(value = "\n        SELECT * FROM beverage_entries\n        WHERE caffeineContent > 0\n        AND isDeleted = 0\n        ORDER BY timestamp DESC\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.fooddiary.data.database.entities.BeverageEntry>> getCaffeinatedBeverages();
    
    @androidx.room.Query(value = "\n        SELECT * FROM beverage_entries\n        WHERE alcoholContent > 0\n        AND isDeleted = 0\n        ORDER BY timestamp DESC\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.fooddiary.data.database.entities.BeverageEntry>> getAlcoholicBeverages();
    
    @androidx.room.Query(value = "\n        UPDATE beverage_entries\n        SET isDeleted = 1, deletedAt = :deletedAt, modifiedAt = :modifiedAt\n        WHERE id = :id\n    ")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object softDelete(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.time.Instant deletedAt, @org.jetbrains.annotations.NotNull()
    java.time.Instant modifiedAt, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM beverage_entries WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object hardDelete(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM beverage_entries")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteAll(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM beverage_entries WHERE isDeleted = 0")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getCount(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
}