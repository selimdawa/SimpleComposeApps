package com.flatcode.simplecomposeapps.movies

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.flatcode.simplecomposeapps.movies.models.MovieItemModel
import com.flatcode.simplecomposeapps.movies.ui.MovieDetailScreen
import com.flatcode.simplecomposeapps.movies.ui.MovieFavoriteScreen
import com.flatcode.simplecomposeapps.movies.ui.MovieHomeScreen
import dagger.hilt.android.AndroidEntryPoint
import io.selimdawa.multicolors.MultiColorManager
import com.google.gson.Gson

@AndroidEntryPoint
class MoviesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        MultiColorManager.applyTheme(this)
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            MoviesAppNavHost(onBack = { finish() })
        }
    }
}

@Composable
fun MoviesAppNavHost(onBack: () -> Unit) {
    val navController = rememberNavController()
    val gson = Gson()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            MovieHomeScreen(
                onBack = onBack,
                onMovieClick = { movie ->
                    val movieJson = java.net.URLEncoder.encode(gson.toJson(movie), "UTF-8")
                    navController.navigate("detail/$movieJson")
                },
                onFavoriteClick = {
                    navController.navigate("favorites")
                }
            )
        }
        composable("favorites") {
            MovieFavoriteScreen(
                onBack = { navController.popBackStack() },
                onMovieClick = { movie ->
                    val movieJson = java.net.URLEncoder.encode(gson.toJson(movie), "UTF-8")
                    navController.navigate("detail/$movieJson")
                }
            )
        }
        composable(
            route = "detail/{movieJson}",
            arguments = listOf(navArgument("movieJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val movieJson = backStackEntry.arguments?.getString("movieJson") ?: ""
            val movie = gson.fromJson(java.net.URLDecoder.decode(movieJson, "UTF-8"), MovieItemModel::class.java)
            MovieDetailScreen(
                movie = movie,
                onBack = { navController.popBackStack() }
            )
        }
    }
}