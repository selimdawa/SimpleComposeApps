package com.flatcode.simplecomposeapps.countries

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
import com.flatcode.simplecomposeapps.countries.ui.CountryDetailScreen
import com.flatcode.simplecomposeapps.countries.ui.DashboardScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class CountriesActivity : ComponentActivity() {

    @Serializable
    object Dashboard

    @Serializable
    data class Detail(val countryUuid: Int)

    override fun onCreate(savedInstanceState: Bundle?) {
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
        startDestination = CountriesActivity.Dashboard,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable<CountriesActivity.Dashboard> {
            DashboardScreen(
                onCountryClick = { uuid ->
                    navController.navigate(CountriesActivity.Detail(uuid))
                }
            )
        }
        composable<CountriesActivity.Detail> { backStackEntry ->
            val args = backStackEntry.toRoute<CountriesActivity.Detail>()
            CountryDetailScreen(
                countryUuid = args.countryUuid,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
