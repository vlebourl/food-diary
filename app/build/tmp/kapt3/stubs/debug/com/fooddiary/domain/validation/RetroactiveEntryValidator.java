package com.fooddiary.domain.validation;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u0007\u0018\u0000 \u00162\u00020\u0001:\u0004\u0016\u0017\u0018\u0019B\u0007\b\u0007\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u001e\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\n2\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\u0010\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fJ\u001a\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\r0\n2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000b0\nJ\u000e\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0007\u001a\u00020\b\u00a8\u0006\u001a"}, d2 = {"Lcom/fooddiary/domain/validation/RetroactiveEntryValidator;", "", "()V", "calculateAccuracyScore", "", "daysBack", "", "entryTimestamp", "Ljava/time/Instant;", "generateWarnings", "", "Lcom/fooddiary/domain/validation/RetroactiveEntryValidator$ValidationWarning;", "getRecommendedActions", "", "result", "Lcom/fooddiary/domain/validation/RetroactiveEntryValidator$ValidationResult;", "getValidationMessage", "getWarningMessages", "warnings", "shouldRequireAdditionalVerification", "", "validateRetroactiveEntry", "Companion", "ValidationLevel", "ValidationResult", "ValidationWarning", "app_debug"})
public final class RetroactiveEntryValidator {
    public static final int MAX_RETROACTIVE_DAYS = 30;
    public static final int ACCURACY_WARNING_DAYS = 7;
    public static final int CONFIDENCE_WARNING_DAYS = 3;
    @org.jetbrains.annotations.NotNull()
    public static final com.fooddiary.domain.validation.RetroactiveEntryValidator.Companion Companion = null;
    
    @javax.inject.Inject()
    public RetroactiveEntryValidator() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.domain.validation.RetroactiveEntryValidator.ValidationResult validateRetroactiveEntry(@org.jetbrains.annotations.NotNull()
    java.time.Instant entryTimestamp) {
        return null;
    }
    
    private final float calculateAccuracyScore(long daysBack, java.time.Instant entryTimestamp) {
        return 0.0F;
    }
    
    private final java.util.List<com.fooddiary.domain.validation.RetroactiveEntryValidator.ValidationWarning> generateWarnings(long daysBack, java.time.Instant entryTimestamp) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getValidationMessage(@org.jetbrains.annotations.NotNull()
    com.fooddiary.domain.validation.RetroactiveEntryValidator.ValidationResult result) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getWarningMessages(@org.jetbrains.annotations.NotNull()
    java.util.List<? extends com.fooddiary.domain.validation.RetroactiveEntryValidator.ValidationWarning> warnings) {
        return null;
    }
    
    public final boolean shouldRequireAdditionalVerification(@org.jetbrains.annotations.NotNull()
    com.fooddiary.domain.validation.RetroactiveEntryValidator.ValidationResult result) {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getRecommendedActions(@org.jetbrains.annotations.NotNull()
    com.fooddiary.domain.validation.RetroactiveEntryValidator.ValidationResult result) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"Lcom/fooddiary/domain/validation/RetroactiveEntryValidator$Companion;", "", "()V", "ACCURACY_WARNING_DAYS", "", "CONFIDENCE_WARNING_DAYS", "MAX_RETROACTIVE_DAYS", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/fooddiary/domain/validation/RetroactiveEntryValidator$ValidationLevel;", "", "(Ljava/lang/String;I)V", "HIGH_CONFIDENCE", "MEDIUM_CONFIDENCE", "LOW_CONFIDENCE", "REJECTED", "app_debug"})
    public static enum ValidationLevel {
        /*public static final*/ HIGH_CONFIDENCE /* = new HIGH_CONFIDENCE() */,
        /*public static final*/ MEDIUM_CONFIDENCE /* = new MEDIUM_CONFIDENCE() */,
        /*public static final*/ LOW_CONFIDENCE /* = new LOW_CONFIDENCE() */,
        /*public static final*/ REJECTED /* = new REJECTED() */;
        
        ValidationLevel() {
        }
        
        @org.jetbrains.annotations.NotNull()
        public static kotlin.enums.EnumEntries<com.fooddiary.domain.validation.RetroactiveEntryValidator.ValidationLevel> getEntries() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B3\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u00a2\u0006\u0002\u0010\rJ\t\u0010\u0017\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0018\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0019\u001a\u00020\u0007H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\tH\u00c6\u0003J\u000f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u00c6\u0003JA\u0010\u001c\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u00c6\u0001J\u0013\u0010\u001d\u001a\u00020\u00032\b\u0010\u001e\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001f\u001a\u00020 H\u00d6\u0001J\t\u0010!\u001a\u00020\"H\u00d6\u0001R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0012R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016\u00a8\u0006#"}, d2 = {"Lcom/fooddiary/domain/validation/RetroactiveEntryValidator$ValidationResult;", "", "isValid", "", "validationLevel", "Lcom/fooddiary/domain/validation/RetroactiveEntryValidator$ValidationLevel;", "daysBack", "", "accuracyScore", "", "warnings", "", "Lcom/fooddiary/domain/validation/RetroactiveEntryValidator$ValidationWarning;", "(ZLcom/fooddiary/domain/validation/RetroactiveEntryValidator$ValidationLevel;JFLjava/util/List;)V", "getAccuracyScore", "()F", "getDaysBack", "()J", "()Z", "getValidationLevel", "()Lcom/fooddiary/domain/validation/RetroactiveEntryValidator$ValidationLevel;", "getWarnings", "()Ljava/util/List;", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "other", "hashCode", "", "toString", "", "app_debug"})
    public static final class ValidationResult {
        private final boolean isValid = false;
        @org.jetbrains.annotations.NotNull()
        private final com.fooddiary.domain.validation.RetroactiveEntryValidator.ValidationLevel validationLevel = null;
        private final long daysBack = 0L;
        private final float accuracyScore = 0.0F;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.fooddiary.domain.validation.RetroactiveEntryValidator.ValidationWarning> warnings = null;
        
