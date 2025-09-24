package com.fooddiary.data.database.entities;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b7\b\u0087\b\u0018\u0000 M2\u00020\u0001:\u0001MB\u00a5\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\n\u0012\b\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\r\u001a\u0004\u0018\u00010\n\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u0012\u000e\u0010\u0010\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0011\u0012\b\u0010\u0012\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0013\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0005\u0012\b\u0010\u0015\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0016\u001a\u00020\u0017\u0012\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0019J\t\u00104\u001a\u00020\u0003H\u00c6\u0003J\u0011\u00105\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0011H\u00c6\u0003J\u000b\u00106\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u00107\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u00108\u001a\u00020\u0005H\u00c6\u0003J\u000b\u00109\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010:\u001a\u00020\u0017H\u00c6\u0003J\u000b\u0010;\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010<\u001a\u00020\u0005H\u00c6\u0003J\t\u0010=\u001a\u00020\u0003H\u00c6\u0003J\t\u0010>\u001a\u00020\bH\u00c6\u0003J\t\u0010?\u001a\u00020\nH\u00c6\u0003J\u0010\u0010@\u001a\u0004\u0018\u00010\nH\u00c6\u0003\u00a2\u0006\u0002\u0010\u001bJ\u000b\u0010A\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u0010\u0010B\u001a\u0004\u0018\u00010\nH\u00c6\u0003\u00a2\u0006\u0002\u0010\u001bJ\u000b\u0010C\u001a\u0004\u0018\u00010\u000fH\u00c6\u0003J\u00c6\u0001\u0010D\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0010\b\u0002\u0010\u0010\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u00112\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0014\u001a\u00020\u00052\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0016\u001a\u00020\u00172\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u0005H\u00c6\u0001\u00a2\u0006\u0002\u0010EJ\u0013\u0010F\u001a\u00020\u00172\b\u0010G\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010H\u001a\u00020\nH\u00d6\u0001J\u0006\u0010I\u001a\u00020\u0000J\t\u0010J\u001a\u00020\u0003H\u00d6\u0001J}\u0010K\u001a\u00020\u00002\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0010\b\u0002\u0010\u0010\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u00112\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010LR\u0015\u0010\r\u001a\u0004\u0018\u00010\n\u00a2\u0006\n\n\u0002\u0010\u001c\u001a\u0004\b\u001a\u0010\u001bR\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\u0014\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0013\u0010\u0018\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010 R\u0015\u0010\u000b\u001a\u0004\u0018\u00010\n\u00a2\u0006\n\n\u0002\u0010\u001c\u001a\u0004\b\"\u0010\u001bR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0011\u0010%\u001a\u00020\u00178F\u00a2\u0006\u0006\u001a\u0004\b%\u0010&R\u0011\u0010\u0016\u001a\u00020\u0017\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010&R\u0011\u0010\'\u001a\u00020\u00178F\u00a2\u0006\u0006\u001a\u0004\b\'\u0010&R\u0013\u0010\f\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010$R\u0013\u0010\u0015\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010 R\u0013\u0010\u0012\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010$R\u0013\u0010\u0013\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010$R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010-R\u0019\u0010\u0010\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b.\u0010/R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b0\u0010 R\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b1\u0010$R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b2\u00103\u00a8\u0006N"}, d2 = {"Lcom/fooddiary/data/database/entities/SymptomEvent;", "", "id", "", "timestamp", "Ljava/time/Instant;", "timezone", "type", "Lcom/fooddiary/data/models/SymptomType;", "severity", "", "duration", "location", "bristolScale", "bristolStoolType", "Lcom/fooddiary/data/models/BristolStoolType;", "suspectedTriggers", "", "notes", "photoPath", "createdAt", "modifiedAt", "isDeleted", "", "deletedAt", "(Ljava/lang/String;Ljava/time/Instant;Ljava/lang/String;Lcom/fooddiary/data/models/SymptomType;ILjava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Lcom/fooddiary/data/models/BristolStoolType;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;Ljava/time/Instant;Ljava/time/Instant;ZLjava/time/Instant;)V", "getBristolScale", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getBristolStoolType", "()Lcom/fooddiary/data/models/BristolStoolType;", "getCreatedAt", "()Ljava/time/Instant;", "getDeletedAt", "getDuration", "getId", "()Ljava/lang/String;", "isActive", "()Z", "isOngoing", "getLocation", "getModifiedAt", "getNotes", "getPhotoPath", "getSeverity", "()I", "getSuspectedTriggers", "()Ljava/util/List;", "getTimestamp", "getTimezone", "getType", "()Lcom/fooddiary/data/models/SymptomType;", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;Ljava/time/Instant;Ljava/lang/String;Lcom/fooddiary/data/models/SymptomType;ILjava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Lcom/fooddiary/data/models/BristolStoolType;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;Ljava/time/Instant;Ljava/time/Instant;ZLjava/time/Instant;)Lcom/fooddiary/data/database/entities/SymptomEvent;", "equals", "other", "hashCode", "softDelete", "toString", "update", "(Lcom/fooddiary/data/models/SymptomType;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Lcom/fooddiary/data/models/BristolStoolType;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;)Lcom/fooddiary/data/database/entities/SymptomEvent;", "Companion", "app_debug"})
@androidx.room.Entity(tableName = "symptom_events")
@androidx.room.TypeConverters(value = {com.fooddiary.data.database.converters.InstantConverter.class, com.fooddiary.data.database.converters.StringListConverter.class, com.fooddiary.data.database.converters.BristolStoolTypeConverter.class})
public final class SymptomEvent {
    @androidx.room.PrimaryKey()
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String id = null;
    @org.jetbrains.annotations.NotNull()
    private final java.time.Instant timestamp = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String timezone = null;
    @org.jetbrains.annotations.NotNull()
    private final com.fooddiary.data.models.SymptomType type = null;
    private final int severity = 0;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer duration = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String location = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer bristolScale = null;
    @org.jetbrains.annotations.Nullable()
    private final com.fooddiary.data.models.BristolStoolType bristolStoolType = null;
    @org.jetbrains.annotations.Nullable()
    private final java.util.List<java.lang.String> suspectedTriggers = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String notes = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String photoPath = null;
    @org.jetbrains.annotations.NotNull()
    private final java.time.Instant createdAt = null;
    @org.jetbrains.annotations.Nullable()
    private final java.time.Instant modifiedAt = null;
    private final boolean isDeleted = false;
    @org.jetbrains.annotations.Nullable()
    private final java.time.Instant deletedAt = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.fooddiary.data.database.entities.SymptomEvent.Companion Companion = null;
    
