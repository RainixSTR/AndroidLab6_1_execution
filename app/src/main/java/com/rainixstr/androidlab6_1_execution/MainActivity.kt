package com.rainixstr.androidlab6_1_execution

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

class MainActivity : AppCompatActivity() {
    private var secondsElapsed: Int = 0
    private var startTime: Long = 0
    private lateinit var textSecondsElapsed: TextView
    private lateinit var task: Future<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
    }

    override fun onStart() {
        super.onStart()

        task = (application as MyExecutorService).executorService.submit {
            while (!task.isCancelled) {
                startTime = System.currentTimeMillis()
                textSecondsElapsed.post {
                    textSecondsElapsed.text = getString(R.string.seconds_elapsed, secondsElapsed++)
                }
                Thread.sleep(1000 + startTime - System.currentTimeMillis())
                Log.d(
                    "Thread",
                    "$secondsElapsed Error sleeping: ${
                        System.currentTimeMillis() - startTime - 1000}"
                )
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        task.cancel(false)
    }


    override fun onStop() {
        super.onStop()
        task.cancel(true)
        Log.d("Thread","Stopped thread")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.run {
            putInt(SECONDS_ELAPSED, secondsElapsed)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.run {
            secondsElapsed = getInt(SECONDS_ELAPSED)
        }
    }

    companion object {
        const val SECONDS_ELAPSED = "Seconds elapsed"
    }
}

