package com.fooddiary.data.database.entities;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0002\b%\b\u0087\b\u0018\u0000 C2\u00020\u0001:\u0001CBw\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\n\u0012\u0006\u0010\f\u001a\u00020\b\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\b\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\n\u00a2\u0006\u0002\u0010\u0013J\t\u0010/\u001a\u00020\u0003H\u00c6\u0003J\u0010\u00100\u001a\u0004\u0018\u00010\bH\u00c6\u0003\u00a2\u0006\u0002\u0010*J\u0010\u00101\u001a\u0004\u0018\u00010\nH\u00c6\u0003\u00a2\u0006\u0002\u0010%J\u0010\u00102\u001a\u0004\u0018\u00010\nH\u00c6\u0003\u00a2\u0006\u0002\u0010%J\t\u00103\u001a\u00020\u0003H\u00c6\u0003J\t\u00104\u001a\u00020\u0006H\u00c6\u0003J\t\u00105\u001a\u00020\bH\u00c6\u0003J\t\u00106\u001a\u00020\nH\u00c6\u0003J\t\u00107\u001a\u00020\nH\u00c6\u0003J\t\u00108\u001a\u00020\bH\u00c6\u0003J\t\u00109\u001a\u00020\u000eH\u00c6\u0003J\u0010\u0010:\u001a\u0004\u0018\u00010\bH\u00c6\u0003\u00a2\u0006\u0002\u0010*J\u008e\u0001\u0010;\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\n2\b\b\u0002\u0010\f\u001a\u00020\b2\b\b\u0002\u0010\r\u001a\u00020\u000e2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\nH\u00c6\u0001\u00a2\u0006\u0002\u0010<J\u0013\u0010=\u001a\u00020\u001f2\b\u0010>\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010?\u001a\u00020\nH\u00d6\u0001J\t\u0010@\u001a\u00020\u0003H\u00d6\u0001Jk\u0010A\u001a\u00020\u00002\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\b2\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\n\u00a2\u0006\u0002\u0010BR\u0011\u0010\u0014\u001a\u00020\b8F\u00a2\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\f\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0016R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0016R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001cR\u0011\u0010\u001e\u001a\u00020\u001f8F\u00a2\u0006\u0006\u001a\u0004\b\u001e\u0010 R\u0011\u0010!\u001a\u00020\u001f8F\u00a2\u0006\u0006\u001a\u0004\b!\u0010 R\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u0015\u0010\u0012\u001a\u0004\u0018\u00010\n\u00a2\u0006\n\n\u0002\u0010&\u001a\u0004\b$\u0010%R\u0015\u0010\u0011\u001a\u0004\u0018\u00010\n\u00a2\u0006\n\n\u0002\u0010&\u001a\u0004\b\'\u0010%R\u0011\u0010\u000b\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010\u0018R\u0015\u0010\u000f\u001a\u0004\u0018\u00010\b\u00a2\u0006\n\n\u0002\u0010+\u001a\u0004\b)\u0010*R\u0015\u0010\u0010\u001a\u0004\u0018\u00010\b\u00a2\u0006\n\n\u0002\u0010+\u001a\u0004\b,\u0010*R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010.\u00a8\u0006D"}, d2 = {"Lcom/fooddiary/data/database/entities/TriggerPattern;", "", "id", "", "foodName", "symptomType", "Lcom/fooddiary/data/models/SymptomType;", "correlationStrength", "", "averageTimeOffsetMinutes", "", "occurrences", "confidence", "lastCalculated", "", "pValue", "standardDeviation", "minTimeOffset", "maxTimeOffset", "(Ljava/lang/String;Ljava/lang/String;Lcom/fooddiary/data/models/SymptomType;FIIFJLjava/lang/Float;Ljava/lang/Float;Ljava/lang/Integer;Ljava/lang/Integer;)V", "averageTimeOffsetHours", "getAverageTimeOffsetHours", "()F", "getAverageTimeOffsetMinutes", "()I", "getConfidence", "getCorrelationStrength", "getFoodName", "()Ljava/lang/String;", "getId", "isHighConfidence", "", "()Z", "isStatisticallySignificant", "getLastCalculated", "()J", "getMaxTimeOffset", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getMinTimeOffset", "getOccurrences", "getPValue", "()Ljava/lang/Float;", "Ljava/lang/Float;", "getStandardDeviation", "getSymptomType", "()Lcom/fooddiary/data/models/SymptomType;", "component1", "component10", "component11", "component12", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Ljava/lang/String;Ljava/lang/String;Lcom/fooddiary/data/models/SymptomType;FIIFJLjava/lang/Float;Ljava/lang/Float;Ljava/lang/Integer;Ljava/lang/Integer;)Lcom/fooddiary/data/database/entities/TriggerPattern;", "equals", "other", "hashCode", "toString", "update", "(Ljava/lang/Float;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Float;Ljava/lang/Float;Ljava/lang/Float;Ljava/lang/Integer;Ljava/lang/Integer;)Lcom/fooddiary/data/database/entities/TriggerPattern;", "Companion", "app_release"})
@androidx.room.Entity(tableName = "trigger_patterns")
@androidx.room.TypeConverters(value = {com.fooddiary.data.database.converters.InstantConverter.class})
public final class TriggerPattern {
    @androidx.room.PrimaryKey()
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String id = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String foodName = null;
    @org.jetbrains.annotations.NotNull()
    private final com.fooddiary.data.models.SymptomType symptomType = null;
    private final float correlationStrength = 0.0F;
    private final int averageTimeOffsetMinutes = 0;
    private final int occurrences = 0;
    private final float confidence = 0.0F;
    private final long lastCalculated = 0L;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Float pValue = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Float standardDeviation = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer minTimeOffset = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer maxTimeOffset = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.fooddiary.data.database.entities.TriggerPattern.Companion Companion = null;
    
