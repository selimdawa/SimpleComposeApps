package com.flatcode.simplecomposeapps.multipledelete.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flatcode.simplecomposeapps.ui.AppIcons
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.ui.theme.rememberAttributeColor
import io.selimdawa.multicolors.MultiColorManager

@Composable
fun MultiDeleteTopAppBar(
    isSelectionMode: Boolean,
    selectedCount: Int,
    onBack: () -> Unit,
    onDelete: () -> Unit,
    onSelectAll: () -> Unit,
    onClearSelection: () -> Unit
) {
    val themeId by MultiColorManager.currentThemeId.collectAsState()
    val colorOnBackground = rememberAttributeColor("colorOnBackground", Color.White, themeId)
    val colorError = rememberAttributeColor("colorError", Color.Red, themeId)

    Surface(
        color = colorOnBackground, modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Navigation Icon
            IconButton(
                onClick = if (isSelectionMode) onClearSelection else onBack,
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = if (isSelectionMode) AppIcons.Close else AppIcons.Back,
                    contentDescription = "Back",
                    tint = colorError,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Title
            Text(
                text = if (isSelectionMode) Strings.selectedPlaceholder(selectedCount) else "Multi Delete",
                color = colorError,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )

            // Actions
            if (isSelectionMode) {
                IconButton(
                    onClick = onDelete, modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = AppIcons.Delete,
                        contentDescription = "Delete",
                        tint = colorError,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(
                    onClick = onSelectAll, modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = AppIcons.SelectAll,
                        contentDescription = "Select All",
                        tint = colorError,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}
