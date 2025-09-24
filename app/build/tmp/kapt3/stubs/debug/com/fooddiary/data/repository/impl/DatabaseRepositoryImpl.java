package com.fooddiary.data.repository.impl;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0019\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0096@\u00a2\u0006\u0002\u0010\u000bJ\u000e\u0010\f\u001a\u00020\rH\u0096@\u00a2\u0006\u0002\u0010\u000eJ\u000e\u0010\u000f\u001a\u00020\u0010H\u0096@\u00a2\u0006\u0002\u0010\u000eJ\u000e\u0010\u0011\u001a\u00020\u0012H\u0096@\u00a2\u0006\u0002\u0010\u000eJ\b\u0010\u0013\u001a\u00020\nH\u0016J\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0096@\u00a2\u0006\u0002\u0010\u000eJ\u000e\u0010\u0016\u001a\u00020\nH\u0096@\u00a2\u0006\u0002\u0010\u000eJ\u000e\u0010\u0017\u001a\u00020\nH\u0096@\u00a2\u0006\u0002\u0010\u000eJ\u0016\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0015H\u0096@\u00a2\u0006\u0002\u0010\u001bJ\u000e\u0010\u001c\u001a\u00020\u001dH\u0096@\u00a2\u0006\u0002\u0010\u000eR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001e"}, d2 = {"Lcom/fooddiary/data/repository/impl/DatabaseRepositoryImpl;", "", "context", "Landroid/content/Context;", "database", "Lcom/fooddiary/data/database/FoodDiaryDatabase;", "(Landroid/content/Context;Lcom/fooddiary/data/database/FoodDiaryDatabase;)V", "cleanupDataOlderThan", "Lcom/fooddiary/data/repository/impl/CleanupResult;", "days", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "clearAllData", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "exportAllData", "Lcom/fooddiary/data/repository/impl/ExportResult;", "getDatabaseSize", "", "getDatabaseVersion", "getOldestEntryDate", "", "getTotalFoodEntries", "getTotalSymptomEvents", "importData", "Lcom/fooddiary/data/repository/impl/ImportResult;", "filePath", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "optimizeDatabase", "Lcom/fooddiary/data/repository/impl/OptimizationResult;", "app_debug"})
public final class DatabaseRepositoryImpl {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.fooddiary.data.database.FoodDiaryDatabase database = null;
    
    @javax.inject.Inject()
    public DatabaseRepositoryImpl(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.database.FoodDiaryDatabase database) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getTotalFoodEntries(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getTotalSymptomEvents(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getDatabaseSize(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Double> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getOldestEntryDate(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object exportAllData(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.fooddiary.data.repository.impl.ExportResult> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object importData(@org.jetbrains.annotations.NotNull()
    java.lang.String filePath, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.fooddiary.data.repository.impl.ImportResult> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object clearAllData(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object optimizeDatabase(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.fooddiary.data.repository.impl.OptimizationResult> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object cleanupDataOlderThan(int days, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.fooddiary.data.repository.impl.CleanupResult> $completion) {
        return null;
    }
    
    public int getDatabaseVersion() {
        return 0;
    }
}