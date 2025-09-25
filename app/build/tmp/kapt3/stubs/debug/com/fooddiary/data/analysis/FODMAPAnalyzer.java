package com.fooddiary.data.analysis;

/**
 * FODMAP Analyzer for ingredient classification and elimination diet support
 * Provides analysis of foods according to the FODMAP (Fermentable, Oligo-, Di-, Mono-saccharides And Polyols) classification
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0003\u0012\u0013\u0014B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u0006J\u0016\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0016\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u000e\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0005\u001a\u00020\u0006J\u0010\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0010\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0005\u001a\u00020\u0006H\u0002\u00a8\u0006\u0015"}, d2 = {"Lcom/fooddiary/data/analysis/FODMAPAnalyzer;", "", "()V", "analyzeIngredient", "Lcom/fooddiary/data/analysis/FODMAPAnalyzer$FODMAPAnalysis;", "ingredient", "", "getFODMAPLevel", "Lcom/fooddiary/data/analysis/FODMAPAnalyzer$FODMAPLevel;", "getHighFODMAPTypes", "", "Lcom/fooddiary/data/analysis/FODMAPAnalyzer$FODMAPType;", "getModerateFODMAPTypes", "isEliminationSafe", "", "isHighFODMAP", "isLowFODMAP", "isModerateFODMAP", "FODMAPAnalysis", "FODMAPLevel", "FODMAPType", "app_debug"})
public final class FODMAPAnalyzer {
    @org.jetbrains.annotations.NotNull()
    public static final com.fooddiary.data.analysis.FODMAPAnalyzer INSTANCE = null;
    
    private FODMAPAnalyzer() {
        super();
    }
    
    /**
     * Analyze a food ingredient for FODMAP content
     * @param ingredient The ingredient to analyze
     * @return FODMAP analysis result
     */
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.analysis.FODMAPAnalyzer.FODMAPAnalysis analyzeIngredient(@org.jetbrains.annotations.NotNull()
    java.lang.String ingredient) {
        return null;
    }
    
    /**
     * Check if an ingredient is safe for elimination phase
     * @param ingredient The ingredient to check
     * @return true if safe for elimination diet
     */
    public final boolean isEliminationSafe(@org.jetbrains.annotations.NotNull()
    java.lang.String ingredient) {
        return false;
    }
    
    /**
     * Get FODMAP level for an ingredient
     * @param ingredient The ingredient to analyze
     * @return FODMAP level
     */
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.analysis.FODMAPAnalyzer.FODMAPLevel getFODMAPLevel(@org.jetbrains.annotations.NotNull()
    java.lang.String ingredient) {
        return null;
    }
    
    private final boolean isHighFODMAP(java.lang.String ingredient) {
        return false;
    }
    
    private final boolean isModerateFODMAP(java.lang.String ingredient) {
        return false;
    }
    
    private final boolean isLowFODMAP(java.lang.String ingredient) {
        return false;
    }
    
    private final java.util.List<com.fooddiary.data.analysis.FODMAPAnalyzer.FODMAPType> getHighFODMAPTypes(java.lang.String ingredient) {
        return null;
    }
    
    private final java.util.List<com.fooddiary.data.analysis.FODMAPAnalyzer.FODMAPType> getModerateFODMAPTypes(java.lang.String ingredient) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0010\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u00a2\u0006\u0002\u0010\u000bJ\t\u0010\u0013\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\bH\u00c6\u0003J\t\u0010\u0016\u001a\u00020\nH\u00c6\u0003J7\u0010\u0017\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\nH\u00c6\u0001J\u0013\u0010\u0018\u001a\u00020\n2\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001a\u001a\u00020\u001bH\u00d6\u0001J\t\u0010\u001c\u001a\u00020\bH\u00d6\u0001R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006\u001d"}, d2 = {"Lcom/fooddiary/data/analysis/FODMAPAnalyzer$FODMAPAnalysis;", "", "level", "Lcom/fooddiary/data/analysis/FODMAPAnalyzer$FODMAPLevel;", "types", "", "Lcom/fooddiary/data/analysis/FODMAPAnalyzer$FODMAPType;", "reason", "", "isEliminationSafe", "", "(Lcom/fooddiary/data/analysis/FODMAPAnalyzer$FODMAPLevel;Ljava/util/List;Ljava/lang/String;Z)V", "()Z", "getLevel", "()Lcom/fooddiary/data/analysis/FODMAPAnalyzer$FODMAPLevel;", "getReason", "()Ljava/lang/String;", "getTypes", "()Ljava/util/List;", "component1", "component2", "component3", "component4", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
    public static final class FODMAPAnalysis {
        @org.jetbrains.annotations.NotNull()
        private final com.fooddiary.data.analysis.FODMAPAnalyzer.FODMAPLevel level = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.fooddiary.data.analysis.FODMAPAnalyzer.FODMAPType> types = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String reason = null;
        private final boolean isEliminationSafe = false;
        
        public FODMAPAnalysis(@org.jetbrains.annotations.NotNull()
        com.fooddiary.data.analysis.FODMAPAnalyzer.FODMAPLevel level, @org.jetbrains.annotations.NotNull()
        java.util.List<? extends com.fooddiary.data.analysis.FODMAPAnalyzer.FODMAPType> types, @org.jetbrains.annotations.NotNull()
        java.lang.String reason, boolean isEliminationSafe) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.fooddiary.data.analysis.FODMAPAnalyzer.FODMAPLevel getLevel() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.fooddiary.data.analysis.FODMAPAnalyzer.FODMAPType> getTypes() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getReason() {
            return null;
        }
        
        public final boolean isEliminationSafe() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.fooddiary.data.analysis.FODMAPAnalyzer.FODMAPLevel component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.fooddiary.data.analysis.FODMAPAnalyzer.FODMAPType> component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component3() {
            return null;
        }
        
        public final boolean component4() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.fooddiary.data.analysis.FODMAPAnalyzer.FODMAPAnalysis copy(@org.jetbrains.annotations.NotNull()
        com.fooddiary.data.analysis.FODMAPAnalyzer.FODMAPLevel level, @org.jetbrains.annotations.NotNull()
        java.util.List<? extends com.fooddiary.data.analysis.FODMAPAnalyzer.FODMAPType> types, @org.jetbrains.annotations.NotNull()
        java.lang.String reason, boolean isEliminationSafe) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/fooddiary/data/analysis/FODMAPAnalyzer$FODMAPLevel;", "", "(Ljava/lang/String;I)V", "LOW", "MODERATE", "HIGH", "UNKNOWN", "app_debug"})
    public static enum FODMAPLevel {
        /*public static final*/ LOW /* = new LOW() */,
        /*public static final*/ MODERATE /* = new MODERATE() */,
        /*public static final*/ HIGH /* = new HIGH() */,
        /*public static final*/ UNKNOWN /* = new UNKNOWN() */;
        
        FODMAPLevel() {
        }
        
        @org.jetbrains.annotations.NotNull()
        public static kotlin.enums.EnumEntries<com.fooddiary.data.analysis.FODMAPAnalyzer.FODMAPLevel> getEntries() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007\u00a8\u0006\b"}, d2 = {"Lcom/fooddiary/data/analysis/FODMAPAnalyzer$FODMAPType;", "", "(Ljava/lang/String;I)V", "OLIGOSACCHARIDES", "DISACCHARIDES", "MONOSACCHARIDES", "POLYOLS", "MIXED", "app_debug"})
    public static enum FODMAPType {
        /*public static final*/ OLIGOSACCHARIDES /* = new OLIGOSACCHARIDES() */,
        /*public static final*/ DISACCHARIDES /* = new DISACCHARIDES() */,
        /*public static final*/ MONOSACCHARIDES /* = new MONOSACCHARIDES() */,
        /*public static final*/ POLYOLS /* = new POLYOLS() */,
        /*public static final*/ MIXED /* = new MIXED() */;
        
        FODMAPType() {
        }
        
        @org.jetbrains.annotations.NotNull()
        public static kotlin.enums.EnumEntries<com.fooddiary.data.analysis.FODMAPAnalyzer.FODMAPType> getEntries() {
            return null;
        }
    }
}