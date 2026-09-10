package com.flatcode.simplecomposeapps.movies

import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.ComponentActivity
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.flatcode.simplecomposeapps.movies.model.MovieItemModel
import com.flatcode.simplecomposeapps.movies.ui.MovieDetailScreen
import com.flatcode.simplecomposeapps.movies.ui.MovieFavoriteScreen
import com.flatcode.simplecomposeapps.movies.ui.MovieHomeScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.reflect.typeOf

@AndroidEntryPoint
class MoviesActivity : ComponentActivity() {

    @Serializable
    object Home

    @Serializable
    object Favorites

    @Serializable
    data class Detail(val movie: MovieItemModel)

    companion object {
        val MovieItemModelNavType = object : NavType<MovieItemModel>(isNullableAllowed = false) {
            override fun get(bundle: Bundle, key: String): MovieItemModel? {
                return bundle.getString(key)?.let { Json.decodeFromString(it) }
            }

            override fun parseValue(value: String): MovieItemModel {
                return Json.decodeFromString(Uri.decode(value))
            }

            override fun serializeAsValue(value: MovieItemModel): String {
                return Uri.encode(Json.encodeToString(value))
            }

            override fun put(bundle: Bundle, key: String, value: MovieItemModel) {
                bundle.putString(key, Json.encodeToString(value))
            }
        }
    }

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
        composable<MoviesActivity.Detail>(
            typeMap = mapOf(typeOf<MovieItemModel>() to MoviesActivity.MovieItemModelNavType)
        ) { backStackEntry ->
            val args = backStackEntry.toRoute<MoviesActivity.Detail>()
            MovieDetailScreen(
                movie = args.movie, onBack = { navController.popBackStack() })
        }
    }
}