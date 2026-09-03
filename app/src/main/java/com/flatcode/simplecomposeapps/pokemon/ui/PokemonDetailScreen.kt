package com.flatcode.simplecomposeapps.pokemon.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.flatcode.simplecomposeapps.pokemon.PokemonDetailsViewModel
import com.flatcode.simplecomposeapps.ui.theme.rememberAttributeColor
import com.flatcode.simplecomposeapps.utils.DATA
import io.selimdawa.multicolors.MultiColorManager

@Composable
fun PokemonDetailScreen(
    pokeId: Int,
    onBack: () -> Unit,
    viewModel: PokemonDetailsViewModel = hiltViewModel()
) {
    val details by viewModel.details.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(false)

    LaunchedEffect(pokeId) {
        viewModel.getPokemonDetails(pokeId)
    }

    Scaffold(
        topBar = {
            PokemonTopAppBar(
                title = details?.name?.replaceFirstChar { it.uppercase() } ?: DATA.POKE,
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
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                details?.let { item ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1.2f)
                                .background(MaterialTheme.colorScheme.secondaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            AsyncImage(
                                model = "${DATA.RAW_URL_POKE}${item.id}.png",
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize().padding(16.dp),
                                contentScale = ContentScale.Fit
                            )
                        }

                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
                            ) {
                                PokeTypeBadge(type = item.type1)
                                item.type2?.let { PokeTypeBadge(type = it) }
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            PokeStatRow(label = "HP :", value = item.hp.toString())
                            PokeStatRow(label = "Attack :", value = item.attack.toString())
                            PokeStatRow(label = "Defense :", value = item.defense.toString())
                            PokeStatRow(label = "Special Attack :", value = item.specialAttack.toString())
                            PokeStatRow(label = "Special Defense :", value = item.specialDefense.toString())
                            PokeStatRow(label = "Speed :", value = item.speed.toString())
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            PokeStatRow(label = "Height :", value = "${item.height / 10.0} m")
                            PokeStatRow(label = "Weight :", value = "${item.weight / 10.0} kg")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PokeTypeBadge(type: String) {
    val color = when (type.lowercase()) {
        "fire" -> Color(0xFFF08030)
        "water" -> Color(0xFF6890F0)
        "grass" -> Color(0xFF78C850)
        "electric" -> Color(0xFFF8D030)
        "ice" -> Color(0xFF98D8D8)
        "fighting" -> Color(0xFFC03028)
        "poison" -> Color(0xFFA040A0)
        "ground" -> Color(0xFFE0C068)
        "flying" -> Color(0xFFA890F0)
        "psychic" -> Color(0xFFF85888)
        "bug" -> Color(0xFFA8B820)
        "rock" -> Color(0xFFB8A038)
        "ghost" -> Color(0xFF705898)
        "dragon" -> Color(0xFF7038F8)
        "dark" -> Color(0xFF705848)
        "steel" -> Color(0xFFB8B8D0)
        "fairy" -> Color(0xFFEE99AC)
        else -> Color(0xFFA8A878)
    }

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = color),
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Text(
            text = type.replaceFirstChar { it.uppercase() },
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}

@Composable
fun PokeStatRow(label: String, value: String) {
    val themeId by MultiColorManager.currentThemeId.collectAsState()
    val mcTrack = rememberAttributeColor("mc_track", Color.White, themeId)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            modifier = Modifier.width(150.dp),
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            color = mcTrack,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}