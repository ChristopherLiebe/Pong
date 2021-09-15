package com.example.pong

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import java.util.*
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    lateinit var leftButton : Button
    lateinit var rightButton : Button
    lateinit var playerPaddleDisplay : ImageView
    lateinit var computerPaddleDisplay : ImageView
    lateinit var ball : ImageView
    lateinit var scoreText : TextView
    lateinit var startButton : Button
    var ballx by Delegates.notNull<Int>()
    var bally by Delegates.notNull<Int>()

    var SPEED = 5
    var BSPEED = 8
    var score = 0
    var scoreMem = score

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wireWidgets()


        startButton.setOnClickListener() {
            startButton.visibility = INVISIBLE
            startButton.isEnabled = false
            score = 0
            scoreMem = score
            scoreText.text = "$score"
            startBall()
            gameLoop()
        }

        leftButton.setOnClickListener() {
            leftMoveTimer()
        }

        rightButton.setOnClickListener() {
            rightMoveTimer()
        }



    }

    private fun wireWidgets() {
        leftButton = findViewById(R.id.LeftButton)
        rightButton = findViewById(R.id.RightButton)
        playerPaddleDisplay = findViewById(R.id.Player_paddle)
        computerPaddleDisplay = findViewById(R.id.Computer_paddle)
        ball = findViewById(R.id.Ball)
        scoreText = findViewById(R.id.scoreView)
        startButton = findViewById(R.id.startButton)
    }

    fun gameLoop() {
        val gameTimer = object : CountDownTimer(100000, 1) {
            override fun onTick(millisUntilFinished: Long) {
                ball.x += ballx
                ball.y += bally
                computerPaddleDisplay.x = ball.x
                if(ball.x >= 1290) {
                    ballx *= -1
                }
                if(ball.x <= 10) {
                    ballx *= -1
                }
                if(ball.y <= 150) {
                    bally *= -1
                    scoreMem = score
                }
                if((ball.x >= playerPaddleDisplay.x - 150) && (ball.x <= playerPaddleDisplay.x + 200) && (ball.y >= playerPaddleDisplay.y - 80) && (ball.y <= playerPaddleDisplay.y + 40)) {
                    bally = -Math.abs(bally)
                    ball.y -= 10
                    score = scoreMem + 1
                    scoreText.text = "$score"
                    if(ballx > 0 ) {
                        ballx++
                    }
                    else {
                        ballx--
                    }
                    if(bally > 0) {
                        bally++
                    }
                    else {
                        bally--
                    }
                }
                if(ball.y >= 2540) {
                    onFinish()
                }
            }

            override fun onFinish() {
                gameReset()
                cancel()

            }
        }


        gameTimer.start()
    }

    private fun leftMoveTimer() {
        val leftTimer = object : CountDownTimer(350, 10) {
            override fun onTick(millisUntilFinished: Long) {
                if(playerPaddleDisplay.x > 0) {
                    playerPaddleDisplay.x -= SPEED
                }
            }
            override fun onFinish() {
            }
        }
        leftTimer.start()
    }

    private fun rightMoveTimer() {
        val rightTimer = object : CountDownTimer(350, 10) {
            override fun onTick(millisUntilFinished: Long) {
                if(playerPaddleDisplay.x < 1140) {
                    playerPaddleDisplay.x += SPEED
                }
            }
            override fun onFinish() {
            }
        }
        rightTimer.start()
    }

    private fun startBall() {
        ballx = (Math.random()*2).toInt() + 1
        bally = (Math.random()*2).toInt() + 1
        if (ballx == 1) {
            ballx = -BSPEED
        }
        else {
            ballx = BSPEED
        }

        if(bally == 1) {
            bally = -BSPEED
        }
        else {
            bally = BSPEED
        }
    }

    private fun gameReset() {
        ball.x = 640F
        ball.y = 1280F
        ballx = 0
        bally = 0
        startButton.visibility = VISIBLE
        startButton.isEnabled = true

    }
}