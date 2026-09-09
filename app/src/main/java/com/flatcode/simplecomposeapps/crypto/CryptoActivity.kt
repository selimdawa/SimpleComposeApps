package com.flatcode.simplecomposeapps.crypto

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
import com.flatcode.simplecomposeapps.crypto.ui.CryptoDetailScreen
import com.flatcode.simplecomposeapps.crypto.ui.CryptoHomeScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class CryptoActivity : ComponentActivity() {

    @Serializable
    object Home

    @Serializable
    data class Detail(val symbol: String, val coinId: Int)

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            CryptoAppNavHost()
        }
    }
}

@Composable
fun CryptoAppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = CryptoActivity.Home,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable<CryptoActivity.Home> {
            CryptoHomeScreen(
                onCoinClick = { symbol, id ->
                    navController.navigate(CryptoActivity.Detail(symbol, id))
                }
            )
        }
        composable<CryptoActivity.Detail> { backStackEntry ->
            val args = backStackEntry.toRoute<CryptoActivity.Detail>()
            CryptoDetailScreen(
                coinId = args.coinId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}