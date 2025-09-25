package com.fooddiary.data.database.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.fooddiary.data.database.converters.ConsumptionContextConverter;
import com.fooddiary.data.database.converters.InstantConverter;
import com.fooddiary.data.database.converters.StringListConverter;
import com.fooddiary.data.database.entities.FoodEntry;
import com.fooddiary.data.models.ConsumptionContext;
import com.fooddiary.data.models.MealType;
import java.lang.Class;
import java.lang.Exception;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@SuppressWarnings({"unchecked", "deprecation"})
public final class FoodEntryDao_Impl implements FoodEntryDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<FoodEntry> __insertionAdapterOfFoodEntry;

  private final InstantConverter __instantConverter = new InstantConverter();

  private final StringListConverter __stringListConverter = new StringListConverter();

  private final ConsumptionContextConverter __consumptionContextConverter = new ConsumptionContextConverter();

  private final EntityDeletionOrUpdateAdapter<FoodEntry> __updateAdapterOfFoodEntry;

  private final SharedSQLiteStatement __preparedStmtOfSoftDelete;

  private final SharedSQLiteStatement __preparedStmtOfHardDelete;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public FoodEntryDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfFoodEntry = new EntityInsertionAdapter<FoodEntry>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `food_entries` (`id`,`timestamp`,`timezone`,`name`,`ingredients`,`portions`,`portionUnit`,`preparationMethod`,`mealType`,`context`,`notes`,`createdAt`,`modifiedAt`,`isDeleted`,`deletedAt`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final FoodEntry entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        final Long _tmp = __instantConverter.fromInstant(entity.getTimestamp());
        if (_tmp == null) {
          statement.bindNull(2);
        } else {
          statement.bindLong(2, _tmp);
        }
        if (entity.getTimezone() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getTimezone());
        }
        if (entity.getName() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getName());
        }
        final String _tmp_1 = __stringListConverter.fromStringList(entity.getIngredients());
        if (_tmp_1 == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, _tmp_1);
        }
        statement.bindDouble(6, entity.getPortions());
        if (entity.getPortionUnit() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getPortionUnit());
        }
        if (entity.getPreparationMethod() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getPreparationMethod());
        }
        statement.bindString(9, __MealType_enumToString(entity.getMealType()));
        final String _tmp_2 = __consumptionContextConverter.fromConsumptionContext(entity.getContext());
        if (_tmp_2 == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, _tmp_2);
        }
        if (entity.getNotes() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getNotes());
        }
        final Long _tmp_3 = __instantConverter.fromInstant(entity.getCreatedAt());
        if (_tmp_3 == null) {
          statement.bindNull(12);
        } else {
          statement.bindLong(12, _tmp_3);
        }
        final Long _tmp_4 = __instantConverter.fromInstant(entity.getModifiedAt());
        if (_tmp_4 == null) {
          statement.bindNull(13);
        } else {
          statement.bindLong(13, _tmp_4);
        }
        final int _tmp_5 = entity.isDeleted() ? 1 : 0;
        statement.bindLong(14, _tmp_5);
        final Long _tmp_6 = __instantConverter.fromInstant(entity.getDeletedAt());
        if (_tmp_6 == null) {
          statement.bindNull(15);
        } else {
          statement.bindLong(15, _tmp_6);
        }
      }
    };
    this.__updateAdapterOfFoodEntry = new EntityDeletionOrUpdateAdapter<FoodEntry>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `food_entries` SET `id` = ?,`timestamp` = ?,`timezone` = ?,`name` = ?,`ingredients` = ?,`portions` = ?,`portionUnit` = ?,`preparationMethod` = ?,`mealType` = ?,`context` = ?,`notes` = ?,`createdAt` = ?,`modifiedAt` = ?,`isDeleted` = ?,`deletedAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final FoodEntry entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        final Long _tmp = __instantConverter.fromInstant(entity.getTimestamp());
        if (_tmp == null) {
          statement.bindNull(2);
        } else {
          statement.bindLong(2, _tmp);
        }
        if (entity.getTimezone() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getTimezone());
        }
        if (entity.getName() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getName());
        }
        final String _tmp_1 = __stringListConverter.fromStringList(entity.getIngredients());
        if (_tmp_1 == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, _tmp_1);
        }
        statement.bindDouble(6, entity.getPortions());
        if (entity.getPortionUnit() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getPortionUnit());
        }
        if (entity.getPreparationMethod() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getPreparationMethod());
        }
        statement.bindString(9, __MealType_enumToString(entity.getMealType()));
        final String _tmp_2 = __consumptionContextConverter.fromConsumptionContext(entity.getContext());
        if (_tmp_2 == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, _tmp_2);
        }
        if (entity.getNotes() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getNotes());
        }
        final Long _tmp_3 = __instantConverter.fromInstant(entity.getCreatedAt());
        if (_tmp_3 == null) {
          statement.bindNull(12);
        } else {
          statement.bindLong(12, _tmp_3);
        }
        final Long _tmp_4 = __instantConverter.fromInstant(entity.getModifiedAt());
        if (_tmp_4 == null) {
          statement.bindNull(13);
        } else {
          statement.bindLong(13, _tmp_4);
        }
        final int _tmp_5 = entity.isDeleted() ? 1 : 0;
        statement.bindLong(14, _tmp_5);
        final Long _tmp_6 = __instantConverter.fromInstant(entity.getDeletedAt());
        if (_tmp_6 == null) {
          statement.bindNull(15);
        } else {
          statement.bindLong(15, _tmp_6);
        }
        if (entity.getId() == null) {
          statement.bindNull(16);
        } else {
          statement.bindString(16, entity.getId());
        }
      }
    };
    this.__preparedStmtOfSoftDelete = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "\n"
                + "        UPDATE food_entries\n"
                + "        SET isDeleted = 1, deletedAt = ?, modifiedAt = ?\n"
                + "        WHERE id = ?\n"
                + "    ";
        return _query;
      }
    };
    this.__preparedStmtOfHardDelete = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM food_entries WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM food_entries";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final FoodEntry entry, final Continuation<? super Long> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfFoodEntry.insertAndReturnId(entry);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object update(final FoodEntry entry, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfFoodEntry.handle(entry);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object softDelete(final String id, final Instant deletedAt, final Instant modifiedAt,
      final Continuation<? super Unit> arg3) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfSoftDelete.acquire();
        int _argIndex = 1;
        final Long _tmp = __instantConverter.fromInstant(deletedAt);
        if (_tmp == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, _tmp);
        }
        _argIndex = 2;
        final Long _tmp_1 = __instantConverter.fromInstant(modifiedAt);
        if (_tmp_1 == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, _tmp_1);
        }
        _argIndex = 3;
        if (id == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, id);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfSoftDelete.release(_stmt);
        }
      }
    }, arg3);
  }

  @Override
  public Object hardDelete(final String id, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfHardDelete.acquire();
        int _argIndex = 1;
        if (id == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, id);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfHardDelete.release(_stmt);
        }
      }
    }, arg1);
  }

  @Override
  public Object deleteAll(final Continuation<? super Unit> arg0) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteAll.release(_stmt);
        }
      }
    }, arg0);
  }

  @Override
  public Object getById(final String id, final Continuation<? super FoodEntry> arg1) {
    final String _sql = "SELECT * FROM food_entries WHERE id = ? AND isDeleted = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, id);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<FoodEntry>() {
      @Override
      @Nullable
      public FoodEntry call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfTimezone = CursorUtil.getColumnIndexOrThrow(_cursor, "timezone");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfIngredients = CursorUtil.getColumnIndexOrThrow(_cursor, "ingredients");
          final int _cursorIndexOfPortions = CursorUtil.getColumnIndexOrThrow(_cursor, "portions");
          final int _cursorIndexOfPortionUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "portionUnit");
          final int _cursorIndexOfPreparationMethod = CursorUtil.getColumnIndexOrThrow(_cursor, "preparationMethod");
          final int _cursorIndexOfMealType = CursorUtil.getColumnIndexOrThrow(_cursor, "mealType");
          final int _cursorIndexOfContext = CursorUtil.getColumnIndexOrThrow(_cursor, "context");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfModifiedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "modifiedAt");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isDeleted");
          final int _cursorIndexOfDeletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "deletedAt");
          final FoodEntry _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final Instant _tmpTimestamp;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfTimestamp)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfTimestamp);
            }
            _tmpTimestamp = __instantConverter.toInstant(_tmp);
            final String _tmpTimezone;
            if (_cursor.isNull(_cursorIndexOfTimezone)) {
              _tmpTimezone = null;
            } else {
              _tmpTimezone = _cursor.getString(_cursorIndexOfTimezone);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final List<String> _tmpIngredients;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfIngredients)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfIngredients);
            }
            _tmpIngredients = __stringListConverter.toStringList(_tmp_1);
            final float _tmpPortions;
            _tmpPortions = _cursor.getFloat(_cursorIndexOfPortions);
            final String _tmpPortionUnit;
            if (_cursor.isNull(_cursorIndexOfPortionUnit)) {
              _tmpPortionUnit = null;
            } else {
              _tmpPortionUnit = _cursor.getString(_cursorIndexOfPortionUnit);
            }
            final String _tmpPreparationMethod;
            if (_cursor.isNull(_cursorIndexOfPreparationMethod)) {
              _tmpPreparationMethod = null;
            } else {
              _tmpPreparationMethod = _cursor.getString(_cursorIndexOfPreparationMethod);
            }
            final MealType _tmpMealType;
            _tmpMealType = __MealType_stringToEnum(_cursor.getString(_cursorIndexOfMealType));
            final ConsumptionContext _tmpContext;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfContext)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfContext);
            }
            _tmpContext = __consumptionContextConverter.toConsumptionContext(_tmp_2);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final Instant _tmpCreatedAt;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            _tmpCreatedAt = __instantConverter.toInstant(_tmp_3);
            final Instant _tmpModifiedAt;
            final Long _tmp_4;
            if (_cursor.isNull(_cursorIndexOfModifiedAt)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getLong(_cursorIndexOfModifiedAt);
            }
            _tmpModifiedAt = __instantConverter.toInstant(_tmp_4);
            final boolean _tmpIsDeleted;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsDeleted);
            _tmpIsDeleted = _tmp_5 != 0;
            final Instant _tmpDeletedAt;
            final Long _tmp_6;
            if (_cursor.isNull(_cursorIndexOfDeletedAt)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getLong(_cursorIndexOfDeletedAt);
            }
            _tmpDeletedAt = __instantConverter.toInstant(_tmp_6);
            _result = new FoodEntry(_tmpId,_tmpTimestamp,_tmpTimezone,_tmpName,_tmpIngredients,_tmpPortions,_tmpPortionUnit,_tmpPreparationMethod,_tmpMealType,_tmpContext,_tmpNotes,_tmpCreatedAt,_tmpModifiedAt,_tmpIsDeleted,_tmpDeletedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, arg1);
  }

  @Override
  public Flow<List<FoodEntry>> getAll() {
    final String _sql = "SELECT * FROM food_entries WHERE isDeleted = 0 ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"food_entries"}, new Callable<List<FoodEntry>>() {
      @Override
      @NonNull
      public List<FoodEntry> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfTimezone = CursorUtil.getColumnIndexOrThrow(_cursor, "timezone");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfIngredients = CursorUtil.getColumnIndexOrThrow(_cursor, "ingredients");
          final int _cursorIndexOfPortions = CursorUtil.getColumnIndexOrThrow(_cursor, "portions");
          final int _cursorIndexOfPortionUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "portionUnit");
          final int _cursorIndexOfPreparationMethod = CursorUtil.getColumnIndexOrThrow(_cursor, "preparationMethod");
          final int _cursorIndexOfMealType = CursorUtil.getColumnIndexOrThrow(_cursor, "mealType");
          final int _cursorIndexOfContext = CursorUtil.getColumnIndexOrThrow(_cursor, "context");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfModifiedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "modifiedAt");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isDeleted");
          final int _cursorIndexOfDeletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "deletedAt");
          final List<FoodEntry> _result = new ArrayList<FoodEntry>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final FoodEntry _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final Instant _tmpTimestamp;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfTimestamp)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfTimestamp);
            }
            _tmpTimestamp = __instantConverter.toInstant(_tmp);
            final String _tmpTimezone;
            if (_cursor.isNull(_cursorIndexOfTimezone)) {
              _tmpTimezone = null;
            } else {
              _tmpTimezone = _cursor.getString(_cursorIndexOfTimezone);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final List<String> _tmpIngredients;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfIngredients)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfIngredients);
            }
            _tmpIngredients = __stringListConverter.toStringList(_tmp_1);
            final float _tmpPortions;
            _tmpPortions = _cursor.getFloat(_cursorIndexOfPortions);
            final String _tmpPortionUnit;
            if (_cursor.isNull(_cursorIndexOfPortionUnit)) {
              _tmpPortionUnit = null;
            } else {
              _tmpPortionUnit = _cursor.getString(_cursorIndexOfPortionUnit);
            }
            final String _tmpPreparationMethod;
            if (_cursor.isNull(_cursorIndexOfPreparationMethod)) {
              _tmpPreparationMethod = null;
            } else {
              _tmpPreparationMethod = _cursor.getString(_cursorIndexOfPreparationMethod);
            }
            final MealType _tmpMealType;
            _tmpMealType = __MealType_stringToEnum(_cursor.getString(_cursorIndexOfMealType));
            final ConsumptionContext _tmpContext;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfContext)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfContext);
            }
            _tmpContext = __consumptionContextConverter.toConsumptionContext(_tmp_2);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final Instant _tmpCreatedAt;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            _tmpCreatedAt = __instantConverter.toInstant(_tmp_3);
            final Instant _tmpModifiedAt;
            final Long _tmp_4;
            if (_cursor.isNull(_cursorIndexOfModifiedAt)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getLong(_cursorIndexOfModifiedAt);
            }
            _tmpModifiedAt = __instantConverter.toInstant(_tmp_4);
            final boolean _tmpIsDeleted;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsDeleted);
            _tmpIsDeleted = _tmp_5 != 0;
            final Instant _tmpDeletedAt;
            final Long _tmp_6;
            if (_cursor.isNull(_cursorIndexOfDeletedAt)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getLong(_cursorIndexOfDeletedAt);
            }
            _tmpDeletedAt = __instantConverter.toInstant(_tmp_6);
            _item = new FoodEntry(_tmpId,_tmpTimestamp,_tmpTimezone,_tmpName,_tmpIngredients,_tmpPortions,_tmpPortionUnit,_tmpPreparationMethod,_tmpMealType,_tmpContext,_tmpNotes,_tmpCreatedAt,_tmpModifiedAt,_tmpIsDeleted,_tmpDeletedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<FoodEntry>> getAllByDateRange(final String startDate, final String endDate) {
    final String _sql = "\n"
            + "        SELECT * FROM food_entries\n"
            + "        WHERE DATE(timestamp, 'unixepoch') BETWEEN ? AND ?\n"
            + "        AND isDeleted = 0\n"
            + "        ORDER BY timestamp DESC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (startDate == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, startDate);
    }
    _argIndex = 2;
    if (endDate == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, endDate);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"food_entries"}, new Callable<List<FoodEntry>>() {
      @Override
      @NonNull
      public List<FoodEntry> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfTimezone = CursorUtil.getColumnIndexOrThrow(_cursor, "timezone");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfIngredients = CursorUtil.getColumnIndexOrThrow(_cursor, "ingredients");
          final int _cursorIndexOfPortions = CursorUtil.getColumnIndexOrThrow(_cursor, "portions");
          final int _cursorIndexOfPortionUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "portionUnit");
          final int _cursorIndexOfPreparationMethod = CursorUtil.getColumnIndexOrThrow(_cursor, "preparationMethod");
          final int _cursorIndexOfMealType = CursorUtil.getColumnIndexOrThrow(_cursor, "mealType");
          final int _cursorIndexOfContext = CursorUtil.getColumnIndexOrThrow(_cursor, "context");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfModifiedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "modifiedAt");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isDeleted");
          final int _cursorIndexOfDeletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "deletedAt");
          final List<FoodEntry> _result = new ArrayList<FoodEntry>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final FoodEntry _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final Instant _tmpTimestamp;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfTimestamp)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfTimestamp);
            }
            _tmpTimestamp = __instantConverter.toInstant(_tmp);
            final String _tmpTimezone;
            if (_cursor.isNull(_cursorIndexOfTimezone)) {
              _tmpTimezone = null;
            } else {
              _tmpTimezone = _cursor.getString(_cursorIndexOfTimezone);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final List<String> _tmpIngredients;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfIngredients)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfIngredients);
            }
            _tmpIngredients = __stringListConverter.toStringList(_tmp_1);
            final float _tmpPortions;
            _tmpPortions = _cursor.getFloat(_cursorIndexOfPortions);
            final String _tmpPortionUnit;
            if (_cursor.isNull(_cursorIndexOfPortionUnit)) {
              _tmpPortionUnit = null;
            } else {
              _tmpPortionUnit = _cursor.getString(_cursorIndexOfPortionUnit);
            }
            final String _tmpPreparationMethod;
            if (_cursor.isNull(_cursorIndexOfPreparationMethod)) {
              _tmpPreparationMethod = null;
            } else {
              _tmpPreparationMethod = _cursor.getString(_cursorIndexOfPreparationMethod);
            }
            final MealType _tmpMealType;
            _tmpMealType = __MealType_stringToEnum(_cursor.getString(_cursorIndexOfMealType));
            final ConsumptionContext _tmpContext;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfContext)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfContext);
            }
            _tmpContext = __consumptionContextConverter.toConsumptionContext(_tmp_2);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final Instant _tmpCreatedAt;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            _tmpCreatedAt = __instantConverter.toInstant(_tmp_3);
            final Instant _tmpModifiedAt;
            final Long _tmp_4;
            if (_cursor.isNull(_cursorIndexOfModifiedAt)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getLong(_cursorIndexOfModifiedAt);
            }
            _tmpModifiedAt = __instantConverter.toInstant(_tmp_4);
            final boolean _tmpIsDeleted;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsDeleted);
            _tmpIsDeleted = _tmp_5 != 0;
            final Instant _tmpDeletedAt;
            final Long _tmp_6;
            if (_cursor.isNull(_cursorIndexOfDeletedAt)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getLong(_cursorIndexOfDeletedAt);
            }
            _tmpDeletedAt = __instantConverter.toInstant(_tmp_6);
            _item = new FoodEntry(_tmpId,_tmpTimestamp,_tmpTimezone,_tmpName,_tmpIngredients,_tmpPortions,_tmpPortionUnit,_tmpPreparationMethod,_tmpMealType,_tmpContext,_tmpNotes,_tmpCreatedAt,_tmpModifiedAt,_tmpIsDeleted,_tmpDeletedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<FoodEntry>> getRecent(final int limit) {
    final String _sql = "SELECT * FROM food_entries WHERE isDeleted = 0 ORDER BY timestamp DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"food_entries"}, new Callable<List<FoodEntry>>() {
      @Override
      @NonNull
      public List<FoodEntry> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfTimezone = CursorUtil.getColumnIndexOrThrow(_cursor, "timezone");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfIngredients = CursorUtil.getColumnIndexOrThrow(_cursor, "ingredients");
          final int _cursorIndexOfPortions = CursorUtil.getColumnIndexOrThrow(_cursor, "portions");
          final int _cursorIndexOfPortionUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "portionUnit");
          final int _cursorIndexOfPreparationMethod = CursorUtil.getColumnIndexOrThrow(_cursor, "preparationMethod");
          final int _cursorIndexOfMealType = CursorUtil.getColumnIndexOrThrow(_cursor, "mealType");
          final int _cursorIndexOfContext = CursorUtil.getColumnIndexOrThrow(_cursor, "context");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfModifiedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "modifiedAt");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isDeleted");
          final int _cursorIndexOfDeletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "deletedAt");
          final List<FoodEntry> _result = new ArrayList<FoodEntry>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final FoodEntry _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final Instant _tmpTimestamp;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfTimestamp)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfTimestamp);
            }
            _tmpTimestamp = __instantConverter.toInstant(_tmp);
            final String _tmpTimezone;
            if (_cursor.isNull(_cursorIndexOfTimezone)) {
              _tmpTimezone = null;
            } else {
              _tmpTimezone = _cursor.getString(_cursorIndexOfTimezone);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final List<String> _tmpIngredients;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfIngredients)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfIngredients);
            }
            _tmpIngredients = __stringListConverter.toStringList(_tmp_1);
            final float _tmpPortions;
            _tmpPortions = _cursor.getFloat(_cursorIndexOfPortions);
            final String _tmpPortionUnit;
            if (_cursor.isNull(_cursorIndexOfPortionUnit)) {
              _tmpPortionUnit = null;
            } else {
              _tmpPortionUnit = _cursor.getString(_cursorIndexOfPortionUnit);
            }
            final String _tmpPreparationMethod;
            if (_cursor.isNull(_cursorIndexOfPreparationMethod)) {
              _tmpPreparationMethod = null;
            } else {
              _tmpPreparationMethod = _cursor.getString(_cursorIndexOfPreparationMethod);
            }
            final MealType _tmpMealType;
            _tmpMealType = __MealType_stringToEnum(_cursor.getString(_cursorIndexOfMealType));
            final ConsumptionContext _tmpContext;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfContext)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfContext);
            }
            _tmpContext = __consumptionContextConverter.toConsumptionContext(_tmp_2);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final Instant _tmpCreatedAt;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            _tmpCreatedAt = __instantConverter.toInstant(_tmp_3);
            final Instant _tmpModifiedAt;
            final Long _tmp_4;
            if (_cursor.isNull(_cursorIndexOfModifiedAt)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getLong(_cursorIndexOfModifiedAt);
            }
            _tmpModifiedAt = __instantConverter.toInstant(_tmp_4);
            final boolean _tmpIsDeleted;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsDeleted);
            _tmpIsDeleted = _tmp_5 != 0;
            final Instant _tmpDeletedAt;
            final Long _tmp_6;
            if (_cursor.isNull(_cursorIndexOfDeletedAt)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getLong(_cursorIndexOfDeletedAt);
            }
            _tmpDeletedAt = __instantConverter.toInstant(_tmp_6);
            _item = new FoodEntry(_tmpId,_tmpTimestamp,_tmpTimezone,_tmpName,_tmpIngredients,_tmpPortions,_tmpPortionUnit,_tmpPreparationMethod,_tmpMealType,_tmpContext,_tmpNotes,_tmpCreatedAt,_tmpModifiedAt,_tmpIsDeleted,_tmpDeletedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<FoodEntry>> searchByName(final String query) {
    final String _sql = "\n"
            + "        SELECT * FROM food_entries\n"
            + "        WHERE name LIKE '%' || ? || '%'\n"
            + "        AND isDeleted = 0\n"
            + "        ORDER BY timestamp DESC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (query == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, query);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"food_entries"}, new Callable<List<FoodEntry>>() {
      @Override
      @NonNull
      public List<FoodEntry> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfTimezone = CursorUtil.getColumnIndexOrThrow(_cursor, "timezone");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfIngredients = CursorUtil.getColumnIndexOrThrow(_cursor, "ingredients");
          final int _cursorIndexOfPortions = CursorUtil.getColumnIndexOrThrow(_cursor, "portions");
          final int _cursorIndexOfPortionUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "portionUnit");
          final int _cursorIndexOfPreparationMethod = CursorUtil.getColumnIndexOrThrow(_cursor, "preparationMethod");
          final int _cursorIndexOfMealType = CursorUtil.getColumnIndexOrThrow(_cursor, "mealType");
          final int _cursorIndexOfContext = CursorUtil.getColumnIndexOrThrow(_cursor, "context");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfModifiedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "modifiedAt");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isDeleted");
          final int _cursorIndexOfDeletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "deletedAt");
          final List<FoodEntry> _result = new ArrayList<FoodEntry>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final FoodEntry _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final Instant _tmpTimestamp;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfTimestamp)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfTimestamp);
            }
            _tmpTimestamp = __instantConverter.toInstant(_tmp);
            final String _tmpTimezone;
            if (_cursor.isNull(_cursorIndexOfTimezone)) {
              _tmpTimezone = null;
            } else {
              _tmpTimezone = _cursor.getString(_cursorIndexOfTimezone);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final List<String> _tmpIngredients;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfIngredients)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfIngredients);
            }
            _tmpIngredients = __stringListConverter.toStringList(_tmp_1);
            final float _tmpPortions;
            _tmpPortions = _cursor.getFloat(_cursorIndexOfPortions);
            final String _tmpPortionUnit;
            if (_cursor.isNull(_cursorIndexOfPortionUnit)) {
              _tmpPortionUnit = null;
            } else {
              _tmpPortionUnit = _cursor.getString(_cursorIndexOfPortionUnit);
            }
            final String _tmpPreparationMethod;
            if (_cursor.isNull(_cursorIndexOfPreparationMethod)) {
              _tmpPreparationMethod = null;
            } else {
              _tmpPreparationMethod = _cursor.getString(_cursorIndexOfPreparationMethod);
            }
            final MealType _tmpMealType;
            _tmpMealType = __MealType_stringToEnum(_cursor.getString(_cursorIndexOfMealType));
            final ConsumptionContext _tmpContext;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfContext)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfContext);
            }
            _tmpContext = __consumptionContextConverter.toConsumptionContext(_tmp_2);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final Instant _tmpCreatedAt;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            _tmpCreatedAt = __instantConverter.toInstant(_tmp_3);
            final Instant _tmpModifiedAt;
            final Long _tmp_4;
            if (_cursor.isNull(_cursorIndexOfModifiedAt)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getLong(_cursorIndexOfModifiedAt);
            }
            _tmpModifiedAt = __instantConverter.toInstant(_tmp_4);
            final boolean _tmpIsDeleted;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsDeleted);
            _tmpIsDeleted = _tmp_5 != 0;
            final Instant _tmpDeletedAt;
            final Long _tmp_6;
            if (_cursor.isNull(_cursorIndexOfDeletedAt)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getLong(_cursorIndexOfDeletedAt);
            }
            _tmpDeletedAt = __instantConverter.toInstant(_tmp_6);
            _item = new FoodEntry(_tmpId,_tmpTimestamp,_tmpTimezone,_tmpName,_tmpIngredients,_tmpPortions,_tmpPortionUnit,_tmpPreparationMethod,_tmpMealType,_tmpContext,_tmpNotes,_tmpCreatedAt,_tmpModifiedAt,_tmpIsDeleted,_tmpDeletedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getMostFrequent(final int limit, final Continuation<? super List<FoodEntry>> arg1) {
    final String _sql = "\n"
            + "        SELECT * FROM food_entries\n"
            + "        WHERE isDeleted = 0\n"
            + "        GROUP BY name\n"
            + "        ORDER BY COUNT(*) DESC\n"
            + "        LIMIT ?\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<FoodEntry>>() {
      @Override
      @NonNull
      public List<FoodEntry> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfTimezone = CursorUtil.getColumnIndexOrThrow(_cursor, "timezone");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfIngredients = CursorUtil.getColumnIndexOrThrow(_cursor, "ingredients");
          final int _cursorIndexOfPortions = CursorUtil.getColumnIndexOrThrow(_cursor, "portions");
          final int _cursorIndexOfPortionUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "portionUnit");
          final int _cursorIndexOfPreparationMethod = CursorUtil.getColumnIndexOrThrow(_cursor, "preparationMethod");
          final int _cursorIndexOfMealType = CursorUtil.getColumnIndexOrThrow(_cursor, "mealType");
          final int _cursorIndexOfContext = CursorUtil.getColumnIndexOrThrow(_cursor, "context");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfModifiedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "modifiedAt");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isDeleted");
          final int _cursorIndexOfDeletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "deletedAt");
          final List<FoodEntry> _result = new ArrayList<FoodEntry>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final FoodEntry _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final Instant _tmpTimestamp;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfTimestamp)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfTimestamp);
            }
            _tmpTimestamp = __instantConverter.toInstant(_tmp);
            final String _tmpTimezone;
            if (_cursor.isNull(_cursorIndexOfTimezone)) {
              _tmpTimezone = null;
            } else {
              _tmpTimezone = _cursor.getString(_cursorIndexOfTimezone);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final List<String> _tmpIngredients;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfIngredients)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfIngredients);
            }
            _tmpIngredients = __stringListConverter.toStringList(_tmp_1);
            final float _tmpPortions;
            _tmpPortions = _cursor.getFloat(_cursorIndexOfPortions);
            final String _tmpPortionUnit;
            if (_cursor.isNull(_cursorIndexOfPortionUnit)) {
              _tmpPortionUnit = null;
            } else {
              _tmpPortionUnit = _cursor.getString(_cursorIndexOfPortionUnit);
            }
            final String _tmpPreparationMethod;
            if (_cursor.isNull(_cursorIndexOfPreparationMethod)) {
              _tmpPreparationMethod = null;
            } else {
              _tmpPreparationMethod = _cursor.getString(_cursorIndexOfPreparationMethod);
            }
            final MealType _tmpMealType;
            _tmpMealType = __MealType_stringToEnum(_cursor.getString(_cursorIndexOfMealType));
            final ConsumptionContext _tmpContext;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfContext)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfContext);
            }
            _tmpContext = __consumptionContextConverter.toConsumptionContext(_tmp_2);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final Instant _tmpCreatedAt;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            _tmpCreatedAt = __instantConverter.toInstant(_tmp_3);
            final Instant _tmpModifiedAt;
            final Long _tmp_4;
            if (_cursor.isNull(_cursorIndexOfModifiedAt)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getLong(_cursorIndexOfModifiedAt);
            }
            _tmpModifiedAt = __instantConverter.toInstant(_tmp_4);
            final boolean _tmpIsDeleted;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsDeleted);
            _tmpIsDeleted = _tmp_5 != 0;
            final Instant _tmpDeletedAt;
            final Long _tmp_6;
            if (_cursor.isNull(_cursorIndexOfDeletedAt)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getLong(_cursorIndexOfDeletedAt);
            }
            _tmpDeletedAt = __instantConverter.toInstant(_tmp_6);
            _item = new FoodEntry(_tmpId,_tmpTimestamp,_tmpTimezone,_tmpName,_tmpIngredients,_tmpPortions,_tmpPortionUnit,_tmpPreparationMethod,_tmpMealType,_tmpContext,_tmpNotes,_tmpCreatedAt,_tmpModifiedAt,_tmpIsDeleted,_tmpDeletedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, arg1);
  }

  @Override
  public Flow<List<FoodEntry>> searchByIngredient(final String ingredient) {
    final String _sql = "\n"
            + "        SELECT * FROM food_entries\n"
            + "        WHERE ingredients LIKE '%' || ? || '%'\n"
            + "        AND isDeleted = 0\n"
            + "        ORDER BY timestamp DESC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (ingredient == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, ingredient);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"food_entries"}, new Callable<List<FoodEntry>>() {
      @Override
      @NonNull
      public List<FoodEntry> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfTimezone = CursorUtil.getColumnIndexOrThrow(_cursor, "timezone");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfIngredients = CursorUtil.getColumnIndexOrThrow(_cursor, "ingredients");
          final int _cursorIndexOfPortions = CursorUtil.getColumnIndexOrThrow(_cursor, "portions");
          final int _cursorIndexOfPortionUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "portionUnit");
          final int _cursorIndexOfPreparationMethod = CursorUtil.getColumnIndexOrThrow(_cursor, "preparationMethod");
          final int _cursorIndexOfMealType = CursorUtil.getColumnIndexOrThrow(_cursor, "mealType");
          final int _cursorIndexOfContext = CursorUtil.getColumnIndexOrThrow(_cursor, "context");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfModifiedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "modifiedAt");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isDeleted");
          final int _cursorIndexOfDeletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "deletedAt");
          final List<FoodEntry> _result = new ArrayList<FoodEntry>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final FoodEntry _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final Instant _tmpTimestamp;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfTimestamp)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfTimestamp);
            }
            _tmpTimestamp = __instantConverter.toInstant(_tmp);
            final String _tmpTimezone;
            if (_cursor.isNull(_cursorIndexOfTimezone)) {
              _tmpTimezone = null;
            } else {
              _tmpTimezone = _cursor.getString(_cursorIndexOfTimezone);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final List<String> _tmpIngredients;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfIngredients)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfIngredients);
            }
            _tmpIngredients = __stringListConverter.toStringList(_tmp_1);
            final float _tmpPortions;
            _tmpPortions = _cursor.getFloat(_cursorIndexOfPortions);
            final String _tmpPortionUnit;
            if (_cursor.isNull(_cursorIndexOfPortionUnit)) {
              _tmpPortionUnit = null;
            } else {
              _tmpPortionUnit = _cursor.getString(_cursorIndexOfPortionUnit);
            }
            final String _tmpPreparationMethod;
            if (_cursor.isNull(_cursorIndexOfPreparationMethod)) {
              _tmpPreparationMethod = null;
            } else {
              _tmpPreparationMethod = _cursor.getString(_cursorIndexOfPreparationMethod);
            }
            final MealType _tmpMealType;
            _tmpMealType = __MealType_stringToEnum(_cursor.getString(_cursorIndexOfMealType));
            final ConsumptionContext _tmpContext;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfContext)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfContext);
            }
            _tmpContext = __consumptionContextConverter.toConsumptionContext(_tmp_2);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final Instant _tmpCreatedAt;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            _tmpCreatedAt = __instantConverter.toInstant(_tmp_3);
            final Instant _tmpModifiedAt;
            final Long _tmp_4;
            if (_cursor.isNull(_cursorIndexOfModifiedAt)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getLong(_cursorIndexOfModifiedAt);
            }
            _tmpModifiedAt = __instantConverter.toInstant(_tmp_4);
            final boolean _tmpIsDeleted;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsDeleted);
            _tmpIsDeleted = _tmp_5 != 0;
            final Instant _tmpDeletedAt;
            final Long _tmp_6;
            if (_cursor.isNull(_cursorIndexOfDeletedAt)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getLong(_cursorIndexOfDeletedAt);
            }
            _tmpDeletedAt = __instantConverter.toInstant(_tmp_6);
            _item = new FoodEntry(_tmpId,_tmpTimestamp,_tmpTimezone,_tmpName,_tmpIngredients,_tmpPortions,_tmpPortionUnit,_tmpPreparationMethod,_tmpMealType,_tmpContext,_tmpNotes,_tmpCreatedAt,_tmpModifiedAt,_tmpIsDeleted,_tmpDeletedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getAllFoodNames(final Continuation<? super List<String>> arg0) {
    final String _sql = "\n"
            + "        SELECT DISTINCT name FROM food_entries\n"
            + "        WHERE isDeleted = 0\n"
            + "        ORDER BY name ASC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<String>>() {
      @Override
      @NonNull
      public List<String> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final List<String> _result = new ArrayList<String>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final String _item;
            if (_cursor.isNull(0)) {
              _item = null;
            } else {
              _item = _cursor.getString(0);
            }
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, arg0);
  }

  @Override
  public Object getAllIngredients(final Continuation<? super List<String>> arg0) {
    final String _sql = "\n"
            + "        SELECT DISTINCT ingredients FROM food_entries\n"
            + "        WHERE isDeleted = 0 AND ingredients IS NOT NULL\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<String>>() {
      @Override
      @NonNull
      public List<String> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final List<String> _result = new ArrayList<String>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final String _item;
            if (_cursor.isNull(0)) {
              _item = null;
            } else {
              _item = _cursor.getString(0);
            }
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, arg0);
  }

  @Override
  public Object getCount(final Continuation<? super Integer> arg0) {
    final String _sql = "SELECT COUNT(*) FROM food_entries WHERE isDeleted = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, arg0);
  }

  @Override
  public Object getCountByDate(final String date, final Continuation<? super Integer> arg1) {
    final String _sql = "\n"
            + "        SELECT COUNT(*) FROM food_entries\n"
            + "        WHERE DATE(timestamp, 'unixepoch') = ?\n"
            + "        AND isDeleted = 0\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (date == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, date);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, arg1);
  }

  @Override
  public Object getEntriesInTimeWindow(final Instant startTime, final Instant endTime,
      final Continuation<? super List<FoodEntry>> arg2) {
    final String _sql = "\n"
            + "        SELECT * FROM food_entries\n"
            + "        WHERE timestamp BETWEEN ? AND ?\n"
            + "        AND isDeleted = 0\n"
            + "        ORDER BY timestamp ASC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    final Long _tmp = __instantConverter.fromInstant(startTime);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    _argIndex = 2;
    final Long _tmp_1 = __instantConverter.fromInstant(endTime);
    if (_tmp_1 == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp_1);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<FoodEntry>>() {
      @Override
      @NonNull
      public List<FoodEntry> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfTimezone = CursorUtil.getColumnIndexOrThrow(_cursor, "timezone");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfIngredients = CursorUtil.getColumnIndexOrThrow(_cursor, "ingredients");
          final int _cursorIndexOfPortions = CursorUtil.getColumnIndexOrThrow(_cursor, "portions");
          final int _cursorIndexOfPortionUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "portionUnit");
          final int _cursorIndexOfPreparationMethod = CursorUtil.getColumnIndexOrThrow(_cursor, "preparationMethod");
          final int _cursorIndexOfMealType = CursorUtil.getColumnIndexOrThrow(_cursor, "mealType");
          final int _cursorIndexOfContext = CursorUtil.getColumnIndexOrThrow(_cursor, "context");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfModifiedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "modifiedAt");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isDeleted");
          final int _cursorIndexOfDeletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "deletedAt");
          final List<FoodEntry> _result = new ArrayList<FoodEntry>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final FoodEntry _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final Instant _tmpTimestamp;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfTimestamp)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfTimestamp);
            }
            _tmpTimestamp = __instantConverter.toInstant(_tmp_2);
            final String _tmpTimezone;
            if (_cursor.isNull(_cursorIndexOfTimezone)) {
              _tmpTimezone = null;
            } else {
              _tmpTimezone = _cursor.getString(_cursorIndexOfTimezone);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final List<String> _tmpIngredients;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfIngredients)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfIngredients);
            }
            _tmpIngredients = __stringListConverter.toStringList(_tmp_3);
            final float _tmpPortions;
            _tmpPortions = _cursor.getFloat(_cursorIndexOfPortions);
            final String _tmpPortionUnit;
            if (_cursor.isNull(_cursorIndexOfPortionUnit)) {
              _tmpPortionUnit = null;
            } else {
              _tmpPortionUnit = _cursor.getString(_cursorIndexOfPortionUnit);
            }
            final String _tmpPreparationMethod;
            if (_cursor.isNull(_cursorIndexOfPreparationMethod)) {
              _tmpPreparationMethod = null;
            } else {
              _tmpPreparationMethod = _cursor.getString(_cursorIndexOfPreparationMethod);
            }
            final MealType _tmpMealType;
            _tmpMealType = __MealType_stringToEnum(_cursor.getString(_cursorIndexOfMealType));
            final ConsumptionContext _tmpContext;
            final String _tmp_4;
            if (_cursor.isNull(_cursorIndexOfContext)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getString(_cursorIndexOfContext);
            }
            _tmpContext = __consumptionContextConverter.toConsumptionContext(_tmp_4);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final Instant _tmpCreatedAt;
            final Long _tmp_5;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            _tmpCreatedAt = __instantConverter.toInstant(_tmp_5);
            final Instant _tmpModifiedAt;
            final Long _tmp_6;
            if (_cursor.isNull(_cursorIndexOfModifiedAt)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getLong(_cursorIndexOfModifiedAt);
            }
            _tmpModifiedAt = __instantConverter.toInstant(_tmp_6);
            final boolean _tmpIsDeleted;
            final int _tmp_7;
            _tmp_7 = _cursor.getInt(_cursorIndexOfIsDeleted);
            _tmpIsDeleted = _tmp_7 != 0;
            final Instant _tmpDeletedAt;
            final Long _tmp_8;
            if (_cursor.isNull(_cursorIndexOfDeletedAt)) {
              _tmp_8 = null;
            } else {
              _tmp_8 = _cursor.getLong(_cursorIndexOfDeletedAt);
            }
            _tmpDeletedAt = __instantConverter.toInstant(_tmp_8);
            _item = new FoodEntry(_tmpId,_tmpTimestamp,_tmpTimezone,_tmpName,_tmpIngredients,_tmpPortions,_tmpPortionUnit,_tmpPreparationMethod,_tmpMealType,_tmpContext,_tmpNotes,_tmpCreatedAt,_tmpModifiedAt,_tmpIsDeleted,_tmpDeletedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, arg2);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }

  private String __MealType_enumToString(@NonNull final MealType _value) {
    switch (_value) {
      case BREAKFAST: return "BREAKFAST";
      case LUNCH: return "LUNCH";
      case DINNER: return "DINNER";
      case SNACK: return "SNACK";
      case OTHER: return "OTHER";
      default: throw new IllegalArgumentException("Can't convert enum to string, unknown enum value: " + _value);
    }
  }

  private MealType __MealType_stringToEnum(@NonNull final String _value) {
    switch (_value) {
      case "BREAKFAST": return MealType.BREAKFAST;
      case "LUNCH": return MealType.LUNCH;
      case "DINNER": return MealType.DINNER;
      case "SNACK": return MealType.SNACK;
      case "OTHER": return MealType.OTHER;
      default: throw new IllegalArgumentException("Can't convert value to enum, unknown value: " + _value);
    }
  }
}