        public ValidationResult(boolean isValid, @org.jetbrains.annotations.NotNull()
        com.fooddiary.domain.validation.RetroactiveEntryValidator.ValidationLevel validationLevel, long daysBack, float accuracyScore, @org.jetbrains.annotations.NotNull()
        java.util.List<? extends com.fooddiary.domain.validation.RetroactiveEntryValidator.ValidationWarning> warnings) {
            super();
        }
        
        public final boolean isValid() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.fooddiary.domain.validation.RetroactiveEntryValidator.ValidationLevel getValidationLevel() {
            return null;
        }
        
        public final long getDaysBack() {
            return 0L;
        }
        
        public final float getAccuracyScore() {
            return 0.0F;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.fooddiary.domain.validation.RetroactiveEntryValidator.ValidationWarning> getWarnings() {
            return null;
        }
        
        public final boolean component1() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.fooddiary.domain.validation.RetroactiveEntryValidator.ValidationLevel component2() {
            return null;
        }
        
        public final long component3() {
            return 0L;
        }
        
        public final float component4() {
            return 0.0F;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.fooddiary.domain.validation.RetroactiveEntryValidator.ValidationWarning> component5() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.fooddiary.domain.validation.RetroactiveEntryValidator.ValidationResult copy(boolean isValid, @org.jetbrains.annotations.NotNull()
        com.fooddiary.domain.validation.RetroactiveEntryValidator.ValidationLevel validationLevel, long daysBack, float accuracyScore, @org.jetbrains.annotations.NotNull()
        java.util.List<? extends com.fooddiary.domain.validation.RetroactiveEntryValidator.ValidationWarning> warnings) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007\u00a8\u0006\b"}, d2 = {"Lcom/fooddiary/domain/validation/RetroactiveEntryValidator$ValidationWarning;", "", "(Ljava/lang/String;I)V", "ACCURACY_DEGRADATION", "MEMORY_RELIABILITY", "TOO_OLD", "WEEKEND_EFFECT", "HOLIDAY_EFFECT", "app_debug"})
    public static enum ValidationWarning {
        /*public static final*/ ACCURACY_DEGRADATION /* = new ACCURACY_DEGRADATION() */,
        /*public static final*/ MEMORY_RELIABILITY /* = new MEMORY_RELIABILITY() */,
        /*public static final*/ TOO_OLD /* = new TOO_OLD() */,
        /*public static final*/ WEEKEND_EFFECT /* = new WEEKEND_EFFECT() */,
        /*public static final*/ HOLIDAY_EFFECT /* = new HOLIDAY_EFFECT() */;
        
        ValidationWarning() {
        }
        
        @org.jetbrains.annotations.NotNull()
        public static kotlin.enums.EnumEntries<com.fooddiary.domain.validation.RetroactiveEntryValidator.ValidationWarning> getEntries() {
            return null;
        }
    }
}