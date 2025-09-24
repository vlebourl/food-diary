package com.fooddiary.presentation.ui.elimination;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b#\b\u0086\b\u0018\u00002\u00020\u0001B{\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\b\u0012\b\b\u0002\u0010\n\u001a\u00020\b\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f\u0012\b\b\u0002\u0010\r\u001a\u00020\b\u0012\b\b\u0002\u0010\u000e\u001a\u00020\b\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\f\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\f\u00a2\u0006\u0002\u0010\u0011J\t\u0010\u001f\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010 \u001a\u0004\u0018\u00010\fH\u00c6\u0003J\u000b\u0010!\u001a\u0004\u0018\u00010\fH\u00c6\u0003J\t\u0010\"\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010#\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\t\u0010$\u001a\u00020\bH\u00c6\u0003J\t\u0010%\u001a\u00020\bH\u00c6\u0003J\t\u0010&\u001a\u00020\bH\u00c6\u0003J\u000b\u0010\'\u001a\u0004\u0018\u00010\fH\u00c6\u0003J\t\u0010(\u001a\u00020\bH\u00c6\u0003J\t\u0010)\u001a\u00020\bH\u00c6\u0003J\u007f\u0010*\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\b2\b\b\u0002\u0010\n\u001a\u00020\b2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f2\b\b\u0002\u0010\r\u001a\u00020\b2\b\b\u0002\u0010\u000e\u001a\u00020\b2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\f2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\fH\u00c6\u0001J\u0013\u0010+\u001a\u00020\u00032\b\u0010,\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010-\u001a\u00020\bH\u00d6\u0001J\t\u0010.\u001a\u00020\fH\u00d6\u0001R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0013\u0010\u000f\u001a\u0004\u0018\u00010\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0018R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0018R\u0011\u0010\r\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0015R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0017R\u0011\u0010\t\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0015R\u0013\u0010\u0010\u001a\u0004\u0018\u00010\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0017R\u0011\u0010\n\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0015R\u0011\u0010\u000e\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0015\u00a8\u0006/"}, d2 = {"Lcom/fooddiary/presentation/ui/elimination/EliminationProtocolUiState;", "", "isLoading", "", "isProtocolActive", "currentPhase", "Lcom/fooddiary/presentation/ui/elimination/EliminationPhase;", "eliminatedFoodsCount", "", "reintroducedFoodsCount", "symptomEventsCount", "protocolStartDate", "", "protocolDayNumber", "totalProtocolDays", "errorMessage", "successMessage", "(ZZLcom/fooddiary/presentation/ui/elimination/EliminationPhase;IIILjava/lang/String;IILjava/lang/String;Ljava/lang/String;)V", "getCurrentPhase", "()Lcom/fooddiary/presentation/ui/elimination/EliminationPhase;", "getEliminatedFoodsCount", "()I", "getErrorMessage", "()Ljava/lang/String;", "()Z", "getProtocolDayNumber", "getProtocolStartDate", "getReintroducedFoodsCount", "getSuccessMessage", "getSymptomEventsCount", "getTotalProtocolDays", "component1", "component10", "component11", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
public final class EliminationProtocolUiState {
    private final boolean isLoading = false;
    private final boolean isProtocolActive = false;
    @org.jetbrains.annotations.Nullable()
    private final com.fooddiary.presentation.ui.elimination.EliminationPhase currentPhase = null;
    private final int eliminatedFoodsCount = 0;
    private final int reintroducedFoodsCount = 0;
    private final int symptomEventsCount = 0;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String protocolStartDate = null;
    private final int protocolDayNumber = 0;
    private final int totalProtocolDays = 0;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String errorMessage = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String successMessage = null;
    
    public EliminationProtocolUiState(boolean isLoading, boolean isProtocolActive, @org.jetbrains.annotations.Nullable()
    com.fooddiary.presentation.ui.elimination.EliminationPhase currentPhase, int eliminatedFoodsCount, int reintroducedFoodsCount, int symptomEventsCount, @org.jetbrains.annotations.Nullable()
    java.lang.String protocolStartDate, int protocolDayNumber, int totalProtocolDays, @org.jetbrains.annotations.Nullable()
    java.lang.String errorMessage, @org.jetbrains.annotations.Nullable()
    java.lang.String successMessage) {
        super();
    }
    
    public final boolean isLoading() {
        return false;
    }
    
    public final boolean isProtocolActive() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.fooddiary.presentation.ui.elimination.EliminationPhase getCurrentPhase() {
        return null;
    }
    
    public final int getEliminatedFoodsCount() {
        return 0;
    }
    
    public final int getReintroducedFoodsCount() {
        return 0;
    }
    
    public final int getSymptomEventsCount() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getProtocolStartDate() {
        return null;
    }
    
    public final int getProtocolDayNumber() {
        return 0;
    }
    
    public final int getTotalProtocolDays() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getErrorMessage() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getSuccessMessage() {
        return null;
    }
    
    public EliminationProtocolUiState() {
        super();
    }
    
    public final boolean component1() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component11() {
        return null;
    }
    
    public final boolean component2() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.fooddiary.presentation.ui.elimination.EliminationPhase component3() {
        return null;
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
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component7() {
        return null;
    }
    
    public final int component8() {
        return 0;
    }
    
    public final int component9() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.presentation.ui.elimination.EliminationProtocolUiState copy(boolean isLoading, boolean isProtocolActive, @org.jetbrains.annotations.Nullable()
    com.fooddiary.presentation.ui.elimination.EliminationPhase currentPhase, int eliminatedFoodsCount, int reintroducedFoodsCount, int symptomEventsCount, @org.jetbrains.annotations.Nullable()
    java.lang.String protocolStartDate, int protocolDayNumber, int totalProtocolDays, @org.jetbrains.annotations.Nullable()
    java.lang.String errorMessage, @org.jetbrains.annotations.Nullable()
    java.lang.String successMessage) {
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