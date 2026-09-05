package com.flatcode.simplecomposeapps.rickAndMorty.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.flatcode.simplecomposeapps.rickAndMorty.utils.Resource
import com.flatcode.simplecomposeapps.rickAndMorty.viewmodel.RickLocationsViewModel
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ERROR
import com.flatcode.simplecomposeapps.ui.theme.MC_TRACK

@Composable
fun RickLocationsScreen(
    onBack: () -> Unit, viewModel: RickLocationsViewModel = hiltViewModel()
) {
    val state by viewModel.locations.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getLocations()
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when (state) {
            is Resource.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center), color = MC_TRACK
                )
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
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                    color = COLOR_ERROR
                )
            }
        }
    }
}