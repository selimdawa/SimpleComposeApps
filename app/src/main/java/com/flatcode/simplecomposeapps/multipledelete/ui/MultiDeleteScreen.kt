package com.flatcode.simplecomposeapps.multipledelete.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flatcode.simplecomposeapps.multipledelete.MultiDeleteViewModel
import com.flatcode.simplecomposeapps.ui.theme.AppIcons
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ERROR
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.ui.theme.MC_TRACK
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.utils.DATA

@Composable
fun MultiDeleteScreen(viewModel: MultiDeleteViewModel) {
    val items = viewModel.items
    val selectedItems = viewModel.selectedItems
    val isSelectionMode by viewModel.isSelectionMode
    val isLoading by viewModel.isLoading
    val context = LocalContext.current

    Scaffold(
        topBar = {
            if (isSelectionMode) {
                MultiDeleteTopAppBar(
                    isSelectionMode = isSelectionMode,
                    selectedCount = selectedItems.size,
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
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.Center), color = MC_TRACK
                )
            } else if (items.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = Strings.NO_DATA_FOUND,
                        color = COLOR_ERROR,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    IconButton(
                        onClick = { viewModel.restoreItems(DATA.MULTI_DELETE_VALUES) },
                        modifier = Modifier.size(60.dp)
                    ) {
                        Icon(
                            imageVector = AppIcons.Sync,
                            contentDescription = Strings.RESTORE_ITEMS,
                            tint = COLOR_ERROR,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
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