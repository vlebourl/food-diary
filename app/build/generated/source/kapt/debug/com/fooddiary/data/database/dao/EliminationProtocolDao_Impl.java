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
import com.fooddiary.data.database.converters.LocalDateConverter;
import com.fooddiary.data.database.converters.StringListConverter;
import com.fooddiary.data.database.entities.EliminationProtocol;
import com.fooddiary.data.models.EliminationPhase;
import java.lang.Class;
import java.lang.Exception;
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
public final class EliminationProtocolDao_Impl implements EliminationProtocolDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<EliminationProtocol> __insertionAdapterOfEliminationProtocol;

  private final LocalDateConverter __localDateConverter = new LocalDateConverter();

  private final StringListConverter __stringListConverter = new StringListConverter();

  private final EntityDeletionOrUpdateAdapter<EliminationProtocol> __updateAdapterOfEliminationProtocol;

  private final SharedSQLiteStatement __preparedStmtOfDelete;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public EliminationProtocolDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfEliminationProtocol = new EntityInsertionAdapter<EliminationProtocol>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `elimination_protocols` (`id`,`name`,`startDate`,`endDate`,`currentPhase`,`eliminatedFoods`,`reintroducedFoods`,`phaseStartDate`,`notes`,`isActive`,`createdAt`,`modifiedAt`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final EliminationProtocol entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        final Long _tmp = __localDateConverter.fromLocalDate(entity.getStartDate());
        if (_tmp == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, _tmp);
        }
        final Long _tmp_1 = __localDateConverter.fromLocalDate(entity.getEndDate());
        if (_tmp_1 == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, _tmp_1);
        }
        statement.bindString(5, __EliminationPhase_enumToString(entity.getCurrentPhase()));
        final String _tmp_2 = __stringListConverter.fromStringList(entity.getEliminatedFoods());
        if (_tmp_2 == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, _tmp_2);
        }
        final String _tmp_3 = __stringListConverter.fromStringList(entity.getReintroducedFoods());
        if (_tmp_3 == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, _tmp_3);
        }
        final Long _tmp_4 = __localDateConverter.fromLocalDate(entity.getPhaseStartDate());
        if (_tmp_4 == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, _tmp_4);
        }
        if (entity.getNotes() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getNotes());
        }
        final int _tmp_5 = entity.isActive() ? 1 : 0;
        statement.bindLong(10, _tmp_5);
        statement.bindLong(11, entity.getCreatedAt());
        if (entity.getModifiedAt() == null) {
          statement.bindNull(12);
        } else {
          statement.bindLong(12, entity.getModifiedAt());
        }
      }
    };
    this.__updateAdapterOfEliminationProtocol = new EntityDeletionOrUpdateAdapter<EliminationProtocol>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `elimination_protocols` SET `id` = ?,`name` = ?,`startDate` = ?,`endDate` = ?,`currentPhase` = ?,`eliminatedFoods` = ?,`reintroducedFoods` = ?,`phaseStartDate` = ?,`notes` = ?,`isActive` = ?,`createdAt` = ?,`modifiedAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final EliminationProtocol entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        final Long _tmp = __localDateConverter.fromLocalDate(entity.getStartDate());
        if (_tmp == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, _tmp);
        }
        final Long _tmp_1 = __localDateConverter.fromLocalDate(entity.getEndDate());
        if (_tmp_1 == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, _tmp_1);
        }
        statement.bindString(5, __EliminationPhase_enumToString(entity.getCurrentPhase()));
        final String _tmp_2 = __stringListConverter.fromStringList(entity.getEliminatedFoods());
        if (_tmp_2 == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, _tmp_2);
        }
        final String _tmp_3 = __stringListConverter.fromStringList(entity.getReintroducedFoods());
        if (_tmp_3 == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, _tmp_3);
        }
        final Long _tmp_4 = __localDateConverter.fromLocalDate(entity.getPhaseStartDate());
        if (_tmp_4 == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, _tmp_4);
        }
        if (entity.getNotes() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getNotes());
        }
        final int _tmp_5 = entity.isActive() ? 1 : 0;
        statement.bindLong(10, _tmp_5);
        statement.bindLong(11, entity.getCreatedAt());
        if (entity.getModifiedAt() == null) {
          statement.bindNull(12);
        } else {
          statement.bindLong(12, entity.getModifiedAt());
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
        final String _query = "DELETE FROM elimination_protocols WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM elimination_protocols";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final EliminationProtocol protocol, final Continuation<? super Long> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfEliminationProtocol.insertAndReturnId(protocol);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object update(final EliminationProtocol protocol, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfEliminationProtocol.handle(protocol);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object delete(final String id, final Continuation<? super Unit> arg1) {
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
  public Object getById(final String id, final Continuation<? super EliminationProtocol> arg1) {
    final String _sql = "SELECT * FROM elimination_protocols WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, id);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<EliminationProtocol>() {
      @Override
      @Nullable
      public EliminationProtocol call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfCurrentPhase = CursorUtil.getColumnIndexOrThrow(_cursor, "currentPhase");
          final int _cursorIndexOfEliminatedFoods = CursorUtil.getColumnIndexOrThrow(_cursor, "eliminatedFoods");
          final int _cursorIndexOfReintroducedFoods = CursorUtil.getColumnIndexOrThrow(_cursor, "reintroducedFoods");
          final int _cursorIndexOfPhaseStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "phaseStartDate");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfModifiedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "modifiedAt");
          final EliminationProtocol _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final LocalDate _tmpStartDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfStartDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfStartDate);
            }
            _tmpStartDate = __localDateConverter.toLocalDate(_tmp);
            final LocalDate _tmpEndDate;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfEndDate)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfEndDate);
            }
            _tmpEndDate = __localDateConverter.toLocalDate(_tmp_1);
            final EliminationPhase _tmpCurrentPhase;
            _tmpCurrentPhase = __EliminationPhase_stringToEnum(_cursor.getString(_cursorIndexOfCurrentPhase));
            final List<String> _tmpEliminatedFoods;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfEliminatedFoods)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfEliminatedFoods);
            }
            _tmpEliminatedFoods = __stringListConverter.toStringList(_tmp_2);
            final List<String> _tmpReintroducedFoods;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfReintroducedFoods)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfReintroducedFoods);
            }
            _tmpReintroducedFoods = __stringListConverter.toStringList(_tmp_3);
            final LocalDate _tmpPhaseStartDate;
            final Long _tmp_4;
            if (_cursor.isNull(_cursorIndexOfPhaseStartDate)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getLong(_cursorIndexOfPhaseStartDate);
            }
            _tmpPhaseStartDate = __localDateConverter.toLocalDate(_tmp_4);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final boolean _tmpIsActive;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_5 != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final Long _tmpModifiedAt;
            if (_cursor.isNull(_cursorIndexOfModifiedAt)) {
              _tmpModifiedAt = null;
            } else {
              _tmpModifiedAt = _cursor.getLong(_cursorIndexOfModifiedAt);
            }
            _result = new EliminationProtocol(_tmpId,_tmpName,_tmpStartDate,_tmpEndDate,_tmpCurrentPhase,_tmpEliminatedFoods,_tmpReintroducedFoods,_tmpPhaseStartDate,_tmpNotes,_tmpIsActive,_tmpCreatedAt,_tmpModifiedAt);
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
  public Object getActive(final Continuation<? super EliminationProtocol> arg0) {
    final String _sql = "SELECT * FROM elimination_protocols WHERE isActive = 1 LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<EliminationProtocol>() {
      @Override
      @Nullable
      public EliminationProtocol call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfCurrentPhase = CursorUtil.getColumnIndexOrThrow(_cursor, "currentPhase");
          final int _cursorIndexOfEliminatedFoods = CursorUtil.getColumnIndexOrThrow(_cursor, "eliminatedFoods");
          final int _cursorIndexOfReintroducedFoods = CursorUtil.getColumnIndexOrThrow(_cursor, "reintroducedFoods");
          final int _cursorIndexOfPhaseStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "phaseStartDate");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfModifiedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "modifiedAt");
          final EliminationProtocol _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final LocalDate _tmpStartDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfStartDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfStartDate);
            }
            _tmpStartDate = __localDateConverter.toLocalDate(_tmp);
            final LocalDate _tmpEndDate;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfEndDate)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfEndDate);
            }
            _tmpEndDate = __localDateConverter.toLocalDate(_tmp_1);
            final EliminationPhase _tmpCurrentPhase;
            _tmpCurrentPhase = __EliminationPhase_stringToEnum(_cursor.getString(_cursorIndexOfCurrentPhase));
            final List<String> _tmpEliminatedFoods;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfEliminatedFoods)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfEliminatedFoods);
            }
            _tmpEliminatedFoods = __stringListConverter.toStringList(_tmp_2);
            final List<String> _tmpReintroducedFoods;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfReintroducedFoods)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfReintroducedFoods);
            }
            _tmpReintroducedFoods = __stringListConverter.toStringList(_tmp_3);
            final LocalDate _tmpPhaseStartDate;
            final Long _tmp_4;
            if (_cursor.isNull(_cursorIndexOfPhaseStartDate)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getLong(_cursorIndexOfPhaseStartDate);
            }
            _tmpPhaseStartDate = __localDateConverter.toLocalDate(_tmp_4);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final boolean _tmpIsActive;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_5 != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final Long _tmpModifiedAt;
            if (_cursor.isNull(_cursorIndexOfModifiedAt)) {
              _tmpModifiedAt = null;
            } else {
              _tmpModifiedAt = _cursor.getLong(_cursorIndexOfModifiedAt);
            }
            _result = new EliminationProtocol(_tmpId,_tmpName,_tmpStartDate,_tmpEndDate,_tmpCurrentPhase,_tmpEliminatedFoods,_tmpReintroducedFoods,_tmpPhaseStartDate,_tmpNotes,_tmpIsActive,_tmpCreatedAt,_tmpModifiedAt);
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
  public Flow<List<EliminationProtocol>> getAll() {
    final String _sql = "SELECT * FROM elimination_protocols ORDER BY startDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"elimination_protocols"}, new Callable<List<EliminationProtocol>>() {
      @Override
      @NonNull
      public List<EliminationProtocol> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfCurrentPhase = CursorUtil.getColumnIndexOrThrow(_cursor, "currentPhase");
          final int _cursorIndexOfEliminatedFoods = CursorUtil.getColumnIndexOrThrow(_cursor, "eliminatedFoods");
          final int _cursorIndexOfReintroducedFoods = CursorUtil.getColumnIndexOrThrow(_cursor, "reintroducedFoods");
          final int _cursorIndexOfPhaseStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "phaseStartDate");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfModifiedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "modifiedAt");
          final List<EliminationProtocol> _result = new ArrayList<EliminationProtocol>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final EliminationProtocol _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final LocalDate _tmpStartDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfStartDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfStartDate);
            }
            _tmpStartDate = __localDateConverter.toLocalDate(_tmp);
            final LocalDate _tmpEndDate;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfEndDate)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfEndDate);
            }
            _tmpEndDate = __localDateConverter.toLocalDate(_tmp_1);
            final EliminationPhase _tmpCurrentPhase;
            _tmpCurrentPhase = __EliminationPhase_stringToEnum(_cursor.getString(_cursorIndexOfCurrentPhase));
            final List<String> _tmpEliminatedFoods;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfEliminatedFoods)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfEliminatedFoods);
            }
            _tmpEliminatedFoods = __stringListConverter.toStringList(_tmp_2);
            final List<String> _tmpReintroducedFoods;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfReintroducedFoods)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfReintroducedFoods);
            }
            _tmpReintroducedFoods = __stringListConverter.toStringList(_tmp_3);
            final LocalDate _tmpPhaseStartDate;
            final Long _tmp_4;
            if (_cursor.isNull(_cursorIndexOfPhaseStartDate)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getLong(_cursorIndexOfPhaseStartDate);
            }
            _tmpPhaseStartDate = __localDateConverter.toLocalDate(_tmp_4);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final boolean _tmpIsActive;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_5 != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final Long _tmpModifiedAt;
            if (_cursor.isNull(_cursorIndexOfModifiedAt)) {
              _tmpModifiedAt = null;
            } else {
              _tmpModifiedAt = _cursor.getLong(_cursorIndexOfModifiedAt);
            }
            _item = new EliminationProtocol(_tmpId,_tmpName,_tmpStartDate,_tmpEndDate,_tmpCurrentPhase,_tmpEliminatedFoods,_tmpReintroducedFoods,_tmpPhaseStartDate,_tmpNotes,_tmpIsActive,_tmpCreatedAt,_tmpModifiedAt);
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
  public Flow<List<EliminationProtocol>> getByPhase(final EliminationPhase phase) {
    final String _sql = "SELECT * FROM elimination_protocols WHERE currentPhase = ? ORDER BY startDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, __EliminationPhase_enumToString(phase));
    return CoroutinesRoom.createFlow(__db, false, new String[] {"elimination_protocols"}, new Callable<List<EliminationProtocol>>() {
      @Override
      @NonNull
      public List<EliminationProtocol> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfCurrentPhase = CursorUtil.getColumnIndexOrThrow(_cursor, "currentPhase");
          final int _cursorIndexOfEliminatedFoods = CursorUtil.getColumnIndexOrThrow(_cursor, "eliminatedFoods");
          final int _cursorIndexOfReintroducedFoods = CursorUtil.getColumnIndexOrThrow(_cursor, "reintroducedFoods");
          final int _cursorIndexOfPhaseStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "phaseStartDate");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfModifiedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "modifiedAt");
          final List<EliminationProtocol> _result = new ArrayList<EliminationProtocol>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final EliminationProtocol _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final LocalDate _tmpStartDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfStartDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfStartDate);
            }
            _tmpStartDate = __localDateConverter.toLocalDate(_tmp);
            final LocalDate _tmpEndDate;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfEndDate)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfEndDate);
            }
            _tmpEndDate = __localDateConverter.toLocalDate(_tmp_1);
            final EliminationPhase _tmpCurrentPhase;
            _tmpCurrentPhase = __EliminationPhase_stringToEnum(_cursor.getString(_cursorIndexOfCurrentPhase));
            final List<String> _tmpEliminatedFoods;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfEliminatedFoods)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfEliminatedFoods);
            }
            _tmpEliminatedFoods = __stringListConverter.toStringList(_tmp_2);
            final List<String> _tmpReintroducedFoods;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfReintroducedFoods)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfReintroducedFoods);
            }
            _tmpReintroducedFoods = __stringListConverter.toStringList(_tmp_3);
            final LocalDate _tmpPhaseStartDate;
            final Long _tmp_4;
            if (_cursor.isNull(_cursorIndexOfPhaseStartDate)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getLong(_cursorIndexOfPhaseStartDate);
            }
            _tmpPhaseStartDate = __localDateConverter.toLocalDate(_tmp_4);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final boolean _tmpIsActive;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_5 != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final Long _tmpModifiedAt;
            if (_cursor.isNull(_cursorIndexOfModifiedAt)) {
              _tmpModifiedAt = null;
            } else {
              _tmpModifiedAt = _cursor.getLong(_cursorIndexOfModifiedAt);
            }
            _item = new EliminationProtocol(_tmpId,_tmpName,_tmpStartDate,_tmpEndDate,_tmpCurrentPhase,_tmpEliminatedFoods,_tmpReintroducedFoods,_tmpPhaseStartDate,_tmpNotes,_tmpIsActive,_tmpCreatedAt,_tmpModifiedAt);
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
  public Flow<List<EliminationProtocol>> getCompleted() {
    final String _sql = "SELECT * FROM elimination_protocols WHERE isActive = 0 ORDER BY startDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"elimination_protocols"}, new Callable<List<EliminationProtocol>>() {
      @Override
      @NonNull
      public List<EliminationProtocol> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfCurrentPhase = CursorUtil.getColumnIndexOrThrow(_cursor, "currentPhase");
          final int _cursorIndexOfEliminatedFoods = CursorUtil.getColumnIndexOrThrow(_cursor, "eliminatedFoods");
          final int _cursorIndexOfReintroducedFoods = CursorUtil.getColumnIndexOrThrow(_cursor, "reintroducedFoods");
          final int _cursorIndexOfPhaseStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "phaseStartDate");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfModifiedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "modifiedAt");
          final List<EliminationProtocol> _result = new ArrayList<EliminationProtocol>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final EliminationProtocol _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final LocalDate _tmpStartDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfStartDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfStartDate);
            }
            _tmpStartDate = __localDateConverter.toLocalDate(_tmp);
            final LocalDate _tmpEndDate;
            final Long _tmp_1;
            if (_cursor.isNull(_cursorIndexOfEndDate)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getLong(_cursorIndexOfEndDate);
            }
            _tmpEndDate = __localDateConverter.toLocalDate(_tmp_1);
            final EliminationPhase _tmpCurrentPhase;
            _tmpCurrentPhase = __EliminationPhase_stringToEnum(_cursor.getString(_cursorIndexOfCurrentPhase));
            final List<String> _tmpEliminatedFoods;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfEliminatedFoods)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfEliminatedFoods);
            }
            _tmpEliminatedFoods = __stringListConverter.toStringList(_tmp_2);
            final List<String> _tmpReintroducedFoods;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfReintroducedFoods)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfReintroducedFoods);
            }
            _tmpReintroducedFoods = __stringListConverter.toStringList(_tmp_3);
            final LocalDate _tmpPhaseStartDate;
            final Long _tmp_4;
            if (_cursor.isNull(_cursorIndexOfPhaseStartDate)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getLong(_cursorIndexOfPhaseStartDate);
            }
            _tmpPhaseStartDate = __localDateConverter.toLocalDate(_tmp_4);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final boolean _tmpIsActive;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_5 != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final Long _tmpModifiedAt;
            if (_cursor.isNull(_cursorIndexOfModifiedAt)) {
              _tmpModifiedAt = null;
            } else {
              _tmpModifiedAt = _cursor.getLong(_cursorIndexOfModifiedAt);
            }
            _item = new EliminationProtocol(_tmpId,_tmpName,_tmpStartDate,_tmpEndDate,_tmpCurrentPhase,_tmpEliminatedFoods,_tmpReintroducedFoods,_tmpPhaseStartDate,_tmpNotes,_tmpIsActive,_tmpCreatedAt,_tmpModifiedAt);
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
    final String _sql = "SELECT COUNT(*) FROM elimination_protocols";
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
  public Object getActiveCount(final Continuation<? super Integer> arg0) {
    final String _sql = "SELECT COUNT(*) FROM elimination_protocols WHERE isActive = 1";
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

  private String __EliminationPhase_enumToString(@NonNull final EliminationPhase _value) {
    switch (_value) {
      case BASELINE: return "BASELINE";
      case ELIMINATION: return "ELIMINATION";
      case REINTRODUCTION: return "REINTRODUCTION";
      case MAINTENANCE: return "MAINTENANCE";
      default: throw new IllegalArgumentException("Can't convert enum to string, unknown enum value: " + _value);
    }
  }

  private EliminationPhase __EliminationPhase_stringToEnum(@NonNull final String _value) {
    switch (_value) {
      case "BASELINE": return EliminationPhase.BASELINE;
      case "ELIMINATION": return EliminationPhase.ELIMINATION;
      case "REINTRODUCTION": return EliminationPhase.REINTRODUCTION;
      case "MAINTENANCE": return EliminationPhase.MAINTENANCE;
      default: throw new IllegalArgumentException("Can't convert value to enum, unknown value: " + _value);
    }
  }
}
