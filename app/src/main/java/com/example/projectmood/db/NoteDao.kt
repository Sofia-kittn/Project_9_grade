package com.example.projectmood.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.projectmood.notes.Note

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes ORDER BY date DESC")
    fun getAll(): List<Note>

//    @Query("SELECT * FROM notes WHERE tags LIKE :tag") // TODO: search single tags, fix it
//    fun loadByTag(tag: String)

    @Insert
    fun insertAll(vararg notes: Note)

    @Update
    fun updateNote(note: Note)

    @Delete
    fun delete(note: Note)
    @Query("SELECT * FROM notes WHERE uid = :id")
    suspend fun getById(id: Int): Note?

    @Query("DELETE FROM notes WHERE uid = :id")
    suspend fun deleteById(id: Int)
}