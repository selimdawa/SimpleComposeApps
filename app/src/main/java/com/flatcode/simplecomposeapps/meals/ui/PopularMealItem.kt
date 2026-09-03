package com.flatcode.simplecomposeapps.meals.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.flatcode.simplecomposeapps.meals.pojo.Meal

@Composable
fun PopularMealItem(
    item: Meal,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(220.dp)
            .height(160.dp)
            .padding(start = 5.dp, end = 5.dp, bottom = 10.dp),
        shape = RoundedCornerShape(6.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        AsyncImage(
            model = item.strMealThumb,
            contentDescription = null,
            modifier = Modifier.fillParentMaxSize(), // Placeholder for fillMaxSize in this context
            contentScale = ContentScale.Crop
        )
    }
}

// Helper to fix fillParentMaxSize error if not in LazyItemScope
@Composable
private fun Modifier.fillParentMaxSize() = this.then(Modifier.fillMaxSize())

@Preview
@Composable
fun PopularMealItemPreview() {
    PopularMealItem(
        item = Meal(
            idMeal = "1",
            strMeal = "Popular Meal",
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
