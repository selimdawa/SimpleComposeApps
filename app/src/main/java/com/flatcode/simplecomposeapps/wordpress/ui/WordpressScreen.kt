package com.flatcode.simplecomposeapps.wordpress.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flatcode.simplecomposeapps.ui.ToolbarContentFav
import com.flatcode.simplecomposeapps.utils.DATA.COLOR_ERROR
import com.flatcode.simplecomposeapps.utils.DATA.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.utils.DATA.MC_TRACK
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.utils.DATA
import com.flatcode.simplecomposeapps.wordpress.viewmodel.WordpressUiState
import com.flatcode.simplecomposeapps.wordpress.viewmodel.WordpressViewModel

@Composable
fun WordpressScreen(
    viewModel: WordpressViewModel, onPostClick: (Int) -> Unit, onFavoritesClick: () -> Unit
) {
    val uiState by viewModel.uiState.observeAsState(WordpressUiState())

    Scaffold(
        topBar = {
            ToolbarContentFav(
                title = DATA.WORDPRESS, onFavoriteClick = onFavoritesClick
            )
        }, containerColor = COLOR_ON_BACKGROUND
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center), color = MC_TRACK
                )
            } else if (uiState.posts.isEmpty()) {
                Text(
                    text = Strings.NONE_DISPLAY,
                    color = COLOR_ERROR,
                    fontSize = 32.sp,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = 20.dp),
                    textAlign = TextAlign.Center
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(bottom = 10.dp)
                ) {
                    itemsIndexed(uiState.posts) { index, post ->
                        WordpressItem(post = post, onClick = { onPostClick(index) })
                    }
                }
            }
        }
    }
}