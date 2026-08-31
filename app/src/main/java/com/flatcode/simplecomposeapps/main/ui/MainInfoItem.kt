package com.flatcode.simplecomposeapps.main.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flatcode.simplecomposeapps.main.MainInfo
import com.flatcode.simplecomposeapps.ui.AppIcons
import com.flatcode.simplecomposeapps.ui.theme.AppTheme
import com.flatcode.simplecomposeapps.ui.theme.Strings

@Composable
fun MainInfoItem(item: MainInfo) {
    val mcTrackColor = AppTheme.colors.track

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
            .padding(bottom = 10.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = Strings.NAME,
                    color = mcTrackColor,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = item.title ?: "",
                    color = mcTrackColor,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            FeatureStatusRow(
                label = Strings.DAGGER_HILT,
                status = item.hilt == 1,
                color = mcTrackColor
            )
            FeatureStatusRow(
                label = Strings.NAVIGATION,
                status = item.navigation == 1,
                color = mcTrackColor
            )
            FeatureStatusRow(
                label = Strings.ROOM,
                status = item.room == 1,
                color = mcTrackColor
            )
            FeatureStatusRow(
                label = Strings.COROUTINES,
                status = item.coroutines == 1,
                color = mcTrackColor
            )
        }
    }
}

@Composable
fun FeatureStatusRow(label: String, status: Boolean, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label, color = color, fontSize = 18.sp, fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(10.dp))
        Image(
            imageVector = if (status) AppIcons.CircleGreen else AppIcons.CircleRed,
            contentDescription = null,
            modifier = Modifier.size(25.dp)
        )
    }
}
