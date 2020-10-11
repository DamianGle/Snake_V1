package com.example.snake

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.shapes.RectShape
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bitmap: Bitmap = Bitmap.createBitmap(700, 1000, Bitmap.Config.ARGB_8888)
        val canvas: Canvas = Canvas(bitmap)

        var shapeDrawable: ShapeDrawable

        // rectangle positions
        var x_Pos = 100;
        var y_Pos = 100;
        var dim = 20;

        // draw rectangle shape to canvas
        shapeDrawable = ShapeDrawable(RectShape())
        shapeDrawable.setBounds( x_Pos, y_Pos, x_Pos + dim, y_Pos + dim)
        shapeDrawable.getPaint().setColor(Color.parseColor("#009944"))
        shapeDrawable.draw(canvas)

        imageV.background = BitmapDrawable(getResources(), bitmap)
    }
}