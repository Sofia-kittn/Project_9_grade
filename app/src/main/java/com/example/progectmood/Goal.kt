package com.example.progectmood

import android.icu.text.SimpleDateFormat
import java.sql.Date
import java.util.Locale

class Goal(
    val id: Long = 0,
    val title: String = "",
    val isCompleted: Boolean = false,
    val date: Long = 0,
    val repeat: List<Boolean> = listOf(false, false, false, false, false, false, false)
) {
    fun shouldShowOnDay(day: Int): Boolean{
        return repeat[day - 1]
    }

    fun getFormattedDate(): String {
        return SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date(date))
    }
}