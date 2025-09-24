package com.fooddiary.data.integrity;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B;\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u00a2\u0006\u0002\u0010\rJ\t\u0010\u0018\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\nH\u00c6\u0003J\t\u0010\u001d\u001a\u00020\fH\u00c6\u0003JK\u0010\u001e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\fH\u00c6\u0001J\u0013\u0010\u001f\u001a\u00020 2\b\u0010!\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\"\u001a\u00020\u0003H\u00d6\u0001J\t\u0010#\u001a\u00020$H\u00d6\u0001R\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0011R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0011\u00a8\u0006%"}, d2 = {"Lcom/fooddiary/data/integrity/IntegrityReport;", "", "totalIssues", "", "criticalIssues", "warnings", "issues", "", "Lcom/fooddiary/data/integrity/IntegrityIssue;", "statistics", "Lcom/fooddiary/data/integrity/IntegrityStatistics;", "checkTimestamp", "Ljava/time/Instant;", "(IIILjava/util/List;Lcom/fooddiary/data/integrity/IntegrityStatistics;Ljava/time/Instant;)V", "getCheckTimestamp", "()Ljava/time/Instant;", "getCriticalIssues", "()I", "getIssues", "()Ljava/util/List;", "getStatistics", "()Lcom/fooddiary/data/integrity/IntegrityStatistics;", "getTotalIssues", "getWarnings", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "toString", "", "app_debug"})
public final class IntegrityReport {
    private final int totalIssues = 0;
    private final int criticalIssues = 0;
    private final int warnings = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.fooddiary.data.integrity.IntegrityIssue> issues = null;
    @org.jetbrains.annotations.NotNull()
    private final com.fooddiary.data.integrity.IntegrityStatistics statistics = null;
    @org.jetbrains.annotations.NotNull()
    private final java.time.Instant checkTimestamp = null;
    
    public IntegrityReport(int totalIssues, int criticalIssues, int warnings, @org.jetbrains.annotations.NotNull()
    java.util.List<? extends com.fooddiary.data.integrity.IntegrityIssue> issues, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.integrity.IntegrityStatistics statistics, @org.jetbrains.annotations.NotNull()
    java.time.Instant checkTimestamp) {
        super();
    }
    
    public final int getTotalIssues() {
        return 0;
    }
    
    public final int getCriticalIssues() {
        return 0;
    }
    
    public final int getWarnings() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.fooddiary.data.integrity.IntegrityIssue> getIssues() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.integrity.IntegrityStatistics getStatistics() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.Instant getCheckTimestamp() {
        return null;
    }
    
    public final int component1() {
        return 0;
    }
    
    public final int component2() {
        return 0;
    }
    
    public final int component3() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.fooddiary.data.integrity.IntegrityIssue> component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.integrity.IntegrityStatistics component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.Instant component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.integrity.IntegrityReport copy(int totalIssues, int criticalIssues, int warnings, @org.jetbrains.annotations.NotNull()
    java.util.List<? extends com.fooddiary.data.integrity.IntegrityIssue> issues, @org.jetbrains.annotations.NotNull()
    com.fooddiary.data.integrity.IntegrityStatistics statistics, @org.jetbrains.annotations.NotNull()
    java.time.Instant checkTimestamp) {
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