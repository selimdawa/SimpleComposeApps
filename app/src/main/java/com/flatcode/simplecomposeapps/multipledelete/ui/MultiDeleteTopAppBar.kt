package com.flatcode.simplecomposeapps.multipledelete.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flatcode.simplecomposeapps.ui.AppIcons
import com.flatcode.simplecomposeapps.ui.theme.AppTheme
import com.flatcode.simplecomposeapps.ui.theme.Strings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MultiDeleteTopAppBar(
    isSelectionMode: Boolean,
    selectedCount: Int,
    onBack: () -> Unit,
    onDelete: () -> Unit,
    onSelectAll: () -> Unit,
    onClearSelection: () -> Unit
) {
    val mcBgColor = AppTheme.colors.background

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(10.dp),
        shape = RoundedCornerShape(6.dp),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = if (isSelectionMode) Strings.selectedPlaceholder(selectedCount) else "Multi Delete",
                    color = Color.White,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(onClick = if (isSelectionMode) onClearSelection else onBack) {
                    Icon(
                        imageVector = if (isSelectionMode) AppIcons.Close else AppIcons.Back,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }
            },
            actions = {
                if (isSelectionMode) {
                    Row {
                        IconButton(onClick = onSelectAll) {
                            Icon(
                                imageVector = AppIcons.SelectAll,
                                contentDescription = "Select All",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        IconButton(onClick = onDelete) {
                            Icon(
                                imageVector = AppIcons.Delete,
                                contentDescription = "Delete",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = mcBgColor
            ),
            windowInsets = WindowInsets(0, 0, 0, 0),
            modifier = Modifier.height(45.dp)
        )
    }
}
