package com.flatcode.simplecomposeapps.weather.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.flatcode.simplecomposeapps.weather.db.WeatherModel

@Composable
fun WeatherItem(
    weather: WeatherModel,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0x38FFFFFF)), // white_22 equivalent
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = weather.time,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = weather.condition,
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 14.sp
                )
            }

            Text(
                text = weather.currentTemp,
                modifier = Modifier.padding(horizontal = 16.dp),
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )

            AsyncImage(
                model = "https:${weather.imageUrl}",
                contentDescription = "Weather icon",
                modifier = Modifier.size(48.dp),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF2196F3)
@Composable
fun WeatherItemPreview() {
    WeatherItem(
        weather = WeatherModel(
            city = "London",
            time = "2023-10-27 12:00",
            condition = "Partly Cloudy",
            currentTemp = "15°C",
            maxTemp = "18°C",
            minTemp = "10°C",
            imageUrl = "//cdn.weatherapi.com/weather/64x64/day/116.png",
            hours = ""
        )
    )
}
