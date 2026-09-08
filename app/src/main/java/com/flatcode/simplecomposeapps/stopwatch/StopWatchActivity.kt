package com.flatcode.simplecomposeapps.stopwatch

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.activity.ComponentActivity
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.flatcode.simplecomposeapps.stopwatch.ui.StopWatchScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StopWatchActivity : ComponentActivity() {

    private val viewModel: StopWatchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            StopWatchNav(viewModel = viewModel)
        }
    }
}

@Composable
fun StopWatchNav(viewModel: StopWatchViewModel) {
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
            StopWatchScreen(viewModel = viewModel)
        }
    }
}
