package com.flatcode.simplecomposeapps.dogs.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ERROR

@Composable
fun DogListItem(
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(320.dp)
            .padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = COLOR_ERROR)
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(2.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "Dog image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DogListItemPreview() {
    DogListItem(imageUrl = "https://example.com/dog.jpg")
}
