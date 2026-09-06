package com.flatcode.simplecomposeapps.todoNote.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.flatcode.simplecomposeapps.todoNote.viewmodel.AddEditNoteViewModel
import com.flatcode.simplecomposeapps.ui.AppIcons
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ON_BACKGROUND

import com.flatcode.simplecomposeapps.ui.theme.Strings

@Composable
fun AddEditNoteScreen(
    onBack: (Int?) -> Unit,
    viewModel: AddEditNoteViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.addEditNoteEvent.collect { event ->
            if (event is AddEditNoteViewModel.AddEditNoteEvent.NavigateBackWithResult) {
                onBack(event.result)
            }
        }
    }

    Scaffold(
        topBar = {
            TodoTopAppBar(
                title = if (viewModel.note != null) Strings.TITLE_EDIT_NOTE else Strings.TITLE_NEW_NOTE,
                onBack = { onBack(null) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onSaveClick() },
                containerColor = Color(0xFF339999),
                contentColor = Color.White
            ) {
                Icon(imageVector = AppIcons.Check, contentDescription = Strings.ADD_NOTE)
            }
        },
        containerColor = COLOR_ON_BACKGROUND
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = viewModel.noteTitle,
                onValueChange = { viewModel.noteTitle = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(Strings.TITLE, color = Color.Gray) },
                label = { Text(Strings.TITLE) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = viewModel.noteContent,
                onValueChange = { viewModel.noteContent = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                placeholder = { Text(Strings.CONTENT, color = Color.Gray) },
                label = { Text(Strings.CONTENT) }
            )
        }
    }
}