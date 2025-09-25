package com.fooddiary.data.database.dao;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\b\bg\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\u00020\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0014\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006H\'J\u0014\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006H\'J$\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u00062\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\fH\'J\u0018\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u00a7@\u00a2\u0006\u0002\u0010\u0012J\u0014\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006H\'J\u0018\u0010\u0014\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0015\u001a\u00020\fH\u00a7@\u00a2\u0006\u0002\u0010\u0016J\u001c\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u00062\u0006\u0010\u0018\u001a\u00020\u0019H\'J\u001c\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u00062\u0006\u0010\u001b\u001a\u00020\fH\'J\u001c\u0010\u001c\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u00062\u0006\u0010\u0010\u001a\u00020\u0011H\'J\u000e\u0010\u001d\u001a\u00020\u0019H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u001e\u001a\u00020\u00192\u0006\u0010\u001f\u001a\u00020\fH\u00a7@\u00a2\u0006\u0002\u0010\u0016J\u0016\u0010 \u001a\u00020\u00192\u0006\u0010\u0010\u001a\u00020\u0011H\u00a7@\u00a2\u0006\u0002\u0010\u0012J$\u0010!\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020#H\u00a7@\u00a2\u0006\u0002\u0010%J\u0010\u0010&\u001a\u0004\u0018\u00010\u0019H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\'\u001a\u00020\u00192\u0006\u0010(\u001a\u00020#H\u00a7@\u00a2\u0006\u0002\u0010)J\u0014\u0010*\u001a\b\u0012\u0004\u0012\u00020+0\u0007H\u00a7@\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010,\u001a\u00020\u00032\u0006\u0010\u0015\u001a\u00020\fH\u00a7@\u00a2\u0006\u0002\u0010\u0016J\u0016\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u00100J&\u00101\u001a\u00020\u00032\u0006\u0010\u0015\u001a\u00020\f2\u0006\u00102\u001a\u00020#2\u0006\u00103\u001a\u00020#H\u00a7@\u00a2\u0006\u0002\u00104J\u0016\u00105\u001a\u00020\u00032\u0006\u0010/\u001a\u00020\bH\u00a7@\u00a2\u0006\u0002\u00100\u00a8\u00066"}, d2 = {"Lcom/fooddiary/data/database/dao/SymptomEventDao;", "", "deleteAll", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getActiveSymptoms", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/fooddiary/data/database/entities/SymptomEvent;", "getAll", "getAllByDateRange", "startDate", "", "endDate", "getAverageSeverityByType", "", "type", "Lcom/fooddiary/data/models/SymptomType;", "(Lcom/fooddiary/data/models/SymptomType;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getBowelMovements", "getById", "id", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getBySeverity", "minSeverity", "", "getByTrigger", "trigger", "getByType", "getCount", "getCountByDate", "date", "getCountByType", "getEventsInTimeWindow", "startTime", "Ljava/time/Instant;", "endTime", "(Ljava/time/Instant;Ljava/time/Instant;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getLongestSymptomFreeStreak", "getSymptomFreeDays", "sinceTimestamp", "(Ljava/time/Instant;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getSymptomFrequency", "Lcom/fooddiary/data/models/SymptomFrequency;", "hardDelete", "insert", "", "event", "(Lcom/fooddiary/data/database/entities/SymptomEvent;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "softDelete", "deletedAt", "modifiedAt", "(Ljava/lang/String;Ljava/time/Instant;Ljava/time/Instant;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "update", "app_release"})
@androidx.room.Dao()
public abstract interface SymptomEventDao {
    
