package com.flatcode.simplecomposeapps.countries.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.flatcode.simplecomposeapps.countries.DetailViewModel
import com.flatcode.simplecomposeapps.ui.ToolbarContent
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.ui.theme.MC_TRACK
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.ui.theme.image_profile
import com.flatcode.simplecomposeapps.utils.DATA
import com.flatcode.simplecomposeapps.utils.SimpleBlurTransformation

@Composable
fun CountryDetailScreen(
    countryUuid: Int, onBack: () -> Unit, viewModel: DetailViewModel = hiltViewModel()
) {
    val country by viewModel.countryLiveData.observeAsState()

    LaunchedEffect(countryUuid) {
        viewModel.getDataFromRoom(countryUuid)
    }

    Scaffold(
        topBar = {
            ToolbarContent(
                title = DATA.COUNTRY_DETAILS, hasBack = true, onBackClick = onBack
            )
        }, containerColor = COLOR_ON_BACKGROUND
    ) { paddingValues ->
        country?.let { item ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(2f)
                        .background(image_profile)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current).data(item.imageURL)
                            .transformations(SimpleBlurTransformation(50f)).build(),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    SubcomposeAsyncImage(
                        model = item.imageURL,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit,
                        loading = {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(40.dp),
                                    color = MC_TRACK,
                                    strokeWidth = 3.dp
                                )
                            }
                        },
                        error = {
                            Box(modifier = Modifier.fillMaxSize().background(image_profile))
                        }
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    DetailInfoRow(label = Strings.COUNTRY_NAME, value = item.countryName ?: "")
                    DetailInfoRow(
                        label = Strings.COUNTRY_CAPITAL, value = item.countryCapital ?: ""
                    )
                    DetailInfoRow(label = Strings.COUNTRY_REGION, value = item.countryRegion ?: "")
                    DetailInfoRow(
                        label = Strings.COUNTRY_LANGUAGE, value = item.countryLanguage ?: ""
                    )
                    DetailInfoRow(
                        label = Strings.COUNTRY_CURRENCY, value = item.countryCurrency ?: ""
                    )
                }
            }
        }
    }
}

@Composable
fun DetailInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = value,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}
