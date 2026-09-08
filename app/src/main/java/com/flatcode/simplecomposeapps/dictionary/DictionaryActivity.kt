package com.flatcode.simplecomposeapps.dictionary

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.flatcode.simplecomposeapps.dictionary.ui.DictionaryScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DictionaryActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            DictionaryAppNavHost()
        }
    }
}

@Composable
fun DictionaryAppNavHost() {
    val viewModel: DictionaryViewModel = hiltViewModel()

    DictionaryScreen(
        viewModel = viewModel
    )
}