package com.papayetoo.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class SigninActivity : AppCompatActivity() {

    var btnStart: Button? = null
    var editInput: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        btnStart = findViewById(R.id.btnStart)
        editInput = findViewById(R.id.et_name)
        btnStart?.setOnClickListener {startMain()}
    }

    private fun startMain() {
        val intent = Intent(this, MainActivity::class.java)
        editInput?.text?.let {
            intent.putExtra("userName", it.toString())
        }
        startActivity(intent)
    }
}