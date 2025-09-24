package com.fooddiary.data.integrity;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000 \u001e2\u00020\u0001:\u0001\u001eB\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u001d\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\u0005H\u0002\u00a2\u0006\u0002\u0010\fJ\u001c\u0010\r\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010H\u0086@\u00a2\u0006\u0002\u0010\u0012J\u001c\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\u0014\u001a\u00020\u0015H\u0082@\u00a2\u0006\u0002\u0010\u0016J\u001c\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\u0014\u001a\u00020\u0015H\u0082@\u00a2\u0006\u0002\u0010\u0016J\u001c\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\u0014\u001a\u00020\u0015H\u0086@\u00a2\u0006\u0002\u0010\u0016J\u001c\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\u0014\u001a\u00020\u0015H\u0082@\u00a2\u0006\u0002\u0010\u0016J\u001c\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00110\u00102\u0006\u0010\u0014\u001a\u00020\u0015H\u0086@\u00a2\u0006\u0002\u0010\u0016J\u000e\u0010\u001b\u001a\u00020\u001cH\u0086@\u00a2\u0006\u0002\u0010\u001dR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0007\u00a8\u0006\u001f"}, d2 = {"Lcom/fooddiary/data/integrity/DataIntegrityChecker;", "", "foodEntryDao", "Lcom/fooddiary/data/database/dao/FoodEntryDao;", "symptomDao", "error/NonExistentClass", "(Lcom/fooddiary/data/database/dao/FoodEntryDao;Lerror/NonExistentClass;)V", "Lerror/NonExistentClass;", "arePotentialDuplicates", "", "entry1", "entry2", "(Lerror/NonExistentClass;Lerror/NonExistentClass;)Z", "attemptAutoFix", "Lcom/fooddiary/data/integrity/AutoFixResult;", "issues", "", "Lcom/fooddiary/data/integrity/IntegrityIssue;", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "checkBusinessLogicConstraints", "statistics", "Lcom/fooddiary/data/integrity/IntegrityStatistics;", "(Lcom/fooddiary/data/integrity/IntegrityStatistics;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "checkDataConsistency", "checkFoodEntryIntegrity", "checkReferentialIntegrity", "checkSymptomEventIntegrity", "performFullIntegrityCheck", "Lcom/fooddiary/data/integrity/IntegrityReport;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "app_debug"})
public final class DataIntegrityChecker {
    @org.jetbrains.annotations.NotNull()
    private final com.fooddiary.data.database.dao.FoodEntryDao foodEntryDao = null;
    @org.jetbrains.annotations.NotNull()
    private final error.NonExistentClass symptomDao = null;
    private static final int MAX_FUTURE_DAYS = 7;
    private static final int MAX_PAST_YEARS = 5;
    private static final int MIN_VALID_SEVERITY = 1;
    private static final int MAX_VALID_SEVERITY = 10;
    private static final double MAX_REASONABLE_PORTION = 5000.0;
    private static final double MIN_REASONABLE_PORTION = 0.1;
    @org.jetbrains.annotations.NotNull()
    public static final com.fooddiary.data.integrity.DataIntegrityChecker.Companion Companion = null;
    
    @javax.inject.Inject()
    public DataIntegrityChecker(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.database.dao.FoodEntryDao foodEntryDao, @org.jetbrains.annotations.NotNull()
    error.NonExistentClass symptomDao) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object performFullIntegrityCheck(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.fooddiary.data.integrity.IntegrityReport> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object checkFoodEntryIntegrity(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.integrity.IntegrityStatistics statistics, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<? extends com.fooddiary.data.integrity.IntegrityIssue>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object checkSymptomEventIntegrity(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.integrity.IntegrityStatistics statistics, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<? extends com.fooddiary.data.integrity.IntegrityIssue>> $completion) {
        return null;
    }
    
    private final java.lang.Object checkReferentialIntegrity(com.fooddiary.data.integrity.IntegrityStatistics statistics, kotlin.coroutines.Continuation<? super java.util.List<? extends com.fooddiary.data.integrity.IntegrityIssue>> $completion) {
        return null;
    }
    
    private final java.lang.Object checkBusinessLogicConstraints(com.fooddiary.data.integrity.IntegrityStatistics statistics, kotlin.coroutines.Continuation<? super java.util.List<? extends com.fooddiary.data.integrity.IntegrityIssue>> $completion) {
        return null;
    }
    
    private final java.lang.Object checkDataConsistency(com.fooddiary.data.integrity.IntegrityStatistics statistics, kotlin.coroutines.Continuation<? super java.util.List<? extends com.fooddiary.data.integrity.IntegrityIssue>> $completion) {
        return null;
    }
    
    private final boolean arePotentialDuplicates(error.NonExistentClass entry1, error.NonExistentClass entry2) {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object attemptAutoFix(@org.jetbrains.annotations.NotNull()
    java.util.List<? extends com.fooddiary.data.integrity.IntegrityIssue> issues, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.fooddiary.data.integrity.AutoFixResult> $completion) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0007X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/fooddiary/data/integrity/DataIntegrityChecker$Companion;", "", "()V", "MAX_FUTURE_DAYS", "", "MAX_PAST_YEARS", "MAX_REASONABLE_PORTION", "", "MAX_VALID_SEVERITY", "MIN_REASONABLE_PORTION", "MIN_VALID_SEVERITY", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}