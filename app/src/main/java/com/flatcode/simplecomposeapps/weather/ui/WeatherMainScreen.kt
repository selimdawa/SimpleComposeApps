package com.flatcode.simplecomposeapps.weather.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.flatcode.simplecomposeapps.ui.theme.MC_BG
import com.flatcode.simplecomposeapps.utils.DATA
import com.flatcode.simplecomposeapps.weather.model.MainViewModel
import com.flatcode.simplecomposeapps.weather.model.WeatherModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import org.json.JSONArray

@Composable
fun WeatherMainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val weatherCurrent by viewModel.liveDataCurrent.observeAsState()
    val weatherList by viewModel.liveDataList.observeAsState(emptyList())
    val savedWeather by viewModel.savedWeather.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(true)

    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = DATA.WEATHER_TABS
    var showSearchDialog by remember { mutableStateOf(false) }

    val pLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            checkLocation(context, viewModel)
        } else {
            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(savedWeather) {
        savedWeather?.let { viewModel.updateCurrent(it) }
    }

    LaunchedEffect(Unit) {
        if (viewModel.lastCity == null) {
            if (ContextCompat.checkSelfPermission(
                    context, Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                checkLocation(context, viewModel)
            } else {
                pLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(), containerColor = MC_BG
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            weatherCurrent?.let { weather ->
                WeatherCard(
                    weather = weather,
                    isLoading = isLoading,
                    onSearchClick = { showSearchDialog = true },
                    onSyncClick = {
                        viewModel.lastCity?.let {
                            viewModel.getWeather(it)
                        } ?: checkLocation(context, viewModel)
                    })

                SecondaryTabRow(
                    selectedTabIndex = selectedTabIndex,
                    containerColor = Color.Transparent,
                    contentColor = Color.White,
                    indicator = {
                        Box(
                            modifier = Modifier
                                .tabIndicatorOffset(selectedTabIndex)
                                .height(3.dp)
                                .padding(horizontal = 48.dp)
                                .background(
                                    color = Color.White, shape = RoundedCornerShape(3.dp)
                                )
                        )
                    },
                    divider = {}) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index },
                            text = {
                                Text(
                                    text = title,
                                    color = if (selectedTabIndex == index) Color.White else Color.White.copy(
                                        alpha = 0.7f
                                    )
                                )
                            })
                    }
                }

                val currentList = if (selectedTabIndex == 0) {
                    getHoursList(weather)
                } else {
                    weatherList
                }

                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(currentList) { item ->
                        WeatherListItem(
                            item = item, onClick = {
                                if (selectedTabIndex == 1) {
                                    viewModel.updateCurrent(item)
                                }
                            })
                    }
                }
            }
        }
    }

    if (showSearchDialog) {
        SearchDialog(onDismiss = { showSearchDialog = false }, onSearch = { city ->
            viewModel.getWeather(city)
        })
    }
}

private fun checkLocation(
    context: Context, viewModel: MainViewModel
) {
    val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
        getLocation(context, viewModel)
    } else {
        context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
    }
}

private fun getLocation(
    context: Context, viewModel: MainViewModel
) {
    if (ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) return

    LocationServices.getFusedLocationProviderClient(context)
        .getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, CancellationTokenSource().token)
        .addOnCompleteListener { task ->
            task.result?.let {
                viewModel.getWeather("${it.latitude},${it.longitude}")
            }
        }
}

private fun getHoursList(wItem: WeatherModel): List<WeatherModel> {
    val list = ArrayList<WeatherModel>()
    if (wItem.hours.isEmpty() || (wItem.hours == DATA.EMPTY)) return emptyList()
    val hoursArray = JSONArray(wItem.hours)

    for (i in 0 until hoursArray.length()) {
        val hourObject = hoursArray.getJSONObject(i)
        val conditionObject = hourObject.getJSONObject(DATA.CONDITION)
        val tempInt = hourObject.getString(DATA.TEMP_C).toFloat().toInt()

        list.add(
            WeatherModel(
                city = wItem.city,
                time = hourObject.getString(DATA.TIME),
                condition = conditionObject.getString(DATA.TEXT),
                currentTemp = "$tempInt°C",
                maxTemp = DATA.EMPTY,
                minTemp = DATA.EMPTY,
                imageUrl = conditionObject.getString(DATA.ICON),
                hours = DATA.EMPTY,
            )
        )
    }
    return list
}