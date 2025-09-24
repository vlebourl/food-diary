package com.fooddiary.domain.analysis;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u001d\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001Bg\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\u0005\u0012\u0006\u0010\t\u001a\u00020\u0005\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u0003\u0012\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00030\u0010\u00a2\u0006\u0002\u0010\u0011J\t\u0010!\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\"\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00030\u0010H\u00c6\u0003J\t\u0010$\u001a\u00020\u0005H\u00c6\u0003J\t\u0010%\u001a\u00020\u0005H\u00c6\u0003J\t\u0010&\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\'\u001a\u00020\u0005H\u00c6\u0003J\t\u0010(\u001a\u00020\u0005H\u00c6\u0003J\u000b\u0010)\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010*\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010+\u001a\u00020\rH\u00c6\u0003J\u0081\u0001\u0010,\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\u00052\b\b\u0002\u0010\t\u001a\u00020\u00052\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\u00032\u000e\b\u0002\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00030\u0010H\u00c6\u0001J\u0013\u0010-\u001a\u00020.2\b\u0010/\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00100\u001a\u000201H\u00d6\u0001J\t\u00102\u001a\u00020\u0003H\u00d6\u0001R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u000e\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0013R\u0011\u0010\u0007\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0013R\u0011\u0010\b\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0018R\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0018R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0018R\u0011\u0010\t\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0018R\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00030\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0013\u0010\n\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0013\u00a8\u00063"}, d2 = {"Lcom/fooddiary/domain/analysis/FODMAPAnalysisResult;", "", "foodName", "", "overallLevel", "Lcom/fooddiary/data/models/FODMAPLevel;", "oligosaccharides", "disaccharides", "monosaccharides", "polyols", "servingSizeNote", "category", "confidence", "", "dataSource", "recommendations", "", "(Ljava/lang/String;Lcom/fooddiary/data/models/FODMAPLevel;Lcom/fooddiary/data/models/FODMAPLevel;Lcom/fooddiary/data/models/FODMAPLevel;Lcom/fooddiary/data/models/FODMAPLevel;Lcom/fooddiary/data/models/FODMAPLevel;Ljava/lang/String;Ljava/lang/String;FLjava/lang/String;Ljava/util/List;)V", "getCategory", "()Ljava/lang/String;", "getConfidence", "()F", "getDataSource", "getDisaccharides", "()Lcom/fooddiary/data/models/FODMAPLevel;", "getFoodName", "getMonosaccharides", "getOligosaccharides", "getOverallLevel", "getPolyols", "getRecommendations", "()Ljava/util/List;", "getServingSizeNote", "component1", "component10", "component11", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
public final class FODMAPAnalysisResult {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String foodName = null;
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
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String servingSizeNote = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String category = null;
    private final float confidence = 0.0F;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String dataSource = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> recommendations = null;
    
    public FODMAPAnalysisResult(@org.jetbrains.annotations.NotNull()
    java.lang.String foodName, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.FODMAPLevel overallLevel, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.FODMAPLevel oligosaccharides, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.FODMAPLevel disaccharides, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.FODMAPLevel monosaccharides, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.FODMAPLevel polyols, @org.jetbrains.annotations.Nullable()
    java.lang.String servingSizeNote, @org.jetbrains.annotations.Nullable()
    java.lang.String category, float confidence, @org.jetbrains.annotations.NotNull()
    java.lang.String dataSource, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> recommendations) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFoodName() {
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
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getServingSizeNote() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getCategory() {
        return null;
    }
    
    public final float getConfidence() {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDataSource() {
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
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component10() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component11() {
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
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component8() {
        return null;
    }
    
    public final float component9() {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.domain.analysis.FODMAPAnalysisResult copy(@org.jetbrains.annotations.NotNull()
    java.lang.String foodName, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.FODMAPLevel overallLevel, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.FODMAPLevel oligosaccharides, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.FODMAPLevel disaccharides, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.FODMAPLevel monosaccharides, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.FODMAPLevel polyols, @org.jetbrains.annotations.Nullable()
    java.lang.String servingSizeNote, @org.jetbrains.annotations.Nullable()
    java.lang.String category, float confidence, @org.jetbrains.annotations.NotNull()
    java.lang.String dataSource, @org.jetbrains.annotations.NotNull()
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