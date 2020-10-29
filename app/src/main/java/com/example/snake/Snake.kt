package com.example.snake

import android.annotation.SuppressLint
import android.content.Intent
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
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.snake_play.*
import org.jetbrains.anko.toast

class Snake: AppCompatActivity()
{
    private var snakeBody = SnakeBody()
    private var snakeBob = SnakeBob()
    private var snakeWalls = SnakeWalls()

    private var snakePointsVal = 0
    private var countTimer = 0

    private var isStop = false

    override fun onCreate(savedInstanceState: Bundle?) {

        snakeBody.setContext(applicationContext)
        snakeBob.setContext(applicationContext)
        snakeWalls.setContext(applicationContext)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.snake_play)

        startTimeCounter()
    }

    @SuppressLint("ResourceType")
    private fun drawFrame(canvas: Canvas)
    {
        val margin = resources.getInteger(R.integer.frame_margin)
        val shapeDrawable = ShapeDrawable(RectShape())

        shapeDrawable.setBounds( margin,margin,viw33.width - margin,viw33.height - margin)
        shapeDrawable.paint.color = resources.getInteger(R.color.layout_color)
        shapeDrawable.paint.style = Paint.Style.STROKE
        shapeDrawable.paint.strokeWidth = 5f
        shapeDrawable.draw(canvas)
    }

    private fun eatBob()
    {
        val dim :Int? = if(snakeBob.isBigBob)
            resources?.getInteger(R.integer.dim)?.times(resources.getInteger(R.integer.big_bob_factor))
        else
            resources?.getInteger(R.integer.dim)

        if (dim != null) {
            if(((snakeBody.snakeTailX[0] - snakeBob.bobPosX) >= dim*(-1)) && ((snakeBody.snakeTailX[0] - snakeBob.bobPosX) <= dim)) {
                if(((snakeBody.snakeTailY[0] - snakeBob.bobPosY) >= dim *(-1)) && ((snakeBody.snakeTailY[0] - snakeBob.bobPosY) <= dim)) {

                    if(snakeBob.isDeleteBob)
                        {
                            snakePointsVal += snakeBody.snakeTailX.count()
                            snakeBody.removeSnake()
                        }
                    else
                        {
                            if(snakeBob.isBigBob)
                            {
                                snakePointsVal += 1
                            }
                            if (snakeBob.isEraseBob)
                                {
                                    snakePointsVal += 1
                                    snakeBody.removeTail()
                                    if (snakeBob.isBigBob)
                                        {
                                            //snakePointsVal += 1
                                            snakeBody.removeTail()
                                        }
                                }
                            if(snakeBob.isNormalBob)
                                {
                                    snakePointsVal += 1
                                    snakeBody.addTail()
                                    if (snakeBob.isBigBob)
                                        {
                                            //snakePointsVal += 1
                                            snakeBody.addTail()
                                        }
                                }

                            if (!snakeBob.isSpeedBob)
                                snakeBob.speedBobTimerCounter = resources.getInteger(R.integer.bob_speed_add)
                            else
                                snakeBob.speedBobTimerCounter = 0

                             if (snakeBob.isWallBob)
                                {
                                    snakePointsVal += 1
                                    snakeBody.addTail()
                                    snakeWalls.createWall(viw33.width, viw33.height, snakeBody.snakeTailX, snakeBody.snakeTailY, snakeBob.bobPosX, snakeBob.bobPosY)
                                    if(snakeBob.isBigBob)
                                        {
                                            //snakePointsVal += 1
                                            snakeBody.addTail()
                                            snakeWalls.createWall(viw33.width, viw33.height, snakeBody.snakeTailX, snakeBody.snakeTailY, snakeBob.bobPosX, snakeBob.bobPosY)
                                        }
                                }
                        }
                    snakeBob.resetBob()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun draw()
    {
        val bitmap: Bitmap = Bitmap.createBitmap(viw33.width, viw33.height, Bitmap.Config.ARGB_8888)
        bitmap.eraseColor(Color.BLACK)

        val canvas = Canvas(bitmap)

        drawFrame(canvas)
        snakeBody.drawTails(canvas)

        snakeBob.drawBob(canvas,viw33.width, viw33.height, snakeBody.snakeTailX, snakeBody.snakeTailY, snakeWalls.wallTailX, snakeWalls.wallTailY)

        eatBob()

        snakeWalls.drawWalls(canvas)

        viw33.background = BitmapDrawable(resources, bitmap)
    }

    @SuppressLint("ClickableViewAccessibility")
    var touchListener = OnTouchListener { _, event -> // save the X,Y coordinates
        onTouchEvent(event)
    }

    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        if(!isStop)
            snakeBody.moveSnake(motionEvent, viw33.width)
        return true
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun doInTimer()
    {
        val myView: View = findViewById(R.id.viw33)
        myView.setOnTouchListener(touchListener)
    }

    @SuppressLint("ResourceType", "SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun doAfterTimer()
    {
        val back = findViewById<Button>(R.id.BackSnake)
        val startStop = findViewById<Button>(R.id.StartStop)

        if(countTimer >= snakeBob.speedBobTimerCounter) {
            if(!isStop)
                snakeBody.moveTails()
            countTimer = 0
        }
        else
            countTimer++

        draw()

        if (snakeBody.checkDeath() || snakeWalls.checkDeath(snakeBody.snakeTailX[0], snakeBody.snakeTailY[0])) {
            //toast("Dead")
            isStop = true
            startStop.isClickable = false
            startStop.visibility = View.INVISIBLE
        }
        val intent = Intent(this, MainActivity::class.java)
        back?.setOnClickListener()
        {
            startActivity(intent)
            finish()
        }

        startStop?.setOnClickListener()
        {
            if(!isStop)
            {
                isStop = true
                startStop.text = "START"
            }
            else
            {
                isStop = false
                startStop.text = "STOP"
            }
        }

        val snakePoints: TextView = findViewById(R.id.countPoints)
        snakePoints.setTextColor(resources.getInteger(R.color.layout_color))
        snakePoints.text = "Points: $snakePointsVal"
    }

    private fun startTimeCounter() {
        object : CountDownTimer((resources.getInteger(R.integer.timer_counter)/resources.getInteger(R.integer.FPS) * 10).toLong(), resources.getInteger(R.integer.timer_counter).toLong())
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
