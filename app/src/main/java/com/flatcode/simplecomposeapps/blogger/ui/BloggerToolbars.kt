package com.flatcode.simplecomposeapps.blogger.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flatcode.simplecomposeapps.ui.theme.AppIcons
import com.flatcode.simplecomposeapps.ui.theme.White
import com.flatcode.simplecomposeapps.utils.DATA.MC_BG

@Composable
fun BloggerSearchToolbar(
    title: String,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    isSearchMode: Boolean,
    onSearchModeChange: (Boolean) -> Unit,
    onPagesClick: () -> Unit,
    onSearchExecute: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(isSearchMode) {
        if (isSearchMode) {
            focusRequester.requestFocus()
            keyboardController?.show()
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp)
            .padding(10.dp),
        shape = RoundedCornerShape(6.dp),
        elevation = CardDefaults.cardElevation(0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MC_BG)
        ) {
            if (isSearchMode) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 8.dp)
                        .clickable(
                            interactionSource = interactionSource, indication = null
                        ) {
                            onSearchModeChange(false)
                            onSearchQueryChange("")
                            onSearchExecute()
                        }
                        .padding(8.dp) // clickable area
                ) {
                    Icon(
                        imageVector = AppIcons.Close,
                        contentDescription = null,
                        tint = White,
                        modifier = Modifier.size(30.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(start = 50.dp, end = 50.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BasicTextField(
                        value = searchQuery,
                        onValueChange = onSearchQueryChange,
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester),
                        textStyle = TextStyle(
                            color = White, fontSize = 18.sp, fontWeight = FontWeight.Medium
                        ),
                        cursorBrush = SolidColor(White),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(onSearch = { onSearchExecute() }),
                        decorationBox = { innerTextField ->
                            if (searchQuery.isEmpty()) {
                                Text(
                                    text = "Search",
                                    color = White.copy(alpha = 0.7f),
                                    fontSize = 18.sp
                                )
                            }
                            innerTextField()
                        })
                }
            } else {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 8.dp)
                        .clickable(
                            interactionSource = interactionSource, indication = null
                        ) { onSearchModeChange(true) }
                        .padding(8.dp)) {
                    Icon(
                        imageVector = AppIcons.Search,
                        contentDescription = null,
                        tint = White,
                        modifier = Modifier.size(30.dp)
                    )
                }

                Text(
                    text = title,
                    color = White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 8.dp)
                        .clickable(
                            interactionSource = interactionSource, indication = null
                        ) { onPagesClick() }
                        .padding(8.dp)) {
                    Icon(
                        imageVector = AppIcons.Article,
                        contentDescription = null,
                        tint = White,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun BloggerNameToolbar(
    title: String, onBack: (() -> Unit)? = null, endItem: (@Composable () -> Unit)? = null
) {
    val interactionSource = remember { MutableInteractionSource() }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp)
            .padding(10.dp),
        shape = RoundedCornerShape(6.dp),
        elevation = CardDefaults.cardElevation(0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MC_BG)
        ) {
            if (onBack != null) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 8.dp)
                        .clickable(
                            interactionSource = interactionSource, indication = null
                        ) { onBack() }
                        .padding(8.dp)) {
                    Icon(
                        imageVector = AppIcons.Back,
                        contentDescription = null,
                        tint = White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Text(
                text = title,
                color = White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )

            if (endItem != null) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 16.dp)
                ) {
                    endItem()
                }
            }
        }
    }
}
