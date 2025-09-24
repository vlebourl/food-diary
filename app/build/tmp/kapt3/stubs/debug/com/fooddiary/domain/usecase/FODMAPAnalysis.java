package com.fooddiary.domain.usecase;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0017\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BM\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\n\u0012\u0006\u0010\f\u001a\u00020\r\u00a2\u0006\u0002\u0010\u000eJ\t\u0010\u001b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001f\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010 \u001a\b\u0012\u0004\u0012\u00020\n0\tH\u00c6\u0003J\u000b\u0010!\u001a\u0004\u0018\u00010\nH\u00c6\u0003J\t\u0010\"\u001a\u00020\rH\u00c6\u0003Ja\u0010#\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\n2\b\b\u0002\u0010\f\u001a\u00020\rH\u00c6\u0001J\u0013\u0010$\u001a\u00020%2\b\u0010&\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\'\u001a\u00020(H\u00d6\u0001J\t\u0010)\u001a\u00020\nH\u00d6\u0001R\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0012R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0012R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0012R\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0012R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001a\u00a8\u0006*"}, d2 = {"Lcom/fooddiary/domain/usecase/FODMAPAnalysis;", "", "overallLevel", "Lcom/fooddiary/data/models/FODMAPLevel;", "oligosaccharides", "disaccharides", "monosaccharides", "polyols", "problematicIngredients", "", "", "servingSizeNote", "confidence", "", "(Lcom/fooddiary/data/models/FODMAPLevel;Lcom/fooddiary/data/models/FODMAPLevel;Lcom/fooddiary/data/models/FODMAPLevel;Lcom/fooddiary/data/models/FODMAPLevel;Lcom/fooddiary/data/models/FODMAPLevel;Ljava/util/List;Ljava/lang/String;F)V", "getConfidence", "()F", "getDisaccharides", "()Lcom/fooddiary/data/models/FODMAPLevel;", "getMonosaccharides", "getOligosaccharides", "getOverallLevel", "getPolyols", "getProblematicIngredients", "()Ljava/util/List;", "getServingSizeNote", "()Ljava/lang/String;", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
public final class FODMAPAnalysis {
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
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String servingSizeNote = null;
    private final float confidence = 0.0F;
    
    public FODMAPAnalysis(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.FODMAPLevel overallLevel, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.FODMAPLevel oligosaccharides, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.FODMAPLevel disaccharides, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.FODMAPLevel monosaccharides, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.FODMAPLevel polyols, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> problematicIngredients, @org.jetbrains.annotations.Nullable()
    java.lang.String servingSizeNote, float confidence) {
        super();
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
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getServingSizeNote() {
        return null;
    }
    
    public final float getConfidence() {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.models.FODMAPLevel component1() {
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
    public final java.util.List<java.lang.String> component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component7() {
        return null;
    }
    
    public final float component8() {
        return 0.0F;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.domain.usecase.FODMAPAnalysis copy(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.FODMAPLevel overallLevel, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.FODMAPLevel oligosaccharides, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.FODMAPLevel disaccharides, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.FODMAPLevel monosaccharides, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.FODMAPLevel polyols, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> problematicIngredients, @org.jetbrains.annotations.Nullable()
    java.lang.String servingSizeNote, float confidence) {
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