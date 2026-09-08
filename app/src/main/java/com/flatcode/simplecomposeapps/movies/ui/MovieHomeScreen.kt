package com.flatcode.simplecomposeapps.movies.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.flatcode.simplecomposeapps.movies.MovieHomeViewModel
import com.flatcode.simplecomposeapps.movies.models.MovieItemModel
import com.flatcode.simplecomposeapps.utils.Resource
import com.flatcode.simplecomposeapps.ui.ToolbarContentFav
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ERROR
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.ui.theme.MC_TRACK
import com.flatcode.simplecomposeapps.utils.DATA

@Composable
fun MovieHomeScreen(
    onMovieClick: (MovieItemModel) -> Unit,
    onFavoriteClick: () -> Unit,
    viewModel: MovieHomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.observeAsState()

    Scaffold(
        topBar = {
            ToolbarContentFav(
                title = DATA.MOVIE,
                onFavoriteClick = onFavoriteClick
            )
        },
        containerColor = COLOR_ON_BACKGROUND
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (uiState) {
                is Resource.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = MC_TRACK
                    )
                }

                is Resource.Success -> {
                    val movies = uiState?.data ?: emptyList()
                    LazyVerticalStaggeredGrid(
                        columns = StaggeredGridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 5.dp)
                    ) {
                        items(movies) { movie ->
                            MovieItem(
                                movie = movie,
                                modifier = Modifier.clickable { onMovieClick(movie) }
                            )
                        }
                    }
                }

                is Resource.Error -> {
                    Text(
                        text = uiState?.message ?: "Error",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp),
                        textAlign = TextAlign.Center,
                        color = COLOR_ERROR
                    )
                }

                else -> {}
            }
        }
    }
}