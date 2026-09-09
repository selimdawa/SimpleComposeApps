package com.flatcode.simplecomposeapps.web.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ERROR
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.web.viewmodel.WebAppUiState
import com.flatcode.simplecomposeapps.web.viewmodel.WebAppViewModel

@Composable
fun WebHistoryScreen(
    viewModel: WebAppViewModel, onNavigateToUrl: (String) -> Unit
) {
    val uiState by viewModel.uiState.observeAsState(WebAppUiState())

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(COLOR_ON_BACKGROUND)
    ) {
        if (uiState.history.isEmpty()) {
            WebEmptyState(
                icon = Icons.Default.History, message = "No history yet"
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(uiState.history) { item ->
                    HistoryItem(
                        item = item,
                        onClick = { onNavigateToUrl(item.url) },
                        onDelete = { viewModel.deleteItem(item) })
                }
            }
        }
    }
}

@Composable
fun WebEmptyState(
    icon: ImageVector, message: String
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            tint = COLOR_ERROR.copy(alpha = 0.5f)
        )
        Text(
            text = message, style = MaterialTheme.typography.bodyLarge, color = COLOR_ERROR
        )
    }
}