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
import com.fooddiary.data.database.entities.MedicalReport;
import com.fooddiary.data.models.ReportFormat;
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
public final class MedicalReportDao_Impl implements MedicalReportDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<MedicalReport> __insertionAdapterOfMedicalReport;

  private final LocalDateConverter __localDateConverter = new LocalDateConverter();

  private final StringListConverter __stringListConverter = new StringListConverter();

  private final EntityDeletionOrUpdateAdapter<MedicalReport> __updateAdapterOfMedicalReport;

  private final SharedSQLiteStatement __preparedStmtOfDelete;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public MedicalReportDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMedicalReport = new EntityInsertionAdapter<MedicalReport>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `medical_reports` (`id`,`title`,`startDate`,`endDate`,`format`,`sections`,`filePath`,`fileSize`,`generatedAt`,`isShared`,`shareHistory`,`notes`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final MedicalReport entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getTitle() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTitle());
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
        statement.bindString(5, __ReportFormat_enumToString(entity.getFormat()));
        final String _tmp_2 = __stringListConverter.fromStringList(entity.getSections());
        if (_tmp_2 == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, _tmp_2);
        }
        if (entity.getFilePath() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getFilePath());
        }
        if (entity.getFileSize() == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, entity.getFileSize());
        }
        statement.bindLong(9, entity.getGeneratedAt());
        final int _tmp_3 = entity.isShared() ? 1 : 0;
        statement.bindLong(10, _tmp_3);
        final String _tmp_4 = __stringListConverter.fromStringList(entity.getShareHistory());
        if (_tmp_4 == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, _tmp_4);
        }
        if (entity.getNotes() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getNotes());
        }
      }
    };
    this.__updateAdapterOfMedicalReport = new EntityDeletionOrUpdateAdapter<MedicalReport>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `medical_reports` SET `id` = ?,`title` = ?,`startDate` = ?,`endDate` = ?,`format` = ?,`sections` = ?,`filePath` = ?,`fileSize` = ?,`generatedAt` = ?,`isShared` = ?,`shareHistory` = ?,`notes` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final MedicalReport entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getTitle() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTitle());
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
        statement.bindString(5, __ReportFormat_enumToString(entity.getFormat()));
        final String _tmp_2 = __stringListConverter.fromStringList(entity.getSections());
        if (_tmp_2 == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, _tmp_2);
        }
        if (entity.getFilePath() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getFilePath());
        }
        if (entity.getFileSize() == null) {
          statement.bindNull(8);
        } else {
          statement.bindLong(8, entity.getFileSize());
        }
        statement.bindLong(9, entity.getGeneratedAt());
        final int _tmp_3 = entity.isShared() ? 1 : 0;
        statement.bindLong(10, _tmp_3);
        final String _tmp_4 = __stringListConverter.fromStringList(entity.getShareHistory());
        if (_tmp_4 == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, _tmp_4);
        }
        if (entity.getNotes() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getNotes());
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
        final String _query = "DELETE FROM medical_reports WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM medical_reports";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final MedicalReport report, final Continuation<? super Long> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfMedicalReport.insertAndReturnId(report);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object update(final MedicalReport report, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfMedicalReport.handle(report);
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
  public Object getById(final String id, final Continuation<? super MedicalReport> arg1) {
    final String _sql = "SELECT * FROM medical_reports WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, id);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<MedicalReport>() {
      @Override
      @Nullable
      public MedicalReport call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfFormat = CursorUtil.getColumnIndexOrThrow(_cursor, "format");
          final int _cursorIndexOfSections = CursorUtil.getColumnIndexOrThrow(_cursor, "sections");
          final int _cursorIndexOfFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "filePath");
          final int _cursorIndexOfFileSize = CursorUtil.getColumnIndexOrThrow(_cursor, "fileSize");
          final int _cursorIndexOfGeneratedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "generatedAt");
          final int _cursorIndexOfIsShared = CursorUtil.getColumnIndexOrThrow(_cursor, "isShared");
          final int _cursorIndexOfShareHistory = CursorUtil.getColumnIndexOrThrow(_cursor, "shareHistory");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final MedicalReport _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
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
            final ReportFormat _tmpFormat;
            _tmpFormat = __ReportFormat_stringToEnum(_cursor.getString(_cursorIndexOfFormat));
            final List<String> _tmpSections;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfSections)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfSections);
            }
            _tmpSections = __stringListConverter.toStringList(_tmp_2);
            final String _tmpFilePath;
            if (_cursor.isNull(_cursorIndexOfFilePath)) {
              _tmpFilePath = null;
            } else {
              _tmpFilePath = _cursor.getString(_cursorIndexOfFilePath);
            }
            final Long _tmpFileSize;
            if (_cursor.isNull(_cursorIndexOfFileSize)) {
              _tmpFileSize = null;
            } else {
              _tmpFileSize = _cursor.getLong(_cursorIndexOfFileSize);
            }
            final long _tmpGeneratedAt;
            _tmpGeneratedAt = _cursor.getLong(_cursorIndexOfGeneratedAt);
            final boolean _tmpIsShared;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsShared);
            _tmpIsShared = _tmp_3 != 0;
            final List<String> _tmpShareHistory;
            final String _tmp_4;
            if (_cursor.isNull(_cursorIndexOfShareHistory)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getString(_cursorIndexOfShareHistory);
            }
            _tmpShareHistory = __stringListConverter.toStringList(_tmp_4);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            _result = new MedicalReport(_tmpId,_tmpTitle,_tmpStartDate,_tmpEndDate,_tmpFormat,_tmpSections,_tmpFilePath,_tmpFileSize,_tmpGeneratedAt,_tmpIsShared,_tmpShareHistory,_tmpNotes);
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
  public Flow<List<MedicalReport>> getAll() {
    final String _sql = "SELECT * FROM medical_reports ORDER BY generatedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"medical_reports"}, new Callable<List<MedicalReport>>() {
      @Override
      @NonNull
      public List<MedicalReport> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfFormat = CursorUtil.getColumnIndexOrThrow(_cursor, "format");
          final int _cursorIndexOfSections = CursorUtil.getColumnIndexOrThrow(_cursor, "sections");
          final int _cursorIndexOfFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "filePath");
          final int _cursorIndexOfFileSize = CursorUtil.getColumnIndexOrThrow(_cursor, "fileSize");
          final int _cursorIndexOfGeneratedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "generatedAt");
          final int _cursorIndexOfIsShared = CursorUtil.getColumnIndexOrThrow(_cursor, "isShared");
          final int _cursorIndexOfShareHistory = CursorUtil.getColumnIndexOrThrow(_cursor, "shareHistory");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final List<MedicalReport> _result = new ArrayList<MedicalReport>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MedicalReport _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
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
            final ReportFormat _tmpFormat;
            _tmpFormat = __ReportFormat_stringToEnum(_cursor.getString(_cursorIndexOfFormat));
            final List<String> _tmpSections;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfSections)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfSections);
            }
            _tmpSections = __stringListConverter.toStringList(_tmp_2);
            final String _tmpFilePath;
            if (_cursor.isNull(_cursorIndexOfFilePath)) {
              _tmpFilePath = null;
            } else {
              _tmpFilePath = _cursor.getString(_cursorIndexOfFilePath);
            }
            final Long _tmpFileSize;
            if (_cursor.isNull(_cursorIndexOfFileSize)) {
              _tmpFileSize = null;
            } else {
              _tmpFileSize = _cursor.getLong(_cursorIndexOfFileSize);
            }
            final long _tmpGeneratedAt;
            _tmpGeneratedAt = _cursor.getLong(_cursorIndexOfGeneratedAt);
            final boolean _tmpIsShared;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsShared);
            _tmpIsShared = _tmp_3 != 0;
            final List<String> _tmpShareHistory;
            final String _tmp_4;
            if (_cursor.isNull(_cursorIndexOfShareHistory)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getString(_cursorIndexOfShareHistory);
            }
            _tmpShareHistory = __stringListConverter.toStringList(_tmp_4);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            _item = new MedicalReport(_tmpId,_tmpTitle,_tmpStartDate,_tmpEndDate,_tmpFormat,_tmpSections,_tmpFilePath,_tmpFileSize,_tmpGeneratedAt,_tmpIsShared,_tmpShareHistory,_tmpNotes);
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
  public Flow<List<MedicalReport>> getByFormat(final ReportFormat format) {
    final String _sql = "SELECT * FROM medical_reports WHERE format = ? ORDER BY generatedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, __ReportFormat_enumToString(format));
    return CoroutinesRoom.createFlow(__db, false, new String[] {"medical_reports"}, new Callable<List<MedicalReport>>() {
      @Override
      @NonNull
      public List<MedicalReport> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfFormat = CursorUtil.getColumnIndexOrThrow(_cursor, "format");
          final int _cursorIndexOfSections = CursorUtil.getColumnIndexOrThrow(_cursor, "sections");
          final int _cursorIndexOfFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "filePath");
          final int _cursorIndexOfFileSize = CursorUtil.getColumnIndexOrThrow(_cursor, "fileSize");
          final int _cursorIndexOfGeneratedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "generatedAt");
          final int _cursorIndexOfIsShared = CursorUtil.getColumnIndexOrThrow(_cursor, "isShared");
          final int _cursorIndexOfShareHistory = CursorUtil.getColumnIndexOrThrow(_cursor, "shareHistory");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final List<MedicalReport> _result = new ArrayList<MedicalReport>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MedicalReport _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
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
            final ReportFormat _tmpFormat;
            _tmpFormat = __ReportFormat_stringToEnum(_cursor.getString(_cursorIndexOfFormat));
            final List<String> _tmpSections;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfSections)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfSections);
            }
            _tmpSections = __stringListConverter.toStringList(_tmp_2);
            final String _tmpFilePath;
            if (_cursor.isNull(_cursorIndexOfFilePath)) {
              _tmpFilePath = null;
            } else {
              _tmpFilePath = _cursor.getString(_cursorIndexOfFilePath);
            }
            final Long _tmpFileSize;
            if (_cursor.isNull(_cursorIndexOfFileSize)) {
              _tmpFileSize = null;
            } else {
              _tmpFileSize = _cursor.getLong(_cursorIndexOfFileSize);
            }
            final long _tmpGeneratedAt;
            _tmpGeneratedAt = _cursor.getLong(_cursorIndexOfGeneratedAt);
            final boolean _tmpIsShared;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsShared);
            _tmpIsShared = _tmp_3 != 0;
            final List<String> _tmpShareHistory;
            final String _tmp_4;
            if (_cursor.isNull(_cursorIndexOfShareHistory)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getString(_cursorIndexOfShareHistory);
            }
            _tmpShareHistory = __stringListConverter.toStringList(_tmp_4);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            _item = new MedicalReport(_tmpId,_tmpTitle,_tmpStartDate,_tmpEndDate,_tmpFormat,_tmpSections,_tmpFilePath,_tmpFileSize,_tmpGeneratedAt,_tmpIsShared,_tmpShareHistory,_tmpNotes);
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
  public Flow<List<MedicalReport>> getGenerated() {
    final String _sql = "SELECT * FROM medical_reports WHERE filePath IS NOT NULL ORDER BY generatedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"medical_reports"}, new Callable<List<MedicalReport>>() {
      @Override
      @NonNull
      public List<MedicalReport> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfFormat = CursorUtil.getColumnIndexOrThrow(_cursor, "format");
          final int _cursorIndexOfSections = CursorUtil.getColumnIndexOrThrow(_cursor, "sections");
          final int _cursorIndexOfFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "filePath");
          final int _cursorIndexOfFileSize = CursorUtil.getColumnIndexOrThrow(_cursor, "fileSize");
          final int _cursorIndexOfGeneratedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "generatedAt");
          final int _cursorIndexOfIsShared = CursorUtil.getColumnIndexOrThrow(_cursor, "isShared");
          final int _cursorIndexOfShareHistory = CursorUtil.getColumnIndexOrThrow(_cursor, "shareHistory");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final List<MedicalReport> _result = new ArrayList<MedicalReport>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MedicalReport _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
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
            final ReportFormat _tmpFormat;
            _tmpFormat = __ReportFormat_stringToEnum(_cursor.getString(_cursorIndexOfFormat));
            final List<String> _tmpSections;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfSections)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfSections);
            }
            _tmpSections = __stringListConverter.toStringList(_tmp_2);
            final String _tmpFilePath;
            if (_cursor.isNull(_cursorIndexOfFilePath)) {
              _tmpFilePath = null;
            } else {
              _tmpFilePath = _cursor.getString(_cursorIndexOfFilePath);
            }
            final Long _tmpFileSize;
            if (_cursor.isNull(_cursorIndexOfFileSize)) {
              _tmpFileSize = null;
            } else {
              _tmpFileSize = _cursor.getLong(_cursorIndexOfFileSize);
            }
            final long _tmpGeneratedAt;
            _tmpGeneratedAt = _cursor.getLong(_cursorIndexOfGeneratedAt);
            final boolean _tmpIsShared;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsShared);
            _tmpIsShared = _tmp_3 != 0;
            final List<String> _tmpShareHistory;
            final String _tmp_4;
            if (_cursor.isNull(_cursorIndexOfShareHistory)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getString(_cursorIndexOfShareHistory);
            }
            _tmpShareHistory = __stringListConverter.toStringList(_tmp_4);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            _item = new MedicalReport(_tmpId,_tmpTitle,_tmpStartDate,_tmpEndDate,_tmpFormat,_tmpSections,_tmpFilePath,_tmpFileSize,_tmpGeneratedAt,_tmpIsShared,_tmpShareHistory,_tmpNotes);
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
  public Flow<List<MedicalReport>> getShared() {
    final String _sql = "SELECT * FROM medical_reports WHERE isShared = 1 ORDER BY generatedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"medical_reports"}, new Callable<List<MedicalReport>>() {
      @Override
      @NonNull
      public List<MedicalReport> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "startDate");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "endDate");
          final int _cursorIndexOfFormat = CursorUtil.getColumnIndexOrThrow(_cursor, "format");
          final int _cursorIndexOfSections = CursorUtil.getColumnIndexOrThrow(_cursor, "sections");
          final int _cursorIndexOfFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "filePath");
          final int _cursorIndexOfFileSize = CursorUtil.getColumnIndexOrThrow(_cursor, "fileSize");
          final int _cursorIndexOfGeneratedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "generatedAt");
          final int _cursorIndexOfIsShared = CursorUtil.getColumnIndexOrThrow(_cursor, "isShared");
          final int _cursorIndexOfShareHistory = CursorUtil.getColumnIndexOrThrow(_cursor, "shareHistory");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final List<MedicalReport> _result = new ArrayList<MedicalReport>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final MedicalReport _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
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
            final ReportFormat _tmpFormat;
            _tmpFormat = __ReportFormat_stringToEnum(_cursor.getString(_cursorIndexOfFormat));
            final List<String> _tmpSections;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfSections)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfSections);
            }
            _tmpSections = __stringListConverter.toStringList(_tmp_2);
            final String _tmpFilePath;
            if (_cursor.isNull(_cursorIndexOfFilePath)) {
              _tmpFilePath = null;
            } else {
              _tmpFilePath = _cursor.getString(_cursorIndexOfFilePath);
            }
            final Long _tmpFileSize;
            if (_cursor.isNull(_cursorIndexOfFileSize)) {
              _tmpFileSize = null;
            } else {
              _tmpFileSize = _cursor.getLong(_cursorIndexOfFileSize);
            }
            final long _tmpGeneratedAt;
            _tmpGeneratedAt = _cursor.getLong(_cursorIndexOfGeneratedAt);
            final boolean _tmpIsShared;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsShared);
            _tmpIsShared = _tmp_3 != 0;
            final List<String> _tmpShareHistory;
            final String _tmp_4;
            if (_cursor.isNull(_cursorIndexOfShareHistory)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getString(_cursorIndexOfShareHistory);
            }
            _tmpShareHistory = __stringListConverter.toStringList(_tmp_4);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            _item = new MedicalReport(_tmpId,_tmpTitle,_tmpStartDate,_tmpEndDate,_tmpFormat,_tmpSections,_tmpFilePath,_tmpFileSize,_tmpGeneratedAt,_tmpIsShared,_tmpShareHistory,_tmpNotes);
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
    final String _sql = "SELECT COUNT(*) FROM medical_reports";
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
  public Object getGeneratedCount(final Continuation<? super Integer> arg0) {
    final String _sql = "SELECT COUNT(*) FROM medical_reports WHERE filePath IS NOT NULL";
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
  public Object getTotalFileSize(final Continuation<? super Long> arg0) {
    final String _sql = "SELECT SUM(COALESCE(fileSize, 0)) FROM medical_reports WHERE filePath IS NOT NULL";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Long _result;
          if (_cursor.moveToFirst()) {
            final Long _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(0);
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

  private String __ReportFormat_enumToString(@NonNull final ReportFormat _value) {
    switch (_value) {
      case PDF: return "PDF";
      case JSON: return "JSON";
      default: throw new IllegalArgumentException("Can't convert enum to string, unknown enum value: " + _value);
    }
  }

  private ReportFormat __ReportFormat_stringToEnum(@NonNull final String _value) {
    switch (_value) {
      case "PDF": return ReportFormat.PDF;
      case "JSON": return ReportFormat.JSON;
      default: throw new IllegalArgumentException("Can't convert value to enum, unknown value: " + _value);
    }
  }
}
