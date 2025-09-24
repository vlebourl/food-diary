package com.fooddiary.data.database.entities;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b3\n\u0002\u0010\b\n\u0002\b\u0006\b\u0087\b\u0018\u0000 L2\u00020\u0001:\u0001LB\u009d\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000b\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u000b\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0013\u0012\b\u0010\u0014\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0005\u0012\b\u0010\u0016\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0017\u001a\u00020\u0011\u0012\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0019J\t\u00102\u001a\u00020\u0003H\u00c6\u0003J\t\u00103\u001a\u00020\u0011H\u00c6\u0003J\t\u00104\u001a\u00020\u0013H\u00c6\u0003J\u000b\u00105\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u00106\u001a\u00020\u0005H\u00c6\u0003J\u000b\u00107\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u00108\u001a\u00020\u0011H\u00c6\u0003J\u000b\u00109\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010:\u001a\u00020\u0005H\u00c6\u0003J\t\u0010;\u001a\u00020\u0003H\u00c6\u0003J\t\u0010<\u001a\u00020\u0003H\u00c6\u0003J\t\u0010=\u001a\u00020\tH\u00c6\u0003J\t\u0010>\u001a\u00020\u000bH\u00c6\u0003J\t\u0010?\u001a\u00020\rH\u00c6\u0003J\u0010\u0010@\u001a\u0004\u0018\u00010\u000bH\u00c6\u0003\u00a2\u0006\u0002\u0010\u001bJ\u0010\u0010A\u001a\u0004\u0018\u00010\u000bH\u00c6\u0003\u00a2\u0006\u0002\u0010\u001bJ\u00b8\u0001\u0010B\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\r2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000b2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u000b2\b\b\u0002\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u00132\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0015\u001a\u00020\u00052\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0017\u001a\u00020\u00112\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u0005H\u00c6\u0001\u00a2\u0006\u0002\u0010CJ\u0013\u0010D\u001a\u00020\u00112\b\u0010E\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010F\u001a\u00020GH\u00d6\u0001J\u0006\u0010H\u001a\u00020\u0000J\t\u0010I\u001a\u00020\u0003H\u00d6\u0001Jw\u0010J\u001a\u00020\u00002\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000b2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u000b2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00112\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00132\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010KR\u0015\u0010\u000f\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\n\n\u0002\u0010\u001c\u001a\u0004\b\u001a\u0010\u001bR\u0015\u0010\u000e\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\n\n\u0002\u0010\u001c\u001a\u0004\b\u001d\u0010\u001bR\u0011\u0010\u0010\u001a\u00020\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0011\u0010\u0015\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0013\u0010\u0018\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010!R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0011\u0010\u0017\u001a\u00020\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u001fR\u0013\u0010\u0016\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010!R\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010$R\u0013\u0010\u0014\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010$R\u0011\u0010\u0012\u001a\u00020\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010)R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010!R\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010$R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010-R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b.\u0010/R\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b0\u00101\u00a8\u0006M"}, d2 = {"Lcom/fooddiary/data/database/entities/BeverageEntry;", "", "id", "", "timestamp", "Ljava/time/Instant;", "timezone", "name", "type", "Lcom/fooddiary/data/models/BeverageType;", "volume", "", "volumeUnit", "Lcom/fooddiary/data/models/VolumeUnit;", "caffeineContent", "alcoholContent", "carbonation", "", "temperature", "Lcom/fooddiary/data/models/Temperature;", "notes", "createdAt", "modifiedAt", "isDeleted", "deletedAt", "(Ljava/lang/String;Ljava/time/Instant;Ljava/lang/String;Ljava/lang/String;Lcom/fooddiary/data/models/BeverageType;FLcom/fooddiary/data/models/VolumeUnit;Ljava/lang/Float;Ljava/lang/Float;ZLcom/fooddiary/data/models/Temperature;Ljava/lang/String;Ljava/time/Instant;Ljava/time/Instant;ZLjava/time/Instant;)V", "getAlcoholContent", "()Ljava/lang/Float;", "Ljava/lang/Float;", "getCaffeineContent", "getCarbonation", "()Z", "getCreatedAt", "()Ljava/time/Instant;", "getDeletedAt", "getId", "()Ljava/lang/String;", "getModifiedAt", "getName", "getNotes", "getTemperature", "()Lcom/fooddiary/data/models/Temperature;", "getTimestamp", "getTimezone", "getType", "()Lcom/fooddiary/data/models/BeverageType;", "getVolume", "()F", "getVolumeUnit", "()Lcom/fooddiary/data/models/VolumeUnit;", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;Ljava/time/Instant;Ljava/lang/String;Ljava/lang/String;Lcom/fooddiary/data/models/BeverageType;FLcom/fooddiary/data/models/VolumeUnit;Ljava/lang/Float;Ljava/lang/Float;ZLcom/fooddiary/data/models/Temperature;Ljava/lang/String;Ljava/time/Instant;Ljava/time/Instant;ZLjava/time/Instant;)Lcom/fooddiary/data/database/entities/BeverageEntry;", "equals", "other", "hashCode", "", "softDelete", "toString", "update", "(Ljava/lang/String;Lcom/fooddiary/data/models/BeverageType;Ljava/lang/Float;Lcom/fooddiary/data/models/VolumeUnit;Ljava/lang/Float;Ljava/lang/Float;Ljava/lang/Boolean;Lcom/fooddiary/data/models/Temperature;Ljava/lang/String;)Lcom/fooddiary/data/database/entities/BeverageEntry;", "Companion", "app_release"})
@androidx.room.Entity(tableName = "beverage_entries")
@androidx.room.TypeConverters(value = {com.fooddiary.data.database.converters.InstantConverter.class})
public final class BeverageEntry {
    @androidx.room.PrimaryKey()
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String id = null;
    @org.jetbrains.annotations.NotNull()
    private final java.time.Instant timestamp = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String timezone = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String name = null;
    @org.jetbrains.annotations.NotNull()
    private final com.fooddiary.data.models.BeverageType type = null;
    private final float volume = 0.0F;
    @org.jetbrains.annotations.NotNull()
    private final com.fooddiary.data.models.VolumeUnit volumeUnit = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Float caffeineContent = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Float alcoholContent = null;
    private final boolean carbonation = false;
    @org.jetbrains.annotations.NotNull()
    private final com.fooddiary.data.models.Temperature temperature = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String notes = null;
    @org.jetbrains.annotations.NotNull()
    private final java.time.Instant createdAt = null;
    @org.jetbrains.annotations.Nullable()
    private final java.time.Instant modifiedAt = null;
    private final boolean isDeleted = false;
    @org.jetbrains.annotations.Nullable()
    private final java.time.Instant deletedAt = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.fooddiary.data.database.entities.BeverageEntry.Companion Companion = null;
    
