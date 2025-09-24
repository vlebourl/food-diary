package com.fooddiary.services;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \n2\u00020\u0001:\u0001\nB\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\u0007\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\t\u00a8\u0006\u000b"}, d2 = {"Lcom/fooddiary/services/CorrelationWorker;", "Landroidx/work/CoroutineWorker;", "context", "Landroid/content/Context;", "workerParams", "Landroidx/work/WorkerParameters;", "(Landroid/content/Context;Landroidx/work/WorkerParameters;)V", "doWork", "Landroidx/work/ListenableWorker$Result;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "app_debug"})
public final class CorrelationWorker extends androidx.work.CoroutineWorker {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String WORK_NAME = "correlation_analysis";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "CorrelationWorker";
    @org.jetbrains.annotations.NotNull()
    public static final com.fooddiary.services.CorrelationWorker.Companion Companion = null;
    
    public CorrelationWorker(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    androidx.work.WorkerParameters workerParams) {
        super(null, null);
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object doWork(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super androidx.work.ListenableWorker.Result> $completion) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tJ\u0018\u0010\n\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\u000b\u001a\u00020\fJ\"\u0010\r\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\u0010\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tJ8\u0010\u0011\u001a,\u0012(\u0012&\u0012\f\u0012\n \u0015*\u0004\u0018\u00010\u00140\u0014 \u0015*\u0012\u0012\f\u0012\n \u0015*\u0004\u0018\u00010\u00140\u0014\u0018\u00010\u00160\u00130\u00122\u0006\u0010\b\u001a\u00020\tR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lcom/fooddiary/services/CorrelationWorker$Companion;", "", "()V", "TAG", "", "WORK_NAME", "cancelAllWork", "", "context", "Landroid/content/Context;", "enqueueForegroundAnalysis", "analysisDays", "", "enqueueOneTime", "expedited", "", "enqueuePeriodicAnalysis", "getWorkInfo", "Landroidx/lifecycle/LiveData;", "", "Landroidx/work/WorkInfo;", "kotlin.jvm.PlatformType", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        public final void enqueueOneTime(@org.jetbrains.annotations.NotNull()
        android.content.Context context, int analysisDays, boolean expedited) {
        }
        
        public final void enqueuePeriodicAnalysis(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
        }
        
        public final void enqueueForegroundAnalysis(@org.jetbrains.annotations.NotNull()
        android.content.Context context, int analysisDays) {
        }
        
        public final void cancelAllWork(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final androidx.lifecycle.LiveData<java.util.List<androidx.work.WorkInfo>> getWorkInfo(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return null;
        }
    }
}