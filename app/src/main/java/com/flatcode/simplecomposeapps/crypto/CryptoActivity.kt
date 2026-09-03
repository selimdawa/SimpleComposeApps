package com.flatcode.simplecomposeapps.crypto

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
import com.flatcode.simplecomposeapps.crypto.ui.CryptoDetailScreen
import com.flatcode.simplecomposeapps.crypto.ui.CryptoHomeScreen
import dagger.hilt.android.AndroidEntryPoint
import io.selimdawa.multicolors.MultiColorManager

@AndroidEntryPoint
class CryptoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        MultiColorManager.applyTheme(this)
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            CryptoAppNavHost(onBack = { finish() })
        }
    }
}

@Composable
fun CryptoAppNavHost(onBack: () -> Unit) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            CryptoHomeScreen(
                onBack = onBack,
                onCoinClick = { symbol, id ->
                    navController.navigate("detail/$symbol/$id")
                }
            )
        }
        composable(
            route = "detail/{symbol}/{coinId}",
            arguments = listOf(
                navArgument("symbol") { type = NavType.StringType },
                navArgument("coinId") { type = NavType.IntType }
            )
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