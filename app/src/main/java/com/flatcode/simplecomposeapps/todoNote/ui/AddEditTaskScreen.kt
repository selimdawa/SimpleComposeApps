package com.flatcode.simplecomposeapps.todoNote.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.flatcode.simplecomposeapps.todoNote.viewmodel.AddEditTaskViewModel
import com.flatcode.simplecomposeapps.ui.theme.AppIcons
import com.flatcode.simplecomposeapps.ui.theme.Gray
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.utils.DATA.COLOR_ERROR
import com.flatcode.simplecomposeapps.utils.DATA.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.utils.DATA.MC_TRACK

@Composable
fun AddEditTaskScreen(
    onBack: (Int?) -> Unit, viewModel: AddEditTaskViewModel = hiltViewModel()
) {
    val taskName by viewModel.taskName.collectAsStateWithLifecycle()
    val taskImportant by viewModel.taskImportant.collectAsStateWithLifecycle()

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
                title = if (viewModel.isEditMode) Strings.TITLE_EDIT_TASK else Strings.TITLE_NEW_TASK,
                onBack = { onBack(null) })
        }, floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onSaveClick() },
                containerColor = COLOR_ON_BACKGROUND,
                contentColor = Color.White,
                shape = CircleShape,
                elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 0.dp),
                modifier = Modifier.padding(25.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .background(MC_TRACK, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = AppIcons.Check, contentDescription = Strings.ADD_TASK)
                }
            }
        }, containerColor = COLOR_ON_BACKGROUND
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
        ) {
            OutlinedTextField(
                value = taskName,
                onValueChange = { viewModel.updateTaskName(it) },
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
                    onCheckedChange = { viewModel.updateTaskImportance(it) },
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
                Row(
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = AppIcons.DateRange,
                        contentDescription = null,
                        tint = COLOR_ERROR
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "Created: ${task.createdDateFormatted}",
                        color = COLOR_ERROR,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}