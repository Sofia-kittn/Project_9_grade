package com.example.progectmood.notes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.progectmood.db.EmotionLevel

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "date") val date: Long, // TODO: date format?
    @ColumnInfo(name = "emotion_level") val emotionLevel: EmotionLevel,
    @ColumnInfo(name = "note") val note: String,
    @ColumnInfo(name = "tags") val tags: List<String>,
)