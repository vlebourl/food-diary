package com.fooddiary.presentation.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u000e\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018J\b\u0010\u0019\u001a\u00020\u0016H\u0002J\u0006\u0010\u001a\u001a\u00020\u0016J\u0012\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00180\u001d0\u001cJ\u001a\u0010\u001e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00180\u001d0\u001c2\u0006\u0010\u001f\u001a\u00020\u0018J\u000e\u0010 \u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0018J\u0006\u0010!\u001a\u00020\u0016J\u0006\u0010\"\u001a\u00020\u0016J\u000e\u0010#\u001a\u00020\u00162\u0006\u0010$\u001a\u00020%J\u000e\u0010&\u001a\u00020\u00162\u0006\u0010\'\u001a\u00020\u0018J\u0014\u0010(\u001a\u00020\u00162\f\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00180\u001dJ\u000e\u0010*\u001a\u00020\u00162\u0006\u0010+\u001a\u00020,J\u000e\u0010-\u001a\u00020\u00162\u0006\u0010.\u001a\u00020/J\u000e\u00100\u001a\u00020\u00162\u0006\u00101\u001a\u00020\u0018J\u000e\u00102\u001a\u00020\u00162\u0006\u00103\u001a\u00020\u0018J\u000e\u00104\u001a\u00020\u00162\u0006\u00105\u001a\u00020\u0018J\u000e\u00106\u001a\u00020\u00162\u0006\u00107\u001a\u00020\u0018J\u000e\u00108\u001a\u00020\u00162\u0006\u00109\u001a\u00020:J\u000e\u0010;\u001a\u00020\u00162\u0006\u0010<\u001a\u00020=R\u0016\u0010\t\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00070\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0010\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0012R\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\r0\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0011\u00a8\u0006>"}, d2 = {"Lcom/fooddiary/presentation/viewmodel/FoodEntryViewModel;", "Landroidx/lifecycle/ViewModel;", "addFoodEntryUseCase", "Lcom/fooddiary/domain/usecase/AddFoodEntryUseCase;", "analyzeFODMAPContentUseCase", "Lcom/fooddiary/domain/usecase/AnalyzeFODMAPContentUseCase;", "foodEntryRepository", "error/NonExistentClass", "(Lcom/fooddiary/domain/usecase/AddFoodEntryUseCase;Lcom/fooddiary/domain/usecase/AnalyzeFODMAPContentUseCase;Lerror/NonExistentClass;)V", "_fodmapAnalysis", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_uiState", "Lcom/fooddiary/presentation/viewmodel/FoodEntryUiState;", "fodmapAnalysis", "Lkotlinx/coroutines/flow/StateFlow;", "getFodmapAnalysis", "()Lkotlinx/coroutines/flow/StateFlow;", "Lerror/NonExistentClass;", "uiState", "getUiState", "addIngredient", "", "ingredient", "", "analyzeFODMAPContent", "clearError", "getRecentFoodNames", "Lkotlinx/coroutines/flow/Flow;", "", "getSuggestedIngredients", "query", "removeIngredient", "resetForm", "saveFoodEntry", "updateEatingSpeed", "speed", "Lcom/fooddiary/data/models/EatingSpeed;", "updateFoodName", "name", "updateIngredients", "ingredients", "updateLocation", "location", "Lcom/fooddiary/data/models/LocationType;", "updateMealType", "mealType", "Lcom/fooddiary/data/models/MealType;", "updateNotes", "notes", "updatePortionUnit", "unit", "updatePortions", "portions", "updatePreparationMethod", "method", "updateSocialContext", "social", "Lcom/fooddiary/data/models/SocialContext;", "updateTimestamp", "timestamp", "Ljava/time/Instant;", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class FoodEntryViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.fooddiary.domain.usecase.AddFoodEntryUseCase addFoodEntryUseCase = null;
    @org.jetbrains.annotations.NotNull()
    private final com.fooddiary.domain.usecase.AnalyzeFODMAPContentUseCase analyzeFODMAPContentUseCase = null;
    @org.jetbrains.annotations.NotNull()
    private final error.NonExistentClass foodEntryRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.fooddiary.presentation.viewmodel.FoodEntryUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.fooddiary.presentation.viewmodel.FoodEntryUiState> uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow _fodmapAnalysis = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<error.NonExistentClass> fodmapAnalysis = null;
    
    @javax.inject.Inject()
    public FoodEntryViewModel(@org.jetbrains.annotations.NotNull()
    com.fooddiary.domain.usecase.AddFoodEntryUseCase addFoodEntryUseCase, @org.jetbrains.annotations.NotNull()
    com.fooddiary.domain.usecase.AnalyzeFODMAPContentUseCase analyzeFODMAPContentUseCase, @org.jetbrains.annotations.NotNull()
    error.NonExistentClass foodEntryRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.fooddiary.presentation.viewmodel.FoodEntryUiState> getUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<error.NonExistentClass> getFodmapAnalysis() {
        return null;
    }
    
    public final void updateFoodName(@org.jetbrains.annotations.NotNull()
    java.lang.String name) {
    }
    
    public final void updateIngredients(@org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> ingredients) {
    }
    
    public final void addIngredient(@org.jetbrains.annotations.NotNull()
    java.lang.String ingredient) {
    }
    
    public final void removeIngredient(@org.jetbrains.annotations.NotNull()
    java.lang.String ingredient) {
    }
    
    public final void updatePortions(@org.jetbrains.annotations.NotNull()
    java.lang.String portions) {
    }
    
    public final void updatePortionUnit(@org.jetbrains.annotations.NotNull()
    java.lang.String unit) {
    }
    
    public final void updateMealType(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.MealType mealType) {
    }
    
    public final void updateLocation(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.LocationType location) {
    }
    
    public final void updateSocialContext(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.SocialContext social) {
    }
    
    public final void updateEatingSpeed(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.EatingSpeed speed) {
    }
    
    public final void updateTimestamp(@org.jetbrains.annotations.NotNull()
    java.time.Instant timestamp) {
    }
    
    public final void updatePreparationMethod(@org.jetbrains.annotations.NotNull()
    java.lang.String method) {
    }
    
    public final void updateNotes(@org.jetbrains.annotations.NotNull()
    java.lang.String notes) {
    }
    
    private final void analyzeFODMAPContent() {
    }
    
    public final void saveFoodEntry() {
    }
    
    public final void resetForm() {
    }
    
    public final void clearError() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<java.lang.String>> getSuggestedIngredients(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<java.lang.String>> getRecentFoodNames() {
        return null;
    }
}