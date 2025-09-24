package com.fooddiary.data.repository.impl;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\f\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\tJ\u000e\u0010\n\u001a\u00020\u0006H\u0096@\u00a2\u0006\u0002\u0010\u000bJ\u0014\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u000e0\rH\u0016J\u0014\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u000e0\rH\u0016J$\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u000e0\r2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0012H\u0016J\u0018\u0010\u0014\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0015\u001a\u00020\u0016H\u0096@\u00a2\u0006\u0002\u0010\u0017J\u0018\u0010\u0018\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0019\u001a\u00020\u001aH\u0096@\u00a2\u0006\u0002\u0010\u001bJ\u0016\u0010\u001c\u001a\u00020\u001a2\u0006\u0010\u0007\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\tJ\u001e\u0010\u001d\u001a\u00020\u00062\u0006\u0010\u001e\u001a\u00020\u001a2\u0006\u0010\u001f\u001a\u00020\u0012H\u0096@\u00a2\u0006\u0002\u0010 J\u0016\u0010!\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\tJ(\u0010\"\u001a\u00020\u00062\u0006\u0010\u001e\u001a\u00020\u001a2\u0006\u0010#\u001a\u00020\u00162\b\u0010$\u001a\u0004\u0018\u00010\u0016H\u0096@\u00a2\u0006\u0002\u0010%R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006&"}, d2 = {"Lcom/fooddiary/data/repository/impl/EliminationProtocolRepositoryImpl;", "", "eliminationProtocolDao", "Lcom/fooddiary/data/database/dao/EliminationProtocolDao;", "(Lcom/fooddiary/data/database/dao/EliminationProtocolDao;)V", "delete", "", "protocol", "Lcom/fooddiary/data/database/entities/EliminationProtocol;", "(Lcom/fooddiary/data/database/entities/EliminationProtocol;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteAll", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getActiveProtocols", "Lkotlinx/coroutines/flow/Flow;", "", "getAll", "getByDateRange", "startDate", "Ljava/time/LocalDate;", "endDate", "getByFoodItem", "foodItem", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getById", "id", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insert", "markAsCompleted", "protocolId", "completionDate", "(JLjava/time/LocalDate;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "update", "updateReintroductionResult", "result", "symptoms", "(JLjava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class EliminationProtocolRepositoryImpl {
    @org.jetbrains.annotations.NotNull()
    private final com.fooddiary.data.database.dao.EliminationProtocolDao eliminationProtocolDao = null;
    
    @javax.inject.Inject()
    public EliminationProtocolRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.database.dao.EliminationProtocolDao eliminationProtocolDao) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.database.entities.EliminationProtocol protocol, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object update(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.database.entities.EliminationProtocol protocol, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object delete(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.database.entities.EliminationProtocol protocol, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.fooddiary.data.database.entities.EliminationProtocol> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.fooddiary.data.database.entities.EliminationProtocol>> getAll() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.fooddiary.data.database.entities.EliminationProtocol>> getActiveProtocols() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.fooddiary.data.database.entities.EliminationProtocol>> getByDateRange(@org.jetbrains.annotations.NotNull()
    java.time.LocalDate startDate, @org.jetbrains.annotations.NotNull()
    java.time.LocalDate endDate) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getByFoodItem(@org.jetbrains.annotations.NotNull()
    java.lang.String foodItem, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.fooddiary.data.database.entities.EliminationProtocol> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object markAsCompleted(long protocolId, @org.jetbrains.annotations.NotNull()
    java.time.LocalDate completionDate, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object updateReintroductionResult(long protocolId, @org.jetbrains.annotations.NotNull()
    java.lang.String result, @org.jetbrains.annotations.Nullable()
    java.lang.String symptoms, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object deleteAll(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}