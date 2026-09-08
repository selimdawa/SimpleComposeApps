package com.flatcode.simplecomposeapps.stockmarket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.flatcode.simplecomposeapps.stockmarket.ui.CompanyListingsScreen
import com.flatcode.simplecomposeapps.stockmarket.model.CompanyListings
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StockMarketActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            StockMarketNav()
        }
    }
}

@Composable
fun StockMarketNav() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = CompanyListings,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable<CompanyListings> {
            CompanyListingsScreen()
        }
    }
}
