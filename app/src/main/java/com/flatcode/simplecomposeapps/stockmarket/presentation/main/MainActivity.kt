package com.flatcode.simplecomposeapps.stockmarket.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.flatcode.simplecomposeapps.stockmarket.presentation.company_listings.CompanyListingsScreen
import com.flatcode.simplecomposeapps.stockmarket.presentation.util.CompanyListings
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ON_BACKGROUND
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(), color = COLOR_ON_BACKGROUND
            ) {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = CompanyListings
                ) {
                    composable<CompanyListings> {
                        CompanyListingsScreen()
                    }
                }
            }
        }
    }
}
