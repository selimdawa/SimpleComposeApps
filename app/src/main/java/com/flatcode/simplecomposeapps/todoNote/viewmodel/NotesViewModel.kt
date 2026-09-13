package com.flatcode.simplecomposeapps.todoNote.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.todoNote.data.Notes
import com.flatcode.simplecomposeapps.todoNote.data.SortOrder
import com.flatcode.simplecomposeapps.todoNote.data.TodoRepository
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.utils.DATA
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class NotesViewModel @Inject constructor(
    private val repository: TodoRepository,
    state: SavedStateHandle
) : ViewModel() {

    val searchQuery = MutableStateFlow(state.get<String>("noteSearchQuery") ?: "")

    private val notesEventChannel = Channel<NotesEvent>()
    val notesEvent = notesEventChannel.receiveAsFlow()

    val preferencesFlow = repository.notesPreferencesFlow

    val notes: StateFlow<List<Notes>?> = combine(
        searchQuery,
        preferencesFlow
    ) { query, filterPreferences ->
        Pair(query, filterPreferences)
    }.flatMapLatest { (query, filterPreferences) ->
        repository.getNotes(query, filterPreferences.sortOrder)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    fun onSortOrderSelected(sortOrder: SortOrder) = viewModelScope.launch {
        repository.updateSortOrderNotes(sortOrder)
    }

    fun onNoteSelected(note: Notes) = viewModelScope.launch {
        notesEventChannel.send(NotesEvent.NavigateToEditNoteScreen(note))
    }

    fun onNoteSwiped(note: Notes) = viewModelScope.launch {
        Timber.d("Note swiped for deletion: %d", note.id)
        repository.deleteNote(note)
        notesEventChannel.send(NotesEvent.ShowUndoDeleteNoteMessage(listOf(note)))
    }

    fun onUndoDeleteClick(notes: List<Notes>) = viewModelScope.launch {
        Timber.d("Restoring deleted notes count: %d", notes.size)
        repository.insertNotes(notes)
    }

    fun onAddNewNoteClick() = viewModelScope.launch {
        notesEventChannel.send(NotesEvent.NavigateToAddNoteScreen)
    }

    fun onDeleteAllClick() = viewModelScope.launch {
        notesEventChannel.send(NotesEvent.ShowDeleteAllConfirmationDialog)
    }

    fun onConfirmDeleteAllClick() = viewModelScope.launch {
        Timber.d("Deleting all notes")
        val allNotes = repository.getAllNotesList()
        repository.deleteAllNotes()
        if (allNotes.isNotEmpty()) {
            notesEventChannel.send(NotesEvent.ShowUndoDeleteNoteMessage(allNotes))
        }
    }

    fun onAddEditNoteResult(result: Int) = viewModelScope.launch {
        when (result) {
            DATA.ADD_RESULT_OK -> notesEventChannel.send(NotesEvent.ShowNoteSavedConfirmationMessage(Strings.MSG_NOTE_ADDED))
            DATA.EDIT_RESULT_OK -> notesEventChannel.send(NotesEvent.ShowNoteSavedConfirmationMessage(Strings.MSG_NOTE_UPDATED))
        }
    }

    sealed class NotesEvent {
        data object NavigateToAddNoteScreen : NotesEvent()
        data class NavigateToEditNoteScreen(val note: Notes) : NotesEvent()
        data class ShowUndoDeleteNoteMessage(val notes: List<Notes>) : NotesEvent()
        data class ShowNoteSavedConfirmationMessage(val msg: String) : NotesEvent()
        data object ShowDeleteAllConfirmationDialog : NotesEvent()
    }
}