package com.ircarren.ghkanban.data.localDataBase.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.ircarren.ghkanban.util.DATABASE_NAME
import com.ircarren.ghkanban.util.DATABASE_VERSION

class DatabaseHelper(
    context: Context,
    factory: SQLiteDatabase.CursorFactory?
) : SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_ISSUE_TABLE = ("CREATE TABLE " +
                "issue" + "("
                + "id" + " INTEGER PRIMARY KEY," +
                "title" + " TEXT," +
                "body" + " TEXT," +
                "status" + " TEXT," +
                "repo" + " TEXT" + ")")
        db?.execSQL(CREATE_ISSUE_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS issue")
        onCreate(db)
    }


}