package ru.vladder2312.customviewtask

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat

class MyView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    var colors = mutableListOf<Int>()
    var defaultColor = ResourcesCompat.getColor(
        resources,
        R.color.green,
        null
    )

    private var paint = Paint()
    private var clear = false
    private var topText = ""
    private var counter = 0
    private val shapes = mutableListOf<Shape>()

    init {
        paint.color = ResourcesCompat.getColor(
            resources,
            android.R.color.background_light,
            null
        )
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (++counter == 10) {
            clear = true
            counter = 0
            topText = ""
            shapes.clear()
            showFinalToast()
        } else {
            shapes.add(
                Shape(
                    Shape.Type.values().random(),
                    getPaintColor(),
                    (50..100).random().toFloat(),
                    (50..100).random().toFloat(),
                    event.x,
                    event.y
                )
            )
        }

        invalidate()
        return super.onTouchEvent(event)
    }

    override fun onDraw(canvas: Canvas) {
        if (clear) {
            canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
            clear = false
        } else {
            shapes.forEach { it.draw(canvas) }
        }

        super.onDraw(canvas)
    }

    private fun getPaintColor(): Int {
        return if (colors.isEmpty()) {
            defaultColor
        } else {
            colors.random()
        }
    }

    private fun showFinalToast() {
        Toast.makeText(
            context,
            context.getString(R.string.game_over),
            Toast.LENGTH_SHORT
        ).show()
    }

    private class Shape(
        val type: Type,
        val color: Int,
        val width: Float,
        val height: Float,
        val x: Float,
        val y: Float
    ) {

        val paint = Paint()
        val round = 10f

        fun draw(canvas: Canvas) {
            paint.color = color
            when (type) {
                Type.CIRCLE -> {
                    canvas.drawCircle(x, y, width / 2, paint)
                }
                Type.RECTANGLE -> {
                    canvas.drawRect(x, y, x + width, y + height, paint)
                }
                Type.ROUNDED_RECTANGLE -> {
                    canvas.drawRoundRect(x, y, x + width, y + width, round, round, paint)
                }
            }
        }

        enum class Type {
            CIRCLE, RECTANGLE, ROUNDED_RECTANGLE
        }
    }
}