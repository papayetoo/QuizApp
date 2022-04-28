package com.papayetoo.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.style.TypefaceSpan
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import eu.tutorials.quizapp.Constants

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val questionList: ArrayList<Question> = Constants.getQuestions()
    private var currentPage: Int = 1
    private var selectedOptionNumber: Int = 0
    private var correctOptionNumber: Int = 0
    private var quizImageView: ImageView? = null
    private var tvQuestion: TextView? = null
    private var tvOptionOne: TextView? = null
    private var tvOptionTwo: TextView? = null
    private var tvOptionThree: TextView? = null
    private var tvOptionFour: TextView? = null
    private var progressBar: ProgressBar? = null
    private var tvProgerss: TextView? = null
    private var btnSubmit: Button? = null

    private var correctCount: Int = 0
    private var userName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        quizImageView = findViewById(R.id.quizImageView)
        tvQuestion = findViewById(R.id.question)
        tvOptionOne = findViewById(R.id.tv_option_one)
        tvOptionTwo = findViewById(R.id.tv_option_two)
        tvOptionThree = findViewById(R.id.tv_option_three)
        tvOptionFour = findViewById(R.id.tv_option_four)

        tvOptionOne?.setOnClickListener(this)
        tvOptionTwo?.setOnClickListener(this)
        tvOptionThree?.setOnClickListener(this)
        tvOptionFour?.setOnClickListener(this)

        userName = intent.getStringExtra("userName")

        progressBar = findViewById(R.id.progressBar)
        tvProgerss = findViewById(R.id.tvProgress)
        btnSubmit = findViewById(R.id.btnSubmit)


        progressBar?.max = questionList.size
        if (questionList.size > 0) {
            setQuiz(questionList[currentPage - 1])
            setProgressBar(currentPage)
            setProgressText(currentPage, questionList.size)
        }

        btnSubmit?.setOnClickListener {
            if (selectedOptionNumber == 0) {
                currentPage++
               when {
                   currentPage <= questionList.size -> {
                       defaultOptionsView()
                       setQuiz(questionList[currentPage - 1])
                       setProgressBar(currentPage)
                       setProgressText(currentPage, questionList.size)
                   }
                   else -> {
//                       Toast.makeText(this, "YOU MAKE THE END", Toast.LENGTH_LONG).show()
                       val intent = Intent(this, ResultActivity::class.java)
                       intent.putExtra("correctAnswerCount", correctCount)
                       intent.putExtra("numbersOfQuetion", questionList.size)
                       userName?.let {
                           intent.putExtra("userName", it)
                       }
                       startActivity(intent)
                   }
               }
            } else {
                val question = questionList[currentPage - 1]
                if (question.correctAnswer != selectedOptionNumber) {
                    answerView(selectedOptionNumber, R.drawable.wrong_option_border_bg)
                } else {
                    correctCount++
                }

                answerView(question.correctAnswer, R.drawable.correct_option_border_bg)
                btnSubmit?.text = if (currentPage == questionList.size) "FINISH" else "GO TO NEXT QUESTION"

                selectedOptionNumber = 0
            }
        }
    }

    private fun defaultOptionsView() {
        val options = ArrayList<TextView>()
        tvOptionOne?.let {
            options.add(0, it)
        }
        tvOptionTwo?.let {
            options.add(1, it)
        }
        tvOptionThree?.let {
            options.add(2, it)
        }
        tvOptionFour?.let {
            options.add(3, it)
        }

        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(
                this,
                R.drawable.default_option_border_bg
            )
        }
    }

    private fun setQuiz(question: Question) {
        val quizImage = question.image
        val quizQuestion = question.question
        val optionOne = question.optionOne
        val optionTwo = question.optionTwo
        val optionThree = question.optionThree
        val optionFour = question.optionFour
        correctOptionNumber = question.correctAnswer
        quizImageView?.setImageResource(quizImage)
        tvQuestion?.text = quizQuestion
        tvOptionOne?.text = optionOne
        tvOptionTwo?.text = optionTwo
        tvOptionThree?.text = optionThree
        tvOptionFour?.text = optionFour
        btnSubmit?.text = "SUBMIT"
    }

    private fun setProgressBar(current: Int) {
        progressBar?.progress = current
    }

    private fun setProgressText(current: Int, max: Int) {
        tvProgerss?.text = "$current/$max"
    }

    private fun selectedOptionView(tv: TextView, _selectedOptionNumber: Int) {
        defaultOptionsView()
        tv.setTextColor(Color.parseColor("#363A43"))
        selectedOptionNumber = _selectedOptionNumber
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this,
            R.drawable.selected_option_border_bg
        )
    }


    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.tv_option_one -> {
                tvOptionOne?.let {
                    selectedOptionView(it, 1)
                }
            }
            R.id.tv_option_two -> {
                tvOptionTwo?.let {
                    selectedOptionView(it, 2)
                }
            }
            R.id.tv_option_three -> {
                tvOptionThree?.let {
                    selectedOptionView(it, 3)
                }
            }
            R.id.tv_option_four -> {
                tvOptionFour?.let {
                    selectedOptionView(it, 4)
                }
            }
        }
    }

    private fun answerView(answer: Int, drawableView: Int) {
        when (answer) {
            1 -> {
                tvOptionOne?.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            2 -> {
                tvOptionTwo?.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            3 -> {
                tvOptionThree?.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            4 -> {
                tvOptionFour?.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }

        }
    }

}