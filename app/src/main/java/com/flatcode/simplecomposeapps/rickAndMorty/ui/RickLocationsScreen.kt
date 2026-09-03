package com.flatcode.simplecomposeapps.rickAndMorty.ui

import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.flatcode.simplecomposeapps.rickAndMorty.RickLocationsViewModel
import com.flatcode.simplecomposeapps.rickAndMorty.utils.Resource
import com.flatcode.simplecomposeapps.ui.ToolbarContent
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ON_BACKGROUND

@Composable
fun RickLocationsScreen(
    onBack: () -> Unit,
    viewModel: RickLocationsViewModel = hiltViewModel()
) {
    val state by viewModel.locations.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getLocations()
    }

    Scaffold(
        topBar = {
            ToolbarContent(title = "Locations", hasBack = false, onBackClick = onBack)
        },
        containerColor = COLOR_ON_BACKGROUND
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (state) {
                is Resource.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                is Resource.Success -> {
                    val locations = state.data?.results ?: emptyList()
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(locations) { location ->
                            LocationItem(item = location)
                        }
                    }
                }

                is Resource.Error -> {
                    Text(
                        text = state.message ?: "Error",
                        modifier = Modifier.align(Alignment.Center).padding(16.dp),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}