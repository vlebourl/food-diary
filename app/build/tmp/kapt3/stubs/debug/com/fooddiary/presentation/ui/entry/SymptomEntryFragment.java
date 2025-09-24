package com.fooddiary.presentation.ui.entry;

@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0012\u001a\u00020\u0013H\u0002J$\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0016J\b\u0010\u001c\u001a\u00020\u0013H\u0016J\u001a\u0010\u001d\u001a\u00020\u00132\u0006\u0010\u001e\u001a\u00020\u00152\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0016J\b\u0010\u001f\u001a\u00020\u0013H\u0002J\b\u0010 \u001a\u00020\u0013H\u0002J\b\u0010!\u001a\u00020\u0013H\u0002J\b\u0010\"\u001a\u00020\u0013H\u0002J\b\u0010#\u001a\u00020\u0013H\u0002J\u0010\u0010$\u001a\u00020\u00132\u0006\u0010%\u001a\u00020&H\u0002J\u0017\u0010\'\u001a\u00020\u00132\b\u0010(\u001a\u0004\u0018\u00010)H\u0002\u00a2\u0006\u0002\u0010*J\b\u0010+\u001a\u00020\u0013H\u0002J\u0016\u0010,\u001a\u00020\u00132\f\u0010-\u001a\b\u0012\u0004\u0012\u00020/0.H\u0002J\u0016\u00100\u001a\u00020\u00132\f\u00101\u001a\b\u0012\u0004\u0012\u00020/0.H\u0002J\u0015\u00102\u001a\u00020\u00132\u0006\u00103\u001a\u00020\u0004H\u0002\u00a2\u0006\u0002\u00104R\u0010\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0004\n\u0002\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u001b\u0010\f\u001a\u00020\r8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u000e\u0010\u000f\u00a8\u00065"}, d2 = {"Lcom/fooddiary/presentation/ui/entry/SymptomEntryFragment;", "Landroidx/fragment/app/Fragment;", "()V", "_binding", "error/NonExistentClass", "Lerror/NonExistentClass;", "binding", "getBinding", "()Lerror/NonExistentClass;", "symptomTypeAdapter", "Landroid/widget/ArrayAdapter;", "Lcom/fooddiary/data/models/SymptomType;", "viewModel", "Lcom/fooddiary/presentation/viewmodel/SymptomEntryViewModel;", "getViewModel", "()Lcom/fooddiary/presentation/viewmodel/SymptomEntryViewModel;", "viewModel$delegate", "Lkotlin/Lazy;", "observeViewModel", "", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "onViewCreated", "view", "setupAdapters", "setupBristolScaleButtons", "setupClickListeners", "setupSliders", "showDateTimePicker", "showTimePicker", "dateInMillis", "", "updateBristolScaleSelection", "selectedScale", "", "(Ljava/lang/Integer;)V", "updateDuration", "updateFoodSuggestions", "suggestions", "", "", "updateTriggersChips", "triggers", "updateUI", "state", "(Lerror/NonExistentClass;)V", "app_debug"})
public final class SymptomEntryFragment extends androidx.fragment.app.Fragment {
    @org.jetbrains.annotations.Nullable()
    private error.NonExistentClass _binding;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewModel$delegate = null;
    private android.widget.ArrayAdapter<com.fooddiary.data.models.SymptomType> symptomTypeAdapter;
    
    public SymptomEntryFragment() {
        super();
    }
    
    private final error.NonExistentClass getBinding() {
        return null;
    }
    
    private final com.fooddiary.presentation.viewmodel.SymptomEntryViewModel getViewModel() {
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
    
    private final void setupSliders() {
    }
    
    private final void setupBristolScaleButtons() {
    }
    
    private final void updateBristolScaleSelection(java.lang.Integer selectedScale) {
    }
    
    private final void updateDuration() {
    }
    
    private final void observeViewModel() {
    }
    
    private final void updateUI(error.NonExistentClass state) {
    }
    
    private final void updateTriggersChips(java.util.List<java.lang.String> triggers) {
    }
    
    private final void updateFoodSuggestions(java.util.List<java.lang.String> suggestions) {
    }
    
    private final void showDateTimePicker() {
    }
    
    private final void showTimePicker(long dateInMillis) {
    }
    
    @java.lang.Override()
    public void onDestroyView() {
    }
}