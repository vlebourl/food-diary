package com.fooddiary.services;

@kotlinx.serialization.Serializable()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u001a\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001B=\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\rJ\t\u0010\u001d\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001f\u001a\u00020\u0007H\u00c6\u0003J\t\u0010 \u001a\u00020\u0007H\u00c6\u0003J\t\u0010!\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\"\u001a\u00020\u000bH\u00c6\u0003J\t\u0010#\u001a\u00020\u000bH\u00c6\u0003JO\u0010$\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u000bH\u00c6\u0001J\u0013\u0010%\u001a\u00020&2\b\u0010\'\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010(\u001a\u00020\u000bH\u00d6\u0001J\t\u0010)\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\t\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001c\u0010\b\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013R\u001c\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b\u0014\u0010\u0011\u001a\u0004\b\u0015\u0010\u0016R\u001c\u0010\u0006\u001a\u00020\u00078\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b\u0017\u0010\u0011\u001a\u0004\b\u0018\u0010\u0013R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\f\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001aR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u000f\u00a8\u0006*"}, d2 = {"Lcom/fooddiary/services/ExportMetadata;", "", "version", "", "exportDate", "Ljava/time/Instant;", "startDate", "Ljava/time/LocalDate;", "endDate", "appVersion", "totalFoodEntries", "", "totalSymptomEvents", "(Ljava/lang/String;Ljava/time/Instant;Ljava/time/LocalDate;Ljava/time/LocalDate;Ljava/lang/String;II)V", "getAppVersion", "()Ljava/lang/String;", "getEndDate$annotations", "()V", "getEndDate", "()Ljava/time/LocalDate;", "getExportDate$annotations", "getExportDate", "()Ljava/time/Instant;", "getStartDate$annotations", "getStartDate", "getTotalFoodEntries", "()I", "getTotalSymptomEvents", "getVersion", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class ExportMetadata {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String version = null;
    @org.jetbrains.annotations.NotNull()
    private final java.time.Instant exportDate = null;
    @org.jetbrains.annotations.NotNull()
    private final java.time.LocalDate startDate = null;
    @org.jetbrains.annotations.NotNull()
    private final java.time.LocalDate endDate = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String appVersion = null;
    private final int totalFoodEntries = 0;
    private final int totalSymptomEvents = 0;
    
    public ExportMetadata(@org.jetbrains.annotations.NotNull()
    java.lang.String version, @org.jetbrains.annotations.NotNull()
    java.time.Instant exportDate, @org.jetbrains.annotations.NotNull()
    java.time.LocalDate startDate, @org.jetbrains.annotations.NotNull()
    java.time.LocalDate endDate, @org.jetbrains.annotations.NotNull()
    java.lang.String appVersion, int totalFoodEntries, int totalSymptomEvents) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getVersion() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.Instant getExportDate() {
        return null;
    }
    
    @kotlinx.serialization.Serializable(with = com.fooddiary.services.InstantSerializer.class)
    @java.lang.Deprecated()
    public static void getExportDate$annotations() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.LocalDate getStartDate() {
        return null;
    }
    
    @kotlinx.serialization.Serializable(with = com.fooddiary.services.LocalDateSerializer.class)
    @java.lang.Deprecated()
    public static void getStartDate$annotations() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.LocalDate getEndDate() {
        return null;
    }
    
    @kotlinx.serialization.Serializable(with = com.fooddiary.services.LocalDateSerializer.class)
    @java.lang.Deprecated()
    public static void getEndDate$annotations() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getAppVersion() {
        return null;
    }
    
    public final int getTotalFoodEntries() {
        return 0;
    }
    
    public final int getTotalSymptomEvents() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.Instant component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.LocalDate component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.LocalDate component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component5() {
        return null;
    }
    
    public final int component6() {
        return 0;
    }
    
    public final int component7() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.services.ExportMetadata copy(@org.jetbrains.annotations.NotNull()
    java.lang.String version, @org.jetbrains.annotations.NotNull()
    java.time.Instant exportDate, @org.jetbrains.annotations.NotNull()
    java.time.LocalDate startDate, @org.jetbrains.annotations.NotNull()
    java.time.LocalDate endDate, @org.jetbrains.annotations.NotNull()
    java.lang.String appVersion, int totalFoodEntries, int totalSymptomEvents) {
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