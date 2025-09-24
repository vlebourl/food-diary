package com.fooddiary.presentation.ui.entry;

@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0019\u001a\u00020\u001aH\u0002J$\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010 2\b\u0010!\u001a\u0004\u0018\u00010\"H\u0016J\b\u0010#\u001a\u00020\u001aH\u0016J\u001a\u0010$\u001a\u00020\u001a2\u0006\u0010%\u001a\u00020\u001c2\b\u0010!\u001a\u0004\u0018\u00010\"H\u0016J\b\u0010&\u001a\u00020\u001aH\u0002J\b\u0010\'\u001a\u00020\u001aH\u0002J\b\u0010(\u001a\u00020\u001aH\u0002J\u0010\u0010)\u001a\u00020\u001a2\u0006\u0010*\u001a\u00020+H\u0002J\u0015\u0010,\u001a\u00020\u001a2\u0006\u0010-\u001a\u00020.H\u0002\u00a2\u0006\u0002\u0010/J\u0016\u00100\u001a\u00020\u001a2\f\u00101\u001a\b\u0012\u0004\u0012\u00020\u001002H\u0002J\u0015\u00103\u001a\u00020\u001a2\u0006\u00104\u001a\u00020.H\u0002\u00a2\u0006\u0002\u0010/R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\tX\u0082.\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0013\u001a\u00020\u00148BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0015\u0010\u0016\u00a8\u00065"}, d2 = {"Lcom/fooddiary/presentation/ui/entry/FoodEntryFragment;", "Landroidx/fragment/app/Fragment;", "()V", "_binding", "Lcom/fooddiary/databinding/FragmentFoodEntryBinding;", "binding", "getBinding", "()Lcom/fooddiary/databinding/FragmentFoodEntryBinding;", "eatingSpeedAdapter", "Landroid/widget/ArrayAdapter;", "Lcom/fooddiary/data/models/EatingSpeed;", "locationAdapter", "Lcom/fooddiary/data/models/LocationType;", "mealTypeAdapter", "Lcom/fooddiary/data/models/MealType;", "portionUnitAdapter", "", "socialContextAdapter", "Lcom/fooddiary/data/models/SocialContext;", "viewModel", "Lcom/fooddiary/presentation/viewmodel/FoodEntryViewModel;", "getViewModel", "()Lcom/fooddiary/presentation/viewmodel/FoodEntryViewModel;", "viewModel$delegate", "Lkotlin/Lazy;", "observeViewModel", "", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "onViewCreated", "view", "setupAdapters", "setupClickListeners", "showDateTimePicker", "showTimePicker", "dateInMillis", "", "updateFODMAPAnalysis", "analysis", "error/NonExistentClass", "(Lerror/NonExistentClass;)V", "updateIngredientsChips", "ingredients", "", "updateUI", "state", "app_debug"})
public final class FoodEntryFragment extends androidx.fragment.app.Fragment {
    @org.jetbrains.annotations.Nullable()
    private com.fooddiary.databinding.FragmentFoodEntryBinding _binding;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewModel$delegate = null;
    private android.widget.ArrayAdapter<com.fooddiary.data.models.MealType> mealTypeAdapter;
    private android.widget.ArrayAdapter<com.fooddiary.data.models.LocationType> locationAdapter;
    private android.widget.ArrayAdapter<com.fooddiary.data.models.SocialContext> socialContextAdapter;
    private android.widget.ArrayAdapter<com.fooddiary.data.models.EatingSpeed> eatingSpeedAdapter;
    private android.widget.ArrayAdapter<java.lang.String> portionUnitAdapter;
    
    public FoodEntryFragment() {
        super();
    }
    
    private final com.fooddiary.databinding.FragmentFoodEntryBinding getBinding() {
        return null;
    }
    
    private final com.fooddiary.presentation.viewmodel.FoodEntryViewModel getViewModel() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull()
    android.view.LayoutInflater inflater, @org.jetbrains.annotations.Nullable()
    android.view.ViewGroup container, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    @java.lang.Override()
    public void onViewCreated(@org.jetbrains.annotations.NotNull()
    android.view.View view, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setupAdapters() {
    }
    
    private final void setupClickListeners() {
    }
    
    private final void observeViewModel() {
    }
    
    private final void updateUI(error.NonExistentClass state) {
    }
    
    private final void updateIngredientsChips(java.util.List<java.lang.String> ingredients) {
    }
    
    private final void updateFODMAPAnalysis(error.NonExistentClass analysis) {
    }
    
    private final void showDateTimePicker() {
    }
    
    private final void showTimePicker(long dateInMillis) {
    }
    
    @java.lang.Override()
    public void onDestroyView() {
    }
}