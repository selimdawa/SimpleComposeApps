package com.flatcode.simplecomposeapps.crypto.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.flatcode.simplecomposeapps.crypto.CryptoHomeViewModel
import com.flatcode.simplecomposeapps.ui.ToolbarContent
import com.flatcode.simplecomposeapps.utils.DATA.COLOR_ERROR
import com.flatcode.simplecomposeapps.utils.DATA.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.utils.DATA.MC_TRACK
import com.flatcode.simplecomposeapps.utils.DATA

@Composable
fun CryptoHomeScreen(
    onCoinClick: (String, Int) -> Unit,
    viewModel: CryptoHomeViewModel = hiltViewModel()
) {
    val cryptoList by viewModel.cryptoList.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(true)
    val errorMessage by viewModel.error.observeAsState()

    val listState = rememberLazyListState()
    val shouldLoadMore by remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem != null && lastVisibleItem.index >= listState.layoutInfo.totalItemsCount - 5
        }
    }

    LaunchedEffect(Unit) {
        if (cryptoList.isEmpty()) {
            viewModel.getData(DATA.API_KEY_CRYPTO, DATA.LIMIT_CRYPTO)
        }
    }

    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore && !isLoading && cryptoList.isNotEmpty()) {
            viewModel.loadNextPage(DATA.API_KEY_CRYPTO)
        }
    }

    Scaffold(
        topBar = {
            ToolbarContent(
                title = DATA.CRYPTO, hasBack = false
            )
        }, containerColor = COLOR_ON_BACKGROUND
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (errorMessage != null && cryptoList.isEmpty()) {
                Text(
                    text = errorMessage!!,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(24.dp),
                    color = COLOR_ERROR,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            } else {
                LazyColumn(
                    state = listState, modifier = Modifier.fillMaxSize()
                ) {
                    items(cryptoList) { coin ->
                        CryptoItem(
                            item = coin, onClick = {
                                val symbol = coin.symbol ?: ""
                                val id = coin.id ?: 0
                                onCoinClick(symbol, id)
                            })
                    }

                    if (isLoading && cryptoList.isNotEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(color = MC_TRACK)
                            }
                        }
                    }
                }
            }

            if (isLoading && cryptoList.isEmpty()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center), color = MC_TRACK
                )
            }
        }
    }
}