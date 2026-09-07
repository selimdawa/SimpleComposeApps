package com.flatcode.simplecomposeapps.candycrushgame

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.flatcode.simplecomposeapps.candycrushgame.ui.CandyCrushScreen
import dagger.hilt.android.AndroidEntryPoint
import io.selimdawa.multicolors.MultiColorManager

@AndroidEntryPoint
class CandyCrushGameActivity : AppCompatActivity() {

    private val viewModel: CandyCrushViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        MultiColorManager.applyTheme(this)
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            CandyCrushScreen(
                viewModel = viewModel
            )
        }
    }
}