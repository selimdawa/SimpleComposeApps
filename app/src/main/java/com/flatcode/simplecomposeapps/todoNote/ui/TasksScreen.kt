package com.flatcode.simplecomposeapps.todoNote.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.flatcode.simplecomposeapps.todoNote.data.Task
import com.flatcode.simplecomposeapps.todoNote.viewmodel.TasksViewModel
import com.flatcode.simplecomposeapps.ui.theme.AppIcons
import com.flatcode.simplecomposeapps.utils.DATA.COLOR_ON_BACKGROUND
import com.flatcode.simplecomposeapps.utils.DATA.MC_TRACK
import com.flatcode.simplecomposeapps.ui.theme.Strings
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.drop
import kotlin.time.Duration.Companion.seconds

@Composable
fun TasksScreen(
    navController: NavHostController,
    onBack: () -> Unit,
    onAddTask: () -> Unit,
    onEditTask: (Task) -> Unit,
    viewModel: TasksViewModel = hiltViewModel()
) {
    val tasks by viewModel.tasks.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val preferences by viewModel.preferencesFlow.collectAsStateWithLifecycle(initialValue = null)
    val result by navController.currentBackStackEntry
        ?.savedStateHandle
        ?.getStateFlow<Int?>("add_edit_result", null)
        ?.collectAsStateWithLifecycle() ?: remember { mutableStateOf(null) }

    LaunchedEffect(result) {
        result?.let {
            viewModel.onAddEditResult(it)
            navController.currentBackStackEntry?.savedStateHandle?.remove<Int>("add_edit_result")
        }
    }

    var showDeleteAllDialog by remember { mutableStateOf(false) }

    var showUndoBar by remember { mutableStateOf(false) }
    var undoTasks by remember { mutableStateOf<List<Task>>(emptyList()) }
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
        viewModel.tasksEvent.collect { event ->
            when (event) {
                is TasksViewModel.TasksEvent.NavigateToAddTaskScreen -> onAddTask()
                is TasksViewModel.TasksEvent.NavigateToEditTaskScreen -> onEditTask(event.task)
                is TasksViewModel.TasksEvent.ShowUndoDeleteTaskMessage -> {
                    undoTasks = event.tasks
                    barMessage = if (event.tasks.size > 1) Strings.MSG_COMPLETED_TASKS_DELETED else Strings.MSG_TASK_DELETED
                    isUndoOperation = true
                    showUndoBar = true
                }

                is TasksViewModel.TasksEvent.ShowTaskSavedConfirmationMessage -> {
                    toastMessage = event.msg
                    showCenteredToast = true
                }

                is TasksViewModel.TasksEvent.NavigateToDeleteAllCompletedTasksScreen -> {
                    showDeleteAllDialog = true
                }
            }
        }
    }

    if (showDeleteAllDialog) {
        TodoDeleteDialog(
            message = Strings.DIALOG_DELETE_MESSAGE_TASKS,
            onConfirm = {
                viewModel.onConfirmDeleteAllCompletedClick()
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
                    title = Strings.TASKS,
                    onBack = onBack,
                    onSearchQueryChange = { viewModel.searchQuery.value = it },
                    searchQuery = searchQuery,
                    onSortOrderSelected = { viewModel.onSortOrderSelected(it) },
                    showHideCompleted = true,
                    hideCompleted = preferences?.hideCompleted ?: false,
                    onHideCompletedClick = { viewModel.onHideCompletedClick(it) },
                    onDeleteAllClick = { viewModel.onDeleteAllCompletedClick() },
                    deleteAllText = Strings.DELETE_COMPLETED_TASKS,
                    hasBack = false
                )
            }, floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onAddNewTaskClick() },
                containerColor = MC_TRACK,
                contentColor = Color.White,
                shape = CircleShape,
                elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 0.dp),
                modifier = Modifier.padding(end = 25.dp)
            ) {
                Icon(imageVector = AppIcons.Add, contentDescription = Strings.ADD_TASK)
            }
        }, containerColor = COLOR_ON_BACKGROUND
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                if (tasks == null) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = MC_TRACK
                    )
                } else if (tasks!!.isEmpty()) {
                    Text(
                        text = Strings.NO_TASKS_FOUND,
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.White
                    )
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(tasks!!, key = { it.id }) { task ->
                            val dismissState = rememberSwipeToDismissBoxState()

                            LaunchedEffect(task) {
                                snapshotFlow { dismissState.currentValue }
                                    .drop(1)
                                    .collect { value ->
                                        if (value != SwipeToDismissBoxValue.Settled) {
                                            viewModel.onTaskSwiped(task)
                                        }
                                    }
                            }

                            LaunchedEffect(task) {
                                if (dismissState.currentValue != SwipeToDismissBoxValue.Settled) {
                                    dismissState.snapTo(SwipeToDismissBoxValue.Settled)
                                }
                            }

                            SwipeToDismissBox(state = dismissState, backgroundContent = {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color.Transparent)
                                        .padding(horizontal = 20.dp)
                                )
                            }, content = {
                                TaskItem(
                                    task = task,
                                    onCheckedChange = { viewModel.onTaskCheckedChanged(task, it) },
                                    modifier = Modifier.clickable { viewModel.onTaskSelected(task) })
                            })
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
                    viewModel.onUndoDeleteClick(undoTasks)
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