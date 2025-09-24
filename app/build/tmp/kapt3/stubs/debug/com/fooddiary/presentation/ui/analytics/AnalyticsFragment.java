package com.fooddiary.presentation.ui.analytics;

@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0016\u001a\u00020\u0017H\u0002J$\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0016J\b\u0010 \u001a\u00020\u0017H\u0016J\u001a\u0010!\u001a\u00020\u00172\u0006\u0010\"\u001a\u00020\u00192\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0016J\b\u0010#\u001a\u00020\u0017H\u0002J\b\u0010$\u001a\u00020\u0017H\u0002J\b\u0010%\u001a\u00020\u0017H\u0002J\b\u0010&\u001a\u00020\u0017H\u0002J\b\u0010\'\u001a\u00020\u0017H\u0002J\b\u0010(\u001a\u00020\u0017H\u0002J\b\u0010)\u001a\u00020\u0017H\u0002J\b\u0010*\u001a\u00020\u0017H\u0002J\b\u0010+\u001a\u00020\u0017H\u0002J\u0010\u0010,\u001a\u00020\u00172\u0006\u0010-\u001a\u00020.H\u0002J\u0010\u0010/\u001a\u00020\u00172\u0006\u00100\u001a\u000201H\u0002J\u0010\u00102\u001a\u00020\u00172\u0006\u00103\u001a\u00020.H\u0002J\u0015\u00104\u001a\u00020\u00172\u0006\u00105\u001a\u00020\u0004H\u0002\u00a2\u0006\u0002\u00106R\u0010\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0004\n\u0002\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0010\u001a\u00020\u00118BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0012\u0010\u0013\u00a8\u00067"}, d2 = {"Lcom/fooddiary/presentation/ui/analytics/AnalyticsFragment;", "Landroidx/fragment/app/Fragment;", "()V", "_binding", "error/NonExistentClass", "Lerror/NonExistentClass;", "binding", "getBinding", "()Lerror/NonExistentClass;", "timeRangeAdapter", "Landroid/widget/ArrayAdapter;", "Lcom/fooddiary/presentation/viewmodel/TimeRange;", "triggerFoodSummaryAdapter", "Lcom/fooddiary/presentation/adapters/TriggerFoodSummaryAdapter;", "triggerPatternAdapter", "Lcom/fooddiary/presentation/adapters/TriggerPatternAdapter;", "viewModel", "Lcom/fooddiary/presentation/viewmodel/AnalyticsViewModel;", "getViewModel", "()Lcom/fooddiary/presentation/viewmodel/AnalyticsViewModel;", "viewModel$delegate", "Lkotlin/Lazy;", "observeViewModel", "", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "onViewCreated", "view", "setupClickListeners", "setupRecyclerViews", "setupTimeRangeSelector", "showFoodsTab", "showOverviewTab", "showPatternConfidenceInfo", "showPatternsTab", "showStatisticalSignificanceInfo", "showSymptomsTab", "updatePatternsVisibility", "hasPatterns", "", "updateTabSelection", "selectedTab", "", "updateTriggerFoodsVisibility", "hasFoods", "updateUI", "state", "(Lerror/NonExistentClass;)V", "app_debug"})
public final class AnalyticsFragment extends androidx.fragment.app.Fragment {
    @org.jetbrains.annotations.Nullable()
    private error.NonExistentClass _binding;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewModel$delegate = null;
    private com.fooddiary.presentation.adapters.TriggerPatternAdapter triggerPatternAdapter;
    private com.fooddiary.presentation.adapters.TriggerFoodSummaryAdapter triggerFoodSummaryAdapter;
    private android.widget.ArrayAdapter<com.fooddiary.presentation.viewmodel.TimeRange> timeRangeAdapter;
    
    public AnalyticsFragment() {
        super();
    }
    
    private final error.NonExistentClass getBinding() {
        return null;
    }
    
    private final com.fooddiary.presentation.viewmodel.AnalyticsViewModel getViewModel() {
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
    
    private final void setupRecyclerViews() {
    }
    
    private final void setupTimeRangeSelector() {
    }
    
    private final void setupClickListeners() {
    }
    
    private final void observeViewModel() {
    }
    
    private final void updateUI(error.NonExistentClass state) {
    }
    
    private final void updatePatternsVisibility(boolean hasPatterns) {
    }
    
    private final void updateTriggerFoodsVisibility(boolean hasFoods) {
    }
    
    private final void showOverviewTab() {
    }
    
    private final void showPatternsTab() {
    }
    
    private final void showFoodsTab() {
    }
    
    private final void showSymptomsTab() {
    }
    
    private final void updateTabSelection(int selectedTab) {
    }
    
    private final void showStatisticalSignificanceInfo() {
    }
    
    private final void showPatternConfidenceInfo() {
    }
    
    @java.lang.Override()
    public void onDestroyView() {
    }
}