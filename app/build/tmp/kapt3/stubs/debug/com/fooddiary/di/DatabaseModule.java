package com.fooddiary.di;

@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0012\u0010\u0007\u001a\u00020\u00062\b\b\u0001\u0010\b\u001a\u00020\tH\u0007J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0005\u001a\u00020\u0006H\u0007\u00a8\u0006\u0018"}, d2 = {"Lcom/fooddiary/di/DatabaseModule;", "", "()V", "provideBeverageEntryDao", "Lcom/fooddiary/data/database/dao/BeverageEntryDao;", "database", "Lcom/fooddiary/data/database/FoodDiaryDatabase;", "provideDatabase", "context", "Landroid/content/Context;", "provideEliminationProtocolDao", "Lcom/fooddiary/data/database/dao/EliminationProtocolDao;", "provideEnvironmentalContextDao", "Lcom/fooddiary/data/database/dao/EnvironmentalContextDao;", "provideFoodEntryDao", "Lcom/fooddiary/data/database/dao/FoodEntryDao;", "provideMedicalReportDao", "Lcom/fooddiary/data/database/dao/MedicalReportDao;", "provideQuickEntryTemplateDao", "Lcom/fooddiary/data/database/dao/QuickEntryTemplateDao;", "provideSymptomEventDao", "Lcom/fooddiary/data/database/dao/SymptomEventDao;", "provideTriggerPatternDao", "Lcom/fooddiary/data/database/dao/TriggerPatternDao;", "app_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public final class DatabaseModule {
    @org.jetbrains.annotations.NotNull()
    public static final com.fooddiary.di.DatabaseModule INSTANCE = null;
    
    private DatabaseModule() {
        super();
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.database.FoodDiaryDatabase provideDatabase(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.database.dao.FoodEntryDao provideFoodEntryDao(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.database.FoodDiaryDatabase database) {
        return null;
    }
    
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.database.dao.BeverageEntryDao provideBeverageEntryDao(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.database.FoodDiaryDatabase database) {
        return null;
    }
    
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.database.dao.SymptomEventDao provideSymptomEventDao(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.database.FoodDiaryDatabase database) {
        return null;
    }
    
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.database.dao.EnvironmentalContextDao provideEnvironmentalContextDao(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.database.FoodDiaryDatabase database) {
        return null;
    }
    
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.database.dao.QuickEntryTemplateDao provideQuickEntryTemplateDao(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.database.FoodDiaryDatabase database) {
        return null;
    }
    
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.database.dao.EliminationProtocolDao provideEliminationProtocolDao(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.database.FoodDiaryDatabase database) {
        return null;
    }
    
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.database.dao.TriggerPatternDao provideTriggerPatternDao(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.database.FoodDiaryDatabase database) {
        return null;
    }
    
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final com.fooddiary.data.database.dao.MedicalReportDao provideMedicalReportDao(@org.jetbrains.annotations.NotNull()
    com.fooddiary.data.database.FoodDiaryDatabase database) {
        return null;
    }
}