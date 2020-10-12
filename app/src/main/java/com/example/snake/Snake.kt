package com.example.snake

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.snake_play.*
import org.jetbrains.anko.toast


class Snake: AppCompatActivity()
{
    // rectangle positions
    private var head_posX = 20;
    private var head_posY = 20;
    //private var dim = 20;
    private var touchX = 0;
    private var touchY = 0;
    private var poolHeight = 0;
    private var poolWidth = 0;

    // For tracking movement Heading
    enum class Heading {
        UP, RIGHT, DOWN, LEFT
    }

    // Start by heading to the right
    private var heading = Heading.RIGHT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.snake_play)

        poolHeight = view.height;
        poolWidth = view.width;

        startTimeCounter()


    }

    private fun drawFrame(canvas: Canvas)
    {
        var margin = resources.getInteger(R.integer.frame_margin);
        var stroke = resources.getInteger(R.integer.frame_stroke);

        var shapeDrawable: ShapeDrawable = ShapeDrawable(RectShape());

        shapeDrawable.setBounds( margin,margin,view.width - margin - 2*stroke ,view.height- margin - 2*stroke);
        shapeDrawable.paint.color = Color.RED;
        shapeDrawable.paint.style = Paint.Style.STROKE;
        shapeDrawable.paint.strokeWidth = 5f;
        shapeDrawable.draw(canvas);
    }

    private fun drawTail(canvas: Canvas, posX: Int, posY: Int)
    {
        var dim = resources.getInteger(R.integer.dim);
        var shapeDrawable: ShapeDrawable = ShapeDrawable(RectShape());

        shapeDrawable.setBounds( posX, posY, posX + dim, posY + dim);
        shapeDrawable.paint.color = Color.MAGENTA;
        shapeDrawable.draw(canvas);
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun doInTimer()
    {
        val myView: View = findViewById(R.id.view)
        myView.setOnTouchListener(touchListener);

    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun doAfterTimer()
    {
        if(heading == Heading.RIGHT)
            head_posX += 20;

        if(heading == Heading.LEFT)
            head_posX -= 20;

        if(heading == Heading.UP)
            head_posY -= 20;

        if(heading == Heading.DOWN)
            head_posY += 20;
        draw();
    }
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun draw()
    {
        val bitmap: Bitmap = Bitmap.createBitmap(700, 1000, Bitmap.Config.ARGB_8888)
        bitmap.eraseColor(Color.BLACK);

        val canvas: Canvas = Canvas(bitmap)

        drawFrame(canvas);
        drawTail(canvas, head_posX, head_posY);

        view.background = BitmapDrawable(resources, bitmap)
    }


    @SuppressLint("ClickableViewAccessibility")
    var touchListener = OnTouchListener { _, event -> // save the X,Y coordinates
        onTouchEvent(event);
    }

    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        when (motionEvent.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_UP -> heading = if (motionEvent.x >= poolWidth / 2) {
                when (heading) {
                    Heading.UP -> Heading.RIGHT
                    Heading.RIGHT -> Heading.DOWN
                    Heading.DOWN -> Heading.LEFT
                    Heading.LEFT -> Heading.UP
                }
            } else {
                when (heading) {
                    Heading.UP -> Heading.LEFT
                    Heading.LEFT -> Heading.DOWN
                    Heading.DOWN -> Heading.RIGHT
                    Heading.RIGHT -> Heading.UP
                }
            }
        }
        return true
    }


    private fun startTimeCounter() {
        val countTime: TextView = findViewById(R.id.countTime)

        object : CountDownTimer(500, 100)
        {
            @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
            override fun onTick(millisUntilFinished: Long) {
                doInTimer()
            }

            @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
            override fun onFinish() {
                this.start()
                doAfterTimer()
            }
        }.start()
    }
}