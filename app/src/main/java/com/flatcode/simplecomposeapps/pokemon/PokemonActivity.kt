package com.flatcode.simplecomposeapps.pokemon

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
import com.flatcode.simplecomposeapps.pokemon.ui.PokemonDetailScreen
import com.flatcode.simplecomposeapps.pokemon.ui.PokemonScreen
import com.flatcode.simplecomposeapps.utils.DATA
import dagger.hilt.android.AndroidEntryPoint
import io.selimdawa.multicolors.MultiColorManager

@AndroidEntryPoint
class PokemonActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        MultiColorManager.applyTheme(this)
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            PokemonAppNavHost()
        }
    }
}

@Composable
fun PokemonAppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "list",
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }) {
        composable("list") {
            PokemonScreen(
                onPokemonClick = { id ->
                    navController.navigate("detail/$id")
                })
        }
        composable(
            route = "detail/{pokeId}", arguments = DATA.POKE_DETAIL_ARGS
        ) { backStackEntry ->
            val pokeId = backStackEntry.arguments?.getInt("pokeId") ?: 0
            PokemonDetailScreen(
                pokeId = pokeId, onBack = { navController.popBackStack() })
        }
    }
}