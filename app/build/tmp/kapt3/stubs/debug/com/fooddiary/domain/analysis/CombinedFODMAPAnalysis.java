package com.fooddiary.domain.analysis;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u001f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001Bu\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\u0006\u0012\u0006\u0010\t\u001a\u00020\u0006\u0012\u0006\u0010\n\u001a\u00020\u0006\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0003\u0012\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u0003\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\f0\u0003\u0012\u0006\u0010\u0011\u001a\u00020\f\u00a2\u0006\u0002\u0010\u0012J\u000f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\u000f\u0010#\u001a\b\u0012\u0004\u0012\u00020\f0\u0003H\u00c6\u0003J\t\u0010$\u001a\u00020\fH\u00c6\u0003J\t\u0010%\u001a\u00020\u0006H\u00c6\u0003J\t\u0010&\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\'\u001a\u00020\u0006H\u00c6\u0003J\t\u0010(\u001a\u00020\u0006H\u00c6\u0003J\t\u0010)\u001a\u00020\u0006H\u00c6\u0003J\u000f\u0010*\u001a\b\u0012\u0004\u0012\u00020\f0\u0003H\u00c6\u0003J\u000f\u0010+\u001a\b\u0012\u0004\u0012\u00020\f0\u0003H\u00c6\u0003J\t\u0010,\u001a\u00020\u000fH\u00c6\u0003J\u008f\u0001\u0010-\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00062\b\b\u0002\u0010\b\u001a\u00020\u00062\b\b\u0002\u0010\t\u001a\u00020\u00062\b\b\u0002\u0010\n\u001a\u00020\u00062\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u00032\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u00032\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\f0\u00032\b\b\u0002\u0010\u0011\u001a\u00020\fH\u00c6\u0001J\u0013\u0010.\u001a\u00020/2\b\u00100\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00101\u001a\u000202H\u00d6\u0001J\t\u00103\u001a\u00020\fH\u00d6\u0001R\u0011\u0010\u000e\u001a\u00020\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\b\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\t\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0016R\u0011\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0016R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0016R\u0011\u0010\n\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0016R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0018R\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\f0\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0018R\u0011\u0010\u0011\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0018\u00a8\u00064"}, d2 = {"Lcom/fooddiary/domain/analysis/CombinedFODMAPAnalysis;", "", "individualAnalyses", "", "Lcom/fooddiary/domain/analysis/FODMAPAnalysisResult;", "overallLevel", "Lcom/fooddiary/data/models/FODMAPLevel;", "oligosaccharides", "disaccharides", "monosaccharides", "polyols", "problematicIngredients", "", "warnings", "confidence", "", "recommendations", "summary", "(Ljava/util/List;Lcom/fooddiary/data/models/FODMAPLevel;Lcom/fooddiary/data/models/FODMAPLevel;Lcom/fooddiary/data/models/FODMAPLevel;Lcom/fooddiary/data/models/FODMAPLevel;Lcom/fooddiary/data/models/FODMAPLevel;Ljava/util/List;Ljava/util/List;FLjava/util/List;Ljava/lang/String;)V", "getConfidence", "()F", "getDisaccharides", "()Lcom/fooddiary/data/models/FODMAPLevel;", "getIndividualAnalyses", "()Ljava/util/List;", "getMonosaccharides", "getOligosaccharides", "getOverallLevel", "getPolyols", "getProblematicIngredients", "getRecommendations", "getSummary", "()Ljava/lang/String;", "getWarnings", "component1", "component10", "component11", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
public final class CombinedFODMAPAnalysis {
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.fooddiary.domain.analysis.FODMAPAnalysisResult> individualAnalyses = null;
    @org.jetbrains.annotations.NotNull()
    private final com.fooddiary.data.models.FODMAPLevel overallLevel = null;
    @org.jetbrains.annotations.NotNull()
    private final com.fooddiary.data.models.FODMAPLevel oligosaccharides = null;
    @org.jetbrains.annotations.NotNull()
    private final com.fooddiary.data.models.FODMAPLevel disaccharides = null;
    @org.jetbrains.annotations.NotNull()
    private final com.fooddiary.data.models.FODMAPLevel monosaccharides = null;
    @org.jetbrains.annotations.NotNull()
    private final com.fooddiary.data.models.FODMAPLevel polyols = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> problematicIngredients = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> warnings = null;
    private final float confidence = 0.0F;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> recommendations = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String summary = null;
    
    public CombinedFODMAPAnalysis(@org.jetbrains.annotations.NotNull()
    java.util.List<com.fooddiary.domain.analysis.FODMAPAnalysisResult> individualAnalyses, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.FODMAPLevel overallLevel, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.FODMAPLevel oligosaccharides, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.FODMAPLevel disaccharides, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.FODMAPLevel monosaccharides, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.FODMAPLevel polyols, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> problematicIngredients, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> warnings, float confidence, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> recommendations, @org.jetbrains.annotations.NotNull()
    java.lang.String summary) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.fooddiary.domain.analysis.FODMAPAnalysisResult> getIndividualAnalyses() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.models.FODMAPLevel getOverallLevel() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.models.FODMAPLevel getOligosaccharides() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.models.FODMAPLevel getDisaccharides() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.models.FODMAPLevel getMonosaccharides() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.models.FODMAPLevel getPolyols() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getProblematicIngredients() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getWarnings() {
        return null;
    }
    
    public final float getConfidence() {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getRecommendations() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSummary() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.fooddiary.domain.analysis.FODMAPAnalysisResult> component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component10() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component11() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.models.FODMAPLevel component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.models.FODMAPLevel component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.models.FODMAPLevel component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.models.FODMAPLevel component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.models.FODMAPLevel component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component8() {
        return null;
    }
    
    public final float component9() {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.domain.analysis.CombinedFODMAPAnalysis copy(@org.jetbrains.annotations.NotNull()
    java.util.List<com.fooddiary.domain.analysis.FODMAPAnalysisResult> individualAnalyses, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.FODMAPLevel overallLevel, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.FODMAPLevel oligosaccharides, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.FODMAPLevel disaccharides, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.FODMAPLevel monosaccharides, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.FODMAPLevel polyols, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> problematicIngredients, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> warnings, float confidence, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> recommendations, @org.jetbrains.annotations.NotNull()
    java.lang.String summary) {
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