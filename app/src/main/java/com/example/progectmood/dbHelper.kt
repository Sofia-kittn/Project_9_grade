package com.example.progectmood

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast

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

    fun addNote(note: EmotionNote){

    }

    fun delNote(note: EmotionNote){

    }

    fun addGoal(goal: Goal){

    }

    fun delGoal(goal: Goal){

    }
}