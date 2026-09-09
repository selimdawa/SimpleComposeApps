package com.flatcode.simplecomposeapps.pokemon.ui

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
import com.flatcode.simplecomposeapps.pokemon.viewmodel.PokeViewModel
import com.flatcode.simplecomposeapps.ui.ToolbarContent
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ERROR
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.utils.DATA
import com.flatcode.simplecomposeapps.utils.Resource

@Composable
fun PokemonScreen(
    onPokemonClick: (Int) -> Unit, viewModel: PokeViewModel = hiltViewModel()
) {
    val pokemonList by viewModel.pokemon.observeAsState(emptyList())
    val status by viewModel.status.observeAsState(Resource.Idle)

    Scaffold(
        topBar = {
            ToolbarContent(
                title = DATA.POKE, hasBack = false
            )
        }, containerColor = COLOR_ON_BACKGROUND
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (status is Resource.Loading && pokemonList.isEmpty()) {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(start = 5.dp, end = 5.dp)
                ) {
                    items(6) {
                        PokemonItemShimmer()
                    }
                }
            } else if (pokemonList.isEmpty()) {
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
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(start = 5.dp, end = 5.dp)
                ) {
                    items(pokemonList) { pokemon ->
                        PokemonItem(
                            pokemon = pokemon,
                            modifier = Modifier.clickable { onPokemonClick(pokemon.id) })
                    }
                }
            }
        }
    }
}