    public SymptomEvent(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.time.Instant timestamp, @org.jetbrains.annotations.NotNull()
    java.lang.String timezone, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.SymptomType type, int severity, @org.jetbrains.annotations.Nullable()
    java.lang.Integer duration, @org.jetbrains.annotations.Nullable()
    java.lang.String location, @org.jetbrains.annotations.Nullable()
    java.lang.Integer bristolScale, @org.jetbrains.annotations.Nullable()
    com.fooddiary.data.models.BristolStoolType bristolStoolType, @org.jetbrains.annotations.Nullable()
    java.util.List<java.lang.String> suspectedTriggers, @org.jetbrains.annotations.Nullable()
    java.lang.String notes, @org.jetbrains.annotations.Nullable()
    java.lang.String photoPath, @org.jetbrains.annotations.NotNull()
    java.time.Instant createdAt, @org.jetbrains.annotations.Nullable()
    java.time.Instant modifiedAt, boolean isDeleted, @org.jetbrains.annotations.Nullable()
    java.time.Instant deletedAt) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.Instant getTimestamp() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTimezone() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.models.SymptomType getType() {
        return null;
    }
    
    public final int getSeverity() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getDuration() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getLocation() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getBristolScale() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.fooddiary.data.models.BristolStoolType getBristolStoolType() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<java.lang.String> getSuspectedTriggers() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getNotes() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getPhotoPath() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.Instant getCreatedAt() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.time.Instant getModifiedAt() {
        return null;
    }
    
