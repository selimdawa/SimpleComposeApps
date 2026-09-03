package com.flatcode.simplecomposeapps.multipledelete.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flatcode.simplecomposeapps.multipledelete.MultiDeleteViewModel
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ERROR
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.ui.theme.Strings

@Composable
fun MultiDeleteScreen(viewModel: MultiDeleteViewModel, onBack: () -> Unit) {
    val items = viewModel.items
    val selectedItems = viewModel.selectedItems
    val isSelectionMode by viewModel.isSelectionMode
    val context = LocalContext.current

    Scaffold(
        topBar = {
            if (isSelectionMode) {
                MultiDeleteTopAppBar(
                    isSelectionMode = isSelectionMode,
                    selectedCount = selectedItems.size,
                    onBack = onBack,
                    onDelete = { viewModel.deleteSelected() },
                    onSelectAll = { viewModel.selectAll() },
                    onClearSelection = { viewModel.exitSelectionMode() })
            }
        }, containerColor = COLOR_ON_BACKGROUND
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (items.isEmpty()) {
                Text(
                    text = Strings.NO_DATA_FOUND,
                    color = COLOR_ERROR,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = 20.dp),
                    textAlign = TextAlign.Center
                )
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(items) { item ->
                        MultiDeleteItem(
                            text = item,
                            isSelected = selectedItems.contains(item),
                            isSelectionMode = isSelectionMode,
                            onLongClick = {
                                if (!isSelectionMode) {
                                    viewModel.enterSelectionMode(item)
                                }
                            },
                            onClick = {
                                if (isSelectionMode) {
                                    viewModel.toggleSelection(item)
                                } else {
                                    Toast.makeText(
                                        context,
                                        Strings.youClickedPlaceholder(item),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            })
                    }
                }
            }
        }
    }
}
