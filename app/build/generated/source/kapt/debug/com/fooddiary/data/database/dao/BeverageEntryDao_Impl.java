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
import com.fooddiary.data.database.entities.BeverageEntry;
import com.fooddiary.data.models.BeverageType;
import com.fooddiary.data.models.Temperature;
import com.fooddiary.data.models.VolumeUnit;
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
public final class BeverageEntryDao_Impl implements BeverageEntryDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<BeverageEntry> __insertionAdapterOfBeverageEntry;

  private final InstantConverter __instantConverter = new InstantConverter();

  private final EntityDeletionOrUpdateAdapter<BeverageEntry> __updateAdapterOfBeverageEntry;

  private final SharedSQLiteStatement __preparedStmtOfSoftDelete;

  private final SharedSQLiteStatement __preparedStmtOfHardDelete;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public BeverageEntryDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfBeverageEntry = new EntityInsertionAdapter<BeverageEntry>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `beverage_entries` (`id`,`timestamp`,`timezone`,`name`,`type`,`volume`,`volumeUnit`,`caffeineContent`,`alcoholContent`,`carbonation`,`temperature`,`notes`,`createdAt`,`modifiedAt`,`isDeleted`,`deletedAt`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BeverageEntry entity) {
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
        statement.bindString(5, __BeverageType_enumToString(entity.getType()));
        statement.bindDouble(6, entity.getVolume());
        statement.bindString(7, __VolumeUnit_enumToString(entity.getVolumeUnit()));
        if (entity.getCaffeineContent() == null) {
          statement.bindNull(8);
        } else {
          statement.bindDouble(8, entity.getCaffeineContent());
        }
        if (entity.getAlcoholContent() == null) {
          statement.bindNull(9);
        } else {
          statement.bindDouble(9, entity.getAlcoholContent());
        }
        final int _tmp_1 = entity.getCarbonation() ? 1 : 0;
        statement.bindLong(10, _tmp_1);
        statement.bindString(11, __Temperature_enumToString(entity.getTemperature()));
        if (entity.getNotes() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getNotes());
        }
        final Long _tmp_2 = __instantConverter.fromInstant(entity.getCreatedAt());
        if (_tmp_2 == null) {
          statement.bindNull(13);
        } else {
          statement.bindLong(13, _tmp_2);
        }
        final Long _tmp_3 = __instantConverter.fromInstant(entity.getModifiedAt());
        if (_tmp_3 == null) {
          statement.bindNull(14);
        } else {
          statement.bindLong(14, _tmp_3);
        }
        final int _tmp_4 = entity.isDeleted() ? 1 : 0;
        statement.bindLong(15, _tmp_4);
        final Long _tmp_5 = __instantConverter.fromInstant(entity.getDeletedAt());
        if (_tmp_5 == null) {
          statement.bindNull(16);
        } else {
          statement.bindLong(16, _tmp_5);
        }
      }
    };
    this.__updateAdapterOfBeverageEntry = new EntityDeletionOrUpdateAdapter<BeverageEntry>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `beverage_entries` SET `id` = ?,`timestamp` = ?,`timezone` = ?,`name` = ?,`type` = ?,`volume` = ?,`volumeUnit` = ?,`caffeineContent` = ?,`alcoholContent` = ?,`carbonation` = ?,`temperature` = ?,`notes` = ?,`createdAt` = ?,`modifiedAt` = ?,`isDeleted` = ?,`deletedAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BeverageEntry entity) {
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
        statement.bindString(5, __BeverageType_enumToString(entity.getType()));
        statement.bindDouble(6, entity.getVolume());
        statement.bindString(7, __VolumeUnit_enumToString(entity.getVolumeUnit()));
        if (entity.getCaffeineContent() == null) {
          statement.bindNull(8);
        } else {
          statement.bindDouble(8, entity.getCaffeineContent());
        }
        if (entity.getAlcoholContent() == null) {
          statement.bindNull(9);
        } else {
          statement.bindDouble(9, entity.getAlcoholContent());
        }
        final int _tmp_1 = entity.getCarbonation() ? 1 : 0;
        statement.bindLong(10, _tmp_1);
        statement.bindString(11, __Temperature_enumToString(entity.getTemperature()));
        if (entity.getNotes() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getNotes());
        }
        final Long _tmp_2 = __instantConverter.fromInstant(entity.getCreatedAt());
        if (_tmp_2 == null) {
          statement.bindNull(13);
        } else {
          statement.bindLong(13, _tmp_2);
        }
        final Long _tmp_3 = __instantConverter.fromInstant(entity.getModifiedAt());
        if (_tmp_3 == null) {
          statement.bindNull(14);
        } else {
          statement.bindLong(14, _tmp_3);
        }
        final int _tmp_4 = entity.isDeleted() ? 1 : 0;
        statement.bindLong(15, _tmp_4);
        final Long _tmp_5 = __instantConverter.fromInstant(entity.getDeletedAt());
        if (_tmp_5 == null) {
          statement.bindNull(16);
        } else {
          statement.bindLong(16, _tmp_5);
        }
        if (entity.getId() == null) {
          statement.bindNull(17);
        } else {
          statement.bindString(17, entity.getId());
        }
      }
    };
    this.__preparedStmtOfSoftDelete = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "\n"
                + "        UPDATE beverage_entries\n"
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
        final String _query = "DELETE FROM beverage_entries WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM beverage_entries";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final BeverageEntry entry, final Continuation<? super Long> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfBeverageEntry.insertAndReturnId(entry);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object update(final BeverageEntry entry, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfBeverageEntry.handle(entry);
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
  public Object getById(final String id, final Continuation<? super BeverageEntry> arg1) {
    final String _sql = "SELECT * FROM beverage_entries WHERE id = ? AND isDeleted = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, id);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<BeverageEntry>() {
      @Override
      @Nullable
      public BeverageEntry call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfTimezone = CursorUtil.getColumnIndexOrThrow(_cursor, "timezone");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfVolume = CursorUtil.getColumnIndexOrThrow(_cursor, "volume");
          final int _cursorIndexOfVolumeUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "volumeUnit");
          final int _cursorIndexOfCaffeineContent = CursorUtil.getColumnIndexOrThrow(_cursor, "caffeineContent");
          final int _cursorIndexOfAlcoholContent = CursorUtil.getColumnIndexOrThrow(_cursor, "alcoholContent");
          final int _cursorIndexOfCarbonation = CursorUtil.getColumnIndexOrThrow(_cursor, "carbonation");
          final int _cursorIndexOfTemperature = CursorUtil.getColumnIndexOrThrow(_cursor, "temperature");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfModifiedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "modifiedAt");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isDeleted");
          final int _cursorIndexOfDeletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "deletedAt");
          final BeverageEntry _result;
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
            final BeverageType _tmpType;
            _tmpType = __BeverageType_stringToEnum(_cursor.getString(_cursorIndexOfType));
            final float _tmpVolume;
            _tmpVolume = _cursor.getFloat(_cursorIndexOfVolume);
            final VolumeUnit _tmpVolumeUnit;
            _tmpVolumeUnit = __VolumeUnit_stringToEnum(_cursor.getString(_cursorIndexOfVolumeUnit));
            final Float _tmpCaffeineContent;
            if (_cursor.isNull(_cursorIndexOfCaffeineContent)) {
              _tmpCaffeineContent = null;
            } else {
              _tmpCaffeineContent = _cursor.getFloat(_cursorIndexOfCaffeineContent);
            }
            final Float _tmpAlcoholContent;
            if (_cursor.isNull(_cursorIndexOfAlcoholContent)) {
              _tmpAlcoholContent = null;
            } else {
              _tmpAlcoholContent = _cursor.getFloat(_cursorIndexOfAlcoholContent);
            }
            final boolean _tmpCarbonation;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfCarbonation);
            _tmpCarbonation = _tmp_1 != 0;
            final Temperature _tmpTemperature;
            _tmpTemperature = __Temperature_stringToEnum(_cursor.getString(_cursorIndexOfTemperature));
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
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
            _result = new BeverageEntry(_tmpId,_tmpTimestamp,_tmpTimezone,_tmpName,_tmpType,_tmpVolume,_tmpVolumeUnit,_tmpCaffeineContent,_tmpAlcoholContent,_tmpCarbonation,_tmpTemperature,_tmpNotes,_tmpCreatedAt,_tmpModifiedAt,_tmpIsDeleted,_tmpDeletedAt);
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
  public Flow<List<BeverageEntry>> getAll() {
    final String _sql = "SELECT * FROM beverage_entries WHERE isDeleted = 0 ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"beverage_entries"}, new Callable<List<BeverageEntry>>() {
      @Override
      @NonNull
      public List<BeverageEntry> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfTimezone = CursorUtil.getColumnIndexOrThrow(_cursor, "timezone");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfVolume = CursorUtil.getColumnIndexOrThrow(_cursor, "volume");
          final int _cursorIndexOfVolumeUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "volumeUnit");
          final int _cursorIndexOfCaffeineContent = CursorUtil.getColumnIndexOrThrow(_cursor, "caffeineContent");
          final int _cursorIndexOfAlcoholContent = CursorUtil.getColumnIndexOrThrow(_cursor, "alcoholContent");
          final int _cursorIndexOfCarbonation = CursorUtil.getColumnIndexOrThrow(_cursor, "carbonation");
          final int _cursorIndexOfTemperature = CursorUtil.getColumnIndexOrThrow(_cursor, "temperature");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfModifiedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "modifiedAt");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isDeleted");
          final int _cursorIndexOfDeletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "deletedAt");
          final List<BeverageEntry> _result = new ArrayList<BeverageEntry>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BeverageEntry _item;
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
            final BeverageType _tmpType;
            _tmpType = __BeverageType_stringToEnum(_cursor.getString(_cursorIndexOfType));
            final float _tmpVolume;
            _tmpVolume = _cursor.getFloat(_cursorIndexOfVolume);
            final VolumeUnit _tmpVolumeUnit;
            _tmpVolumeUnit = __VolumeUnit_stringToEnum(_cursor.getString(_cursorIndexOfVolumeUnit));
            final Float _tmpCaffeineContent;
            if (_cursor.isNull(_cursorIndexOfCaffeineContent)) {
              _tmpCaffeineContent = null;
            } else {
              _tmpCaffeineContent = _cursor.getFloat(_cursorIndexOfCaffeineContent);
            }
            final Float _tmpAlcoholContent;
            if (_cursor.isNull(_cursorIndexOfAlcoholContent)) {
              _tmpAlcoholContent = null;
            } else {
              _tmpAlcoholContent = _cursor.getFloat(_cursorIndexOfAlcoholContent);
            }
            final boolean _tmpCarbonation;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfCarbonation);
            _tmpCarbonation = _tmp_1 != 0;
            final Temperature _tmpTemperature;
            _tmpTemperature = __Temperature_stringToEnum(_cursor.getString(_cursorIndexOfTemperature));
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
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
            _item = new BeverageEntry(_tmpId,_tmpTimestamp,_tmpTimezone,_tmpName,_tmpType,_tmpVolume,_tmpVolumeUnit,_tmpCaffeineContent,_tmpAlcoholContent,_tmpCarbonation,_tmpTemperature,_tmpNotes,_tmpCreatedAt,_tmpModifiedAt,_tmpIsDeleted,_tmpDeletedAt);
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
  public Flow<List<BeverageEntry>> getAllByDateRange(final String startDate, final String endDate) {
    final String _sql = "\n"
            + "        SELECT * FROM beverage_entries\n"
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
    return CoroutinesRoom.createFlow(__db, false, new String[] {"beverage_entries"}, new Callable<List<BeverageEntry>>() {
      @Override
      @NonNull
      public List<BeverageEntry> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfTimezone = CursorUtil.getColumnIndexOrThrow(_cursor, "timezone");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfVolume = CursorUtil.getColumnIndexOrThrow(_cursor, "volume");
          final int _cursorIndexOfVolumeUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "volumeUnit");
          final int _cursorIndexOfCaffeineContent = CursorUtil.getColumnIndexOrThrow(_cursor, "caffeineContent");
          final int _cursorIndexOfAlcoholContent = CursorUtil.getColumnIndexOrThrow(_cursor, "alcoholContent");
          final int _cursorIndexOfCarbonation = CursorUtil.getColumnIndexOrThrow(_cursor, "carbonation");
          final int _cursorIndexOfTemperature = CursorUtil.getColumnIndexOrThrow(_cursor, "temperature");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfModifiedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "modifiedAt");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isDeleted");
          final int _cursorIndexOfDeletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "deletedAt");
          final List<BeverageEntry> _result = new ArrayList<BeverageEntry>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BeverageEntry _item;
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
            final BeverageType _tmpType;
            _tmpType = __BeverageType_stringToEnum(_cursor.getString(_cursorIndexOfType));
            final float _tmpVolume;
            _tmpVolume = _cursor.getFloat(_cursorIndexOfVolume);
            final VolumeUnit _tmpVolumeUnit;
            _tmpVolumeUnit = __VolumeUnit_stringToEnum(_cursor.getString(_cursorIndexOfVolumeUnit));
            final Float _tmpCaffeineContent;
            if (_cursor.isNull(_cursorIndexOfCaffeineContent)) {
              _tmpCaffeineContent = null;
            } else {
              _tmpCaffeineContent = _cursor.getFloat(_cursorIndexOfCaffeineContent);
            }
            final Float _tmpAlcoholContent;
            if (_cursor.isNull(_cursorIndexOfAlcoholContent)) {
              _tmpAlcoholContent = null;
            } else {
              _tmpAlcoholContent = _cursor.getFloat(_cursorIndexOfAlcoholContent);
            }
            final boolean _tmpCarbonation;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfCarbonation);
            _tmpCarbonation = _tmp_1 != 0;
            final Temperature _tmpTemperature;
            _tmpTemperature = __Temperature_stringToEnum(_cursor.getString(_cursorIndexOfTemperature));
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
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
            _item = new BeverageEntry(_tmpId,_tmpTimestamp,_tmpTimezone,_tmpName,_tmpType,_tmpVolume,_tmpVolumeUnit,_tmpCaffeineContent,_tmpAlcoholContent,_tmpCarbonation,_tmpTemperature,_tmpNotes,_tmpCreatedAt,_tmpModifiedAt,_tmpIsDeleted,_tmpDeletedAt);
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
  public Flow<Float> getCaffeineIntake(final String date) {
    final String _sql = "\n"
            + "        SELECT COALESCE(SUM(caffeineContent * volume / 1000.0), 0) as totalCaffeine\n"
            + "        FROM beverage_entries\n"
            + "        WHERE DATE(timestamp, 'unixepoch') = ?\n"
            + "        AND caffeineContent IS NOT NULL\n"
            + "        AND isDeleted = 0\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (date == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, date);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"beverage_entries"}, new Callable<Float>() {
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
  public Flow<Float> getHydration(final String date) {
    final String _sql = "\n"
            + "        SELECT COALESCE(SUM(CASE\n"
            + "            WHEN volumeUnit = 'L' THEN volume * 1000\n"
            + "            WHEN volumeUnit = 'OZ' THEN volume * 29.5735\n"
            + "            WHEN volumeUnit = 'CUP' THEN volume * 240\n"
            + "            ELSE volume\n"
            + "        END), 0) as totalHydration\n"
            + "        FROM beverage_entries\n"
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
    return CoroutinesRoom.createFlow(__db, false, new String[] {"beverage_entries"}, new Callable<Float>() {
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
  public Flow<List<BeverageEntry>> getCaffeinatedBeverages() {
    final String _sql = "\n"
            + "        SELECT * FROM beverage_entries\n"
            + "        WHERE caffeineContent > 0\n"
            + "        AND isDeleted = 0\n"
            + "        ORDER BY timestamp DESC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"beverage_entries"}, new Callable<List<BeverageEntry>>() {
      @Override
      @NonNull
      public List<BeverageEntry> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfTimezone = CursorUtil.getColumnIndexOrThrow(_cursor, "timezone");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfVolume = CursorUtil.getColumnIndexOrThrow(_cursor, "volume");
          final int _cursorIndexOfVolumeUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "volumeUnit");
          final int _cursorIndexOfCaffeineContent = CursorUtil.getColumnIndexOrThrow(_cursor, "caffeineContent");
          final int _cursorIndexOfAlcoholContent = CursorUtil.getColumnIndexOrThrow(_cursor, "alcoholContent");
          final int _cursorIndexOfCarbonation = CursorUtil.getColumnIndexOrThrow(_cursor, "carbonation");
          final int _cursorIndexOfTemperature = CursorUtil.getColumnIndexOrThrow(_cursor, "temperature");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfModifiedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "modifiedAt");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isDeleted");
          final int _cursorIndexOfDeletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "deletedAt");
          final List<BeverageEntry> _result = new ArrayList<BeverageEntry>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BeverageEntry _item;
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
            final BeverageType _tmpType;
            _tmpType = __BeverageType_stringToEnum(_cursor.getString(_cursorIndexOfType));
            final float _tmpVolume;
            _tmpVolume = _cursor.getFloat(_cursorIndexOfVolume);
            final VolumeUnit _tmpVolumeUnit;
            _tmpVolumeUnit = __VolumeUnit_stringToEnum(_cursor.getString(_cursorIndexOfVolumeUnit));
            final Float _tmpCaffeineContent;
            if (_cursor.isNull(_cursorIndexOfCaffeineContent)) {
              _tmpCaffeineContent = null;
            } else {
              _tmpCaffeineContent = _cursor.getFloat(_cursorIndexOfCaffeineContent);
            }
            final Float _tmpAlcoholContent;
            if (_cursor.isNull(_cursorIndexOfAlcoholContent)) {
              _tmpAlcoholContent = null;
            } else {
              _tmpAlcoholContent = _cursor.getFloat(_cursorIndexOfAlcoholContent);
            }
            final boolean _tmpCarbonation;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfCarbonation);
            _tmpCarbonation = _tmp_1 != 0;
            final Temperature _tmpTemperature;
            _tmpTemperature = __Temperature_stringToEnum(_cursor.getString(_cursorIndexOfTemperature));
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
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
            _item = new BeverageEntry(_tmpId,_tmpTimestamp,_tmpTimezone,_tmpName,_tmpType,_tmpVolume,_tmpVolumeUnit,_tmpCaffeineContent,_tmpAlcoholContent,_tmpCarbonation,_tmpTemperature,_tmpNotes,_tmpCreatedAt,_tmpModifiedAt,_tmpIsDeleted,_tmpDeletedAt);
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
  public Flow<List<BeverageEntry>> getAlcoholicBeverages() {
    final String _sql = "\n"
            + "        SELECT * FROM beverage_entries\n"
            + "        WHERE alcoholContent > 0\n"
            + "        AND isDeleted = 0\n"
            + "        ORDER BY timestamp DESC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"beverage_entries"}, new Callable<List<BeverageEntry>>() {
      @Override
      @NonNull
      public List<BeverageEntry> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfTimezone = CursorUtil.getColumnIndexOrThrow(_cursor, "timezone");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfVolume = CursorUtil.getColumnIndexOrThrow(_cursor, "volume");
          final int _cursorIndexOfVolumeUnit = CursorUtil.getColumnIndexOrThrow(_cursor, "volumeUnit");
          final int _cursorIndexOfCaffeineContent = CursorUtil.getColumnIndexOrThrow(_cursor, "caffeineContent");
          final int _cursorIndexOfAlcoholContent = CursorUtil.getColumnIndexOrThrow(_cursor, "alcoholContent");
          final int _cursorIndexOfCarbonation = CursorUtil.getColumnIndexOrThrow(_cursor, "carbonation");
          final int _cursorIndexOfTemperature = CursorUtil.getColumnIndexOrThrow(_cursor, "temperature");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfModifiedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "modifiedAt");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isDeleted");
          final int _cursorIndexOfDeletedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "deletedAt");
          final List<BeverageEntry> _result = new ArrayList<BeverageEntry>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BeverageEntry _item;
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
            final BeverageType _tmpType;
            _tmpType = __BeverageType_stringToEnum(_cursor.getString(_cursorIndexOfType));
            final float _tmpVolume;
            _tmpVolume = _cursor.getFloat(_cursorIndexOfVolume);
            final VolumeUnit _tmpVolumeUnit;
            _tmpVolumeUnit = __VolumeUnit_stringToEnum(_cursor.getString(_cursorIndexOfVolumeUnit));
            final Float _tmpCaffeineContent;
            if (_cursor.isNull(_cursorIndexOfCaffeineContent)) {
              _tmpCaffeineContent = null;
            } else {
              _tmpCaffeineContent = _cursor.getFloat(_cursorIndexOfCaffeineContent);
            }
            final Float _tmpAlcoholContent;
            if (_cursor.isNull(_cursorIndexOfAlcoholContent)) {
              _tmpAlcoholContent = null;
            } else {
              _tmpAlcoholContent = _cursor.getFloat(_cursorIndexOfAlcoholContent);
            }
            final boolean _tmpCarbonation;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfCarbonation);
            _tmpCarbonation = _tmp_1 != 0;
            final Temperature _tmpTemperature;
            _tmpTemperature = __Temperature_stringToEnum(_cursor.getString(_cursorIndexOfTemperature));
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
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
            _item = new BeverageEntry(_tmpId,_tmpTimestamp,_tmpTimezone,_tmpName,_tmpType,_tmpVolume,_tmpVolumeUnit,_tmpCaffeineContent,_tmpAlcoholContent,_tmpCarbonation,_tmpTemperature,_tmpNotes,_tmpCreatedAt,_tmpModifiedAt,_tmpIsDeleted,_tmpDeletedAt);
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
  public Object getCount(final Continuation<? super Integer> arg0) {
    final String _sql = "SELECT COUNT(*) FROM beverage_entries WHERE isDeleted = 0";
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }

  private String __BeverageType_enumToString(@NonNull final BeverageType _value) {
    switch (_value) {
      case WATER: return "WATER";
      case COFFEE: return "COFFEE";
      case TEA: return "TEA";
      case JUICE: return "JUICE";
      case SOFT_DRINK: return "SOFT_DRINK";
      case ALCOHOL: return "ALCOHOL";
      case MILK: return "MILK";
      case SPORTS_DRINK: return "SPORTS_DRINK";
      case ENERGY_DRINK: return "ENERGY_DRINK";
      case OTHER: return "OTHER";
      default: throw new IllegalArgumentException("Can't convert enum to string, unknown enum value: " + _value);
    }
  }

  private String __VolumeUnit_enumToString(@NonNull final VolumeUnit _value) {
    switch (_value) {
      case ML: return "ML";
      case L: return "L";
      case OZ: return "OZ";
      case CUP: return "CUP";
      default: throw new IllegalArgumentException("Can't convert enum to string, unknown enum value: " + _value);
    }
  }

  private String __Temperature_enumToString(@NonNull final Temperature _value) {
    switch (_value) {
      case ICE_COLD: return "ICE_COLD";
      case COLD: return "COLD";
      case ROOM_TEMPERATURE: return "ROOM_TEMPERATURE";
      case WARM: return "WARM";
      case HOT: return "HOT";
      default: throw new IllegalArgumentException("Can't convert enum to string, unknown enum value: " + _value);
    }
  }

  private BeverageType __BeverageType_stringToEnum(@NonNull final String _value) {
    switch (_value) {
      case "WATER": return BeverageType.WATER;
      case "COFFEE": return BeverageType.COFFEE;
      case "TEA": return BeverageType.TEA;
      case "JUICE": return BeverageType.JUICE;
      case "SOFT_DRINK": return BeverageType.SOFT_DRINK;
      case "ALCOHOL": return BeverageType.ALCOHOL;
      case "MILK": return BeverageType.MILK;
      case "SPORTS_DRINK": return BeverageType.SPORTS_DRINK;
      case "ENERGY_DRINK": return BeverageType.ENERGY_DRINK;
      case "OTHER": return BeverageType.OTHER;
      default: throw new IllegalArgumentException("Can't convert value to enum, unknown value: " + _value);
    }
  }

  private VolumeUnit __VolumeUnit_stringToEnum(@NonNull final String _value) {
    switch (_value) {
      case "ML": return VolumeUnit.ML;
      case "L": return VolumeUnit.L;
      case "OZ": return VolumeUnit.OZ;
      case "CUP": return VolumeUnit.CUP;
      default: throw new IllegalArgumentException("Can't convert value to enum, unknown value: " + _value);
    }
  }

  private Temperature __Temperature_stringToEnum(@NonNull final String _value) {
    switch (_value) {
      case "ICE_COLD": return Temperature.ICE_COLD;
      case "COLD": return Temperature.COLD;
      case "ROOM_TEMPERATURE": return Temperature.ROOM_TEMPERATURE;
      case "WARM": return Temperature.WARM;
      case "HOT": return Temperature.HOT;
      default: throw new IllegalArgumentException("Can't convert value to enum, unknown value: " + _value);
    }
  }
}
