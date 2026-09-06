package com.flatcode.simplecomposeapps.countries

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.flatcode.simplecomposeapps.countries.ui.CountryDetailScreen
import com.flatcode.simplecomposeapps.countries.ui.DashboardScreen
import dagger.hilt.android.AndroidEntryPoint
import io.selimdawa.multicolors.MultiColorManager

@AndroidEntryPoint
class CountriesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        MultiColorManager.applyTheme(this)
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            CountriesAppNavHost()
        }
    }
}

@Composable
fun CountriesAppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "dashboard",
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable("dashboard") {
            DashboardScreen(
                onCountryClick = { uuid ->
                    navController.navigate("detail/$uuid")
                }
            )
        }
        composable(
            route = "detail/{countryUuid}",
            arguments = listOf(navArgument("countryUuid") { type = NavType.IntType })
        ) { backStackEntry ->
            val countryUuid = backStackEntry.arguments?.getInt("countryUuid") ?: 0
            CountryDetailScreen(
                countryUuid = countryUuid,
                onBack = { navController.popBackStack() }
            )
        }
    }
}