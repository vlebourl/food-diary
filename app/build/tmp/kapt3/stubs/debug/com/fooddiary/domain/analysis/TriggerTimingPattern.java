package com.fooddiary.domain.analysis;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BG\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0\n\u0012\u0006\u0010\u000b\u001a\u00020\u0003\u0012\u0006\u0010\f\u001a\u00020\b\u00a2\u0006\u0002\u0010\rJ\t\u0010\u0018\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\bH\u00c6\u0003J\u0015\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0\nH\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\bH\u00c6\u0003JW\u0010\u001e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\b\b\u0002\u0010\u0007\u001a\u00020\b2\u0014\b\u0002\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0\n2\b\b\u0002\u0010\u000b\u001a\u00020\u00032\b\b\u0002\u0010\f\u001a\u00020\bH\u00c6\u0001J\u0013\u0010\u001f\u001a\u00020 2\b\u0010!\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\"\u001a\u00020#H\u00d6\u0001J\t\u0010$\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u000b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0013R\u001d\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\f\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u000f\u00a8\u0006%"}, d2 = {"Lcom/fooddiary/domain/analysis/TriggerTimingPattern;", "", "foodName", "", "episodes", "", "Lcom/fooddiary/domain/analysis/TriggerEpisode;", "averageOnsetMinutes", "", "onsetRange", "Lkotlin/Pair;", "mostCommonOnsetWindow", "severityCorrelation", "(Ljava/lang/String;Ljava/util/List;DLkotlin/Pair;Ljava/lang/String;D)V", "getAverageOnsetMinutes", "()D", "getEpisodes", "()Ljava/util/List;", "getFoodName", "()Ljava/lang/String;", "getMostCommonOnsetWindow", "getOnsetRange", "()Lkotlin/Pair;", "getSeverityCorrelation", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
public final class TriggerTimingPattern {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String foodName = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.fooddiary.domain.analysis.TriggerEpisode> episodes = null;
    private final double averageOnsetMinutes = 0.0;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Pair<java.lang.Double, java.lang.Double> onsetRange = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String mostCommonOnsetWindow = null;
    private final double severityCorrelation = 0.0;
    
    public TriggerTimingPattern(@org.jetbrains.annotations.NotNull()
    java.lang.String foodName, @org.jetbrains.annotations.NotNull()
    java.util.List<com.fooddiary.domain.analysis.TriggerEpisode> episodes, double averageOnsetMinutes, @org.jetbrains.annotations.NotNull()
    kotlin.Pair<java.lang.Double, java.lang.Double> onsetRange, @org.jetbrains.annotations.NotNull()
    java.lang.String mostCommonOnsetWindow, double severityCorrelation) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFoodName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.fooddiary.domain.analysis.TriggerEpisode> getEpisodes() {
        return null;
    }
    
    public final double getAverageOnsetMinutes() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlin.Pair<java.lang.Double, java.lang.Double> getOnsetRange() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getMostCommonOnsetWindow() {
        return null;
    }
    
    public final double getSeverityCorrelation() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.fooddiary.domain.analysis.TriggerEpisode> component2() {
        return null;
    }
    
    public final double component3() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlin.Pair<java.lang.Double, java.lang.Double> component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component5() {
        return null;
    }
    
    public final double component6() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.domain.analysis.TriggerTimingPattern copy(@org.jetbrains.annotations.NotNull()
    java.lang.String foodName, @org.jetbrains.annotations.NotNull()
    java.util.List<com.fooddiary.domain.analysis.TriggerEpisode> episodes, double averageOnsetMinutes, @org.jetbrains.annotations.NotNull()
    kotlin.Pair<java.lang.Double, java.lang.Double> onsetRange, @org.jetbrains.annotations.NotNull()
    java.lang.String mostCommonOnsetWindow, double severityCorrelation) {
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