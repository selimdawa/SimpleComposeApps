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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.flatcode.simplecomposeapps.crypto.CryptoHomeViewModel
import com.flatcode.simplecomposeapps.ui.ToolbarContent
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ERROR
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.utils.DATA

@Composable
fun CryptoHomeScreen(
    onBack: () -> Unit,
    onCoinClick: (String, Int) -> Unit,
    viewModel: CryptoHomeViewModel = hiltViewModel()
) {
    val cryptoList by viewModel.cryptoList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

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
                title = DATA.CRYPTO, hasBack = false, onBackClick = onBack
            )
        }, containerColor = COLOR_ON_BACKGROUND
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
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
                            CircularProgressIndicator(color = COLOR_ERROR)
                        }
                    }
                }
            }

            if (isLoading && cryptoList.isEmpty()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center), color = COLOR_ERROR
                )
            }
        }
    }
}