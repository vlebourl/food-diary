package com.fooddiary.data.database.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomDatabaseKt;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.fooddiary.data.database.converters.StringMapConverter;
import com.fooddiary.data.database.entities.QuickEntryTemplate;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@SuppressWarnings({"unchecked", "deprecation"})
public final class QuickEntryTemplateDao_Impl implements QuickEntryTemplateDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<QuickEntryTemplate> __insertionAdapterOfQuickEntryTemplate;

  private final StringMapConverter __stringMapConverter = new StringMapConverter();

  private final EntityDeletionOrUpdateAdapter<QuickEntryTemplate> __updateAdapterOfQuickEntryTemplate;

  private final SharedSQLiteStatement __preparedStmtOfDelete;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public QuickEntryTemplateDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfQuickEntryTemplate = new EntityInsertionAdapter<QuickEntryTemplate>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `quick_entry_templates` (`id`,`name`,`entryType`,`defaultData`,`buttonColor`,`buttonIcon`,`isActive`,`sortOrder`,`createdAt`,`modifiedAt`) VALUES (?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final QuickEntryTemplate entity) {
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
        if (entity.getEntryType() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getEntryType());
        }
        final String _tmp = __stringMapConverter.fromStringMap(entity.getDefaultData());
        if (_tmp == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, _tmp);
        }
        if (entity.getButtonColor() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getButtonColor());
        }
        if (entity.getButtonIcon() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getButtonIcon());
        }
        final int _tmp_1 = entity.isActive() ? 1 : 0;
        statement.bindLong(7, _tmp_1);
        statement.bindLong(8, entity.getSortOrder());
        statement.bindLong(9, entity.getCreatedAt());
        if (entity.getModifiedAt() == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, entity.getModifiedAt());
        }
      }
    };
    this.__updateAdapterOfQuickEntryTemplate = new EntityDeletionOrUpdateAdapter<QuickEntryTemplate>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `quick_entry_templates` SET `id` = ?,`name` = ?,`entryType` = ?,`defaultData` = ?,`buttonColor` = ?,`buttonIcon` = ?,`isActive` = ?,`sortOrder` = ?,`createdAt` = ?,`modifiedAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final QuickEntryTemplate entity) {
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
        if (entity.getEntryType() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getEntryType());
        }
        final String _tmp = __stringMapConverter.fromStringMap(entity.getDefaultData());
        if (_tmp == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, _tmp);
        }
        if (entity.getButtonColor() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getButtonColor());
        }
        if (entity.getButtonIcon() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getButtonIcon());
        }
        final int _tmp_1 = entity.isActive() ? 1 : 0;
        statement.bindLong(7, _tmp_1);
        statement.bindLong(8, entity.getSortOrder());
        statement.bindLong(9, entity.getCreatedAt());
        if (entity.getModifiedAt() == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, entity.getModifiedAt());
        }
        if (entity.getId() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getId());
        }
      }
    };
    this.__preparedStmtOfDelete = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM quick_entry_templates WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM quick_entry_templates";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final QuickEntryTemplate template, final Continuation<? super Long> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfQuickEntryTemplate.insertAndReturnId(template);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object update(final QuickEntryTemplate template, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfQuickEntryTemplate.handle(template);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object reorder(final List<QuickEntryTemplate> templates,
      final Continuation<? super Unit> arg1) {
    return RoomDatabaseKt.withTransaction(__db, (__cont) -> QuickEntryTemplateDao.DefaultImpls.reorder(QuickEntryTemplateDao_Impl.this, templates, __cont), arg1);
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
  public Object getById(final String id, final Continuation<? super QuickEntryTemplate> arg1) {
    final String _sql = "SELECT * FROM quick_entry_templates WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, id);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<QuickEntryTemplate>() {
      @Override
      @Nullable
      public QuickEntryTemplate call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfEntryType = CursorUtil.getColumnIndexOrThrow(_cursor, "entryType");
          final int _cursorIndexOfDefaultData = CursorUtil.getColumnIndexOrThrow(_cursor, "defaultData");
          final int _cursorIndexOfButtonColor = CursorUtil.getColumnIndexOrThrow(_cursor, "buttonColor");
          final int _cursorIndexOfButtonIcon = CursorUtil.getColumnIndexOrThrow(_cursor, "buttonIcon");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfSortOrder = CursorUtil.getColumnIndexOrThrow(_cursor, "sortOrder");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfModifiedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "modifiedAt");
          final QuickEntryTemplate _result;
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
            final String _tmpEntryType;
            if (_cursor.isNull(_cursorIndexOfEntryType)) {
              _tmpEntryType = null;
            } else {
              _tmpEntryType = _cursor.getString(_cursorIndexOfEntryType);
            }
            final Map<String, String> _tmpDefaultData;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfDefaultData)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfDefaultData);
            }
            _tmpDefaultData = __stringMapConverter.toStringMap(_tmp);
            final String _tmpButtonColor;
            if (_cursor.isNull(_cursorIndexOfButtonColor)) {
              _tmpButtonColor = null;
            } else {
              _tmpButtonColor = _cursor.getString(_cursorIndexOfButtonColor);
            }
            final String _tmpButtonIcon;
            if (_cursor.isNull(_cursorIndexOfButtonIcon)) {
              _tmpButtonIcon = null;
            } else {
              _tmpButtonIcon = _cursor.getString(_cursorIndexOfButtonIcon);
            }
            final boolean _tmpIsActive;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_1 != 0;
            final int _tmpSortOrder;
            _tmpSortOrder = _cursor.getInt(_cursorIndexOfSortOrder);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final Long _tmpModifiedAt;
            if (_cursor.isNull(_cursorIndexOfModifiedAt)) {
              _tmpModifiedAt = null;
            } else {
              _tmpModifiedAt = _cursor.getLong(_cursorIndexOfModifiedAt);
            }
            _result = new QuickEntryTemplate(_tmpId,_tmpName,_tmpEntryType,_tmpDefaultData,_tmpButtonColor,_tmpButtonIcon,_tmpIsActive,_tmpSortOrder,_tmpCreatedAt,_tmpModifiedAt);
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
  public Flow<List<QuickEntryTemplate>> getAllActive() {
    final String _sql = "SELECT * FROM quick_entry_templates WHERE isActive = 1 ORDER BY sortOrder ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"quick_entry_templates"}, new Callable<List<QuickEntryTemplate>>() {
      @Override
      @NonNull
      public List<QuickEntryTemplate> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfEntryType = CursorUtil.getColumnIndexOrThrow(_cursor, "entryType");
          final int _cursorIndexOfDefaultData = CursorUtil.getColumnIndexOrThrow(_cursor, "defaultData");
          final int _cursorIndexOfButtonColor = CursorUtil.getColumnIndexOrThrow(_cursor, "buttonColor");
          final int _cursorIndexOfButtonIcon = CursorUtil.getColumnIndexOrThrow(_cursor, "buttonIcon");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfSortOrder = CursorUtil.getColumnIndexOrThrow(_cursor, "sortOrder");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfModifiedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "modifiedAt");
          final List<QuickEntryTemplate> _result = new ArrayList<QuickEntryTemplate>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final QuickEntryTemplate _item;
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
            final String _tmpEntryType;
            if (_cursor.isNull(_cursorIndexOfEntryType)) {
              _tmpEntryType = null;
            } else {
              _tmpEntryType = _cursor.getString(_cursorIndexOfEntryType);
            }
            final Map<String, String> _tmpDefaultData;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfDefaultData)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfDefaultData);
            }
            _tmpDefaultData = __stringMapConverter.toStringMap(_tmp);
            final String _tmpButtonColor;
            if (_cursor.isNull(_cursorIndexOfButtonColor)) {
              _tmpButtonColor = null;
            } else {
              _tmpButtonColor = _cursor.getString(_cursorIndexOfButtonColor);
            }
            final String _tmpButtonIcon;
            if (_cursor.isNull(_cursorIndexOfButtonIcon)) {
              _tmpButtonIcon = null;
            } else {
              _tmpButtonIcon = _cursor.getString(_cursorIndexOfButtonIcon);
            }
            final boolean _tmpIsActive;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_1 != 0;
            final int _tmpSortOrder;
            _tmpSortOrder = _cursor.getInt(_cursorIndexOfSortOrder);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final Long _tmpModifiedAt;
            if (_cursor.isNull(_cursorIndexOfModifiedAt)) {
              _tmpModifiedAt = null;
            } else {
              _tmpModifiedAt = _cursor.getLong(_cursorIndexOfModifiedAt);
            }
            _item = new QuickEntryTemplate(_tmpId,_tmpName,_tmpEntryType,_tmpDefaultData,_tmpButtonColor,_tmpButtonIcon,_tmpIsActive,_tmpSortOrder,_tmpCreatedAt,_tmpModifiedAt);
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
  public Flow<List<QuickEntryTemplate>> getAll() {
    final String _sql = "SELECT * FROM quick_entry_templates ORDER BY sortOrder ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"quick_entry_templates"}, new Callable<List<QuickEntryTemplate>>() {
      @Override
      @NonNull
      public List<QuickEntryTemplate> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfEntryType = CursorUtil.getColumnIndexOrThrow(_cursor, "entryType");
          final int _cursorIndexOfDefaultData = CursorUtil.getColumnIndexOrThrow(_cursor, "defaultData");
          final int _cursorIndexOfButtonColor = CursorUtil.getColumnIndexOrThrow(_cursor, "buttonColor");
          final int _cursorIndexOfButtonIcon = CursorUtil.getColumnIndexOrThrow(_cursor, "buttonIcon");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfSortOrder = CursorUtil.getColumnIndexOrThrow(_cursor, "sortOrder");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfModifiedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "modifiedAt");
          final List<QuickEntryTemplate> _result = new ArrayList<QuickEntryTemplate>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final QuickEntryTemplate _item;
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
            final String _tmpEntryType;
            if (_cursor.isNull(_cursorIndexOfEntryType)) {
              _tmpEntryType = null;
            } else {
              _tmpEntryType = _cursor.getString(_cursorIndexOfEntryType);
            }
            final Map<String, String> _tmpDefaultData;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfDefaultData)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfDefaultData);
            }
            _tmpDefaultData = __stringMapConverter.toStringMap(_tmp);
            final String _tmpButtonColor;
            if (_cursor.isNull(_cursorIndexOfButtonColor)) {
              _tmpButtonColor = null;
            } else {
              _tmpButtonColor = _cursor.getString(_cursorIndexOfButtonColor);
            }
            final String _tmpButtonIcon;
            if (_cursor.isNull(_cursorIndexOfButtonIcon)) {
              _tmpButtonIcon = null;
            } else {
              _tmpButtonIcon = _cursor.getString(_cursorIndexOfButtonIcon);
            }
            final boolean _tmpIsActive;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_1 != 0;
            final int _tmpSortOrder;
            _tmpSortOrder = _cursor.getInt(_cursorIndexOfSortOrder);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final Long _tmpModifiedAt;
            if (_cursor.isNull(_cursorIndexOfModifiedAt)) {
              _tmpModifiedAt = null;
            } else {
              _tmpModifiedAt = _cursor.getLong(_cursorIndexOfModifiedAt);
            }
            _item = new QuickEntryTemplate(_tmpId,_tmpName,_tmpEntryType,_tmpDefaultData,_tmpButtonColor,_tmpButtonIcon,_tmpIsActive,_tmpSortOrder,_tmpCreatedAt,_tmpModifiedAt);
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
  public Flow<List<QuickEntryTemplate>> getByType(final String type) {
    final String _sql = "SELECT * FROM quick_entry_templates WHERE entryType = ? AND isActive = 1 ORDER BY sortOrder ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (type == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, type);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"quick_entry_templates"}, new Callable<List<QuickEntryTemplate>>() {
      @Override
      @NonNull
      public List<QuickEntryTemplate> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfEntryType = CursorUtil.getColumnIndexOrThrow(_cursor, "entryType");
          final int _cursorIndexOfDefaultData = CursorUtil.getColumnIndexOrThrow(_cursor, "defaultData");
          final int _cursorIndexOfButtonColor = CursorUtil.getColumnIndexOrThrow(_cursor, "buttonColor");
          final int _cursorIndexOfButtonIcon = CursorUtil.getColumnIndexOrThrow(_cursor, "buttonIcon");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "isActive");
          final int _cursorIndexOfSortOrder = CursorUtil.getColumnIndexOrThrow(_cursor, "sortOrder");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfModifiedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "modifiedAt");
          final List<QuickEntryTemplate> _result = new ArrayList<QuickEntryTemplate>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final QuickEntryTemplate _item;
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
            final String _tmpEntryType;
            if (_cursor.isNull(_cursorIndexOfEntryType)) {
              _tmpEntryType = null;
            } else {
              _tmpEntryType = _cursor.getString(_cursorIndexOfEntryType);
            }
            final Map<String, String> _tmpDefaultData;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfDefaultData)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfDefaultData);
            }
            _tmpDefaultData = __stringMapConverter.toStringMap(_tmp);
            final String _tmpButtonColor;
            if (_cursor.isNull(_cursorIndexOfButtonColor)) {
              _tmpButtonColor = null;
            } else {
              _tmpButtonColor = _cursor.getString(_cursorIndexOfButtonColor);
            }
            final String _tmpButtonIcon;
            if (_cursor.isNull(_cursorIndexOfButtonIcon)) {
              _tmpButtonIcon = null;
            } else {
              _tmpButtonIcon = _cursor.getString(_cursorIndexOfButtonIcon);
            }
            final boolean _tmpIsActive;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_1 != 0;
            final int _tmpSortOrder;
            _tmpSortOrder = _cursor.getInt(_cursorIndexOfSortOrder);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final Long _tmpModifiedAt;
            if (_cursor.isNull(_cursorIndexOfModifiedAt)) {
              _tmpModifiedAt = null;
            } else {
              _tmpModifiedAt = _cursor.getLong(_cursorIndexOfModifiedAt);
            }
            _item = new QuickEntryTemplate(_tmpId,_tmpName,_tmpEntryType,_tmpDefaultData,_tmpButtonColor,_tmpButtonIcon,_tmpIsActive,_tmpSortOrder,_tmpCreatedAt,_tmpModifiedAt);
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
  public Object getActiveCount(final Continuation<? super Integer> arg0) {
    final String _sql = "SELECT COUNT(*) FROM quick_entry_templates WHERE isActive = 1";
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
  public Object getMaxSortOrder(final Continuation<? super Integer> arg0) {
    final String _sql = "SELECT MAX(sortOrder) FROM quick_entry_templates WHERE isActive = 1";
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
    }, arg0);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
