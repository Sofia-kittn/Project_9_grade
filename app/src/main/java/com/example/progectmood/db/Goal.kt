package com.example.progectmood.db

import android.icu.text.SimpleDateFormat
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date
import java.util.Locale


@Entity(tableName = "goals")
data class Goal(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "is_completed") val isCompleted: Boolean,
    @ColumnInfo(name = "date") val date: Long, // TODO: date format?
//    @ColumnInfo(name = "") val repeat:
) {
    fun getFormattedDate(): String {
        return SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date(date))
    }
}
