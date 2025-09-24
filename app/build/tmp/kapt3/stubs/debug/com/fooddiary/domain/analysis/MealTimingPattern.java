package com.fooddiary.domain.analysis;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u001a\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001BQ\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f\u0012\u0006\u0010\u000e\u001a\u00020\u0003\u0012\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00030\f\u00a2\u0006\u0002\u0010\u0010J\t\u0010\u001e\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001f\u001a\u00020\u0005H\u00c6\u0003J\t\u0010 \u001a\u00020\u0005H\u00c6\u0003J\t\u0010!\u001a\u00020\bH\u00c6\u0003J\t\u0010\"\u001a\u00020\nH\u00c6\u0003J\u000f\u0010#\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u00c6\u0003J\t\u0010$\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00030\fH\u00c6\u0003Je\u0010&\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\n2\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f2\b\b\u0002\u0010\u000e\u001a\u00020\u00032\u000e\b\u0002\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00030\fH\u00c6\u0001J\u0013\u0010\'\u001a\u00020(2\b\u0010)\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010*\u001a\u00020\u0005H\u00d6\u0001J\t\u0010+\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0014R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00030\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0017R\u0011\u0010\u000e\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001a\u00a8\u0006,"}, d2 = {"Lcom/fooddiary/domain/analysis/MealTimingPattern;", "", "timeOfDay", "", "hour", "", "mealCount", "averageSymptomSeverity", "", "symptomFrequency", "", "mostCommonSymptoms", "", "Lcom/fooddiary/data/models/SymptomType;", "riskLevel", "recommendations", "(Ljava/lang/String;IIDFLjava/util/List;Ljava/lang/String;Ljava/util/List;)V", "getAverageSymptomSeverity", "()D", "getHour", "()I", "getMealCount", "getMostCommonSymptoms", "()Ljava/util/List;", "getRecommendations", "getRiskLevel", "()Ljava/lang/String;", "getSymptomFrequency", "()F", "getTimeOfDay", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class MealTimingPattern {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String timeOfDay = null;
    private final int hour = 0;
    private final int mealCount = 0;
    private final double averageSymptomSeverity = 0.0;
    private final float symptomFrequency = 0.0F;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.fooddiary.data.models.SymptomType> mostCommonSymptoms = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String riskLevel = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> recommendations = null;
    
    public MealTimingPattern(@org.jetbrains.annotations.NotNull()
    java.lang.String timeOfDay, int hour, int mealCount, double averageSymptomSeverity, float symptomFrequency, @org.jetbrains.annotations.NotNull()
    java.util.List<? extends com.fooddiary.data.models.SymptomType> mostCommonSymptoms, @org.jetbrains.annotations.NotNull()
    java.lang.String riskLevel, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> recommendations) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTimeOfDay() {
        return null;
    }
    
    public final int getHour() {
        return 0;
    }
    
    public final int getMealCount() {
        return 0;
    }
    
    public final double getAverageSymptomSeverity() {
        return 0.0;
    }
    
    public final float getSymptomFrequency() {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.fooddiary.data.models.SymptomType> getMostCommonSymptoms() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRiskLevel() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getRecommendations() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    public final int component2() {
        return 0;
    }
    
    public final int component3() {
        return 0;
    }
    
    public final double component4() {
        return 0.0;
    }
    
    public final float component5() {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.fooddiary.data.models.SymptomType> component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.domain.analysis.MealTimingPattern copy(@org.jetbrains.annotations.NotNull()
    java.lang.String timeOfDay, int hour, int mealCount, double averageSymptomSeverity, float symptomFrequency, @org.jetbrains.annotations.NotNull()
    java.util.List<? extends com.fooddiary.data.models.SymptomType> mostCommonSymptoms, @org.jetbrains.annotations.NotNull()
    java.lang.String riskLevel, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> recommendations) {
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