package com.flatcode.simplecomposeapps.news.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.flatcode.simplecomposeapps.news.model.NewsHeadlines
import com.flatcode.simplecomposeapps.ui.theme.AppTheme
import com.flatcode.simplecomposeapps.ui.theme.rememberAttributeColor
import com.flatcode.simplecomposeapps.utils.DATA
import io.selimdawa.multicolors.MultiColorManager

@Composable
fun NewsItem(headline: NewsHeadlines, onClick: () -> Unit) {
    val themeId by MultiColorManager.currentThemeId.collectAsState()
    val mcBg = rememberAttributeColor("mc_bg", Color.DarkGray, themeId)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
            .padding(bottom = 10.dp)
            .clickable { onClick() }
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(6.dp),
            colors = CardDefaults.cardColors(containerColor = mcBg)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
            ) {
                AsyncImage(
                    model = headline.urlToImage ?: DATA.EMPTY,
                    contentDescription = null,
                    modifier = Modifier
                        .weight(0.5f)
                        .aspectRatio(2f / 1.4f),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(horizontal = 10.dp, vertical = 5.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = (headline.title ?: DATA.EMPTY).trim(),
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth(),
                        lineHeight = 18.sp
                    )

                    Text(
                        text = headline.source?.name ?: DATA.EMPTY,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}