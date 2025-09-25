package com.fooddiary.data.database.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\t\n\u0002\b\f\bg\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0014\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006H\'J$\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u00062\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\'J\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0007H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0007H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\u000f\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0010\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\u0011J\u000e\u0010\u0012\u001a\u00020\u0013H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\u0011J$\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0018H\u00a7@\u00a2\u0006\u0002\u0010\u001aJ\u001c\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u0006\u0010\u001c\u001a\u00020\u0013H\u00a7@\u00a2\u0006\u0002\u0010\u001dJ\u001c\u0010\u001e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u00062\u0006\u0010\u001c\u001a\u00020\u0013H\'J\u0016\u0010\u001f\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\u0011J\u0016\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010#J\u001c\u0010$\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u00062\u0006\u0010%\u001a\u00020\u000bH\'J\u001c\u0010&\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u00062\u0006\u0010\'\u001a\u00020\u000bH\'J&\u0010(\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u000b2\u0006\u0010)\u001a\u00020\u00182\u0006\u0010*\u001a\u00020\u0018H\u00a7@\u00a2\u0006\u0002\u0010+J\u0016\u0010,\u001a\u00020\u00032\u0006\u0010\"\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u0010#\u00a8\u0006-"}, d2 = {"Lcom/fooddiary/data/database/dao/FoodEntryDao;", "", "deleteAll", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAll", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/fooddiary/data/database/entities/FoodEntry;", "getAllByDateRange", "startDate", "", "endDate", "getAllFoodNames", "getAllIngredients", "getById", "id", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getCount", "", "getCountByDate", "date", "getEntriesInTimeWindow", "startTime", "Ljava/time/Instant;", "endTime", "(Ljava/time/Instant;Ljava/time/Instant;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMostFrequent", "limit", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getRecent", "hardDelete", "insert", "", "entry", "(Lcom/fooddiary/data/database/entities/FoodEntry;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "searchByIngredient", "ingredient", "searchByName", "query", "softDelete", "deletedAt", "modifiedAt", "(Ljava/lang/String;Ljava/time/Instant;Ljava/time/Instant;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "update", "app_release"})
@androidx.room.Dao()
public abstract interface FoodEntryDao {
    
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.database.entities.FoodEntry entry, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object update(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.database.entities.FoodEntry entry, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM food_entries WHERE id = :id AND isDeleted = 0")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getById(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.fooddiary.data.database.entities.FoodEntry> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM food_entries WHERE isDeleted = 0 ORDER BY timestamp DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.fooddiary.data.database.entities.FoodEntry>> getAll();
    
    @androidx.room.Query(value = "\n        SELECT * FROM food_entries\n        WHERE DATE(timestamp, \'unixepoch\') BETWEEN :startDate AND :endDate\n        AND isDeleted = 0\n        ORDER BY timestamp DESC\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.fooddiary.data.database.entities.FoodEntry>> getAllByDateRange(@org.jetbrains.annotations.NotNull()
    java.lang.String startDate, @org.jetbrains.annotations.NotNull()
    java.lang.String endDate);
    
    @androidx.room.Query(value = "SELECT * FROM food_entries WHERE isDeleted = 0 ORDER BY timestamp DESC LIMIT :limit")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.fooddiary.data.database.entities.FoodEntry>> getRecent(int limit);
    
    @androidx.room.Query(value = "\n        SELECT * FROM food_entries\n        WHERE name LIKE \'%\' || :query || \'%\'\n        AND isDeleted = 0\n        ORDER BY timestamp DESC\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.fooddiary.data.database.entities.FoodEntry>> searchByName(@org.jetbrains.annotations.NotNull()
    java.lang.String query);
    
    @androidx.room.Query(value = "\n        SELECT * FROM food_entries\n        WHERE isDeleted = 0\n        GROUP BY name\n        ORDER BY COUNT(*) DESC\n        LIMIT :limit\n    ")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getMostFrequent(int limit, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.fooddiary.data.database.entities.FoodEntry>> $completion);
    
    @androidx.room.Query(value = "\n        SELECT * FROM food_entries\n        WHERE ingredients LIKE \'%\' || :ingredient || \'%\'\n        AND isDeleted = 0\n        ORDER BY timestamp DESC\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.fooddiary.data.database.entities.FoodEntry>> searchByIngredient(@org.jetbrains.annotations.NotNull()
    java.lang.String ingredient);
    
    @androidx.room.Query(value = "\n        SELECT DISTINCT name FROM food_entries\n        WHERE isDeleted = 0\n        ORDER BY name ASC\n    ")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllFoodNames(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<java.lang.String>> $completion);
    
    @androidx.room.Query(value = "\n        SELECT DISTINCT ingredients FROM food_entries\n        WHERE isDeleted = 0 AND ingredients IS NOT NULL\n    ")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllIngredients(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<java.lang.String>> $completion);
    
    @androidx.room.Query(value = "\n        UPDATE food_entries\n        SET isDeleted = 1, deletedAt = :deletedAt, modifiedAt = :modifiedAt\n        WHERE id = :id\n    ")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object softDelete(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.time.Instant deletedAt, @org.jetbrains.annotations.NotNull()
    java.time.Instant modifiedAt, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM food_entries WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object hardDelete(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM food_entries")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteAll(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM food_entries WHERE isDeleted = 0")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getCount(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Query(value = "\n        SELECT COUNT(*) FROM food_entries\n        WHERE DATE(timestamp, \'unixepoch\') = :date\n        AND isDeleted = 0\n    ")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getCountByDate(@org.jetbrains.annotations.NotNull()
    java.lang.String date, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Query(value = "\n        SELECT * FROM food_entries\n        WHERE timestamp BETWEEN :startTime AND :endTime\n        AND isDeleted = 0\n        ORDER BY timestamp ASC\n    ")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getEntriesInTimeWindow(@org.jetbrains.annotations.NotNull()
    java.time.Instant startTime, @org.jetbrains.annotations.NotNull()
    java.time.Instant endTime, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.fooddiary.data.database.entities.FoodEntry>> $completion);
}