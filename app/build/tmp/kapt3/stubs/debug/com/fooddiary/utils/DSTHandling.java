package com.fooddiary.utils;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0003\u0003\u0004\u0005B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0003\u0006\u0007\b\u00a8\u0006\t"}, d2 = {"Lcom/fooddiary/utils/DSTHandling;", "", "()V", "NoTransition", "RecentTransition", "UpcomingTransition", "Lcom/fooddiary/utils/DSTHandling$NoTransition;", "Lcom/fooddiary/utils/DSTHandling$RecentTransition;", "Lcom/fooddiary/utils/DSTHandling$UpcomingTransition;", "app_debug"})
public abstract class DSTHandling {
    
    private DSTHandling() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/fooddiary/utils/DSTHandling$NoTransition;", "Lcom/fooddiary/utils/DSTHandling;", "()V", "app_debug"})
    public static final class NoTransition extends com.fooddiary.utils.DSTHandling {
        @org.jetbrains.annotations.NotNull()
        public static final com.fooddiary.utils.DSTHandling.NoTransition INSTANCE = null;
        
        private NoTransition() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0010\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0011\u001a\u00020\u0007H\u00c6\u0003J\'\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u00c6\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u00d6\u0003J\t\u0010\u0017\u001a\u00020\u0005H\u00d6\u0001J\t\u0010\u0018\u001a\u00020\u0007H\u00d6\u0001R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u0019"}, d2 = {"Lcom/fooddiary/utils/DSTHandling$RecentTransition;", "Lcom/fooddiary/utils/DSTHandling;", "transitionTime", "Ljava/time/Instant;", "offsetChange", "", "note", "", "(Ljava/time/Instant;ILjava/lang/String;)V", "getNote", "()Ljava/lang/String;", "getOffsetChange", "()I", "getTransitionTime", "()Ljava/time/Instant;", "component1", "component2", "component3", "copy", "equals", "", "other", "", "hashCode", "toString", "app_debug"})
    public static final class RecentTransition extends com.fooddiary.utils.DSTHandling {
        @org.jetbrains.annotations.NotNull()
        private final java.time.Instant transitionTime = null;
        private final int offsetChange = 0;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String note = null;
        
        public RecentTransition(@org.jetbrains.annotations.NotNull()
        java.time.Instant transitionTime, int offsetChange, @org.jetbrains.annotations.NotNull()
        java.lang.String note) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.time.Instant getTransitionTime() {
            return null;
        }
        
        public final int getOffsetChange() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getNote() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.time.Instant component1() {
            return null;
        }
        
        public final int component2() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.fooddiary.utils.DSTHandling.RecentTransition copy(@org.jetbrains.annotations.NotNull()
        java.time.Instant transitionTime, int offsetChange, @org.jetbrains.annotations.NotNull()
        java.lang.String note) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0010\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0011\u001a\u00020\u0007H\u00c6\u0003J\'\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u00c6\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u00d6\u0003J\t\u0010\u0017\u001a\u00020\u0005H\u00d6\u0001J\t\u0010\u0018\u001a\u00020\u0007H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u0019"}, d2 = {"Lcom/fooddiary/utils/DSTHandling$UpcomingTransition;", "Lcom/fooddiary/utils/DSTHandling;", "transitionTime", "Ljava/time/Instant;", "offsetChange", "", "warning", "", "(Ljava/time/Instant;ILjava/lang/String;)V", "getOffsetChange", "()I", "getTransitionTime", "()Ljava/time/Instant;", "getWarning", "()Ljava/lang/String;", "component1", "component2", "component3", "copy", "equals", "", "other", "", "hashCode", "toString", "app_debug"})
    public static final class UpcomingTransition extends com.fooddiary.utils.DSTHandling {
        @org.jetbrains.annotations.NotNull()
        private final java.time.Instant transitionTime = null;
        private final int offsetChange = 0;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String warning = null;
        
        public UpcomingTransition(@org.jetbrains.annotations.NotNull()
        java.time.Instant transitionTime, int offsetChange, @org.jetbrains.annotations.NotNull()
        java.lang.String warning) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.time.Instant getTransitionTime() {
            return null;
        }
        
        public final int getOffsetChange() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getWarning() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.time.Instant component1() {
            return null;
        }
        
        public final int component2() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.fooddiary.utils.DSTHandling.UpcomingTransition copy(@org.jetbrains.annotations.NotNull()
        java.time.Instant transitionTime, int offsetChange, @org.jetbrains.annotations.NotNull()
        java.lang.String warning) {
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