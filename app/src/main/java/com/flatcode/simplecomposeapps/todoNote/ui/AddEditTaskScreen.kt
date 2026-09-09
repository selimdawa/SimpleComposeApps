package com.flatcode.simplecomposeapps.todoNote.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.flatcode.simplecomposeapps.todoNote.viewmodel.AddEditTaskViewModel
import com.flatcode.simplecomposeapps.ui.theme.AppIcons
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ERROR
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.ui.theme.Gray
import com.flatcode.simplecomposeapps.ui.theme.Strings

@Composable
fun AddEditTaskScreen(
    onBack: (Int?) -> Unit, viewModel: AddEditTaskViewModel = hiltViewModel()
) {
    val taskName by viewModel.taskName.observeAsState("")
    val taskImportant by viewModel.taskImportant.observeAsState(false)

    LaunchedEffect(Unit) {
        viewModel.addEditTaskEvent.collect { event ->
            if (event is AddEditTaskViewModel.AddEditTaskEvent.NavigateBackWithResult) {
                onBack(event.result)
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(), topBar = {
        AddEditTopAppBar(
            title = if (viewModel.task != null) Strings.TITLE_EDIT_TASK else Strings.TITLE_NEW_TASK,
            onBack = { onBack(null) })
    }, floatingActionButton = {
        FloatingActionButton(
            onClick = { viewModel.onSaveClick() },
            containerColor = COLOR_ON_BACKGROUND,
            contentColor = COLOR_ERROR,
            modifier = Modifier.padding(25.dp)
        ) {
            Icon(imageVector = AppIcons.Check, contentDescription = Strings.ADD_TASK)
        }
    }, containerColor = COLOR_ON_BACKGROUND
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(15.dp)
        ) {
            OutlinedTextField(
                value = taskName,
                onValueChange = { viewModel.taskName.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                label = { Text(Strings.TASK, color = COLOR_ERROR) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = COLOR_ERROR,
                    unfocusedTextColor = COLOR_ERROR,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedLabelColor = COLOR_ERROR,
                    unfocusedLabelColor = COLOR_ERROR,
                    focusedBorderColor = COLOR_ERROR,
                    unfocusedBorderColor = Gray,
                )
            )

            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = taskImportant,
                    onCheckedChange = { viewModel.taskImportant.value = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = COLOR_ERROR,
                        uncheckedColor = COLOR_ERROR,
                        checkmarkColor = COLOR_ON_BACKGROUND
                    )
                )
                Text(
                    text = Strings.IMPORTANT_TASK,
                    color = COLOR_ERROR,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            viewModel.task?.let { task ->
                Text(
                    text = Strings.dateCreated(task.createdDateFormatted),
                    color = COLOR_ERROR,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(15.dp)
                )
            }
        }
    }
}