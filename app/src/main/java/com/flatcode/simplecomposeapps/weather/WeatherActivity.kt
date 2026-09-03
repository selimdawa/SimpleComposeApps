package com.flatcode.simplecomposeapps.weather

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.flatcode.simplecomposeapps.weather.ui.WeatherScreen
import dagger.hilt.android.AndroidEntryPoint
import io.selimdawa.multicolors.MultiColorManager

@AndroidEntryPoint
class WeatherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        MultiColorManager.applyTheme(this)
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            WeatherScreen(onBack = { finish() })
        }
    }
}