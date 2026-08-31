package com.flatcode.simplecomposeapps.randomimagegenerating.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.flatcode.simplecomposeapps.ui.AppIcons
import com.flatcode.simplecomposeapps.randomimagegenerating.ImageInfoViewModel
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.ui.theme.rememberAttributeColor
import io.selimdawa.multicolors.MultiColorManager

@Composable
fun ImageInfoContent(
    modifier: Modifier = Modifier,
    viewModel: ImageInfoViewModel,
    onOpenUrl: (String) -> Unit
) {
    val themeId by MultiColorManager.currentThemeId.collectAsState()
    val mcTick = rememberAttributeColor("mc_tick", Color.Gray, themeId)
    val catInfo by viewModel.catInfo

    Box(modifier = modifier) {
        // Background
        Image(
            painter = painterResource(id = AppIcons.Blur),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        catInfo?.let { info ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    AsyncImage(
                        model = info.imageUrl.ifEmpty { AppIcons.HelloKitty },
                        contentDescription = "Cat Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f),
                        contentScale = ContentScale.Crop
                    )
                }

                Text(
                    text = info.name,
                    color = Color.White,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 10.dp),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = info.origin,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 10.dp),
                    textAlign = TextAlign.Center
                )

                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    color = Color.White,
                    thickness = 1.dp
                )

                Text(
                    text = info.description,
                    color = Color.White,
                    modifier = Modifier.padding(top = 10.dp),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = Strings.TEMPERAMENT,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 10.dp),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = info.temperament,
                    color = Color.White,
                    modifier = Modifier.padding(top = 10.dp),
                    textAlign = TextAlign.Center
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = { onOpenUrl(info.wikiUrl) },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = mcTick),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(text = Strings.WIKIPEDIA, color = Color.White)
                    }

                    Button(
                        onClick = { onOpenUrl(info.moreLink) },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = mcTick),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(text = Strings.MORE_INFO, color = Color.White)
                    }
                }
            }
        }
    }
}