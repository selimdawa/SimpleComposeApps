package com.flatcode.simplecomposeapps.dogs.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.flatcode.simplecomposeapps.dogs.DogUiState
import com.flatcode.simplecomposeapps.dogs.DogViewModel
import com.flatcode.simplecomposeapps.ui.AppIcons
import com.flatcode.simplecomposeapps.ui.ToolbarContent
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ERROR
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.utils.DATA

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogsScreen(
    viewModel: DogViewModel, onBack: () -> Unit
) {
    val breeds by viewModel.breedsList.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    var expanded by remember { mutableStateOf(false) }
    var selectedBreed by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            ToolbarContent(
                title = DATA.DOGS, hasBack = false, onBackClick = onBack
            )
        }, containerColor = COLOR_ON_BACKGROUND
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                OutlinedTextField(
                    value = selectedBreed,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text(Strings.HINT_TEXT_BREEDS) },
                    placeholder = { Text(Strings.SELECT_BREED) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = COLOR_ERROR,
                        unfocusedBorderColor = COLOR_ERROR,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedTextColor = COLOR_ERROR,
                        unfocusedTextColor = COLOR_ERROR,
                        focusedLabelColor = COLOR_ERROR,
                        unfocusedLabelColor = COLOR_ERROR,
                        cursorColor = COLOR_ERROR,
                        focusedTrailingIconColor = COLOR_ERROR,
                        unfocusedTrailingIconColor = COLOR_ERROR,
                        focusedPlaceholderColor = COLOR_ERROR.copy(alpha = 0.6f),
                        unfocusedPlaceholderColor = COLOR_ERROR.copy(alpha = 0.6f)
                    )
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    containerColor = COLOR_ON_BACKGROUND
                ) {
                    breeds.forEach { breed ->
                        DropdownMenuItem(
                            text = { Text(breed) }, onClick = {
                            selectedBreed = breed
                            viewModel.getDogPhotosList(breed)
                            expanded = false
                        }, colors = MenuDefaults.itemColors(
                            textColor = COLOR_ERROR
                        )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Box(modifier = Modifier.fillMaxSize()) {
                when (uiState) {
                    is DogUiState.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center), color = COLOR_ERROR
                        )
                    }

                    is DogUiState.Error -> {
                        Image(
                            imageVector = AppIcons.ConnectionError,
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(170.dp)
                        )
                    }

                    is DogUiState.Success -> {
                        val photos = (uiState as DogUiState.Success).photos
                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(photos) { photo ->
                                DogListItem(imageUrl = photo)
                            }
                        }
                    }

                    else -> {}
                }
            }
        }
    }
}