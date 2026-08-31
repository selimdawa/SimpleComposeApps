package com.flatcode.simplecomposeapps.multipledelete.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MultiDeleteItem(
    text: String,
    isSelected: Boolean,
    isSelectionMode: Boolean,
    onLongClick: () -> Unit,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) Color.LightGray else Color.Transparent

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isSelectionMode) {
            Checkbox(
                checked = isSelected,
                onCheckedChange = null,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(24.dp),
                colors = CheckboxDefaults.colors(
                    checkedColor = Color.DarkGray,
                    uncheckedColor = Color.Gray
                )
            )
        }
        Text(
            text = text,
            fontSize = 18.sp,
            color = Color.Black
        )
    }
}
