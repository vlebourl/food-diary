package com.fooddiary.data.database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.fooddiary.data.database.dao.BeverageEntryDao;
import com.fooddiary.data.database.dao.BeverageEntryDao_Impl;
import com.fooddiary.data.database.dao.EliminationProtocolDao;
import com.fooddiary.data.database.dao.EliminationProtocolDao_Impl;
import com.fooddiary.data.database.dao.EnvironmentalContextDao;
import com.fooddiary.data.database.dao.EnvironmentalContextDao_Impl;
import com.fooddiary.data.database.dao.FoodEntryDao;
import com.fooddiary.data.database.dao.FoodEntryDao_Impl;
import com.fooddiary.data.database.dao.MedicalReportDao;
import com.fooddiary.data.database.dao.MedicalReportDao_Impl;
import com.fooddiary.data.database.dao.QuickEntryTemplateDao;
import com.fooddiary.data.database.dao.QuickEntryTemplateDao_Impl;
import com.fooddiary.data.database.dao.SymptomEventDao;
import com.fooddiary.data.database.dao.SymptomEventDao_Impl;
import com.fooddiary.data.database.dao.TriggerPatternDao;
import com.fooddiary.data.database.dao.TriggerPatternDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class FoodDiaryDatabase_Impl extends FoodDiaryDatabase {
  private volatile FoodEntryDao _foodEntryDao;

  private volatile BeverageEntryDao _beverageEntryDao;

  private volatile SymptomEventDao _symptomEventDao;

  private volatile EnvironmentalContextDao _environmentalContextDao;

  private volatile QuickEntryTemplateDao _quickEntryTemplateDao;

  private volatile EliminationProtocolDao _eliminationProtocolDao;

  private volatile TriggerPatternDao _triggerPatternDao;

  private volatile MedicalReportDao _medicalReportDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `food_entries` (`id` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, `timezone` TEXT NOT NULL, `name` TEXT NOT NULL, `ingredients` TEXT NOT NULL, `portions` REAL NOT NULL, `portionUnit` TEXT NOT NULL, `preparationMethod` TEXT, `mealType` TEXT NOT NULL, `context` TEXT NOT NULL, `notes` TEXT, `createdAt` INTEGER NOT NULL, `modifiedAt` INTEGER, `isDeleted` INTEGER NOT NULL, `deletedAt` INTEGER, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `beverage_entries` (`id` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, `timezone` TEXT NOT NULL, `name` TEXT NOT NULL, `type` TEXT NOT NULL, `volume` REAL NOT NULL, `volumeUnit` TEXT NOT NULL, `caffeineContent` REAL, `alcoholContent` REAL, `carbonation` INTEGER NOT NULL, `temperature` TEXT NOT NULL, `notes` TEXT, `createdAt` INTEGER NOT NULL, `modifiedAt` INTEGER, `isDeleted` INTEGER NOT NULL, `deletedAt` INTEGER, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `symptom_events` (`id` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, `timezone` TEXT NOT NULL, `type` TEXT NOT NULL, `severity` INTEGER NOT NULL, `duration` INTEGER, `location` TEXT, `bristolScale` INTEGER, `bristolStoolType` INTEGER, `suspectedTriggers` TEXT, `notes` TEXT, `photoPath` TEXT, `createdAt` INTEGER NOT NULL, `modifiedAt` INTEGER, `isDeleted` INTEGER NOT NULL, `deletedAt` INTEGER, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `environmental_contexts` (`date` INTEGER NOT NULL, `stressLevel` INTEGER NOT NULL, `sleepHours` REAL NOT NULL, `sleepQuality` INTEGER NOT NULL, `exerciseMinutes` INTEGER, `exerciseType` TEXT, `exerciseIntensity` TEXT, `menstrualPhase` TEXT, `weather` TEXT, `location` TEXT, `additionalNotes` TEXT, PRIMARY KEY(`date`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `quick_entry_templates` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `entryType` TEXT NOT NULL, `defaultData` TEXT NOT NULL, `buttonColor` TEXT NOT NULL, `buttonIcon` TEXT NOT NULL, `isActive` INTEGER NOT NULL, `sortOrder` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, `modifiedAt` INTEGER, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `elimination_protocols` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `startDate` INTEGER NOT NULL, `endDate` INTEGER, `currentPhase` TEXT NOT NULL, `eliminatedFoods` TEXT NOT NULL, `reintroducedFoods` TEXT NOT NULL, `phaseStartDate` INTEGER NOT NULL, `notes` TEXT, `isActive` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, `modifiedAt` INTEGER, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `trigger_patterns` (`id` TEXT NOT NULL, `foodName` TEXT NOT NULL, `symptomType` TEXT NOT NULL, `correlationStrength` REAL NOT NULL, `averageTimeOffsetMinutes` INTEGER NOT NULL, `occurrences` INTEGER NOT NULL, `confidence` REAL NOT NULL, `lastCalculated` INTEGER NOT NULL, `pValue` REAL, `standardDeviation` REAL, `minTimeOffset` INTEGER, `maxTimeOffset` INTEGER, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `medical_reports` (`id` TEXT NOT NULL, `title` TEXT NOT NULL, `startDate` INTEGER NOT NULL, `endDate` INTEGER NOT NULL, `format` TEXT NOT NULL, `sections` TEXT NOT NULL, `filePath` TEXT, `fileSize` INTEGER, `generatedAt` INTEGER NOT NULL, `isShared` INTEGER NOT NULL, `shareHistory` TEXT NOT NULL, `notes` TEXT, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '77a7abd78ecea39f603fd7d27a658d8b')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `food_entries`");
        db.execSQL("DROP TABLE IF EXISTS `beverage_entries`");
        db.execSQL("DROP TABLE IF EXISTS `symptom_events`");
        db.execSQL("DROP TABLE IF EXISTS `environmental_contexts`");
        db.execSQL("DROP TABLE IF EXISTS `quick_entry_templates`");
        db.execSQL("DROP TABLE IF EXISTS `elimination_protocols`");
        db.execSQL("DROP TABLE IF EXISTS `trigger_patterns`");
        db.execSQL("DROP TABLE IF EXISTS `medical_reports`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsFoodEntries = new HashMap<String, TableInfo.Column>(15);
        _columnsFoodEntries.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFoodEntries.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFoodEntries.put("timezone", new TableInfo.Column("timezone", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFoodEntries.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFoodEntries.put("ingredients", new TableInfo.Column("ingredients", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFoodEntries.put("portions", new TableInfo.Column("portions", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFoodEntries.put("portionUnit", new TableInfo.Column("portionUnit", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFoodEntries.put("preparationMethod", new TableInfo.Column("preparationMethod", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFoodEntries.put("mealType", new TableInfo.Column("mealType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFoodEntries.put("context", new TableInfo.Column("context", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFoodEntries.put("notes", new TableInfo.Column("notes", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFoodEntries.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFoodEntries.put("modifiedAt", new TableInfo.Column("modifiedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFoodEntries.put("isDeleted", new TableInfo.Column("isDeleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFoodEntries.put("deletedAt", new TableInfo.Column("deletedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFoodEntries = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesFoodEntries = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoFoodEntries = new TableInfo("food_entries", _columnsFoodEntries, _foreignKeysFoodEntries, _indicesFoodEntries);
        final TableInfo _existingFoodEntries = TableInfo.read(db, "food_entries");
        if (!_infoFoodEntries.equals(_existingFoodEntries)) {
          return new RoomOpenHelper.ValidationResult(false, "food_entries(com.fooddiary.data.database.entities.FoodEntry).\n"
                  + " Expected:\n" + _infoFoodEntries + "\n"
                  + " Found:\n" + _existingFoodEntries);
        }
        final HashMap<String, TableInfo.Column> _columnsBeverageEntries = new HashMap<String, TableInfo.Column>(16);
        _columnsBeverageEntries.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBeverageEntries.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBeverageEntries.put("timezone", new TableInfo.Column("timezone", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBeverageEntries.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBeverageEntries.put("type", new TableInfo.Column("type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBeverageEntries.put("volume", new TableInfo.Column("volume", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBeverageEntries.put("volumeUnit", new TableInfo.Column("volumeUnit", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBeverageEntries.put("caffeineContent", new TableInfo.Column("caffeineContent", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBeverageEntries.put("alcoholContent", new TableInfo.Column("alcoholContent", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBeverageEntries.put("carbonation", new TableInfo.Column("carbonation", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBeverageEntries.put("temperature", new TableInfo.Column("temperature", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBeverageEntries.put("notes", new TableInfo.Column("notes", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBeverageEntries.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBeverageEntries.put("modifiedAt", new TableInfo.Column("modifiedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBeverageEntries.put("isDeleted", new TableInfo.Column("isDeleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBeverageEntries.put("deletedAt", new TableInfo.Column("deletedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysBeverageEntries = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesBeverageEntries = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoBeverageEntries = new TableInfo("beverage_entries", _columnsBeverageEntries, _foreignKeysBeverageEntries, _indicesBeverageEntries);
        final TableInfo _existingBeverageEntries = TableInfo.read(db, "beverage_entries");
        if (!_infoBeverageEntries.equals(_existingBeverageEntries)) {
          return new RoomOpenHelper.ValidationResult(false, "beverage_entries(com.fooddiary.data.database.entities.BeverageEntry).\n"
                  + " Expected:\n" + _infoBeverageEntries + "\n"
                  + " Found:\n" + _existingBeverageEntries);
        }
        final HashMap<String, TableInfo.Column> _columnsSymptomEvents = new HashMap<String, TableInfo.Column>(16);
        _columnsSymptomEvents.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSymptomEvents.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSymptomEvents.put("timezone", new TableInfo.Column("timezone", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSymptomEvents.put("type", new TableInfo.Column("type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSymptomEvents.put("severity", new TableInfo.Column("severity", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSymptomEvents.put("duration", new TableInfo.Column("duration", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSymptomEvents.put("location", new TableInfo.Column("location", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSymptomEvents.put("bristolScale", new TableInfo.Column("bristolScale", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSymptomEvents.put("bristolStoolType", new TableInfo.Column("bristolStoolType", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSymptomEvents.put("suspectedTriggers", new TableInfo.Column("suspectedTriggers", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSymptomEvents.put("notes", new TableInfo.Column("notes", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSymptomEvents.put("photoPath", new TableInfo.Column("photoPath", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSymptomEvents.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSymptomEvents.put("modifiedAt", new TableInfo.Column("modifiedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSymptomEvents.put("isDeleted", new TableInfo.Column("isDeleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSymptomEvents.put("deletedAt", new TableInfo.Column("deletedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSymptomEvents = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSymptomEvents = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSymptomEvents = new TableInfo("symptom_events", _columnsSymptomEvents, _foreignKeysSymptomEvents, _indicesSymptomEvents);
        final TableInfo _existingSymptomEvents = TableInfo.read(db, "symptom_events");
        if (!_infoSymptomEvents.equals(_existingSymptomEvents)) {
          return new RoomOpenHelper.ValidationResult(false, "symptom_events(com.fooddiary.data.database.entities.SymptomEvent).\n"
                  + " Expected:\n" + _infoSymptomEvents + "\n"
                  + " Found:\n" + _existingSymptomEvents);
        }
        final HashMap<String, TableInfo.Column> _columnsEnvironmentalContexts = new HashMap<String, TableInfo.Column>(11);
        _columnsEnvironmentalContexts.put("date", new TableInfo.Column("date", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEnvironmentalContexts.put("stressLevel", new TableInfo.Column("stressLevel", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEnvironmentalContexts.put("sleepHours", new TableInfo.Column("sleepHours", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEnvironmentalContexts.put("sleepQuality", new TableInfo.Column("sleepQuality", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEnvironmentalContexts.put("exerciseMinutes", new TableInfo.Column("exerciseMinutes", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEnvironmentalContexts.put("exerciseType", new TableInfo.Column("exerciseType", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEnvironmentalContexts.put("exerciseIntensity", new TableInfo.Column("exerciseIntensity", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEnvironmentalContexts.put("menstrualPhase", new TableInfo.Column("menstrualPhase", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEnvironmentalContexts.put("weather", new TableInfo.Column("weather", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEnvironmentalContexts.put("location", new TableInfo.Column("location", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEnvironmentalContexts.put("additionalNotes", new TableInfo.Column("additionalNotes", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysEnvironmentalContexts = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesEnvironmentalContexts = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoEnvironmentalContexts = new TableInfo("environmental_contexts", _columnsEnvironmentalContexts, _foreignKeysEnvironmentalContexts, _indicesEnvironmentalContexts);
        final TableInfo _existingEnvironmentalContexts = TableInfo.read(db, "environmental_contexts");
        if (!_infoEnvironmentalContexts.equals(_existingEnvironmentalContexts)) {
          return new RoomOpenHelper.ValidationResult(false, "environmental_contexts(com.fooddiary.data.database.entities.EnvironmentalContext).\n"
                  + " Expected:\n" + _infoEnvironmentalContexts + "\n"
                  + " Found:\n" + _existingEnvironmentalContexts);
        }
        final HashMap<String, TableInfo.Column> _columnsQuickEntryTemplates = new HashMap<String, TableInfo.Column>(10);
        _columnsQuickEntryTemplates.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuickEntryTemplates.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuickEntryTemplates.put("entryType", new TableInfo.Column("entryType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuickEntryTemplates.put("defaultData", new TableInfo.Column("defaultData", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuickEntryTemplates.put("buttonColor", new TableInfo.Column("buttonColor", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuickEntryTemplates.put("buttonIcon", new TableInfo.Column("buttonIcon", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuickEntryTemplates.put("isActive", new TableInfo.Column("isActive", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuickEntryTemplates.put("sortOrder", new TableInfo.Column("sortOrder", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuickEntryTemplates.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuickEntryTemplates.put("modifiedAt", new TableInfo.Column("modifiedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysQuickEntryTemplates = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesQuickEntryTemplates = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoQuickEntryTemplates = new TableInfo("quick_entry_templates", _columnsQuickEntryTemplates, _foreignKeysQuickEntryTemplates, _indicesQuickEntryTemplates);
        final TableInfo _existingQuickEntryTemplates = TableInfo.read(db, "quick_entry_templates");
        if (!_infoQuickEntryTemplates.equals(_existingQuickEntryTemplates)) {
          return new RoomOpenHelper.ValidationResult(false, "quick_entry_templates(com.fooddiary.data.database.entities.QuickEntryTemplate).\n"
                  + " Expected:\n" + _infoQuickEntryTemplates + "\n"
                  + " Found:\n" + _existingQuickEntryTemplates);
        }
        final HashMap<String, TableInfo.Column> _columnsEliminationProtocols = new HashMap<String, TableInfo.Column>(12);
        _columnsEliminationProtocols.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEliminationProtocols.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEliminationProtocols.put("startDate", new TableInfo.Column("startDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEliminationProtocols.put("endDate", new TableInfo.Column("endDate", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEliminationProtocols.put("currentPhase", new TableInfo.Column("currentPhase", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEliminationProtocols.put("eliminatedFoods", new TableInfo.Column("eliminatedFoods", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEliminationProtocols.put("reintroducedFoods", new TableInfo.Column("reintroducedFoods", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEliminationProtocols.put("phaseStartDate", new TableInfo.Column("phaseStartDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEliminationProtocols.put("notes", new TableInfo.Column("notes", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEliminationProtocols.put("isActive", new TableInfo.Column("isActive", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEliminationProtocols.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEliminationProtocols.put("modifiedAt", new TableInfo.Column("modifiedAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysEliminationProtocols = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesEliminationProtocols = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoEliminationProtocols = new TableInfo("elimination_protocols", _columnsEliminationProtocols, _foreignKeysEliminationProtocols, _indicesEliminationProtocols);
        final TableInfo _existingEliminationProtocols = TableInfo.read(db, "elimination_protocols");
        if (!_infoEliminationProtocols.equals(_existingEliminationProtocols)) {
          return new RoomOpenHelper.ValidationResult(false, "elimination_protocols(com.fooddiary.data.database.entities.EliminationProtocol).\n"
                  + " Expected:\n" + _infoEliminationProtocols + "\n"
                  + " Found:\n" + _existingEliminationProtocols);
        }
        final HashMap<String, TableInfo.Column> _columnsTriggerPatterns = new HashMap<String, TableInfo.Column>(12);
        _columnsTriggerPatterns.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTriggerPatterns.put("foodName", new TableInfo.Column("foodName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTriggerPatterns.put("symptomType", new TableInfo.Column("symptomType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTriggerPatterns.put("correlationStrength", new TableInfo.Column("correlationStrength", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTriggerPatterns.put("averageTimeOffsetMinutes", new TableInfo.Column("averageTimeOffsetMinutes", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTriggerPatterns.put("occurrences", new TableInfo.Column("occurrences", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTriggerPatterns.put("confidence", new TableInfo.Column("confidence", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTriggerPatterns.put("lastCalculated", new TableInfo.Column("lastCalculated", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTriggerPatterns.put("pValue", new TableInfo.Column("pValue", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTriggerPatterns.put("standardDeviation", new TableInfo.Column("standardDeviation", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTriggerPatterns.put("minTimeOffset", new TableInfo.Column("minTimeOffset", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTriggerPatterns.put("maxTimeOffset", new TableInfo.Column("maxTimeOffset", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTriggerPatterns = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTriggerPatterns = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTriggerPatterns = new TableInfo("trigger_patterns", _columnsTriggerPatterns, _foreignKeysTriggerPatterns, _indicesTriggerPatterns);
        final TableInfo _existingTriggerPatterns = TableInfo.read(db, "trigger_patterns");
        if (!_infoTriggerPatterns.equals(_existingTriggerPatterns)) {
          return new RoomOpenHelper.ValidationResult(false, "trigger_patterns(com.fooddiary.data.database.entities.TriggerPattern).\n"
                  + " Expected:\n" + _infoTriggerPatterns + "\n"
                  + " Found:\n" + _existingTriggerPatterns);
        }
        final HashMap<String, TableInfo.Column> _columnsMedicalReports = new HashMap<String, TableInfo.Column>(12);
        _columnsMedicalReports.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedicalReports.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedicalReports.put("startDate", new TableInfo.Column("startDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedicalReports.put("endDate", new TableInfo.Column("endDate", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedicalReports.put("format", new TableInfo.Column("format", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedicalReports.put("sections", new TableInfo.Column("sections", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedicalReports.put("filePath", new TableInfo.Column("filePath", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedicalReports.put("fileSize", new TableInfo.Column("fileSize", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedicalReports.put("generatedAt", new TableInfo.Column("generatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedicalReports.put("isShared", new TableInfo.Column("isShared", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedicalReports.put("shareHistory", new TableInfo.Column("shareHistory", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMedicalReports.put("notes", new TableInfo.Column("notes", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMedicalReports = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMedicalReports = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMedicalReports = new TableInfo("medical_reports", _columnsMedicalReports, _foreignKeysMedicalReports, _indicesMedicalReports);
        final TableInfo _existingMedicalReports = TableInfo.read(db, "medical_reports");
        if (!_infoMedicalReports.equals(_existingMedicalReports)) {
          return new RoomOpenHelper.ValidationResult(false, "medical_reports(com.fooddiary.data.database.entities.MedicalReport).\n"
                  + " Expected:\n" + _infoMedicalReports + "\n"
                  + " Found:\n" + _existingMedicalReports);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "77a7abd78ecea39f603fd7d27a658d8b", "c635a51e8b7354522d25d21c031a78ed");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "food_entries","beverage_entries","symptom_events","environmental_contexts","quick_entry_templates","elimination_protocols","trigger_patterns","medical_reports");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `food_entries`");
      _db.execSQL("DELETE FROM `beverage_entries`");
      _db.execSQL("DELETE FROM `symptom_events`");
      _db.execSQL("DELETE FROM `environmental_contexts`");
      _db.execSQL("DELETE FROM `quick_entry_templates`");
      _db.execSQL("DELETE FROM `elimination_protocols`");
      _db.execSQL("DELETE FROM `trigger_patterns`");
      _db.execSQL("DELETE FROM `medical_reports`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(FoodEntryDao.class, FoodEntryDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(BeverageEntryDao.class, BeverageEntryDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(SymptomEventDao.class, SymptomEventDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(EnvironmentalContextDao.class, EnvironmentalContextDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(QuickEntryTemplateDao.class, QuickEntryTemplateDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(EliminationProtocolDao.class, EliminationProtocolDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(TriggerPatternDao.class, TriggerPatternDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(MedicalReportDao.class, MedicalReportDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public FoodEntryDao foodEntryDao() {
    if (_foodEntryDao != null) {
      return _foodEntryDao;
    } else {
      synchronized(this) {
        if(_foodEntryDao == null) {
          _foodEntryDao = new FoodEntryDao_Impl(this);
        }
        return _foodEntryDao;
      }
    }
  }

  @Override
  public BeverageEntryDao beverageEntryDao() {
    if (_beverageEntryDao != null) {
      return _beverageEntryDao;
    } else {
      synchronized(this) {
        if(_beverageEntryDao == null) {
          _beverageEntryDao = new BeverageEntryDao_Impl(this);
        }
        return _beverageEntryDao;
      }
    }
  }

  @Override
  public SymptomEventDao symptomEventDao() {
    if (_symptomEventDao != null) {
      return _symptomEventDao;
    } else {
      synchronized(this) {
        if(_symptomEventDao == null) {
          _symptomEventDao = new SymptomEventDao_Impl(this);
        }
        return _symptomEventDao;
      }
    }
  }

  @Override
  public EnvironmentalContextDao environmentalContextDao() {
    if (_environmentalContextDao != null) {
      return _environmentalContextDao;
    } else {
      synchronized(this) {
        if(_environmentalContextDao == null) {
          _environmentalContextDao = new EnvironmentalContextDao_Impl(this);
        }
        return _environmentalContextDao;
      }
    }
  }

  @Override
  public QuickEntryTemplateDao quickEntryTemplateDao() {
    if (_quickEntryTemplateDao != null) {
      return _quickEntryTemplateDao;
    } else {
      synchronized(this) {
        if(_quickEntryTemplateDao == null) {
          _quickEntryTemplateDao = new QuickEntryTemplateDao_Impl(this);
        }
        return _quickEntryTemplateDao;
      }
    }
  }

  @Override
  public EliminationProtocolDao eliminationProtocolDao() {
    if (_eliminationProtocolDao != null) {
      return _eliminationProtocolDao;
    } else {
      synchronized(this) {
        if(_eliminationProtocolDao == null) {
          _eliminationProtocolDao = new EliminationProtocolDao_Impl(this);
        }
        return _eliminationProtocolDao;
      }
    }
  }

  @Override
  public TriggerPatternDao triggerPatternDao() {
    if (_triggerPatternDao != null) {
      return _triggerPatternDao;
    } else {
      synchronized(this) {
        if(_triggerPatternDao == null) {
          _triggerPatternDao = new TriggerPatternDao_Impl(this);
        }
        return _triggerPatternDao;
      }
    }
  }

  @Override
  public MedicalReportDao medicalReportDao() {
    if (_medicalReportDao != null) {
      return _medicalReportDao;
    } else {
      synchronized(this) {
        if(_medicalReportDao == null) {
          _medicalReportDao = new MedicalReportDao_Impl(this);
        }
        return _medicalReportDao;
      }
    }
  }
}
