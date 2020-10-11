package com.example.snake

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Snake: AppCompatActivity()
{
    private var counter = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.snake_play)

        startTimeCounter()
    }

    private fun startTimeCounter() {
        val countTime: TextView = findViewById(R.id.countTime)

        object : CountDownTimer(10000, 1000)
        {
            override fun onTick(millisUntilFinished: Long) {
                countTime.text = counter.toString()
                counter++

                if(counter == 10)
                    counter = 0;
            }
            override fun onFinish() {
                    this.start()
            }
        }.start()
    }
}