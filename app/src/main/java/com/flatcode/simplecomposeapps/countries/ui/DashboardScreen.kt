package com.flatcode.simplecomposeapps.countries.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.flatcode.simplecomposeapps.countries.viewmodel.DashboardViewModel
import com.flatcode.simplecomposeapps.ui.CustomProgressBar
import com.flatcode.simplecomposeapps.ui.ToolbarContent
import com.flatcode.simplecomposeapps.utils.DATA.COLOR_ERROR
import com.flatcode.simplecomposeapps.utils.DATA.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.utils.DATA.MC_TRACK
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.utils.DATA
import com.flatcode.simplecomposeapps.utils.Resource

@Composable
fun DashboardScreen(
    onCountryClick: (Int) -> Unit, viewModel: DashboardViewModel = hiltViewModel()
) {
    val result by viewModel.countriesResult.observeAsState(Resource.Idle)

    Scaffold(
        topBar = {
            ToolbarContent(
                title = DATA.COUNTRIES, hasBack = false
            )
        }, containerColor = COLOR_ON_BACKGROUND
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (result) {
                is Resource.Loading -> {
                    CustomProgressBar(
                        modifier = Modifier.align(Alignment.Center), color = MC_TRACK
                    )
                }

                is Resource.Error -> {
                    Text(
                        text = result.message ?: Strings.ERROR_LOADING_COUNTRIES,
                        modifier = Modifier.align(Alignment.Center),
                        color = COLOR_ERROR
                    )
                }

                is Resource.Success -> {
                    val countries = result.data ?: emptyList()
                    if (countries.isEmpty()) {
                        Text(
                            text = Strings.NONE_DISPLAY,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(16.dp),
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            color = COLOR_ERROR
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(countries) { country ->
                                CountryItem(
                                    item = country,
                                    modifier = Modifier.clickable { onCountryClick(country.uuid) })
                            }
                        }
                    }
                }

                else -> {}
            }
        }
    }
}