package com.fooddiary.data.integrity;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\b\u0013\u0014\u0015\u0016\u0017\u0018\u0019\u001aB\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002R\u0012\u0010\u0003\u001a\u00020\u0004X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\bX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0012\u0010\u000b\u001a\u00020\bX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\nR\u0012\u0010\r\u001a\u00020\u000eX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\u0004\u0018\u00010\bX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0012\u0010\n\u0082\u0001\b\u001b\u001c\u001d\u001e\u001f !\"\u00a8\u0006#"}, d2 = {"Lcom/fooddiary/data/integrity/IntegrityIssue;", "", "()V", "autoFixable", "", "getAutoFixable", "()Z", "entityId", "", "getEntityId", "()Ljava/lang/String;", "entityType", "getEntityType", "severity", "Lcom/fooddiary/data/integrity/IssueSeverity;", "getSeverity", "()Lcom/fooddiary/data/integrity/IssueSeverity;", "suggestedFix", "getSuggestedFix", "BrokenReference", "CorruptedData", "EmptyCollection", "InvalidTimestamp", "InvalidValue", "MissingRequiredField", "PotentialDuplicate", "TemporalInconsistency", "Lcom/fooddiary/data/integrity/IntegrityIssue$BrokenReference;", "Lcom/fooddiary/data/integrity/IntegrityIssue$CorruptedData;", "Lcom/fooddiary/data/integrity/IntegrityIssue$EmptyCollection;", "Lcom/fooddiary/data/integrity/IntegrityIssue$InvalidTimestamp;", "Lcom/fooddiary/data/integrity/IntegrityIssue$InvalidValue;", "Lcom/fooddiary/data/integrity/IntegrityIssue$MissingRequiredField;", "Lcom/fooddiary/data/integrity/IntegrityIssue$PotentialDuplicate;", "Lcom/fooddiary/data/integrity/IntegrityIssue$TemporalInconsistency;", "app_debug"})
public abstract class IntegrityIssue {
    
    private IntegrityIssue() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract java.lang.String getEntityType();
    
    @org.jetbrains.annotations.NotNull()
    public abstract java.lang.String getEntityId();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.fooddiary.data.integrity.IssueSeverity getSeverity();
    
    public abstract boolean getAutoFixable();
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.String getSuggestedFix();
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0018\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BK\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\rJ\t\u0010\u0019\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\tH\u00c6\u0003J\t\u0010\u001f\u001a\u00020\u000bH\u00c6\u0003J\u000b\u0010 \u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J[\u0010!\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001J\u0013\u0010\"\u001a\u00020\u000b2\b\u0010#\u001a\u0004\u0018\u00010$H\u00d6\u0003J\t\u0010%\u001a\u00020&H\u00d6\u0001J\t\u0010\'\u001a\u00020\u0003H\u00d6\u0001R\u0014\u0010\n\u001a\u00020\u000bX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0011R\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0011R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0011R\u0014\u0010\b\u001a\u00020\tX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0016\u0010\f\u001a\u0004\u0018\u00010\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0011\u00a8\u0006("}, d2 = {"Lcom/fooddiary/data/integrity/IntegrityIssue$BrokenReference;", "Lcom/fooddiary/data/integrity/IntegrityIssue;", "entityType", "", "entityId", "referencedEntityType", "referencedEntityId", "fieldName", "severity", "Lcom/fooddiary/data/integrity/IssueSeverity;", "autoFixable", "", "suggestedFix", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/fooddiary/data/integrity/IssueSeverity;ZLjava/lang/String;)V", "getAutoFixable", "()Z", "getEntityId", "()Ljava/lang/String;", "getEntityType", "getFieldName", "getReferencedEntityId", "getReferencedEntityType", "getSeverity", "()Lcom/fooddiary/data/integrity/IssueSeverity;", "getSuggestedFix", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "equals", "other", "", "hashCode", "", "toString", "app_debug"})
    public static final class BrokenReference extends com.fooddiary.data.integrity.IntegrityIssue {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String entityType = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String entityId = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String referencedEntityType = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String referencedEntityId = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String fieldName = null;
        @org.jetbrains.annotations.NotNull()
        private final com.fooddiary.data.integrity.IssueSeverity severity = null;
        private final boolean autoFixable = false;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String suggestedFix = null;
        
