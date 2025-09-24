package com.fooddiary.services;

@kotlinx.serialization.Serializable()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u001f\b\u0087\b\u0018\u00002\u00020\u0001BM\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\t\u0012\u0006\u0010\r\u001a\u00020\u0006\u0012\u0006\u0010\u000e\u001a\u00020\t\u00a2\u0006\u0002\u0010\u000fJ\t\u0010\u001c\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u001f\u001a\u00020\u0006H\u00c6\u0003J\t\u0010 \u001a\u00020\tH\u00c6\u0003J\t\u0010!\u001a\u00020\u000bH\u00c6\u0003J\t\u0010\"\u001a\u00020\tH\u00c6\u0003J\t\u0010#\u001a\u00020\u0006H\u00c6\u0003J\t\u0010$\u001a\u00020\tH\u00c6\u0003Jc\u0010%\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00062\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\t2\b\b\u0002\u0010\r\u001a\u00020\u00062\b\b\u0002\u0010\u000e\u001a\u00020\tH\u00c6\u0001J\u0013\u0010&\u001a\u00020\u000b2\b\u0010\'\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010(\u001a\u00020\tH\u00d6\u0001J\t\u0010)\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\r\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\f\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0011R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0017R\u0011\u0010\u000e\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0013R\u0011\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0011R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0013R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0016\u00a8\u0006*"}, d2 = {"Lcom/fooddiary/services/CorrelationExport;", "", "foodName", "", "symptomType", "correlationCoefficient", "", "pValue", "sampleSize", "", "isStatisticallySignificant", "", "averageTimeOffsetMinutes", "averageSeverity", "occurrences", "(Ljava/lang/String;Ljava/lang/String;DDIZIDI)V", "getAverageSeverity", "()D", "getAverageTimeOffsetMinutes", "()I", "getCorrelationCoefficient", "getFoodName", "()Ljava/lang/String;", "()Z", "getOccurrences", "getPValue", "getSampleSize", "getSymptomType", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
public final class CorrelationExport {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String foodName = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String symptomType = null;
    private final double correlationCoefficient = 0.0;
    private final double pValue = 0.0;
    private final int sampleSize = 0;
    private final boolean isStatisticallySignificant = false;
    private final int averageTimeOffsetMinutes = 0;
    private final double averageSeverity = 0.0;
    private final int occurrences = 0;
    
    public CorrelationExport(@org.jetbrains.annotations.NotNull()
    java.lang.String foodName, @org.jetbrains.annotations.NotNull()
    java.lang.String symptomType, double correlationCoefficient, double pValue, int sampleSize, boolean isStatisticallySignificant, int averageTimeOffsetMinutes, double averageSeverity, int occurrences) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFoodName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSymptomType() {
        return null;
    }
    
    public final double getCorrelationCoefficient() {
        return 0.0;
    }
    
    public final double getPValue() {
        return 0.0;
    }
    
    public final int getSampleSize() {
        return 0;
    }
    
    public final boolean isStatisticallySignificant() {
        return false;
    }
    
    public final int getAverageTimeOffsetMinutes() {
        return 0;
    }
    
    public final double getAverageSeverity() {
        return 0.0;
    }
    
    public final int getOccurrences() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    public final double component3() {
        return 0.0;
    }
    
    public final double component4() {
        return 0.0;
    }
    
    public final int component5() {
        return 0;
    }
    
    public final boolean component6() {
        return false;
    }
    
    public final int component7() {
        return 0;
    }
    
    public final double component8() {
        return 0.0;
    }
    
    public final int component9() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.services.CorrelationExport copy(@org.jetbrains.annotations.NotNull()
    java.lang.String foodName, @org.jetbrains.annotations.NotNull()
    java.lang.String symptomType, double correlationCoefficient, double pValue, int sampleSize, boolean isStatisticallySignificant, int averageTimeOffsetMinutes, double averageSeverity, int occurrences) {
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