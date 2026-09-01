package com.flatcode.simplecomposeapps.pokemon

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
import com.flatcode.simplecomposeapps.pokemon.ui.PokemonDetailScreen
import com.flatcode.simplecomposeapps.pokemon.ui.PokemonScreen
import com.flatcode.simplecomposeapps.ui.theme.SimpleComposeAppsTheme
import dagger.hilt.android.AndroidEntryPoint
import io.selimdawa.multicolors.MultiColorManager

@AndroidEntryPoint
class PokemonActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        MultiColorManager.applyTheme(this)
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            SimpleComposeAppsTheme {
                PokemonAppNavHost(onBack = { finish() })
            }
        }
    }
}

@Composable
fun PokemonAppNavHost(onBack: () -> Unit) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            PokemonScreen(
                onBack = onBack,
                onPokemonClick = { id ->
                    navController.navigate("detail/$id")
                }
            )
        }
        composable(
            route = "detail/{pokeId}",
            arguments = listOf(navArgument("pokeId") { type = NavType.IntType })
        ) { backStackEntry ->
            val pokeId = backStackEntry.arguments?.getInt("pokeId") ?: 0
            PokemonDetailScreen(
                pokeId = pokeId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}