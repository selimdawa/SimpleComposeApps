package com.flatcode.simplecomposeapps.blogger.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.flatcode.simplecomposeapps.blogger.ui.BloggerDetailsScreen
import com.flatcode.simplecomposeapps.blogger.ui.BloggerPagesScreen
import com.flatcode.simplecomposeapps.blogger.ui.BloggerScreen
import com.flatcode.simplecomposeapps.blogger.viewmodel.BloggerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class BloggerAppActivity : ComponentActivity() {

    @Serializable
    object Home

    @Serializable
    object Pages

    @Serializable
    data class PostDetails(val postId: String)

    @Serializable
    data class PageDetails(val pageId: String)

    private val viewModel: BloggerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            BloggerNavHost(viewModel = viewModel, onFinish = { finish() })
        }
    }
}

@Composable
fun BloggerNavHost(viewModel: BloggerViewModel, onFinish: () -> Unit) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = BloggerAppActivity.Home,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable<BloggerAppActivity.Home> {
            BloggerScreen(
                viewModel = viewModel,
                onBack = onFinish,
                onPagesClick = { navController.navigate(BloggerAppActivity.Pages) },
                onPostClick = { postId -> navController.navigate(BloggerAppActivity.PostDetails(postId)) }
            )
        }
        composable<BloggerAppActivity.Pages> {
            BloggerPagesScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onPageClick = { pageId -> navController.navigate(BloggerAppActivity.PageDetails(pageId)) }
            )
        }
        composable<BloggerAppActivity.PostDetails> { backStackEntry ->
            val args = backStackEntry.toRoute<BloggerAppActivity.PostDetails>()
            BloggerDetailsScreen(
                viewModel = viewModel,
                id = args.postId,
                isPage = false,
                onBack = { navController.popBackStack() }
            )
        }
        composable<BloggerAppActivity.PageDetails> { backStackEntry ->
            val args = backStackEntry.toRoute<BloggerAppActivity.PageDetails>()
            BloggerDetailsScreen(
                viewModel = viewModel,
                id = args.pageId,
                isPage = true,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
