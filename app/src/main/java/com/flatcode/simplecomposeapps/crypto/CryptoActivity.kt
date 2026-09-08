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
import com.flatcode.simplecomposeapps.crypto.ui.CryptoDetailScreen
import com.flatcode.simplecomposeapps.crypto.ui.CryptoHomeScreen
import com.flatcode.simplecomposeapps.utils.DATA
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CryptoActivity : ComponentActivity() {

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
        startDestination = "home",
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable("home") {
            CryptoHomeScreen(
                onCoinClick = { symbol, id ->
                    navController.navigate("detail/$symbol/$id")
                }
            )
        }
        composable(
            route = "detail/{symbol}/{coinId}",
            arguments = DATA.CRYPTO_DETAIL_ARGS
        ) { backStackEntry ->
            val symbol = backStackEntry.arguments?.getString("symbol") ?: ""
            val coinId = backStackEntry.arguments?.getInt("coinId") ?: 0
            CryptoDetailScreen(
                symbol = symbol,
                coinId = coinId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}