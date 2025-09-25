package com.fooddiary.data.concurrency;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000$\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0006\u001aF\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u001e\b\u0004\u0010\u0006\u001a\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\b\u0012\u0006\u0012\u0004\u0018\u00010\t0\u0007H\u0086H\u00a2\u0006\u0002\u0010\n\u001aF\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u00020\u00032\u0006\u0010\f\u001a\u00020\u00052\u001e\b\u0004\u0010\u0006\u001a\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\b\u0012\u0006\u0012\u0004\u0018\u00010\t0\u0007H\u0086H\u00a2\u0006\u0002\u0010\n\u001aF\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u00052\u001e\b\u0004\u0010\u0006\u001a\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\b\u0012\u0006\u0012\u0004\u0018\u00010\t0\u0007H\u0086H\u00a2\u0006\u0002\u0010\n\u00a8\u0006\u000f"}, d2 = {"withFoodEntryLock", "Lcom/fooddiary/data/concurrency/ConcurrentOperationResult;", "T", "Lcom/fooddiary/data/concurrency/ConcurrentAccessManager;", "foodEntryId", "", "operation", "Lkotlin/Function1;", "Lkotlin/coroutines/Continuation;", "", "(Lcom/fooddiary/data/concurrency/ConcurrentAccessManager;Ljava/lang/String;Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "withSymptomLock", "symptomId", "withUserLock", "userId", "app_release"})
public final class ConcurrentAccessManagerKt {
    
    @org.jetbrains.annotations.Nullable()
    public static final <T extends java.lang.Object>java.lang.Object withFoodEntryLock(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.concurrency.ConcurrentAccessManager $this$withFoodEntryLock, @org.jetbrains.annotations.NotNull()
    java.lang.String foodEntryId, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super kotlin.coroutines.Continuation<? super T>, ? extends java.lang.Object> operation, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.fooddiary.data.concurrency.ConcurrentOperationResult<? extends T>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public static final <T extends java.lang.Object>java.lang.Object withSymptomLock(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.concurrency.ConcurrentAccessManager $this$withSymptomLock, @org.jetbrains.annotations.NotNull()
    java.lang.String symptomId, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super kotlin.coroutines.Continuation<? super T>, ? extends java.lang.Object> operation, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.fooddiary.data.concurrency.ConcurrentOperationResult<? extends T>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public static final <T extends java.lang.Object>java.lang.Object withUserLock(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.concurrency.ConcurrentAccessManager $this$withUserLock, @org.jetbrains.annotations.NotNull()
    java.lang.String userId, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super kotlin.coroutines.Continuation<? super T>, ? extends java.lang.Object> operation, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.fooddiary.data.concurrency.ConcurrentOperationResult<? extends T>> $completion) {
        return null;
    }
    
    private static final <T extends java.lang.Object>java.lang.Object withFoodEntryLock$$forInline(com.fooddiary.data.concurrency.ConcurrentAccessManager $this$withFoodEntryLock, java.lang.String foodEntryId, kotlin.jvm.functions.Function1<? super kotlin.coroutines.Continuation<? super T>, ? extends java.lang.Object> operation, kotlin.coroutines.Continuation<? super com.fooddiary.data.concurrency.ConcurrentOperationResult<? extends T>> $completion) {
        return null;
    }
    
    private static final <T extends java.lang.Object>java.lang.Object withSymptomLock$$forInline(com.fooddiary.data.concurrency.ConcurrentAccessManager $this$withSymptomLock, java.lang.String symptomId, kotlin.jvm.functions.Function1<? super kotlin.coroutines.Continuation<? super T>, ? extends java.lang.Object> operation, kotlin.coroutines.Continuation<? super com.fooddiary.data.concurrency.ConcurrentOperationResult<? extends T>> $completion) {
        return null;
    }
    
    private static final <T extends java.lang.Object>java.lang.Object withUserLock$$forInline(com.fooddiary.data.concurrency.ConcurrentAccessManager $this$withUserLock, java.lang.String userId, kotlin.jvm.functions.Function1<? super kotlin.coroutines.Continuation<? super T>, ? extends java.lang.Object> operation, kotlin.coroutines.Continuation<? super com.fooddiary.data.concurrency.ConcurrentOperationResult<? extends T>> $completion) {
        return null;
    }
}