package com.flatcode.simplecomposeapps.meals.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.flatcode.simplecomposeapps.meals.MealsHomeViewModel
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.utils.DATA

@Composable
fun FavoritesMealsScreen(
    onBack: () -> Unit,
    onMealClick: (String, String, String) -> Unit,
    viewModel: MealsHomeViewModel = hiltViewModel()
) {
    val favorites by viewModel.observeFavoritesMealsLiveData().observeAsState(emptyList())

    Scaffold(
        topBar = {
            MealsTopAppBar(title = Strings.FAVORITES, onBack = onBack)
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (favorites.isEmpty()) {
                Text(
                    text = Strings.NO_DATA_FOUND,
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.error
                )
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(8.dp)
                ) {
                    items(favorites) { meal ->
                        MealItem(
                            item = meal,
                            modifier = Modifier.clickable { onMealClick(meal.idMeal, meal.strMeal ?: "", meal.strMealThumb ?: "") }
                        )
                    }
                }
            }
        }
    }
}