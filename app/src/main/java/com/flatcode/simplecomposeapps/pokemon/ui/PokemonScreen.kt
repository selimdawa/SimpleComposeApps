package com.flatcode.simplecomposeapps.pokemon.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.MaterialTheme
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.flatcode.simplecomposeapps.pokemon.PokeViewModel
import com.flatcode.simplecomposeapps.pokemon.data.model.PokeResult
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.utils.DATA

@Composable
fun PokemonScreen(
    onBack: () -> Unit,
    onPokemonClick: (Int) -> Unit,
    viewModel: PokeViewModel = hiltViewModel()
) {
    val pokemonList by viewModel.pokemon.observeAsState(emptyList())

    Scaffold(
        topBar = {
            PokemonTopAppBar(
                title = DATA.POKE,
                onBack = onBack
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (pokemonList.isEmpty()) {
                // Showing loading or empty state
                // The original had a shimmer, but for now simple text or progress
                Text(
                    text = Strings.NONE_DISPLAY,
                    modifier = Modifier.align(Alignment.Center).padding(16.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.error
                )
            } else {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(pokemonList) { pokemon ->
                        PokemonItem(
                            pokemon = PokeResult(pokemon.name, pokemon.url),
                            modifier = Modifier.clickable { onPokemonClick(pokemon.id) }
                        )
                    }
                }
            }
        }
    }
}