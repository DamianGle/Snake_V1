package com.example.snake

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape

class SnakeWalls{
    private var context: Context? = null

    var wallTailX = mutableListOf<Int>()
    var wallTailY = mutableListOf<Int>()

    fun setContext(con: Context?) {
        context = con
    }

    fun createWall(sizeX: Int, sizeY: Int)
    {
        val dim = context?.resources?.getInteger(R.integer.dim)
        val frameMargin = context?.resources?.getInteger(R.integer.frame_margin)

        wallTailX.add((dim!! + frameMargin!!..sizeX - dim - frameMargin).random())
        wallTailY.add((dim + frameMargin..sizeY - dim - frameMargin).random())
    }

    fun drawWalls(canvas: Canvas)
    {
        var i = 0

        while(i < wallTailX.count()) {
            drawWall(canvas, wallTailX[i], wallTailY[i])
            i++
        }
    }

    @SuppressLint("ResourceType")
    private fun drawWall(canvas: Canvas, posX: Int, posY: Int)
    {
        val dim = context?.resources?.getInteger(R.integer.dim)

        val shapeDrawable = ShapeDrawable(RectShape())

        shapeDrawable.setBounds( posX, posY, posX + dim!!, posY + dim)
        shapeDrawable.paint.color = context?.resources?.getInteger(R.color.wall_color)!!
        shapeDrawable.paint.style = Paint.Style.STROKE
        shapeDrawable.paint.strokeWidth = (context?.resources?.getInteger(R.integer.wall_stroke)!!).toFloat()
        shapeDrawable.draw(canvas)
    }
}