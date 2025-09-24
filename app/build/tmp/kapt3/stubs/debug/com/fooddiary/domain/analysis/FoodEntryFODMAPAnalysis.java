package com.fooddiary.domain.analysis;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0015\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BC\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u0012\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u00a2\u0006\u0002\u0010\u000eJ\t\u0010\u001a\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\u0005H\u00c6\u0003J\u000b\u0010\u001c\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\tH\u00c6\u0003J\u000f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u00c6\u0003J\u000f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u00c6\u0003JS\u0010 \u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u00c6\u0001J\u0013\u0010!\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010$\u001a\u00020%H\u00d6\u0001J\t\u0010&\u001a\u00020\fH\u00d6\u0001R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0016\u00a8\u0006\'"}, d2 = {"Lcom/fooddiary/domain/analysis/FoodEntryFODMAPAnalysis;", "", "foodEntry", "Lcom/fooddiary/data/database/entities/FoodEntry;", "primaryFoodAnalysis", "Lcom/fooddiary/domain/analysis/FODMAPAnalysisResult;", "ingredientsAnalysis", "Lcom/fooddiary/domain/analysis/CombinedFODMAPAnalysis;", "combinedLevel", "Lcom/fooddiary/data/models/FODMAPLevel;", "portionWarnings", "", "", "recommendations", "(Lcom/fooddiary/data/database/entities/FoodEntry;Lcom/fooddiary/domain/analysis/FODMAPAnalysisResult;Lcom/fooddiary/domain/analysis/CombinedFODMAPAnalysis;Lcom/fooddiary/data/models/FODMAPLevel;Ljava/util/List;Ljava/util/List;)V", "getCombinedLevel", "()Lcom/fooddiary/data/models/FODMAPLevel;", "getFoodEntry", "()Lcom/fooddiary/data/database/entities/FoodEntry;", "getIngredientsAnalysis", "()Lcom/fooddiary/domain/analysis/CombinedFODMAPAnalysis;", "getPortionWarnings", "()Ljava/util/List;", "getPrimaryFoodAnalysis", "()Lcom/fooddiary/domain/analysis/FODMAPAnalysisResult;", "getRecommendations", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
public final class FoodEntryFODMAPAnalysis {
    @org.jetbrains.annotations.NotNull()
    private final com.fooddiary.data.database.entities.FoodEntry foodEntry = null;
    @org.jetbrains.annotations.NotNull()
    private final com.fooddiary.domain.analysis.FODMAPAnalysisResult primaryFoodAnalysis = null;
    @org.jetbrains.annotations.Nullable()
    private final com.fooddiary.domain.analysis.CombinedFODMAPAnalysis ingredientsAnalysis = null;
    @org.jetbrains.annotations.NotNull()
    private final com.fooddiary.data.models.FODMAPLevel combinedLevel = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> portionWarnings = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> recommendations = null;
    
    public FoodEntryFODMAPAnalysis(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.database.entities.FoodEntry foodEntry, @org.jetbrains.annotations.NotNull()
    com.fooddiary.domain.analysis.FODMAPAnalysisResult primaryFoodAnalysis, @org.jetbrains.annotations.Nullable()
    com.fooddiary.domain.analysis.CombinedFODMAPAnalysis ingredientsAnalysis, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.FODMAPLevel combinedLevel, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> portionWarnings, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> recommendations) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.database.entities.FoodEntry getFoodEntry() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.domain.analysis.FODMAPAnalysisResult getPrimaryFoodAnalysis() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.fooddiary.domain.analysis.CombinedFODMAPAnalysis getIngredientsAnalysis() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.models.FODMAPLevel getCombinedLevel() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getPortionWarnings() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getRecommendations() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.database.entities.FoodEntry component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.domain.analysis.FODMAPAnalysisResult component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.fooddiary.domain.analysis.CombinedFODMAPAnalysis component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.models.FODMAPLevel component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.domain.analysis.FoodEntryFODMAPAnalysis copy(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.database.entities.FoodEntry foodEntry, @org.jetbrains.annotations.NotNull()
    com.fooddiary.domain.analysis.FODMAPAnalysisResult primaryFoodAnalysis, @org.jetbrains.annotations.Nullable()
    com.fooddiary.domain.analysis.CombinedFODMAPAnalysis ingredientsAnalysis, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.FODMAPLevel combinedLevel, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> portionWarnings, @org.jetbrains.annotations.NotNull()
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