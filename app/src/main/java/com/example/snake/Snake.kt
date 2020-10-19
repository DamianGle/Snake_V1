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
    private var snakeBody = SnakeBody();
    private var snakeBob = SnakeBob();

    private var countTimer = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        snakeBody.setContext(applicationContext);
        snakeBob.setContext(applicationContext);

        super.onCreate(savedInstanceState)
        setContentView(R.layout.snake_play)

        startTimeCounter();
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

    private fun eatBob()
    {
        val dim = resources.getInteger(R.integer.dim)
        if(((snakeBody.snakeTailX[0] - snakeBob.bobPosX) >= dim*(-1)) && ((snakeBody.snakeTailX[0] - snakeBob.bobPosX) <= dim)) {
            if(((snakeBody.snakeTailY[0] - snakeBob.bobPosY) >= dim *(-1)) && ((snakeBody.snakeTailY[0] - snakeBob.bobPosY) <= dim)) {

                snakeBody.addTail()
                snakeBob.isSpeedBob = false
                snakeBob.isNormalBob = false
                snakeBob.isBob = false

            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun draw()
    {
        val bitmap: Bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        bitmap.eraseColor(Color.BLACK);

        val canvas: Canvas = Canvas(bitmap)

        drawFrame(canvas);
        snakeBody.drawTails(canvas);

        snakeBob.drawBob(canvas,view.width, view.height)

        eatBob()

        view.background = BitmapDrawable(resources, bitmap)
    }

    @SuppressLint("ClickableViewAccessibility")
    var touchListener = OnTouchListener { _, event -> // save the X,Y coordinates
        onTouchEvent(event);
    }

    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        snakeBody.moveSnake(motionEvent, view.width)

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
        if(!snakeBob.isSpeedBob)
            snakeBob.speedBobTimerCounter = resources.getInteger(R.integer.bob_speed_add)
        else
            snakeBob.speedBobTimerCounter = 0

        if(countTimer >= snakeBob.speedBobTimerCounter) {
            snakeBody.moveTails();
            countTimer = 0
        }
        else
            countTimer++

        draw();
        if (snakeBody.checkDeath())
            toast("Dead")
    }

    private fun startTimeCounter() {
        val countTime: TextView = findViewById(R.id.countTime)
        object : CountDownTimer((resources.getInteger(R.integer.timer_counter)/resources.getInteger(R.integer.FPS) * 10).toLong()
        , resources.getInteger(R.integer.timer_counter).toLong())
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