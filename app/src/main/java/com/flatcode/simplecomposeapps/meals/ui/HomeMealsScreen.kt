package com.flatcode.simplecomposeapps.meals.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.flatcode.simplecomposeapps.meals.MealsHomeViewModel
import com.flatcode.simplecomposeapps.meals.pojo.Meal
import com.flatcode.simplecomposeapps.ui.ToolbarContent
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.utils.DATA

@Composable
fun HomeMealsScreen(
    onBack: () -> Unit,
    onMealClick: (String, String, String) -> Unit,
    onCategoryClick: (String) -> Unit,
    viewModel: MealsHomeViewModel = hiltViewModel()
) {
    val randomMeal by viewModel.observeRandomMealLiveData().observeAsState()
    val popularItems by viewModel.observerPopularItemsLiveData().observeAsState(emptyList())
    val categories by viewModel.observeCategoriesLiveData().observeAsState(emptyList())

    LaunchedEffect(Unit) {
        viewModel.getRandomMeal()
        viewModel.getPopularItems()
        viewModel.getCategories()
    }

    Scaffold(
        topBar = {
            ToolbarContent(title = DATA.MEALS, hasBack = false, onBackClick = onBack)
        },
        containerColor = COLOR_ON_BACKGROUND
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // What would you like to eat?
            Text(
                text = Strings.WHAT_WOULD_YOU_LIKE_TO_EAT,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Random Meal
            randomMeal?.let { meal ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.8f)
                        .clickable { onMealClick(meal.idMeal, meal.strMeal ?: "", meal.strMealThumb ?: "") },
                    shape = RoundedCornerShape(12.dp)
                ) {
                    AsyncImage(
                        model = meal.strMealThumb,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Popular Items
            Text(
                text = Strings.OVER_POPULAR_ITEMS,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyRow {
                items(popularItems) { meal ->
                    PopularMealItem(
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

            Spacer(modifier = Modifier.height(24.dp))

            // Categories
            Text(
                text = Strings.CATEGORIES,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column {
                categories.chunked(3).forEach { rowItems ->
                    Row(modifier = Modifier.fillMaxWidth()) {
                        rowItems.forEach { category ->
                            Box(modifier = Modifier.weight(1f)) {
                                CategoryMealItem(
                                    item = category,
                                    modifier = Modifier.clickable { onCategoryClick(category.strCategory ?: "") }
                                )
                            }
                        }
                        repeat(3 - rowItems.size) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}