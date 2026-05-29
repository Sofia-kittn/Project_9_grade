package com.example.projectmood.stats

import com.example.projectmood.notes.Note
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object HeatmapManager {

    val dayFormat = createDayFormat()

    val monthFormat = createMonthFormat()

    fun generateHeatmap(notes: List<Note>): List<HeatmapDay> {

        val grouped = notes.groupBy {
            dayFormat.format(it.date)
        }

        val result = mutableListOf<HeatmapDay>()

        val calendar = Calendar.getInstance()
        normalize(calendar)

        val minimumStart = Calendar.getInstance()
        normalize(minimumStart)

        minimumStart.add(Calendar.MONTH, -3)
        minimumStart.set(Calendar.DAY_OF_MONTH, 8)

        if (calendar.after(minimumStart)) {
            calendar.timeInMillis = minimumStart.timeInMillis
        }

        if (notes.isNotEmpty()) {
            val oldestNote = notes.minBy { it.date }
            calendar.timeInMillis = minOf(
                oldestNote.date,
                minimumStart.timeInMillis
            )
        } else {
            calendar.timeInMillis = minimumStart.timeInMillis
        }

        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DAY_OF_YEAR, -1)
        }

        val endCalendar = Calendar.getInstance()
        normalize(endCalendar)

        while (!calendar.after(endCalendar)) {

            val currentTime = calendar.timeInMillis

            val key = dayFormat.format(currentTime)

            val notesForDay = grouped[key]

            val average = notesForDay
                ?.map { it.emotionLevel.level }
                ?.average()
                ?.toFloat()

            result.add(
                HeatmapDay(
                    dateString = key,
                    month = monthFormat.format(currentTime),
                    averageMood = average
                )
            )

            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }

        return result
    }

    private fun createDayFormat(): SimpleDateFormat {

        return SimpleDateFormat(
            "yyyy-MM-dd",
            Locale.getDefault()
        )
    }

    private fun createMonthFormat(): SimpleDateFormat {

        return SimpleDateFormat(
            "MMM",
            Locale.getDefault()
        )
    }

    private fun normalize(calendar: Calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
    }

}