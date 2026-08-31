package com.flatcode.simplecomposeapps.stopwatch

import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import java.util.Locale

class StopWatchViewModel : ViewModel() {

    private val _timeDisplay = mutableStateOf("00:00:00")
    val timeDisplay: State<String> = _timeDisplay

    private val _lastTime = mutableStateOf("00:00:00")
    val lastTime: State<String> = _lastTime

    private val _isRunning = mutableStateOf(false)
    val isRunning: State<Boolean> = _isRunning

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

            _timeDisplay.value = String.format(Locale.US, "%02d:%02d:%02d", min, sec, milliSec)
            handler.postDelayed(this, 60)
        }
    }

    fun startOrPause() {
        if (!_isRunning.value) {
            tStart = SystemClock.uptimeMillis()
            handler.postDelayed(runnable, 0)
            _isRunning.value = true
        } else {
            tBuff += tMilliSec
            handler.removeCallbacks(runnable)
            _isRunning.value = false
        }
    }

    fun stop() {
        if (!_isRunning.value) {
            _lastTime.value = _timeDisplay.value
            tMilliSec = 0L
            tStart = 0L
            tBuff = 0L
            tUpdate = 0L
            sec = 0
            min = 0
            milliSec = 0
            _timeDisplay.value = "00:00:00"
        }
    }

    override fun onCleared() {
        super.onCleared()
        handler.removeCallbacks(runnable)
    }
}