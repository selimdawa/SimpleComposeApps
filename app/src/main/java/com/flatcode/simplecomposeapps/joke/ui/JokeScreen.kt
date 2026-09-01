package com.flatcode.simplecomposeapps.joke.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flatcode.simplecomposeapps.joke.viewmodel.JokeViewModel
import com.flatcode.simplecomposeapps.ui.theme.AppTheme
import com.flatcode.simplecomposeapps.ui.theme.rememberAttributeColor
import com.flatcode.simplecomposeapps.utils.DATA
import io.selimdawa.multicolors.MultiColorManager

@Composable
fun JokeScreen(viewModel: JokeViewModel) {
    val jokes = viewModel.jokes
    val isLoading = viewModel.isLoading.value
    val errorMessage = viewModel.errorMessage.value
    val selectedCategory = viewModel.selectedCategory.value
    val categories = viewModel.categories
    val themeId by MultiColorManager.currentThemeId.collectAsState()
    val mcBg = rememberAttributeColor("mc_bg", AppTheme.colors.background, themeId)
    val colorOnBackground = rememberAttributeColor("colorOnBackground", Color.White, themeId)
    val colorError = rememberAttributeColor("colorError", Color.Red, themeId)

    Scaffold(
        topBar = {
            JokeToolbar(
                title = DATA.JOKE,
            )
        },
        containerColor = mcBg,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                contentPadding = PaddingValues(horizontal = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { category ->
                    val isSelected = category == selectedCategory
                    Surface(
                        color = if (isSelected) colorError else colorOnBackground,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { viewModel.onCategorySelected(category) }) {
                        Text(
                            text = category,
                            color = if (isSelected) colorOnBackground else colorError,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                }
            }

            Box(modifier = Modifier.weight(1f)) {
                if (errorMessage != null) {
                    Text(
                        text = errorMessage,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp),
                        color = colorError
                    )
                } else if (!isLoading) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(jokes) { joke ->
                            JokeItem(joke = joke)
                        }
                    }
                }
            }
        }
    }
}