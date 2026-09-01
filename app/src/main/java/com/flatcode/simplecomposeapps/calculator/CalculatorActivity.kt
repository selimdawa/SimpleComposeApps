package com.flatcode.simplecomposeapps.calculator

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.flatcode.simplecomposeapps.calculator.ui.CalculatorScreen
import com.flatcode.simplecomposeapps.ui.theme.SimpleComposeAppsTheme
import dagger.hilt.android.AndroidEntryPoint
import io.selimdawa.multicolors.MultiColorManager

@AndroidEntryPoint
class CalculatorActivity : AppCompatActivity() {

    private lateinit var viewModel: CalculatorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        MultiColorManager.applyTheme(this)
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[CalculatorViewModel::class.java]

        setContent {
            SimpleComposeAppsTheme {
                CalculatorScreen(
                    viewModel = viewModel,
                    onBack = { finish() }
                )
            }
        }
    }
}