package com.flatcode.simplecomposeapps.joke.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.flatcode.simplecomposeapps.joke.ui.JokeScreen
import com.flatcode.simplecomposeapps.joke.viewmodel.JokeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JokeAppActivity : ComponentActivity() {

    private val viewModel: JokeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            JokeNav(viewModel = viewModel)
        }
    }
}

@Composable
fun JokeNav(viewModel: JokeViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            JokeScreen(viewModel = viewModel)
        }
    }
}
