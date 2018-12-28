package com.lei.address.addressapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Vincent.Lei on 2018/12/27.
 * Title：
 * Note：
 */
public class DBOuter {


    private static class DbHelper extends SQLiteOpenHelper {
        DbHelper(Context context) {
            this(context, Address.DB_NAME, null, Address.DB_VERSION);
        }

        DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String sql = "CREATE TABLE " + Address.TABLE_NAME + " ("
                    + Address.COL_CODE + " INTEGER PRIMARY KEY ,"
                    + Address.COL_NAME + " TEXT ,"
                    + Address.COL_EN_NAME + " TEXT ,"
                    + Address.COL_SHORT_NAME + " TEXT ,"
                    + Address.COL_LEVEL + " INTEGER ,"
                    + Address.COL_PARENT_CODE + " INTEGER"
                    + " )";
            db.execSQL(sql);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    private static DBOuter mInstance;

    public static DBOuter getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DBOuter.class) {
                if (mInstance == null && context != null) {
                    mInstance = new DBOuter(context.getApplicationContext());
                }
            }
        }
        return mInstance;
    }

    private DbHelper mDbHelper;
    private SQLiteDatabase mDb;

    private DBOuter(Context context) {
        mDbHelper = new DbHelper(context);
    }

    public SQLiteDatabase openDataBase() {
        if (mDb == null || !mDb.isOpen())
            mDb = mDbHelper.getWritableDatabase();
        return mDb;
    }

    public void closeDataBase() {
        if (mDb != null && mDb.isOpen())
            mDb.close();
        mDb = null;
    }

    public SQLiteDatabase getDataBase() {
        if (mDb == null || !mDb.isOpen())
            openDataBase();
        return mDb;
    }

    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return mDb.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
    }

    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        return mDb.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
    }

    public Cursor query(String sql) {
        return mDb.rawQuery(sql, null);
    }

    public int update(String table, ContentValues values, String whereClause, String[] whereArgs) {
        return mDb.update(table, values, whereClause, whereArgs);
    }

    public void update(String sql) {
        mDb.execSQL(sql);
    }

    public long insert(String table, ContentValues values) {
        return mDb.insert(table, null, values);
    }

    public void insert(String sql) {
        mDb.execSQL(sql);
    }

    public int delete(String table, String whereClause, String[] whereArgs) {
        return mDb.delete(table, whereClause, whereArgs);
    }

    public void delete(String sql) {
        mDb.execSQL(sql);
    }

    public void beginTransaction() {
        mDb.beginTransaction();
    }

    public void setTransactionSuccessful() {
        mDb.setTransactionSuccessful();
    }

    public void endTransaction() {
        mDb.endTransaction();
    }


}
