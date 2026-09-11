package com.flatcode.simplecomposeapps.todoNote.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.todoNote.data.NoteDao
import com.flatcode.simplecomposeapps.todoNote.data.Notes
import com.flatcode.simplecomposeapps.todoNote.data.PreferencesManager
import com.flatcode.simplecomposeapps.todoNote.data.SortOrder
import com.flatcode.simplecomposeapps.ui.theme.Strings
import com.flatcode.simplecomposeapps.utils.DATA
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteDao: NoteDao,
    private val preferencesManager: PreferencesManager,
    state: SavedStateHandle
) : ViewModel() {

    val searchQuery = state.getLiveData("noteSearchQuery", "")

    private val notesEventChannel = Channel<NotesEvent>()
    val notesEvent = notesEventChannel.receiveAsFlow()

    val preferencesFlow = preferencesManager.notesPreferencesFlow

    private val notesFlow = combine(
        searchQuery.asFlow(),
        preferencesFlow
    ) { query, filterPreferences ->
        Pair(query, filterPreferences)
    }.flatMapLatest { (query, filterPreferences) ->
        noteDao.getNotes(query, filterPreferences.sortOrder)
    }

    val notes = notesFlow.asLiveData()

    fun onSortOrderSelected(sortOrder: SortOrder) = viewModelScope.launch {
        preferencesManager.updateSortOrderNotes(sortOrder)
    }

    fun onNoteSelected(note: Notes) = viewModelScope.launch {
        notesEventChannel.send(NotesEvent.NavigateToEditNoteScreen(note))
    }

    fun onNoteSwiped(note: Notes) = viewModelScope.launch {
        noteDao.delete(note)
        notesEventChannel.send(NotesEvent.ShowUndoDeleteNoteMessage(listOf(note)))
    }

    fun onUndoDeleteClick(notes: List<Notes>) = viewModelScope.launch {
        noteDao.insertAll(notes)
    }

    fun onAddNewNoteClick() = viewModelScope.launch {
        notesEventChannel.send(NotesEvent.NavigateToAddNoteScreen)
    }

    fun onDeleteAllClick() = viewModelScope.launch {
        notesEventChannel.send(NotesEvent.ShowDeleteAllConfirmationDialog)
    }

    fun onConfirmDeleteAllClick() = viewModelScope.launch {
        val allNotes = noteDao.getAllNotesList()
        noteDao.deleteAllNotes()
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