package ru.vladder2312.customviewtask

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat

class MyView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    var colors = mutableListOf<Int>()
    var textColor = ResourcesCompat.getColor(
        resources,
        R.color.black,
        null
    )
    var bgColor = ResourcesCompat.getColor(
        resources,
        android.R.color.background_light,
        null
    )
    var defaultColor = ResourcesCompat.getColor(
        resources,
        R.color.green,
        null
    )
    var counterColor = ResourcesCompat.getColor(
        resources,
        R.color.black,
        null
    )

    private val paint = Paint()
    private val shapes = mutableListOf<Shape>()
    private var clear = false
    private var counter = 0

    init {
        val attributeArray: TypedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.MyView
        )
        defaultColor = attributeArray.getColor(R.styleable.MyView_default_color, defaultColor)
        textColor = attributeArray.getColor(R.styleable.MyView_counter_color, counterColor)
        attributeArray.recycle()

        paint.textSize = TEXT_SIZE
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (++counter == 10) {
            clear = true
            counter = 0
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
            paint.color = bgColor
            canvas.drawRect(START, START, width.toFloat(), height.toFloat(), paint)

            paint.color = textColor
            canvas.drawText(counter.toString(), (width/2).toFloat(), TEXT_SIZE, paint)
            clear = false
        } else {
            paint.color = textColor
            canvas.drawText(counter.toString(), (width/2).toFloat(), TEXT_SIZE, paint)

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

    private companion object {
        const val TEXT_SIZE = 96f
        const val START = 0f
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