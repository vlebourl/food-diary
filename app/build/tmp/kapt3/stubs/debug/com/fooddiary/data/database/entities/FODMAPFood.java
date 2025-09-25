package com.fooddiary.data.database.entities;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0018\n\u0002\u0010\b\n\u0002\b\u0005\b\u0087\b\u0018\u0000 82\u00020\u0001:\u00018Bg\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\b\u0012\u0006\u0010\n\u001a\u00020\b\u0012\u0006\u0010\u000b\u001a\u00020\b\u0012\u0006\u0010\f\u001a\u00020\b\u0012\u0006\u0010\r\u001a\u00020\u0003\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u0003\u0012\u000e\b\u0002\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00030\u0010\u00a2\u0006\u0002\u0010\u0011J\t\u0010%\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010&\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000f\u0010\'\u001a\b\u0012\u0004\u0012\u00020\u00030\u0010H\u00c6\u0003J\t\u0010(\u001a\u00020\u0003H\u00c6\u0003J\t\u0010)\u001a\u00020\u0006H\u00c6\u0003J\t\u0010*\u001a\u00020\bH\u00c6\u0003J\t\u0010+\u001a\u00020\bH\u00c6\u0003J\t\u0010,\u001a\u00020\bH\u00c6\u0003J\t\u0010-\u001a\u00020\bH\u00c6\u0003J\t\u0010.\u001a\u00020\bH\u00c6\u0003J\t\u0010/\u001a\u00020\u0003H\u00c6\u0003J\u007f\u00100\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\b2\b\b\u0002\u0010\n\u001a\u00020\b2\b\b\u0002\u0010\u000b\u001a\u00020\b2\b\b\u0002\u0010\f\u001a\u00020\b2\b\b\u0002\u0010\r\u001a\u00020\u00032\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00032\u000e\b\u0002\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00030\u0010H\u00c6\u0001J\u0013\u00101\u001a\u00020\u001b2\b\u00102\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00103\u001a\u000204H\u00d6\u0001J\u000e\u00105\u001a\u00020\u001b2\u0006\u00106\u001a\u00020\u0003J\t\u00107\u001a\u00020\u0003H\u00d6\u0001R\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00030\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\t\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u001a\u001a\u00020\u001b8F\u00a2\u0006\u0006\u001a\u0004\b\u001a\u0010\u001cR\u0011\u0010\u001d\u001a\u00020\u001b8F\u00a2\u0006\u0006\u001a\u0004\b\u001d\u0010\u001cR\u0011\u0010\n\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0017R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0019R\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u0019R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0017R\u0011\u0010\f\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0017R\u0011\u0010\u000b\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0017R\u0011\u0010\r\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\u0019\u00a8\u00069"}, d2 = {"Lcom/fooddiary/data/database/entities/FODMAPFood;", "", "id", "", "name", "category", "Lcom/fooddiary/data/models/FoodCategory;", "oligosaccharides", "Lcom/fooddiary/data/models/FODMAPLevel;", "disaccharides", "monosaccharides", "polyols", "overallLevel", "servingSize", "notes", "aliases", "", "(Ljava/lang/String;Ljava/lang/String;Lcom/fooddiary/data/models/FoodCategory;Lcom/fooddiary/data/models/FODMAPLevel;Lcom/fooddiary/data/models/FODMAPLevel;Lcom/fooddiary/data/models/FODMAPLevel;Lcom/fooddiary/data/models/FODMAPLevel;Lcom/fooddiary/data/models/FODMAPLevel;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;)V", "getAliases", "()Ljava/util/List;", "getCategory", "()Lcom/fooddiary/data/models/FoodCategory;", "getDisaccharides", "()Lcom/fooddiary/data/models/FODMAPLevel;", "getId", "()Ljava/lang/String;", "isHighFODMAP", "", "()Z", "isLowFODMAP", "getMonosaccharides", "getName", "getNotes", "getOligosaccharides", "getOverallLevel", "getPolyols", "getServingSize", "component1", "component10", "component11", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "", "matches", "searchTerm", "toString", "Companion", "app_debug"})
@androidx.room.Entity(tableName = "fodmap_foods")
public final class FODMAPFood {
    @androidx.room.PrimaryKey()
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String id = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String name = null;
    @org.jetbrains.annotations.NotNull()
    private final com.fooddiary.data.models.FoodCategory category = null;
    @org.jetbrains.annotations.NotNull()
    private final com.fooddiary.data.models.FODMAPLevel oligosaccharides = null;
    @org.jetbrains.annotations.NotNull()
    private final com.fooddiary.data.models.FODMAPLevel disaccharides = null;
    @org.jetbrains.annotations.NotNull()
    private final com.fooddiary.data.models.FODMAPLevel monosaccharides = null;
    @org.jetbrains.annotations.NotNull()
    private final com.fooddiary.data.models.FODMAPLevel polyols = null;
    @org.jetbrains.annotations.NotNull()
    private final com.fooddiary.data.models.FODMAPLevel overallLevel = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String servingSize = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String notes = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> aliases = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<com.fooddiary.data.database.entities.FODMAPFood> HIGH_FODMAP_FOODS = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<com.fooddiary.data.database.entities.FODMAPFood> LOW_FODMAP_FOODS = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.fooddiary.data.database.entities.FODMAPFood.Companion Companion = null;
    
    public FODMAPFood(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.FoodCategory category, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.FODMAPLevel oligosaccharides, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.FODMAPLevel disaccharides, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.FODMAPLevel monosaccharides, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.FODMAPLevel polyols, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.FODMAPLevel overallLevel, @org.jetbrains.annotations.NotNull()
    java.lang.String servingSize, @org.jetbrains.annotations.Nullable()
    java.lang.String notes, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> aliases) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.models.FoodCategory getCategory() {
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
    public final com.fooddiary.data.models.FODMAPLevel getOverallLevel() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getServingSize() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getNotes() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getAliases() {
        return null;
    }
    
    public final boolean isHighFODMAP() {
        return false;
    }
    
    public final boolean isLowFODMAP() {
        return false;
    }
    
    public final boolean matches(@org.jetbrains.annotations.NotNull()
    java.lang.String searchTerm) {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component10() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component11() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.models.FoodCategory component3() {
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
    public final com.fooddiary.data.models.FODMAPLevel component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.models.FODMAPLevel component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.database.entities.FODMAPFood copy(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.FoodCategory category, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.FODMAPLevel oligosaccharides, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.FODMAPLevel disaccharides, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.FODMAPLevel monosaccharides, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.FODMAPLevel polyols, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.FODMAPLevel overallLevel, @org.jetbrains.annotations.NotNull()
    java.lang.String servingSize, @org.jetbrains.annotations.Nullable()
    java.lang.String notes, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> aliases) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0007\u00a8\u0006\u000b"}, d2 = {"Lcom/fooddiary/data/database/entities/FODMAPFood$Companion;", "", "()V", "HIGH_FODMAP_FOODS", "", "Lcom/fooddiary/data/database/entities/FODMAPFood;", "getHIGH_FODMAP_FOODS", "()Ljava/util/List;", "LOW_FODMAP_FOODS", "getLOW_FODMAP_FOODS", "getAllFODMAPFoods", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.fooddiary.data.database.entities.FODMAPFood> getHIGH_FODMAP_FOODS() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.fooddiary.data.database.entities.FODMAPFood> getLOW_FODMAP_FOODS() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.fooddiary.data.database.entities.FODMAPFood> getAllFODMAPFoods() {
            return null;
        }
    }
}