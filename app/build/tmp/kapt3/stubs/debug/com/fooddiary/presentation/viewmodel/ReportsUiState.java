package com.fooddiary.presentation.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b3\b\u0086\b\u0018\u00002\u00020\u0001B\u00a7\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007\u0012\b\b\u0002\u0010\t\u001a\u00020\u0007\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\u000b\u0012\b\b\u0002\u0010\r\u001a\u00020\u000b\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u000b\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u000b\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0013\u0012\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u000b\u0012\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\u0002\u0010\u0016J\t\u00101\u001a\u00020\u0003H\u00c6\u0003J\u000b\u00102\u001a\u0004\u0018\u00010\u000fH\u00c6\u0003J\u000b\u00103\u001a\u0004\u0018\u00010\u000bH\u00c6\u0003J\u000b\u00104\u001a\u0004\u0018\u00010\u000bH\u00c6\u0003J\u0010\u00105\u001a\u0004\u0018\u00010\u0013H\u00c6\u0003\u00a2\u0006\u0002\u0010\'J\u000b\u00106\u001a\u0004\u0018\u00010\u000bH\u00c6\u0003J\u000b\u00107\u001a\u0004\u0018\u00010\u000bH\u00c6\u0003J\t\u00108\u001a\u00020\u0003H\u00c6\u0003J\t\u00109\u001a\u00020\u0003H\u00c6\u0003J\t\u0010:\u001a\u00020\u0007H\u00c6\u0003J\t\u0010;\u001a\u00020\u0007H\u00c6\u0003J\t\u0010<\u001a\u00020\u0007H\u00c6\u0003J\t\u0010=\u001a\u00020\u000bH\u00c6\u0003J\t\u0010>\u001a\u00020\u000bH\u00c6\u0003J\t\u0010?\u001a\u00020\u000bH\u00c6\u0003J\u00b0\u0001\u0010@\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\u00072\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u000b2\b\b\u0002\u0010\r\u001a\u00020\u000b2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u000b2\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u000b2\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00132\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u000b2\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u000bH\u00c6\u0001\u00a2\u0006\u0002\u0010AJ\u0013\u0010B\u001a\u00020\u00032\b\u0010C\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010D\u001a\u00020\u0007H\u00d6\u0001J\t\u0010E\u001a\u00020\u000bH\u00d6\u0001R\u0011\u0010\u0017\u001a\u00020\u00038F\u00a2\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u001a\u001a\u00020\u00038F\u00a2\u0006\u0006\u001a\u0004\b\u001b\u0010\u0019R\u0013\u0010\u0015\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0013\u0010\u0014\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001dR\u0013\u0010\u0011\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u001dR\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0011\u0010\"\u001a\u00020\u00038F\u00a2\u0006\u0006\u001a\u0004\b#\u0010\u0019R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0019R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0019R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0019R\u0013\u0010$\u001a\u0004\u0018\u00010\u000b8F\u00a2\u0006\u0006\u001a\u0004\b%\u0010\u001dR\u0015\u0010\u0012\u001a\u0004\u0018\u00010\u0013\u00a2\u0006\n\n\u0002\u0010(\u001a\u0004\b&\u0010\'R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010\u001dR\u0011\u0010\r\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010\u001dR\u0013\u0010\u0010\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010\u001dR\u0011\u0010\t\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010-R\u0011\u0010\f\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b.\u0010\u001dR\u0011\u0010\b\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b/\u0010-R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b0\u0010-\u00a8\u0006F"}, d2 = {"Lcom/fooddiary/presentation/viewmodel/ReportsUiState;", "", "isLoading", "", "isGenerating", "isExporting", "totalSymptoms", "", "totalFoodEntries", "significantPatternsCount", "reportPreview", "", "timeRangeDescription", "reportTypeDescription", "generatedReport", "Lcom/fooddiary/presentation/viewmodel/ReportData;", "shareableReportText", "exportedFilePath", "lastGeneratedTime", "", "exportSuccessMessage", "errorMessage", "(ZZZIIILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/fooddiary/presentation/viewmodel/ReportData;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;Ljava/lang/String;Ljava/lang/String;)V", "canExportReport", "getCanExportReport", "()Z", "canGenerateReport", "getCanGenerateReport", "getErrorMessage", "()Ljava/lang/String;", "getExportSuccessMessage", "getExportedFilePath", "getGeneratedReport", "()Lcom/fooddiary/presentation/viewmodel/ReportData;", "hasData", "getHasData", "lastGeneratedFormatted", "getLastGeneratedFormatted", "getLastGeneratedTime", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getReportPreview", "getReportTypeDescription", "getShareableReportText", "getSignificantPatternsCount", "()I", "getTimeRangeDescription", "getTotalFoodEntries", "getTotalSymptoms", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(ZZZIIILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/fooddiary/presentation/viewmodel/ReportData;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;Ljava/lang/String;Ljava/lang/String;)Lcom/fooddiary/presentation/viewmodel/ReportsUiState;", "equals", "other", "hashCode", "toString", "app_debug"})
public final class ReportsUiState {
    private final boolean isLoading = false;
    private final boolean isGenerating = false;
    private final boolean isExporting = false;
    private final int totalSymptoms = 0;
    private final int totalFoodEntries = 0;
    private final int significantPatternsCount = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String reportPreview = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String timeRangeDescription = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String reportTypeDescription = null;
    @org.jetbrains.annotations.Nullable()
    private final com.fooddiary.presentation.viewmodel.ReportData generatedReport = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String shareableReportText = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String exportedFilePath = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long lastGeneratedTime = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String exportSuccessMessage = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String errorMessage = null;
    
