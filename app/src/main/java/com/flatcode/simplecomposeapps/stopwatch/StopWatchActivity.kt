package com.flatcode.simplecomposeapps.stopwatch

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.flatcode.simplecomposeapps.stopwatch.ui.StopWatchScreen
import io.selimdawa.multicolors.MultiColorManager

class StopWatchActivity : AppCompatActivity() {

    private lateinit var viewModel: StopWatchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        MultiColorManager.applyTheme(this)
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[StopWatchViewModel::class.java]

        setContent {
            StopWatchScreen(
                viewModel = viewModel,
                onBack = { finish() }
            )
        }
    }
}