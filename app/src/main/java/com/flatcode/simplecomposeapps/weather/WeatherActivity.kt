package com.flatcode.simplecomposeapps.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.flatcode.simplecomposeapps.weather.ui.WeatherMainScreen
import dagger.hilt.android.AndroidEntryPoint
import io.selimdawa.multicolors.MultiColorManager

@AndroidEntryPoint
class WeatherActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // Apply theme from the MultiColors library
        MultiColorManager.applyTheme(this)

        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            MultiColorManager.applyTheme(this)
            WeatherNav()
        }
    }
}

@Composable
fun WeatherNav() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "main",
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable("main") {
            WeatherMainScreen()
        }
    }
}