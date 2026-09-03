package com.flatcode.simplecomposeapps.wordpress.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.flatcode.simplecomposeapps.wordpress.ui.WordpressFavoritesScreen
import com.flatcode.simplecomposeapps.wordpress.viewmodel.WordpressViewModel

class WordpressFavoritesActivity : ComponentActivity() {

    private val viewModel: WordpressViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            val uiState by viewModel.uiState.collectAsState()

            WordpressFavoritesScreen(
                posts = uiState.favoritePosts,
                isLoading = uiState.isLoading,
                onBack = { finish() },
                onPostClick = { post ->
                    val intent = WordpressDetailsActivity.createIntent(
                        this@WordpressFavoritesActivity,
                        if (post.id != 0) post.id else post.wpPostId,
                        post.featuredMedia,
                        post.title?.rendered ?: post.wpTitle,
                        post.excerpt?.rendered ?: post.wpExcerpt,
                        post.content?.rendered ?: post.wpContent
                    )
                    startActivity(intent)
                })
        }
    }
}