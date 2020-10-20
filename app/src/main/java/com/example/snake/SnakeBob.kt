package com.example.snake

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.view.View

class SnakeBob {
    private var context: Context? = null
    var isBob: Boolean = false

    var isNormalBob = false
    var isEraseBob = false
    var isSpeedBob = false
    var speedBobTimerCounter = 0


    var bobPosX: Int = 0
    var bobPosY: Int = 0

    fun setContext(con: Context?) {
        context = con
    }

    @SuppressLint("ResourceType")
    fun drawBob(canvas: Canvas, sizeX: Int, sizeY: Int)
    {
        val dim = context?.resources?.getInteger(R.integer.dim)
        val frameMargin = context?.resources?.getInteger(R.integer.frame_margin)
        val shapeDrawable = ShapeDrawable(RectShape())

        if(!isBob) {
            bobPosX = (dim!! + frameMargin!!..sizeX - dim!! - frameMargin).random()
            bobPosY = (dim!! + frameMargin!!..sizeY - dim!! - frameMargin).random()

            when ((1..3).random()) {
                    1 -> isNormalBob = true
                    2 -> isSpeedBob = true
                    3 -> isEraseBob = true
            }
            isBob = true
        }

        shapeDrawable.setBounds(bobPosX, bobPosY, bobPosX + dim!!, bobPosY + dim)
        if(isNormalBob) shapeDrawable.paint.color = context?.resources?.getInteger(R.color.normal_bob_color)!!
        if(isSpeedBob) shapeDrawable.paint.color = context?.resources?.getInteger(R.color.speed_bob_color)!!
        if(isEraseBob) shapeDrawable.paint.color = context?.resources?.getInteger(R.color.erase_bob_color)!!

        shapeDrawable.draw(canvas)


    }
}