    public BeverageEntry(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.time.Instant timestamp, @org.jetbrains.annotations.NotNull()
    java.lang.String timezone, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.BeverageType type, float volume, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.VolumeUnit volumeUnit, @org.jetbrains.annotations.Nullable()
    java.lang.Float caffeineContent, @org.jetbrains.annotations.Nullable()
    java.lang.Float alcoholContent, boolean carbonation, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.Temperature temperature, @org.jetbrains.annotations.Nullable()
    java.lang.String notes, @org.jetbrains.annotations.NotNull()
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
    public final java.lang.String getName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.models.BeverageType getType() {
        return null;
    }
    
    public final float getVolume() {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.models.VolumeUnit getVolumeUnit() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Float getCaffeineContent() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Float getAlcoholContent() {
        return null;
    }
    
    public final boolean getCarbonation() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.models.Temperature getTemperature() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getNotes() {
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
    public final com.fooddiary.data.database.entities.BeverageEntry softDelete() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.database.entities.BeverageEntry update(@org.jetbrains.annotations.Nullable()
    java.lang.String name, @org.jetbrains.annotations.Nullable()
    com.fooddiary.data.models.BeverageType type, @org.jetbrains.annotations.Nullable()
    java.lang.Float volume, @org.jetbrains.annotations.Nullable()
    com.fooddiary.data.models.VolumeUnit volumeUnit, @org.jetbrains.annotations.Nullable()
    java.lang.Float caffeineContent, @org.jetbrains.annotations.Nullable()
    java.lang.Float alcoholContent, @org.jetbrains.annotations.Nullable()
    java.lang.Boolean carbonation, @org.jetbrains.annotations.Nullable()
    com.fooddiary.data.models.Temperature temperature, @org.jetbrains.annotations.Nullable()
    java.lang.String notes) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    public final boolean component10() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.models.Temperature component11() {
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
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.models.BeverageType component5() {
        return null;
    }
    
    public final float component6() {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.models.VolumeUnit component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Float component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Float component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.database.entities.BeverageEntry copy(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.time.Instant timestamp, @org.jetbrains.annotations.NotNull()
    java.lang.String timezone, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.BeverageType type, float volume, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.VolumeUnit volumeUnit, @org.jetbrains.annotations.Nullable()
    java.lang.Float caffeineContent, @org.jetbrains.annotations.Nullable()
    java.lang.Float alcoholContent, boolean carbonation, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.Temperature temperature, @org.jetbrains.annotations.Nullable()
    java.lang.String notes, @org.jetbrains.annotations.NotNull()
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002Jw\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u00062\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\n2\b\b\u0002\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u00152\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\u0002\u0010\u0017\u00a8\u0006\u0018"}, d2 = {"Lcom/fooddiary/data/database/entities/BeverageEntry$Companion;", "", "()V", "create", "Lcom/fooddiary/data/database/entities/BeverageEntry;", "name", "", "type", "Lcom/fooddiary/data/models/BeverageType;", "volume", "", "volumeUnit", "Lcom/fooddiary/data/models/VolumeUnit;", "timestamp", "Ljava/time/Instant;", "timezone", "caffeineContent", "alcoholContent", "carbonation", "", "temperature", "Lcom/fooddiary/data/models/Temperature;", "notes", "(Ljava/lang/String;Lcom/fooddiary/data/models/BeverageType;FLcom/fooddiary/data/models/VolumeUnit;Ljava/time/Instant;Ljava/lang/String;Ljava/lang/Float;Ljava/lang/Float;ZLcom/fooddiary/data/models/Temperature;Ljava/lang/String;)Lcom/fooddiary/data/database/entities/BeverageEntry;", "app_release"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.fooddiary.data.database.entities.BeverageEntry create(@org.jetbrains.annotations.NotNull()
        java.lang.String name, @org.jetbrains.annotations.NotNull()
        com.fooddiary.data.models.BeverageType type, float volume, @org.jetbrains.annotations.NotNull()
        com.fooddiary.data.models.VolumeUnit volumeUnit, @org.jetbrains.annotations.NotNull()
        java.time.Instant timestamp, @org.jetbrains.annotations.NotNull()
        java.lang.String timezone, @org.jetbrains.annotations.Nullable()
        java.lang.Float caffeineContent, @org.jetbrains.annotations.Nullable()
        java.lang.Float alcoholContent, boolean carbonation, @org.jetbrains.annotations.NotNull()
        com.fooddiary.data.models.Temperature temperature, @org.jetbrains.annotations.Nullable()
        java.lang.String notes) {
            return null;
        }
    }
}