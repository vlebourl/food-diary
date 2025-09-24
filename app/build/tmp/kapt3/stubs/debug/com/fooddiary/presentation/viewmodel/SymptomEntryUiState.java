package com.fooddiary.presentation.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b:\b\u0086\b\u0018\u00002\u00020\u0001B\u00cb\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0005\u0012\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\n0\r\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000f\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\n\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0013\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0013\u0012\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\n\u00a2\u0006\u0002\u0010\u0019J\t\u00106\u001a\u00020\u0003H\u00c6\u0003J\u000b\u00107\u001a\u0004\u0018\u00010\nH\u00c6\u0003J\u000b\u00108\u001a\u0004\u0018\u00010\nH\u00c6\u0003J\t\u00109\u001a\u00020\u0013H\u00c6\u0003J\t\u0010:\u001a\u00020\u0013H\u00c6\u0003J\u000b\u0010;\u001a\u0004\u0018\u00010\nH\u00c6\u0003J\u000b\u0010<\u001a\u0004\u0018\u00010\nH\u00c6\u0003J\u000b\u0010=\u001a\u0004\u0018\u00010\nH\u00c6\u0003J\u000b\u0010>\u001a\u0004\u0018\u00010\nH\u00c6\u0003J\t\u0010?\u001a\u00020\u0005H\u00c6\u0003J\u0010\u0010@\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001bJ\u0010\u0010A\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001bJ\u0010\u0010B\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001bJ\u000b\u0010C\u001a\u0004\u0018\u00010\nH\u00c6\u0003J\u0010\u0010D\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003\u00a2\u0006\u0002\u0010\u001bJ\u000f\u0010E\u001a\b\u0012\u0004\u0012\u00020\n0\rH\u00c6\u0003J\t\u0010F\u001a\u00020\u000fH\u00c6\u0003J\u00d4\u0001\u0010G\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00052\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\n0\r2\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\n2\b\b\u0002\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u00132\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u0016\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\nH\u00c6\u0001\u00a2\u0006\u0002\u0010HJ\u0013\u0010I\u001a\u00020\u00132\b\u0010J\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010K\u001a\u00020\u0005H\u00d6\u0001J\t\u0010L\u001a\u00020\nH\u00d6\u0001R\u0015\u0010\u000b\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\n\n\u0002\u0010\u001c\u001a\u0004\b\u001a\u0010\u001bR\u0013\u0010\u0018\u001a\u0004\u0018\u00010\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0015\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\n\n\u0002\u0010\u001c\u001a\u0004\b\u001f\u0010\u001bR\u0013\u0010 \u001a\u0004\u0018\u00010\n8F\u00a2\u0006\u0006\u001a\u0004\b!\u0010\u001eR\u0015\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\n\n\u0002\u0010\u001c\u001a\u0004\b\"\u0010\u001bR\u0015\u0010\b\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\n\n\u0002\u0010\u001c\u001a\u0004\b#\u0010\u001bR\u0013\u0010\u0016\u001a\u0004\u0018\u00010\n\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\u001eR\u0011\u0010\u0012\u001a\u00020\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010%R\u0011\u0010\u0014\u001a\u00020\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010%R\u0011\u0010&\u001a\u00020\u00138F\u00a2\u0006\u0006\u001a\u0004\b&\u0010%R\u0013\u0010\t\u001a\u0004\u0018\u00010\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010\u001eR\u0013\u0010\u0010\u001a\u0004\u0018\u00010\n\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010\u001eR\u0013\u0010\u0011\u001a\u0004\u0018\u00010\n\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010\u001eR\u0013\u0010\u0015\u001a\u0004\u0018\u00010\n\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010\u001eR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010,R\u0013\u0010\u0017\u001a\u0004\u0018\u00010\n\u00a2\u0006\b\n\u0000\u001a\u0004\b-\u0010\u001eR\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\n0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b.\u0010/R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b0\u00101R\u0011\u0010\u000e\u001a\u00020\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b2\u00103R\u0011\u00104\u001a\u00020\n8F\u00a2\u0006\u0006\u001a\u0004\b5\u0010\u001e\u00a8\u0006M"}, d2 = {"Lcom/fooddiary/presentation/viewmodel/SymptomEntryUiState;", "", "symptomType", "Lcom/fooddiary/data/models/SymptomType;", "severity", "", "duration", "durationHours", "durationMinutes", "location", "", "bristolScale", "suspectedTriggers", "", "timestamp", "Ljava/time/Instant;", "notes", "photoPath", "isLoading", "", "isSuccess", "savedEventId", "errorMessage", "severityError", "bristolScaleError", "(Lcom/fooddiary/data/models/SymptomType;ILjava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Ljava/util/List;Ljava/time/Instant;Ljava/lang/String;Ljava/lang/String;ZZLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getBristolScale", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getBristolScaleError", "()Ljava/lang/String;", "getDuration", "durationFormatted", "getDurationFormatted", "getDurationHours", "getDurationMinutes", "getErrorMessage", "()Z", "isValid", "getLocation", "getNotes", "getPhotoPath", "getSavedEventId", "getSeverity", "()I", "getSeverityError", "getSuspectedTriggers", "()Ljava/util/List;", "getSymptomType", "()Lcom/fooddiary/data/models/SymptomType;", "getTimestamp", "()Ljava/time/Instant;", "timestampFormatted", "getTimestampFormatted", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Lcom/fooddiary/data/models/SymptomType;ILjava/lang/Integer;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Ljava/util/List;Ljava/time/Instant;Ljava/lang/String;Ljava/lang/String;ZZLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lcom/fooddiary/presentation/viewmodel/SymptomEntryUiState;", "equals", "other", "hashCode", "toString", "app_debug"})
public final class SymptomEntryUiState {
    @org.jetbrains.annotations.NotNull()
    private final com.fooddiary.data.models.SymptomType symptomType = null;
    private final int severity = 0;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer duration = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer durationHours = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer durationMinutes = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String location = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer bristolScale = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> suspectedTriggers = null;
    @org.jetbrains.annotations.NotNull()
    private final java.time.Instant timestamp = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String notes = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String photoPath = null;
    private final boolean isLoading = false;
    private final boolean isSuccess = false;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String savedEventId = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String errorMessage = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String severityError = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String bristolScaleError = null;
    
