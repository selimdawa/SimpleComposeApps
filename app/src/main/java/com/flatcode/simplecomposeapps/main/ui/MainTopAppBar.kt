package com.flatcode.simplecomposeapps.main.ui

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
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.flatcode.simplecomposeapps.R
import io.selimdawa.multicolors.MultiColorButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(onInfoClick: () -> Unit) {
    val context = LocalContext.current
    val mcBgColor = rememberColorAttr(context, "mc_bg")

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
                text = stringResource(id = R.string.app_name),
                color = Color.White,
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold
            )
        }, navigationIcon = {
            AndroidView(
                factory = { ctx ->
                    MultiColorButton(ctx)
                }, modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .size(30.dp)
            )
        }, actions = {
            CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides 0.dp) {
                IconButton(
                    onClick = onInfoClick,
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .size(30.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_info),
                        contentDescription = "Info",
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = mcBgColor
        ), windowInsets = WindowInsets(0, 0, 0, 0), modifier = Modifier.height(45.dp)
        )
    }
}