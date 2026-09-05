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
import com.flatcode.simplecomposeapps.meals.model.Meal
import com.flatcode.simplecomposeapps.ui.theme.image_profile
import com.flatcode.simplecomposeapps.ui.theme.MC_TRACK

@Composable
fun MealItem(
    item: Meal,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 5.dp, end = 5.dp, bottom = 10.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(6.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            colors = CardDefaults.cardColors(containerColor = MC_TRACK)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = item.strMealThumb,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .background(image_profile),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = item.strMeal ?: "",
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp),
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview
@Composable
fun MealItemPreview() {
    MealItem(
        item = Meal(
            idMeal = "1",
            strMeal = "Teriyaki Chicken Carrot Ginger Soup",
            strMealThumb = "https://www.themealdb.com/images/media/meals/g046bb1644039423.jpg",
            dateModified = null, strArea = null, strCategory = null, strCreativeCommonsConfirmed = null,
            strDrinkAlternate = null, strImageSource = null, strIngredient1 = null, strIngredient10 = null,
            strIngredient11 = null, strIngredient12 = null, strIngredient13 = null, strIngredient14 = null,
            strIngredient15 = null, strIngredient16 = null, strIngredient17 = null, strIngredient18 = null,
            strIngredient19 = null, strIngredient2 = null, strIngredient20 = null, strIngredient3 = null,
            strIngredient4 = null, strIngredient5 = null, strIngredient6 = null, strIngredient7 = null,
            strIngredient8 = null, strIngredient9 = null, strInstructions = null, strMeasure1 = null,
            strMeasure10 = null, strMeasure11 = null, strMeasure12 = null, strMeasure13 = null,
            strMeasure14 = null, strMeasure15 = null, strMeasure16 = null, strMeasure17 = null,
            strMeasure18 = null, strMeasure19 = null, strMeasure2 = null, strMeasure20 = null,
            strMeasure3 = null, strMeasure4 = null, strMeasure5 = null, strMeasure6 = null,
            strMeasure7 = null, strMeasure8 = null, strMeasure9 = null, strSource = null,
            strTags = null, strYoutube = null
        )
    )
}
