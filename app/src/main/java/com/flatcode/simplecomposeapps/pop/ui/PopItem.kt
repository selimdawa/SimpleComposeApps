package com.flatcode.simplecomposeapps.pop.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.flatcode.simplecomposeapps.pop.model.PopItem
import com.flatcode.simplecomposeapps.ui.AppIcons
import com.flatcode.simplecomposeapps.ui.theme.MC_BG
import com.flatcode.simplecomposeapps.ui.theme.MC_TRACK
import com.flatcode.simplecomposeapps.utils.DATA

@Composable
fun PopItem(
    item: PopItem,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 5.dp, end = 5.dp, bottom = 10.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MC_TRACK)
        ) {
            AsyncImage(
                model = item.img,
                contentDescription = item.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .background(Color.White),
                contentScale = ContentScale.Fit,
                error = rememberVectorPainter(image = AppIcons.Load),
                placeholder = rememberVectorPainter(image = AppIcons.Load)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MC_BG)
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = item.name,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                if (item.series != DATA.UNKNOWN) {
                    Text(
                        text = item.series,
                        color = Color.LightGray,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}