package com.flatcode.simplecomposeapps.movies.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
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
import com.flatcode.simplecomposeapps.movies.MovieFavoriteViewModel
import com.flatcode.simplecomposeapps.movies.models.MovieItemModel
import com.flatcode.simplecomposeapps.ui.ToolbarContent
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ERROR
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.ui.theme.Strings

@Composable
fun MovieFavoriteScreen(
    onBack: () -> Unit,
    onMovieClick: (MovieItemModel) -> Unit,
    viewModel: MovieFavoriteViewModel = hiltViewModel()
) {
    val favorites by viewModel.allMovies.observeAsState(emptyList())

    Scaffold(
        topBar = {
            ToolbarContent(
                title = Strings.FAVORITE_MOVIES, hasBack = true, onBackClick = onBack
            )
        }, containerColor = COLOR_ON_BACKGROUND
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (favorites.isEmpty()) {
                Text(
                    text = Strings.NO_FAVORITES_YET,
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    color = COLOR_ERROR
                )
            } else {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 5.dp)
                ) {
                    items(favorites.asReversed()) { movie ->
                        MovieItem(
                            movie = movie, modifier = Modifier.clickable { onMovieClick(movie) })
                    }
                }
            }
        }
    }
}