package com.flatcode.simplecomposeapps.pokemon.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
        LoadingCard(modifier = Modifier.weight(1f))
        LoadingCard(modifier = Modifier.weight(1f))
    }
}

@Composable
private fun LoadingCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(horizontal = 5.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black)
    ) {
        Card(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(6.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Image placeholder
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )

                // ID placeholder
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(width = 48.dp, height = 16.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )

                // Name placeholder
                Box(
                    modifier = Modifier
                        .padding(start = 8.dp, bottom = 8.dp)
                        .size(width = 80.dp, height = 24.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PokemonLoadingItemPreview() {
    PokemonLoadingItem()
}
