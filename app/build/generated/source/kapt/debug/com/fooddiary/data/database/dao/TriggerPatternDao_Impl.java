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
import com.fooddiary.data.database.entities.TriggerPattern;
import com.fooddiary.data.models.SymptomType;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Float;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@SuppressWarnings({"unchecked", "deprecation"})
public final class TriggerPatternDao_Impl implements TriggerPatternDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TriggerPattern> __insertionAdapterOfTriggerPattern;

  private final EntityDeletionOrUpdateAdapter<TriggerPattern> __updateAdapterOfTriggerPattern;

  private final SharedSQLiteStatement __preparedStmtOfDelete;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public TriggerPatternDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTriggerPattern = new EntityInsertionAdapter<TriggerPattern>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `trigger_patterns` (`id`,`foodName`,`symptomType`,`correlationStrength`,`averageTimeOffsetMinutes`,`occurrences`,`confidence`,`lastCalculated`,`pValue`,`standardDeviation`,`minTimeOffset`,`maxTimeOffset`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TriggerPattern entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getFoodName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getFoodName());
        }
        statement.bindString(3, __SymptomType_enumToString(entity.getSymptomType()));
        statement.bindDouble(4, entity.getCorrelationStrength());
        statement.bindLong(5, entity.getAverageTimeOffsetMinutes());
        statement.bindLong(6, entity.getOccurrences());
        statement.bindDouble(7, entity.getConfidence());
        statement.bindLong(8, entity.getLastCalculated());
        if (entity.getPValue() == null) {
          statement.bindNull(9);
        } else {
          statement.bindDouble(9, entity.getPValue());
        }
        if (entity.getStandardDeviation() == null) {
          statement.bindNull(10);
        } else {
          statement.bindDouble(10, entity.getStandardDeviation());
        }
        if (entity.getMinTimeOffset() == null) {
          statement.bindNull(11);
        } else {
          statement.bindLong(11, entity.getMinTimeOffset());
        }
        if (entity.getMaxTimeOffset() == null) {
          statement.bindNull(12);
        } else {
          statement.bindLong(12, entity.getMaxTimeOffset());
        }
      }
    };
    this.__updateAdapterOfTriggerPattern = new EntityDeletionOrUpdateAdapter<TriggerPattern>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `trigger_patterns` SET `id` = ?,`foodName` = ?,`symptomType` = ?,`correlationStrength` = ?,`averageTimeOffsetMinutes` = ?,`occurrences` = ?,`confidence` = ?,`lastCalculated` = ?,`pValue` = ?,`standardDeviation` = ?,`minTimeOffset` = ?,`maxTimeOffset` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TriggerPattern entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getFoodName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getFoodName());
        }
        statement.bindString(3, __SymptomType_enumToString(entity.getSymptomType()));
        statement.bindDouble(4, entity.getCorrelationStrength());
        statement.bindLong(5, entity.getAverageTimeOffsetMinutes());
        statement.bindLong(6, entity.getOccurrences());
        statement.bindDouble(7, entity.getConfidence());
        statement.bindLong(8, entity.getLastCalculated());
        if (entity.getPValue() == null) {
          statement.bindNull(9);
        } else {
          statement.bindDouble(9, entity.getPValue());
        }
        if (entity.getStandardDeviation() == null) {
          statement.bindNull(10);
        } else {
          statement.bindDouble(10, entity.getStandardDeviation());
        }
        if (entity.getMinTimeOffset() == null) {
          statement.bindNull(11);
        } else {
          statement.bindLong(11, entity.getMinTimeOffset());
        }
        if (entity.getMaxTimeOffset() == null) {
          statement.bindNull(12);
        } else {
          statement.bindLong(12, entity.getMaxTimeOffset());
        }
        if (entity.getId() == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, entity.getId());
        }
      }
    };
    this.__preparedStmtOfDelete = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM trigger_patterns WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM trigger_patterns";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final TriggerPattern pattern, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfTriggerPattern.insert(pattern);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final TriggerPattern pattern, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfTriggerPattern.handle(pattern);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final String id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDelete.acquire();
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
          __preparedStmtOfDelete.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAll(final Continuation<? super Unit> $completion) {
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
    }, $completion);
  }

  @Override
  public Flow<List<TriggerPattern>> getAll() {
    final String _sql = "SELECT * FROM trigger_patterns ORDER BY correlationStrength DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"trigger_patterns"}, new Callable<List<TriggerPattern>>() {
      @Override
      @NonNull
      public List<TriggerPattern> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfFoodName = CursorUtil.getColumnIndexOrThrow(_cursor, "foodName");
          final int _cursorIndexOfSymptomType = CursorUtil.getColumnIndexOrThrow(_cursor, "symptomType");
          final int _cursorIndexOfCorrelationStrength = CursorUtil.getColumnIndexOrThrow(_cursor, "correlationStrength");
          final int _cursorIndexOfAverageTimeOffsetMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "averageTimeOffsetMinutes");
          final int _cursorIndexOfOccurrences = CursorUtil.getColumnIndexOrThrow(_cursor, "occurrences");
          final int _cursorIndexOfConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "confidence");
          final int _cursorIndexOfLastCalculated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastCalculated");
          final int _cursorIndexOfPValue = CursorUtil.getColumnIndexOrThrow(_cursor, "pValue");
          final int _cursorIndexOfStandardDeviation = CursorUtil.getColumnIndexOrThrow(_cursor, "standardDeviation");
          final int _cursorIndexOfMinTimeOffset = CursorUtil.getColumnIndexOrThrow(_cursor, "minTimeOffset");
          final int _cursorIndexOfMaxTimeOffset = CursorUtil.getColumnIndexOrThrow(_cursor, "maxTimeOffset");
          final List<TriggerPattern> _result = new ArrayList<TriggerPattern>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TriggerPattern _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpFoodName;
            if (_cursor.isNull(_cursorIndexOfFoodName)) {
              _tmpFoodName = null;
            } else {
              _tmpFoodName = _cursor.getString(_cursorIndexOfFoodName);
            }
            final SymptomType _tmpSymptomType;
            _tmpSymptomType = __SymptomType_stringToEnum(_cursor.getString(_cursorIndexOfSymptomType));
            final float _tmpCorrelationStrength;
            _tmpCorrelationStrength = _cursor.getFloat(_cursorIndexOfCorrelationStrength);
            final int _tmpAverageTimeOffsetMinutes;
            _tmpAverageTimeOffsetMinutes = _cursor.getInt(_cursorIndexOfAverageTimeOffsetMinutes);
            final int _tmpOccurrences;
            _tmpOccurrences = _cursor.getInt(_cursorIndexOfOccurrences);
            final float _tmpConfidence;
            _tmpConfidence = _cursor.getFloat(_cursorIndexOfConfidence);
            final long _tmpLastCalculated;
            _tmpLastCalculated = _cursor.getLong(_cursorIndexOfLastCalculated);
            final Float _tmpPValue;
            if (_cursor.isNull(_cursorIndexOfPValue)) {
              _tmpPValue = null;
            } else {
              _tmpPValue = _cursor.getFloat(_cursorIndexOfPValue);
            }
            final Float _tmpStandardDeviation;
            if (_cursor.isNull(_cursorIndexOfStandardDeviation)) {
              _tmpStandardDeviation = null;
            } else {
              _tmpStandardDeviation = _cursor.getFloat(_cursorIndexOfStandardDeviation);
            }
            final Integer _tmpMinTimeOffset;
            if (_cursor.isNull(_cursorIndexOfMinTimeOffset)) {
              _tmpMinTimeOffset = null;
            } else {
              _tmpMinTimeOffset = _cursor.getInt(_cursorIndexOfMinTimeOffset);
            }
            final Integer _tmpMaxTimeOffset;
            if (_cursor.isNull(_cursorIndexOfMaxTimeOffset)) {
              _tmpMaxTimeOffset = null;
            } else {
              _tmpMaxTimeOffset = _cursor.getInt(_cursorIndexOfMaxTimeOffset);
            }
            _item = new TriggerPattern(_tmpId,_tmpFoodName,_tmpSymptomType,_tmpCorrelationStrength,_tmpAverageTimeOffsetMinutes,_tmpOccurrences,_tmpConfidence,_tmpLastCalculated,_tmpPValue,_tmpStandardDeviation,_tmpMinTimeOffset,_tmpMaxTimeOffset);
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
  public Flow<List<TriggerPattern>> getBySymptomType(final SymptomType type) {
    final String _sql = "SELECT * FROM trigger_patterns WHERE symptomType = ? ORDER BY correlationStrength DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, __SymptomType_enumToString(type));
    return CoroutinesRoom.createFlow(__db, false, new String[] {"trigger_patterns"}, new Callable<List<TriggerPattern>>() {
      @Override
      @NonNull
      public List<TriggerPattern> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfFoodName = CursorUtil.getColumnIndexOrThrow(_cursor, "foodName");
          final int _cursorIndexOfSymptomType = CursorUtil.getColumnIndexOrThrow(_cursor, "symptomType");
          final int _cursorIndexOfCorrelationStrength = CursorUtil.getColumnIndexOrThrow(_cursor, "correlationStrength");
          final int _cursorIndexOfAverageTimeOffsetMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "averageTimeOffsetMinutes");
          final int _cursorIndexOfOccurrences = CursorUtil.getColumnIndexOrThrow(_cursor, "occurrences");
          final int _cursorIndexOfConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "confidence");
          final int _cursorIndexOfLastCalculated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastCalculated");
          final int _cursorIndexOfPValue = CursorUtil.getColumnIndexOrThrow(_cursor, "pValue");
          final int _cursorIndexOfStandardDeviation = CursorUtil.getColumnIndexOrThrow(_cursor, "standardDeviation");
          final int _cursorIndexOfMinTimeOffset = CursorUtil.getColumnIndexOrThrow(_cursor, "minTimeOffset");
          final int _cursorIndexOfMaxTimeOffset = CursorUtil.getColumnIndexOrThrow(_cursor, "maxTimeOffset");
          final List<TriggerPattern> _result = new ArrayList<TriggerPattern>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TriggerPattern _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpFoodName;
            if (_cursor.isNull(_cursorIndexOfFoodName)) {
              _tmpFoodName = null;
            } else {
              _tmpFoodName = _cursor.getString(_cursorIndexOfFoodName);
            }
            final SymptomType _tmpSymptomType;
            _tmpSymptomType = __SymptomType_stringToEnum(_cursor.getString(_cursorIndexOfSymptomType));
            final float _tmpCorrelationStrength;
            _tmpCorrelationStrength = _cursor.getFloat(_cursorIndexOfCorrelationStrength);
            final int _tmpAverageTimeOffsetMinutes;
            _tmpAverageTimeOffsetMinutes = _cursor.getInt(_cursorIndexOfAverageTimeOffsetMinutes);
            final int _tmpOccurrences;
            _tmpOccurrences = _cursor.getInt(_cursorIndexOfOccurrences);
            final float _tmpConfidence;
            _tmpConfidence = _cursor.getFloat(_cursorIndexOfConfidence);
            final long _tmpLastCalculated;
            _tmpLastCalculated = _cursor.getLong(_cursorIndexOfLastCalculated);
            final Float _tmpPValue;
            if (_cursor.isNull(_cursorIndexOfPValue)) {
              _tmpPValue = null;
            } else {
              _tmpPValue = _cursor.getFloat(_cursorIndexOfPValue);
            }
            final Float _tmpStandardDeviation;
            if (_cursor.isNull(_cursorIndexOfStandardDeviation)) {
              _tmpStandardDeviation = null;
            } else {
              _tmpStandardDeviation = _cursor.getFloat(_cursorIndexOfStandardDeviation);
            }
            final Integer _tmpMinTimeOffset;
            if (_cursor.isNull(_cursorIndexOfMinTimeOffset)) {
              _tmpMinTimeOffset = null;
            } else {
              _tmpMinTimeOffset = _cursor.getInt(_cursorIndexOfMinTimeOffset);
            }
            final Integer _tmpMaxTimeOffset;
            if (_cursor.isNull(_cursorIndexOfMaxTimeOffset)) {
              _tmpMaxTimeOffset = null;
            } else {
              _tmpMaxTimeOffset = _cursor.getInt(_cursorIndexOfMaxTimeOffset);
            }
            _item = new TriggerPattern(_tmpId,_tmpFoodName,_tmpSymptomType,_tmpCorrelationStrength,_tmpAverageTimeOffsetMinutes,_tmpOccurrences,_tmpConfidence,_tmpLastCalculated,_tmpPValue,_tmpStandardDeviation,_tmpMinTimeOffset,_tmpMaxTimeOffset);
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
  public Flow<List<TriggerPattern>> getByFood(final String foodName) {
    final String _sql = "SELECT * FROM trigger_patterns WHERE foodName = ? ORDER BY correlationStrength DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (foodName == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, foodName);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"trigger_patterns"}, new Callable<List<TriggerPattern>>() {
      @Override
      @NonNull
      public List<TriggerPattern> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfFoodName = CursorUtil.getColumnIndexOrThrow(_cursor, "foodName");
          final int _cursorIndexOfSymptomType = CursorUtil.getColumnIndexOrThrow(_cursor, "symptomType");
          final int _cursorIndexOfCorrelationStrength = CursorUtil.getColumnIndexOrThrow(_cursor, "correlationStrength");
          final int _cursorIndexOfAverageTimeOffsetMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "averageTimeOffsetMinutes");
          final int _cursorIndexOfOccurrences = CursorUtil.getColumnIndexOrThrow(_cursor, "occurrences");
          final int _cursorIndexOfConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "confidence");
          final int _cursorIndexOfLastCalculated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastCalculated");
          final int _cursorIndexOfPValue = CursorUtil.getColumnIndexOrThrow(_cursor, "pValue");
          final int _cursorIndexOfStandardDeviation = CursorUtil.getColumnIndexOrThrow(_cursor, "standardDeviation");
          final int _cursorIndexOfMinTimeOffset = CursorUtil.getColumnIndexOrThrow(_cursor, "minTimeOffset");
          final int _cursorIndexOfMaxTimeOffset = CursorUtil.getColumnIndexOrThrow(_cursor, "maxTimeOffset");
          final List<TriggerPattern> _result = new ArrayList<TriggerPattern>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TriggerPattern _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpFoodName;
            if (_cursor.isNull(_cursorIndexOfFoodName)) {
              _tmpFoodName = null;
            } else {
              _tmpFoodName = _cursor.getString(_cursorIndexOfFoodName);
            }
            final SymptomType _tmpSymptomType;
            _tmpSymptomType = __SymptomType_stringToEnum(_cursor.getString(_cursorIndexOfSymptomType));
            final float _tmpCorrelationStrength;
            _tmpCorrelationStrength = _cursor.getFloat(_cursorIndexOfCorrelationStrength);
            final int _tmpAverageTimeOffsetMinutes;
            _tmpAverageTimeOffsetMinutes = _cursor.getInt(_cursorIndexOfAverageTimeOffsetMinutes);
            final int _tmpOccurrences;
            _tmpOccurrences = _cursor.getInt(_cursorIndexOfOccurrences);
            final float _tmpConfidence;
            _tmpConfidence = _cursor.getFloat(_cursorIndexOfConfidence);
            final long _tmpLastCalculated;
            _tmpLastCalculated = _cursor.getLong(_cursorIndexOfLastCalculated);
            final Float _tmpPValue;
            if (_cursor.isNull(_cursorIndexOfPValue)) {
              _tmpPValue = null;
            } else {
              _tmpPValue = _cursor.getFloat(_cursorIndexOfPValue);
            }
            final Float _tmpStandardDeviation;
            if (_cursor.isNull(_cursorIndexOfStandardDeviation)) {
              _tmpStandardDeviation = null;
            } else {
              _tmpStandardDeviation = _cursor.getFloat(_cursorIndexOfStandardDeviation);
            }
            final Integer _tmpMinTimeOffset;
            if (_cursor.isNull(_cursorIndexOfMinTimeOffset)) {
              _tmpMinTimeOffset = null;
            } else {
              _tmpMinTimeOffset = _cursor.getInt(_cursorIndexOfMinTimeOffset);
            }
            final Integer _tmpMaxTimeOffset;
            if (_cursor.isNull(_cursorIndexOfMaxTimeOffset)) {
              _tmpMaxTimeOffset = null;
            } else {
              _tmpMaxTimeOffset = _cursor.getInt(_cursorIndexOfMaxTimeOffset);
            }
            _item = new TriggerPattern(_tmpId,_tmpFoodName,_tmpSymptomType,_tmpCorrelationStrength,_tmpAverageTimeOffsetMinutes,_tmpOccurrences,_tmpConfidence,_tmpLastCalculated,_tmpPValue,_tmpStandardDeviation,_tmpMinTimeOffset,_tmpMaxTimeOffset);
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
  public Flow<List<TriggerPattern>> getHighConfidence(final float minConfidence) {
    final String _sql = "SELECT * FROM trigger_patterns WHERE correlationStrength >= ? ORDER BY correlationStrength DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindDouble(_argIndex, minConfidence);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"trigger_patterns"}, new Callable<List<TriggerPattern>>() {
      @Override
      @NonNull
      public List<TriggerPattern> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfFoodName = CursorUtil.getColumnIndexOrThrow(_cursor, "foodName");
          final int _cursorIndexOfSymptomType = CursorUtil.getColumnIndexOrThrow(_cursor, "symptomType");
          final int _cursorIndexOfCorrelationStrength = CursorUtil.getColumnIndexOrThrow(_cursor, "correlationStrength");
          final int _cursorIndexOfAverageTimeOffsetMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "averageTimeOffsetMinutes");
          final int _cursorIndexOfOccurrences = CursorUtil.getColumnIndexOrThrow(_cursor, "occurrences");
          final int _cursorIndexOfConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "confidence");
          final int _cursorIndexOfLastCalculated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastCalculated");
          final int _cursorIndexOfPValue = CursorUtil.getColumnIndexOrThrow(_cursor, "pValue");
          final int _cursorIndexOfStandardDeviation = CursorUtil.getColumnIndexOrThrow(_cursor, "standardDeviation");
          final int _cursorIndexOfMinTimeOffset = CursorUtil.getColumnIndexOrThrow(_cursor, "minTimeOffset");
          final int _cursorIndexOfMaxTimeOffset = CursorUtil.getColumnIndexOrThrow(_cursor, "maxTimeOffset");
          final List<TriggerPattern> _result = new ArrayList<TriggerPattern>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TriggerPattern _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpFoodName;
            if (_cursor.isNull(_cursorIndexOfFoodName)) {
              _tmpFoodName = null;
            } else {
              _tmpFoodName = _cursor.getString(_cursorIndexOfFoodName);
            }
            final SymptomType _tmpSymptomType;
            _tmpSymptomType = __SymptomType_stringToEnum(_cursor.getString(_cursorIndexOfSymptomType));
            final float _tmpCorrelationStrength;
            _tmpCorrelationStrength = _cursor.getFloat(_cursorIndexOfCorrelationStrength);
            final int _tmpAverageTimeOffsetMinutes;
            _tmpAverageTimeOffsetMinutes = _cursor.getInt(_cursorIndexOfAverageTimeOffsetMinutes);
            final int _tmpOccurrences;
            _tmpOccurrences = _cursor.getInt(_cursorIndexOfOccurrences);
            final float _tmpConfidence;
            _tmpConfidence = _cursor.getFloat(_cursorIndexOfConfidence);
            final long _tmpLastCalculated;
            _tmpLastCalculated = _cursor.getLong(_cursorIndexOfLastCalculated);
            final Float _tmpPValue;
            if (_cursor.isNull(_cursorIndexOfPValue)) {
              _tmpPValue = null;
            } else {
              _tmpPValue = _cursor.getFloat(_cursorIndexOfPValue);
            }
            final Float _tmpStandardDeviation;
            if (_cursor.isNull(_cursorIndexOfStandardDeviation)) {
              _tmpStandardDeviation = null;
            } else {
              _tmpStandardDeviation = _cursor.getFloat(_cursorIndexOfStandardDeviation);
            }
            final Integer _tmpMinTimeOffset;
            if (_cursor.isNull(_cursorIndexOfMinTimeOffset)) {
              _tmpMinTimeOffset = null;
            } else {
              _tmpMinTimeOffset = _cursor.getInt(_cursorIndexOfMinTimeOffset);
            }
            final Integer _tmpMaxTimeOffset;
            if (_cursor.isNull(_cursorIndexOfMaxTimeOffset)) {
              _tmpMaxTimeOffset = null;
            } else {
              _tmpMaxTimeOffset = _cursor.getInt(_cursorIndexOfMaxTimeOffset);
            }
            _item = new TriggerPattern(_tmpId,_tmpFoodName,_tmpSymptomType,_tmpCorrelationStrength,_tmpAverageTimeOffsetMinutes,_tmpOccurrences,_tmpConfidence,_tmpLastCalculated,_tmpPValue,_tmpStandardDeviation,_tmpMinTimeOffset,_tmpMaxTimeOffset);
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
  public Flow<List<TriggerPattern>> getStatisticallySignificant() {
    final String _sql = "\n"
            + "        SELECT * FROM trigger_patterns\n"
            + "        WHERE occurrences >= 10\n"
            + "        AND correlationStrength >= 0.6\n"
            + "        AND confidence >= 0.95\n"
            + "        AND pValue < 0.05\n"
            + "        ORDER BY correlationStrength DESC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"trigger_patterns"}, new Callable<List<TriggerPattern>>() {
      @Override
      @NonNull
      public List<TriggerPattern> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfFoodName = CursorUtil.getColumnIndexOrThrow(_cursor, "foodName");
          final int _cursorIndexOfSymptomType = CursorUtil.getColumnIndexOrThrow(_cursor, "symptomType");
          final int _cursorIndexOfCorrelationStrength = CursorUtil.getColumnIndexOrThrow(_cursor, "correlationStrength");
          final int _cursorIndexOfAverageTimeOffsetMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "averageTimeOffsetMinutes");
          final int _cursorIndexOfOccurrences = CursorUtil.getColumnIndexOrThrow(_cursor, "occurrences");
          final int _cursorIndexOfConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "confidence");
          final int _cursorIndexOfLastCalculated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastCalculated");
          final int _cursorIndexOfPValue = CursorUtil.getColumnIndexOrThrow(_cursor, "pValue");
          final int _cursorIndexOfStandardDeviation = CursorUtil.getColumnIndexOrThrow(_cursor, "standardDeviation");
          final int _cursorIndexOfMinTimeOffset = CursorUtil.getColumnIndexOrThrow(_cursor, "minTimeOffset");
          final int _cursorIndexOfMaxTimeOffset = CursorUtil.getColumnIndexOrThrow(_cursor, "maxTimeOffset");
          final List<TriggerPattern> _result = new ArrayList<TriggerPattern>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final TriggerPattern _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpFoodName;
            if (_cursor.isNull(_cursorIndexOfFoodName)) {
              _tmpFoodName = null;
            } else {
              _tmpFoodName = _cursor.getString(_cursorIndexOfFoodName);
            }
            final SymptomType _tmpSymptomType;
            _tmpSymptomType = __SymptomType_stringToEnum(_cursor.getString(_cursorIndexOfSymptomType));
            final float _tmpCorrelationStrength;
            _tmpCorrelationStrength = _cursor.getFloat(_cursorIndexOfCorrelationStrength);
            final int _tmpAverageTimeOffsetMinutes;
            _tmpAverageTimeOffsetMinutes = _cursor.getInt(_cursorIndexOfAverageTimeOffsetMinutes);
            final int _tmpOccurrences;
            _tmpOccurrences = _cursor.getInt(_cursorIndexOfOccurrences);
            final float _tmpConfidence;
            _tmpConfidence = _cursor.getFloat(_cursorIndexOfConfidence);
            final long _tmpLastCalculated;
            _tmpLastCalculated = _cursor.getLong(_cursorIndexOfLastCalculated);
            final Float _tmpPValue;
            if (_cursor.isNull(_cursorIndexOfPValue)) {
              _tmpPValue = null;
            } else {
              _tmpPValue = _cursor.getFloat(_cursorIndexOfPValue);
            }
            final Float _tmpStandardDeviation;
            if (_cursor.isNull(_cursorIndexOfStandardDeviation)) {
              _tmpStandardDeviation = null;
            } else {
              _tmpStandardDeviation = _cursor.getFloat(_cursorIndexOfStandardDeviation);
            }
            final Integer _tmpMinTimeOffset;
            if (_cursor.isNull(_cursorIndexOfMinTimeOffset)) {
              _tmpMinTimeOffset = null;
            } else {
              _tmpMinTimeOffset = _cursor.getInt(_cursorIndexOfMinTimeOffset);
            }
            final Integer _tmpMaxTimeOffset;
            if (_cursor.isNull(_cursorIndexOfMaxTimeOffset)) {
              _tmpMaxTimeOffset = null;
            } else {
              _tmpMaxTimeOffset = _cursor.getInt(_cursorIndexOfMaxTimeOffset);
            }
            _item = new TriggerPattern(_tmpId,_tmpFoodName,_tmpSymptomType,_tmpCorrelationStrength,_tmpAverageTimeOffsetMinutes,_tmpOccurrences,_tmpConfidence,_tmpLastCalculated,_tmpPValue,_tmpStandardDeviation,_tmpMinTimeOffset,_tmpMaxTimeOffset);
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
  public Object getById(final String id, final Continuation<? super TriggerPattern> $completion) {
    final String _sql = "SELECT * FROM trigger_patterns WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, id);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<TriggerPattern>() {
      @Override
      @Nullable
      public TriggerPattern call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfFoodName = CursorUtil.getColumnIndexOrThrow(_cursor, "foodName");
          final int _cursorIndexOfSymptomType = CursorUtil.getColumnIndexOrThrow(_cursor, "symptomType");
          final int _cursorIndexOfCorrelationStrength = CursorUtil.getColumnIndexOrThrow(_cursor, "correlationStrength");
          final int _cursorIndexOfAverageTimeOffsetMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "averageTimeOffsetMinutes");
          final int _cursorIndexOfOccurrences = CursorUtil.getColumnIndexOrThrow(_cursor, "occurrences");
          final int _cursorIndexOfConfidence = CursorUtil.getColumnIndexOrThrow(_cursor, "confidence");
          final int _cursorIndexOfLastCalculated = CursorUtil.getColumnIndexOrThrow(_cursor, "lastCalculated");
          final int _cursorIndexOfPValue = CursorUtil.getColumnIndexOrThrow(_cursor, "pValue");
          final int _cursorIndexOfStandardDeviation = CursorUtil.getColumnIndexOrThrow(_cursor, "standardDeviation");
          final int _cursorIndexOfMinTimeOffset = CursorUtil.getColumnIndexOrThrow(_cursor, "minTimeOffset");
          final int _cursorIndexOfMaxTimeOffset = CursorUtil.getColumnIndexOrThrow(_cursor, "maxTimeOffset");
          final TriggerPattern _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpFoodName;
            if (_cursor.isNull(_cursorIndexOfFoodName)) {
              _tmpFoodName = null;
            } else {
              _tmpFoodName = _cursor.getString(_cursorIndexOfFoodName);
            }
            final SymptomType _tmpSymptomType;
            _tmpSymptomType = __SymptomType_stringToEnum(_cursor.getString(_cursorIndexOfSymptomType));
            final float _tmpCorrelationStrength;
            _tmpCorrelationStrength = _cursor.getFloat(_cursorIndexOfCorrelationStrength);
            final int _tmpAverageTimeOffsetMinutes;
            _tmpAverageTimeOffsetMinutes = _cursor.getInt(_cursorIndexOfAverageTimeOffsetMinutes);
            final int _tmpOccurrences;
            _tmpOccurrences = _cursor.getInt(_cursorIndexOfOccurrences);
            final float _tmpConfidence;
            _tmpConfidence = _cursor.getFloat(_cursorIndexOfConfidence);
            final long _tmpLastCalculated;
            _tmpLastCalculated = _cursor.getLong(_cursorIndexOfLastCalculated);
            final Float _tmpPValue;
            if (_cursor.isNull(_cursorIndexOfPValue)) {
              _tmpPValue = null;
            } else {
              _tmpPValue = _cursor.getFloat(_cursorIndexOfPValue);
            }
            final Float _tmpStandardDeviation;
            if (_cursor.isNull(_cursorIndexOfStandardDeviation)) {
              _tmpStandardDeviation = null;
            } else {
              _tmpStandardDeviation = _cursor.getFloat(_cursorIndexOfStandardDeviation);
            }
            final Integer _tmpMinTimeOffset;
            if (_cursor.isNull(_cursorIndexOfMinTimeOffset)) {
              _tmpMinTimeOffset = null;
            } else {
              _tmpMinTimeOffset = _cursor.getInt(_cursorIndexOfMinTimeOffset);
            }
            final Integer _tmpMaxTimeOffset;
            if (_cursor.isNull(_cursorIndexOfMaxTimeOffset)) {
              _tmpMaxTimeOffset = null;
            } else {
              _tmpMaxTimeOffset = _cursor.getInt(_cursorIndexOfMaxTimeOffset);
            }
            _result = new TriggerPattern(_tmpId,_tmpFoodName,_tmpSymptomType,_tmpCorrelationStrength,_tmpAverageTimeOffsetMinutes,_tmpOccurrences,_tmpConfidence,_tmpLastCalculated,_tmpPValue,_tmpStandardDeviation,_tmpMinTimeOffset,_tmpMaxTimeOffset);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM trigger_patterns";
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
    }, $completion);
  }

  @Override
  public Object getSignificantCount(final Continuation<? super Integer> $completion) {
    final String _sql = "\n"
            + "        SELECT COUNT(*) FROM trigger_patterns\n"
            + "        WHERE correlationStrength >= 0.6\n"
            + "        AND confidence >= 0.95\n"
            + "        AND occurrences >= 10\n"
            + "    ";
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
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }

  private String __SymptomType_enumToString(@NonNull final SymptomType _value) {
    switch (_value) {
      case BLOATING: return "BLOATING";
      case GAS: return "GAS";
      case PAIN: return "PAIN";
      case CRAMPING: return "CRAMPING";
      case DIARRHEA: return "DIARRHEA";
      case CONSTIPATION: return "CONSTIPATION";
      case NAUSEA: return "NAUSEA";
      case REFLUX: return "REFLUX";
      case HEARTBURN: return "HEARTBURN";
      case FATIGUE: return "FATIGUE";
      case HEADACHE: return "HEADACHE";
      case SKIN_REACTION: return "SKIN_REACTION";
      case JOINT_PAIN: return "JOINT_PAIN";
      case BOWEL_MOVEMENT: return "BOWEL_MOVEMENT";
      case OTHER: return "OTHER";
      default: throw new IllegalArgumentException("Can't convert enum to string, unknown enum value: " + _value);
    }
  }

  private SymptomType __SymptomType_stringToEnum(@NonNull final String _value) {
    switch (_value) {
      case "BLOATING": return SymptomType.BLOATING;
      case "GAS": return SymptomType.GAS;
      case "PAIN": return SymptomType.PAIN;
      case "CRAMPING": return SymptomType.CRAMPING;
      case "DIARRHEA": return SymptomType.DIARRHEA;
      case "CONSTIPATION": return SymptomType.CONSTIPATION;
      case "NAUSEA": return SymptomType.NAUSEA;
      case "REFLUX": return SymptomType.REFLUX;
      case "HEARTBURN": return SymptomType.HEARTBURN;
      case "FATIGUE": return SymptomType.FATIGUE;
      case "HEADACHE": return SymptomType.HEADACHE;
      case "SKIN_REACTION": return SymptomType.SKIN_REACTION;
      case "JOINT_PAIN": return SymptomType.JOINT_PAIN;
      case "BOWEL_MOVEMENT": return SymptomType.BOWEL_MOVEMENT;
      case "OTHER": return SymptomType.OTHER;
      default: throw new IllegalArgumentException("Can't convert value to enum, unknown value: " + _value);
    }
  }
}
