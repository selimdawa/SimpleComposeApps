package com.flatcode.simplecomposeapps.countries.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.flatcode.simplecomposeapps.countries.DashboardViewModel
import com.flatcode.simplecomposeapps.ui.ToolbarContent
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ERROR
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.ui.theme.MC_TRACK
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.utils.DATA

@Composable
fun DashboardScreen(
    onBack: () -> Unit,
    onCountryClick: (Int) -> Unit,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val countries by viewModel.countries.observeAsState(emptyList())
    val isLoading by viewModel.countryLoading.observeAsState(true)
    val isError by viewModel.countryError.observeAsState(false)

    LaunchedEffect(Unit) {
        viewModel.refreshData()
    }

    Scaffold(
        topBar = {
            ToolbarContent(
                title = DATA.COUNTRIES, hasBack = false, onBackClick = onBack
            )
        }, containerColor = COLOR_ON_BACKGROUND
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (isLoading && countries.isEmpty()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center), color = MC_TRACK
                )
            } else if (isError) {
                Text(
                    text = "Error loading countries",
                    modifier = Modifier.align(Alignment.Center),
                    color = COLOR_ERROR
                )
            } else if (!isLoading && countries.isEmpty()) {
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
                    modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(top = 10.dp)
                ) {
                    items(countries) { country ->
                        CountryItem(
                            item = country,
                            modifier = Modifier.clickable { onCountryClick(country.uuid) })
                    }
                }
            }
        }
    }
}