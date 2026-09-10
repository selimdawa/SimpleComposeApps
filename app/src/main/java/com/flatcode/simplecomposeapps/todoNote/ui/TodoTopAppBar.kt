package com.flatcode.simplecomposeapps.todoNote.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.flatcode.simplecomposeapps.todoNote.data.SortOrder
import com.flatcode.simplecomposeapps.ui.theme.AppIcons
import com.flatcode.simplecomposeapps.utils.DATA.COLOR_ERROR
import com.flatcode.simplecomposeapps.utils.DATA.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.ui.theme.Gray
import com.flatcode.simplecomposeapps.ui.theme.Strings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoTopAppBar(
    title: String,
    onBack: () -> Unit,
    onSearchQueryChange: (String) -> Unit = {},
    searchQuery: String = "",
    onSortOrderSelected: (SortOrder) -> Unit = {},
    showHideCompleted: Boolean = false,
    hideCompleted: Boolean = false,
    onHideCompletedClick: (Boolean) -> Unit = {},
    onDeleteAllClick: () -> Unit = {},
    deleteAllText: String = "",
    hasBack: Boolean = true
) {
    var isSearchExpanded by remember { mutableStateOf(searchQuery.isNotEmpty()) }
    var sortMenuExpanded by remember { mutableStateOf(false) }
    var moreMenuExpanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            if (isSearchExpanded) {
                TextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChange,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            Strings.SEARCH_HINT, color = COLOR_ERROR.copy(alpha = 0.7f)
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        cursorColor = COLOR_ERROR,
                        focusedTextColor = COLOR_ERROR,
                        unfocusedTextColor = COLOR_ERROR
                    ),
                    singleLine = true
                )
            } else {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = COLOR_ERROR
                )
            }
        }, navigationIcon = {
            if (hasBack) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = AppIcons.Back, contentDescription = "Back", tint = COLOR_ERROR
                    )
                }
            }
        }, actions = {
            IconButton(onClick = {
                isSearchExpanded = !isSearchExpanded
                if (!isSearchExpanded) {
                    onSearchQueryChange("")
                }
            }) {
                Icon(
                    imageVector = if (isSearchExpanded) AppIcons.Close else AppIcons.Search,
                    contentDescription = Strings.SEARCH,
                    tint = COLOR_ERROR
                )
            }

            Box {
                IconButton(onClick = { sortMenuExpanded = true }) {
                    Icon(
                        imageVector = AppIcons.Sort, contentDescription = "Sort", tint = COLOR_ERROR
                    )
                }
                DropdownMenu(
                    expanded = sortMenuExpanded,
                    onDismissRequest = { sortMenuExpanded = false },
                    modifier = Modifier.background(COLOR_ON_BACKGROUND)
                ) {
                    DropdownMenuItem(
                        text = { Text(Strings.SORT_BY_NAME) },
                        onClick = {
                            onSortOrderSelected(SortOrder.BY_NAME)
                            sortMenuExpanded = false
                        },
                        modifier = Modifier.background(COLOR_ON_BACKGROUND),
                        colors = MenuDefaults.itemColors(textColor = COLOR_ERROR)
                    )
                    DropdownMenuItem(
                        text = { Text(Strings.SORT_BY_DATE) },
                        onClick = {
                            onSortOrderSelected(SortOrder.BY_DATE)
                            sortMenuExpanded = false
                        },
                        modifier = Modifier.background(COLOR_ON_BACKGROUND),
                        colors = MenuDefaults.itemColors(textColor = COLOR_ERROR)
                    )
                }
            }

            Box {
                IconButton(onClick = { moreMenuExpanded = true }) {
                    Icon(
                        imageVector = AppIcons.More, contentDescription = "More", tint = COLOR_ERROR
                    )
                }
                DropdownMenu(
                    expanded = moreMenuExpanded,
                    onDismissRequest = { moreMenuExpanded = false },
                    modifier = Modifier.background(COLOR_ON_BACKGROUND)
                ) {
                    if (showHideCompleted) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = Strings.HIDE_COMPLETED,
                                    color = if (hideCompleted) COLOR_ERROR else Color.Unspecified
                                )
                            },
                            onClick = {
                                onHideCompletedClick(!hideCompleted)
                                moreMenuExpanded = false
                            },
                            trailingIcon = {
                                Checkbox(
                                    checked = hideCompleted,
                                    onCheckedChange = null,
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = COLOR_ERROR,
                                        uncheckedColor = COLOR_ERROR,
                                        checkmarkColor = COLOR_ON_BACKGROUND
                                    )
                                )
                            },
                            modifier = Modifier.background(COLOR_ON_BACKGROUND),
                            colors = MenuDefaults.itemColors(textColor = COLOR_ERROR)
                        )
                    }
                    if (deleteAllText.isNotEmpty()) {
                        DropdownMenuItem(
                            text = { Text(deleteAllText) },
                            onClick = {
                                onDeleteAllClick()
                                moreMenuExpanded = false
                            },
                            modifier = Modifier.background(COLOR_ON_BACKGROUND),
                            colors = MenuDefaults.itemColors(textColor = Gray)
                        )
                    }
                }
            }
        }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        )
    )
}