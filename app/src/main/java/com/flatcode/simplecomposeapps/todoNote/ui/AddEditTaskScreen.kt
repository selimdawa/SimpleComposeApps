package com.flatcode.simplecomposeapps.todoNote.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import com.flatcode.simplecomposeapps.todoNote.viewmodel.AddEditTaskViewModel
import com.flatcode.simplecomposeapps.ui.AppIcons
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ERROR
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ON_BACKGROUND

import com.flatcode.simplecomposeapps.ui.theme.Strings

@Composable
fun AddEditTaskScreen(
    onBack: (Int?) -> Unit,
    viewModel: AddEditTaskViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.addEditTaskEvent.collect { event ->
            if (event is AddEditTaskViewModel.AddEditTaskEvent.NavigateBackWithResult) {
                onBack(event.result)
            }
        }
    }

    Scaffold(
        topBar = {
            TodoTopAppBar(
                title = if (viewModel.task != null) Strings.TITLE_EDIT_TASK else Strings.TITLE_NEW_TASK,
                onBack = { onBack(null) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onSaveClick() },
                containerColor = Color(0xFF339999),
                contentColor = Color.White
            ) {
                Icon(imageVector = AppIcons.Check, contentDescription = Strings.ADD_TASK)
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
                value = viewModel.taskName,
                onValueChange = { viewModel.taskName = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(Strings.NAME.replace(" :", ""), color = Color.Gray) },
                label = { Text(Strings.NAME.replace(" :", "")) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = viewModel.taskImportant,
                    onCheckedChange = { viewModel.taskImportant = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = COLOR_ERROR,
                        uncheckedColor = COLOR_ERROR,
                        checkmarkColor = COLOR_ON_BACKGROUND
                    )
                )
                Text(text = Strings.IMPORTANT_TASK, color = Color.White)
            }
        }
    }
}