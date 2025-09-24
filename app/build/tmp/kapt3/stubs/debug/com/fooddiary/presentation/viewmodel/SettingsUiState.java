package com.fooddiary.presentation.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u000e\n\u0002\b1\b\u0086\b\u0018\u00002\u00020\u0001B\u00b7\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\f\u001a\u00020\u0003\u0012\b\b\u0002\u0010\r\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0011\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0013\u0012\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0013\u0012\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0013\u0012\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u0013\u00a2\u0006\u0002\u0010\u0017J\t\u0010.\u001a\u00020\u0003H\u00c6\u0003J\t\u0010/\u001a\u00020\u0003H\u00c6\u0003J\t\u00100\u001a\u00020\u000eH\u00c6\u0003J\t\u00101\u001a\u00020\u000eH\u00c6\u0003J\t\u00102\u001a\u00020\u0011H\u00c6\u0003J\u000b\u00103\u001a\u0004\u0018\u00010\u0013H\u00c6\u0003J\u000b\u00104\u001a\u0004\u0018\u00010\u0013H\u00c6\u0003J\u000b\u00105\u001a\u0004\u0018\u00010\u0013H\u00c6\u0003J\u000b\u00106\u001a\u0004\u0018\u00010\u0013H\u00c6\u0003J\t\u00107\u001a\u00020\u0003H\u00c6\u0003J\t\u00108\u001a\u00020\u0003H\u00c6\u0003J\t\u00109\u001a\u00020\u0003H\u00c6\u0003J\t\u0010:\u001a\u00020\u0003H\u00c6\u0003J\t\u0010;\u001a\u00020\u0003H\u00c6\u0003J\t\u0010<\u001a\u00020\u0003H\u00c6\u0003J\t\u0010=\u001a\u00020\u0003H\u00c6\u0003J\t\u0010>\u001a\u00020\u0003H\u00c6\u0003J\u00bb\u0001\u0010?\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u00032\b\b\u0002\u0010\f\u001a\u00020\u00032\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u000e2\b\b\u0002\u0010\u0010\u001a\u00020\u00112\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00132\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u00132\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u00132\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\u0013H\u00c6\u0001J\u0013\u0010@\u001a\u00020\u00032\b\u0010A\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010B\u001a\u00020\u000eH\u00d6\u0001J\t\u0010C\u001a\u00020\u0013H\u00d6\u0001R\u0011\u0010\f\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\t\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0019R\u0011\u0010\u001b\u001a\u00020\u00138F\u00a2\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\u0010\u001a\u00020\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0013\u0010\u0016\u001a\u0004\u0018\u00010\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u001dR\u0013\u0010\u0014\u001a\u0004\u0018\u00010\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001dR\u0011\u0010\"\u001a\u00020\u00038F\u00a2\u0006\u0006\u001a\u0004\b#\u0010\u0019R\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0019R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0019R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0019R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0019R\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u0019R\u0011\u0010\n\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\u0019R\u0011\u0010\b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\u0019R\u0013\u0010\u0012\u001a\u0004\u0018\u00010\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\u001dR\u0011\u0010\u000b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010\u0019R\u0013\u0010\u0015\u001a\u0004\u0018\u00010\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010\u001dR\u0011\u0010)\u001a\u00020\u00138F\u00a2\u0006\u0006\u001a\u0004\b*\u0010\u001dR\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010,R\u0011\u0010\u000f\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010,\u00a8\u0006D"}, d2 = {"Lcom/fooddiary/presentation/viewmodel/SettingsUiState;", "", "isLoading", "", "isExporting", "isImporting", "isClearing", "isOptimizing", "notificationsEnabled", "darkModeEnabled", "medicalMode", "showFodmapInfo", "biometricAuthEnabled", "totalFoodEntries", "", "totalSymptomEvents", "databaseSizeMB", "", "oldestEntryDate", "", "exportFilePath", "successMessage", "errorMessage", "(ZZZZZZZZZZIIDLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getBiometricAuthEnabled", "()Z", "getDarkModeEnabled", "databaseSizeFormatted", "getDatabaseSizeFormatted", "()Ljava/lang/String;", "getDatabaseSizeMB", "()D", "getErrorMessage", "getExportFilePath", "hasData", "getHasData", "getMedicalMode", "getNotificationsEnabled", "getOldestEntryDate", "getShowFodmapInfo", "getSuccessMessage", "totalEntriesFormatted", "getTotalEntriesFormatted", "getTotalFoodEntries", "()I", "getTotalSymptomEvents", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
public final class SettingsUiState {
    private final boolean isLoading = false;
    private final boolean isExporting = false;
    private final boolean isImporting = false;
    private final boolean isClearing = false;
    private final boolean isOptimizing = false;
    private final boolean notificationsEnabled = false;
    private final boolean darkModeEnabled = false;
    private final boolean medicalMode = false;
    private final boolean showFodmapInfo = false;
    private final boolean biometricAuthEnabled = false;
    private final int totalFoodEntries = 0;
    private final int totalSymptomEvents = 0;
    private final double databaseSizeMB = 0.0;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String oldestEntryDate = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String exportFilePath = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String successMessage = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String errorMessage = null;
    
    public SettingsUiState(boolean isLoading, boolean isExporting, boolean isImporting, boolean isClearing, boolean isOptimizing, boolean notificationsEnabled, boolean darkModeEnabled, boolean medicalMode, boolean showFodmapInfo, boolean biometricAuthEnabled, int totalFoodEntries, int totalSymptomEvents, double databaseSizeMB, @org.jetbrains.annotations.Nullable()
    java.lang.String oldestEntryDate, @org.jetbrains.annotations.Nullable()
    java.lang.String exportFilePath, @org.jetbrains.annotations.Nullable()
    java.lang.String successMessage, @org.jetbrains.annotations.Nullable()
    java.lang.String errorMessage) {
        super();
    }
    
    public final boolean isLoading() {
        return false;
    }
    
    public final boolean isExporting() {
        return false;
    }
    
    public final boolean isImporting() {
        return false;
    }
    
    public final boolean isClearing() {
        return false;
    }
    
    public final boolean isOptimizing() {
        return false;
    }
    
    public final boolean getNotificationsEnabled() {
        return false;
    }
    
    public final boolean getDarkModeEnabled() {
        return false;
    }
    
    public final boolean getMedicalMode() {
        return false;
    }
    
    public final boolean getShowFodmapInfo() {
        return false;
    }
    
    public final boolean getBiometricAuthEnabled() {
        return false;
    }
    
    public final int getTotalFoodEntries() {
        return 0;
    }
    
    public final int getTotalSymptomEvents() {
        return 0;
    }
    
    public final double getDatabaseSizeMB() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getOldestEntryDate() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getExportFilePath() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getSuccessMessage() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getErrorMessage() {
        return null;
    }
    
    public final boolean getHasData() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDatabaseSizeFormatted() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTotalEntriesFormatted() {
        return null;
    }
    
    public SettingsUiState() {
        super();
    }
    
    public final boolean component1() {
        return false;
    }
    
    public final boolean component10() {
        return false;
    }
    
    public final int component11() {
        return 0;
    }
    
    public final int component12() {
        return 0;
    }
    
    public final double component13() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component14() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component15() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component16() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component17() {
        return null;
    }
    
    public final boolean component2() {
        return false;
    }
    
    public final boolean component3() {
        return false;
    }
    
    public final boolean component4() {
        return false;
    }
    
    public final boolean component5() {
        return false;
    }
    
    public final boolean component6() {
        return false;
    }
    
    public final boolean component7() {
        return false;
    }
    
    public final boolean component8() {
        return false;
    }
    
    public final boolean component9() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.presentation.viewmodel.SettingsUiState copy(boolean isLoading, boolean isExporting, boolean isImporting, boolean isClearing, boolean isOptimizing, boolean notificationsEnabled, boolean darkModeEnabled, boolean medicalMode, boolean showFodmapInfo, boolean biometricAuthEnabled, int totalFoodEntries, int totalSymptomEvents, double databaseSizeMB, @org.jetbrains.annotations.Nullable()
    java.lang.String oldestEntryDate, @org.jetbrains.annotations.Nullable()
    java.lang.String exportFilePath, @org.jetbrains.annotations.Nullable()
    java.lang.String successMessage, @org.jetbrains.annotations.Nullable()
    java.lang.String errorMessage) {
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