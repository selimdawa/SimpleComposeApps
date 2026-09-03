package com.flatcode.simplecomposeapps.rickAndMorty.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
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
import com.flatcode.simplecomposeapps.rickAndMorty.data.models.Location
import com.flatcode.simplecomposeapps.ui.theme.MC_BG

@Composable
fun LocationItem(
    item: Location,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 5.dp, end = 5.dp, bottom = 10.dp),
        shape = RoundedCornerShape(6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MC_BG)
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
                text = item.url,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 10.dp),
                color = Color.White,
                fontSize = 18.sp,
                textAlign = TextAlign.End
            )
        }
    }
}

@Preview
@Composable
fun LocationItemPreview() {
    LocationItem(
        item = Location(
            id = 1,
            name = "Earth (C-137)",
            type = "Planet",
            dimension = "Dimension C-137",
            residents = emptyList(),
            url = "https://rickandmortyapi.com/api/location/1",
            created = ""
        )
    )
}
