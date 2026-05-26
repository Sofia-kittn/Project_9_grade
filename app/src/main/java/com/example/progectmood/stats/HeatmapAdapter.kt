package com.example.progectmood.stats

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.progectmood.R
import androidx.core.graphics.toColorInt

class HeatmapAdapter(
    private val days: List<HeatmapDay>
) : RecyclerView.Adapter<HeatmapAdapter.DayViewHolder>() {

    class DayViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val square: View = view.findViewById(R.id.day_square)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_heatmap_day, parent, false)

        return DayViewHolder(view)
    }

    override fun getItemCount(): Int = days.size

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {

        val day = days[position]

        val color = if (day.averageMood == null) {
            "#EBEDF0".toColorInt()
        } else {
            moodToColor(day.averageMood)
        }

        holder.square.setBackgroundColor(color)
    }


    fun moodToColor(mood: Float): Int {

        val clamped = mood.coerceIn(1f, 5f)

        // Normalize [1,5] -> [0,1]
        val normalized = (clamped - 1f) / 4f

        // 0 = red, 60 = yellow, 120 = green
        val hue = normalized * 120f

        return Color.HSVToColor(
            floatArrayOf(
                hue,   // Hue
                0.75f, // Saturation
                0.95f  // Value/Brightness
            )
        )
    }
}