    @androidx.room.Insert()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.database.entities.SymptomEvent event, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object update(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.database.entities.SymptomEvent event, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM symptom_events WHERE id = :id AND isDeleted = 0")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getById(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.fooddiary.data.database.entities.SymptomEvent> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM symptom_events WHERE isDeleted = 0 ORDER BY timestamp DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.fooddiary.data.database.entities.SymptomEvent>> getAll();
    
    @androidx.room.Query(value = "\n        SELECT * FROM symptom_events\n        WHERE DATE(timestamp, \'unixepoch\') BETWEEN :startDate AND :endDate\n        AND isDeleted = 0\n        ORDER BY timestamp DESC\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.fooddiary.data.database.entities.SymptomEvent>> getAllByDateRange(@org.jetbrains.annotations.NotNull()
    java.lang.String startDate, @org.jetbrains.annotations.NotNull()
    java.lang.String endDate);
    
    @androidx.room.Query(value = "SELECT * FROM symptom_events WHERE type = :type AND isDeleted = 0 ORDER BY timestamp DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.fooddiary.data.database.entities.SymptomEvent>> getByType(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.SymptomType type);
    
    @androidx.room.Query(value = "SELECT * FROM symptom_events WHERE severity >= :minSeverity AND isDeleted = 0 ORDER BY timestamp DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.fooddiary.data.database.entities.SymptomEvent>> getBySeverity(int minSeverity);
    
    @androidx.room.Query(value = "\n        SELECT * FROM symptom_events\n        WHERE duration IS NULL OR duration > 0\n        AND isDeleted = 0\n        ORDER BY timestamp DESC\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.fooddiary.data.database.entities.SymptomEvent>> getActiveSymptoms();
    
    @androidx.room.Query(value = "\n        SELECT * FROM symptom_events\n        WHERE timestamp BETWEEN :startTime AND :endTime\n        AND isDeleted = 0\n        ORDER BY timestamp ASC\n    ")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getEventsInTimeWindow(@org.jetbrains.annotations.NotNull()
    java.time.Instant startTime, @org.jetbrains.annotations.NotNull()
    java.time.Instant endTime, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.fooddiary.data.database.entities.SymptomEvent>> $completion);
    
    @androidx.room.Query(value = "\n        SELECT AVG(severity) FROM symptom_events\n        WHERE type = :type AND isDeleted = 0\n    ")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAverageSeverityByType(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.SymptomType type, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Float> $completion);
    
    @androidx.room.Query(value = "\n        SELECT COUNT(*) FROM symptom_events\n        WHERE type = :type AND isDeleted = 0\n    ")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getCountByType(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.SymptomType type, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Query(value = "\n        SELECT type, COUNT(*) as count FROM symptom_events\n        WHERE isDeleted = 0\n        GROUP BY type\n        ORDER BY count DESC\n    ")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getSymptomFrequency(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.fooddiary.data.models.SymptomFrequency>> $completion);
    
    @androidx.room.Query(value = "\n        SELECT * FROM symptom_events\n        WHERE bristolScale IS NOT NULL AND isDeleted = 0\n        ORDER BY timestamp DESC\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.fooddiary.data.database.entities.SymptomEvent>> getBowelMovements();
    
    @androidx.room.Query(value = "\n        SELECT * FROM symptom_events\n        WHERE suspectedTriggers LIKE \'%\' || :trigger || \'%\'\n        AND isDeleted = 0\n        ORDER BY timestamp DESC\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.fooddiary.data.database.entities.SymptomEvent>> getByTrigger(@org.jetbrains.annotations.NotNull()
    java.lang.String trigger);
    
    @androidx.room.Query(value = "\n        UPDATE symptom_events\n        SET isDeleted = 1, deletedAt = :deletedAt, modifiedAt = :modifiedAt\n        WHERE id = :id\n    ")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object softDelete(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.time.Instant deletedAt, @org.jetbrains.annotations.NotNull()
    java.time.Instant modifiedAt, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM symptom_events WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object hardDelete(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM symptom_events")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteAll(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM symptom_events WHERE isDeleted = 0")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getCount(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Query(value = "\n        SELECT COUNT(*) FROM symptom_events\n        WHERE DATE(timestamp, \'unixepoch\') = :date\n        AND isDeleted = 0\n    ")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getCountByDate(@org.jetbrains.annotations.NotNull()
    java.lang.String date, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Query(value = "\n        SELECT COUNT(DISTINCT DATE(timestamp, \'unixepoch\')) FROM symptom_events\n        WHERE isDeleted = 0 AND timestamp >= :sinceTimestamp\n    ")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getSymptomFreeDays(@org.jetbrains.annotations.NotNull()
    java.time.Instant sinceTimestamp, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Query(value = "\n        SELECT MAX(consecutive_days) FROM (\n            SELECT COUNT(*) as consecutive_days\n            FROM symptom_events\n            WHERE isDeleted = 0\n            GROUP BY date(timestamp, \'unixepoch\')\n            ORDER BY timestamp\n        )\n    ")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getLongestSymptomFreeStreak(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
}