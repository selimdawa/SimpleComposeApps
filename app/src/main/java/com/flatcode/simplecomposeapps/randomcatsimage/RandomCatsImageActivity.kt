package com.flatcode.simplecomposeapps.randomcatsimage

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.activity.ComponentActivity
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.core.net.toUri
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.flatcode.simplecomposeapps.randomcatsimage.ui.RandomCatsImageScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RandomCatsImageActivity : ComponentActivity() {

    private val viewModel: RandomCatsImageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            RandomCatsImageNav(
                viewModel = viewModel,
                onDownload = { url ->
                    val browser = Intent(Intent.ACTION_VIEW, url.toUri())
                    startActivity(browser)
                }
            )
        }
    }
}

@Composable
fun RandomCatsImageNav(viewModel: RandomCatsImageViewModel, onDownload: (String) -> Unit) {
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
            RandomCatsImageScreen(
                viewModel = viewModel,
                onDownload = onDownload
            )
        }
    }
}