package com.flatcode.simplecomposeapps.candycrushgame

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
import com.flatcode.simplecomposeapps.candycrushgame.ui.CandyCrushScreen
import dagger.hilt.android.AndroidEntryPoint
import io.selimdawa.multicolors.MultiColorManager

@AndroidEntryPoint
class CandyCrushGameActivity : ComponentActivity() {

    private val viewModel: CandyCrushViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        MultiColorManager.applyTheme(this)
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            CandyCrushNav(viewModel = viewModel)
        }
    }
}

@Composable
fun CandyCrushNav(viewModel: CandyCrushViewModel) {
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
            CandyCrushScreen(viewModel = viewModel)
        }
    }
}
