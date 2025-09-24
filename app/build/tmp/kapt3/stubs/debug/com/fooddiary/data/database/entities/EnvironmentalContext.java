package com.fooddiary.data.database.entities;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b%\n\u0002\u0010\u000b\n\u0002\b\u0007\b\u0087\b\u0018\u0000 ;2\u00020\u0001:\u0001;Bk\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0005\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\b\u0010\f\u001a\u0004\u0018\u00010\r\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u000b\u0012\b\u0010\u0011\u001a\u0004\u0018\u00010\u000b\u0012\b\u0010\u0012\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\u0002\u0010\u0013J\t\u0010\'\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010(\u001a\u0004\u0018\u00010\u000bH\u00c6\u0003J\u000b\u0010)\u001a\u0004\u0018\u00010\u000bH\u00c6\u0003J\t\u0010*\u001a\u00020\u0005H\u00c6\u0003J\t\u0010+\u001a\u00020\u0007H\u00c6\u0003J\t\u0010,\u001a\u00020\u0005H\u00c6\u0003J\u0010\u0010-\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001bJ\u000b\u0010.\u001a\u0004\u0018\u00010\u000bH\u00c6\u0003J\u000b\u0010/\u001a\u0004\u0018\u00010\rH\u00c6\u0003J\u000b\u00100\u001a\u0004\u0018\u00010\u000fH\u00c6\u0003J\u000b\u00101\u001a\u0004\u0018\u00010\u000bH\u00c6\u0003J\u008a\u0001\u00102\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00052\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u000b2\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u000b2\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u000bH\u00c6\u0001\u00a2\u0006\u0002\u00103J\u0013\u00104\u001a\u0002052\b\u00106\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00107\u001a\u00020\u0005H\u00d6\u0001J\t\u00108\u001a\u00020\u000bH\u00d6\u0001J\u0083\u0001\u00109\u001a\u00020\u00002\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u000b2\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u000b2\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\u0002\u0010:R\u0013\u0010\u0012\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0013\u0010\f\u001a\u0004\u0018\u00010\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0015\u0010\t\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\n\n\u0002\u0010\u001c\u001a\u0004\b\u001a\u0010\u001bR\u0013\u0010\n\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0015R\u0013\u0010\u0011\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0015R\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0011\u0010\b\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010$R\u0013\u0010\u0010\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\u0015\u00a8\u0006<"}, d2 = {"Lcom/fooddiary/data/database/entities/EnvironmentalContext;", "", "date", "Ljava/time/LocalDate;", "stressLevel", "", "sleepHours", "", "sleepQuality", "exerciseMinutes", "exerciseType", "", "exerciseIntensity", "Lcom/fooddiary/data/models/ExerciseIntensity;", "menstrualPhase", "Lcom/fooddiary/data/models/MenstrualPhase;", "weather", "location", "additionalNotes", "(Ljava/time/LocalDate;IFILjava/lang/Integer;Ljava/lang/String;Lcom/fooddiary/data/models/ExerciseIntensity;Lcom/fooddiary/data/models/MenstrualPhase;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getAdditionalNotes", "()Ljava/lang/String;", "getDate", "()Ljava/time/LocalDate;", "getExerciseIntensity", "()Lcom/fooddiary/data/models/ExerciseIntensity;", "getExerciseMinutes", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getExerciseType", "getLocation", "getMenstrualPhase", "()Lcom/fooddiary/data/models/MenstrualPhase;", "getSleepHours", "()F", "getSleepQuality", "()I", "getStressLevel", "getWeather", "component1", "component10", "component11", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/time/LocalDate;IFILjava/lang/Integer;Ljava/lang/String;Lcom/fooddiary/data/models/ExerciseIntensity;Lcom/fooddiary/data/models/MenstrualPhase;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lcom/fooddiary/data/database/entities/EnvironmentalContext;", "equals", "", "other", "hashCode", "toString", "update", "(Ljava/lang/Integer;Ljava/lang/Float;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;Lcom/fooddiary/data/models/ExerciseIntensity;Lcom/fooddiary/data/models/MenstrualPhase;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lcom/fooddiary/data/database/entities/EnvironmentalContext;", "Companion", "app_debug"})
@androidx.room.Entity(tableName = "environmental_contexts")
@androidx.room.TypeConverters(value = {com.fooddiary.data.database.converters.LocalDateConverter.class})
public final class EnvironmentalContext {
    @androidx.room.PrimaryKey()
    @org.jetbrains.annotations.NotNull()
    private final java.time.LocalDate date = null;
    private final int stressLevel = 0;
    private final float sleepHours = 0.0F;
    private final int sleepQuality = 0;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer exerciseMinutes = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String exerciseType = null;
    @org.jetbrains.annotations.Nullable()
    private final com.fooddiary.data.models.ExerciseIntensity exerciseIntensity = null;
    @org.jetbrains.annotations.Nullable()
    private final com.fooddiary.data.models.MenstrualPhase menstrualPhase = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String weather = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String location = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String additionalNotes = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.fooddiary.data.database.entities.EnvironmentalContext.Companion Companion = null;
    
