package com.flatcode.simplecomposeapps.news.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.flatcode.simplecomposeapps.news.ui.NewsAppDetailsScreen
import com.flatcode.simplecomposeapps.news.ui.NewsScreen
import com.flatcode.simplecomposeapps.news.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsAppActivity : ComponentActivity() {

    private val viewModel: NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            NewsNavHost(viewModel = viewModel)
        }
    }
}

@Composable
fun NewsNavHost(viewModel: NewsViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            NewsScreen(
                viewModel = viewModel,
                onNewsClick = { headline ->
                    (viewModel.selectedHeadline as MutableState).value = headline
                    navController.navigate("details")
                }
            )
        }
        composable("details") {
            val headline = viewModel.selectedHeadline.value
            if (headline != null) {
                NewsAppDetailsScreen(
                    headline = headline,
                    onBack = { navController.popBackStack() }
                )
            } else {
                navController.popBackStack()
            }
        }
    }
}

