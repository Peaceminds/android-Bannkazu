package com.drolma.bannkazu.transwarp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Drolma on 16/5/19.
 */
public class ormTool {

    private static final String TAG = "ormTool";
    private dbTool dbHelper;
    private SQLiteDatabase db;


    public ormTool(Context context) {
        dbHelper = new dbTool(context);
        db = dbHelper.getReadableDatabase();
        Log.w(TAG, ">>> DB is initialled and connected");
    }


    public void addRec(List<modelTool> models, int flag) {
        if (flag == 5) {
            db.beginTransaction();
            try {
                for (modelTool mdt : models) {
                    db.execSQL("INSERT INTO screcord VALUES(NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                            new Object[]{
                                    mdt.dateTime,
                                    mdt.pScore1,
                                    mdt.pScore2,
                                    mdt.pScore3,
                                    mdt.pScore4,
                                    mdt.pScore5,
                                    mdt.isLLWin,
                                    mdt.isAutoInsert,
                                    mdt.llt1,
                                    mdt.llt2,
                                    mdt.llt3,
                                    mdt.llt4,
                                    mdt.llt5,
                                    mdt.wit1,
                                    mdt.wit2,
                                    mdt.wit3,
                                    mdt.wit4,
                                    mdt.wit5
                            });
                    Log.w(TAG, ">>> mdt === " + mdt.dateTime +","+ mdt.pScore1 +","+
                            mdt.pScore2 +","+ mdt.pScore3 +","+ mdt.pScore4 +","+
                            mdt.pScore5 +","+ mdt.isLLWin +","+ mdt.isAutoInsert +" | "+
                            mdt.llt1 +","+ mdt.llt2 +","+ mdt.llt3 +","+ mdt.llt4 +","+ mdt.llt5 +" | "+
                            mdt.wit1 +","+ mdt.wit2 +","+ mdt.wit3 +","+ mdt.wit4 +","+ mdt.wit5);
                }
                db.setTransactionSuccessful();
                Log.w(TAG, ">>> addRec is success : )");
            }
            catch (Throwable e) {
                e.printStackTrace();
                Log.w(TAG, ">>> addRec is failed : (");
            }
            finally {
                db.endTransaction();
            }

        } else