    public EnvironmentalContext(@org.jetbrains.annotations.NotNull()
    java.time.LocalDate date, int stressLevel, float sleepHours, int sleepQuality, @org.jetbrains.annotations.Nullable()
    java.lang.Integer exerciseMinutes, @org.jetbrains.annotations.Nullable()
    java.lang.String exerciseType, @org.jetbrains.annotations.Nullable()
    com.fooddiary.data.models.ExerciseIntensity exerciseIntensity, @org.jetbrains.annotations.Nullable()
    com.fooddiary.data.models.MenstrualPhase menstrualPhase, @org.jetbrains.annotations.Nullable()
    java.lang.String weather, @org.jetbrains.annotations.Nullable()
    java.lang.String location, @org.jetbrains.annotations.Nullable()
    java.lang.String additionalNotes) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.LocalDate getDate() {
        return null;
    }
    
    public final int getStressLevel() {
        return 0;
    }
    
    public final float getSleepHours() {
        return 0.0F;
    }
    
    public final int getSleepQuality() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getExerciseMinutes() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getExerciseType() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.fooddiary.data.models.ExerciseIntensity getExerciseIntensity() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.fooddiary.data.models.MenstrualPhase getMenstrualPhase() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getWeather() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getLocation() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getAdditionalNotes() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.database.entities.EnvironmentalContext update(@org.jetbrains.annotations.Nullable()
    java.lang.Integer stressLevel, @org.jetbrains.annotations.Nullable()
    java.lang.Float sleepHours, @org.jetbrains.annotations.Nullable()
    java.lang.Integer sleepQuality, @org.jetbrains.annotations.Nullable()
    java.lang.Integer exerciseMinutes, @org.jetbrains.annotations.Nullable()
    java.lang.String exerciseType, @org.jetbrains.annotations.Nullable()
    com.fooddiary.data.models.ExerciseIntensity exerciseIntensity, @org.jetbrains.annotations.Nullable()
    com.fooddiary.data.models.MenstrualPhase menstrualPhase, @org.jetbrains.annotations.Nullable()
    java.lang.String weather, @org.jetbrains.annotations.Nullable()
    java.lang.String location, @org.jetbrains.annotations.Nullable()
    java.lang.String additionalNotes) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.LocalDate component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component11() {
        return null;
    }
    
    public final int component2() {
        return 0;
    }
    
    public final float component3() {
        return 0.0F;
    }
    
    public final int component4() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.fooddiary.data.models.ExerciseIntensity component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.fooddiary.data.models.MenstrualPhase component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.database.entities.EnvironmentalContext copy(@org.jetbrains.annotations.NotNull()
    java.time.LocalDate date, int stressLevel, float sleepHours, int sleepQuality, @org.jetbrains.annotations.Nullable()
    java.lang.Integer exerciseMinutes, @org.jetbrains.annotations.Nullable()
    java.lang.String exerciseType, @org.jetbrains.annotations.Nullable()
    com.fooddiary.data.models.ExerciseIntensity exerciseIntensity, @org.jetbrains.annotations.Nullable()
    com.fooddiary.data.models.MenstrualPhase menstrualPhase, @org.jetbrains.annotations.Nullable()
    java.lang.String weather, @org.jetbrains.annotations.Nullable()
    java.lang.String location, @org.jetbrains.annotations.Nullable()
    java.lang.String additionalNotes) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u007f\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00102\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u00122\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u000e2\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u000e2\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u000e\u00a2\u0006\u0002\u0010\u0016\u00a8\u0006\u0017"}, d2 = {"Lcom/fooddiary/data/database/entities/EnvironmentalContext$Companion;", "", "()V", "create", "Lcom/fooddiary/data/database/entities/EnvironmentalContext;", "date", "Ljava/time/LocalDate;", "stressLevel", "", "sleepHours", "", "sleepQuality", "exerciseMinutes", "exerciseType", "", "exerciseIntensity", "Lcom/fooddiary/data/models/ExerciseIntensity;", "menstrualPhase", "Lcom/fooddiary/data/models/MenstrualPhase;", "weather", "location", "additionalNotes", "(Ljava/time/LocalDate;IFILjava/lang/Integer;Ljava/lang/String;Lcom/fooddiary/data/models/ExerciseIntensity;Lcom/fooddiary/data/models/MenstrualPhase;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lcom/fooddiary/data/database/entities/EnvironmentalContext;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.fooddiary.data.database.entities.EnvironmentalContext create(@org.jetbrains.annotations.NotNull()
        java.time.LocalDate date, int stressLevel, float sleepHours, int sleepQuality, @org.jetbrains.annotations.Nullable()
        java.lang.Integer exerciseMinutes, @org.jetbrains.annotations.Nullable()
        java.lang.String exerciseType, @org.jetbrains.annotations.Nullable()
        com.fooddiary.data.models.ExerciseIntensity exerciseIntensity, @org.jetbrains.annotations.Nullable()
        com.fooddiary.data.models.MenstrualPhase menstrualPhase, @org.jetbrains.annotations.Nullable()
        java.lang.String weather, @org.jetbrains.annotations.Nullable()
        java.lang.String location, @org.jetbrains.annotations.Nullable()
        java.lang.String additionalNotes) {
            return null;
        }
    }
}