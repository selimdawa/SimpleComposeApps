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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flatcode.simplecomposeapps.joke.viewmodel.JokeViewModel
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ERROR
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.ui.theme.MC_BG
import com.flatcode.simplecomposeapps.utils.DATA

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.text.style.TextAlign
import com.flatcode.simplecomposeapps.ui.theme.MC_TRACK
import com.flatcode.simplecomposeapps.ui.theme.Strings

@Composable
fun JokeScreen(viewModel: JokeViewModel) {
    val jokes = viewModel.jokes
    val isLoading by viewModel.isLoading
    val errorMessage by viewModel.errorMessage
    val selectedCategory by viewModel.selectedCategory
    val categories = viewModel.categories

    Scaffold(
        topBar = {
            JokeToolbar(
                title = DATA.JOKE,
            )
        },
        containerColor = MC_BG,
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
                        color = if (isSelected) COLOR_ERROR else COLOR_ON_BACKGROUND,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { viewModel.onCategorySelected(category) }) {
                        Text(
                            text = category,
                            color = if (isSelected) COLOR_ON_BACKGROUND else COLOR_ERROR,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                }
            }

            Box(modifier = Modifier.weight(1f)) {
                if (isLoading && jokes.isEmpty()) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(50.dp)
                            .align(Alignment.Center),
                        color = MC_TRACK
                    )
                } else if (errorMessage != null && jokes.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = errorMessage!!,
                            color = COLOR_ERROR,
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp
                        )
                        Button(
                            onClick = { viewModel.retry() },
                            colors = ButtonDefaults.buttonColors(containerColor = COLOR_ERROR)
                        ) {
                            Text(text = "Retry", color = COLOR_ON_BACKGROUND)
                        }
                    }
                } else if (jokes.isEmpty() && !isLoading) {
                    Text(
                        text = Strings.NONE_DISPLAY,
                        color = COLOR_ERROR,
                        fontSize = 32.sp,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(horizontal = 20.dp),
                        textAlign = TextAlign.Center
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(jokes) { joke ->
                            JokeItem(joke = joke)
                        }
                    }
                    
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(50.dp)
                                .align(Alignment.Center),
                            color = MC_TRACK
                        )
                    }
                }
            }
        }
    }
}