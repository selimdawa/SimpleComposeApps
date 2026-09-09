package com.flatcode.simplecomposeapps.news2.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.flatcode.simplecomposeapps.news2.viewmodel.NewsTopArticlesViewModel
import com.flatcode.simplecomposeapps.utils.Resource
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ERROR
import com.flatcode.simplecomposeapps.ui.theme.MC_TRACK

@Composable
fun TopArticlesScreen(
    viewModel: NewsTopArticlesViewModel = hiltViewModel()
) {
    val state by viewModel.topArticles.observeAsState(Resource.Loading())

    LaunchedEffect(Unit) {
        viewModel.getTopArticles("us")
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
                        TopArticleItem(article)
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

            else -> {}
        }
    }
}