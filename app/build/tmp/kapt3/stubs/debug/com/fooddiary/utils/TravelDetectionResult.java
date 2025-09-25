package com.fooddiary.utils;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0003\u0003\u0004\u0005B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0003\u0006\u0007\b\u00a8\u0006\t"}, d2 = {"Lcom/fooddiary/utils/TravelDetectionResult;", "", "()V", "InsufficientData", "NoTravelDetected", "TravelDetected", "Lcom/fooddiary/utils/TravelDetectionResult$InsufficientData;", "Lcom/fooddiary/utils/TravelDetectionResult$NoTravelDetected;", "Lcom/fooddiary/utils/TravelDetectionResult$TravelDetected;", "app_debug"})
public abstract class TravelDetectionResult {
    
    private TravelDetectionResult() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/fooddiary/utils/TravelDetectionResult$InsufficientData;", "Lcom/fooddiary/utils/TravelDetectionResult;", "()V", "app_debug"})
    public static final class InsufficientData extends com.fooddiary.utils.TravelDetectionResult {
        @org.jetbrains.annotations.NotNull()
        public static final com.fooddiary.utils.TravelDetectionResult.InsufficientData INSTANCE = null;
        
        private InsufficientData() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/fooddiary/utils/TravelDetectionResult$NoTravelDetected;", "Lcom/fooddiary/utils/TravelDetectionResult;", "()V", "app_debug"})
    public static final class NoTravelDetected extends com.fooddiary.utils.TravelDetectionResult {
        @org.jetbrains.annotations.NotNull()
        public static final com.fooddiary.utils.TravelDetectionResult.NoTravelDetected INSTANCE = null;
        
        private NoTravelDetected() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001b\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007J\u000f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\t\u0010\r\u001a\u00020\u0006H\u00c6\u0003J#\u0010\u000e\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0006H\u00c6\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u00d6\u0003J\t\u0010\u0013\u001a\u00020\u0014H\u00d6\u0001J\t\u0010\u0015\u001a\u00020\u0006H\u00d6\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0016"}, d2 = {"Lcom/fooddiary/utils/TravelDetectionResult$TravelDetected;", "Lcom/fooddiary/utils/TravelDetectionResult;", "changes", "", "Lcom/fooddiary/utils/TimezoneChange;", "recommendation", "", "(Ljava/util/List;Ljava/lang/String;)V", "getChanges", "()Ljava/util/List;", "getRecommendation", "()Ljava/lang/String;", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_debug"})
    public static final class TravelDetected extends com.fooddiary.utils.TravelDetectionResult {
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.fooddiary.utils.TimezoneChange> changes = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String recommendation = null;
        
        public TravelDetected(@org.jetbrains.annotations.NotNull()
        java.util.List<com.fooddiary.utils.TimezoneChange> changes, @org.jetbrains.annotations.NotNull()
        java.lang.String recommendation) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.fooddiary.utils.TimezoneChange> getChanges() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getRecommendation() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.fooddiary.utils.TimezoneChange> component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.fooddiary.utils.TravelDetectionResult.TravelDetected copy(@org.jetbrains.annotations.NotNull()
        java.util.List<com.fooddiary.utils.TimezoneChange> changes, @org.jetbrains.annotations.NotNull()
        java.lang.String recommendation) {
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