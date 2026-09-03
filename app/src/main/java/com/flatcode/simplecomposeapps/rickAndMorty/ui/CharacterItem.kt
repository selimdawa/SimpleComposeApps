package com.flatcode.simplecomposeapps.rickAndMorty.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.flatcode.simplecomposeapps.rickAndMorty.data.models.Character
import com.flatcode.simplecomposeapps.rickAndMorty.data.models.LocationShort
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.ui.theme.SimpleComposeAppsTheme
import com.flatcode.simplecomposeapps.ui.theme.ImageProfile
import com.flatcode.simplecomposeapps.ui.theme.rememberAttributeColor
import io.selimdawa.multicolors.MultiColorManager

@Composable
fun CharacterItem(
    item: Character,
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(mcBg)
        ) {
            Box(
                modifier = Modifier
                    .width(120.dp)
                    .fillMaxHeight()
                    .background(ImageProfile)
            ) {
                AsyncImage(
                    model = item.image,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .blur(10.dp),
                    contentScale = ContentScale.Crop
                )
                AsyncImage(
                    model = item.image,
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                        .align(Alignment.Center),
                    contentScale = ContentScale.Fit
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = item.name,
                    color = Color.White,
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 10.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    val indicatorColor = when (item.status.lowercase()) {
                        "alive" -> Color.Green
                        "dead" -> Color.Red
                        else -> Color.Gray
                    }
                    Box(
                        modifier = Modifier
                            .size(15.dp)
                            .clip(CircleShape)
                            .background(indicatorColor)
                    )
                    Text(
                        text = item.status,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                    Text(
                        text = item.species,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 10.dp),
                    thickness = 1.dp,
                    color = Color.White
                )

                Text(
                    text = Strings.LAST_KNOW_LOCATION,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = item.location.name,
                    color = Color.White,
                    fontSize = 16.sp
                )

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 10.dp),
                    thickness = 1.dp,
                    color = Color.White
                )

                Text(
                    text = Strings.GENDER,
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = item.gender,
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun CharacterItemPreview() {
    SimpleComposeAppsTheme {
        CharacterItem(
            item = Character(
                id = 1,
                name = "Rick Sanchez",
                status = "Alive",
                species = "Human",
                type = "",
                gender = "Male",
                origin = LocationShort("Earth", ""),
                location = LocationShort("Citadel of Ricks", ""),
                image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
                episode = emptyList(),
                url = "",
                created = ""
            )
        )
    }
}
