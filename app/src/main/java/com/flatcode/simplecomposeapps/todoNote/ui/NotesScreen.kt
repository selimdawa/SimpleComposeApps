package com.flatcode.simplecomposeapps.todoNote.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.flatcode.simplecomposeapps.todoNote.data.Notes
import com.flatcode.simplecomposeapps.todoNote.viewmodel.NotesViewModel
import com.flatcode.simplecomposeapps.ui.theme.AppIcons
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.utils.DATA.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.utils.DATA.MC_TRACK
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun NotesScreen(
    navController: NavHostController,
    onBack: () -> Unit,
    onAddNote: () -> Unit,
    onEditNote: (Notes) -> Unit,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val notes by viewModel.notes.observeAsState(null)
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

    var showDeleteAllDialog by remember { mutableStateOf(false) }

    var showUndoBar by remember { mutableStateOf(false) }
    var undoNotes by remember { mutableStateOf<List<Notes>>(emptyList()) }
    var barMessage by remember { mutableStateOf("") }
    var isUndoOperation by remember { mutableStateOf(false) }

    var showCenteredToast by remember { mutableStateOf(false) }
    var toastMessage by remember { mutableStateOf("") }

    LaunchedEffect(showUndoBar) {
        if (showUndoBar) {
            delay(4.seconds)
            showUndoBar = false
        }
    }

    LaunchedEffect(showCenteredToast) {
        if (showCenteredToast) {
            delay(2.seconds)
            showCenteredToast = false
        }
    }

    LaunchedEffect(Unit) {
        viewModel.notesEvent.collect { event ->
            when (event) {
                is NotesViewModel.NotesEvent.NavigateToAddNoteScreen -> onAddNote()
                is NotesViewModel.NotesEvent.NavigateToEditNoteScreen -> onEditNote(event.note)
                is NotesViewModel.NotesEvent.ShowUndoDeleteNoteMessage -> {
                    undoNotes = event.notes
                    barMessage = if (event.notes.size > 1) Strings.MSG_ALL_NOTES_DELETED else Strings.MSG_NOTE_DELETED
                    isUndoOperation = true
                    showUndoBar = true
                }

                is NotesViewModel.NotesEvent.ShowNoteSavedConfirmationMessage -> {
                    toastMessage = event.msg
                    showCenteredToast = true
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

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
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
                    containerColor = MC_TRACK,
                    contentColor = Color.White,
                    shape = CircleShape,
                    elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 0.dp),
                    modifier = Modifier.padding(end = 25.dp)
                ) {
                    Icon(imageVector = AppIcons.Add, contentDescription = Strings.ADD_NOTE)
                }
            },
            containerColor = COLOR_ON_BACKGROUND
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                if (notes == null) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = MC_TRACK
                    )
                } else if (notes!!.isEmpty()) {
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
                        items(notes!!, key = { it.id }) { note ->
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

        TodoToast(
            isVisible = showUndoBar,
            message = barMessage,
            onUndo = if (isUndoOperation) {
                {
                    viewModel.onUndoDeleteClick(undoNotes)
                    showUndoBar = false
                }
            } else null,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp)
        )

        TodoToast(
            isVisible = showCenteredToast,
            message = toastMessage,
            isCentered = true,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp)
        )
    }
}