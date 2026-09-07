package com.flatcode.simplecomposeapps.wordpress.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.flatcode.simplecomposeapps.wordpress.model.Post
import com.flatcode.simplecomposeapps.wordpress.model.Rendered
import com.flatcode.simplecomposeapps.wordpress.ui.WordpressDetailsScreen
import com.flatcode.simplecomposeapps.wordpress.ui.WordpressFavoritesScreen
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
            WordpressNavHost(viewModel = viewModel)
        }
    }
}

@Composable
fun WordpressNavHost(viewModel: WordpressViewModel) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "main",
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }) {
        composable("main") {
            WordpressScreen(viewModel = viewModel, onPostClick = { index ->
                val uiState = viewModel.uiState.value
                val post = uiState.posts[index]
                viewModel.selectPost(post)
                navController.navigate("details")
            }, onFavoritesClick = {
                navController.navigate("favorites")
            })
        }
        composable("favorites") {
            val uiState by viewModel.uiState.collectAsState()
            WordpressFavoritesScreen(
                posts = uiState.favoritePosts,
                isLoading = uiState.isLoading,
                onBack = { navController.popBackStack() },
                onPostClick = { post ->
                    val wpPost = Post(
                        id = if (post.id != 0) post.id else post.wpPostId,
                        featuredMedia = post.featuredMedia,
                        featuredMediaUrl = post.featuredMediaUrl,
                        title = Rendered(rendered = post.title?.rendered ?: post.wpTitle),
                        excerpt = Rendered(rendered = post.excerpt?.rendered ?: post.wpExcerpt),
                        content = Rendered(rendered = post.content?.rendered ?: post.wpContent)
                    )
                    viewModel.selectPost(wpPost)
                    navController.navigate("details")
                })
        }
        composable("details") {
            val uiState by viewModel.uiState.collectAsState()
            val post = uiState.selectedPost
            if (post != null) {
                WordpressDetailsScreen(
                    featuredMediaId = post.featuredMedia,
                    featuredMediaUrl = post.featuredMediaUrl,
                    title = post.title?.rendered.orEmpty(),
                    content = post.content?.rendered.orEmpty(),
                    isFavorite = uiState.favoritePosts.any { it.wpPostId == post.id || it.id == post.id },
                    onBack = { navController.popBackStack() },
                    onToggleFavorite = { viewModel.toggleFavorite(post) })
            } else {
                navController.popBackStack()
            }
        }
    }
}