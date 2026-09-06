package com.flatcode.simplecomposeapps.todoNote.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.flatcode.simplecomposeapps.todoNote.data.Notes
import com.flatcode.simplecomposeapps.todoNote.viewmodel.NotesViewModel
import com.flatcode.simplecomposeapps.ui.AppIcons
import com.flatcode.simplecomposeapps.ui.theme.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.ui.theme.Strings

import androidx.navigation.NavHostController

@Composable
fun NotesScreen(
    navController: NavHostController,
    onBack: () -> Unit,
    onAddNote: () -> Unit,
    onEditNote: (Notes) -> Unit,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val notes by viewModel.notes.observeAsState(emptyList())
    val searchQuery by viewModel.searchQuery.observeAsState("")
    val resultState = navController.currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<Int>("add_edit_result")
        ?.observeAsState()
    val result = resultState?.value

    LaunchedEffect(result) {
        result?.let {
            viewModel.onAddEditNoteResult(it)
            navController.currentBackStackEntry?.savedStateHandle?.remove<Int>("add_edit_result")
        }
    }

    val snackbarHostState = remember { SnackbarHostState() }
    var showDeleteAllDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.notesEvent.collect { event ->
            when (event) {
                is NotesViewModel.NotesEvent.NavigateToAddNoteScreen -> onAddNote()
                is NotesViewModel.NotesEvent.NavigateToEditNoteScreen -> onEditNote(event.note)
                is NotesViewModel.NotesEvent.ShowUndoDeleteNoteMessage -> {
                    val result = snackbarHostState.showSnackbar(
                        message = Strings.MSG_NOTE_DELETED,
                        actionLabel = Strings.UNDO
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onUndoDeleteClick(event.note)
                    }
                }
                is NotesViewModel.NotesEvent.ShowNoteSavedConfirmationMessage -> {
                    snackbarHostState.showSnackbar(event.msg)
                }
                is NotesViewModel.NotesEvent.ShowDeleteAllConfirmationDialog -> {
                    showDeleteAllDialog = true
                }
            }
        }
    }

    if (showDeleteAllDialog) {
        TodoDeleteDialog(
            message = Strings.DIALOG_DELETE_MESSAGE_NOTES,
            onConfirm = {
                viewModel.onConfirmDeleteAllClick()
                showDeleteAllDialog = false
            },
            onDismiss = { showDeleteAllDialog = false }
        )
    }

    Scaffold(
        topBar = {
            TodoTopAppBar(
                title = Strings.NOTES,
                onBack = onBack,
                onSearchQueryChange = { viewModel.searchQuery.value = it },
                searchQuery = searchQuery,
                onSortOrderSelected = { viewModel.onSortOrderSelected(it) },
                onDeleteAllClick = { viewModel.onDeleteAllClick() },
                deleteAllText = Strings.MSG_ALL_NOTES_DELETED,
                hasBack = false
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onAddNewNoteClick() },
                containerColor = Color(0xFF339999),
                contentColor = Color.White
            ) {
                Icon(imageVector = AppIcons.Add, contentDescription = Strings.ADD_NOTE)
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = COLOR_ON_BACKGROUND
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (notes.isEmpty()) {
                Text(
                    text = Strings.NO_NOTES_FOUND,
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White
                )
            } else {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(notes, key = { it.id }) { note ->
                        NoteItem(
                            note = note,
                            onDeleteClick = { viewModel.onNoteSwiped(note) },
                            modifier = Modifier.clickable { viewModel.onNoteSelected(note) }
                        )
                    }
                }
            }
        }
    }
}