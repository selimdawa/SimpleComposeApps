package com.flatcode.simplecomposeapps.randomcatsimage

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import com.flatcode.simplecomposeapps.randomcatsimage.ui.RandomCatsImageScreen
import io.selimdawa.multicolors.MultiColorManager

class RandomCatsImageActivity : AppCompatActivity() {

    private lateinit var viewModel: RandomCatsImageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        MultiColorManager.applyTheme(this)
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[RandomCatsImageViewModel::class.java]

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