package com.example.progectmood.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Goal::class, Note::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoDao(): GoalDao
    abstract fun noteDao(): NoteDao
}