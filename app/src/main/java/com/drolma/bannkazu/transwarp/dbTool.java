package com.drolma.bannkazu.transwarp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Drolma on 16/5/19.
 * 不完全是自己写的db，除了初始化、升级方法被重写外，其余的均继承自SQLiteOpenHelper
 */
public class dbTool extends SQLiteOpenHelper{

    private static  final String TAG = "dbTool";
    private static final String DATABASE_NAME = "scoredb.db";
    private static final int DATABASE_VERSION = 1;

    public dbTool(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // 数据库第一次被创建时onCreate会被调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS screcord" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "dateTime VARCHAR, " +
                "pScore1 VARCHAR, " +
                "pScore2 VARCHAR, " +
                "pScore3 VARCHAR, " +
                "pScore4 VARCHAR, " +
                "pScore5 VARCHAR, " +
                "isLLWin INTEGER, " +
                "isAutoInsert INTEGER," +
                "llt1 INTEGER," +
                "llt2 INTEGER," +
                "llt3 INTEGER," +
                "llt4 INTEGER," +
                "llt5 INTEGER," +
                "wit1 INTEGER," +
                "wit2 INTEGER," +
                "wit3 INTEGER," +
                "wit4 INTEGER," +
                "wit5 INTEGER)");
        db.execSQL("CREATE TABLE IF NOT EXISTS screcord3" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "dateTime VARCHAR, " +
                "pScore1 VARCHAR, " +
                "pScore2 VARCHAR, " +
                "pScore3 VARCHAR, " +
                "isLLWin INTEGER, " +
                "isAutoInsert INTEGER," +
                "llt1 INTEGER," +
                "llt2 INTEGER," +
                "llt3 INTEGER," +
                "wit1 INTEGER," +
                "wit2 INTEGER," +
                "wit3 INTEGER)");
        Log.w(TAG, ">>> Database is initialled");
    }

    // 如果DATABASE_VERSION值被改为2,系统发现现有数据库版本不同,即会调用onUpgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE screcord ADD COLUMN other STRING");
        db.execSQL("ALTER TABLE screcord3 ADD COLUMN other STRING");
        Log.w(TAG, ">>> Database is onUpgraded");
    }

//    public void cleanAllTable(SQLiteDatabase db, int tableName) {
//        if (tableName == 5) {
//            db.execSQL("DROP TABLE IF EXISTS screcord");
//            Log.w(TAG, ">>> Table screcord is DROPPED!!!");
//        } else if (tableName == 3) {
//            db.execSQL("DROP TABLE screcord3");
//            Log.w(TAG, ">>> Table screcord is DROPPED!!!");
//        }
//    }


}