        if (flag == 3) {
            db.beginTransaction();
            try {
                for (modelTool mdt : models) {
                    db.execSQL("INSERT INTO screcord3 VALUES(NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                            new Object[]{
                                    mdt.dateTime,
                                    mdt.pScore1,
                                    mdt.pScore2,
                                    mdt.pScore3,
                                    mdt.isLLWin,
                                    mdt.isAutoInsert,
                                    mdt.llt1,
                                    mdt.llt2,
                                    mdt.llt3,
                                    mdt.wit1,
                                    mdt.wit2,
                                    mdt.wit3
                            });
                    Log.w(TAG, ">>> mdt === " + mdt.dateTime +","+ mdt.pScore1 +","+
                            mdt.pScore2 +","+ mdt.pScore3 +","+ mdt.isLLWin +","+
                            mdt.isAutoInsert +" | "+ mdt.llt1 +","+ mdt.llt2 +","+ mdt.llt3 +" | "+
                            mdt.wit1 +","+ mdt.wit2 +","+ mdt.wit3);
                }
                db.setTransactionSuccessful();
                Log.w(TAG, ">>> addRec is success : )");
            }
            catch (Throwable e) {
                e.printStackTrace();
                Log.w(TAG, ">>> addRec is failed : (");
            }
            finally {
                db.endTransaction();
            }
        }

    }


    public List<modelTool> queryRec(int flag) {
        ArrayList<modelTool> imodels = new ArrayList<modelTool>();
        if (flag == 5) {
            Cursor c = theQueryCursor5();
            while (c.moveToNext()) {
                modelTool imdt = new modelTool();
                imdt._id = c.getInt(c.getColumnIndex("_id"));
                imdt.dateTime = c.getString(c.getColumnIndex("dateTime"));
                imdt.pScore1 = c.getString(c.getColumnIndex("pScore1"));
                imdt.pScore2 = c.getString(c.getColumnIndex("pScore2"));
                imdt.pScore3 = c.getString(c.getColumnIndex("pScore3"));
                imdt.pScore4 = c.getString(c.getColumnIndex("pScore4"));
                imdt.pScore5 = c.getString(c.getColumnIndex("pScore5"));
                imdt.isLLWin = c.getInt(c.getColumnIndex("isLLWin"));
                imdt.isAutoInsert = c.getInt(c.getColumnIndex("isAutoInsert"));
                imdt.llt1 = c.getInt(c.getColumnIndex("llt1"));
                imdt.llt2 = c.getInt(c.getColumnIndex("llt2"));
                imdt.llt3 = c.getInt(c.getColumnIndex("llt3"));
                imdt.llt4 = c.getInt(c.getColumnIndex("llt4"));
                imdt.llt5 = c.getInt(c.getColumnIndex("llt5"));
                imdt.wit1 = c.getInt(c.getColumnIndex("wit1"));
                imdt.wit2 = c.getInt(c.getColumnIndex("wit2"));
                imdt.wit3 = c.getInt(c.getColumnIndex("wit3"));
                imdt.wit4 = c.getInt(c.getColumnIndex("wit4"));
                imdt.wit5 = c.getInt(c.getColumnIndex("wit5"));
                imodels.add(imdt);
                Log.w(TAG, ">>> Load imdt === " + imdt.dateTime +","+ imdt.pScore1 +","+ imdt.pScore2 +","+
                        imdt.pScore3 +","+ imdt.pScore4 +","+ imdt.pScore5 +","+ imdt.isLLWin +","+ imdt.isAutoInsert +" | "+
                        imdt.llt1 +","+ imdt.llt2 +","+ imdt.llt3 +","+ imdt.llt4 +","+ imdt.llt5 +" | "+
                        imdt.wit1 +","+ imdt.wit2 +","+ imdt.wit3 +","+ imdt.wit4 +","+ imdt.wit5 );
            }
            Log.w(TAG, ">>> a query is completed ~");
            c.close();

        } else

        if (flag == 3) {
            Cursor c = theQueryCursor3();
            while (c.moveToNext()) {
                modelTool imdt = new modelTool();
                imdt._id = c.getInt(c.getColumnIndex("_id"));
                imdt.dateTime = c.getString(c.getColumnIndex("dateTime"));
                imdt.pScore1 = c.getString(c.getColumnIndex("pScore1"));
                imdt.pScore2 = c.getString(c.getColumnIndex("pScore2"));
                imdt.pScore3 = c.getString(c.getColumnIndex("pScore3"));
                imdt.isLLWin = c.getInt(c.getColumnIndex("isLLWin"));
                imdt.isAutoInsert = c.getInt(c.getColumnIndex("isAutoInsert"));
                imdt.llt1 = c.getInt(c.getColumnIndex("llt1"));
                imdt.llt2 = c.getInt(c.getColumnIndex("llt2"));
                imdt.llt3 = c.getInt(c.getColumnIndex("llt3"));
                imdt.wit1 = c.getInt(c.getColumnIndex("wit1"));
                imdt.wit2 = c.getInt(c.getColumnIndex("wit2"));
                imdt.wit3 = c.getInt(c.getColumnIndex("wit3"));
                imodels.add(imdt);
                Log.w(TAG, ">>> Load imdt === " + imdt.dateTime +","+ imdt.pScore1 +","+ imdt.pScore2 +","+
                        imdt.pScore3 +","+ imdt.isLLWin +","+ imdt.isAutoInsert +" | "+
                        imdt.llt1 +","+ imdt.llt2 +","+ imdt.llt3 +" | "+
                        imdt.wit1 +","+ imdt.wit2 +","+ imdt.wit3);
            }
            Log.w(TAG, ">>> a query is completed ~");
            c.close();
        }

        return imodels;
    }

    private Cursor theQueryCursor5() {
        Cursor c = db.rawQuery("SELECT * FROM screcord", null);
        return c;
    }

    private Cursor theQueryCursor3() {
        Cursor c = db.rawQuery("SELECT * FROM screcord3", null);
        return c;
    }

    public void cleanDBTable (int i) {
        if (i == 5) {
            db.delete("screcord", null, null);
            Log.w(TAG, ">>> Table screcord is DROPPED!!!");
        } else if (i == 3) {
            db.delete("screcord3", null, null);
            Log.w(TAG, ">>> Table screcord3 is DROPPED!!!");
        }
        //        dbHelper.cleanAllTable(db, i);
    }

    public void closeDB() {
        db.close();
        Log.w(TAG, ">>> DB connection is closed");
    }

}
