package com.flatcode.simplecomposeapps.movies

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.ComponentActivity
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.flatcode.simplecomposeapps.movies.models.MovieItemModel
import com.flatcode.simplecomposeapps.movies.ui.MovieDetailScreen
import com.flatcode.simplecomposeapps.movies.ui.MovieFavoriteScreen
import com.flatcode.simplecomposeapps.movies.ui.MovieHomeScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MoviesActivity : ComponentActivity() {

    @Serializable
    object Home

    @Serializable
    object Favorites

    @Serializable
    data class Detail(val movie: MovieItemModel)

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            MoviesAppNavHost()
        }
    }
}

@Composable
fun MoviesAppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = MoviesActivity.Home,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }) {
        composable<MoviesActivity.Home> {
            MovieHomeScreen(onMovieClick = { movie ->
                navController.navigate(MoviesActivity.Detail(movie))
            }, onFavoriteClick = {
                navController.navigate(MoviesActivity.Favorites)
            })
        }
        composable<MoviesActivity.Favorites> {
            MovieFavoriteScreen(onBack = { navController.popBackStack() }, onMovieClick = { movie ->
                navController.navigate(MoviesActivity.Detail(movie))
            })
        }
        composable<MoviesActivity.Detail> { backStackEntry ->
            val args = backStackEntry.toRoute<MoviesActivity.Detail>()
            MovieDetailScreen(
                movie = args.movie, onBack = { navController.popBackStack() })
        }
    }
}