    public final boolean isDeleted() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.time.Instant getDeletedAt() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.database.entities.SymptomEvent softDelete() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.database.entities.SymptomEvent update(@org.jetbrains.annotations.Nullable()
    com.fooddiary.data.models.SymptomType type, @org.jetbrains.annotations.Nullable()
    java.lang.Integer severity, @org.jetbrains.annotations.Nullable()
    java.lang.Integer duration, @org.jetbrains.annotations.Nullable()
    java.lang.String location, @org.jetbrains.annotations.Nullable()
    java.lang.Integer bristolScale, @org.jetbrains.annotations.Nullable()
    com.fooddiary.data.models.BristolStoolType bristolStoolType, @org.jetbrains.annotations.Nullable()
    java.util.List<java.lang.String> suspectedTriggers, @org.jetbrains.annotations.Nullable()
    java.lang.String notes, @org.jetbrains.annotations.Nullable()
    java.lang.String photoPath) {
        return null;
    }
    
    public final boolean isOngoing() {
        return false;
    }
    
    public final boolean isActive() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.List<java.lang.String> component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component11() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component12() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.Instant component13() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.time.Instant component14() {
        return null;
    }
    
    public final boolean component15() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.time.Instant component16() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.Instant component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.models.SymptomType component4() {
        return null;
    }
    
    public final int component5() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.fooddiary.data.models.BristolStoolType component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.database.entities.SymptomEvent copy(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.time.Instant timestamp, @org.jetbrains.annotations.NotNull()
    java.lang.String timezone, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.SymptomType type, int severity, @org.jetbrains.annotations.Nullable()
    java.lang.Integer duration, @org.jetbrains.annotations.Nullable()
    java.lang.String location, @org.jetbrains.annotations.Nullable()
    java.lang.Integer bristolScale, @org.jetbrains.annotations.Nullable()
    com.fooddiary.data.models.BristolStoolType bristolStoolType, @org.jetbrains.annotations.Nullable()
    java.util.List<java.lang.String> suspectedTriggers, @org.jetbrains.annotations.Nullable()
    java.lang.String notes, @org.jetbrains.annotations.Nullable()
    java.lang.String photoPath, @org.jetbrains.annotations.NotNull()
    java.time.Instant createdAt, @org.jetbrains.annotations.Nullable()
    java.time.Instant modifiedAt, boolean isDeleted, @org.jetbrains.annotations.Nullable()
    java.time.Instant deletedAt) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0089\u0001\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\f2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\f2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0010\b\u0002\u0010\u0012\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u00132\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\f2\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\f\u00a2\u0006\u0002\u0010\u0016\u00a8\u0006\u0017"}, d2 = {"Lcom/fooddiary/data/database/entities/SymptomEvent$Companion;", "", "()V", "create", "Lcom/fooddiary/data/database/entities/SymptomEvent;", "type", "Lcom/fooddiary/data/models/SymptomType;", "severity", "", "timestamp", "Ljava/time/Instant;", "timezone", "", "duration", "location", "bristolScale", "bristolStoolType", "Lcom/fooddiary/data/models/BristolStoolType;", "suspectedTriggers", "", "notes", "photoPath", "(Lcom/fooddiary/data/models/SymptomType;ILjava/time/Instant;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Lcom/fooddiary/data/models/BristolStoolType;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;)Lcom/fooddiary/data/database/entities/SymptomEvent;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.fooddiary.data.database.entities.SymptomEvent create(@org.jetbrains.annotations.NotNull()
        com.fooddiary.data.models.SymptomType type, int severity, @org.jetbrains.annotations.NotNull()
        java.time.Instant timestamp, @org.jetbrains.annotations.NotNull()
        java.lang.String timezone, @org.jetbrains.annotations.Nullable()
        java.lang.Integer duration, @org.jetbrains.annotations.Nullable()
        java.lang.String location, @org.jetbrains.annotations.Nullable()
        java.lang.Integer bristolScale, @org.jetbrains.annotations.Nullable()
        com.fooddiary.data.models.BristolStoolType bristolStoolType, @org.jetbrains.annotations.Nullable()
        java.util.List<java.lang.String> suspectedTriggers, @org.jetbrains.annotations.Nullable()
        java.lang.String notes, @org.jetbrains.annotations.Nullable()
        java.lang.String photoPath) {
            return null;
        }
    }
}