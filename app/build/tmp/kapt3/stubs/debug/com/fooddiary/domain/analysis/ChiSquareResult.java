package com.fooddiary.domain.analysis;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0013\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u00a2\u0006\u0002\u0010\u000bJ\t\u0010\u0014\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\bH\u00c6\u0003J\t\u0010\u0018\u001a\u00020\nH\u00c6\u0003J;\u0010\u0019\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\nH\u00c6\u0001J\u0013\u0010\u001a\u001a\u00020\n2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001c\u001a\u00020\u0006H\u00d6\u0001J\t\u0010\u001d\u001a\u00020\u001eH\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0012R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\r\u00a8\u0006\u001f"}, d2 = {"Lcom/fooddiary/domain/analysis/ChiSquareResult;", "", "chiSquareStatistic", "", "pValue", "degreesOfFreedom", "", "contingencyTable", "Lcom/fooddiary/domain/analysis/ContingencyTable;", "isStatisticallySignificant", "", "(DDILcom/fooddiary/domain/analysis/ContingencyTable;Z)V", "getChiSquareStatistic", "()D", "getContingencyTable", "()Lcom/fooddiary/domain/analysis/ContingencyTable;", "getDegreesOfFreedom", "()I", "()Z", "getPValue", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "other", "hashCode", "toString", "", "app_debug"})
public final class ChiSquareResult {
    private final double chiSquareStatistic = 0.0;
    private final double pValue = 0.0;
    private final int degreesOfFreedom = 0;
    @org.jetbrains.annotations.NotNull()
    private final com.fooddiary.domain.analysis.ContingencyTable contingencyTable = null;
    private final boolean isStatisticallySignificant = false;
    
    public ChiSquareResult(double chiSquareStatistic, double pValue, int degreesOfFreedom, @org.jetbrains.annotations.NotNull()
    com.fooddiary.domain.analysis.ContingencyTable contingencyTable, boolean isStatisticallySignificant) {
        super();
    }
    
    public final double getChiSquareStatistic() {
        return 0.0;
    }
    
    public final double getPValue() {
        return 0.0;
    }
    
    public final int getDegreesOfFreedom() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.domain.analysis.ContingencyTable getContingencyTable() {
        return null;
    }
    
    public final boolean isStatisticallySignificant() {
        return false;
    }
    
    public final double component1() {
        return 0.0;
    }
    
    public final double component2() {
        return 0.0;
    }
    
    public final int component3() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.domain.analysis.ContingencyTable component4() {
        return null;
    }
    
    public final boolean component5() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.domain.analysis.ChiSquareResult copy(double chiSquareStatistic, double pValue, int degreesOfFreedom, @org.jetbrains.annotations.NotNull()
    com.fooddiary.domain.analysis.ContingencyTable contingencyTable, boolean isStatisticallySignificant) {
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