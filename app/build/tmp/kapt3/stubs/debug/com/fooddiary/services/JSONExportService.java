package com.fooddiary.services;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u0000 52\u00020\u0001:\u00015B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004JP\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u000b2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\tH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0013\u0010\u0014Jl\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\t0\b2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\u000b2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u000b2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u000b2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\tH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001a\u0010\u001bJB\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\t0\b2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\u000b2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\tH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001d\u0010\u001eJB\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\t0\b2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u000b2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\tH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b \u0010\u001eJ\b\u0010!\u001a\u00020\"H\u0002J\b\u0010#\u001a\u00020$H\u0002J$\u0010%\u001a\b\u0012\u0004\u0012\u00020&0\b2\u0006\u0010\'\u001a\u00020\tH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b(\u0010)J\u0010\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020\"H\u0002J\u0010\u0010-\u001a\u00020+2\u0006\u0010,\u001a\u00020\"H\u0002J\f\u0010.\u001a\u00020\u0017*\u00020/H\u0002J\f\u0010.\u001a\u00020\u0019*\u000200H\u0002J\u0011\u00101\u001a\u000202*\u00020\u000eH\u0002\u00a2\u0006\u0002\u00103J\f\u00101\u001a\u00020/*\u00020\u0017H\u0002J\f\u00101\u001a\u000200*\u00020\u0019H\u0002J\f\u00101\u001a\u000204*\u00020\fH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u00066"}, d2 = {"Lcom/fooddiary/services/JSONExportService;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "json", "Lkotlinx/serialization/json/Json;", "exportAnalyticsOnly", "Lkotlin/Result;", "Ljava/io/File;", "correlations", "", "Lcom/fooddiary/domain/analysis/CorrelationAnalysis;", "triggerPatterns", "error/NonExistentClass", "startDate", "Ljava/time/LocalDate;", "endDate", "outputFile", "exportAnalyticsOnly-hUnOzRk", "(Ljava/util/List;Ljava/util/List;Ljava/time/LocalDate;Ljava/time/LocalDate;Ljava/io/File;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "exportCompleteData", "foodEntries", "Lcom/fooddiary/data/database/entities/FoodEntry;", "symptomEvents", "Lcom/fooddiary/data/database/entities/SymptomEvent;", "exportCompleteData-eH_QyT8", "(Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/time/LocalDate;Ljava/time/LocalDate;Ljava/io/File;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "exportFoodEntriesOnly", "exportFoodEntriesOnly-yxL6bBk", "(Ljava/util/List;Ljava/time/LocalDate;Ljava/time/LocalDate;Ljava/io/File;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "exportSymptomEventsOnly", "exportSymptomEventsOnly-yxL6bBk", "getAppVersion", "", "getSchemaDefinition", "Lcom/fooddiary/services/SchemaDefinition;", "importData", "Lcom/fooddiary/services/ImportResult;", "inputFile", "importData-gIAlu-s", "(Ljava/io/File;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "validateExportedJSON", "", "jsonString", "validateImportedJSON", "toEntity", "Lcom/fooddiary/services/FoodEntryExport;", "Lcom/fooddiary/services/SymptomEventExport;", "toExportModel", "Lcom/fooddiary/services/TriggerPatternExport;", "(Lerror/NonExistentClass;)Lcom/fooddiary/services/TriggerPatternExport;", "Lcom/fooddiary/services/CorrelationExport;", "Companion", "app_debug"})
public final class JSONExportService {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.serialization.json.Json json = null;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String SCHEMA_VERSION = "1.0.0";
    @org.jetbrains.annotations.NotNull()
    public static final com.fooddiary.services.JSONExportService.Companion Companion = null;
    
    @javax.inject.Inject()
    public JSONExportService(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    private final void validateExportedJSON(java.lang.String jsonString) {
    }
    
    private final void validateImportedJSON(java.lang.String jsonString) {
    }
    
    private final java.lang.String getAppVersion() {
        return null;
    }
    
    private final com.fooddiary.services.SchemaDefinition getSchemaDefinition() {
        return null;
    }
    
    private final com.fooddiary.services.FoodEntryExport toExportModel(com.fooddiary.data.database.entities.FoodEntry $this$toExportModel) {
        return null;
    }
    
    private final com.fooddiary.services.SymptomEventExport toExportModel(com.fooddiary.data.database.entities.SymptomEvent $this$toExportModel) {
        return null;
    }
    
    private final com.fooddiary.services.CorrelationExport toExportModel(com.fooddiary.domain.analysis.CorrelationAnalysis $this$toExportModel) {
        return null;
    }
    
    private final com.fooddiary.services.TriggerPatternExport toExportModel(error.NonExistentClass $this$toExportModel) {
        return null;
    }
    
    private final com.fooddiary.data.database.entities.FoodEntry toEntity(com.fooddiary.services.FoodEntryExport $this$toEntity) {
        return null;
    }
    
    private final com.fooddiary.data.database.entities.SymptomEvent toEntity(com.fooddiary.services.SymptomEventExport $this$toEntity) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/fooddiary/services/JSONExportService$Companion;", "", "()V", "SCHEMA_VERSION", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}