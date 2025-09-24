package com.fooddiary.presentation.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b/\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u00ad\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\r\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0011\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0011\u0012\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0017J\t\u0010.\u001a\u00020\u0003H\u00c6\u0003J\t\u0010/\u001a\u00020\u0011H\u00c6\u0003J\t\u00100\u001a\u00020\u0011H\u00c6\u0003J\u000b\u00101\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u00102\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u00103\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u00104\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000f\u00105\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005H\u00c6\u0003J\t\u00106\u001a\u00020\u0003H\u00c6\u0003J\t\u00107\u001a\u00020\u0003H\u00c6\u0003J\t\u00108\u001a\u00020\tH\u00c6\u0003J\t\u00109\u001a\u00020\u000bH\u00c6\u0003J\t\u0010:\u001a\u00020\rH\u00c6\u0003J\u000b\u0010;\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010<\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u00b1\u0001\u0010=\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\r2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u00112\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001J\u0013\u0010>\u001a\u00020\u00112\b\u0010?\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010@\u001a\u00020AH\u00d6\u0001J\t\u0010B\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0013\u0010\u0014\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001bR\u0013\u0010\u0015\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001bR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0011\u0010\u0010\u001a\u00020\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010 R\u0011\u0010\u0012\u001a\u00020\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010 R\u0011\u0010!\u001a\u00020\u00118F\u00a2\u0006\u0006\u001a\u0004\b!\u0010 R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010#R\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\u001bR\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\u001bR\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\u001bR\u0013\u0010\u0016\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010\u001bR\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010\u001bR\u0013\u0010\u0013\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010\u001bR\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010+R\u0011\u0010,\u001a\u00020\u00038F\u00a2\u0006\u0006\u001a\u0004\b-\u0010\u001b\u00a8\u0006C"}, d2 = {"Lcom/fooddiary/presentation/viewmodel/FoodEntryUiState;", "", "foodName", "", "ingredients", "", "portions", "portionUnit", "mealType", "Lcom/fooddiary/data/models/MealType;", "context", "Lcom/fooddiary/data/models/ConsumptionContext;", "timestamp", "Ljava/time/Instant;", "preparationMethod", "notes", "isLoading", "", "isSuccess", "savedEntryId", "errorMessage", "foodNameError", "portionsError", "(Ljava/lang/String;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;Lcom/fooddiary/data/models/MealType;Lcom/fooddiary/data/models/ConsumptionContext;Ljava/time/Instant;Ljava/lang/String;Ljava/lang/String;ZZLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getContext", "()Lcom/fooddiary/data/models/ConsumptionContext;", "getErrorMessage", "()Ljava/lang/String;", "getFoodName", "getFoodNameError", "getIngredients", "()Ljava/util/List;", "()Z", "isValid", "getMealType", "()Lcom/fooddiary/data/models/MealType;", "getNotes", "getPortionUnit", "getPortions", "getPortionsError", "getPreparationMethod", "getSavedEntryId", "getTimestamp", "()Ljava/time/Instant;", "timestampFormatted", "getTimestampFormatted", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
public final class FoodEntryUiState {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String foodName = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> ingredients = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String portions = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String portionUnit = null;
    @org.jetbrains.annotations.NotNull()
    private final com.fooddiary.data.models.MealType mealType = null;
    @org.jetbrains.annotations.NotNull()
    private final com.fooddiary.data.models.ConsumptionContext context = null;
    @org.jetbrains.annotations.NotNull()
    private final java.time.Instant timestamp = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String preparationMethod = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String notes = null;
    private final boolean isLoading = false;
    private final boolean isSuccess = false;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String savedEntryId = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String errorMessage = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String foodNameError = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String portionsError = null;
    
    public FoodEntryUiState(@org.jetbrains.annotations.NotNull()
    java.lang.String foodName, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> ingredients, @org.jetbrains.annotations.NotNull()
    java.lang.String portions, @org.jetbrains.annotations.NotNull()
    java.lang.String portionUnit, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.MealType mealType, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.ConsumptionContext context, @org.jetbrains.annotations.NotNull()
    java.time.Instant timestamp, @org.jetbrains.annotations.Nullable()
    java.lang.String preparationMethod, @org.jetbrains.annotations.Nullable()
    java.lang.String notes, boolean isLoading, boolean isSuccess, @org.jetbrains.annotations.Nullable()
    java.lang.String savedEntryId, @org.jetbrains.annotations.Nullable()
    java.lang.String errorMessage, @org.jetbrains.annotations.Nullable()
    java.lang.String foodNameError, @org.jetbrains.annotations.Nullable()
    java.lang.String portionsError) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFoodName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getIngredients() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPortions() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPortionUnit() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.models.MealType getMealType() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.models.ConsumptionContext getContext() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.Instant getTimestamp() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getPreparationMethod() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getNotes() {
        return null;
    }
    
    public final boolean isLoading() {
        return false;
    }
    
    public final boolean isSuccess() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getSavedEntryId() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getErrorMessage() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getFoodNameError() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getPortionsError() {
        return null;
    }
    
    public final boolean isValid() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTimestampFormatted() {
        return null;
    }
    
    public FoodEntryUiState() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    public final boolean component10() {
        return false;
    }
    
    public final boolean component11() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component12() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component13() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component14() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component15() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.models.MealType component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.models.ConsumptionContext component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.Instant component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.presentation.viewmodel.FoodEntryUiState copy(@org.jetbrains.annotations.NotNull()
    java.lang.String foodName, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> ingredients, @org.jetbrains.annotations.NotNull()
    java.lang.String portions, @org.jetbrains.annotations.NotNull()
    java.lang.String portionUnit, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.MealType mealType, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.ConsumptionContext context, @org.jetbrains.annotations.NotNull()
    java.time.Instant timestamp, @org.jetbrains.annotations.Nullable()
    java.lang.String preparationMethod, @org.jetbrains.annotations.Nullable()
    java.lang.String notes, boolean isLoading, boolean isSuccess, @org.jetbrains.annotations.Nullable()
    java.lang.String savedEntryId, @org.jetbrains.annotations.Nullable()
    java.lang.String errorMessage, @org.jetbrains.annotations.Nullable()
    java.lang.String foodNameError, @org.jetbrains.annotations.Nullable()
    java.lang.String portionsError) {
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