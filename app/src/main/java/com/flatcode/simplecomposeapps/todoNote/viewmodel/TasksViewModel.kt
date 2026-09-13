package com.flatcode.simplecomposeapps.todoNote.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flatcode.simplecomposeapps.todoNote.data.SortOrder
import com.flatcode.simplecomposeapps.todoNote.data.Task
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
class TasksViewModel @Inject constructor(
    private val repository: TodoRepository,
    state: SavedStateHandle
) : ViewModel() {

    val searchQuery = MutableStateFlow(state.get<String>("searchQuery") ?: "")
    val preferencesFlow = repository.tasksPreferencesFlow

    private val taskEventChannel = Channel<TasksEvent>()
    val tasksEvent = taskEventChannel.receiveAsFlow()

    val tasks: StateFlow<List<Task>?> = combine(
        searchQuery,
        preferencesFlow
    ) { query, filterPreferences ->
        Pair(query, filterPreferences)
    }.flatMapLatest { (query, filterPreferences) ->
        repository.getTasks(query, filterPreferences.sortOrder, filterPreferences.hideCompleted)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    fun onSortOrderSelected(sortOrder: SortOrder) = viewModelScope.launch {
        repository.updateSortOrderTasks(sortOrder)
    }

    fun onHideCompletedClick(hideCompleted: Boolean) = viewModelScope.launch {
        repository.updateHideCompleted(hideCompleted)
    }

    fun onTaskSelected(task: Task) = viewModelScope.launch {
        taskEventChannel.send(TasksEvent.NavigateToEditTaskScreen(task))
    }

    fun onTaskCheckedChanged(task: Task, isChecked: Boolean) = viewModelScope.launch {
        repository.updateTask(task.copy(completed = isChecked))
    }

    fun onTaskSwiped(task: Task) = viewModelScope.launch {
        Timber.d("Task swiped for deletion: %d", task.id)
        repository.deleteTask(task)
        taskEventChannel.send(TasksEvent.ShowUndoDeleteTaskMessage(listOf(task)))
    }

    fun onUndoDeleteClick(tasks: List<Task>) = viewModelScope.launch {
        Timber.d("Restoring deleted tasks count: %d", tasks.size)
        repository.insertTasks(tasks)
    }

    fun onAddNewTaskClick() = viewModelScope.launch {
        taskEventChannel.send(TasksEvent.NavigateToAddTaskScreen)
    }

    fun onDeleteAllCompletedClick() = viewModelScope.launch {
        taskEventChannel.send(TasksEvent.NavigateToDeleteAllCompletedTasksScreen)
    }

    fun onConfirmDeleteAllCompletedClick() = viewModelScope.launch {
        Timber.d("Deleting all completed tasks")
        val completedTasks = repository.getCompletedTasksList()
        repository.deleteCompletedTasks()
        if (completedTasks.isNotEmpty()) {
            taskEventChannel.send(TasksEvent.ShowUndoDeleteTaskMessage(completedTasks))
        }
    }

    fun onAddEditResult(result: Int) = viewModelScope.launch {
        when (result) {
            DATA.ADD_RESULT_OK -> taskEventChannel.send(TasksEvent.ShowTaskSavedConfirmationMessage(Strings.MSG_TASK_ADDED))
            DATA.EDIT_RESULT_OK -> taskEventChannel.send(TasksEvent.ShowTaskSavedConfirmationMessage(Strings.MSG_TASK_UPDATED))
        }
    }

    sealed class TasksEvent {
        data object NavigateToAddTaskScreen : TasksEvent()
        data class NavigateToEditTaskScreen(val task: Task) : TasksEvent()
        data class ShowUndoDeleteTaskMessage(val tasks: List<Task>) : TasksEvent()
        data class ShowTaskSavedConfirmationMessage(val msg: String) : TasksEvent()
        data object NavigateToDeleteAllCompletedTasksScreen : TasksEvent()
    }
}