package com.flatcode.simplecomposeapps.blogger.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.flatcode.simplecomposeapps.blogger.ui.BloggerDetailsScreen
import com.flatcode.simplecomposeapps.blogger.ui.BloggerPagesScreen
import com.flatcode.simplecomposeapps.blogger.ui.BloggerScreen
import com.flatcode.simplecomposeapps.blogger.viewmodel.BloggerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BloggerAppActivity : ComponentActivity() {

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
        startDestination = "home",
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable("home") {
            BloggerScreen(
                viewModel = viewModel,
                onBack = onFinish,
                onPagesClick = { navController.navigate("pages") },
                onPostClick = { postId -> navController.navigate("postDetails/$postId") }
            )
        }
        composable("pages") {
            BloggerPagesScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onPageClick = { pageId -> navController.navigate("pageDetails/$pageId") }
            )
        }
        composable(
            route = "postDetails/{postId}",
            arguments = listOf(navArgument("postId") { type = NavType.StringType })
        ) { backStackEntry ->
            val postId = backStackEntry.arguments?.getString("postId") ?: ""
            BloggerDetailsScreen(
                viewModel = viewModel,
                id = postId,
                isPage = false,
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            route = "pageDetails/{pageId}",
            arguments = listOf(navArgument("pageId") { type = NavType.StringType })
        ) { backStackEntry ->
            val pageId = backStackEntry.arguments?.getString("pageId") ?: ""
            BloggerDetailsScreen(
                viewModel = viewModel,
                id = pageId,
                isPage = true,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
