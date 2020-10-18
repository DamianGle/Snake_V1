package com.example.snake

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
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

        if(!isBob) {
            bobPosX = (dim!!..view.width).random() - dim!!
            bobPosY = (dim!!..view.height).random() - dim!!

            isBob = true
        }

        val shapeDrawable = ShapeDrawable(RectShape())

        shapeDrawable.setBounds( bobPosX, bobPosY, bobPosX + dim!!, bobPosY + dim)
        shapeDrawable.paint.color = context?.resources?.getInteger(R.color.normal_bob_color)!!
        shapeDrawable.draw(canvas)
    }
}