package com.fooddiary.domain.analysis;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\u0006\n\u0002\b\u001e\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001BU\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e\u00a2\u0006\u0002\u0010\u000fJ\t\u0010\"\u001a\u00020\u0003H\u00c6\u0003J\t\u0010#\u001a\u00020\u0005H\u00c6\u0003J\t\u0010$\u001a\u00020\u0007H\u00c6\u0003J\t\u0010%\u001a\u00020\tH\u00c6\u0003J\u000b\u0010&\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u0010\u0010\'\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0018J\u0010\u0010(\u001a\u0004\u0018\u00010\tH\u00c6\u0003\u00a2\u0006\u0002\u0010\u001bJ\u0010\u0010)\u001a\u0004\u0018\u00010\u000eH\u00c6\u0003\u00a2\u0006\u0002\u0010 Jf\u0010*\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\t2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000eH\u00c6\u0001\u00a2\u0006\u0002\u0010+J\u0013\u0010,\u001a\u00020-2\b\u0010.\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010/\u001a\u00020\u0007H\u00d6\u0001J\t\u00100\u001a\u00020\u0005H\u00d6\u0001R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0013\u0010\n\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0015R\u0015\u0010\u000b\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\n\n\u0002\u0010\u0019\u001a\u0004\b\u0017\u0010\u0018R\u0015\u0010\f\u001a\u0004\u0018\u00010\t\u00a2\u0006\n\n\u0002\u0010\u001c\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0015\u0010\r\u001a\u0004\u0018\u00010\u000e\u00a2\u0006\n\n\u0002\u0010!\u001a\u0004\b\u001f\u0010 \u00a8\u00061"}, d2 = {"Lcom/fooddiary/domain/analysis/DailyRhythm;", "", "rhythmType", "Lcom/fooddiary/domain/analysis/DailyRhythmType;", "description", "", "averageTime", "", "consistency", "", "mealType", "peakHour", "peakPercentage", "standardDeviation", "", "(Lcom/fooddiary/domain/analysis/DailyRhythmType;Ljava/lang/String;IFLjava/lang/String;Ljava/lang/Integer;Ljava/lang/Float;Ljava/lang/Double;)V", "getAverageTime", "()I", "getConsistency", "()F", "getDescription", "()Ljava/lang/String;", "getMealType", "getPeakHour", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getPeakPercentage", "()Ljava/lang/Float;", "Ljava/lang/Float;", "getRhythmType", "()Lcom/fooddiary/domain/analysis/DailyRhythmType;", "getStandardDeviation", "()Ljava/lang/Double;", "Ljava/lang/Double;", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "(Lcom/fooddiary/domain/analysis/DailyRhythmType;Ljava/lang/String;IFLjava/lang/String;Ljava/lang/Integer;Ljava/lang/Float;Ljava/lang/Double;)Lcom/fooddiary/domain/analysis/DailyRhythm;", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class DailyRhythm {
    @org.jetbrains.annotations.NotNull()
    private final com.fooddiary.domain.analysis.DailyRhythmType rhythmType = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String description = null;
    private final int averageTime = 0;
    private final float consistency = 0.0F;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String mealType = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer peakHour = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Float peakPercentage = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double standardDeviation = null;
    
    public DailyRhythm(@org.jetbrains.annotations.NotNull()
    com.fooddiary.domain.analysis.DailyRhythmType rhythmType, @org.jetbrains.annotations.NotNull()
    java.lang.String description, int averageTime, float consistency, @org.jetbrains.annotations.Nullable()
    java.lang.String mealType, @org.jetbrains.annotations.Nullable()
    java.lang.Integer peakHour, @org.jetbrains.annotations.Nullable()
    java.lang.Float peakPercentage, @org.jetbrains.annotations.Nullable()
    java.lang.Double standardDeviation) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.domain.analysis.DailyRhythmType getRhythmType() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDescription() {
        return null;
    }
    
    public final int getAverageTime() {
        return 0;
    }
    
    public final float getConsistency() {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getMealType() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getPeakHour() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Float getPeakPercentage() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getStandardDeviation() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.domain.analysis.DailyRhythmType component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    public final int component3() {
        return 0;
    }
    
    public final float component4() {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Float component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.domain.analysis.DailyRhythm copy(@org.jetbrains.annotations.NotNull()
    com.fooddiary.domain.analysis.DailyRhythmType rhythmType, @org.jetbrains.annotations.NotNull()
    java.lang.String description, int averageTime, float consistency, @org.jetbrains.annotations.Nullable()
    java.lang.String mealType, @org.jetbrains.annotations.Nullable()
    java.lang.Integer peakHour, @org.jetbrains.annotations.Nullable()
    java.lang.Float peakPercentage, @org.jetbrains.annotations.Nullable()
    java.lang.Double standardDeviation) {
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
}