        public BrokenReference(@org.jetbrains.annotations.NotNull()
        java.lang.String entityType, @org.jetbrains.annotations.NotNull()
        java.lang.String entityId, @org.jetbrains.annotations.NotNull()
        java.lang.String referencedEntityType, @org.jetbrains.annotations.NotNull()
        java.lang.String referencedEntityId, @org.jetbrains.annotations.NotNull()
        java.lang.String fieldName, @org.jetbrains.annotations.NotNull()
        com.fooddiary.data.integrity.IssueSeverity severity, boolean autoFixable, @org.jetbrains.annotations.Nullable()
        java.lang.String suggestedFix) {
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String getEntityType() {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String getEntityId() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getReferencedEntityType() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getReferencedEntityId() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getFieldName() {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public com.fooddiary.data.integrity.IssueSeverity getSeverity() {
            return null;
        }
        
        @java.lang.Override()
        public boolean getAutoFixable() {
            return false;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.Nullable()
        public java.lang.String getSuggestedFix() {
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
        public final java.lang.String component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component4() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component5() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.fooddiary.data.integrity.IssueSeverity component6() {
            return null;
        }
        
        public final boolean component7() {
            return false;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component8() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.fooddiary.data.integrity.IntegrityIssue.BrokenReference copy(@org.jetbrains.annotations.NotNull()
        java.lang.String entityType, @org.jetbrains.annotations.NotNull()
        java.lang.String entityId, @org.jetbrains.annotations.NotNull()
        java.lang.String referencedEntityType, @org.jetbrains.annotations.NotNull()
        java.lang.String referencedEntityId, @org.jetbrains.annotations.NotNull()
        java.lang.String fieldName, @org.jetbrains.annotations.NotNull()
        com.fooddiary.data.integrity.IssueSeverity severity, boolean autoFixable, @org.jetbrains.annotations.Nullable()
        java.lang.String suggestedFix) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0014\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B=\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u000bJ\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\tH\u00c6\u0003J\u000b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003JG\u0010\u001b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001J\u0013\u0010\u001c\u001a\u00020\t2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u00d6\u0003J\t\u0010\u001f\u001a\u00020 H\u00d6\u0001J\t\u0010!\u001a\u00020\u0003H\u00d6\u0001R\u0014\u0010\b\u001a\u00020\tX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000fR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0016\u0010\n\u001a\u0004\u0018\u00010\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000f\u00a8\u0006\""}, d2 = {"Lcom/fooddiary/data/integrity/IntegrityIssue$CorruptedData;", "Lcom/fooddiary/data/integrity/IntegrityIssue;", "entityType", "", "entityId", "corruption", "severity", "Lcom/fooddiary/data/integrity/IssueSeverity;", "autoFixable", "", "suggestedFix", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/fooddiary/data/integrity/IssueSeverity;ZLjava/lang/String;)V", "getAutoFixable", "()Z", "getCorruption", "()Ljava/lang/String;", "getEntityId", "getEntityType", "getSeverity", "()Lcom/fooddiary/data/integrity/IssueSeverity;", "getSuggestedFix", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "other", "", "hashCode", "", "toString", "app_debug"})
    public static final class CorruptedData extends com.fooddiary.data.integrity.IntegrityIssue {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String entityType = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String entityId = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String corruption = null;
        @org.jetbrains.annotations.NotNull()
        private final com.fooddiary.data.integrity.IssueSeverity severity = null;
        private final boolean autoFixable = false;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String suggestedFix = null;
        
        public CorruptedData(@org.jetbrains.annotations.NotNull()
        java.lang.String entityType, @org.jetbrains.annotations.NotNull()
        java.lang.String entityId, @org.jetbrains.annotations.NotNull()
        java.lang.String corruption, @org.jetbrains.annotations.NotNull()
        com.fooddiary.data.integrity.IssueSeverity severity, boolean autoFixable, @org.jetbrains.annotations.Nullable()
        java.lang.String suggestedFix) {
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String getEntityType() {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String getEntityId() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getCorruption() {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public com.fooddiary.data.integrity.IssueSeverity getSeverity() {
            return null;
        }
        
        @java.lang.Override()
        public boolean getAutoFixable() {
            return false;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.Nullable()
        public java.lang.String getSuggestedFix() {
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
        public final java.lang.String component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.fooddiary.data.integrity.IssueSeverity component4() {
            return null;
        }
        
        public final boolean component5() {
            return false;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component6() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.fooddiary.data.integrity.IntegrityIssue.CorruptedData copy(@org.jetbrains.annotations.NotNull()
        java.lang.String entityType, @org.jetbrains.annotations.NotNull()
        java.lang.String entityId, @org.jetbrains.annotations.NotNull()
        java.lang.String corruption, @org.jetbrains.annotations.NotNull()
        com.fooddiary.data.integrity.IssueSeverity severity, boolean autoFixable, @org.jetbrains.annotations.Nullable()
        java.lang.String suggestedFix) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0014\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B;\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u000bJ\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\tH\u00c6\u0003J\u000b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003JG\u0010\u001b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001J\u0013\u0010\u001c\u001a\u00020\t2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u00d6\u0003J\t\u0010\u001f\u001a\u00020 H\u00d6\u0001J\t\u0010!\u001a\u00020\u0003H\u00d6\u0001R\u0014\u0010\b\u001a\u00020\tX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000fR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0016\u0010\n\u001a\u0004\u0018\u00010\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000f\u00a8\u0006\""}, d2 = {"Lcom/fooddiary/data/integrity/IntegrityIssue$EmptyCollection;", "Lcom/fooddiary/data/integrity/IntegrityIssue;", "entityType", "", "entityId", "fieldName", "severity", "Lcom/fooddiary/data/integrity/IssueSeverity;", "autoFixable", "", "suggestedFix", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/fooddiary/data/integrity/IssueSeverity;ZLjava/lang/String;)V", "getAutoFixable", "()Z", "getEntityId", "()Ljava/lang/String;", "getEntityType", "getFieldName", "getSeverity", "()Lcom/fooddiary/data/integrity/IssueSeverity;", "getSuggestedFix", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "other", "", "hashCode", "", "toString", "app_debug"})
    public static final class EmptyCollection extends com.fooddiary.data.integrity.IntegrityIssue {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String entityType = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String entityId = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String fieldName = null;
        @org.jetbrains.annotations.NotNull()
        private final com.fooddiary.data.integrity.IssueSeverity severity = null;
        private final boolean autoFixable = false;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String suggestedFix = null;
        
        public EmptyCollection(@org.jetbrains.annotations.NotNull()
        java.lang.String entityType, @org.jetbrains.annotations.NotNull()
        java.lang.String entityId, @org.jetbrains.annotations.NotNull()
        java.lang.String fieldName, @org.jetbrains.annotations.NotNull()
        com.fooddiary.data.integrity.IssueSeverity severity, boolean autoFixable, @org.jetbrains.annotations.Nullable()
        java.lang.String suggestedFix) {
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String getEntityType() {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String getEntityId() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getFieldName() {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public com.fooddiary.data.integrity.IssueSeverity getSeverity() {
            return null;
        }
        
        @java.lang.Override()
        public boolean getAutoFixable() {
            return false;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.Nullable()
        public java.lang.String getSuggestedFix() {
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
        public final java.lang.String component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.fooddiary.data.integrity.IssueSeverity component4() {
            return null;
        }
        
        public final boolean component5() {
            return false;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component6() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.fooddiary.data.integrity.IntegrityIssue.EmptyCollection copy(@org.jetbrains.annotations.NotNull()
        java.lang.String entityType, @org.jetbrains.annotations.NotNull()
        java.lang.String entityId, @org.jetbrains.annotations.NotNull()
        java.lang.String fieldName, @org.jetbrains.annotations.NotNull()
        com.fooddiary.data.integrity.IssueSeverity severity, boolean autoFixable, @org.jetbrains.annotations.Nullable()
        java.lang.String suggestedFix) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0017\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BC\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\rJ\t\u0010\u0019\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\tH\u00c6\u0003J\t\u0010\u001e\u001a\u00020\u000bH\u00c6\u0003J\u000b\u0010\u001f\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003JQ\u0010 \u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001J\u0013\u0010!\u001a\u00020\u000b2\b\u0010\"\u001a\u0004\u0018\u00010#H\u00d6\u0003J\t\u0010$\u001a\u00020%H\u00d6\u0001J\t\u0010&\u001a\u00020\u0003H\u00d6\u0001R\u0014\u0010\n\u001a\u00020\u000bX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0011R\u0014\u0010\b\u001a\u00020\tX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0016\u0010\f\u001a\u0004\u0018\u00010\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0011R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018\u00a8\u0006\'"}, d2 = {"Lcom/fooddiary/data/integrity/IntegrityIssue$InvalidTimestamp;", "Lcom/fooddiary/data/integrity/IntegrityIssue;", "entityType", "", "entityId", "timestamp", "Ljava/time/Instant;", "reason", "severity", "Lcom/fooddiary/data/integrity/IssueSeverity;", "autoFixable", "", "suggestedFix", "(Ljava/lang/String;Ljava/lang/String;Ljava/time/Instant;Ljava/lang/String;Lcom/fooddiary/data/integrity/IssueSeverity;ZLjava/lang/String;)V", "getAutoFixable", "()Z", "getEntityId", "()Ljava/lang/String;", "getEntityType", "getReason", "getSeverity", "()Lcom/fooddiary/data/integrity/IssueSeverity;", "getSuggestedFix", "getTimestamp", "()Ljava/time/Instant;", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "other", "", "hashCode", "", "toString", "app_debug"})
    public static final class InvalidTimestamp extends com.fooddiary.data.integrity.IntegrityIssue {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String entityType = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String entityId = null;
        @org.jetbrains.annotations.NotNull()
        private final java.time.Instant timestamp = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String reason = null;
        @org.jetbrains.annotations.NotNull()
        private final com.fooddiary.data.integrity.IssueSeverity severity = null;
        private final boolean autoFixable = false;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String suggestedFix = null;
        
        public InvalidTimestamp(@org.jetbrains.annotations.NotNull()
        java.lang.String entityType, @org.jetbrains.annotations.NotNull()
        java.lang.String entityId, @org.jetbrains.annotations.NotNull()
        java.time.Instant timestamp, @org.jetbrains.annotations.NotNull()
        java.lang.String reason, @org.jetbrains.annotations.NotNull()
        com.fooddiary.data.integrity.IssueSeverity severity, boolean autoFixable, @org.jetbrains.annotations.Nullable()
        java.lang.String suggestedFix) {
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String getEntityType() {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String getEntityId() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.time.Instant getTimestamp() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getReason() {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public com.fooddiary.data.integrity.IssueSeverity getSeverity() {
            return null;
        }
        
        @java.lang.Override()
        public boolean getAutoFixable() {
            return false;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.Nullable()
        public java.lang.String getSuggestedFix() {
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
        public final java.time.Instant component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component4() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.fooddiary.data.integrity.IssueSeverity component5() {
            return null;
        }
        
        public final boolean component6() {
            return false;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component7() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.fooddiary.data.integrity.IntegrityIssue.InvalidTimestamp copy(@org.jetbrains.annotations.NotNull()
        java.lang.String entityType, @org.jetbrains.annotations.NotNull()
        java.lang.String entityId, @org.jetbrains.annotations.NotNull()
        java.time.Instant timestamp, @org.jetbrains.annotations.NotNull()
        java.lang.String reason, @org.jetbrains.annotations.NotNull()
        com.fooddiary.data.integrity.IssueSeverity severity, boolean autoFixable, @org.jetbrains.annotations.Nullable()
        java.lang.String suggestedFix) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0018\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BK\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\rJ\t\u0010\u0019\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001d\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\tH\u00c6\u0003J\t\u0010\u001f\u001a\u00020\u000bH\u00c6\u0003J\u000b\u0010 \u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J[\u0010!\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001J\u0013\u0010\"\u001a\u00020\u000b2\b\u0010#\u001a\u0004\u0018\u00010$H\u00d6\u0003J\t\u0010%\u001a\u00020&H\u00d6\u0001J\t\u0010\'\u001a\u00020\u0003H\u00d6\u0001R\u0014\u0010\n\u001a\u00020\u000bX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0011R\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0011R\u0014\u0010\b\u001a\u00020\tX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0016\u0010\f\u001a\u0004\u0018\u00010\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0011R\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0011\u00a8\u0006("}, d2 = {"Lcom/fooddiary/data/integrity/IntegrityIssue$InvalidValue;", "Lcom/fooddiary/data/integrity/IntegrityIssue;", "entityType", "", "entityId", "fieldName", "value", "reason", "severity", "Lcom/fooddiary/data/integrity/IssueSeverity;", "autoFixable", "", "suggestedFix", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/fooddiary/data/integrity/IssueSeverity;ZLjava/lang/String;)V", "getAutoFixable", "()Z", "getEntityId", "()Ljava/lang/String;", "getEntityType", "getFieldName", "getReason", "getSeverity", "()Lcom/fooddiary/data/integrity/IssueSeverity;", "getSuggestedFix", "getValue", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "equals", "other", "", "hashCode", "", "toString", "app_debug"})
    public static final class InvalidValue extends com.fooddiary.data.integrity.IntegrityIssue {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String entityType = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String entityId = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String fieldName = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String value = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String reason = null;
        @org.jetbrains.annotations.NotNull()
        private final com.fooddiary.data.integrity.IssueSeverity severity = null;
        private final boolean autoFixable = false;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String suggestedFix = null;
        
        public InvalidValue(@org.jetbrains.annotations.NotNull()
        java.lang.String entityType, @org.jetbrains.annotations.NotNull()
        java.lang.String entityId, @org.jetbrains.annotations.NotNull()
        java.lang.String fieldName, @org.jetbrains.annotations.NotNull()
        java.lang.String value, @org.jetbrains.annotations.NotNull()
        java.lang.String reason, @org.jetbrains.annotations.NotNull()
        com.fooddiary.data.integrity.IssueSeverity severity, boolean autoFixable, @org.jetbrains.annotations.Nullable()
        java.lang.String suggestedFix) {
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String getEntityType() {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String getEntityId() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getFieldName() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getValue() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getReason() {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public com.fooddiary.data.integrity.IssueSeverity getSeverity() {
            return null;
        }
        
        @java.lang.Override()
        public boolean getAutoFixable() {
            return false;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.Nullable()
        public java.lang.String getSuggestedFix() {
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
        public final java.lang.String component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component4() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component5() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.fooddiary.data.integrity.IssueSeverity component6() {
            return null;
        }
        
        public final boolean component7() {
            return false;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component8() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.fooddiary.data.integrity.IntegrityIssue.InvalidValue copy(@org.jetbrains.annotations.NotNull()
        java.lang.String entityType, @org.jetbrains.annotations.NotNull()
        java.lang.String entityId, @org.jetbrains.annotations.NotNull()
        java.lang.String fieldName, @org.jetbrains.annotations.NotNull()
        java.lang.String value, @org.jetbrains.annotations.NotNull()
        java.lang.String reason, @org.jetbrains.annotations.NotNull()
        com.fooddiary.data.integrity.IssueSeverity severity, boolean autoFixable, @org.jetbrains.annotations.Nullable()
        java.lang.String suggestedFix) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0014\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B;\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u000bJ\t\u0010\u0015\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0017\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\tH\u00c6\u0003J\u000b\u0010\u001a\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003JG\u0010\u001b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001J\u0013\u0010\u001c\u001a\u00020\t2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u00d6\u0003J\t\u0010\u001f\u001a\u00020 H\u00d6\u0001J\t\u0010!\u001a\u00020\u0003H\u00d6\u0001R\u0014\u0010\b\u001a\u00020\tX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000fR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0016\u0010\n\u001a\u0004\u0018\u00010\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000f\u00a8\u0006\""}, d2 = {"Lcom/fooddiary/data/integrity/IntegrityIssue$MissingRequiredField;", "Lcom/fooddiary/data/integrity/IntegrityIssue;", "entityType", "", "entityId", "fieldName", "severity", "Lcom/fooddiary/data/integrity/IssueSeverity;", "autoFixable", "", "suggestedFix", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/fooddiary/data/integrity/IssueSeverity;ZLjava/lang/String;)V", "getAutoFixable", "()Z", "getEntityId", "()Ljava/lang/String;", "getEntityType", "getFieldName", "getSeverity", "()Lcom/fooddiary/data/integrity/IssueSeverity;", "getSuggestedFix", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "other", "", "hashCode", "", "toString", "app_debug"})
    public static final class MissingRequiredField extends com.fooddiary.data.integrity.IntegrityIssue {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String entityType = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String entityId = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String fieldName = null;
        @org.jetbrains.annotations.NotNull()
        private final com.fooddiary.data.integrity.IssueSeverity severity = null;
        private final boolean autoFixable = false;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String suggestedFix = null;
        
        public MissingRequiredField(@org.jetbrains.annotations.NotNull()
        java.lang.String entityType, @org.jetbrains.annotations.NotNull()
        java.lang.String entityId, @org.jetbrains.annotations.NotNull()
        java.lang.String fieldName, @org.jetbrains.annotations.NotNull()
        com.fooddiary.data.integrity.IssueSeverity severity, boolean autoFixable, @org.jetbrains.annotations.Nullable()
        java.lang.String suggestedFix) {
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String getEntityType() {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String getEntityId() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getFieldName() {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public com.fooddiary.data.integrity.IssueSeverity getSeverity() {
            return null;
        }
        
        @java.lang.Override()
        public boolean getAutoFixable() {
            return false;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.Nullable()
        public java.lang.String getSuggestedFix() {
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
        public final java.lang.String component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.fooddiary.data.integrity.IssueSeverity component4() {
            return null;
        }
        
        public final boolean component5() {
            return false;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component6() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.fooddiary.data.integrity.IntegrityIssue.MissingRequiredField copy(@org.jetbrains.annotations.NotNull()
        java.lang.String entityType, @org.jetbrains.annotations.NotNull()
        java.lang.String entityId, @org.jetbrains.annotations.NotNull()
        java.lang.String fieldName, @org.jetbrains.annotations.NotNull()
        com.fooddiary.data.integrity.IssueSeverity severity, boolean autoFixable, @org.jetbrains.annotations.Nullable()
        java.lang.String suggestedFix) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u001c\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BK\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\rJ\t\u0010\u001d\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010 \u001a\u00020\u0003H\u00c6\u0003J\t\u0010!\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\"\u001a\u00020\tH\u00c6\u0003J\t\u0010#\u001a\u00020\u000bH\u00c6\u0003J\u000b\u0010$\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J[\u0010%\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001J\u0013\u0010&\u001a\u00020\u000b2\b\u0010\'\u001a\u0004\u0018\u00010(H\u00d6\u0003J\t\u0010)\u001a\u00020*H\u00d6\u0001J\t\u0010+\u001a\u00020\u0003H\u00d6\u0001R\u0014\u0010\n\u001a\u00020\u000bX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0011R\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0011R\u0014\u0010\u0015\u001a\u00020\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0011R\u0014\u0010\u0017\u001a\u00020\u0003X\u0096D\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0011R\u0014\u0010\b\u001a\u00020\tX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0011R\u0016\u0010\f\u001a\u0004\u0018\u00010\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0011\u00a8\u0006,"}, d2 = {"Lcom/fooddiary/data/integrity/IntegrityIssue$PotentialDuplicate;", "Lcom/fooddiary/data/integrity/IntegrityIssue;", "entity1Type", "", "entity1Id", "entity2Type", "entity2Id", "similarityReason", "severity", "Lcom/fooddiary/data/integrity/IssueSeverity;", "autoFixable", "", "suggestedFix", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/fooddiary/data/integrity/IssueSeverity;ZLjava/lang/String;)V", "getAutoFixable", "()Z", "getEntity1Id", "()Ljava/lang/String;", "getEntity1Type", "getEntity2Id", "getEntity2Type", "entityId", "getEntityId", "entityType", "getEntityType", "getSeverity", "()Lcom/fooddiary/data/integrity/IssueSeverity;", "getSimilarityReason", "getSuggestedFix", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "equals", "other", "", "hashCode", "", "toString", "app_debug"})
    public static final class PotentialDuplicate extends com.fooddiary.data.integrity.IntegrityIssue {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String entity1Type = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String entity1Id = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String entity2Type = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String entity2Id = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String similarityReason = null;
        @org.jetbrains.annotations.NotNull()
        private final com.fooddiary.data.integrity.IssueSeverity severity = null;
        private final boolean autoFixable = false;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String suggestedFix = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String entityType = "DuplicateCheck";
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String entityId = null;
        
        public PotentialDuplicate(@org.jetbrains.annotations.NotNull()
        java.lang.String entity1Type, @org.jetbrains.annotations.NotNull()
        java.lang.String entity1Id, @org.jetbrains.annotations.NotNull()
        java.lang.String entity2Type, @org.jetbrains.annotations.NotNull()
        java.lang.String entity2Id, @org.jetbrains.annotations.NotNull()
        java.lang.String similarityReason, @org.jetbrains.annotations.NotNull()
        com.fooddiary.data.integrity.IssueSeverity severity, boolean autoFixable, @org.jetbrains.annotations.Nullable()
        java.lang.String suggestedFix) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getEntity1Type() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getEntity1Id() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getEntity2Type() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getEntity2Id() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getSimilarityReason() {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public com.fooddiary.data.integrity.IssueSeverity getSeverity() {
            return null;
        }
        
        @java.lang.Override()
        public boolean getAutoFixable() {
            return false;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.Nullable()
        public java.lang.String getSuggestedFix() {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String getEntityType() {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String getEntityId() {
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
        public final java.lang.String component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component4() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component5() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.fooddiary.data.integrity.IssueSeverity component6() {
            return null;
        }
        
        public final boolean component7() {
            return false;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component8() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.fooddiary.data.integrity.IntegrityIssue.PotentialDuplicate copy(@org.jetbrains.annotations.NotNull()
        java.lang.String entity1Type, @org.jetbrains.annotations.NotNull()
        java.lang.String entity1Id, @org.jetbrains.annotations.NotNull()
        java.lang.String entity2Type, @org.jetbrains.annotations.NotNull()
        java.lang.String entity2Id, @org.jetbrains.annotations.NotNull()
        java.lang.String similarityReason, @org.jetbrains.annotations.NotNull()
        com.fooddiary.data.integrity.IssueSeverity severity, boolean autoFixable, @org.jetbrains.annotations.Nullable()
        java.lang.String suggestedFix) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u001d\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BK\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\n\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u000eJ\t\u0010\u001f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010 \u001a\u00020\u0003H\u00c6\u0003J\t\u0010!\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\"\u001a\u00020\u0006H\u00c6\u0003J\t\u0010#\u001a\u00020\u0003H\u00c6\u0003J\t\u0010$\u001a\u00020\nH\u00c6\u0003J\t\u0010%\u001a\u00020\fH\u00c6\u0003J\u000b\u0010&\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J[\u0010\'\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00062\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\f2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001J\u0013\u0010(\u001a\u00020\f2\b\u0010)\u001a\u0004\u0018\u00010*H\u00d6\u0003J\t\u0010+\u001a\u00020,H\u00d6\u0001J\t\u0010-\u001a\u00020\u0003H\u00d6\u0001R\u0014\u0010\u000b\u001a\u00020\fX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\u00020\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0014\u001a\u00020\u0003X\u0096D\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0013R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0013R\u0011\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0013R\u0014\u0010\t\u001a\u00020\nX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0016\u0010\r\u001a\u0004\u0018\u00010\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0013R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0013R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0018\u00a8\u0006."}, d2 = {"Lcom/fooddiary/data/integrity/IntegrityIssue$TemporalInconsistency;", "Lcom/fooddiary/data/integrity/IntegrityIssue;", "symptomId", "", "foodEntryId", "symptomTime", "Ljava/time/Instant;", "foodTime", "reason", "severity", "Lcom/fooddiary/data/integrity/IssueSeverity;", "autoFixable", "", "suggestedFix", "(Ljava/lang/String;Ljava/lang/String;Ljava/time/Instant;Ljava/time/Instant;Ljava/lang/String;Lcom/fooddiary/data/integrity/IssueSeverity;ZLjava/lang/String;)V", "getAutoFixable", "()Z", "entityId", "getEntityId", "()Ljava/lang/String;", "entityType", "getEntityType", "getFoodEntryId", "getFoodTime", "()Ljava/time/Instant;", "getReason", "getSeverity", "()Lcom/fooddiary/data/integrity/IssueSeverity;", "getSuggestedFix", "getSymptomId", "getSymptomTime", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "equals", "other", "", "hashCode", "", "toString", "app_debug"})
    public static final class TemporalInconsistency extends com.fooddiary.data.integrity.IntegrityIssue {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String symptomId = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String foodEntryId = null;
        @org.jetbrains.annotations.NotNull()
        private final java.time.Instant symptomTime = null;
        @org.jetbrains.annotations.NotNull()
        private final java.time.Instant foodTime = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String reason = null;
        @org.jetbrains.annotations.NotNull()
        private final com.fooddiary.data.integrity.IssueSeverity severity = null;
        private final boolean autoFixable = false;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.String suggestedFix = null;
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String entityType = "TemporalRelationship";
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String entityId = null;
        
        public TemporalInconsistency(@org.jetbrains.annotations.NotNull()
        java.lang.String symptomId, @org.jetbrains.annotations.NotNull()
        java.lang.String foodEntryId, @org.jetbrains.annotations.NotNull()
        java.time.Instant symptomTime, @org.jetbrains.annotations.NotNull()
        java.time.Instant foodTime, @org.jetbrains.annotations.NotNull()
        java.lang.String reason, @org.jetbrains.annotations.NotNull()
        com.fooddiary.data.integrity.IssueSeverity severity, boolean autoFixable, @org.jetbrains.annotations.Nullable()
        java.lang.String suggestedFix) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getSymptomId() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getFoodEntryId() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.time.Instant getSymptomTime() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.time.Instant getFoodTime() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getReason() {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public com.fooddiary.data.integrity.IssueSeverity getSeverity() {
            return null;
        }
        
        @java.lang.Override()
        public boolean getAutoFixable() {
            return false;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.Nullable()
        public java.lang.String getSuggestedFix() {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String getEntityType() {
            return null;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String getEntityId() {
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
        public final java.time.Instant component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.time.Instant component4() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component5() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.fooddiary.data.integrity.IssueSeverity component6() {
            return null;
        }
        
        public final boolean component7() {
            return false;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.String component8() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.fooddiary.data.integrity.IntegrityIssue.TemporalInconsistency copy(@org.jetbrains.annotations.NotNull()
        java.lang.String symptomId, @org.jetbrains.annotations.NotNull()
        java.lang.String foodEntryId, @org.jetbrains.annotations.NotNull()
        java.time.Instant symptomTime, @org.jetbrains.annotations.NotNull()
        java.time.Instant foodTime, @org.jetbrains.annotations.NotNull()
        java.lang.String reason, @org.jetbrains.annotations.NotNull()
        com.fooddiary.data.integrity.IssueSeverity severity, boolean autoFixable, @org.jetbrains.annotations.Nullable()
        java.lang.String suggestedFix) {
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