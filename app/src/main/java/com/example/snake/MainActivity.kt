package com.example.snake

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bitmap: Bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888)
        bitmap.eraseColor(Color.BLACK)

        BackgroundView.background = BitmapDrawable(resources, bitmap)

        val intent = Intent(this, Snake::class.java)

        val play = findViewById<Button>(R.id.SnakePlay)
        play?.setOnClickListener()
        {
            startActivity(intent)
            finish()
        }

        val exit = findViewById<Button>(R.id.SnakeExit)
        exit?.setOnClickListener()
        {
            moveTaskToBack(true)
            exitProcess(-1)
        }
    }
}