package com.fooddiary.domain.analysis;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0014\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B[\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0003\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0003\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\u0003\u00a2\u0006\u0002\u0010\u0011J\u000f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\u000f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003H\u00c6\u0003J\u000f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\b0\u0003H\u00c6\u0003J\u000f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\n0\u0003H\u00c6\u0003J\t\u0010 \u001a\u00020\fH\u00c6\u0003J\t\u0010!\u001a\u00020\u000eH\u00c6\u0003J\u000f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00100\u0003H\u00c6\u0003Jm\u0010#\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u00032\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u00032\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u00032\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u000e2\u000e\b\u0002\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\u0003H\u00c6\u0001J\u0013\u0010$\u001a\u00020%2\b\u0010&\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\'\u001a\u00020(H\u00d6\u0001J\t\u0010)\u001a\u00020\u0010H\u00d6\u0001R\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0017R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0017R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0017R\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0017\u00a8\u0006*"}, d2 = {"Lcom/fooddiary/domain/analysis/EatingWindowAnalysisResult;", "", "optimalWindows", "", "Lcom/fooddiary/domain/analysis/OptimalEatingWindow;", "eatingSpeedCorrelations", "Lcom/fooddiary/domain/analysis/EatingSpeedCorrelation;", "mealTimingPatterns", "Lcom/fooddiary/domain/analysis/MealTimingPattern;", "fastingBenefits", "Lcom/fooddiary/domain/analysis/FastingPeriodAnalysis;", "circadianAlignment", "Lcom/fooddiary/domain/analysis/CircadianAlignmentAnalysis;", "analysisConfidence", "", "recommendations", "", "(Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Lcom/fooddiary/domain/analysis/CircadianAlignmentAnalysis;DLjava/util/List;)V", "getAnalysisConfidence", "()D", "getCircadianAlignment", "()Lcom/fooddiary/domain/analysis/CircadianAlignmentAnalysis;", "getEatingSpeedCorrelations", "()Ljava/util/List;", "getFastingBenefits", "getMealTimingPatterns", "getOptimalWindows", "getRecommendations", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
public final class EatingWindowAnalysisResult {
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.fooddiary.domain.analysis.OptimalEatingWindow> optimalWindows = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.fooddiary.domain.analysis.EatingSpeedCorrelation> eatingSpeedCorrelations = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.fooddiary.domain.analysis.MealTimingPattern> mealTimingPatterns = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.fooddiary.domain.analysis.FastingPeriodAnalysis> fastingBenefits = null;
    @org.jetbrains.annotations.NotNull()
    private final com.fooddiary.domain.analysis.CircadianAlignmentAnalysis circadianAlignment = null;
    private final double analysisConfidence = 0.0;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> recommendations = null;
    
    public EatingWindowAnalysisResult(@org.jetbrains.annotations.NotNull()
    java.util.List<com.fooddiary.domain.analysis.OptimalEatingWindow> optimalWindows, @org.jetbrains.annotations.NotNull()
    java.util.List<com.fooddiary.domain.analysis.EatingSpeedCorrelation> eatingSpeedCorrelations, @org.jetbrains.annotations.NotNull()
    java.util.List<com.fooddiary.domain.analysis.MealTimingPattern> mealTimingPatterns, @org.jetbrains.annotations.NotNull()
    java.util.List<com.fooddiary.domain.analysis.FastingPeriodAnalysis> fastingBenefits, @org.jetbrains.annotations.NotNull()
    com.fooddiary.domain.analysis.CircadianAlignmentAnalysis circadianAlignment, double analysisConfidence, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> recommendations) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.fooddiary.domain.analysis.OptimalEatingWindow> getOptimalWindows() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.fooddiary.domain.analysis.EatingSpeedCorrelation> getEatingSpeedCorrelations() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.fooddiary.domain.analysis.MealTimingPattern> getMealTimingPatterns() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.fooddiary.domain.analysis.FastingPeriodAnalysis> getFastingBenefits() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.domain.analysis.CircadianAlignmentAnalysis getCircadianAlignment() {
        return null;
    }
    
    public final double getAnalysisConfidence() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getRecommendations() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.fooddiary.domain.analysis.OptimalEatingWindow> component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.fooddiary.domain.analysis.EatingSpeedCorrelation> component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.fooddiary.domain.analysis.MealTimingPattern> component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.fooddiary.domain.analysis.FastingPeriodAnalysis> component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.domain.analysis.CircadianAlignmentAnalysis component5() {
        return null;
    }
    
    public final double component6() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.domain.analysis.EatingWindowAnalysisResult copy(@org.jetbrains.annotations.NotNull()
    java.util.List<com.fooddiary.domain.analysis.OptimalEatingWindow> optimalWindows, @org.jetbrains.annotations.NotNull()
    java.util.List<com.fooddiary.domain.analysis.EatingSpeedCorrelation> eatingSpeedCorrelations, @org.jetbrains.annotations.NotNull()
    java.util.List<com.fooddiary.domain.analysis.MealTimingPattern> mealTimingPatterns, @org.jetbrains.annotations.NotNull()
    java.util.List<com.fooddiary.domain.analysis.FastingPeriodAnalysis> fastingBenefits, @org.jetbrains.annotations.NotNull()
    com.fooddiary.domain.analysis.CircadianAlignmentAnalysis circadianAlignment, double analysisConfidence, @org.jetbrains.annotations.NotNull()
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