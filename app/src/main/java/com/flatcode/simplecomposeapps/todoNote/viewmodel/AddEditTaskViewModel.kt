package com.flatcode.simplecomposeapps.todoNote.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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

    var taskName by mutableStateOf(state.get<String>("taskName") ?: task?.name ?: "")
    var taskImportant by mutableStateOf(state.get<Boolean>("taskImportant") ?: task?.important ?: false)

    val addEditTaskEvent: SharedFlow<AddEditTaskEvent>
        field = MutableSharedFlow<AddEditTaskEvent>()

    fun onSaveClick() {
        if (taskName.isBlank()) {
            return
        }

        if (task != null) {
            val updatedTask = task.copy(name = taskName, important = taskImportant)
            updateTask(updatedTask)
        } else {
            val newTask = Task(name = taskName, important = taskImportant)
            createTask(newTask)
        }
    }

    private fun createTask(task: Task) = viewModelScope.launch {
        taskDao.insert(task)
        addEditTaskEvent.emit(AddEditTaskEvent.NavigateBackWithResult(DATA.ADD_RESULT_OK))
    }

    private fun updateTask(task: Task) = viewModelScope.launch {
        taskDao.update(task)
        addEditTaskEvent.emit(AddEditTaskEvent.NavigateBackWithResult(DATA.EDIT_RESULT_OK))
    }

    sealed class AddEditTaskEvent {
        data class NavigateBackWithResult(val result: Int) : AddEditTaskEvent()
    }
}