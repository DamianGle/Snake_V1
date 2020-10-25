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

    fun checkDeath(headPosX: Int, headPosY: Int) :Boolean
    {
        val dim = context?.resources?.getInteger(R.integer.dim)
        val mdim = dim?.times((-1))
        var i = 0
        for(i in 0 until wallTailX.count())
        {
            if (dim != null) {
                if(((headPosX - wallTailX[i]) >= mdim!!) && ((headPosX - wallTailX[i]) <= dim)) {
                    if(((headPosY - wallTailY[i]) >= mdim) && ((headPosY - wallTailY[i]) <= dim)) {
                        return true
                    }
                }
            }
        }
        return false
    }

    fun createWall(sizeX: Int, sizeY: Int, snakePosX:List<Int>, snakePosY:List<Int>, bobPosX: Int, bobPosY: Int)
    {
        val dim = context?.resources?.getInteger(R.integer.dim)
        val mdim = dim?.times((-1))
        val frameMargin = context?.resources?.getInteger(R.integer.frame_margin)

        var wallTailx = (dim!! + frameMargin!!..sizeX - dim - frameMargin).random()
        var wallTaily = (dim!! + frameMargin!!..sizeY - dim - frameMargin).random()

        var i = 0
        var z = 0

        for(z in 0 until snakePosX.count()) {
            for (i in 0 until wallTailX.count()) {
                if ((((wallTailx - wallTailX[i]) >= mdim!!) && ((wallTailx - wallTailX[i]) <= dim)
                            && ((wallTaily - wallTailY[i]) >= mdim) && ((wallTaily - wallTailY[i]) <= dim))
                    || ((((wallTailx - bobPosX) >= mdim) && ((wallTailx - bobPosX) <= dim)
                            && ((wallTaily - bobPosY) >= mdim) && ((wallTaily - bobPosY) <= dim)))
                    || ((((wallTailx - snakePosX[z]) >= mdim) && ((wallTailx - snakePosX[z]) <= dim)
                            && ((wallTaily - snakePosY[z]) >= mdim) && ((wallTaily - snakePosY[z]) <= dim)))
                ) {
                    wallTailx = (dim!! + frameMargin!!..sizeX - dim - frameMargin).random()
                    wallTaily = (dim!! + frameMargin!!..sizeY - dim - frameMargin).random()
                    continue
                }
            }
        }
        wallTailX.add(wallTailx)
        wallTailY.add(wallTaily)
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