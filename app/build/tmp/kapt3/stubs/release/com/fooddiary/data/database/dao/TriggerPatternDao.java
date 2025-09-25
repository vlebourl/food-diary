package com.fooddiary.data.database.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0007\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\u0007\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u0014\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\nH\'J\u001c\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\n2\u0006\u0010\u000e\u001a\u00020\u0005H\'J\u0018\u0010\u000f\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u001c\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\n2\u0006\u0010\u0011\u001a\u00020\u0012H\'J\u000e\u0010\u0013\u001a\u00020\u0014H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u001e\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\n2\b\b\u0002\u0010\u0016\u001a\u00020\u0017H\'J\u000e\u0010\u0018\u001a\u00020\u0014H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u0014\u0010\u0019\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\nH\'J\u0016\u0010\u001a\u001a\u00020\u00032\u0006\u0010\u001b\u001a\u00020\fH\u00a7@\u00a2\u0006\u0002\u0010\u001cJ\u0016\u0010\u001d\u001a\u00020\u00032\u0006\u0010\u001b\u001a\u00020\fH\u00a7@\u00a2\u0006\u0002\u0010\u001c\u00a8\u0006\u001e"}, d2 = {"Lcom/fooddiary/data/database/dao/TriggerPatternDao;", "", "delete", "", "id", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteAll", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAll", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/fooddiary/data/database/entities/TriggerPattern;", "getByFood", "foodName", "getById", "getBySymptomType", "type", "Lcom/fooddiary/data/models/SymptomType;", "getCount", "", "getHighConfidence", "minConfidence", "", "getSignificantCount", "getStatisticallySignificant", "insert", "pattern", "(Lcom/fooddiary/data/database/entities/TriggerPattern;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "update", "app_release"})
@androidx.room.Dao()
public abstract interface TriggerPatternDao {
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.database.entities.TriggerPattern pattern, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object update(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.database.entities.TriggerPattern pattern, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM trigger_patterns ORDER BY correlationStrength DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.fooddiary.data.database.entities.TriggerPattern>> getAll();
    
    @androidx.room.Query(value = "SELECT * FROM trigger_patterns WHERE symptomType = :type ORDER BY correlationStrength DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.fooddiary.data.database.entities.TriggerPattern>> getBySymptomType(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.SymptomType type);
    
    @androidx.room.Query(value = "SELECT * FROM trigger_patterns WHERE foodName = :foodName ORDER BY correlationStrength DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.fooddiary.data.database.entities.TriggerPattern>> getByFood(@org.jetbrains.annotations.NotNull()
    java.lang.String foodName);
    
    @androidx.room.Query(value = "SELECT * FROM trigger_patterns WHERE correlationStrength >= :minConfidence ORDER BY correlationStrength DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.fooddiary.data.database.entities.TriggerPattern>> getHighConfidence(float minConfidence);
    
    @androidx.room.Query(value = "\n        SELECT * FROM trigger_patterns\n        WHERE occurrences >= 10\n        AND correlationStrength >= 0.6\n        AND confidence >= 0.95\n        AND pValue < 0.05\n        ORDER BY correlationStrength DESC\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.fooddiary.data.database.entities.TriggerPattern>> getStatisticallySignificant();
    
    @androidx.room.Query(value = "SELECT * FROM trigger_patterns WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getById(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.fooddiary.data.database.entities.TriggerPattern> $completion);
    
    @androidx.room.Query(value = "DELETE FROM trigger_patterns WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object delete(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM trigger_patterns")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteAll(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM trigger_patterns")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getCount(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Query(value = "\n        SELECT COUNT(*) FROM trigger_patterns\n        WHERE correlationStrength >= 0.6\n        AND confidence >= 0.95\n        AND occurrences >= 10\n    ")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getSignificantCount(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}