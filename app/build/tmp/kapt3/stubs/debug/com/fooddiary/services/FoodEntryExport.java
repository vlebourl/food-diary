package com.fooddiary.services;

@kotlinx.serialization.Serializable()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\"\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001Bg\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0005\u0012\u0006\u0010\t\u001a\u00020\u0005\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0010\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0012J\t\u0010&\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\'\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010(\u001a\u00020\u0005H\u00c6\u0003J\t\u0010)\u001a\u00020\u0007H\u00c6\u0003J\t\u0010*\u001a\u00020\u0005H\u00c6\u0003J\t\u0010+\u001a\u00020\u0005H\u00c6\u0003J\u000f\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00050\u000bH\u00c6\u0003J\t\u0010-\u001a\u00020\rH\u00c6\u0003J\t\u0010.\u001a\u00020\u0005H\u00c6\u0003J\u0010\u0010/\u001a\u0004\u0018\u00010\u0010H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0014J|\u00100\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00052\b\b\u0002\u0010\t\u001a\u00020\u00052\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u000b2\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\u00052\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00102\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0005H\u00c6\u0001\u00a2\u0006\u0002\u00101J\u0013\u00102\u001a\u0002032\b\u00104\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00105\u001a\u00020\u0010H\u00d6\u0001J\t\u00106\u001a\u00020\u0005H\u00d6\u0001R\u0015\u0010\u000f\u001a\u0004\u0018\u00010\u0010\u00a2\u0006\n\n\u0002\u0010\u0015\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\t\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0017R\u0011\u0010\u000e\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0017R\u0011\u0010\b\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0017R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0013\u0010\u0011\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0017R\u001c\u0010\f\u001a\u00020\r8\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b\"\u0010#\u001a\u0004\b$\u0010%\u00a8\u00067"}, d2 = {"Lcom/fooddiary/services/FoodEntryExport;", "", "id", "", "foodName", "", "portions", "", "portionUnit", "mealType", "ingredients", "", "timestamp", "Ljava/time/Instant;", "notes", "estimatedCalories", "", "preparationMethod", "(JLjava/lang/String;DLjava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/time/Instant;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;)V", "getEstimatedCalories", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getFoodName", "()Ljava/lang/String;", "getId", "()J", "getIngredients", "()Ljava/util/List;", "getMealType", "getNotes", "getPortionUnit", "getPortions", "()D", "getPreparationMethod", "getTimestamp$annotations", "()V", "getTimestamp", "()Ljava/time/Instant;", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(JLjava/lang/String;DLjava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/time/Instant;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;)Lcom/fooddiary/services/FoodEntryExport;", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class FoodEntryExport {
    private final long id = 0L;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String foodName = null;
    private final double portions = 0.0;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String portionUnit = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String mealType = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> ingredients = null;
    @org.jetbrains.annotations.NotNull()
    private final java.time.Instant timestamp = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String notes = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer estimatedCalories = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String preparationMethod = null;
    
    public FoodEntryExport(long id, @org.jetbrains.annotations.NotNull()
    java.lang.String foodName, double portions, @org.jetbrains.annotations.NotNull()
    java.lang.String portionUnit, @org.jetbrains.annotations.NotNull()
    java.lang.String mealType, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> ingredients, @org.jetbrains.annotations.NotNull()
    java.time.Instant timestamp, @org.jetbrains.annotations.NotNull()
    java.lang.String notes, @org.jetbrains.annotations.Nullable()
    java.lang.Integer estimatedCalories, @org.jetbrains.annotations.Nullable()
    java.lang.String preparationMethod) {
        super();
    }
    
    public final long getId() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFoodName() {
        return null;
    }
    
    public final double getPortions() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPortionUnit() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getMealType() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getIngredients() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.Instant getTimestamp() {
        return null;
    }
    
    @kotlinx.serialization.Serializable(with = com.fooddiary.services.InstantSerializer.class)
    @java.lang.Deprecated()
    public static void getTimestamp$annotations() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getNotes() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getEstimatedCalories() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getPreparationMethod() {
        return null;
    }
    
    public final long component1() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component10() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    public final double component3() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.Instant component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.services.FoodEntryExport copy(long id, @org.jetbrains.annotations.NotNull()
    java.lang.String foodName, double portions, @org.jetbrains.annotations.NotNull()
    java.lang.String portionUnit, @org.jetbrains.annotations.NotNull()
    java.lang.String mealType, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> ingredients, @org.jetbrains.annotations.NotNull()
    java.time.Instant timestamp, @org.jetbrains.annotations.NotNull()
    java.lang.String notes, @org.jetbrains.annotations.Nullable()
    java.lang.Integer estimatedCalories, @org.jetbrains.annotations.Nullable()
    java.lang.String preparationMethod) {
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