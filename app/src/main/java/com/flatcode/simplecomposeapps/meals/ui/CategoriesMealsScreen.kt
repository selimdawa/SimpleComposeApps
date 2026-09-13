package com.flatcode.simplecomposeapps.meals.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.flatcode.simplecomposeapps.ui.CustomProgressBar
import com.flatcode.simplecomposeapps.utils.Resource
import com.flatcode.simplecomposeapps.meals.viewmodel.MealsHomeViewModel
import com.flatcode.simplecomposeapps.utils.DATA.COLOR_ERROR
import com.flatcode.simplecomposeapps.utils.DATA.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.utils.DATA.MC_TRACK
import com.flatcode.simplecomposeapps.ui.theme.Strings

@Composable
fun CategoriesMealsScreen(
    onCategoryClick: (String) -> Unit, viewModel: MealsHomeViewModel = hiltViewModel()
) {
    val categoriesResult by viewModel.categories.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getCategories()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(COLOR_ON_BACKGROUND)
    ) {
        when (categoriesResult) {
            is Resource.Loading -> {
                CustomProgressBar(
                    modifier = Modifier.align(Alignment.Center), color = MC_TRACK
                )
            }
            is Resource.Success -> {
                val categories = categoriesResult.data ?: emptyList()
                if (categories.isEmpty()) {
                    Text(
                        text = Strings.NO_DATA_FOUND,
                        modifier = Modifier.align(Alignment.Center),
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        color = COLOR_ERROR
                    )
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 10.dp),
                        horizontalArrangement = Arrangement.spacedBy(0.dp),
                        verticalArrangement = Arrangement.spacedBy(0.dp)
                    ) {
                        items(categories) { category ->
                            CategoryMealItem(
                                item = category, modifier = Modifier.clickable {
                                    onCategoryClick(
                                        category.strCategory
                                    )
                                })
                        }
                    }
                }
            }
            is Resource.Error -> {
                Text(
                    text = categoriesResult.message ?: Strings.UNKNOWN_ERROR,
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    color = COLOR_ERROR
                )
            }
            else -> {}
        }
    }
}