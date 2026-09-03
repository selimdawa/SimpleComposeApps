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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.flatcode.simplecomposeapps.rickAndMorty.RickCharactersViewModel
import com.flatcode.simplecomposeapps.rickAndMorty.utils.Resource
import com.flatcode.simplecomposeapps.ui.ToolbarContent

@Composable
fun RickCharactersScreen(
    onBack: () -> Unit,
    viewModel: RickCharactersViewModel = hiltViewModel()
) {
    val state by viewModel.characters.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getCharacters()
    }

    Scaffold(
        topBar = {
            ToolbarContent(title = "Characters", hasBack = false, onBackClick = onBack)
        },
        containerColor = MaterialTheme.colorScheme.background
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
                    val characters = state.data?.results ?: emptyList()
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(characters) { character ->
                            CharacterItem(item = character)
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