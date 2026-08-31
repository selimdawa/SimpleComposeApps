package com.flatcode.simplecomposeapps.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flatcode.simplecomposeapps.ui.AppIcons
import com.flatcode.simplecomposeapps.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonTopAppBar(
    title: String,
    onBack: (() -> Unit)? = null
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
                    text = title,
                    color = Color.White,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                if (onBack != null) {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = AppIcons.Back,
                            contentDescription = "Back",
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
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