    public SymptomEntryUiState(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.SymptomType symptomType, int severity, @org.jetbrains.annotations.Nullable()
    java.lang.Integer duration, @org.jetbrains.annotations.Nullable()
    java.lang.Integer durationHours, @org.jetbrains.annotations.Nullable()
    java.lang.Integer durationMinutes, @org.jetbrains.annotations.Nullable()
    java.lang.String location, @org.jetbrains.annotations.Nullable()
    java.lang.Integer bristolScale, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> suspectedTriggers, @org.jetbrains.annotations.NotNull()
    java.time.Instant timestamp, @org.jetbrains.annotations.Nullable()
    java.lang.String notes, @org.jetbrains.annotations.Nullable()
    java.lang.String photoPath, boolean isLoading, boolean isSuccess, @org.jetbrains.annotations.Nullable()
    java.lang.String savedEventId, @org.jetbrains.annotations.Nullable()
    java.lang.String errorMessage, @org.jetbrains.annotations.Nullable()
    java.lang.String severityError, @org.jetbrains.annotations.Nullable()
    java.lang.String bristolScaleError) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.models.SymptomType getSymptomType() {
        return null;
    }
    
    public final int getSeverity() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getDuration() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getDurationHours() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getDurationMinutes() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getLocation() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getBristolScale() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getSuspectedTriggers() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.Instant getTimestamp() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getNotes() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getPhotoPath() {
        return null;
    }
    
    public final boolean isLoading() {
        return false;
    }
    
    public final boolean isSuccess() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getSavedEventId() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getErrorMessage() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getSeverityError() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getBristolScaleError() {
        return null;
    }
    
    public final boolean isValid() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTimestampFormatted() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getDurationFormatted() {
        return null;
    }
    
    public SymptomEntryUiState() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.models.SymptomType component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component11() {
        return null;
    }
    
    public final boolean component12() {
        return false;
    }
    
    public final boolean component13() {
        return false;
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
    
    public final int component2() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.Instant component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.presentation.viewmodel.SymptomEntryUiState copy(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.models.SymptomType symptomType, int severity, @org.jetbrains.annotations.Nullable()
    java.lang.Integer duration, @org.jetbrains.annotations.Nullable()
    java.lang.Integer durationHours, @org.jetbrains.annotations.Nullable()
    java.lang.Integer durationMinutes, @org.jetbrains.annotations.Nullable()
    java.lang.String location, @org.jetbrains.annotations.Nullable()
    java.lang.Integer bristolScale, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> suspectedTriggers, @org.jetbrains.annotations.NotNull()
    java.time.Instant timestamp, @org.jetbrains.annotations.Nullable()
    java.lang.String notes, @org.jetbrains.annotations.Nullable()
    java.lang.String photoPath, boolean isLoading, boolean isSuccess, @org.jetbrains.annotations.Nullable()
    java.lang.String savedEventId, @org.jetbrains.annotations.Nullable()
    java.lang.String errorMessage, @org.jetbrains.annotations.Nullable()
    java.lang.String severityError, @org.jetbrains.annotations.Nullable()
    java.lang.String bristolScaleError) {
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