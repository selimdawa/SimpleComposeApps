package com.flatcode.simplecomposeapps.rickAndMorty.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.flatcode.simplecomposeapps.rickAndMorty.viewmodel.RickLocationsViewModel
import com.flatcode.simplecomposeapps.utils.Resource
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ERROR
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.ui.theme.MC_TRACK

@Composable
fun RickLocationsScreen(
    viewModel: RickLocationsViewModel = hiltViewModel()
) {
    val state by viewModel.locations.observeAsState(Resource.Loading())
    val isLoading by viewModel.isLoading.observeAsState(false)
    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        if (state.data.isNullOrEmpty()) {
            viewModel.getLocations()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(COLOR_ON_BACKGROUND)
    ) {
        when (state) {
            is Resource.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center), color = MC_TRACK
                )
            }

            is Resource.Success -> {
                val locations = state.data ?: emptyList()

                LaunchedEffect(listState, locations.size) {
                    snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
                        .collect { lastVisibleItemIndex ->
                            if (lastVisibleItemIndex != null && lastVisibleItemIndex >= locations.size - 5) {
                                viewModel.getLocations()
                            }
                        }
                }

                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(locations) { location ->
                        LocationItem(item = location)
                    }

                    if (isLoading) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(color = MC_TRACK)
                            }
                        }
                    }
                }
            }

            is Resource.Error -> {
                Text(
                    text = state.message ?: "Error",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(24.dp),
                    color = COLOR_ERROR,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }

            else -> {}
        }
    }
}