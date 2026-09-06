package com.flatcode.simplecomposeapps.weather.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.os.Build
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
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.flatcode.simplecomposeapps.ui.theme.MC_BG
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.utils.DATA
import com.flatcode.simplecomposeapps.weather.model.MainViewModel
import com.flatcode.simplecomposeapps.weather.model.WeatherModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import timber.log.Timber
import java.util.Locale
import kotlin.coroutines.resume

@Composable
fun WeatherMainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val weatherCurrent by viewModel.liveDataCurrent.collectAsState()
    val weatherList by viewModel.liveDataList.collectAsState()
    val savedWeather by viewModel.savedWeather.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf(Strings.HOURS, Strings.DAYS)
    var showSearchDialog by remember { mutableStateOf(false) }

    val pLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            checkLocation(context, viewModel, scope)
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
                checkLocation(context, viewModel, scope)
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
                            getWeatherRequest(it, context, viewModel, scope)
                        } ?: checkLocation(context, viewModel, scope)
                    })

                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    containerColor = Color.Transparent,
                    contentColor = Color.White,
                    indicator = { tabPositions ->
                        if (selectedTabIndex < tabPositions.size) {
                            Box(
                                modifier = Modifier
                                    .tabIndicatorOffset(tabPositions[selectedTabIndex])
                                    .height(3.dp)
                                    .padding(horizontal = 48.dp)
                                    .background(
                                        color = Color.White, shape = RoundedCornerShape(3.dp)
                                    )
                            )
                        }
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
                                    viewModel.updateCurrent(it)
                                }
                            })
                    }
                }
            }
        }
    }

    if (showSearchDialog) {
        SearchDialog(onDismiss = { showSearchDialog = false }, onSearch = { city ->
            getWeatherRequest(city, context, viewModel, scope)
        })
    }
}

private fun checkLocation(
    context: Context, viewModel: MainViewModel, scope: CoroutineScope
) {
    val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
        getLocation(context, viewModel, scope)
    } else {
        context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
    }
}

private fun getLocation(
    context: Context, viewModel: MainViewModel, scope: CoroutineScope
) {
    if (ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) return

    LocationServices.getFusedLocationProviderClient(context)
        .getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, CancellationTokenSource().token)
        .addOnCompleteListener { task ->
            task.result?.let {
                getWeatherRequest("${it.latitude},${it.longitude}", context, viewModel, scope)
            }
        }
}

private fun getWeatherRequest(
    city: String, context: Context, viewModel: MainViewModel, scope: CoroutineScope
) {
    viewModel.lastCity = city
    viewModel.setLoading(true)
    val url = "${DATA.BASE_URL_WEATHER}${DATA.API_KEY_WEATHER}&q=$city&days=3&aqi=no&alerts=no"
    val request = StringRequest(Request.Method.GET, url, { result ->
        parseWeatherData(result, context, viewModel, scope)
    }, { error ->
        viewModel.setLoading(false)
        Timber.d(error)
    })
    Volley.newRequestQueue(context).add(request)
}

private fun parseWeatherData(
    result: String, context: Context, viewModel: MainViewModel, scope: CoroutineScope
) {
    scope.launch {
        val mainObject = JSONObject(result)
        val cityName = getCityName(mainObject, context)
        val list = parseDays(mainObject, cityName, viewModel)
        parseCurrentDate(mainObject, list, cityName, viewModel)
        viewModel.setLoading(false)
    }
}

private suspend fun getCityName(mainObject: JSONObject, context: Context): String =
    withContext(Dispatchers.IO) {
        val location = mainObject.getJSONObject("location")
        val name = location.getString("name")
        val lat = location.getDouble("lat")
        val lon = location.getDouble("lon")
        val geocoder = Geocoder(context, Locale.getDefault())

        return@withContext try {
            val address = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                suspendCancellableCoroutine { continuation ->
                    geocoder.getFromLocation(lat, lon, 1) { addresses ->
                        continuation.resume(addresses.firstOrNull())
                    }
                }
            } else {
                @Suppress("DEPRECATION") geocoder.getFromLocation(lat, lon, 1)?.firstOrNull()
            }
            address?.locality ?: address?.subAdminArea ?: name
        } catch (_: Exception) {
            name
        }
    }

private fun parseDays(
    mainObject: JSONObject, cityName: String, viewModel: MainViewModel
): List<WeatherModel> {
    val list = ArrayList<WeatherModel>()
    val daysArray = mainObject.getJSONObject("forecast").getJSONArray("forecastday")

    for (i in 0 until daysArray.length()) {
        val day = daysArray.getJSONObject(i)
        val dayInfo = day.getJSONObject("day")
        val condition = dayInfo.getJSONObject("condition")

        list.add(
            WeatherModel(
                city = cityName,
                time = day.getString("date"),
                condition = condition.getString("text"),
                currentTemp = DATA.EMPTY,
                maxTemp = dayInfo.getString("maxtemp_c").toFloat().toInt().toString(),
                minTemp = dayInfo.getString("mintemp_c").toFloat().toInt().toString(),
                imageUrl = condition.getString("icon"),
                hours = day.getJSONArray("hour").toString(),
            )
        )
    }
    viewModel.updateList(list)
    return list
}

private fun parseCurrentDate(
    mainObject: JSONObject,
    weatherItem: List<WeatherModel>,
    cityName: String,
    viewModel: MainViewModel
) {
    if (weatherItem.isEmpty()) return
    val current = mainObject.getJSONObject("current")
    val condition = current.getJSONObject("condition")
    val firstDay = weatherItem[0]

    val item = WeatherModel(
        city = cityName,
        time = current.getString("last_updated"),
        condition = condition.getString("text"),
        currentTemp = "${current.getString("temp_c")}°C",
        maxTemp = firstDay.maxTemp,
        minTemp = firstDay.minTemp,
        imageUrl = condition.getString("icon"),
        hours = firstDay.hours
    )
    viewModel.updateCurrent(item)
    viewModel.saveWeather(item)
}

private fun getHoursList(wItem: WeatherModel): List<WeatherModel> {
    val list = ArrayList<WeatherModel>()
    if (wItem.hours.isEmpty() || (wItem.hours == DATA.EMPTY)) return emptyList()
    val hoursArray = JSONArray(wItem.hours)

    for (i in 0 until hoursArray.length()) {
        val hourObject = hoursArray.getJSONObject(i)
        val conditionObject = hourObject.getJSONObject("condition")
        val tempInt = hourObject.getString("temp_c").toFloat().toInt()

        list.add(
            WeatherModel(
                city = wItem.city,
                time = hourObject.getString("time"),
                condition = conditionObject.getString("text"),
                currentTemp = "$tempInt°C",
                maxTemp = DATA.EMPTY,
                minTemp = DATA.EMPTY,
                imageUrl = conditionObject.getString("icon"),
                hours = DATA.EMPTY,
            )
        )
    }
    return list
}