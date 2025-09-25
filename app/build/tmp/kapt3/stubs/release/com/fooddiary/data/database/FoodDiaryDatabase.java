package com.fooddiary.data.database;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\bH&J\b\u0010\t\u001a\u00020\nH&J\b\u0010\u000b\u001a\u00020\fH&J\b\u0010\r\u001a\u00020\u000eH&J\b\u0010\u000f\u001a\u00020\u0010H&J\b\u0010\u0011\u001a\u00020\u0012H&\u00a8\u0006\u0014"}, d2 = {"Lcom/fooddiary/data/database/FoodDiaryDatabase;", "Landroidx/room/RoomDatabase;", "()V", "beverageEntryDao", "Lcom/fooddiary/data/database/dao/BeverageEntryDao;", "eliminationProtocolDao", "Lcom/fooddiary/data/database/dao/EliminationProtocolDao;", "environmentalContextDao", "Lcom/fooddiary/data/database/dao/EnvironmentalContextDao;", "foodEntryDao", "Lcom/fooddiary/data/database/dao/FoodEntryDao;", "medicalReportDao", "Lcom/fooddiary/data/database/dao/MedicalReportDao;", "quickEntryTemplateDao", "Lcom/fooddiary/data/database/dao/QuickEntryTemplateDao;", "symptomEventDao", "Lcom/fooddiary/data/database/dao/SymptomEventDao;", "triggerPatternDao", "Lcom/fooddiary/data/database/dao/TriggerPatternDao;", "Companion", "app_release"})
@androidx.room.Database(entities = {com.fooddiary.data.database.entities.FoodEntry.class, com.fooddiary.data.database.entities.BeverageEntry.class, com.fooddiary.data.database.entities.SymptomEvent.class, com.fooddiary.data.database.entities.EnvironmentalContext.class, com.fooddiary.data.database.entities.QuickEntryTemplate.class, com.fooddiary.data.database.entities.EliminationProtocol.class, com.fooddiary.data.database.entities.TriggerPattern.class, com.fooddiary.data.database.entities.MedicalReport.class}, version = 1, exportSchema = true)
@androidx.room.TypeConverters(value = {com.fooddiary.data.database.converters.InstantConverter.class, com.fooddiary.data.database.converters.LocalDateConverter.class, com.fooddiary.data.database.converters.StringListConverter.class, com.fooddiary.data.database.converters.StringMapConverter.class, com.fooddiary.data.database.converters.ConsumptionContextConverter.class})
public abstract class FoodDiaryDatabase extends androidx.room.RoomDatabase {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String DATABASE_NAME = "food_diary_database";
    public static final int LATEST_VERSION = 1;
    @org.jetbrains.annotations.NotNull()
    public static final com.fooddiary.data.database.FoodDiaryDatabase.Companion Companion = null;
    
    public FoodDiaryDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.fooddiary.data.database.dao.FoodEntryDao foodEntryDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.fooddiary.data.database.dao.BeverageEntryDao beverageEntryDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.fooddiary.data.database.dao.SymptomEventDao symptomEventDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.fooddiary.data.database.dao.EnvironmentalContextDao environmentalContextDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.fooddiary.data.database.dao.QuickEntryTemplateDao quickEntryTemplateDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.fooddiary.data.database.dao.EliminationProtocolDao eliminationProtocolDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.fooddiary.data.database.dao.TriggerPatternDao triggerPatternDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.fooddiary.data.database.dao.MedicalReportDao medicalReportDao();
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"Lcom/fooddiary/data/database/FoodDiaryDatabase$Companion;", "", "()V", "DATABASE_NAME", "", "LATEST_VERSION", "", "app_release"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}