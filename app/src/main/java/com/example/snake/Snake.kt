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
import org.w3c.dom.Node


class Snake: AppCompatActivity()
{
    private var snakeTailX = mutableListOf<Int>();
    private var snakeTailY = mutableListOf<Int>();

    enum class Heading {
        UP, RIGHT, DOWN, LEFT
    }

    private var heading = Heading.RIGHT;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.snake_play)

        initialize();
        startTimeCounter();
    }

    private fun initialize()
    {
        snakeTailX.add(0, 200);
        snakeTailY.add(0, 200);

        snakeTailX.add(1, 200);
        snakeTailY.add(1, 200);


        var random = (1..4).random();
        when(random)
        {
            1 -> heading = Heading.LEFT;
            2 -> heading = Heading.RIGHT;
            3 -> heading = Heading.UP;
            4 -> heading = Heading.DOWN;
        }
    }

    private fun drawFrame(canvas: Canvas)
    {
        val margin = resources.getInteger(R.integer.frame_margin);
        val shapeDrawable: ShapeDrawable = ShapeDrawable(RectShape());

        shapeDrawable.setBounds( margin,margin,view.width - margin,view.height - margin);
        shapeDrawable.paint.color = Color.RED;
        shapeDrawable.paint.style = Paint.Style.STROKE;
        shapeDrawable.paint.strokeWidth = 5f;
        shapeDrawable.draw(canvas);
    }

    private fun drawTails(canvas: Canvas)
    {
        var i = 0;

        while(i < snakeTailX.count()) {
            drawTail(canvas, snakeTailX[i], snakeTailY[i]);
            i++;
        }
    }

    private fun drawTail(canvas: Canvas, posX: Int, posY: Int)
    {
        val dim = resources.getInteger(R.integer.dim);
        val shapeDrawable: ShapeDrawable = ShapeDrawable(RectShape());

        shapeDrawable.setBounds( posX, posY, posX + dim, posY + dim);
        shapeDrawable.paint.color = Color.MAGENTA;
        shapeDrawable.draw(canvas);
    }

    private fun moveHead()
    {
        val dim = resources.getInteger(R.integer.dim);

        when(heading)
        {
            Heading.RIGHT -> snakeTailX[0] += dim;
            Heading.LEFT -> snakeTailX[0] -= dim;
            Heading.UP -> snakeTailY[0] -= dim;
            Heading.DOWN -> snakeTailY[0] += dim;
        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun draw()
    {
        val bitmap: Bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        bitmap.eraseColor(Color.BLACK);

        val canvas: Canvas = Canvas(bitmap)

        drawFrame(canvas);
        drawTails(canvas);

        view.background = BitmapDrawable(resources, bitmap)
    }

    @SuppressLint("ClickableViewAccessibility")
    var touchListener = OnTouchListener { _, event -> // save the X,Y coordinates
        onTouchEvent(event);
    }

    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        when (motionEvent.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_UP -> heading = if (motionEvent.x >= view.width / 2) {
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

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun doInTimer()
    {
        val myView: View = findViewById(R.id.view)
        myView.setOnTouchListener(touchListener);
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun doAfterTimer()
    {
        moveHead();
        draw();
    }

    private fun startTimeCounter() {
        val countTime: TextView = findViewById(R.id.countTime)

        object : CountDownTimer((resources.getInteger(R.integer.timer_counter)/resources.getInteger(R.integer.FPS) * 10).toLong(),
            resources.getInteger(R.integer.timer_counter).toLong())
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