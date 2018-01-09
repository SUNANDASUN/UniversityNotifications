package com.indianservers.rrbexams.database

import android.content.Context
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper

class DatabaseAssetHelperTest(val context:Context): SQLiteAssetHelper(context,"rrbdata.db",null,2) {

    // RRB table name
    val TABLE_SUBJECTS = "Subjects"
    val TABLE_TOPICS = "Topics"
    val TABLE_SETS = "SETS"
    val TABLE_QUESTIONS = "Questions"
    val TABLE_FORMULAS = "Formulas"
    // Subject Table Columns names
    val KEY_SID = "SubjectID"
    val KEY_ID = "ID"
    val KEY_SNAME = "SubjectName"
    // TOPICS Table Columns names
    val KEY_TID = "TID"
    val KEY_TSID = "SUBID"
    val KEY_TNAME = "TNAME"
    val KEY_TSTATUS = "STATUS"
    val KEY_TQ = "TQ"

    // SETS Table Columns names
    val KEY_PRMKEY = "PrmKey"
    val KEY_STID = "TopicID"
    val KEY_SETID = "SETID"
    val KEY_SETNAME = "SetName"
    // QUESTIONS Table Columns names
    val KEY_QID = "QID"
    val KEY_QSETID = "SetID"
    // public static final String KEY_SSTATUS = "STATUS";
    // public static final String KEY_STQ = "TQ";
    val KEY_QTID = "TopicID"
    // public static final String KEY_QSID = "SID";
    val KEY_QUES = "Questions"
    val KEY_OPT1 = "Options1"
    val KEY_OPT2 = "Options2"
    val KEY_OPT3 = "Options3"
    val KEY_OPT4 = "Options4"
    val KEY_CORRECT = "Answer"
    val KEY_FAV = "isFav"
    val KEY_DESC = "Desc"

    // Formulas Table Columns names
    val KEY_prmkey = "prmkey"
    val KEY_FTOPIC = "Topic_Name"
    val KEY_FORMULA = "Formula"

    // Database Version
      val DATABASE_VERSION = 3
    // Database Name
    val DATABASE_NAME = "rrbdata.db"



}