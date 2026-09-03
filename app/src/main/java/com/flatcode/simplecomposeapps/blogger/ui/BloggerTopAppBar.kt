package com.flatcode.simplecomposeapps.blogger.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.sp
import com.flatcode.simplecomposeapps.ui.AppIcons
import com.flatcode.simplecomposeapps.ui.theme.MC_TRACK

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BloggerTopAppBar(
    title: String,
    onSearch: (String) -> Unit,
    onPagesClick: () -> Unit,
    onBack: (() -> Unit)? = null
) {
    var isSearchMode by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    TopAppBar(
        title = {
        if (isSearchMode) {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search...", color = Color.White.copy(alpha = 0.7f)) },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    onSearch(searchQuery)
                })
            )
        } else {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }, navigationIcon = {
        if (isSearchMode) {
            IconButton(onClick = {
                isSearchMode = false
                searchQuery = ""
                onSearch("")
            }) {
                Icon(
                    imageVector = AppIcons.Close, contentDescription = null, tint = Color.White
                )
            }
        } else if (onBack != null) {
            IconButton(onClick = onBack) {
                Icon(imageVector = AppIcons.Back, contentDescription = null, tint = Color.White)
            }
        } else {
            IconButton(onClick = { isSearchMode = true }) {
                Icon(
                    imageVector = AppIcons.Search, contentDescription = null, tint = Color.White
                )
            }
        }
    }, actions = {
        if (isSearchMode) {
            IconButton(onClick = { onSearch(searchQuery) }) {
                Icon(
                    imageVector = AppIcons.Search, contentDescription = null, tint = Color.White
                )
            }
        } else {
            IconButton(onClick = onPagesClick) {
                Icon(
                    imageVector = AppIcons.Article,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }, colors = TopAppBarDefaults.topAppBarColors(containerColor = MC_TRACK)
    )
}