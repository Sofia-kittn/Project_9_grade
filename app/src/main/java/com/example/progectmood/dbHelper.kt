package com.example.progectmood

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class dbHelper(val context: Context):
    SQLiteOpenHelper(context, "app", null, 1){

    override fun onCreate(db: SQLiteDatabase?) {
        val quary = "CREATE TABLE notes()"

    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
        TODO("Not yet implemented")
    }
}