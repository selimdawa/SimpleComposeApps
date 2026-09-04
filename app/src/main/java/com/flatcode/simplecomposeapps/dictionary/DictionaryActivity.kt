package com.flatcode.simplecomposeapps.dictionary

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.flatcode.simplecomposeapps.dictionary.ui.DictionaryScreen
import dagger.hilt.android.AndroidEntryPoint
import io.selimdawa.multicolors.MultiColorManager

@AndroidEntryPoint
class DictionaryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        MultiColorManager.applyTheme(this)
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            DictionaryAppNavHost(onBack = { finish() })
        }
    }
}

@Composable
fun DictionaryAppNavHost(onBack: () -> Unit) {
    val viewModel: DictionaryViewModel = hiltViewModel()

    DictionaryScreen(
        viewModel = viewModel,
        onBack = onBack
    )
}
