package com.flatcode.simplecomposeapps.main.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flatcode.simplecomposeapps.R
import com.flatcode.simplecomposeapps.main.MainViewModel

@Composable
fun MainScreen(
    viewModel: MainViewModel, onInfoClick: () -> Unit
) {
    val items by viewModel.dataMain.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(true)

    Scaffold(
        topBar = {
            MainTopAppBar(onInfoClick = onInfoClick)
        }, containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary
                )
            } else if (items.isEmpty()) {
                Text(
                    text = stringResource(id = R.string.none_display),
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 32.sp,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = 20.dp),
                    textAlign = TextAlign.Center
                )
            } else {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(
                        bottom = 10.dp,
                        start = 5.dp,
                        end = 5.dp
                    )
                ) {
                    items(items) { item ->
                        MainItem(item = item)
                    }
                }
            }
        }
    }
}