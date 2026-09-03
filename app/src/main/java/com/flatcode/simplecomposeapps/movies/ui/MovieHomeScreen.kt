package com.flatcode.simplecomposeapps.movies.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.flatcode.simplecomposeapps.movies.MovieHomeViewModel
import com.flatcode.simplecomposeapps.movies.models.MovieItemModel
import com.flatcode.simplecomposeapps.movies.models.MoviesUiState
import com.flatcode.simplecomposeapps.ui.AppIcons
import com.flatcode.simplecomposeapps.ui.ToolbarContent
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.ui.theme.Strings

@Composable
fun MovieHomeScreen(
    onBack: () -> Unit,
    onMovieClick: (MovieItemModel) -> Unit,
    onFavoriteClick: () -> Unit,
    viewModel: MovieHomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.observeAsState()

    Scaffold(
        topBar = {
            ToolbarContent(
                title = Strings.MOVIES,
                hasBack = false,
                onBackClick = onBack,
                rightIcon = AppIcons.Favorite,
                onRightClick = onFavoriteClick
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
                is MoviesUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                is MoviesUiState.Success -> {
                    val movies = (uiState as MoviesUiState.Success).movies
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(8.dp)
                    ) {
                        items(movies) { movie ->
                            MovieItem(
                                movie = movie,
                                modifier = Modifier.clickable { onMovieClick(movie) }
                            )
                        }
                    }
                }

                is MoviesUiState.Error -> {
                    Text(
                        text = (uiState as MoviesUiState.Error).message,
                        modifier = Modifier.align(Alignment.Center).padding(16.dp),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                else -> {}
            }
        }
    }
}