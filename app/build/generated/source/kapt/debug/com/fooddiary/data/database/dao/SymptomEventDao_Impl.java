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
import com.fooddiary.data.database.converters.InstantConverter;
import com.fooddiary.data.database.converters.StringListConverter;
import com.fooddiary.data.database.entities.SymptomEvent;
import com.fooddiary.data.models.SymptomFrequency;
import com.fooddiary.data.models.SymptomType;
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
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@SuppressWarnings({"unchecked", "deprecation"})
public final class SymptomEventDao_Impl implements SymptomEventDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<SymptomEvent> __insertionAdapterOfSymptomEvent;

  private final InstantConverter __instantConverter = new InstantConverter();

  private final StringListConverter __stringListConverter = new StringListConverter();

  private final EntityDeletionOrUpdateAdapter<SymptomEvent> __updateAdapterOfSymptomEvent;

  private final SharedSQLiteStatement __preparedStmtOfSoftDelete;

  private final SharedSQLiteStatement __preparedStmtOfHardDelete;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public SymptomEventDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSymptomEvent = new EntityInsertionAdapter<SymptomEvent>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `symptom_events` (`id`,`timestamp`,`timezone`,`type`,`severity`,`duration`,`location`,`bristolScale`,`suspectedTriggers`,`notes`,`photoPath`,`createdAt`,`modifiedAt`,`isDeleted`,`deletedAt`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SymptomEvent entity) {
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
        statement.bindString(4, __SymptomType_enumToString(entity.getType()));
        statement.bindLong(5, entity.getSeverity());
        if (entity.getDuration() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getDuration());
        }
        if (entity.getLocation() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getLocation());
        }
        if (entity.getBristolScale() == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, entity.getBristolScale());
        }
        final String _tmp_1 = __stringListConverter.fromStringList(entity.getSuspectedTriggers());
        if (_tmp_1 == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, _tmp_1);
        }
        if (entity.getNotes() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getNotes());
        }
        if (entity.getPhotoPath() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getPhotoPath());
        }
        final Long _tmp_2 = __instantConverter.fromInstant(entity.getCreatedAt());
        if (_tmp_2 == null) {
          statement.bindNull(12);
        } else {
          statement.bindLong(12, _tmp_2);
        }
        final Long _tmp_3 = __instantConverter.fromInstant(entity.getModifiedAt());
        if (_tmp_3 == null) {
          statement.bindNull(13);
        } else {
          statement.bindLong(13, _tmp_3);
        }
        final int _tmp_4 = entity.isDeleted() ? 1 : 0;
        statement.bindLong(14, _tmp_4);
        final Long _tmp_5 = __instantConverter.fromInstant(entity.getDeletedAt());
        if (_tmp_5 == null) {
          statement.bindNull(15);
        } else {
          statement.bindLong(15, _tmp_5);
        }
      }
    };
    this.__updateAdapterOfSymptomEvent = new EntityDeletionOrUpdateAdapter<SymptomEvent>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `symptom_events` SET `id` = ?,`timestamp` = ?,`timezone` = ?,`type` = ?,`severity` = ?,`duration` = ?,`location` = ?,`bristolScale` = ?,`suspectedTriggers` = ?,`notes` = ?,`photoPath` = ?,`createdAt` = ?,`modifiedAt` = ?,`isDeleted` = ?,`deletedAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SymptomEvent entity) {
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
        statement.bindString(4, __SymptomType_enumToString(entity.getType()));
        statement.bindLong(5, entity.getSeverity());
        if (entity.getDuration() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getDuration());
        }
        if (entity.getLocation() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getLocation());
        }
        if (entity.getBristolScale() == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, entity.getBristolScale());
        }
        final String _tmp_1 = __stringListConverter.fromStringList(entity.getSuspectedTriggers());
        if (_tmp_1 == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, _tmp_1);
        }
        if (entity.getNotes() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getNotes());
        }
        if (entity.getPhotoPath() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getPhotoPath());
        }
        final Long _tmp_2 = __instantConverter.fromInstant(entity.getCreatedAt());
        if (_tmp_2 == null) {
          statement.bindNull(12);
        } else {
          statement.bindLong(12, _tmp_2);
        }
        final Long _tmp_3 = __instantConverter.fromInstant(entity.getModifiedAt());
        if (_tmp_3 == null) {
          statement.bindNull(13);
        } else {
          statement.bindLong(13, _tmp_3);
        }
        final int _tmp_4 = entity.isDeleted() ? 1 : 0;
        statement.bindLong(14, _tmp_4);
        final Long _tmp_5 = __instantConverter.fromInstant(entity.getDeletedAt());
        if (_tmp_5 == null) {
          statement.bindNull(15);
        } else {
          statement.bindLong(15, _tmp_5);
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
                + "        UPDATE symptom_events\n"
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
        final String _query = "DELETE FROM symptom_events WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM symptom_events";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final SymptomEvent event, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfSymptomEvent.insertAndReturnId(event);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final SymptomEvent event, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfSymptomEvent.handle(event);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object softDelete(final String id, final Instant deletedAt, final Instant modifiedAt,
      final Continuation<? super Unit> $completion) {
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
    }, $completion);
  }

  @Override
  public Object hardDelete(final String id, final Continuation<? super Unit> $completion) {
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
  public Object getById(final String id, final Continuation<? super SymptomEvent> $completion) {
    final String _sql = "SELECT * FROM symptom_events WHERE id = ? AND isDeleted = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, id);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<SymptomEvent>() {
      @Override
      @Nullable
      public SymptomEvent call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfTimezone = CursorUtil.getColumnIndexOrThrow(_cursor, "timezone");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfSeverity = CursorUtil.getColumnIndexOrThrow(_cursor, "severity");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfBristolScale = CursorUtil.getColumnIndexOrThrow(_cursor, "bristolScale");
          final int _cursorIndexOfSuspectedTriggers = CursorUtil.getColumnIndexOrThrow(_cursor, "suspectedTriggers");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfPhotoPath = CursorUtil.getColumnIndexOrThrow(_cursor, "photoPath");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfModifiedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "modifiedAt");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isDeleted");
          final int _cursorIndexOfDeletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "deletedAt");
          final SymptomEvent _result;
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
            final SymptomType _tmpType;
            _tmpType = __SymptomType_stringToEnum(_cursor.getString(_cursorIndexOfType));
            final int _tmpSeverity;
            _tmpSeverity = _cursor.getInt(_cursorIndexOfSeverity);
            final Integer _tmpDuration;
            if (_cursor.isNull(_cursorIndexOfDuration)) {
              _tmpDuration = null;
            } else {
              _tmpDuration = _cursor.getInt(_cursorIndexOfDuration);
            }
            final String _tmpLocation;
            if (_cursor.isNull(_cursorIndexOfLocation)) {
              _tmpLocation = null;
            } else {
              _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            }
            final Integer _tmpBristolScale;
            if (_cursor.isNull(_cursorIndexOfBristolScale)) {
              _tmpBristolScale = null;
            } else {
              _tmpBristolScale = _cursor.getInt(_cursorIndexOfBristolScale);
            }
            final List<String> _tmpSuspectedTriggers;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfSuspectedTriggers)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfSuspectedTriggers);
            }
            _tmpSuspectedTriggers = __stringListConverter.toStringList(_tmp_1);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final String _tmpPhotoPath;
            if (_cursor.isNull(_cursorIndexOfPhotoPath)) {
              _tmpPhotoPath = null;
            } else {
              _tmpPhotoPath = _cursor.getString(_cursorIndexOfPhotoPath);
            }
            final Instant _tmpCreatedAt;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            _tmpCreatedAt = __instantConverter.toInstant(_tmp_2);
            final Instant _tmpModifiedAt;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfModifiedAt)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfModifiedAt);
            }
            _tmpModifiedAt = __instantConverter.toInstant(_tmp_3);
            final boolean _tmpIsDeleted;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsDeleted);
            _tmpIsDeleted = _tmp_4 != 0;
            final Instant _tmpDeletedAt;
            final Long _tmp_5;
            if (_cursor.isNull(_cursorIndexOfDeletedAt)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getLong(_cursorIndexOfDeletedAt);
            }
            _tmpDeletedAt = __instantConverter.toInstant(_tmp_5);
            _result = new SymptomEvent(_tmpId,_tmpTimestamp,_tmpTimezone,_tmpType,_tmpSeverity,_tmpDuration,_tmpLocation,_tmpBristolScale,_tmpSuspectedTriggers,_tmpNotes,_tmpPhotoPath,_tmpCreatedAt,_tmpModifiedAt,_tmpIsDeleted,_tmpDeletedAt);
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
  public Flow<List<SymptomEvent>> getAll() {
    final String _sql = "SELECT * FROM symptom_events WHERE isDeleted = 0 ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"symptom_events"}, new Callable<List<SymptomEvent>>() {
      @Override
      @NonNull
      public List<SymptomEvent> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfTimezone = CursorUtil.getColumnIndexOrThrow(_cursor, "timezone");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfSeverity = CursorUtil.getColumnIndexOrThrow(_cursor, "severity");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfBristolScale = CursorUtil.getColumnIndexOrThrow(_cursor, "bristolScale");
          final int _cursorIndexOfSuspectedTriggers = CursorUtil.getColumnIndexOrThrow(_cursor, "suspectedTriggers");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfPhotoPath = CursorUtil.getColumnIndexOrThrow(_cursor, "photoPath");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfModifiedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "modifiedAt");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isDeleted");
          final int _cursorIndexOfDeletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "deletedAt");
          final List<SymptomEvent> _result = new ArrayList<SymptomEvent>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SymptomEvent _item;
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
            final SymptomType _tmpType;
            _tmpType = __SymptomType_stringToEnum(_cursor.getString(_cursorIndexOfType));
            final int _tmpSeverity;
            _tmpSeverity = _cursor.getInt(_cursorIndexOfSeverity);
            final Integer _tmpDuration;
            if (_cursor.isNull(_cursorIndexOfDuration)) {
              _tmpDuration = null;
            } else {
              _tmpDuration = _cursor.getInt(_cursorIndexOfDuration);
            }
            final String _tmpLocation;
            if (_cursor.isNull(_cursorIndexOfLocation)) {
              _tmpLocation = null;
            } else {
              _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            }
            final Integer _tmpBristolScale;
            if (_cursor.isNull(_cursorIndexOfBristolScale)) {
              _tmpBristolScale = null;
            } else {
              _tmpBristolScale = _cursor.getInt(_cursorIndexOfBristolScale);
            }
            final List<String> _tmpSuspectedTriggers;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfSuspectedTriggers)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfSuspectedTriggers);
            }
            _tmpSuspectedTriggers = __stringListConverter.toStringList(_tmp_1);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final String _tmpPhotoPath;
            if (_cursor.isNull(_cursorIndexOfPhotoPath)) {
              _tmpPhotoPath = null;
            } else {
              _tmpPhotoPath = _cursor.getString(_cursorIndexOfPhotoPath);
            }
            final Instant _tmpCreatedAt;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            _tmpCreatedAt = __instantConverter.toInstant(_tmp_2);
            final Instant _tmpModifiedAt;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfModifiedAt)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfModifiedAt);
            }
            _tmpModifiedAt = __instantConverter.toInstant(_tmp_3);
            final boolean _tmpIsDeleted;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsDeleted);
            _tmpIsDeleted = _tmp_4 != 0;
            final Instant _tmpDeletedAt;
            final Long _tmp_5;
            if (_cursor.isNull(_cursorIndexOfDeletedAt)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getLong(_cursorIndexOfDeletedAt);
            }
            _tmpDeletedAt = __instantConverter.toInstant(_tmp_5);
            _item = new SymptomEvent(_tmpId,_tmpTimestamp,_tmpTimezone,_tmpType,_tmpSeverity,_tmpDuration,_tmpLocation,_tmpBristolScale,_tmpSuspectedTriggers,_tmpNotes,_tmpPhotoPath,_tmpCreatedAt,_tmpModifiedAt,_tmpIsDeleted,_tmpDeletedAt);
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
  public Flow<List<SymptomEvent>> getAllByDateRange(final String startDate, final String endDate) {
    final String _sql = "\n"
            + "        SELECT * FROM symptom_events\n"
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
    return CoroutinesRoom.createFlow(__db, false, new String[] {"symptom_events"}, new Callable<List<SymptomEvent>>() {
      @Override
      @NonNull
      public List<SymptomEvent> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfTimezone = CursorUtil.getColumnIndexOrThrow(_cursor, "timezone");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfSeverity = CursorUtil.getColumnIndexOrThrow(_cursor, "severity");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfBristolScale = CursorUtil.getColumnIndexOrThrow(_cursor, "bristolScale");
          final int _cursorIndexOfSuspectedTriggers = CursorUtil.getColumnIndexOrThrow(_cursor, "suspectedTriggers");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfPhotoPath = CursorUtil.getColumnIndexOrThrow(_cursor, "photoPath");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfModifiedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "modifiedAt");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isDeleted");
          final int _cursorIndexOfDeletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "deletedAt");
          final List<SymptomEvent> _result = new ArrayList<SymptomEvent>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SymptomEvent _item;
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
            final SymptomType _tmpType;
            _tmpType = __SymptomType_stringToEnum(_cursor.getString(_cursorIndexOfType));
            final int _tmpSeverity;
            _tmpSeverity = _cursor.getInt(_cursorIndexOfSeverity);
            final Integer _tmpDuration;
            if (_cursor.isNull(_cursorIndexOfDuration)) {
              _tmpDuration = null;
            } else {
              _tmpDuration = _cursor.getInt(_cursorIndexOfDuration);
            }
            final String _tmpLocation;
            if (_cursor.isNull(_cursorIndexOfLocation)) {
              _tmpLocation = null;
            } else {
              _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            }
            final Integer _tmpBristolScale;
            if (_cursor.isNull(_cursorIndexOfBristolScale)) {
              _tmpBristolScale = null;
            } else {
              _tmpBristolScale = _cursor.getInt(_cursorIndexOfBristolScale);
            }
            final List<String> _tmpSuspectedTriggers;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfSuspectedTriggers)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfSuspectedTriggers);
            }
            _tmpSuspectedTriggers = __stringListConverter.toStringList(_tmp_1);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final String _tmpPhotoPath;
            if (_cursor.isNull(_cursorIndexOfPhotoPath)) {
              _tmpPhotoPath = null;
            } else {
              _tmpPhotoPath = _cursor.getString(_cursorIndexOfPhotoPath);
            }
            final Instant _tmpCreatedAt;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            _tmpCreatedAt = __instantConverter.toInstant(_tmp_2);
            final Instant _tmpModifiedAt;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfModifiedAt)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfModifiedAt);
            }
            _tmpModifiedAt = __instantConverter.toInstant(_tmp_3);
            final boolean _tmpIsDeleted;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsDeleted);
            _tmpIsDeleted = _tmp_4 != 0;
            final Instant _tmpDeletedAt;
            final Long _tmp_5;
            if (_cursor.isNull(_cursorIndexOfDeletedAt)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getLong(_cursorIndexOfDeletedAt);
            }
            _tmpDeletedAt = __instantConverter.toInstant(_tmp_5);
            _item = new SymptomEvent(_tmpId,_tmpTimestamp,_tmpTimezone,_tmpType,_tmpSeverity,_tmpDuration,_tmpLocation,_tmpBristolScale,_tmpSuspectedTriggers,_tmpNotes,_tmpPhotoPath,_tmpCreatedAt,_tmpModifiedAt,_tmpIsDeleted,_tmpDeletedAt);
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
  public Flow<List<SymptomEvent>> getByType(final SymptomType type) {
    final String _sql = "SELECT * FROM symptom_events WHERE type = ? AND isDeleted = 0 ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, __SymptomType_enumToString(type));
    return CoroutinesRoom.createFlow(__db, false, new String[] {"symptom_events"}, new Callable<List<SymptomEvent>>() {
      @Override
      @NonNull
      public List<SymptomEvent> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfTimezone = CursorUtil.getColumnIndexOrThrow(_cursor, "timezone");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfSeverity = CursorUtil.getColumnIndexOrThrow(_cursor, "severity");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfBristolScale = CursorUtil.getColumnIndexOrThrow(_cursor, "bristolScale");
          final int _cursorIndexOfSuspectedTriggers = CursorUtil.getColumnIndexOrThrow(_cursor, "suspectedTriggers");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfPhotoPath = CursorUtil.getColumnIndexOrThrow(_cursor, "photoPath");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfModifiedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "modifiedAt");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isDeleted");
          final int _cursorIndexOfDeletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "deletedAt");
          final List<SymptomEvent> _result = new ArrayList<SymptomEvent>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SymptomEvent _item;
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
            final SymptomType _tmpType;
            _tmpType = __SymptomType_stringToEnum(_cursor.getString(_cursorIndexOfType));
            final int _tmpSeverity;
            _tmpSeverity = _cursor.getInt(_cursorIndexOfSeverity);
            final Integer _tmpDuration;
            if (_cursor.isNull(_cursorIndexOfDuration)) {
              _tmpDuration = null;
            } else {
              _tmpDuration = _cursor.getInt(_cursorIndexOfDuration);
            }
            final String _tmpLocation;
            if (_cursor.isNull(_cursorIndexOfLocation)) {
              _tmpLocation = null;
            } else {
              _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            }
            final Integer _tmpBristolScale;
            if (_cursor.isNull(_cursorIndexOfBristolScale)) {
              _tmpBristolScale = null;
            } else {
              _tmpBristolScale = _cursor.getInt(_cursorIndexOfBristolScale);
            }
            final List<String> _tmpSuspectedTriggers;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfSuspectedTriggers)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfSuspectedTriggers);
            }
            _tmpSuspectedTriggers = __stringListConverter.toStringList(_tmp_1);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final String _tmpPhotoPath;
            if (_cursor.isNull(_cursorIndexOfPhotoPath)) {
              _tmpPhotoPath = null;
            } else {
              _tmpPhotoPath = _cursor.getString(_cursorIndexOfPhotoPath);
            }
            final Instant _tmpCreatedAt;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            _tmpCreatedAt = __instantConverter.toInstant(_tmp_2);
            final Instant _tmpModifiedAt;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfModifiedAt)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfModifiedAt);
            }
            _tmpModifiedAt = __instantConverter.toInstant(_tmp_3);
            final boolean _tmpIsDeleted;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsDeleted);
            _tmpIsDeleted = _tmp_4 != 0;
            final Instant _tmpDeletedAt;
            final Long _tmp_5;
            if (_cursor.isNull(_cursorIndexOfDeletedAt)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getLong(_cursorIndexOfDeletedAt);
            }
            _tmpDeletedAt = __instantConverter.toInstant(_tmp_5);
            _item = new SymptomEvent(_tmpId,_tmpTimestamp,_tmpTimezone,_tmpType,_tmpSeverity,_tmpDuration,_tmpLocation,_tmpBristolScale,_tmpSuspectedTriggers,_tmpNotes,_tmpPhotoPath,_tmpCreatedAt,_tmpModifiedAt,_tmpIsDeleted,_tmpDeletedAt);
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
  public Flow<List<SymptomEvent>> getBySeverity(final int minSeverity) {
    final String _sql = "SELECT * FROM symptom_events WHERE severity >= ? AND isDeleted = 0 ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, minSeverity);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"symptom_events"}, new Callable<List<SymptomEvent>>() {
      @Override
      @NonNull
      public List<SymptomEvent> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfTimezone = CursorUtil.getColumnIndexOrThrow(_cursor, "timezone");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfSeverity = CursorUtil.getColumnIndexOrThrow(_cursor, "severity");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfBristolScale = CursorUtil.getColumnIndexOrThrow(_cursor, "bristolScale");
          final int _cursorIndexOfSuspectedTriggers = CursorUtil.getColumnIndexOrThrow(_cursor, "suspectedTriggers");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfPhotoPath = CursorUtil.getColumnIndexOrThrow(_cursor, "photoPath");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfModifiedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "modifiedAt");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isDeleted");
          final int _cursorIndexOfDeletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "deletedAt");
          final List<SymptomEvent> _result = new ArrayList<SymptomEvent>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SymptomEvent _item;
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
            final SymptomType _tmpType;
            _tmpType = __SymptomType_stringToEnum(_cursor.getString(_cursorIndexOfType));
            final int _tmpSeverity;
            _tmpSeverity = _cursor.getInt(_cursorIndexOfSeverity);
            final Integer _tmpDuration;
            if (_cursor.isNull(_cursorIndexOfDuration)) {
              _tmpDuration = null;
            } else {
              _tmpDuration = _cursor.getInt(_cursorIndexOfDuration);
            }
            final String _tmpLocation;
            if (_cursor.isNull(_cursorIndexOfLocation)) {
              _tmpLocation = null;
            } else {
              _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            }
            final Integer _tmpBristolScale;
            if (_cursor.isNull(_cursorIndexOfBristolScale)) {
              _tmpBristolScale = null;
            } else {
              _tmpBristolScale = _cursor.getInt(_cursorIndexOfBristolScale);
            }
            final List<String> _tmpSuspectedTriggers;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfSuspectedTriggers)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfSuspectedTriggers);
            }
            _tmpSuspectedTriggers = __stringListConverter.toStringList(_tmp_1);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final String _tmpPhotoPath;
            if (_cursor.isNull(_cursorIndexOfPhotoPath)) {
              _tmpPhotoPath = null;
            } else {
              _tmpPhotoPath = _cursor.getString(_cursorIndexOfPhotoPath);
            }
            final Instant _tmpCreatedAt;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            _tmpCreatedAt = __instantConverter.toInstant(_tmp_2);
            final Instant _tmpModifiedAt;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfModifiedAt)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfModifiedAt);
            }
            _tmpModifiedAt = __instantConverter.toInstant(_tmp_3);
            final boolean _tmpIsDeleted;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsDeleted);
            _tmpIsDeleted = _tmp_4 != 0;
            final Instant _tmpDeletedAt;
            final Long _tmp_5;
            if (_cursor.isNull(_cursorIndexOfDeletedAt)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getLong(_cursorIndexOfDeletedAt);
            }
            _tmpDeletedAt = __instantConverter.toInstant(_tmp_5);
            _item = new SymptomEvent(_tmpId,_tmpTimestamp,_tmpTimezone,_tmpType,_tmpSeverity,_tmpDuration,_tmpLocation,_tmpBristolScale,_tmpSuspectedTriggers,_tmpNotes,_tmpPhotoPath,_tmpCreatedAt,_tmpModifiedAt,_tmpIsDeleted,_tmpDeletedAt);
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
  public Flow<List<SymptomEvent>> getActiveSymptoms() {
    final String _sql = "\n"
            + "        SELECT * FROM symptom_events\n"
            + "        WHERE duration IS NULL OR duration > 0\n"
            + "        AND isDeleted = 0\n"
            + "        ORDER BY timestamp DESC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"symptom_events"}, new Callable<List<SymptomEvent>>() {
      @Override
      @NonNull
      public List<SymptomEvent> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfTimezone = CursorUtil.getColumnIndexOrThrow(_cursor, "timezone");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfSeverity = CursorUtil.getColumnIndexOrThrow(_cursor, "severity");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfBristolScale = CursorUtil.getColumnIndexOrThrow(_cursor, "bristolScale");
          final int _cursorIndexOfSuspectedTriggers = CursorUtil.getColumnIndexOrThrow(_cursor, "suspectedTriggers");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfPhotoPath = CursorUtil.getColumnIndexOrThrow(_cursor, "photoPath");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfModifiedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "modifiedAt");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isDeleted");
          final int _cursorIndexOfDeletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "deletedAt");
          final List<SymptomEvent> _result = new ArrayList<SymptomEvent>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SymptomEvent _item;
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
            final SymptomType _tmpType;
            _tmpType = __SymptomType_stringToEnum(_cursor.getString(_cursorIndexOfType));
            final int _tmpSeverity;
            _tmpSeverity = _cursor.getInt(_cursorIndexOfSeverity);
            final Integer _tmpDuration;
            if (_cursor.isNull(_cursorIndexOfDuration)) {
              _tmpDuration = null;
            } else {
              _tmpDuration = _cursor.getInt(_cursorIndexOfDuration);
            }
            final String _tmpLocation;
            if (_cursor.isNull(_cursorIndexOfLocation)) {
              _tmpLocation = null;
            } else {
              _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            }
            final Integer _tmpBristolScale;
            if (_cursor.isNull(_cursorIndexOfBristolScale)) {
              _tmpBristolScale = null;
            } else {
              _tmpBristolScale = _cursor.getInt(_cursorIndexOfBristolScale);
            }
            final List<String> _tmpSuspectedTriggers;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfSuspectedTriggers)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfSuspectedTriggers);
            }
            _tmpSuspectedTriggers = __stringListConverter.toStringList(_tmp_1);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final String _tmpPhotoPath;
            if (_cursor.isNull(_cursorIndexOfPhotoPath)) {
              _tmpPhotoPath = null;
            } else {
              _tmpPhotoPath = _cursor.getString(_cursorIndexOfPhotoPath);
            }
            final Instant _tmpCreatedAt;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            _tmpCreatedAt = __instantConverter.toInstant(_tmp_2);
            final Instant _tmpModifiedAt;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfModifiedAt)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfModifiedAt);
            }
            _tmpModifiedAt = __instantConverter.toInstant(_tmp_3);
            final boolean _tmpIsDeleted;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsDeleted);
            _tmpIsDeleted = _tmp_4 != 0;
            final Instant _tmpDeletedAt;
            final Long _tmp_5;
            if (_cursor.isNull(_cursorIndexOfDeletedAt)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getLong(_cursorIndexOfDeletedAt);
            }
            _tmpDeletedAt = __instantConverter.toInstant(_tmp_5);
            _item = new SymptomEvent(_tmpId,_tmpTimestamp,_tmpTimezone,_tmpType,_tmpSeverity,_tmpDuration,_tmpLocation,_tmpBristolScale,_tmpSuspectedTriggers,_tmpNotes,_tmpPhotoPath,_tmpCreatedAt,_tmpModifiedAt,_tmpIsDeleted,_tmpDeletedAt);
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
  public Object getEventsInTimeWindow(final Instant startTime, final Instant endTime,
      final Continuation<? super List<SymptomEvent>> $completion) {
    final String _sql = "\n"
            + "        SELECT * FROM symptom_events\n"
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
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<SymptomEvent>>() {
      @Override
      @NonNull
      public List<SymptomEvent> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfTimezone = CursorUtil.getColumnIndexOrThrow(_cursor, "timezone");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfSeverity = CursorUtil.getColumnIndexOrThrow(_cursor, "severity");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfBristolScale = CursorUtil.getColumnIndexOrThrow(_cursor, "bristolScale");
          final int _cursorIndexOfSuspectedTriggers = CursorUtil.getColumnIndexOrThrow(_cursor, "suspectedTriggers");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfPhotoPath = CursorUtil.getColumnIndexOrThrow(_cursor, "photoPath");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfModifiedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "modifiedAt");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isDeleted");
          final int _cursorIndexOfDeletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "deletedAt");
          final List<SymptomEvent> _result = new ArrayList<SymptomEvent>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SymptomEvent _item;
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
            final SymptomType _tmpType;
            _tmpType = __SymptomType_stringToEnum(_cursor.getString(_cursorIndexOfType));
            final int _tmpSeverity;
            _tmpSeverity = _cursor.getInt(_cursorIndexOfSeverity);
            final Integer _tmpDuration;
            if (_cursor.isNull(_cursorIndexOfDuration)) {
              _tmpDuration = null;
            } else {
              _tmpDuration = _cursor.getInt(_cursorIndexOfDuration);
            }
            final String _tmpLocation;
            if (_cursor.isNull(_cursorIndexOfLocation)) {
              _tmpLocation = null;
            } else {
              _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            }
            final Integer _tmpBristolScale;
            if (_cursor.isNull(_cursorIndexOfBristolScale)) {
              _tmpBristolScale = null;
            } else {
              _tmpBristolScale = _cursor.getInt(_cursorIndexOfBristolScale);
            }
            final List<String> _tmpSuspectedTriggers;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfSuspectedTriggers)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfSuspectedTriggers);
            }
            _tmpSuspectedTriggers = __stringListConverter.toStringList(_tmp_3);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final String _tmpPhotoPath;
            if (_cursor.isNull(_cursorIndexOfPhotoPath)) {
              _tmpPhotoPath = null;
            } else {
              _tmpPhotoPath = _cursor.getString(_cursorIndexOfPhotoPath);
            }
            final Instant _tmpCreatedAt;
            final Long _tmp_4;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            _tmpCreatedAt = __instantConverter.toInstant(_tmp_4);
            final Instant _tmpModifiedAt;
            final Long _tmp_5;
            if (_cursor.isNull(_cursorIndexOfModifiedAt)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getLong(_cursorIndexOfModifiedAt);
            }
            _tmpModifiedAt = __instantConverter.toInstant(_tmp_5);
            final boolean _tmpIsDeleted;
            final int _tmp_6;
            _tmp_6 = _cursor.getInt(_cursorIndexOfIsDeleted);
            _tmpIsDeleted = _tmp_6 != 0;
            final Instant _tmpDeletedAt;
            final Long _tmp_7;
            if (_cursor.isNull(_cursorIndexOfDeletedAt)) {
              _tmp_7 = null;
            } else {
              _tmp_7 = _cursor.getLong(_cursorIndexOfDeletedAt);
            }
            _tmpDeletedAt = __instantConverter.toInstant(_tmp_7);
            _item = new SymptomEvent(_tmpId,_tmpTimestamp,_tmpTimezone,_tmpType,_tmpSeverity,_tmpDuration,_tmpLocation,_tmpBristolScale,_tmpSuspectedTriggers,_tmpNotes,_tmpPhotoPath,_tmpCreatedAt,_tmpModifiedAt,_tmpIsDeleted,_tmpDeletedAt);
            _result.add(_item);
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
  public Object getAverageSeverityByType(final SymptomType type,
      final Continuation<? super Float> $completion) {
    final String _sql = "\n"
            + "        SELECT AVG(severity) FROM symptom_events\n"
            + "        WHERE type = ? AND isDeleted = 0\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, __SymptomType_enumToString(type));
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Float>() {
      @Override
      @Nullable
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
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getCountByType(final SymptomType type,
      final Continuation<? super Integer> $completion) {
    final String _sql = "\n"
            + "        SELECT COUNT(*) FROM symptom_events\n"
            + "        WHERE type = ? AND isDeleted = 0\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, __SymptomType_enumToString(type));
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
  public Object getSymptomFrequency(
      final Continuation<? super List<SymptomFrequency>> $completion) {
    final String _sql = "\n"
            + "        SELECT type, COUNT(*) as count FROM symptom_events\n"
            + "        WHERE isDeleted = 0\n"
            + "        GROUP BY type\n"
            + "        ORDER BY count DESC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<SymptomFrequency>>() {
      @Override
      @NonNull
      public List<SymptomFrequency> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfType = 0;
          final int _cursorIndexOfCount = 1;
          final List<SymptomFrequency> _result = new ArrayList<SymptomFrequency>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SymptomFrequency _item;
            final String _tmpType;
            if (_cursor.isNull(_cursorIndexOfType)) {
              _tmpType = null;
            } else {
              _tmpType = _cursor.getString(_cursorIndexOfType);
            }
            final int _tmpCount;
            _tmpCount = _cursor.getInt(_cursorIndexOfCount);
            _item = new SymptomFrequency(_tmpType,_tmpCount);
            _result.add(_item);
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
  public Flow<List<SymptomEvent>> getBowelMovements() {
    final String _sql = "\n"
            + "        SELECT * FROM symptom_events\n"
            + "        WHERE bristolScale IS NOT NULL AND isDeleted = 0\n"
            + "        ORDER BY timestamp DESC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"symptom_events"}, new Callable<List<SymptomEvent>>() {
      @Override
      @NonNull
      public List<SymptomEvent> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfTimezone = CursorUtil.getColumnIndexOrThrow(_cursor, "timezone");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfSeverity = CursorUtil.getColumnIndexOrThrow(_cursor, "severity");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfBristolScale = CursorUtil.getColumnIndexOrThrow(_cursor, "bristolScale");
          final int _cursorIndexOfSuspectedTriggers = CursorUtil.getColumnIndexOrThrow(_cursor, "suspectedTriggers");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfPhotoPath = CursorUtil.getColumnIndexOrThrow(_cursor, "photoPath");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfModifiedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "modifiedAt");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isDeleted");
          final int _cursorIndexOfDeletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "deletedAt");
          final List<SymptomEvent> _result = new ArrayList<SymptomEvent>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SymptomEvent _item;
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
            final SymptomType _tmpType;
            _tmpType = __SymptomType_stringToEnum(_cursor.getString(_cursorIndexOfType));
            final int _tmpSeverity;
            _tmpSeverity = _cursor.getInt(_cursorIndexOfSeverity);
            final Integer _tmpDuration;
            if (_cursor.isNull(_cursorIndexOfDuration)) {
              _tmpDuration = null;
            } else {
              _tmpDuration = _cursor.getInt(_cursorIndexOfDuration);
            }
            final String _tmpLocation;
            if (_cursor.isNull(_cursorIndexOfLocation)) {
              _tmpLocation = null;
            } else {
              _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            }
            final Integer _tmpBristolScale;
            if (_cursor.isNull(_cursorIndexOfBristolScale)) {
              _tmpBristolScale = null;
            } else {
              _tmpBristolScale = _cursor.getInt(_cursorIndexOfBristolScale);
            }
            final List<String> _tmpSuspectedTriggers;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfSuspectedTriggers)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfSuspectedTriggers);
            }
            _tmpSuspectedTriggers = __stringListConverter.toStringList(_tmp_1);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final String _tmpPhotoPath;
            if (_cursor.isNull(_cursorIndexOfPhotoPath)) {
              _tmpPhotoPath = null;
            } else {
              _tmpPhotoPath = _cursor.getString(_cursorIndexOfPhotoPath);
            }
            final Instant _tmpCreatedAt;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            _tmpCreatedAt = __instantConverter.toInstant(_tmp_2);
            final Instant _tmpModifiedAt;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfModifiedAt)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfModifiedAt);
            }
            _tmpModifiedAt = __instantConverter.toInstant(_tmp_3);
            final boolean _tmpIsDeleted;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsDeleted);
            _tmpIsDeleted = _tmp_4 != 0;
            final Instant _tmpDeletedAt;
            final Long _tmp_5;
            if (_cursor.isNull(_cursorIndexOfDeletedAt)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getLong(_cursorIndexOfDeletedAt);
            }
            _tmpDeletedAt = __instantConverter.toInstant(_tmp_5);
            _item = new SymptomEvent(_tmpId,_tmpTimestamp,_tmpTimezone,_tmpType,_tmpSeverity,_tmpDuration,_tmpLocation,_tmpBristolScale,_tmpSuspectedTriggers,_tmpNotes,_tmpPhotoPath,_tmpCreatedAt,_tmpModifiedAt,_tmpIsDeleted,_tmpDeletedAt);
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
  public Flow<List<SymptomEvent>> getByTrigger(final String trigger) {
    final String _sql = "\n"
            + "        SELECT * FROM symptom_events\n"
            + "        WHERE suspectedTriggers LIKE '%' || ? || '%'\n"
            + "        AND isDeleted = 0\n"
            + "        ORDER BY timestamp DESC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (trigger == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, trigger);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"symptom_events"}, new Callable<List<SymptomEvent>>() {
      @Override
      @NonNull
      public List<SymptomEvent> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfTimezone = CursorUtil.getColumnIndexOrThrow(_cursor, "timezone");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfSeverity = CursorUtil.getColumnIndexOrThrow(_cursor, "severity");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfBristolScale = CursorUtil.getColumnIndexOrThrow(_cursor, "bristolScale");
          final int _cursorIndexOfSuspectedTriggers = CursorUtil.getColumnIndexOrThrow(_cursor, "suspectedTriggers");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfPhotoPath = CursorUtil.getColumnIndexOrThrow(_cursor, "photoPath");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfModifiedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "modifiedAt");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isDeleted");
          final int _cursorIndexOfDeletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "deletedAt");
          final List<SymptomEvent> _result = new ArrayList<SymptomEvent>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SymptomEvent _item;
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
            final SymptomType _tmpType;
            _tmpType = __SymptomType_stringToEnum(_cursor.getString(_cursorIndexOfType));
            final int _tmpSeverity;
            _tmpSeverity = _cursor.getInt(_cursorIndexOfSeverity);
            final Integer _tmpDuration;
            if (_cursor.isNull(_cursorIndexOfDuration)) {
              _tmpDuration = null;
            } else {
              _tmpDuration = _cursor.getInt(_cursorIndexOfDuration);
            }
            final String _tmpLocation;
            if (_cursor.isNull(_cursorIndexOfLocation)) {
              _tmpLocation = null;
            } else {
              _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            }
            final Integer _tmpBristolScale;
            if (_cursor.isNull(_cursorIndexOfBristolScale)) {
              _tmpBristolScale = null;
            } else {
              _tmpBristolScale = _cursor.getInt(_cursorIndexOfBristolScale);
            }
            final List<String> _tmpSuspectedTriggers;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfSuspectedTriggers)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfSuspectedTriggers);
            }
            _tmpSuspectedTriggers = __stringListConverter.toStringList(_tmp_1);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final String _tmpPhotoPath;
            if (_cursor.isNull(_cursorIndexOfPhotoPath)) {
              _tmpPhotoPath = null;
            } else {
              _tmpPhotoPath = _cursor.getString(_cursorIndexOfPhotoPath);
            }
            final Instant _tmpCreatedAt;
            final Long _tmp_2;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getLong(_cursorIndexOfCreatedAt);
            }
            _tmpCreatedAt = __instantConverter.toInstant(_tmp_2);
            final Instant _tmpModifiedAt;
            final Long _tmp_3;
            if (_cursor.isNull(_cursorIndexOfModifiedAt)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getLong(_cursorIndexOfModifiedAt);
            }
            _tmpModifiedAt = __instantConverter.toInstant(_tmp_3);
            final boolean _tmpIsDeleted;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsDeleted);
            _tmpIsDeleted = _tmp_4 != 0;
            final Instant _tmpDeletedAt;
            final Long _tmp_5;
            if (_cursor.isNull(_cursorIndexOfDeletedAt)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getLong(_cursorIndexOfDeletedAt);
            }
            _tmpDeletedAt = __instantConverter.toInstant(_tmp_5);
            _item = new SymptomEvent(_tmpId,_tmpTimestamp,_tmpTimezone,_tmpType,_tmpSeverity,_tmpDuration,_tmpLocation,_tmpBristolScale,_tmpSuspectedTriggers,_tmpNotes,_tmpPhotoPath,_tmpCreatedAt,_tmpModifiedAt,_tmpIsDeleted,_tmpDeletedAt);
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
    final String _sql = "SELECT COUNT(*) FROM symptom_events WHERE isDeleted = 0";
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
  public Object getCountByDate(final String date, final Continuation<? super Integer> $completion) {
    final String _sql = "\n"
            + "        SELECT COUNT(*) FROM symptom_events\n"
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
    }, $completion);
  }

  @Override
  public Object getSymptomFreeDays(final Instant sinceTimestamp,
      final Continuation<? super Integer> $completion) {
    final String _sql = "\n"
            + "        SELECT COUNT(DISTINCT DATE(timestamp, 'unixepoch')) FROM symptom_events\n"
            + "        WHERE isDeleted = 0 AND timestamp >= ?\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final Long _tmp = __instantConverter.fromInstant(sinceTimestamp);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
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
            final Integer _tmp_1;
            if (_cursor.isNull(0)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getInt(0);
            }
            _result = _tmp_1;
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
  public Object getLongestSymptomFreeStreak(final Continuation<? super Integer> $completion) {
    final String _sql = "\n"
            + "        SELECT MAX(consecutive_days) FROM (\n"
            + "            SELECT COUNT(*) as consecutive_days\n"
            + "            FROM symptom_events\n"
            + "            WHERE isDeleted = 0\n"
            + "            GROUP BY date(timestamp, 'unixepoch')\n"
            + "            ORDER BY timestamp\n"
            + "        )\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @Nullable
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
