package com.fooddiary.domain.analysis;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u001a\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001BS\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0005\u0012\u0006\u0010\t\u001a\u00020\u0007\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\u000b\u0012\u0006\u0010\r\u001a\u00020\u0007\u0012\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f\u00a2\u0006\u0002\u0010\u0011J\t\u0010 \u001a\u00020\u0003H\u00c6\u0003J\t\u0010!\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\"\u001a\u00020\u0007H\u00c6\u0003J\t\u0010#\u001a\u00020\u0005H\u00c6\u0003J\t\u0010$\u001a\u00020\u0007H\u00c6\u0003J\t\u0010%\u001a\u00020\u000bH\u00c6\u0003J\t\u0010&\u001a\u00020\u000bH\u00c6\u0003J\t\u0010\'\u001a\u00020\u0007H\u00c6\u0003J\u000f\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fH\u00c6\u0003Ji\u0010)\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00052\b\b\u0002\u0010\t\u001a\u00020\u00072\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u000b2\b\b\u0002\u0010\r\u001a\u00020\u00072\u000e\b\u0002\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fH\u00c6\u0001J\u0013\u0010*\u001a\u00020+2\b\u0010,\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010-\u001a\u00020\u000bH\u00d6\u0001J\t\u0010.\u001a\u00020\u0010H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\b\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0013R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\t\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0018R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\f\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0015R\u0011\u0010\r\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0018\u00a8\u0006/"}, d2 = {"Lcom/fooddiary/domain/analysis/OptimalEatingWindow;", "", "pattern", "Lcom/fooddiary/domain/analysis/EatingWindowPattern;", "adherenceRate", "", "averageSymptomSeverity", "", "averageSymptomFrequency", "benefitScore", "adherentDays", "", "totalAnalyzedDays", "windowConsistency", "recommendations", "", "", "(Lcom/fooddiary/domain/analysis/EatingWindowPattern;FDFDIIDLjava/util/List;)V", "getAdherenceRate", "()F", "getAdherentDays", "()I", "getAverageSymptomFrequency", "getAverageSymptomSeverity", "()D", "getBenefitScore", "getPattern", "()Lcom/fooddiary/domain/analysis/EatingWindowPattern;", "getRecommendations", "()Ljava/util/List;", "getTotalAnalyzedDays", "getWindowConsistency", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class OptimalEatingWindow {
    @org.jetbrains.annotations.NotNull()
    private final com.fooddiary.domain.analysis.EatingWindowPattern pattern = null;
    private final float adherenceRate = 0.0F;
    private final double averageSymptomSeverity = 0.0;
    private final float averageSymptomFrequency = 0.0F;
    private final double benefitScore = 0.0;
    private final int adherentDays = 0;
    private final int totalAnalyzedDays = 0;
    private final double windowConsistency = 0.0;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> recommendations = null;
    
    public OptimalEatingWindow(@org.jetbrains.annotations.NotNull()
    com.fooddiary.domain.analysis.EatingWindowPattern pattern, float adherenceRate, double averageSymptomSeverity, float averageSymptomFrequency, double benefitScore, int adherentDays, int totalAnalyzedDays, double windowConsistency, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> recommendations) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.domain.analysis.EatingWindowPattern getPattern() {
        return null;
    }
    
    public final float getAdherenceRate() {
        return 0.0F;
    }
    
    public final double getAverageSymptomSeverity() {
        return 0.0;
    }
    
    public final float getAverageSymptomFrequency() {
        return 0.0F;
    }
    
    public final double getBenefitScore() {
        return 0.0;
    }
    
    public final int getAdherentDays() {
        return 0;
    }
    
    public final int getTotalAnalyzedDays() {
        return 0;
    }
    
    public final double getWindowConsistency() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getRecommendations() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.domain.analysis.EatingWindowPattern component1() {
        return null;
    }
    
    public final float component2() {
        return 0.0F;
    }
    
    public final double component3() {
        return 0.0;
    }
    
    public final float component4() {
        return 0.0F;
    }
    
    public final double component5() {
        return 0.0;
    }
    
    public final int component6() {
        return 0;
    }
    
    public final int component7() {
        return 0;
    }
    
    public final double component8() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.domain.analysis.OptimalEatingWindow copy(@org.jetbrains.annotations.NotNull()
    com.fooddiary.domain.analysis.EatingWindowPattern pattern, float adherenceRate, double averageSymptomSeverity, float averageSymptomFrequency, double benefitScore, int adherentDays, int totalAnalyzedDays, double windowConsistency, @org.jetbrains.annotations.NotNull()
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