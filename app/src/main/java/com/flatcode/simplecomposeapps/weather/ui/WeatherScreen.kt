package com.flatcode.simplecomposeapps.weather.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.flatcode.simplecomposeapps.weather.WeatherViewModel
import com.flatcode.simplecomposeapps.weather.db.WeatherModel

@Composable
fun WeatherScreen(
    onBack: () -> Unit,
    viewModel: WeatherViewModel = hiltViewModel()
) {
    val currentData by viewModel.liveDataCurrent.observeAsState()
    val listData by viewModel.liveDataList.observeAsState(emptyList())

    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Hours", "Days")

    Scaffold(
        topBar = {
            WeatherTopAppBar(
                onBack = onBack,
                onSearchClick = { /* Implement search dialog */ },
                onSyncClick = { /* Implement sync logic */ }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            currentData?.let { data ->
                CurrentWeatherCard(data = data)
            }

            Spacer(modifier = Modifier.height(16.dp))

            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = Color.Transparent,
                contentColor = Color.White
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(text = title) }
                    )
                }
            }

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(listData) { item ->
                    WeatherItem(weather = item)
                }
            }
        }
    }
}

@Composable
fun CurrentWeatherCard(data: WeatherModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0x38FFFFFF)), // white_22
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = data.time, color = Color.White, fontSize = 16.sp)
            Text(text = data.city, color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold)
            
            AsyncImage(
                model = "https:${data.imageUrl}",
                contentDescription = null,
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Fit
            )
            
            Text(text = data.currentTemp, color = Color.White, fontSize = 48.sp, fontWeight = FontWeight.Bold)
            Text(text = data.condition, color = Color.White, fontSize = 20.sp)
            
            val maxMinTemp = "${data.maxTemp}°C / ${data.minTemp}°C"
            Text(text = maxMinTemp, color = Color.White, fontSize = 16.sp)
        }
    }
}