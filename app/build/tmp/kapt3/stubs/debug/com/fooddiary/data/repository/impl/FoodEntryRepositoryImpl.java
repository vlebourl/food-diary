package com.fooddiary.data.repository.impl;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\r\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\tJ\u000e\u0010\n\u001a\u00020\u0006H\u0096@\u00a2\u0006\u0002\u0010\u000bJ\u0016\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0096@\u00a2\u0006\u0002\u0010\u0010J\u0014\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00130\u0012H\u0016J$\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00130\u00122\u0006\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\u000fH\u0016J\u0018\u0010\u0017\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0018\u001a\u00020\u0019H\u0096@\u00a2\u0006\u0002\u0010\u001aJ\u001c\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00130\u00122\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J$\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\r0\u00122\u0006\u0010\u0015\u001a\u00020\u001d2\u0006\u0010\u0016\u001a\u00020\u001dH\u0096@\u00a2\u0006\u0002\u0010\u001fJ\u001c\u0010 \u001a\b\u0012\u0004\u0012\u00020\u001d0\u00132\u0006\u0010!\u001a\u00020\rH\u0096@\u00a2\u0006\u0002\u0010\"J\u001c\u0010#\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00130\u00122\u0006\u0010!\u001a\u00020\rH\u0016J\u001c\u0010$\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00130\u00122\u0006\u0010%\u001a\u00020\u001dH\u0016J\u0016\u0010&\u001a\u00020\u00192\u0006\u0010\u0007\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\tJ\u001c\u0010\'\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00130\u00122\u0006\u0010(\u001a\u00020\u001dH\u0016J\u0016\u0010)\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\tR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006*"}, d2 = {"Lcom/fooddiary/data/repository/impl/FoodEntryRepositoryImpl;", "", "foodEntryDao", "Lcom/fooddiary/data/database/dao/FoodEntryDao;", "(Lcom/fooddiary/data/database/dao/FoodEntryDao;)V", "delete", "", "foodEntry", "Lcom/fooddiary/data/database/entities/FoodEntry;", "(Lcom/fooddiary/data/database/entities/FoodEntry;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteAll", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteOlderThan", "", "date", "Ljava/time/Instant;", "(Ljava/time/Instant;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAll", "Lkotlinx/coroutines/flow/Flow;", "", "getByDateRange", "startDate", "endDate", "getById", "id", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getByMealType", "mealType", "", "getCountByDateRange", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getFrequentFoods", "limit", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getRecentEntries", "getWithIngredient", "ingredient", "insert", "searchByFoodName", "query", "update", "app_debug"})
public final class FoodEntryRepositoryImpl {
    @org.jetbrains.annotations.NotNull()
    private final com.fooddiary.data.database.dao.FoodEntryDao foodEntryDao = null;
    
    @javax.inject.Inject()
    public FoodEntryRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.database.dao.FoodEntryDao foodEntryDao) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.database.entities.FoodEntry foodEntry, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object update(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.database.entities.FoodEntry foodEntry, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object delete(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.database.entities.FoodEntry foodEntry, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.fooddiary.data.database.entities.FoodEntry> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.fooddiary.data.database.entities.FoodEntry>> getAll() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.fooddiary.data.database.entities.FoodEntry>> getByDateRange(@org.jetbrains.annotations.NotNull()
    java.time.Instant startDate, @org.jetbrains.annotations.NotNull()
    java.time.Instant endDate) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.fooddiary.data.database.entities.FoodEntry>> getByMealType(@org.jetbrains.annotations.NotNull()
    java.lang.String mealType) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.fooddiary.data.database.entities.FoodEntry>> searchByFoodName(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.fooddiary.data.database.entities.FoodEntry>> getRecentEntries(int limit) {
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
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getFrequentFoods(int limit, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<java.lang.String>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.fooddiary.data.database.entities.FoodEntry>> getWithIngredient(@org.jetbrains.annotations.NotNull()
    java.lang.String ingredient) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object deleteAll(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}