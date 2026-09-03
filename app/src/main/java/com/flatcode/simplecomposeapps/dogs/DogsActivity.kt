package com.flatcode.simplecomposeapps.dogs

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModelProvider
import com.flatcode.simplecomposeapps.dogs.ui.DogsScreen
import com.flatcode.simplecomposeapps.ui.theme.Strings
import dagger.hilt.android.AndroidEntryPoint
import io.selimdawa.multicolors.MultiColorManager

@AndroidEntryPoint
class DogsActivity : AppCompatActivity() {

    private lateinit var viewModel: DogViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        MultiColorManager.applyTheme(this)
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[DogViewModel::class.java]

        setContent {
            LaunchedEffect(Unit) {
                viewModel.setBreedsList(Strings.BREEDS_LIST)
            }

            DogsScreen(
                viewModel = viewModel,
                onBack = { finish() }
            )
        }
    }
}