package com.fooddiary.presentation.ui.elimination;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0004\u0003\u0004\u0005\u0006B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0004\u0007\b\t\n\u00a8\u0006\u000b"}, d2 = {"Lcom/fooddiary/presentation/ui/elimination/EliminationProtocolEvent;", "", "()V", "NavigateToSymptomEntry", "ProtocolCompleted", "ShowPhaseCompletionDialog", "ShowReintroductionGuidance", "Lcom/fooddiary/presentation/ui/elimination/EliminationProtocolEvent$NavigateToSymptomEntry;", "Lcom/fooddiary/presentation/ui/elimination/EliminationProtocolEvent$ProtocolCompleted;", "Lcom/fooddiary/presentation/ui/elimination/EliminationProtocolEvent$ShowPhaseCompletionDialog;", "Lcom/fooddiary/presentation/ui/elimination/EliminationProtocolEvent$ShowReintroductionGuidance;", "app_debug"})
public abstract class EliminationProtocolEvent {
    
    private EliminationProtocolEvent() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/fooddiary/presentation/ui/elimination/EliminationProtocolEvent$NavigateToSymptomEntry;", "Lcom/fooddiary/presentation/ui/elimination/EliminationProtocolEvent;", "()V", "app_debug"})
    public static final class NavigateToSymptomEntry extends com.fooddiary.presentation.ui.elimination.EliminationProtocolEvent {
        @org.jetbrains.annotations.NotNull()
        public static final com.fooddiary.presentation.ui.elimination.EliminationProtocolEvent.NavigateToSymptomEntry INSTANCE = null;
        
        private NavigateToSymptomEntry() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0011"}, d2 = {"Lcom/fooddiary/presentation/ui/elimination/EliminationProtocolEvent$ProtocolCompleted;", "Lcom/fooddiary/presentation/ui/elimination/EliminationProtocolEvent;", "summary", "Lcom/fooddiary/presentation/ui/elimination/ProtocolSummary;", "(Lcom/fooddiary/presentation/ui/elimination/ProtocolSummary;)V", "getSummary", "()Lcom/fooddiary/presentation/ui/elimination/ProtocolSummary;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "app_debug"})
    public static final class ProtocolCompleted extends com.fooddiary.presentation.ui.elimination.EliminationProtocolEvent {
        @org.jetbrains.annotations.NotNull()
        private final com.fooddiary.presentation.ui.elimination.ProtocolSummary summary = null;
        
        public ProtocolCompleted(@org.jetbrains.annotations.NotNull()
        com.fooddiary.presentation.ui.elimination.ProtocolSummary summary) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.fooddiary.presentation.ui.elimination.ProtocolSummary getSummary() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.fooddiary.presentation.ui.elimination.ProtocolSummary component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.fooddiary.presentation.ui.elimination.EliminationProtocolEvent.ProtocolCompleted copy(@org.jetbrains.annotations.NotNull()
        com.fooddiary.presentation.ui.elimination.ProtocolSummary summary) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0011"}, d2 = {"Lcom/fooddiary/presentation/ui/elimination/EliminationProtocolEvent$ShowPhaseCompletionDialog;", "Lcom/fooddiary/presentation/ui/elimination/EliminationProtocolEvent;", "phase", "Lcom/fooddiary/presentation/ui/elimination/EliminationPhase;", "(Lcom/fooddiary/presentation/ui/elimination/EliminationPhase;)V", "getPhase", "()Lcom/fooddiary/presentation/ui/elimination/EliminationPhase;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "app_debug"})
    public static final class ShowPhaseCompletionDialog extends com.fooddiary.presentation.ui.elimination.EliminationProtocolEvent {
        @org.jetbrains.annotations.NotNull()
        private final com.fooddiary.presentation.ui.elimination.EliminationPhase phase = null;
        
        public ShowPhaseCompletionDialog(@org.jetbrains.annotations.NotNull()
        com.fooddiary.presentation.ui.elimination.EliminationPhase phase) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.fooddiary.presentation.ui.elimination.EliminationPhase getPhase() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.fooddiary.presentation.ui.elimination.EliminationPhase component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.fooddiary.presentation.ui.elimination.EliminationProtocolEvent.ShowPhaseCompletionDialog copy(@org.jetbrains.annotations.NotNull()
        com.fooddiary.presentation.ui.elimination.EliminationPhase phase) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\n\u001a\u00020\u0003H\u00c6\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u00d6\u0003J\t\u0010\u0010\u001a\u00020\u0011H\u00d6\u0001J\t\u0010\u0012\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007\u00a8\u0006\u0013"}, d2 = {"Lcom/fooddiary/presentation/ui/elimination/EliminationProtocolEvent$ShowReintroductionGuidance;", "Lcom/fooddiary/presentation/ui/elimination/EliminationProtocolEvent;", "foodName", "", "guidelines", "(Ljava/lang/String;Ljava/lang/String;)V", "getFoodName", "()Ljava/lang/String;", "getGuidelines", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_debug"})
    public static final class ShowReintroductionGuidance extends com.fooddiary.presentation.ui.elimination.EliminationProtocolEvent {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String foodName = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String guidelines = null;
        
        public ShowReintroductionGuidance(@org.jetbrains.annotations.NotNull()
        java.lang.String foodName, @org.jetbrains.annotations.NotNull()
        java.lang.String guidelines) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getFoodName() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getGuidelines() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.fooddiary.presentation.ui.elimination.EliminationProtocolEvent.ShowReintroductionGuidance copy(@org.jetbrains.annotations.NotNull()
        java.lang.String foodName, @org.jetbrains.annotations.NotNull()
        java.lang.String guidelines) {
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
}