package com.flatcode.simplecomposeapps.todoNote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.todoNote.data.NoteDao
import com.flatcode.simplecomposeapps.todoNote.data.Notes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteDao: NoteDao
) : ViewModel() {

    val notes = noteDao.getAllNotes().asLiveData()

    private val _notesEvent = MutableSharedFlow<NotesEvent>()
    val notesEvent = _notesEvent.asSharedFlow()

    fun onNoteSelected(note: Notes) = viewModelScope.launch {
        _notesEvent.emit(NotesEvent.NavigateToEditNoteScreen(note))
    }

    fun onNoteSwiped(note: Notes) = viewModelScope.launch {
        noteDao.delete(note)
    }

    fun onAddNewNoteClick() = viewModelScope.launch {
        _notesEvent.emit(NotesEvent.NavigateToAddNoteScreen)
    }

    fun onConfirmClick() = viewModelScope.launch {
        noteDao.deleteAllNotes()
    }

    sealed class NotesEvent {
        data object NavigateToAddNoteScreen : NotesEvent()
        data class NavigateToEditNoteScreen(val note: Notes) : NotesEvent()
    }
}