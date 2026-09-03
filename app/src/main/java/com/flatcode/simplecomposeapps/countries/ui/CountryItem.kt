package com.flatcode.simplecomposeapps.countries.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.flatcode.simplecomposeapps.countries.model.Country
import com.flatcode.simplecomposeapps.ui.theme.image_profile
import com.flatcode.simplecomposeapps.ui.theme.MC_BG
import com.flatcode.simplecomposeapps.ui.theme.SimpleComposeAppsTheme

@Composable
fun CountryItem(
    item: Country,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp, vertical = 5.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MC_BG)
        ) {
            Box(
                modifier = Modifier
                    .width(140.dp)
                    .aspectRatio(2f)
            ) {
                AsyncImage(
                    model = item.imageURL,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize().background(image_profile),
                    contentScale = ContentScale.Crop
                )
                // Second image in XML is just empty holder for name or similar?
                // <ImageView android:id="@+id/imageName" ... />
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 10.dp, vertical = 10.dp)
            ) {
                Text(
                    text = item.countryName ?: "",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = item.countryRegion ?: "",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Preview
@Composable
fun CountryItemPreview() {
    SimpleComposeAppsTheme {
        CountryItem(
            item = Country(
                countryName = "Afghanistan",
                countryRegion = "Asia",
                countryCapital = "Kabul",
                countryCurrency = "AFN",
                countryLanguage = "Pashto",
                imageURL = "https://restcountries.eu/data/afg.svg"
            )
        )
    }
}
