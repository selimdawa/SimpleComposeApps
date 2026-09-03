package com.flatcode.simplecomposeapps.rickAndMorty.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flatcode.simplecomposeapps.rickAndMorty.data.models.Episode
import com.flatcode.simplecomposeapps.ui.theme.SimpleComposeAppsTheme
import com.flatcode.simplecomposeapps.ui.theme.rememberAttributeColor
import io.selimdawa.multicolors.MultiColorManager

@Composable
fun EpisodeItem(
    item: Episode,
    modifier: Modifier = Modifier
) {
    val themeId by MultiColorManager.currentThemeId.collectAsState()
    val mcBg = rememberAttributeColor("mc_bg", Color.DarkGray, themeId)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp, vertical = 5.dp),
        shape = RoundedCornerShape(6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(mcBg)
        ) {
            Text(
                text = item.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 10.dp),
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Text(
                text = item.airDate,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                color = Color.White,
                fontSize = 18.sp,
                textAlign = TextAlign.End
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 10.dp),
                thickness = 1.dp,
                color = Color.White
            )
            Text(
                text = item.episode,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                color = Color.White,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
            Text(
                text = item.created,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 10.dp),
                color = Color.White,
                textAlign = TextAlign.End
            )
        }
    }
}

@Preview
@Composable
fun EpisodeItemPreview() {
    SimpleComposeAppsTheme {
        EpisodeItem(
            item = Episode(
                id = 1,
                name = "Pilot",
                airDate = "December 2, 2013",
                episode = "S01E01",
                characters = emptyList(),
                url = "",
                created = "2017-11-10T12:56:33.798Z"
            )
        )
    }
}
