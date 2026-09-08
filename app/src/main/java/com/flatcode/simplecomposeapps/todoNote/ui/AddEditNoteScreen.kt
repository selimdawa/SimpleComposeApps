package com.flatcode.simplecomposeapps.todoNote.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.flatcode.simplecomposeapps.todoNote.viewmodel.AddEditNoteViewModel
import com.flatcode.simplecomposeapps.ui.AppIcons
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ERROR
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.ui.theme.MC_TRACK

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
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AddEditTopAppBar(
                title = if (viewModel.note != null) Strings.TITLE_EDIT_NOTE else Strings.TITLE_NEW_NOTE,
                onBack = { onBack(null) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onSaveClick() },
                containerColor = COLOR_ON_BACKGROUND
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .background(MC_TRACK, CircleShape)
                        .padding(15.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = AppIcons.Check,
                        contentDescription = Strings.ADD_NOTE,
                        tint = Color.White
                    )
                }
            }
        },
        containerColor = COLOR_ON_BACKGROUND
    ) { paddingValues ->
        val textFieldColors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = COLOR_ERROR,
            unfocusedBorderColor = COLOR_ERROR,
            focusedLabelColor = COLOR_ERROR,
            unfocusedLabelColor = COLOR_ERROR,
            cursorColor = COLOR_ERROR,
            focusedTextColor = COLOR_ERROR,
            unfocusedTextColor = COLOR_ERROR,
            focusedPlaceholderColor = COLOR_ERROR,
            unfocusedPlaceholderColor = COLOR_ERROR
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(10.dp)
        ) {
            OutlinedTextField(
                value = viewModel.noteTitle,
                onValueChange = { viewModel.noteTitle = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(Strings.TITLE) },
                label = { Text(Strings.TITLE) },
                colors = textFieldColors,
                textStyle = TextStyle(fontSize = 18.sp)
            )

            Spacer(modifier = Modifier.height(5.dp))

            OutlinedTextField(
                value = viewModel.noteContent,
                onValueChange = { viewModel.noteContent = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                placeholder = { Text(Strings.CONTENT) },
                label = { Text(Strings.CONTENT) },
                colors = textFieldColors,
                minLines = 15,
                textStyle = TextStyle(fontSize = 18.sp)
            )

            Spacer(modifier = Modifier.height(30.dp))

            Row(
                modifier = Modifier.padding(start = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = AppIcons.DateRange,
                    contentDescription = null,
                    tint = COLOR_ERROR
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = viewModel.note?.dateCreatedFormatted ?: "",
                    color = COLOR_ERROR,
                    fontSize = 18.sp
                )
            }
        }
    }
}
