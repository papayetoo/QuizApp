package com.papayetoo.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ResultActivity : AppCompatActivity() {
    private var tvScore: TextView? = null
    private var tvUserName: TextView? = null
    private var btnFinish: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        tvScore = findViewById(R.id.tv_score)
        tvUserName = findViewById(R.id.tv_userName)
        btnFinish = findViewById(R.id.btn_finish)

        tvUserName?.text = intent.getStringExtra("userName")
        val score  = intent.getIntExtra("correctAnswerCount", 0)
        val quetionsCount = intent.getIntExtra("numbersOfQuetion", 0)
        tvScore?.text = "Your Score is $score out of $quetionsCount"

        btnFinish?.setOnClickListener {
            startActivity(Intent(this, SigninActivity::class.java))
        }
    }
}