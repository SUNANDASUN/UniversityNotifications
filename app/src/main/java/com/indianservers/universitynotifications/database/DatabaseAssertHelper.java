package com.indianservers.universitynotifications.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;


public class DatabaseAssertHelper extends SQLiteAssetHelper {
    // RRB table name
    public static final String TABLE_SUBJECTS = "Subjects";
    public static final String TABLE_TOPICS = "Topics";
    public static final String TABLE_SETS = "SETS";
    public static final String TABLE_QUESTIONS = "Questions";
    public static final String TABLE_FORMULAS = "Formulas";
    // Subject Table Columns names
    public static final String KEY_SID = "SubjectID";
    public static final String KEY_ID = "ID";
    public static final String KEY_SNAME = "SubjectName";
    // TOPICS Table Columns names
    public static final String KEY_TID = "TID";
    public static final String KEY_TSID = "SUBID";
    public static final String KEY_TNAME = "TNAME";
    public static final String KEY_TSTATUS = "STATUS";
    public static final String KEY_TQ = "TQ";

    // SETS Table Columns names
    public static final String KEY_PRMKEY = "PrmKey";
    public static final String KEY_STID = "TopicID";
    public static final String KEY_SETID = "SETID";
    public static final String KEY_SETNAME = "SetName";
    // QUESTIONS Table Columns names
    public static final String KEY_QID = "QID";
    public static final String KEY_QSETID = "SetID";
    // public static final String KEY_SSTATUS = "STATUS";
    // public static final String KEY_STQ = "TQ";
    public static final String KEY_QTID = "TopicID";
    // public static final String KEY_QSID = "SID";
    public static final String KEY_QUES = "Questions";
    public static final String KEY_OPT1 = "Options1";
    public static final String KEY_OPT2 = "Options2";
    public static final String KEY_OPT3 = "Options3";
    public static final String KEY_OPT4 = "Options4";
    public static final String KEY_CORRECT = "Answer";
    public static final String KEY_FAV = "isFav";
    public static final String KEY_DESC = "Desc";

    // Formulas Table Columns names
    public static final String KEY_prmkey = "prmkey";
    public static final String KEY_FTOPIC = "Topic_Name";
    public static final String KEY_FORMULA = "Formula";

    // Database Version
    private static final int DATABASE_VERSION = 2;
    // Database Name
    private static final String DATABASE_NAME = "rrbdata.db";




    public DatabaseAssertHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void updateFavValue(int favValue, int questionId) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Log.v("sai", "fav value in update function-1111------------" + favValue);
        values.put(KEY_FAV, favValue);

        String selection1 = KEY_QID + "=?";
        Log.v("sai", "fav value in update function-------------" + favValue);
        // String[] selectionArgs1 = {String.valueOf(QID)};
        Log.v("sai", "Values value in update function-----2525--------" + values.get(KEY_FAV));
        if (questionId != 0) {
            int rowId = db.update(TABLE_QUESTIONS, values, selection1, new String[]{String.valueOf(questionId)});
            Log.v("sai", "Row Id in update function-------------" + rowId);
        }


    }
}
