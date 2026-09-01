package com.flatcode.simplecomposeapps.todoNote.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.flatcode.simplecomposeapps.todoNote.AddEditTaskViewModel
import com.flatcode.simplecomposeapps.ui.AppIcons

@Composable
fun AddEditTaskScreen(
    onBack: () -> Unit,
    viewModel: AddEditTaskViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.addEditTaskEvent.collect { event ->
            if (event is AddEditTaskViewModel.AddEditTaskEvent.NavigateBackWithResult) {
                onBack()
            }
        }
    }

    Scaffold(
        topBar = {
            TodoTopAppBar(
                title = if (viewModel.task != null) "Edit Task" else "New Task",
                onBack = onBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onSaveClick() },
                containerColor = Color(0xFF339999),
                contentColor = Color.White
            ) {
                Icon(imageVector = AppIcons.Check, contentDescription = "Save Task")
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
                value = viewModel.taskName,
                onValueChange = { viewModel.taskName = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Task name", color = Color.Gray) },
                label = { Text("Task name") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = viewModel.taskImportant,
                    onCheckedChange = { viewModel.taskImportant = it }
                )
                Text(text = "Important Task", color = Color.White)
            }
        }
    }
}