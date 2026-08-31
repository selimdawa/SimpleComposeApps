package com.flatcode.simplecomposeapps.wordpress.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.flatcode.simplecomposeapps.ui.theme.SimpleComposeAppsTheme
import com.flatcode.simplecomposeapps.wordpress.model.Post
import com.flatcode.simplecomposeapps.wordpress.model.Rendered
import com.flatcode.simplecomposeapps.wordpress.ui.WordpressDetailsScreen
import com.flatcode.simplecomposeapps.wordpress.viewmodel.WordpressViewModel

class WordpressDetailsActivity : ComponentActivity() {

    private val viewModel: WordpressViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val id = intent.getIntExtra("postId", -1)
        val featuredMedia = intent.getIntExtra("featuredMedia", -1)
        val title = intent.getStringExtra("postTitle").orEmpty()
        val excerpt = intent.getStringExtra("postExcerpt").orEmpty()
        val contentPost = intent.getStringExtra("postContent").orEmpty()
            .replace("\\\\n".toRegex(), "<br>")
            .replace("\\\\r".toRegex(), "").replace("\\\\".toRegex(), "")

        val post = Post(
            id = id,
            featuredMedia = featuredMedia,
            title = Rendered(rendered = title),
            excerpt = Rendered(rendered = excerpt),
            content = Rendered(rendered = contentPost)
        )

        setContent {
            SimpleComposeAppsTheme {
                val uiState by viewModel.uiState.collectAsState()
                
                // For details, we might want to check the DB directly or use a specific state
                // But let's use the toggleFavorite from ViewModel
                
                WordpressDetailsScreen(
                    id = id,
                    featuredMediaId = featuredMedia,
                    title = title,
                    content = contentPost,
                    isFavorite = uiState.favoritePosts.any { it.wpPostId == id },
                    onBack = { finish() },
                    onToggleFavorite = {
                        viewModel.toggleFavorite(post)
                    }
                )
            }
        }
    }

    companion object {
        fun createIntent(
            context: android.content.Context, id: Int, featuredMedia: Int, title: String?,
            excerpt: String?, content: String?,
        ): android.content.Intent {
            return android.content.Intent(context, WordpressDetailsActivity::class.java).apply {
                putExtra("postId", id)
                putExtra("featuredMedia", featuredMedia)
                putExtra("postExcerpt", excerpt)
                putExtra("postTitle", title)
                putExtra("postContent", content)
            }
        }
    }
}
