package com.flatcode.simplecomposeapps.dogs.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flatcode.simplecomposeapps.dogs.DogViewModel
import com.flatcode.simplecomposeapps.ui.theme.AppIcons
import com.flatcode.simplecomposeapps.ui.ToolbarContent
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ERROR
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.ui.theme.MC_TRACK
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.utils.DATA
import com.flatcode.simplecomposeapps.utils.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogsScreen(
    viewModel: DogViewModel
) {
    val breeds by viewModel.breedsList.observeAsState(emptyList())
    val uiState by viewModel.uiState.observeAsState(Resource.Idle)
    val isNetworkAvailable by viewModel.isNetworkAvailable.collectAsState(initial = true)

    var expanded by remember { mutableStateOf(false) }
    var selectedBreed by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            ToolbarContent(
                title = DATA.DOGS, hasBack = false
            )
        }, containerColor = COLOR_ON_BACKGROUND
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            AnimatedVisibility(
                visible = !isNetworkAvailable,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Text(
                    text = Strings.NO_INTERNET_CONNECTION,
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(COLOR_ERROR)
                        .padding(8.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp
                )
            }

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
                        .menuAnchor(type = ExposedDropdownMenuAnchorType.PrimaryNotEditable)
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
                val photos = uiState.data ?: emptyList()

                if (photos.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(photos, key = { it }) { photo ->
                            DogListItem(imageUrl = photo)
                        }
                    }
                }

                when (uiState) {
                    is Resource.Loading -> {
                        if (photos.isEmpty()) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center), color = MC_TRACK
                            )
                        }
                    }

                    is Resource.Error -> {
                        if (photos.isEmpty()) {
                            Image(
                                imageVector = AppIcons.ConnectionError,
                                contentDescription = null,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .size(170.dp)
                            )
                        }
                    }

                    else -> {}
                }
            }
        }
    }
}