    public ReportsUiState(boolean isLoading, boolean isGenerating, boolean isExporting, int totalSymptoms, int totalFoodEntries, int significantPatternsCount, @org.jetbrains.annotations.NotNull()
    java.lang.String reportPreview, @org.jetbrains.annotations.NotNull()
    java.lang.String timeRangeDescription, @org.jetbrains.annotations.NotNull()
    java.lang.String reportTypeDescription, @org.jetbrains.annotations.Nullable()
    com.fooddiary.presentation.viewmodel.ReportData generatedReport, @org.jetbrains.annotations.Nullable()
    java.lang.String shareableReportText, @org.jetbrains.annotations.Nullable()
    java.lang.String exportedFilePath, @org.jetbrains.annotations.Nullable()
    java.lang.Long lastGeneratedTime, @org.jetbrains.annotations.Nullable()
    java.lang.String exportSuccessMessage, @org.jetbrains.annotations.Nullable()
    java.lang.String errorMessage) {
        super();
    }
    
    public final boolean isLoading() {
        return false;
    }
    
    public final boolean isGenerating() {
        return false;
    }
    
    public final boolean isExporting() {
        return false;
    }
    
    public final int getTotalSymptoms() {
        return 0;
    }
    
    public final int getTotalFoodEntries() {
        return 0;
    }
    
    public final int getSignificantPatternsCount() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getReportPreview() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTimeRangeDescription() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getReportTypeDescription() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.fooddiary.presentation.viewmodel.ReportData getGeneratedReport() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getShareableReportText() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getExportedFilePath() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getLastGeneratedTime() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getExportSuccessMessage() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getErrorMessage() {
        return null;
    }
    
    public final boolean getHasData() {
        return false;
    }
    
    public final boolean getCanGenerateReport() {
        return false;
    }
    
    public final boolean getCanExportReport() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getLastGeneratedFormatted() {
        return null;
    }
    
    public ReportsUiState() {
        super();
    }
    
    public final boolean component1() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.fooddiary.presentation.viewmodel.ReportData component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component11() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component12() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component13() {
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
    
    public final boolean component2() {
        return false;
    }
    
    public final boolean component3() {
        return false;
    }
    
    public final int component4() {
        return 0;
    }
    
    public final int component5() {
        return 0;
    }
    
    public final int component6() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.presentation.viewmodel.ReportsUiState copy(boolean isLoading, boolean isGenerating, boolean isExporting, int totalSymptoms, int totalFoodEntries, int significantPatternsCount, @org.jetbrains.annotations.NotNull()
    java.lang.String reportPreview, @org.jetbrains.annotations.NotNull()
    java.lang.String timeRangeDescription, @org.jetbrains.annotations.NotNull()
    java.lang.String reportTypeDescription, @org.jetbrains.annotations.Nullable()
    com.fooddiary.presentation.viewmodel.ReportData generatedReport, @org.jetbrains.annotations.Nullable()
    java.lang.String shareableReportText, @org.jetbrains.annotations.Nullable()
    java.lang.String exportedFilePath, @org.jetbrains.annotations.Nullable()
    java.lang.Long lastGeneratedTime, @org.jetbrains.annotations.Nullable()
    java.lang.String exportSuccessMessage, @org.jetbrains.annotations.Nullable()
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