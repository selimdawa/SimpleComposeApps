package com.flatcode.simplecomposeapps.news2.ui

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
import com.flatcode.simplecomposeapps.news2.NewsEverythingViewModel
import com.flatcode.simplecomposeapps.news2.common.Resource
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.utils.DATA

@Composable
fun EverythingScreen(
    onBack: () -> Unit,
    viewModel: NewsEverythingViewModel = hiltViewModel()
) {
    val state by viewModel.everything.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getEverything("android")
    }

    Scaffold(
        topBar = {
            NewsTopAppBar(title = Strings.EVERYTHING, onBack = onBack)
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
                    val articles = state.data?.articles ?: emptyList()
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(articles) { article ->
                            EverythingNewsItem(item = article)
                        }
                    }
                }

                is Resource.Error -> {
                    Text(
                        text = state.message ?: "Error",
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}