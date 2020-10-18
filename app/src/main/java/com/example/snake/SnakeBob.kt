package com.example.snake

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.view.View

class SnakeBob {
    private var context: Context? = null
    var isBob: Boolean = false
    var bobPosX: Int = 0
    var bobPosY: Int = 0

    fun setContext(con: Context?) {
        context = con
    }

    @SuppressLint("ResourceType")
    fun drawBob(canvas: Canvas, view: View)
    {
        val dim = context?.resources?.getInteger(R.integer.dim)
        val frameMargin = context?.resources?.getInteger(R.integer.frame_margin)

        if(!isBob) {
            bobPosX = (dim!! + frameMargin!!..view.width - dim!! - frameMargin).random()
            bobPosY = (dim!! + frameMargin!!..view.height - dim!! - frameMargin).random()

            isBob = true
        }

        val shapeDrawable = ShapeDrawable(RectShape())

        shapeDrawable.setBounds( bobPosX, bobPosY, bobPosX + dim!!, bobPosY + dim)
        shapeDrawable.paint.color = context?.resources?.getInteger(R.color.normal_bob_color)!!
        shapeDrawable.draw(canvas)
    }
}