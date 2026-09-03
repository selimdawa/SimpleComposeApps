package com.flatcode.simplecomposeapps.news.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.flatcode.simplecomposeapps.news.ui.NewsScreen
import com.flatcode.simplecomposeapps.news.viewmodel.NewsViewModel
import com.flatcode.simplecomposeapps.utils.DATA

class NewsAppActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this)[NewsViewModel::class.java]

        setContent {
            NewsScreen(
                viewModel = viewModel,
                onBack = { finish() },
                onNewsClick = { headline ->
                    val intent = Intent(this, NewsAppDetailsActivity::class.java).apply {
                        putExtra(DATA.DATA, headline)
                    }
                    startActivity(intent)
                }
            )
        }
    }
}
