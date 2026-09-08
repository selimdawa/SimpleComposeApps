package com.flatcode.simplecomposeapps.stopwatch

import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.flatcode.simplecomposeapps.utils.DATA
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.stopwatch.data.StopWatchDao
import com.flatcode.simplecomposeapps.stopwatch.data.StopWatchEntity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class StopWatchViewModel @Inject constructor(
    private val stopWatchDao: StopWatchDao
) : ViewModel() {

    private val _timeDisplay = mutableStateOf(DATA.ZERO_TIME)
    val timeDisplay: State<String> = _timeDisplay

    private val _lastTime = mutableStateOf(DATA.ZERO_TIME)
    val lastTime: State<String> = _lastTime

    private val _isRunning = mutableStateOf(false)
    val isRunning: State<Boolean> = _isRunning

    init {
        observeLastTime()
    }

    private fun observeLastTime() {
        viewModelScope.launch {
            stopWatchDao.getLastTime().collectLatest {
                _lastTime.value = it ?: DATA.ZERO_TIME
            }
        }
    }

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
            val finalTime = _timeDisplay.value
            _lastTime.value = finalTime
            viewModelScope.launch {
                stopWatchDao.saveLastTime(StopWatchEntity(lastTime = finalTime))
            }
            tMilliSec = 0L
            tStart = 0L
            tBuff = 0L
            tUpdate = 0L
            sec = 0
            min = 0
            milliSec = 0
            _timeDisplay.value = DATA.ZERO_TIME
        }
    }

    override fun onCleared() {
        handler.removeCallbacks(runnable)
    }
}