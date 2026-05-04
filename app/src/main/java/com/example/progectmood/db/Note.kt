package com.example.progectmood.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


//typealias Tag = String

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "date") val date: Long, // TODO: date format?
    @ColumnInfo(name = "emotion_level") val emotionLevel: EmotionLevel,
    @ColumnInfo(name = "note") val note: String?,
    @ColumnInfo(name = "tags") val tags: List<String>,
)
