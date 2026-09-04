package com.flatcode.simplecomposeapps.crypto.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.flatcode.simplecomposeapps.crypto.CryptoDetailViewModel
import com.flatcode.simplecomposeapps.ui.ToolbarContent
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.ui.theme.MC_TRACK
import com.flatcode.simplecomposeapps.utils.DATA

@Composable
fun CryptoDetailScreen(
    symbol: String,
    coinId: Int,
    onBack: () -> Unit,
    viewModel: CryptoDetailViewModel = hiltViewModel()
) {
    val coinDetail by viewModel.cryptoDetail.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(coinId) {
        viewModel.getCryptoDetail(DATA.API_KEY_CRYPTO, coinId)
    }

    Scaffold(
        topBar = {
            ToolbarContent(
                title = DATA.CRYPTO_DETAILS,
                hasBack = true,
                onBackClick = onBack
            )
        },
        containerColor = COLOR_ON_BACKGROUND
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MC_TRACK
                )
            } else {
                coinDetail?.let { detail ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val logoUrl = detail.logo?.replace("64x64", "200x200") ?: detail.logo
                        AsyncImage(
                            model = logoUrl,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(top = 30.dp)
                                .size(120.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Fit
                        )

                        Text(
                            text = detail.name ?: "",
                            modifier = Modifier.padding(top = 24.dp),
                            color = MC_TRACK,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = detail.symbol ?: "",
                            modifier = Modifier.padding(top = 10.dp),
                            color = MC_TRACK,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = detail.description ?: "",
                            modifier = Modifier
                                .padding(top = 20.dp)
                                .padding(horizontal = 10.dp),
                            color = MC_TRACK,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}