package com.flatcode.simplecomposeapps.news.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.flatcode.simplecomposeapps.news.ui.NewsScreen
import com.flatcode.simplecomposeapps.news.viewmodel.NewsViewModel
import com.flatcode.simplecomposeapps.utils.DATA
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsAppActivity : ComponentActivity() {

    private val viewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            NewsScreen(
                viewModel = viewModel,
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