    public TriggerPattern(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String foodName, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.SymptomType symptomType, float correlationStrength, int averageTimeOffsetMinutes, int occurrences, float confidence, long lastCalculated, @org.jetbrains.annotations.Nullable()
    java.lang.Float pValue, @org.jetbrains.annotations.Nullable()
    java.lang.Float standardDeviation, @org.jetbrains.annotations.Nullable()
    java.lang.Integer minTimeOffset, @org.jetbrains.annotations.Nullable()
    java.lang.Integer maxTimeOffset) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFoodName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.models.SymptomType getSymptomType() {
        return null;
    }
    
    public final float getCorrelationStrength() {
        return 0.0F;
    }
    
    public final int getAverageTimeOffsetMinutes() {
        return 0;
    }
    
    public final int getOccurrences() {
        return 0;
    }
    
    public final float getConfidence() {
        return 0.0F;
    }
    
    public final long getLastCalculated() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Float getPValue() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Float getStandardDeviation() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getMinTimeOffset() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getMaxTimeOffset() {
        return null;
    }
    
    public final boolean isStatisticallySignificant() {
        return false;
    }
    
    public final boolean isHighConfidence() {
        return false;
    }
    
    public final float getAverageTimeOffsetHours() {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.database.entities.TriggerPattern update(@org.jetbrains.annotations.Nullable()
    java.lang.Float correlationStrength, @org.jetbrains.annotations.Nullable()
    java.lang.Integer averageTimeOffsetMinutes, @org.jetbrains.annotations.Nullable()
    java.lang.Integer occurrences, @org.jetbrains.annotations.Nullable()
    java.lang.Float confidence, @org.jetbrains.annotations.Nullable()
    java.lang.Float pValue, @org.jetbrains.annotations.Nullable()
    java.lang.Float standardDeviation, @org.jetbrains.annotations.Nullable()
    java.lang.Integer minTimeOffset, @org.jetbrains.annotations.Nullable()
    java.lang.Integer maxTimeOffset) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Float component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component11() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component12() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.models.SymptomType component3() {
        return null;
    }
    
    public final float component4() {
        return 0.0F;
    }
    
    public final int component5() {
        return 0;
    }
    
    public final int component6() {
        return 0;
    }
    
    public final float component7() {
        return 0.0F;
    }
    
    public final long component8() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Float component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.database.entities.TriggerPattern copy(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String foodName, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.SymptomType symptomType, float correlationStrength, int averageTimeOffsetMinutes, int occurrences, float confidence, long lastCalculated, @org.jetbrains.annotations.Nullable()
    java.lang.Float pValue, @org.jetbrains.annotations.Nullable()
    java.lang.Float standardDeviation, @org.jetbrains.annotations.Nullable()
    java.lang.Integer minTimeOffset, @org.jetbrains.annotations.Nullable()
    java.lang.Integer maxTimeOffset) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0002\b\b\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002Jk\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\n2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\f2\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\f\u00a2\u0006\u0002\u0010\u0013\u00a8\u0006\u0014"}, d2 = {"Lcom/fooddiary/data/database/entities/TriggerPattern$Companion;", "", "()V", "create", "Lcom/fooddiary/data/database/entities/TriggerPattern;", "foodName", "", "symptomType", "Lcom/fooddiary/data/models/SymptomType;", "correlationStrength", "", "averageTimeOffsetMinutes", "", "occurrences", "confidence", "pValue", "standardDeviation", "minTimeOffset", "maxTimeOffset", "(Ljava/lang/String;Lcom/fooddiary/data/models/SymptomType;FIIFLjava/lang/Float;Ljava/lang/Float;Ljava/lang/Integer;Ljava/lang/Integer;)Lcom/fooddiary/data/database/entities/TriggerPattern;", "app_release"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.fooddiary.data.database.entities.TriggerPattern create(@org.jetbrains.annotations.NotNull()
        java.lang.String foodName, @org.jetbrains.annotations.NotNull()
        com.fooddiary.data.models.SymptomType symptomType, float correlationStrength, int averageTimeOffsetMinutes, int occurrences, float confidence, @org.jetbrains.annotations.Nullable()
        java.lang.Float pValue, @org.jetbrains.annotations.Nullable()
        java.lang.Float standardDeviation, @org.jetbrains.annotations.Nullable()
        java.lang.Integer minTimeOffset, @org.jetbrains.annotations.Nullable()
        java.lang.Integer maxTimeOffset) {
            return null;
        }
    }
}