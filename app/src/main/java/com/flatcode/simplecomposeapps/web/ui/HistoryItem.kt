package com.flatcode.simplecomposeapps.web.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.ConfigurationCompat
import com.flatcode.simplecomposeapps.ui.theme.AppIcons
import com.flatcode.simplecomposeapps.utils.DATA.MC_BG
import com.flatcode.simplecomposeapps.ui.theme.White
import com.flatcode.simplecomposeapps.web.data.WebEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@SuppressLint("ComposeNonObservableLocale")
@Composable
fun HistoryItem(
    item: WebEntity, onClick: () -> Unit, onDelete: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val locale = remember(configuration) {
        ConfigurationCompat.getLocales(configuration)[0] ?: Locale.getDefault()
    }
    val sdf = remember(locale) { SimpleDateFormat("MMM dd, HH:mm", locale) }
    val date = remember(item.timestamp, sdf) { sdf.format(Date(item.timestamp)) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(0.dp),
        onClick = onClick
    ) {
        Box(modifier = Modifier.fillMaxWidth().background(MC_BG)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = item.title,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 16.sp,
                        color = White
                    )
                    Text(
                        text = item.url,
                        fontSize = 12.sp,
                        color = White.copy(alpha = 0.7f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = date, fontSize = 10.sp, color = White.copy(alpha = 0.5f)
                    )
                }
                IconButton(onClick = onDelete) {
                    Icon(AppIcons.DeleteCal, contentDescription = "Remove", tint = White)
                }
            }
        }
    }
}