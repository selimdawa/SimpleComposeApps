package com.flatcode.simplecomposeapps.stopwatch

import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import java.util.Locale

class StopWatchViewModel : ViewModel() {

    val timeDisplay: State<String>
        field = mutableStateOf("00:00:00")

    val lastTime: State<String>
        field = mutableStateOf("00:00:00")

    val isRunning: State<Boolean>
        field = mutableStateOf(false)

    private var handler = Handler(Looper.getMainLooper())
    private var tMilliSec = 0L
    private var tStart = 0L
    private var tBuff = 0L
    private var tUpdate = 0L
    private var sec = 0
    private var min = 0
    private var milliSec = 0

    private val runnable: Runnable = object : Runnable {
        override fun run() {
            tMilliSec = SystemClock.uptimeMillis() - tStart
            tUpdate = tBuff + tMilliSec
            sec = (tUpdate / 1000).toInt()
            min = sec / 60
            sec %= 60
            milliSec = (tUpdate % 100).toInt()

            timeDisplay.value = String.format(Locale.US, "%02d:%02d:%02d", min, sec, milliSec)
            handler.postDelayed(this, 60)
        }
    }

    fun startOrPause() {
        if (!isRunning.value) {
            tStart = SystemClock.uptimeMillis()
            handler.postDelayed(runnable, 0)
            isRunning.value = true
        } else {
            tBuff += tMilliSec
            handler.removeCallbacks(runnable)
            isRunning.value = false
        }
    }

    fun stop() {
        if (!isRunning.value) {
            lastTime.value = timeDisplay.value
            tMilliSec = 0L
            tStart = 0L
            tBuff = 0L
            tUpdate = 0L
            sec = 0
            min = 0
            milliSec = 0
            timeDisplay.value = "00:00:00"
        }
    }

    override fun onCleared() {
        handler.removeCallbacks(runnable)
    }
}