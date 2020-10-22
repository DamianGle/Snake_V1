package com.example.snake

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape

class SnakeBob {
    private var context: Context? = null
    var isBob: Boolean = false

    var isNormalBob = false
    var isEraseBob = false
    var isSpeedBob = false
    var isBigBob = false
    var isDeleteBob = false
    var speedBobTimerCounter = 0

    var bobPosX: Int = 0
    var bobPosY: Int = 0

    fun setContext(con: Context?) {
        context = con
        speedBobTimerCounter = context?.resources?.getInteger(R.integer.bob_speed_add)!!
    }

    @SuppressLint("ResourceType")
    fun drawBob(canvas: Canvas, sizeX: Int, sizeY: Int)
    {
        if(!isBob) {
            isDeleteBob = when((1..10).random()) {
                1 -> true
                else -> false
            }

            when ((1..2).random()) {
                1 -> isBigBob = true
                2 -> isBigBob = false
            }
        }
        val dim :Int? = if(isBigBob)
            context?.resources?.getInteger(R.integer.dim)?.times(context!!.resources.getInteger(R.integer.big_bob_factor))
        else
            context?.resources?.getInteger(R.integer.dim)

        val frameMargin = context?.resources?.getInteger(R.integer.frame_margin)
        val shapeDrawable = ShapeDrawable(RectShape())

        if(!isBob) {
            bobPosX = (dim!! + frameMargin!!..sizeX - dim - frameMargin).random()
            bobPosY = (dim + frameMargin..sizeY - dim - frameMargin).random()

            if(!isDeleteBob) {
                when ((1..3).random()) {
                    1 -> isNormalBob = true
                    2 -> isSpeedBob = true
                    3 -> isEraseBob = true
                }
            }
            isBob = true
        }

        shapeDrawable.setBounds(bobPosX, bobPosY, bobPosX + dim!!, bobPosY + dim)
        if(isNormalBob) shapeDrawable.paint.color = context?.resources?.getInteger(R.color.normal_bob_color)!!
        if(isSpeedBob) shapeDrawable.paint.color = context?.resources?.getInteger(R.color.speed_bob_color)!!
        if(isEraseBob) shapeDrawable.paint.color = context?.resources?.getInteger(R.color.erase_bob_color)!!
        if(isDeleteBob) shapeDrawable.paint.color = context?.resources?.getInteger(R.color.delete_bob_color)!!

        shapeDrawable.draw(canvas)
    }

    fun resetBob()
    {
        isEraseBob = false
        isSpeedBob = false
        isNormalBob = false
        isBigBob = false
        isBob = false
    }
}