package com.flatcode.simplecomposeapps.meals.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.flatcode.simplecomposeapps.meals.pojo.Category
import com.flatcode.simplecomposeapps.ui.theme.MC_TRACK
import com.flatcode.simplecomposeapps.ui.theme.image_profile

@Composable
fun CategoryMealItem(
    item: Category, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 5.dp, end = 5.dp, bottom = 10.dp),
        shape = RoundedCornerShape(6.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MC_TRACK),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = item.strCategoryThumb,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .background(image_profile),
                contentScale = ContentScale.Fit
            )
            Text(
                text = item.strCategory,
                modifier = Modifier.padding(horizontal = 5.dp, vertical = 5.dp),
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun CategoryMealItemPreview() {
    CategoryMealItem(
        item = Category(
            idCategory = "1",
            strCategory = "Beef",
            strCategoryDescription = "",
            strCategoryThumb = "https://www.themealdb.com/images/category/beef.png"
        )
    )
}