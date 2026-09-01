package com.flatcode.simplecomposeapps.pop

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.flatcode.simplecomposeapps.pop.ui.PopScreen
import com.flatcode.simplecomposeapps.ui.theme.SimpleComposeAppsTheme
import dagger.hilt.android.AndroidEntryPoint
import io.selimdawa.multicolors.MultiColorManager

@AndroidEntryPoint
class PopActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        MultiColorManager.applyTheme(this)
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            SimpleComposeAppsTheme {
                PopScreen(onBack = { finish() })
            }
        }
    }
}