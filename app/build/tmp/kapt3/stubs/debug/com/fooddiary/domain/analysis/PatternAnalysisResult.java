package com.fooddiary.domain.analysis;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0016\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001Bi\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0003\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0003\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0003\u0012\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0003\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\u0006\u0010\u0011\u001a\u00020\u0012\u00a2\u0006\u0002\u0010\u0013J\u000f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\u000f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00060\u0003H\u00c6\u0003J\u000f\u0010!\u001a\b\u0012\u0004\u0012\u00020\b0\u0003H\u00c6\u0003J\u000f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\n0\u0003H\u00c6\u0003J\u000f\u0010#\u001a\b\u0012\u0004\u0012\u00020\f0\u0003H\u00c6\u0003J\u000f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0003H\u00c6\u0003J\t\u0010%\u001a\u00020\u0010H\u00c6\u0003J\t\u0010&\u001a\u00020\u0012H\u00c6\u0003J}\u0010\'\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u00032\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u00032\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u00032\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u00032\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u00032\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u0012H\u00c6\u0001J\u0013\u0010(\u001a\u00020)2\b\u0010*\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010+\u001a\u00020\u0010H\u00d6\u0001J\t\u0010,\u001a\u00020-H\u00d6\u0001R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u000f\u001a\u00020\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0015R\u0011\u0010\u0011\u001a\u00020\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0015R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0015R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0015R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0015\u00a8\u0006."}, d2 = {"Lcom/fooddiary/domain/analysis/PatternAnalysisResult;", "", "eatingHabits", "", "Lcom/fooddiary/domain/analysis/EatingHabit;", "weeklyPatterns", "Lcom/fooddiary/domain/analysis/WeeklyPattern;", "seasonalPatterns", "Lcom/fooddiary/domain/analysis/SeasonalPattern;", "dailyRhythms", "Lcom/fooddiary/domain/analysis/DailyRhythm;", "adaptiveTriggers", "Lcom/fooddiary/domain/analysis/AdaptiveTrigger;", "behavioralPatterns", "Lcom/fooddiary/domain/analysis/BehavioralPattern;", "analysisPeriod", "", "confidenceScore", "", "(Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;IF)V", "getAdaptiveTriggers", "()Ljava/util/List;", "getAnalysisPeriod", "()I", "getBehavioralPatterns", "getConfidenceScore", "()F", "getDailyRhythms", "getEatingHabits", "getSeasonalPatterns", "getWeeklyPatterns", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "equals", "", "other", "hashCode", "toString", "", "app_debug"})
public final class PatternAnalysisResult {
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.fooddiary.domain.analysis.EatingHabit> eatingHabits = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.fooddiary.domain.analysis.WeeklyPattern> weeklyPatterns = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.fooddiary.domain.analysis.SeasonalPattern> seasonalPatterns = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.fooddiary.domain.analysis.DailyRhythm> dailyRhythms = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.fooddiary.domain.analysis.AdaptiveTrigger> adaptiveTriggers = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.fooddiary.domain.analysis.BehavioralPattern> behavioralPatterns = null;
    private final int analysisPeriod = 0;
    private final float confidenceScore = 0.0F;
    
    public PatternAnalysisResult(@org.jetbrains.annotations.NotNull()
    java.util.List<com.fooddiary.domain.analysis.EatingHabit> eatingHabits, @org.jetbrains.annotations.NotNull()
    java.util.List<com.fooddiary.domain.analysis.WeeklyPattern> weeklyPatterns, @org.jetbrains.annotations.NotNull()
    java.util.List<com.fooddiary.domain.analysis.SeasonalPattern> seasonalPatterns, @org.jetbrains.annotations.NotNull()
    java.util.List<com.fooddiary.domain.analysis.DailyRhythm> dailyRhythms, @org.jetbrains.annotations.NotNull()
    java.util.List<com.fooddiary.domain.analysis.AdaptiveTrigger> adaptiveTriggers, @org.jetbrains.annotations.NotNull()
    java.util.List<com.fooddiary.domain.analysis.BehavioralPattern> behavioralPatterns, int analysisPeriod, float confidenceScore) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.fooddiary.domain.analysis.EatingHabit> getEatingHabits() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.fooddiary.domain.analysis.WeeklyPattern> getWeeklyPatterns() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.fooddiary.domain.analysis.SeasonalPattern> getSeasonalPatterns() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.fooddiary.domain.analysis.DailyRhythm> getDailyRhythms() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.fooddiary.domain.analysis.AdaptiveTrigger> getAdaptiveTriggers() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.fooddiary.domain.analysis.BehavioralPattern> getBehavioralPatterns() {
        return null;
    }
    
    public final int getAnalysisPeriod() {
        return 0;
    }
    
    public final float getConfidenceScore() {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.fooddiary.domain.analysis.EatingHabit> component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.fooddiary.domain.analysis.WeeklyPattern> component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.fooddiary.domain.analysis.SeasonalPattern> component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.fooddiary.domain.analysis.DailyRhythm> component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.fooddiary.domain.analysis.AdaptiveTrigger> component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.fooddiary.domain.analysis.BehavioralPattern> component6() {
        return null;
    }
    
    public final int component7() {
        return 0;
    }
    
    public final float component8() {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.domain.analysis.PatternAnalysisResult copy(@org.jetbrains.annotations.NotNull()
    java.util.List<com.fooddiary.domain.analysis.EatingHabit> eatingHabits, @org.jetbrains.annotations.NotNull()
    java.util.List<com.fooddiary.domain.analysis.WeeklyPattern> weeklyPatterns, @org.jetbrains.annotations.NotNull()
    java.util.List<com.fooddiary.domain.analysis.SeasonalPattern> seasonalPatterns, @org.jetbrains.annotations.NotNull()
    java.util.List<com.fooddiary.domain.analysis.DailyRhythm> dailyRhythms, @org.jetbrains.annotations.NotNull()
    java.util.List<com.fooddiary.domain.analysis.AdaptiveTrigger> adaptiveTriggers, @org.jetbrains.annotations.NotNull()
    java.util.List<com.fooddiary.domain.analysis.BehavioralPattern> behavioralPatterns, int analysisPeriod, float confidenceScore) {
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