package com.flatcode.simplecomposeapps.pokemon.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import com.flatcode.simplecomposeapps.pokemon.viewmodel.PokemonDetailsViewModel
import com.flatcode.simplecomposeapps.ui.LoadingAnimation
import com.flatcode.simplecomposeapps.ui.ToolbarContent
import com.flatcode.simplecomposeapps.utils.DATA.COLOR_ERROR
import com.flatcode.simplecomposeapps.utils.DATA.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.utils.DATA.MC_TICK
import com.flatcode.simplecomposeapps.utils.DATA.MC_TRACK
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.utils.DATA
import com.flatcode.simplecomposeapps.utils.Resource

@Composable
fun PokemonDetailScreen(
    pokeId: Int, onBack: () -> Unit, viewModel: PokemonDetailsViewModel = hiltViewModel()
) {
    val detailsResult by viewModel.details.observeAsState(Resource.Idle)

    LaunchedEffect(pokeId) {
        viewModel.getPokemonDetails(pokeId)
    }

    Scaffold(
        topBar = {
            ToolbarContent(title = detailsResult.data?.name?.replaceFirstChar { it.uppercase() }
                ?: DATA.POKE, hasBack = true, onBackClick = onBack)
        }, containerColor = COLOR_ON_BACKGROUND
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                detailsResult.data != null -> {
                    detailsResult.data?.let { item ->
                        val errorColor = COLOR_ERROR
                        val onBgColor = COLOR_ON_BACKGROUND

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(320.dp)
                                    .background(onBgColor), contentAlignment = Alignment.Center
                            ) {
                                var isImageLoading by remember { mutableStateOf(true) }
                                AsyncImage(
                                    model = "${DATA.RAW_URL_POKE}${item.id}.png",
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(280.dp)
                                        .padding(16.dp),
                                    contentScale = ContentScale.Fit,
                                    onState = { state ->
                                        isImageLoading = state is AsyncImagePainter.State.Loading
                                    })
                                if (isImageLoading) {
                                    LoadingAnimation(modifier = Modifier.size(80.dp))
                                }
                            }

                            Column(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                            ) {
                                Text(
                                    text = Strings.TYPE,
                                    color = errorColor,
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(vertical = 5.dp)
                                )

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 5.dp),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    PokeTypeBadge(type = item.type1)
                                    item.type2?.let { PokeTypeBadge(type = it) }
                                }

                                Spacer(modifier = Modifier.height(5.dp))

                                PokeStatRow(
                                    label = Strings.HP,
                                    value = item.hp.toString(),
                                    color = errorColor
                                )
                                PokeStatRow(
                                    label = Strings.SPEED,
                                    value = item.speed.toString(),
                                    color = errorColor
                                )
                                PokeStatRow(
                                    label = Strings.ATTACK,
                                    value = item.attack.toString(),
                                    color = errorColor
                                )
                                PokeStatRow(
                                    label = Strings.DEFENSE,
                                    value = item.defense.toString(),
                                    color = errorColor
                                )
                                PokeStatRow(
                                    label = Strings.SPECIAL_ATTACK,
                                    value = item.specialAttack.toString(),
                                    color = errorColor
                                )
                                PokeStatRow(
                                    label = Strings.SPECIAL_DEFENSE,
                                    value = item.specialDefense.toString(),
                                    color = errorColor
                                )
                                PokeStatRow(
                                    label = Strings.HEIGHT,
                                    value = "${item.height.toDouble() / 10.0} m",
                                    color = errorColor
                                )
                                PokeStatRow(
                                    label = Strings.WEIGHT,
                                    value = "${item.weight.toDouble() / 10.0} kg",
                                    color = errorColor
                                )
                            }
                        }
                    }
                }

                detailsResult is Resource.Loading -> {
                    LoadingAnimation(
                        modifier = Modifier
                            .size(80.dp)
                            .align(Alignment.Center)
                    )
                }

                detailsResult is Resource.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = Strings.FAILED_LOAD_DATA,
                            color = COLOR_ERROR,
                            fontSize = 35.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(20.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PokeTypeBadge(type: String) {
    val typeGradient = Brush.verticalGradient(
        0.0f to MC_TICK, 0.2f to MC_TRACK, 1.0f to Color.Black
    )

    Box(
        modifier = Modifier
            .width(150.dp)
            .padding(vertical = 4.dp)
            .background(brush = typeGradient, shape = RoundedCornerShape(25.dp))
            .border(1.dp, COLOR_ERROR, RoundedCornerShape(25.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = type.replaceFirstChar { it.uppercase() },
            modifier = Modifier.padding(5.dp),
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
fun PokeStatRow(label: String, value: String, color: Color = COLOR_ERROR) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = label, color = color, fontSize = 22.sp, fontWeight = FontWeight.Bold
        )
        Text(
            text = value,
            modifier = Modifier.padding(start = 10.dp),
            color = color,
            fontSize = 22.sp
        )
    }
}