package com.fooddiary.domain.analysis;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b \b\u0086\b\u0018\u00002\u00020\u0001BQ\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\u000b\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\u0006\u0010\u0011\u001a\u00020\u0012\u00a2\u0006\u0002\u0010\u0013J\t\u0010$\u001a\u00020\u0003H\u00c6\u0003J\t\u0010%\u001a\u00020\u0005H\u00c6\u0003J\u000b\u0010&\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\u000b\u0010\'\u001a\u0004\u0018\u00010\tH\u00c6\u0003J\t\u0010(\u001a\u00020\u000bH\u00c6\u0003J\t\u0010)\u001a\u00020\u000bH\u00c6\u0003J\t\u0010*\u001a\u00020\u000eH\u00c6\u0003J\t\u0010+\u001a\u00020\u0010H\u00c6\u0003J\t\u0010,\u001a\u00020\u0012H\u00c6\u0003Jg\u0010-\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u000b2\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u0012H\u00c6\u0001J\u0013\u0010.\u001a\u00020\u00102\b\u0010/\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00100\u001a\u00020\u000bH\u00d6\u0001J\t\u00101\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\f\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0013\u0010\b\u001a\u0004\u0018\u00010\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\u000f\u001a\u00020\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u001cR\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0017R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0011\u0010\u0011\u001a\u00020\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010#\u00a8\u00062"}, d2 = {"Lcom/fooddiary/domain/analysis/CorrelationAnalysis;", "", "foodName", "", "symptomType", "Lcom/fooddiary/data/models/SymptomType;", "pearsonResult", "Lcom/fooddiary/domain/analysis/CorrelationResult;", "chiSquareResult", "Lcom/fooddiary/domain/analysis/ChiSquareResult;", "occurrences", "", "averageTimeOffsetMinutes", "averageSeverity", "", "isStatisticallySignificant", "", "recommendedAction", "Lcom/fooddiary/domain/analysis/RecommendedAction;", "(Ljava/lang/String;Lcom/fooddiary/data/models/SymptomType;Lcom/fooddiary/domain/analysis/CorrelationResult;Lcom/fooddiary/domain/analysis/ChiSquareResult;IIDZLcom/fooddiary/domain/analysis/RecommendedAction;)V", "getAverageSeverity", "()D", "getAverageTimeOffsetMinutes", "()I", "getChiSquareResult", "()Lcom/fooddiary/domain/analysis/ChiSquareResult;", "getFoodName", "()Ljava/lang/String;", "()Z", "getOccurrences", "getPearsonResult", "()Lcom/fooddiary/domain/analysis/CorrelationResult;", "getRecommendedAction", "()Lcom/fooddiary/domain/analysis/RecommendedAction;", "getSymptomType", "()Lcom/fooddiary/data/models/SymptomType;", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
public final class CorrelationAnalysis {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String foodName = null;
    @org.jetbrains.annotations.NotNull()
    private final com.fooddiary.data.models.SymptomType symptomType = null;
    @org.jetbrains.annotations.Nullable()
    private final com.fooddiary.domain.analysis.CorrelationResult pearsonResult = null;
    @org.jetbrains.annotations.Nullable()
    private final com.fooddiary.domain.analysis.ChiSquareResult chiSquareResult = null;
    private final int occurrences = 0;
    private final int averageTimeOffsetMinutes = 0;
    private final double averageSeverity = 0.0;
    private final boolean isStatisticallySignificant = false;
    @org.jetbrains.annotations.NotNull()
    private final com.fooddiary.domain.analysis.RecommendedAction recommendedAction = null;
    
    public CorrelationAnalysis(@org.jetbrains.annotations.NotNull()
    java.lang.String foodName, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.SymptomType symptomType, @org.jetbrains.annotations.Nullable()
    com.fooddiary.domain.analysis.CorrelationResult pearsonResult, @org.jetbrains.annotations.Nullable()
    com.fooddiary.domain.analysis.ChiSquareResult chiSquareResult, int occurrences, int averageTimeOffsetMinutes, double averageSeverity, boolean isStatisticallySignificant, @org.jetbrains.annotations.NotNull()
    com.fooddiary.domain.analysis.RecommendedAction recommendedAction) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFoodName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.models.SymptomType getSymptomType() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.fooddiary.domain.analysis.CorrelationResult getPearsonResult() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.fooddiary.domain.analysis.ChiSquareResult getChiSquareResult() {
        return null;
    }
    
    public final int getOccurrences() {
        return 0;
    }
    
    public final int getAverageTimeOffsetMinutes() {
        return 0;
    }
    
    public final double getAverageSeverity() {
        return 0.0;
    }
    
    public final boolean isStatisticallySignificant() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.domain.analysis.RecommendedAction getRecommendedAction() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.models.SymptomType component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.fooddiary.domain.analysis.CorrelationResult component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.fooddiary.domain.analysis.ChiSquareResult component4() {
        return null;
    }
    
    public final int component5() {
        return 0;
    }
    
    public final int component6() {
        return 0;
    }
    
    public final double component7() {
        return 0.0;
    }
    
    public final boolean component8() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.domain.analysis.RecommendedAction component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.domain.analysis.CorrelationAnalysis copy(@org.jetbrains.annotations.NotNull()
    java.lang.String foodName, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.SymptomType symptomType, @org.jetbrains.annotations.Nullable()
    com.fooddiary.domain.analysis.CorrelationResult pearsonResult, @org.jetbrains.annotations.Nullable()
    com.fooddiary.domain.analysis.ChiSquareResult chiSquareResult, int occurrences, int averageTimeOffsetMinutes, double averageSeverity, boolean isStatisticallySignificant, @org.jetbrains.annotations.NotNull()
    com.fooddiary.domain.analysis.RecommendedAction recommendedAction) {
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