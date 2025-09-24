package com.fooddiary.domain.analysis;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010$\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001BQ\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0006\u0010\t\u001a\u00020\n\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0007\u00a2\u0006\u0002\u0010\rJ\u0015\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\u0015\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\u000f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\nH\u00c6\u0003J\u000f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\f0\u0007H\u00c6\u0003J_\u0010\u001b\u001a\u00020\u00002\u0014\b\u0002\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u00032\u0014\b\u0002\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\b\b\u0002\u0010\t\u001a\u00020\n2\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0007H\u00c6\u0001J\u0013\u0010\u001c\u001a\u00020\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001f\u001a\u00020\u0004H\u00d6\u0001J\t\u0010 \u001a\u00020\fH\u00d6\u0001R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001d\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u001d\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0011R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0013\u00a8\u0006!"}, d2 = {"Lcom/fooddiary/domain/analysis/EatingPatternAnalysis;", "", "mealTimeDistribution", "", "", "symptomTimeDistribution", "timeOfDayCorrelations", "", "Lcom/fooddiary/domain/analysis/TimeOfDayCorrelation;", "eatingSpeedAnalysis", "Lcom/fooddiary/domain/analysis/EatingSpeedCorrelation;", "recommendations", "", "(Ljava/util/Map;Ljava/util/Map;Ljava/util/List;Lcom/fooddiary/domain/analysis/EatingSpeedCorrelation;Ljava/util/List;)V", "getEatingSpeedAnalysis", "()Lcom/fooddiary/domain/analysis/EatingSpeedCorrelation;", "getMealTimeDistribution", "()Ljava/util/Map;", "getRecommendations", "()Ljava/util/List;", "getSymptomTimeDistribution", "getTimeOfDayCorrelations", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class EatingPatternAnalysis {
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<java.lang.Integer, java.lang.Integer> mealTimeDistribution = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<java.lang.Integer, java.lang.Integer> symptomTimeDistribution = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.fooddiary.domain.analysis.TimeOfDayCorrelation> timeOfDayCorrelations = null;
    @org.jetbrains.annotations.NotNull()
    private final com.fooddiary.domain.analysis.EatingSpeedCorrelation eatingSpeedAnalysis = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> recommendations = null;
    
    public EatingPatternAnalysis(@org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.Integer, java.lang.Integer> mealTimeDistribution, @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.Integer, java.lang.Integer> symptomTimeDistribution, @org.jetbrains.annotations.NotNull()
    java.util.List<com.fooddiary.domain.analysis.TimeOfDayCorrelation> timeOfDayCorrelations, @org.jetbrains.annotations.NotNull()
    com.fooddiary.domain.analysis.EatingSpeedCorrelation eatingSpeedAnalysis, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> recommendations) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<java.lang.Integer, java.lang.Integer> getMealTimeDistribution() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<java.lang.Integer, java.lang.Integer> getSymptomTimeDistribution() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.fooddiary.domain.analysis.TimeOfDayCorrelation> getTimeOfDayCorrelations() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.domain.analysis.EatingSpeedCorrelation getEatingSpeedAnalysis() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getRecommendations() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<java.lang.Integer, java.lang.Integer> component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<java.lang.Integer, java.lang.Integer> component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.fooddiary.domain.analysis.TimeOfDayCorrelation> component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.domain.analysis.EatingSpeedCorrelation component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.domain.analysis.EatingPatternAnalysis copy(@org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.Integer, java.lang.Integer> mealTimeDistribution, @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.Integer, java.lang.Integer> symptomTimeDistribution, @org.jetbrains.annotations.NotNull()
    java.util.List<com.fooddiary.domain.analysis.TimeOfDayCorrelation> timeOfDayCorrelations, @org.jetbrains.annotations.NotNull()
    com.fooddiary.domain.analysis.EatingSpeedCorrelation eatingSpeedAnalysis, @org.jetbrains.annotations.NotNull()
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