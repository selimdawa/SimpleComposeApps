package com.flatcode.simplecomposeapps.pokemon.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PokemonLoadingItem(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
    ) {
        PokemonItemShimmer(modifier = Modifier.weight(1f))
        PokemonItemShimmer(modifier = Modifier.weight(1f))
    }
}

@Preview(showBackground = true)
@Composable
fun PokemonLoadingItemPreview() {
    PokemonLoadingItem()
}