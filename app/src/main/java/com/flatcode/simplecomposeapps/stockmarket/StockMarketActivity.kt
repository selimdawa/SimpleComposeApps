package com.flatcode.simplecomposeapps.stockmarket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.flatcode.simplecomposeapps.stockmarket.ui.CompanyListingsScreen
import com.flatcode.simplecomposeapps.stockmarket.viewmodel.StockMarketViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StockMarketActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            StockMarketNavHost()
        }
    }
}

@Composable
fun StockMarketNavHost() {
    val viewModel: StockMarketViewModel = hiltViewModel()

    CompanyListingsScreen(
        viewModel = viewModel
    )
}