package com.flatcode.simplecomposeapps.wordpress.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.flatcode.simplecomposeapps.wordpress.ui.WordpressScreen
import com.flatcode.simplecomposeapps.wordpress.viewmodel.WordpressViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WordpressActivity : ComponentActivity() {

    private val viewModel: WordpressViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            WordpressScreen(
                viewModel = viewModel,
                onPostClick = { index ->
                    val post = viewModel.uiState.value.posts[index]
                    val intent = WordpressDetailsActivity.createIntent(
                        this,
                        post.id,
                        post.featuredMedia,
                        post.title?.rendered,
                        post.excerpt?.rendered,
                        post.content?.rendered
                    )
                    startActivity(intent)
                },
                onFavoritesClick = {
                    val intent = Intent(this, WordpressFavoritesActivity::class.java)
                    startActivity(intent)
                }
            )
        }
    }
}
