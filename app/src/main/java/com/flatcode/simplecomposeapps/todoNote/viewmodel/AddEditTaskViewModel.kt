package com.flatcode.simplecomposeapps.todoNote.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.todoNote.data.Task
import com.flatcode.simplecomposeapps.todoNote.data.TaskDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

import com.flatcode.simplecomposeapps.utils.DATA

@HiltViewModel
class AddEditTaskViewModel @Inject constructor(
    private val taskDao: TaskDao,
    state: SavedStateHandle
) : ViewModel() {

    val task = state.get<Task>("task")

    val taskName = state.getLiveData("taskName", task?.name ?: "")
    val taskImportant = state.getLiveData("taskImportant", task?.important ?: false)

    private val _addEditTaskEvent = MutableSharedFlow<AddEditTaskEvent>()
    val addEditTaskEvent: SharedFlow<AddEditTaskEvent> = _addEditTaskEvent

    fun onSaveClick() {
        val name = taskName.value ?: ""
        val important = taskImportant.value ?: false
        if (name.isBlank()) {
            return
        }

        if (task != null) {
            val updatedTask = task.copy(name = name, important = important)
            updateTask(updatedTask)
        } else {
            val newTask = Task(name = name, important = important)
            createTask(newTask)
        }
    }

    private fun createTask(task: Task) = viewModelScope.launch {
        taskDao.insert(task)
        _addEditTaskEvent.emit(AddEditTaskEvent.NavigateBackWithResult(DATA.ADD_RESULT_OK))
    }

    private fun updateTask(task: Task) = viewModelScope.launch {
        taskDao.update(task)
        _addEditTaskEvent.emit(AddEditTaskEvent.NavigateBackWithResult(DATA.EDIT_RESULT_OK))
    }

    sealed class AddEditTaskEvent {
        data class NavigateBackWithResult(val result: Int) : AddEditTaskEvent()
    }
}