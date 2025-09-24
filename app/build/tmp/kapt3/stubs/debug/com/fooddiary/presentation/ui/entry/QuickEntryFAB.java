package com.fooddiary.presentation.ui.entry;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B%\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0006\u0010\u0014\u001a\u00020\u0015J\b\u0010\u0016\u001a\u00020\u0015H\u0002J\b\u0010\u0017\u001a\u00020\u0015H\u0002J\u0010\u0010\u0018\u001a\u00020\u00152\u0006\u0010\u0019\u001a\u00020\nH\u0002J\u0006\u0010\u001a\u001a\u00020\nJ\b\u0010\u001b\u001a\u00020\u0015H\u0002J\b\u0010\u001c\u001a\u00020\u0015H\u0002J\b\u0010\u001d\u001a\u00020\u0015H\u0014J\u000e\u0010\u001e\u001a\u00020\u00152\u0006\u0010\u001f\u001a\u00020\u0007J\u000e\u0010 \u001a\u00020\u00152\u0006\u0010!\u001a\u00020\u0007J\b\u0010\"\u001a\u00020\u0015H\u0002J.\u0010#\u001a\u00020\u00152\u0006\u0010$\u001a\u00020\u00112\u0006\u0010%\u001a\u00020\u00132\u0006\u0010&\u001a\u00020\u00072\f\u0010\'\u001a\b\u0012\u0004\u0012\u00020\u00150(H\u0002J\b\u0010)\u001a\u00020\u0015H\u0002J\b\u0010*\u001a\u00020\u0015H\u0002J\b\u0010+\u001a\u00020\u0015H\u0002R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\rR\u000e\u0010\u000e\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006,"}, d2 = {"Lcom/fooddiary/presentation/ui/entry/QuickEntryFAB;", "Landroid/widget/FrameLayout;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "animationInProgress", "", "binding", "error/NonExistentClass", "Lerror/NonExistentClass;", "isExpanded", "speedDialFabs", "", "Lcom/google/android/material/floatingactionbutton/FloatingActionButton;", "speedDialLabels", "Landroid/view/View;", "collapseIfExpanded", "", "collapseSpeedDial", "expandSpeedDial", "hideSpeedDialOptions", "animate", "isSpeedDialExpanded", "navigateToFoodEntry", "navigateToSymptomEntry", "onDetachedFromWindow", "setMainFabBackgroundColor", "colorRes", "setMainFabIcon", "iconRes", "setupSpeedDial", "setupSpeedDialOption", "fab", "label", "icon", "action", "Lkotlin/Function0;", "showQuickLogDialog", "startVoiceEntry", "toggleSpeedDial", "app_debug"})
public final class QuickEntryFAB extends android.widget.FrameLayout {
    @org.jetbrains.annotations.NotNull()
    private final error.NonExistentClass binding = null;
    private boolean isExpanded = false;
    private boolean animationInProgress = false;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.google.android.material.floatingactionbutton.FloatingActionButton> speedDialFabs = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<android.view.View> speedDialLabels = null;
    
    @kotlin.jvm.JvmOverloads()
    public QuickEntryFAB(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    android.util.AttributeSet attrs, int defStyleAttr) {
        super(null);
    }
    
    private final void setupSpeedDial() {
    }
    
    private final void setupSpeedDialOption(com.google.android.material.floatingactionbutton.FloatingActionButton fab, android.view.View label, int icon, kotlin.jvm.functions.Function0<kotlin.Unit> action) {
    }
    
    private final void toggleSpeedDial() {
    }
    
    private final void expandSpeedDial() {
    }
    
    private final void collapseSpeedDial() {
    }
    
    private final void hideSpeedDialOptions(boolean animate) {
    }
    
    private final void navigateToFoodEntry() {
    }
    
    private final void navigateToSymptomEntry() {
    }
    
    private final void showQuickLogDialog() {
    }
    
    private final void startVoiceEntry() {
    }
    
    public final void setMainFabIcon(int iconRes) {
    }
    
    public final void setMainFabBackgroundColor(int colorRes) {
    }
    
    public final boolean isSpeedDialExpanded() {
        return false;
    }
    
    public final void collapseIfExpanded() {
    }
    
    @java.lang.Override()
    protected void onDetachedFromWindow() {
    }
    
    @kotlin.jvm.JvmOverloads()
    public QuickEntryFAB(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super(null);
    }
    
    @kotlin.jvm.JvmOverloads()
    public QuickEntryFAB(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    android.util.AttributeSet attrs) {
        super(null);
    }
}