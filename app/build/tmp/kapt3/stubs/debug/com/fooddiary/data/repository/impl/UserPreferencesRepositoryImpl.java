package com.fooddiary.data.repository.impl;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\r\n\u0002\u0010\u0002\n\u0002\b\u0012\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u001d\u001a\u00020\u001eH\u0096@\u00a2\u0006\u0002\u0010\u001fJ\u0016\u0010 \u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\u0007H\u0096@\u00a2\u0006\u0002\u0010\"J\u0016\u0010#\u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\u0007H\u0096@\u00a2\u0006\u0002\u0010\"J\u0016\u0010$\u001a\u00020\u001e2\u0006\u0010%\u001a\u00020\rH\u0096@\u00a2\u0006\u0002\u0010&J\u0016\u0010\'\u001a\u00020\u001e2\u0006\u0010(\u001a\u00020\u0010H\u0096@\u00a2\u0006\u0002\u0010)J\u0016\u0010*\u001a\u00020\u001e2\u0006\u0010+\u001a\u00020\u0010H\u0096@\u00a2\u0006\u0002\u0010)J\u0016\u0010,\u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\u0007H\u0096@\u00a2\u0006\u0002\u0010\"J\u0016\u0010-\u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\u0007H\u0096@\u00a2\u0006\u0002\u0010\"J\u0016\u0010.\u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\u0007H\u0096@\u00a2\u0006\u0002\u0010\"J\u0016\u0010/\u001a\u00020\u001e2\u0006\u0010+\u001a\u00020\u0010H\u0096@\u00a2\u0006\u0002\u0010)R\u001a\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u001a\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u0006X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\tR\u001a\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\u0006X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\tR\u001a\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00100\u0006X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\tR\u001a\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\tR\u001a\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\tR\u001a\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\tR\u001a\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00100\u0006X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\tR\u0010\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u001c\u00a8\u00060"}, d2 = {"Lcom/fooddiary/data/repository/impl/UserPreferencesRepositoryImpl;", "", "userPreferences", "error/NonExistentClass", "(Lerror/NonExistentClass;)V", "biometricAuthEnabled", "Lkotlinx/coroutines/flow/Flow;", "", "getBiometricAuthEnabled", "()Lkotlinx/coroutines/flow/Flow;", "darkModeEnabled", "getDarkModeEnabled", "dataRetentionDays", "", "getDataRetentionDays", "exportFormat", "", "getExportFormat", "mealReminderTime", "getMealReminderTime", "medicalMode", "getMedicalMode", "notificationsEnabled", "getNotificationsEnabled", "showFodmapInfo", "getShowFodmapInfo", "symptomReminderTime", "getSymptomReminderTime", "Lerror/NonExistentClass;", "resetToDefaults", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setBiometricAuthEnabled", "enabled", "(ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setDarkModeEnabled", "setDataRetentionDays", "days", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setExportFormat", "format", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setMealReminderTime", "time", "setMedicalMode", "setNotificationsEnabled", "setShowFodmapInfo", "setSymptomReminderTime", "app_debug"})
public final class UserPreferencesRepositoryImpl {
    @org.jetbrains.annotations.NotNull()
    private final error.NonExistentClass userPreferences = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.Boolean> notificationsEnabled = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.String> mealReminderTime = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.String> symptomReminderTime = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.Boolean> darkModeEnabled = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.Integer> dataRetentionDays = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.String> exportFormat = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.Boolean> medicalMode = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.Boolean> showFodmapInfo = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.Boolean> biometricAuthEnabled = null;
    
    @javax.inject.Inject()
    public UserPreferencesRepositoryImpl(@org.jetbrains.annotations.NotNull()
    error.NonExistentClass userPreferences) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.lang.Boolean> getNotificationsEnabled() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.lang.String> getMealReminderTime() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.lang.String> getSymptomReminderTime() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.lang.Boolean> getDarkModeEnabled() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.lang.Integer> getDataRetentionDays() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.lang.String> getExportFormat() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.lang.Boolean> getMedicalMode() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.lang.Boolean> getShowFodmapInfo() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.lang.Boolean> getBiometricAuthEnabled() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object setNotificationsEnabled(boolean enabled, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object setMealReminderTime(@org.jetbrains.annotations.NotNull()
    java.lang.String time, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object setSymptomReminderTime(@org.jetbrains.annotations.NotNull()
    java.lang.String time, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object setDarkModeEnabled(boolean enabled, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object setDataRetentionDays(int days, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object setExportFormat(@org.jetbrains.annotations.NotNull()
    java.lang.String format, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object setMedicalMode(boolean enabled, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object setShowFodmapInfo(boolean enabled, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object setBiometricAuthEnabled(boolean enabled, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object resetToDefaults(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}