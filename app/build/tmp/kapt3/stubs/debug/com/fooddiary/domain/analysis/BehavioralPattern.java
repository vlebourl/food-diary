package com.fooddiary.domain.analysis;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0015\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B=\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\u0002\u0010\fJ\t\u0010\u0018\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\u0007H\u00c6\u0003J\u000b\u0010\u001c\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u000bH\u00c6\u0003\u00a2\u0006\u0002\u0010\u000eJN\u0010\u001e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000bH\u00c6\u0001\u00a2\u0006\u0002\u0010\u001fJ\u0013\u0010 \u001a\u00020!2\b\u0010\"\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010#\u001a\u00020\u000bH\u00d6\u0001J\t\u0010$\u001a\u00020\u0005H\u00d6\u0001R\u0015\u0010\n\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\n\n\u0002\u0010\u000f\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\b\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0011R\u0013\u0010\t\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0013R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017\u00a8\u0006%"}, d2 = {"Lcom/fooddiary/domain/analysis/BehavioralPattern;", "", "patternType", "Lcom/fooddiary/domain/analysis/BehavioralPatternType;", "description", "", "frequency", "", "confidence", "mealType", "averageDuration", "", "(Lcom/fooddiary/domain/analysis/BehavioralPatternType;Ljava/lang/String;FFLjava/lang/String;Ljava/lang/Integer;)V", "getAverageDuration", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getConfidence", "()F", "getDescription", "()Ljava/lang/String;", "getFrequency", "getMealType", "getPatternType", "()Lcom/fooddiary/domain/analysis/BehavioralPatternType;", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "(Lcom/fooddiary/domain/analysis/BehavioralPatternType;Ljava/lang/String;FFLjava/lang/String;Ljava/lang/Integer;)Lcom/fooddiary/domain/analysis/BehavioralPattern;", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class BehavioralPattern {
    @org.jetbrains.annotations.NotNull()
    private final com.fooddiary.domain.analysis.BehavioralPatternType patternType = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String description = null;
    private final float frequency = 0.0F;
    private final float confidence = 0.0F;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String mealType = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer averageDuration = null;
    
    public BehavioralPattern(@org.jetbrains.annotations.NotNull()
    com.fooddiary.domain.analysis.BehavioralPatternType patternType, @org.jetbrains.annotations.NotNull()
    java.lang.String description, float frequency, float confidence, @org.jetbrains.annotations.Nullable()
    java.lang.String mealType, @org.jetbrains.annotations.Nullable()
    java.lang.Integer averageDuration) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.domain.analysis.BehavioralPatternType getPatternType() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDescription() {
        return null;
    }
    
    public final float getFrequency() {
        return 0.0F;
    }
    
    public final float getConfidence() {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getMealType() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getAverageDuration() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.domain.analysis.BehavioralPatternType component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    public final float component3() {
        return 0.0F;
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
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.domain.analysis.BehavioralPattern copy(@org.jetbrains.annotations.NotNull()
    com.fooddiary.domain.analysis.BehavioralPatternType patternType, @org.jetbrains.annotations.NotNull()
    java.lang.String description, float frequency, float confidence, @org.jetbrains.annotations.Nullable()
    java.lang.String mealType, @org.jetbrains.annotations.Nullable()
    java.lang.Integer averageDuration) {
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