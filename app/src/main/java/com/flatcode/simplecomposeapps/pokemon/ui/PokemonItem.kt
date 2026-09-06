package com.flatcode.simplecomposeapps.pokemon.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.flatcode.simplecomposeapps.R
import com.flatcode.simplecomposeapps.pokemon.domain.model.PokeItem
import com.flatcode.simplecomposeapps.ui.LoadingAnimation
import com.flatcode.simplecomposeapps.ui.theme.Dark
import com.flatcode.simplecomposeapps.ui.theme.MC_BG
import com.flatcode.simplecomposeapps.ui.theme.White
import com.flatcode.simplecomposeapps.ui.theme.image_profile
import com.valentinilk.shimmer.shimmer

@Composable
fun PokemonItem(
    pokemon: PokeItem, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
            .padding(bottom = 10.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Dark)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(6.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                SubcomposeAsyncImage(
                    model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${pokemon.id}.png",
                    contentDescription = "Pokemon image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(image_profile),
                    contentScale = ContentScale.Fit,
                    loading = {
                        Box(
                            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                        ) {
                            LoadingAnimation(modifier = Modifier.size(80.dp))
                        }
                    })

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MC_BG)
                ) {
                    Text(
                        text = pokemon.formatId,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        color = White,
                        fontSize = 18.sp
                    )

                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = White
                    )

                    Text(
                        text = pokemon.name.replaceFirstChar { it.uppercase() },
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        color = White,
                        fontSize = 21.sp
                    )
                }
            }
        }
    }
}


@Composable
fun PokemonItemShimmer(modifier: Modifier = Modifier) {
    val shimmerColor = Color(0xFFC8C8C9)
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
            .padding(bottom = 10.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(6.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .shimmer()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(shimmerColor)
                )
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(width = 32.dp, height = 16.dp)
                        .background(shimmerColor)
                )
                Box(
                    modifier = Modifier
                        .padding(start = 8.dp, bottom = 8.dp)
                        .size(width = 100.dp, height = 24.dp)
                        .background(shimmerColor)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PokemonItemPreview() {
    PokemonItem(
        pokemon = PokeItem(
            id = 1, name = "bulbasaur", url = "https://pokeapi.co/api/v2/pokemon/1/"
        )
    )
}