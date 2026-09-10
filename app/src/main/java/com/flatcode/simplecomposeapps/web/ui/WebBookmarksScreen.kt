package com.flatcode.simplecomposeapps.web.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.flatcode.simplecomposeapps.utils.DATA.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.web.viewmodel.WebAppUiState
import com.flatcode.simplecomposeapps.web.viewmodel.WebAppViewModel

@Composable
fun WebBookmarksScreen(
    viewModel: WebAppViewModel, onNavigateToUrl: (String) -> Unit
) {
    val uiState by viewModel.uiState.observeAsState(WebAppUiState())

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(COLOR_ON_BACKGROUND)
    ) {
        if (uiState.bookmarks.isEmpty()) {
            WebEmptyState(
                icon = Icons.Default.Bookmark, message = "No bookmarks yet"
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(uiState.bookmarks) { item ->
                    BookmarkItem(
                        item = item,
                        onClick = { onNavigateToUrl(item.url) },
                        onDelete = { viewModel.deleteItem(item) })
                }
            }
        }
    }
}