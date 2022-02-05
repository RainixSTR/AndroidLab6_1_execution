package com.rainixstr.androidlab6_1_execution

import android.app.Application
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MyExecutorService: Application() {
    val executorService: ExecutorService = Executors.newFixedThreadPool(1)
}