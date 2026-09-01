package com.flatcode.simplecomposeapps.todoNote.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.flatcode.simplecomposeapps.todoNote.AddEditNoteViewModel
import com.flatcode.simplecomposeapps.ui.AppIcons

@Composable
fun AddEditNoteScreen(
    onBack: () -> Unit,
    viewModel: AddEditNoteViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.addEditNoteEvent.collect { event ->
            if (event is AddEditNoteViewModel.AddEditNoteEvent.NavigateBackWithResult) {
                onBack()
            }
        }
    }

    Scaffold(
        topBar = {
            TodoTopAppBar(
                title = if (viewModel.note != null) "Edit Note" else "New Note",
                onBack = onBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onSaveClick() },
                containerColor = Color(0xFF339999),
                contentColor = Color.White
            ) {
                Icon(imageVector = AppIcons.Check, contentDescription = "Save Note")
            }
        },
        containerColor = MaterialTheme.colorScheme.background
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
                placeholder = { Text("Title", color = Color.Gray) },
                label = { Text("Title") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = viewModel.noteContent,
                onValueChange = { viewModel.noteContent = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                placeholder = { Text("Content", color = Color.Gray) },
                label = { Text("Content") }
            )
        }
    }
}