package com.fooddiary.data.repository.impl;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u000b\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\tJ\u000e\u0010\n\u001a\u00020\u0006H\u0096@\u00a2\u0006\u0002\u0010\u000bJ\u0016\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0096@\u00a2\u0006\u0002\u0010\u0010J\u0014\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00130\u0012H\u0016J$\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00130\u00122\u0006\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\u000fH\u0016J\u0018\u0010\u0017\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0018\u001a\u00020\u0019H\u0096@\u00a2\u0006\u0002\u0010\u001aJ\u001c\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00130\u00122\u0006\u0010\u001c\u001a\u00020\rH\u0016J\u0016\u0010\u001d\u001a\u00020\u00192\u0006\u0010\u0007\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\tJ\u001e\u0010\u001e\u001a\u00020\u00062\u0006\u0010\u001f\u001a\u00020\u00192\u0006\u0010 \u001a\u00020\u000fH\u0096@\u00a2\u0006\u0002\u0010!J\u0016\u0010\"\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0096@\u00a2\u0006\u0002\u0010\tJ\u001e\u0010#\u001a\u00020\u00062\u0006\u0010\u001f\u001a\u00020\u00192\u0006\u0010$\u001a\u00020%H\u0096@\u00a2\u0006\u0002\u0010&R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\'"}, d2 = {"Lcom/fooddiary/data/repository/impl/MedicalReportRepositoryImpl;", "", "medicalReportDao", "Lcom/fooddiary/data/database/dao/MedicalReportDao;", "(Lcom/fooddiary/data/database/dao/MedicalReportDao;)V", "delete", "", "report", "Lcom/fooddiary/data/database/entities/MedicalReport;", "(Lcom/fooddiary/data/database/entities/MedicalReport;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteAll", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteOlderThan", "", "date", "Ljava/time/LocalDate;", "(Ljava/time/LocalDate;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAll", "Lkotlinx/coroutines/flow/Flow;", "", "getByDateRange", "startDate", "endDate", "getById", "id", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getRecentReports", "limit", "insert", "markAsSent", "reportId", "sentDate", "(JLjava/time/LocalDate;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "update", "updateExportPath", "exportPath", "", "(JLjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class MedicalReportRepositoryImpl {
    @org.jetbrains.annotations.NotNull()
    private final com.fooddiary.data.database.dao.MedicalReportDao medicalReportDao = null;
    
    @javax.inject.Inject()
    public MedicalReportRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.database.dao.MedicalReportDao medicalReportDao) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object insert(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.database.entities.MedicalReport report, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object update(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.database.entities.MedicalReport report, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object delete(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.database.entities.MedicalReport report, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.fooddiary.data.database.entities.MedicalReport> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.fooddiary.data.database.entities.MedicalReport>> getAll() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.fooddiary.data.database.entities.MedicalReport>> getByDateRange(@org.jetbrains.annotations.NotNull()
    java.time.LocalDate startDate, @org.jetbrains.annotations.NotNull()
    java.time.LocalDate endDate) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.fooddiary.data.database.entities.MedicalReport>> getRecentReports(int limit) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object markAsSent(long reportId, @org.jetbrains.annotations.NotNull()
    java.time.LocalDate sentDate, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object updateExportPath(long reportId, @org.jetbrains.annotations.NotNull()
    java.lang.String exportPath, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object deleteOlderThan(@org.jetbrains.annotations.NotNull()
    java.time.LocalDate date, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object deleteAll(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}