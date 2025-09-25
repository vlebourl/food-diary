package com.fooddiary.data.database.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.fooddiary.data.database.converters.LocalDateConverter;
import com.fooddiary.data.database.entities.EnvironmentalContext;
import com.fooddiary.data.models.ExerciseIntensity;
import com.fooddiary.data.models.MenstrualPhase;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Float;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@SuppressWarnings({"unchecked", "deprecation"})
public final class EnvironmentalContextDao_Impl implements EnvironmentalContextDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<EnvironmentalContext> __insertionAdapterOfEnvironmentalContext;

  private final LocalDateConverter __localDateConverter = new LocalDateConverter();

  private final SharedSQLiteStatement __preparedStmtOfDelete;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public EnvironmentalContextDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfEnvironmentalContext = new EntityInsertionAdapter<EnvironmentalContext>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `environmental_contexts` (`date`,`stressLevel`,`sleepHours`,`sleepQuality`,`exerciseMinutes`,`exerciseType`,`exerciseIntensity`,`menstrualPhase`,`weather`,`location`,`additionalNotes`) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final EnvironmentalContext entity) {
        final Long _tmp = __localDateConverter.fromLocalDate(entity.getDate());
        if (_tmp == null) {
          statement.bindNull(1);
        } else {
          statement.bindLong(1, _tmp);
        }
        statement.bindLong(2, entity.getStressLevel());
        statement.bindDouble(3, entity.getSleepHours());
        statement.bindLong(4, entity.getSleepQuality());
        if (entity.getExerciseMinutes() == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, entity.getExerciseMinutes());
        }
        if (entity.getExerciseType() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getExerciseType());
        }
        if (entity.getExerciseIntensity() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, __ExerciseIntensity_enumToString(entity.getExerciseIntensity()));
        }
        if (entity.getMenstrualPhase() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, __MenstrualPhase_enumToString(entity.getMenstrualPhase()));
        }
        if (entity.getWeather() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getWeather());
        }
        if (entity.getLocation() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getLocation());
        }
        if (entity.getAdditionalNotes() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getAdditionalNotes());
        }
      }
    };
    this.__preparedStmtOfDelete = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM environmental_contexts WHERE date = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM environmental_contexts";
        return _query;
      }
    };
  }

  @Override
  public Object insertOrUpdate(final EnvironmentalContext context,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfEnvironmentalContext.insert(context);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final LocalDate date, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDelete.acquire();
        int _argIndex = 1;
        final Long _tmp = __localDateConverter.fromLocalDate(date);
        if (_tmp == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindLong(_argIndex, _tmp);
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
  public Object getByDate(final LocalDate date,
      final Continuation<? super EnvironmentalContext> $completion) {
    final String _sql = "SELECT * FROM environmental_contexts WHERE date = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final Long _tmp = __localDateConverter.fromLocalDate(date);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<EnvironmentalContext>() {
      @Override
      @Nullable
      public EnvironmentalContext call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfStressLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "stressLevel");
          final int _cursorIndexOfSleepHours = CursorUtil.getColumnIndexOrThrow(_cursor, "sleepHours");
          final int _cursorIndexOfSleepQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "sleepQuality");
          final int _cursorIndexOfExerciseMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "exerciseMinutes");
          final int _cursorIndexOfExerciseType = CursorUtil.getColumnIndexOrThrow(_cursor, "exerciseType");
          final int _cursorIndexOfExerciseIntensity = CursorUtil.getColumnIndexOrThrow(_cursor, "exerciseIntensity");
          final int _cursorIndexOfMenstrualPhase = CursorUtil.getColumnIndexOrThrow(_cursor, "menstrualPhase");
          final int _cursorIndexOfWeather = CursorUtil.getColumnIndexOrThrow(_cursor, "weather");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfAdditionalNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "additionalNotes");
          final EnvironmentalContext _result;
          if (_cursor.moveToFirst()) {
            final LocalDate _tmpDate;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfDate);
            }
            _tmpDate = __localDateConverter.toLocalDate(_tmp_1);
            final int _tmpStressLevel;
            _tmpStressLevel = _cursor.getInt(_cursorIndexOfStressLevel);
            final float _tmpSleepHours;
            _tmpSleepHours = _cursor.getFloat(_cursorIndexOfSleepHours);
            final int _tmpSleepQuality;
            _tmpSleepQuality = _cursor.getInt(_cursorIndexOfSleepQuality);
            final Integer _tmpExerciseMinutes;
            if (_cursor.isNull(_cursorIndexOfExerciseMinutes)) {
              _tmpExerciseMinutes = null;
            } else {
              _tmpExerciseMinutes = _cursor.getInt(_cursorIndexOfExerciseMinutes);
            }
            final String _tmpExerciseType;
            if (_cursor.isNull(_cursorIndexOfExerciseType)) {
              _tmpExerciseType = null;
            } else {
              _tmpExerciseType = _cursor.getString(_cursorIndexOfExerciseType);
            }
            final ExerciseIntensity _tmpExerciseIntensity;
            if (_cursor.isNull(_cursorIndexOfExerciseIntensity)) {
              _tmpExerciseIntensity = null;
            } else {
              _tmpExerciseIntensity = __ExerciseIntensity_stringToEnum(_cursor.getString(_cursorIndexOfExerciseIntensity));
            }
            final MenstrualPhase _tmpMenstrualPhase;
            if (_cursor.isNull(_cursorIndexOfMenstrualPhase)) {
              _tmpMenstrualPhase = null;
            } else {
              _tmpMenstrualPhase = __MenstrualPhase_stringToEnum(_cursor.getString(_cursorIndexOfMenstrualPhase));
            }
            final String _tmpWeather;
            if (_cursor.isNull(_cursorIndexOfWeather)) {
              _tmpWeather = null;
            } else {
              _tmpWeather = _cursor.getString(_cursorIndexOfWeather);
            }
            final String _tmpLocation;
            if (_cursor.isNull(_cursorIndexOfLocation)) {
              _tmpLocation = null;
            } else {
              _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            }
            final String _tmpAdditionalNotes;
            if (_cursor.isNull(_cursorIndexOfAdditionalNotes)) {
              _tmpAdditionalNotes = null;
            } else {
              _tmpAdditionalNotes = _cursor.getString(_cursorIndexOfAdditionalNotes);
            }
            _result = new EnvironmentalContext(_tmpDate,_tmpStressLevel,_tmpSleepHours,_tmpSleepQuality,_tmpExerciseMinutes,_tmpExerciseType,_tmpExerciseIntensity,_tmpMenstrualPhase,_tmpWeather,_tmpLocation,_tmpAdditionalNotes);
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
  public Flow<List<EnvironmentalContext>> getByDateRange(final LocalDate startDate,
      final LocalDate endDate) {
    final String _sql = "\n"
            + "        SELECT * FROM environmental_contexts\n"
            + "        WHERE date BETWEEN ? AND ?\n"
            + "        ORDER BY date DESC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    final Long _tmp = __localDateConverter.fromLocalDate(startDate);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    _argIndex = 2;
    final Long _tmp_1 = __localDateConverter.fromLocalDate(endDate);
    if (_tmp_1 == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp_1);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"environmental_contexts"}, new Callable<List<EnvironmentalContext>>() {
      @Override
      @NonNull
      public List<EnvironmentalContext> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfStressLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "stressLevel");
          final int _cursorIndexOfSleepHours = CursorUtil.getColumnIndexOrThrow(_cursor, "sleepHours");
          final int _cursorIndexOfSleepQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "sleepQuality");
          final int _cursorIndexOfExerciseMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "exerciseMinutes");
          final int _cursorIndexOfExerciseType = CursorUtil.getColumnIndexOrThrow(_cursor, "exerciseType");
          final int _cursorIndexOfExerciseIntensity = CursorUtil.getColumnIndexOrThrow(_cursor, "exerciseIntensity");
          final int _cursorIndexOfMenstrualPhase = CursorUtil.getColumnIndexOrThrow(_cursor, "menstrualPhase");
          final int _cursorIndexOfWeather = CursorUtil.getColumnIndexOrThrow(_cursor, "weather");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfAdditionalNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "additionalNotes");
          final List<EnvironmentalContext> _result = new ArrayList<EnvironmentalContext>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final EnvironmentalContext _item;
            final LocalDate _tmpDate;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfDate);
            }
            _tmpDate = __localDateConverter.toLocalDate(_tmp_2);
            final int _tmpStressLevel;
            _tmpStressLevel = _cursor.getInt(_cursorIndexOfStressLevel);
            final float _tmpSleepHours;
            _tmpSleepHours = _cursor.getFloat(_cursorIndexOfSleepHours);
            final int _tmpSleepQuality;
            _tmpSleepQuality = _cursor.getInt(_cursorIndexOfSleepQuality);
            final Integer _tmpExerciseMinutes;
            if (_cursor.isNull(_cursorIndexOfExerciseMinutes)) {
              _tmpExerciseMinutes = null;
            } else {
              _tmpExerciseMinutes = _cursor.getInt(_cursorIndexOfExerciseMinutes);
            }
            final String _tmpExerciseType;
            if (_cursor.isNull(_cursorIndexOfExerciseType)) {
              _tmpExerciseType = null;
            } else {
              _tmpExerciseType = _cursor.getString(_cursorIndexOfExerciseType);
            }
            final ExerciseIntensity _tmpExerciseIntensity;
            if (_cursor.isNull(_cursorIndexOfExerciseIntensity)) {
              _tmpExerciseIntensity = null;
            } else {
              _tmpExerciseIntensity = __ExerciseIntensity_stringToEnum(_cursor.getString(_cursorIndexOfExerciseIntensity));
            }
            final MenstrualPhase _tmpMenstrualPhase;
            if (_cursor.isNull(_cursorIndexOfMenstrualPhase)) {
              _tmpMenstrualPhase = null;
            } else {
              _tmpMenstrualPhase = __MenstrualPhase_stringToEnum(_cursor.getString(_cursorIndexOfMenstrualPhase));
            }
            final String _tmpWeather;
            if (_cursor.isNull(_cursorIndexOfWeather)) {
              _tmpWeather = null;
            } else {
              _tmpWeather = _cursor.getString(_cursorIndexOfWeather);
            }
            final String _tmpLocation;
            if (_cursor.isNull(_cursorIndexOfLocation)) {
              _tmpLocation = null;
            } else {
              _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            }
            final String _tmpAdditionalNotes;
            if (_cursor.isNull(_cursorIndexOfAdditionalNotes)) {
              _tmpAdditionalNotes = null;
            } else {
              _tmpAdditionalNotes = _cursor.getString(_cursorIndexOfAdditionalNotes);
            }
            _item = new EnvironmentalContext(_tmpDate,_tmpStressLevel,_tmpSleepHours,_tmpSleepQuality,_tmpExerciseMinutes,_tmpExerciseType,_tmpExerciseIntensity,_tmpMenstrualPhase,_tmpWeather,_tmpLocation,_tmpAdditionalNotes);
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
  public Flow<Float> getAverageStress(final int days) {
    final String _sql = "\n"
            + "        SELECT AVG(stressLevel) FROM environmental_contexts\n"
            + "        WHERE date >= date('now', '-' || ? || ' days')\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, days);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"environmental_contexts"}, new Callable<Float>() {
      @Override
      @NonNull
      public Float call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Float _result;
          if (_cursor.moveToFirst()) {
            final Float _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getFloat(0);
            }
            _result = _tmp;
          } else {
            _result = null;
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
  public Flow<Float> getAverageSleep(final int days) {
    final String _sql = "\n"
            + "        SELECT AVG(sleepHours) FROM environmental_contexts\n"
            + "        WHERE date >= date('now', '-' || ? || ' days')\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, days);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"environmental_contexts"}, new Callable<Float>() {
      @Override
      @NonNull
      public Float call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Float _result;
          if (_cursor.moveToFirst()) {
            final Float _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getFloat(0);
            }
            _result = _tmp;
          } else {
            _result = null;
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
  public Flow<Float> getAverageSleepQuality(final int days) {
    final String _sql = "\n"
            + "        SELECT AVG(sleepQuality) FROM environmental_contexts\n"
            + "        WHERE date >= date('now', '-' || ? || ' days')\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, days);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"environmental_contexts"}, new Callable<Float>() {
      @Override
      @NonNull
      public Float call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Float _result;
          if (_cursor.moveToFirst()) {
            final Float _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getFloat(0);
            }
            _result = _tmp;
          } else {
            _result = null;
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
  public Flow<Integer> getTotalExerciseMinutes(final int days) {
    final String _sql = "\n"
            + "        SELECT SUM(COALESCE(exerciseMinutes, 0)) FROM environmental_contexts\n"
            + "        WHERE date >= date('now', '-' || ? || ' days')\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, days);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"environmental_contexts"}, new Callable<Integer>() {
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
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<EnvironmentalContext>> getAll() {
    final String _sql = "SELECT * FROM environmental_contexts ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"environmental_contexts"}, new Callable<List<EnvironmentalContext>>() {
      @Override
      @NonNull
      public List<EnvironmentalContext> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfStressLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "stressLevel");
          final int _cursorIndexOfSleepHours = CursorUtil.getColumnIndexOrThrow(_cursor, "sleepHours");
          final int _cursorIndexOfSleepQuality = CursorUtil.getColumnIndexOrThrow(_cursor, "sleepQuality");
          final int _cursorIndexOfExerciseMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "exerciseMinutes");
          final int _cursorIndexOfExerciseType = CursorUtil.getColumnIndexOrThrow(_cursor, "exerciseType");
          final int _cursorIndexOfExerciseIntensity = CursorUtil.getColumnIndexOrThrow(_cursor, "exerciseIntensity");
          final int _cursorIndexOfMenstrualPhase = CursorUtil.getColumnIndexOrThrow(_cursor, "menstrualPhase");
          final int _cursorIndexOfWeather = CursorUtil.getColumnIndexOrThrow(_cursor, "weather");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfAdditionalNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "additionalNotes");
          final List<EnvironmentalContext> _result = new ArrayList<EnvironmentalContext>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final EnvironmentalContext _item;
            final LocalDate _tmpDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfDate);
            }
            _tmpDate = __localDateConverter.toLocalDate(_tmp);
            final int _tmpStressLevel;
            _tmpStressLevel = _cursor.getInt(_cursorIndexOfStressLevel);
            final float _tmpSleepHours;
            _tmpSleepHours = _cursor.getFloat(_cursorIndexOfSleepHours);
            final int _tmpSleepQuality;
            _tmpSleepQuality = _cursor.getInt(_cursorIndexOfSleepQuality);
            final Integer _tmpExerciseMinutes;
            if (_cursor.isNull(_cursorIndexOfExerciseMinutes)) {
              _tmpExerciseMinutes = null;
            } else {
              _tmpExerciseMinutes = _cursor.getInt(_cursorIndexOfExerciseMinutes);
            }
            final String _tmpExerciseType;
            if (_cursor.isNull(_cursorIndexOfExerciseType)) {
              _tmpExerciseType = null;
            } else {
              _tmpExerciseType = _cursor.getString(_cursorIndexOfExerciseType);
            }
            final ExerciseIntensity _tmpExerciseIntensity;
            if (_cursor.isNull(_cursorIndexOfExerciseIntensity)) {
              _tmpExerciseIntensity = null;
            } else {
              _tmpExerciseIntensity = __ExerciseIntensity_stringToEnum(_cursor.getString(_cursorIndexOfExerciseIntensity));
            }
            final MenstrualPhase _tmpMenstrualPhase;
            if (_cursor.isNull(_cursorIndexOfMenstrualPhase)) {
              _tmpMenstrualPhase = null;
            } else {
              _tmpMenstrualPhase = __MenstrualPhase_stringToEnum(_cursor.getString(_cursorIndexOfMenstrualPhase));
            }
            final String _tmpWeather;
            if (_cursor.isNull(_cursorIndexOfWeather)) {
              _tmpWeather = null;
            } else {
              _tmpWeather = _cursor.getString(_cursorIndexOfWeather);
            }
            final String _tmpLocation;
            if (_cursor.isNull(_cursorIndexOfLocation)) {
              _tmpLocation = null;
            } else {
              _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            }
            final String _tmpAdditionalNotes;
            if (_cursor.isNull(_cursorIndexOfAdditionalNotes)) {
              _tmpAdditionalNotes = null;
            } else {
              _tmpAdditionalNotes = _cursor.getString(_cursorIndexOfAdditionalNotes);
            }
            _item = new EnvironmentalContext(_tmpDate,_tmpStressLevel,_tmpSleepHours,_tmpSleepQuality,_tmpExerciseMinutes,_tmpExerciseType,_tmpExerciseIntensity,_tmpMenstrualPhase,_tmpWeather,_tmpLocation,_tmpAdditionalNotes);
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
  public Object getCount(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM environmental_contexts";
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

  private String __ExerciseIntensity_enumToString(@NonNull final ExerciseIntensity _value) {
    switch (_value) {
      case LOW: return "LOW";
      case MODERATE: return "MODERATE";
      case HIGH: return "HIGH";
      default: throw new IllegalArgumentException("Can't convert enum to string, unknown enum value: " + _value);
    }
  }

  private String __MenstrualPhase_enumToString(@NonNull final MenstrualPhase _value) {
    switch (_value) {
      case MENSTRUAL: return "MENSTRUAL";
      case FOLLICULAR: return "FOLLICULAR";
      case OVULATION: return "OVULATION";
      case LUTEAL: return "LUTEAL";
      default: throw new IllegalArgumentException("Can't convert enum to string, unknown enum value: " + _value);
    }
  }

  private ExerciseIntensity __ExerciseIntensity_stringToEnum(@NonNull final String _value) {
    switch (_value) {
      case "LOW": return ExerciseIntensity.LOW;
      case "MODERATE": return ExerciseIntensity.MODERATE;
      case "HIGH": return ExerciseIntensity.HIGH;
      default: throw new IllegalArgumentException("Can't convert value to enum, unknown value: " + _value);
    }
  }

  private MenstrualPhase __MenstrualPhase_stringToEnum(@NonNull final String _value) {
    switch (_value) {
      case "MENSTRUAL": return MenstrualPhase.MENSTRUAL;
      case "FOLLICULAR": return MenstrualPhase.FOLLICULAR;
      case "OVULATION": return MenstrualPhase.OVULATION;
      case "LUTEAL": return MenstrualPhase.LUTEAL;
      default: throw new IllegalArgumentException("Can't convert value to enum, unknown value: " + _value);
    }
  }
}
