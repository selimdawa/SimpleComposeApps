package com.flatcode.simplecomposeapps.candycrushgame

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.flatcode.simplecomposeapps.candycrushgame.ui.CandyCrushScreen
import com.flatcode.simplecomposeapps.ui.theme.SimpleComposeAppsTheme
import io.selimdawa.multicolors.MultiColorManager

class CandyCrushGameActivity : AppCompatActivity() {

    private lateinit var viewModel: CandyCrushViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        MultiColorManager.applyTheme(this)
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[CandyCrushViewModel::class.java]

        setContent {
            SimpleComposeAppsTheme {
                CandyCrushScreen(
                    viewModel = viewModel,
                    onBack = { finish() }
                )
            }
        }
    }
}