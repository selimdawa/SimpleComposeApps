package com.flatcode.simplecomposeapps.countries

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.flatcode.simplecomposeapps.countries.ui.CountryDetailScreen
import com.flatcode.simplecomposeapps.countries.ui.DashboardScreen
import com.flatcode.simplecomposeapps.utils.DATA
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
        startDestination = DATA.DASHBOARD,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable(DATA.DASHBOARD) {
            DashboardScreen(
                onCountryClick = { uuid ->
                    navController.navigate("${DATA.DETAIL_BY_ID}$uuid")
                }
            )
        }
        composable(
            route = DATA.DETAIL_COUNTRY,
            arguments = DATA.COUNTRY_DETAIL_ARGS
        ) { backStackEntry ->
            val countryUuid = backStackEntry.arguments?.getInt(DATA.COUNTRY_UUID) ?: 0
            CountryDetailScreen(
                countryUuid = countryUuid,
                onBack = { navController.popBackStack() }
            )
        }
    }
}