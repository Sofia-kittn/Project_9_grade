package com.example.progectmood.stats

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import kotlin.math.max
import androidx.core.graphics.toColorInt
import java.text.SimpleDateFormat
import java.util.Locale

class HeatmapView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private val cellSize = dp(18f)

    private val cellSpacing = dp(4f)

    private val monthTextHeight = dp(24f)

    private val cornerRadius = dp(4f)

    private val weeks = mutableListOf<List<HeatmapDay>>()

    private val squarePaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.GRAY
        textSize = dp(11f)
    }

    private val rect = RectF()

    fun setData(days: List<HeatmapDay>) {

        weeks.clear()

        weeks.addAll(days.chunked(7))

        requestLayout()

        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val width = (
                weeks.size * (cellSize + cellSpacing)
                ).toInt() + paddingLeft + paddingRight

        val height = (
                monthTextHeight +
                        7 * (cellSize + cellSpacing)
                ).toInt() + paddingTop + paddingBottom

        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        var previousMonth = ""

        weeks.forEachIndexed { weekIndex, week ->

            val x = paddingLeft + weekIndex * (cellSize + cellSpacing)

            val firstDay = week.first()

            if (firstDay.month != previousMonth) {

                previousMonth = firstDay.month

                canvas.drawText(
                    formatMonthLabel(firstDay.dateString),
                    x,
                    paddingTop + textPaint.textSize,
                    textPaint
                )
            }

            week.forEachIndexed { dayIndex, day ->

                val y = paddingTop +
                        monthTextHeight +
                        dayIndex * (cellSize + cellSpacing)

                squarePaint.color = getColor(day.averageMood)

                rect.set(
                    x,
                    y,
                    x + cellSize,
                    y + cellSize
                )

                canvas.drawRoundRect(
                    rect,
                    cornerRadius,
                    cornerRadius,
                    squarePaint
                )
            }
        }
    }

    private fun getColor(mood: Float?): Int {

        if (mood == null) {
            return "#EBEDF0".toColorInt()
        }

        val clamped = mood.coerceIn(1f, 5f)

        val normalized = (clamped - 1f) / 4f

        val hue = normalized * 120f

        return Color.HSVToColor(
            floatArrayOf(
                hue,
                0.75f,
                0.95f
            )
        )
    }

    private fun dp(value: Float): Float {

        return value * resources.displayMetrics.density
    }

    private fun formatMonthLabel(dateString: String): String {

        val parser = SimpleDateFormat(
            "yyyy-MM-dd",
            Locale.US
        )

        val formatter = SimpleDateFormat(
            "MMM''yy",
            Locale.getDefault()
        )

        val date = parser.parse(dateString)
            ?: return ""

        return formatter.format(date)
    }
}