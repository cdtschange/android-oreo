package com.cdts.oreo.ui.schema.view.webview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.support.annotation.ColorInt
import android.util.AttributeSet
import android.view.View

class ProgressView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {
    private lateinit var paint: Paint
    private var currentWidth: Int = 0
    private var currentHeight: Int = 0
    private var progress: Int = 0

    init {
        init()
    }

    private fun init() {
        paint = Paint().also {
            it.isAntiAlias = true
            it.isDither = true
            it.isAntiAlias = true
            it.strokeWidth = 10f
            it.color = Color.RED
        }
    }

    override fun onSizeChanged(w: Int, h: Int, ow: Int, oh: Int) {
        currentWidth = w
        currentHeight = h
        super.onSizeChanged(w, h, ow, oh)
    }


    override fun onDraw(canvas: Canvas) {
        canvas.drawRect(0f, 0f, (currentWidth * progress / 100).toFloat(), currentHeight.toFloat(), paint)
        super.onDraw(canvas)
    }

    fun setProgress(newProgress: Int) {
        this.progress = newProgress
        invalidate()
    }

    fun setColor(@ColorInt color: Int) {
        paint.color = color
    }
}