package com.example.snake

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
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
    var posX = 20;
    var posY = 20;
    var dimm = 20;
    var touchX = 0;
    var touchY = 0;

    private val lastTouchDownXY = FloatArray(2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.snake_play)

        startTimeCounter()


    }

    private fun startTimeCounter() {
        val countTime: TextView = findViewById(R.id.countTime)

        object : CountDownTimer(2000, 100)
        {
            override fun onTick(millisUntilFinished: Long) {
                val myView: View = findViewById(R.id.view)
                myView.setOnTouchListener(touchListener);
                myView.setOnClickListener(clickListener);
            }

            @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
            override fun onFinish() {
                    this.start()
                doAfterTimer()
            }
        }.start()
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun doAfterTimer()
    {
        posY += 20;
        draw();
    }
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun draw()
    {
        val bitmap: Bitmap = Bitmap.createBitmap(700, 1000, Bitmap.Config.ARGB_8888)
        bitmap.eraseColor(Color.BLACK);
        val canvas: Canvas = Canvas(bitmap)


        // draw rectangle shape to canvas
        var shapeDrawable: ShapeDrawable = ShapeDrawable(RectShape())
        shapeDrawable.setBounds( posX, posY, posX + dimm, posY + dimm)
        shapeDrawable.paint.color = Color.parseColor("#009944")
        shapeDrawable.draw(canvas)


        view.background = BitmapDrawable(getResources(), bitmap)
    }

    // the purpose of the touch listener is just to store the touch X,Y coordinates
    @SuppressLint("ClickableViewAccessibility")
    var touchListener = OnTouchListener { v, event -> // save the X,Y coordinates
        if (event.actionMasked == MotionEvent.ACTION_DOWN) {
            touchX = event.x.toInt()
            touchY = event.y.toInt()
        }

        // let the touch event pass on to whoever needs it
        false
    }

    private var clickListener =
        View.OnClickListener { // retrieve the stored coordinates
            // use the coordinates for whatever
            toast("onLongClick: x = $touchX, y = $touchY")
        }
}