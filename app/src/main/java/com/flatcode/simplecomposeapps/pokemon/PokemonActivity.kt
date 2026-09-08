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
import androidx.navigation.toRoute
import com.flatcode.simplecomposeapps.pokemon.ui.PokemonDetailScreen
import com.flatcode.simplecomposeapps.pokemon.ui.PokemonScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class PokemonActivity : ComponentActivity() {

    @Serializable
    object List

    @Serializable
    data class Detail(val pokeId: Int)

    override fun onCreate(savedInstanceState: Bundle?) {
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
        startDestination = PokemonActivity.List,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }) {
        composable<PokemonActivity.List> {
            PokemonScreen(
                onPokemonClick = { id ->
                    navController.navigate(PokemonActivity.Detail(id))
                })
        }
        composable<PokemonActivity.Detail> { backStackEntry ->
            val args = backStackEntry.toRoute<PokemonActivity.Detail>()
            PokemonDetailScreen(
                pokeId = args.pokeId, onBack = { navController.popBackStack() })
        }
    }
}