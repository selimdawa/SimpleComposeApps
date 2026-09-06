package com.flatcode.simplecomposeapps.weather.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.flatcode.simplecomposeapps.ui.AppIcons
import com.flatcode.simplecomposeapps.ui.theme.MC_BG
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.weather.model.WeatherModel

@Composable
fun WeatherListItem(item: WeatherModel, onClick: (WeatherModel) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .clickable { onClick(item) },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0x22FFFFFF)
        ),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = item.time, style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Medium, color = Color.White, fontSize = 16.sp
                    )
                )
                Text(
                    text = item.condition, style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp
                    )
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = item.currentTemp.ifEmpty { "${item.maxTemp}°C / ${item.minTemp}°C" },
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Medium, color = Color.White, fontSize = 20.sp
                    ),
                    modifier = Modifier.padding(end = 16.dp)
                )
                AsyncImage(
                    model = "https:${item.imageUrl}",
                    contentDescription = Strings.WEATHER_ICON,
                    modifier = Modifier.size(48.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}

@Composable
fun WeatherCard(
    weather: WeatherModel, onSearchClick: () -> Unit, onSyncClick: () -> Unit
) {
    val maxMin = "${weather.maxTemp}°C / ${weather.minTemp}°C"
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0x33FFFFFF)
        ),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = weather.time, style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Medium,
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 14.sp
                    )
                )
                AsyncImage(
                    model = "https:${weather.imageUrl}",
                    contentDescription = Strings.WEATHER_ICON,
                    modifier = Modifier.size(70.dp),
                    contentScale = ContentScale.Fit
                )
            }
            Text(
                text = weather.city, style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Medium, color = Color.White, fontSize = 32.sp
                )
            )
            Text(
                text = weather.currentTemp.ifEmpty { maxMin },
                style = MaterialTheme.typography.displayLarge.copy(
                    fontWeight = FontWeight.Light, color = Color.White, fontSize = 72.sp
                )
            )
            Text(
                text = weather.condition, style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Medium,
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 18.sp
                )
            )
            Text(
                text = if (weather.currentTemp.isEmpty()) "" else maxMin,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.White.copy(alpha = 0.7f), fontSize = 14.sp
                )
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onSearchClick) {
                    Icon(
                        imageVector = AppIcons.Search,
                        contentDescription = Strings.SEARCH,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
                IconButton(onClick = onSyncClick) {
                    Icon(
                        imageVector = AppIcons.Sync,
                        contentDescription = Strings.SYNC,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun SearchDialog(
    onDismiss: () -> Unit, onSearch: (String) -> Unit
) {
    var cityName by remember { mutableStateOf("") }
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(0.dp),
            colors = CardDefaults.cardColors(containerColor = MC_BG)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = Strings.CITY_NAME_HINT,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Medium, color = Color.White, fontSize = 18.sp
                    )
                )
                OutlinedTextField(
                    value = cityName,
                    onValueChange = { cityName = it },
                    label = { Text(Strings.SEARCH_HINT, color = Color.White) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White.copy(alpha = 0.7f),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    )
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(text = Strings.CANCEL, color = Color.White)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(onClick = {
                        onSearch(cityName)
                        onDismiss()
                    }) {
                        Text(text = Strings.OK, color = Color.White)
                    }
                }
            }
        }
    }
}