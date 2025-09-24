package com.fooddiary.domain.analysis;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0016\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B=\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\n\u0012\u0006\u0010\f\u001a\u00020\r\u00a2\u0006\u0002\u0010\u000eJ\t\u0010\u001b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u001f\u001a\u00020\nH\u00c6\u0003J\t\u0010 \u001a\u00020\nH\u00c6\u0003J\t\u0010!\u001a\u00020\rH\u00c6\u0003JO\u0010\"\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\n2\b\b\u0002\u0010\f\u001a\u00020\rH\u00c6\u0001J\u0013\u0010#\u001a\u00020$2\b\u0010%\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010&\u001a\u00020\nH\u00d6\u0001J\t\u0010\'\u001a\u00020(H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\b\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0016R\u0011\u0010\u000b\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0019\u00a8\u0006)"}, d2 = {"Lcom/fooddiary/domain/analysis/FastingPeriodAnalysis;", "", "date", "Ljava/time/LocalDate;", "fastingHours", "", "lastMealTime", "Ljava/time/Instant;", "firstMealTime", "symptomsDuringFasting", "", "symptomsAfterBreaking", "fastingBenefit", "", "(Ljava/time/LocalDate;JLjava/time/Instant;Ljava/time/Instant;IID)V", "getDate", "()Ljava/time/LocalDate;", "getFastingBenefit", "()D", "getFastingHours", "()J", "getFirstMealTime", "()Ljava/time/Instant;", "getLastMealTime", "getSymptomsAfterBreaking", "()I", "getSymptomsDuringFasting", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "", "other", "hashCode", "toString", "", "app_debug"})
public final class FastingPeriodAnalysis {
    @org.jetbrains.annotations.NotNull()
    private final java.time.LocalDate date = null;
    private final long fastingHours = 0L;
    @org.jetbrains.annotations.NotNull()
    private final java.time.Instant lastMealTime = null;
    @org.jetbrains.annotations.NotNull()
    private final java.time.Instant firstMealTime = null;
    private final int symptomsDuringFasting = 0;
    private final int symptomsAfterBreaking = 0;
    private final double fastingBenefit = 0.0;
    
    public FastingPeriodAnalysis(@org.jetbrains.annotations.NotNull()
    java.time.LocalDate date, long fastingHours, @org.jetbrains.annotations.NotNull()
    java.time.Instant lastMealTime, @org.jetbrains.annotations.NotNull()
    java.time.Instant firstMealTime, int symptomsDuringFasting, int symptomsAfterBreaking, double fastingBenefit) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.LocalDate getDate() {
        return null;
    }
    
    public final long getFastingHours() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.Instant getLastMealTime() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.Instant getFirstMealTime() {
        return null;
    }
    
    public final int getSymptomsDuringFasting() {
        return 0;
    }
    
    public final int getSymptomsAfterBreaking() {
        return 0;
    }
    
    public final double getFastingBenefit() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.LocalDate component1() {
        return null;
    }
    
    public final long component2() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.Instant component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.time.Instant component4() {
        return null;
    }
    
    public final int component5() {
        return 0;
    }
    
    public final int component6() {
        return 0;
    }
    
    public final double component7() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.domain.analysis.FastingPeriodAnalysis copy(@org.jetbrains.annotations.NotNull()
    java.time.LocalDate date, long fastingHours, @org.jetbrains.annotations.NotNull()
    java.time.Instant lastMealTime, @org.jetbrains.annotations.NotNull()
    java.time.Instant firstMealTime, int symptomsDuringFasting, int symptomsAfterBreaking, double fastingBenefit) {
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