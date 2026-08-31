package com.flatcode.simplecomposeapps.blogger.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flatcode.simplecomposeapps.ui.AppIcons
import com.flatcode.simplecomposeapps.ui.theme.AppTheme
import com.flatcode.simplecomposeapps.ui.theme.Strings

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
    val mcTrack = AppTheme.colors.track

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
        },
        navigationIcon = {
            if (isSearchMode) {
                IconButton(onClick = {
                    isSearchMode = false
                    searchQuery = ""
                    onSearch("")
                }) {
                    Icon(imageVector = AppIcons.Close, contentDescription = null, tint = Color.White)
                }
            } else if (onBack != null) {
                IconButton(onClick = onBack) {
                    Icon(imageVector = AppIcons.Back, contentDescription = null, tint = Color.White)
                }
            } else {
                IconButton(onClick = { isSearchMode = true }) {
                    Icon(imageVector = AppIcons.Search, contentDescription = null, tint = Color.White)
                }
            }
        },
        actions = {
            if (isSearchMode) {
                IconButton(onClick = { onSearch(searchQuery) }) {
                    Icon(imageVector = AppIcons.Search, contentDescription = null, tint = Color.White)
                }
            } else {
                IconButton(onClick = onPagesClick) {
                    Icon(imageVector = AppIcons.Article, contentDescription = null, tint = Color.White)
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = mcTrack)
    )
}
