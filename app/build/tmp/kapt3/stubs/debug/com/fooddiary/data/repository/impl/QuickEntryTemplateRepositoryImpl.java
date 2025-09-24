package com.fooddiary.data.repository.impl;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\tJ\u000e\u0010\n\u001a\u00020\u0006H\u0096@\u00a2\u0006\u0002\u0010\u000bJ\u0014\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u000e0\rH\u0016J\u0014\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u000e0\rH\u0016J\u0018\u0010\u0010\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0011\u001a\u00020\u0012H\u0096@\u00a2\u0006\u0002\u0010\u0013J\u001c\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u000e0\r2\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u001c\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u000e0\r2\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\u0016\u0010\u001a\u001a\u00020\u00062\u0006\u0010\u001b\u001a\u00020\u0012H\u0096@\u00a2\u0006\u0002\u0010\u0013J\u0016\u0010\u001c\u001a\u00020\u00122\u0006\u0010\u0007\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\tJ\u001c\u0010\u001d\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u000e0\r2\u0006\u0010\u001e\u001a\u00020\u0019H\u0016J\u001e\u0010\u001f\u001a\u00020\u00062\u0006\u0010\u001b\u001a\u00020\u00122\u0006\u0010 \u001a\u00020!H\u0096@\u00a2\u0006\u0002\u0010\"J\u0016\u0010#\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\tJ\u001e\u0010$\u001a\u00020\u00062\u0006\u0010\u001b\u001a\u00020\u00122\u0006\u0010%\u001a\u00020\u0012H\u0096@\u00a2\u0006\u0002\u0010&R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\'"}, d2 = {"Lcom/fooddiary/data/repository/impl/QuickEntryTemplateRepositoryImpl;", "", "quickEntryTemplateDao", "Lcom/fooddiary/data/database/dao/QuickEntryTemplateDao;", "(Lcom/fooddiary/data/database/dao/QuickEntryTemplateDao;)V", "delete", "", "template", "Lcom/fooddiary/data/database/entities/QuickEntryTemplate;", "(Lcom/fooddiary/data/database/entities/QuickEntryTemplate;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteAll", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getActiveTemplates", "Lkotlinx/coroutines/flow/Flow;", "", "getAll", "getById", "id", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getFrequentlyUsedTemplates", "limit", "", "getTemplatesByType", "type", "", "incrementUsageCount", "templateId", "insert", "searchByName", "query", "setActive", "isActive", "", "(JZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "update", "updateLastUsedTime", "timestamp", "(JJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class QuickEntryTemplateRepositoryImpl {
    @org.jetbrains.annotations.NotNull()
    private final com.fooddiary.data.database.dao.QuickEntryTemplateDao quickEntryTemplateDao = null;
    
    @javax.inject.Inject()
    public QuickEntryTemplateRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.database.dao.QuickEntryTemplateDao quickEntryTemplateDao) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.database.entities.QuickEntryTemplate template, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object update(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.database.entities.QuickEntryTemplate template, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object delete(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.database.entities.QuickEntryTemplate template, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.fooddiary.data.database.entities.QuickEntryTemplate> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.fooddiary.data.database.entities.QuickEntryTemplate>> getAll() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.fooddiary.data.database.entities.QuickEntryTemplate>> getActiveTemplates() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.fooddiary.data.database.entities.QuickEntryTemplate>> getTemplatesByType(@org.jetbrains.annotations.NotNull()
    java.lang.String type) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.fooddiary.data.database.entities.QuickEntryTemplate>> getFrequentlyUsedTemplates(int limit) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object incrementUsageCount(long templateId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object updateLastUsedTime(long templateId, long timestamp, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object setActive(long templateId, boolean isActive, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.fooddiary.data.database.entities.QuickEntryTemplate>> searchByName(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object deleteAll(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}