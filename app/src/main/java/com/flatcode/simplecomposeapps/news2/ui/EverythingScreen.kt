package com.flatcode.simplecomposeapps.news2.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.flatcode.simplecomposeapps.news2.viewmodel.NewsEverythingViewModel
import com.flatcode.simplecomposeapps.news2.common.Resource
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ERROR
import com.flatcode.simplecomposeapps.ui.theme.MC_TRACK

@Composable
fun EverythingScreen(
    viewModel: NewsEverythingViewModel = hiltViewModel()
) {
    val state by viewModel.everything.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getEverything("android")
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when (state) {
            is Resource.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MC_TRACK
                )
            }

            is Resource.Success -> {
                val articles = state.data?.articles ?: emptyList()
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(articles) { article ->
                        EverythingNewsItem(article)
                    }
                }
            }

            is Resource.Error -> {
                Text(
                    text = state.message ?: "Error",
                    modifier = Modifier.align(Alignment.Center),
                    color = COLOR_ERROR,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}