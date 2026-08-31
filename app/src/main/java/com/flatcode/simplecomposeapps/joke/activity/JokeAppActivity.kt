package com.flatcode.simplecomposeapps.joke.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.flatcode.simplecomposeapps.joke.ui.JokeScreen
import com.flatcode.simplecomposeapps.joke.viewmodel.JokeViewModel
import com.flatcode.simplecomposeapps.ui.theme.SimpleComposeAppsTheme

class JokeAppActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this)[JokeViewModel::class.java]

        setContent {
            SimpleComposeAppsTheme {
                JokeScreen(
                    viewModel = viewModel,
                ) { finish() }
            }
        }
    }
}