package com.flatcode.simplecomposeapps.meals.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.flatcode.simplecomposeapps.meals.CategoriesMealsViewModel
import com.flatcode.simplecomposeapps.meals.pojo.Meal

@Composable
fun CategoryMealsScreen(
    categoryName: String,
    onBack: () -> Unit,
    onMealClick: (String, String, String) -> Unit,
    viewModel: CategoriesMealsViewModel = hiltViewModel()
) {
    val meals by viewModel.observeMealsLiveData().observeAsState(emptyList())

    LaunchedEffect(categoryName) {
        viewModel.getMealsByCategory(categoryName)
    }

    Scaffold(
        topBar = {
            MealsTopAppBar(title = categoryName, onBack = onBack)
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Category: $categoryName (${meals.size})",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(8.dp)
                ) {
                    items(meals) { meal ->
                        MealItem(
                            item = Meal(
                                idMeal = meal.idMeal,
                                strMeal = meal.strMeal,
                                strMealThumb = meal.strMealThumb,
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
                            ),
                            modifier = Modifier.clickable { onMealClick(meal.idMeal, meal.strMeal, meal.strMealThumb) }
                        )
                    }
                }
            }
        }
    }
}