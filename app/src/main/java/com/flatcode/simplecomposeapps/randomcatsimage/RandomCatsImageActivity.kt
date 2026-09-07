package com.flatcode.simplecomposeapps.randomcatsimage

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.flatcode.simplecomposeapps.randomcatsimage.ui.RandomCatsImageScreen
import dagger.hilt.android.AndroidEntryPoint
import io.selimdawa.multicolors.MultiColorManager

@AndroidEntryPoint
class RandomCatsImageActivity : AppCompatActivity() {

    private val viewModel: RandomCatsImageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        MultiColorManager.applyTheme(this)
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            RandomCatsImageScreen(
                viewModel = viewModel,
                onDownload = { url ->
                    val browser = Intent(Intent.ACTION_VIEW, url.toUri())
                    startActivity(browser)
                },
            )
        }
    }
}