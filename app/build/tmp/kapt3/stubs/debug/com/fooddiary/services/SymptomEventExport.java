package com.fooddiary.services;

@kotlinx.serialization.Serializable()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u001f\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001Be\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0005\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0005\u0012\u000e\b\u0002\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00050\u000f\u00a2\u0006\u0002\u0010\u0010J\t\u0010#\u001a\u00020\u0003H\u00c6\u0003J\t\u0010$\u001a\u00020\u0005H\u00c6\u0003J\t\u0010%\u001a\u00020\u0007H\u00c6\u0003J\t\u0010&\u001a\u00020\tH\u00c6\u0003J\u0010\u0010\'\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0014J\t\u0010(\u001a\u00020\u0005H\u00c6\u0003J\u000b\u0010)\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000b\u0010*\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000f\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00050\u000fH\u00c6\u0003Jt\u0010,\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\u000b\u001a\u00020\u00052\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00052\u000e\b\u0002\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00050\u000fH\u00c6\u0001\u00a2\u0006\u0002\u0010-J\u0013\u0010.\u001a\u00020/2\b\u00100\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00101\u001a\u00020\u0007H\u00d6\u0001J\t\u00102\u001a\u00020\u0005H\u00d6\u0001R\u0013\u0010\f\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0015\u0010\n\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\n\n\u0002\u0010\u0015\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0013\u0010\r\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0012R\u0011\u0010\u000b\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0012R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0012R\u001c\u0010\b\u001a\u00020\t8\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001f\u0010 R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00050\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\"\u00a8\u00063"}, d2 = {"Lcom/fooddiary/services/SymptomEventExport;", "", "id", "", "symptomType", "", "severity", "", "timestamp", "Ljava/time/Instant;", "duration", "notes", "bristolStoolType", "location", "triggers", "", "(JLjava/lang/String;ILjava/time/Instant;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;)V", "getBristolStoolType", "()Ljava/lang/String;", "getDuration", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getId", "()J", "getLocation", "getNotes", "getSeverity", "()I", "getSymptomType", "getTimestamp$annotations", "()V", "getTimestamp", "()Ljava/time/Instant;", "getTriggers", "()Ljava/util/List;", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(JLjava/lang/String;ILjava/time/Instant;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;)Lcom/fooddiary/services/SymptomEventExport;", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class SymptomEventExport {
    private final long id = 0L;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String symptomType = null;
    private final int severity = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.time.Instant timestamp = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer duration = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String notes = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String bristolStoolType = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String location = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> triggers = null;
    
    public SymptomEventExport(long id, @org.jetbrains.annotations.NotNull()
    java.lang.String symptomType, int severity, @org.jetbrains.annotations.NotNull()
    java.time.Instant timestamp, @org.jetbrains.annotations.Nullable()
    java.lang.Integer duration, @org.jetbrains.annotations.NotNull()
    java.lang.String notes, @org.jetbrains.annotations.Nullable()
    java.lang.String bristolStoolType, @org.jetbrains.annotations.Nullable()
    java.lang.String location, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> triggers) {
        super();
    }
    
    public final long getId() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSymptomType() {
        return null;
    }
    
    public final int getSeverity() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.Instant getTimestamp() {
        return null;
    }
    
    @kotlinx.serialization.Serializable(with = com.fooddiary.services.InstantSerializer.class)
    @java.lang.Deprecated()
    public static void getTimestamp$annotations() {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getDuration() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getNotes() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getBristolStoolType() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getLocation() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getTriggers() {
        return null;
    }
    
    public final long component1() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    public final int component3() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.Instant component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.services.SymptomEventExport copy(long id, @org.jetbrains.annotations.NotNull()
    java.lang.String symptomType, int severity, @org.jetbrains.annotations.NotNull()
    java.time.Instant timestamp, @org.jetbrains.annotations.Nullable()
    java.lang.Integer duration, @org.jetbrains.annotations.NotNull()
    java.lang.String notes, @org.jetbrains.annotations.Nullable()
    java.lang.String bristolStoolType, @org.jetbrains.annotations.Nullable()
    java.lang.String location, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> triggers) {
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