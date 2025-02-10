package com.example.trab1_todolist.ui.feature.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trab1_todolist.data.TaskDatabaseProvider
import com.example.trab1_todolist.data.TaskRepositoryImpl
import com.example.trab1_todolist.ui.components.TaskComponent
import com.example.trab1_todolist.domain.Task
import com.example.trab1_todolist.domain.taskList
import com.example.trab1_todolist.navigation.AddEditRoute
import com.example.trab1_todolist.ui.UiEvent
import com.example.trab1_todolist.ui.components.ProfileHeaderComponent
import com.example.trab1_todolist.ui.components.WelcomeMessageComponent
import com.example.trab1_todolist.ui.theme.Trab1_ToDoListTheme

@Composable
fun ListScreen(
    navigateToAddEditScreen: (id: Long?) -> Unit
) {
    val context = LocalContext.current.applicationContext
    val database = TaskDatabaseProvider.provide(context)
    val repository = TaskRepositoryImpl(dao = database.taskDao)
    val viewModel = viewModel<ListViewModel> {
        ListViewModel(repository = repository)
    }

    val tasks by viewModel.tasks.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { uiEvent ->
            when(uiEvent) {
                is UiEvent.Navigate<*> -> {
                    when(uiEvent.route) {
                        is AddEditRoute -> {
                            navigateToAddEditScreen(uiEvent.route.id)
                        }
                    }
                }
                UiEvent.NavigateBack -> {  }
                is UiEvent.ShowSnackbar -> {  }
            }
        }
    }

    ListContent(
        tasks = tasks,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun ListContent(
    tasks: List<Task>,
    onEvent: (ListEvent) -> Unit
) {
    var selectedScreen by remember { mutableStateOf(1) }
    var screens = listOf("Calendar", "Home", "Notifications")
    val pendingTasks = tasks.count { !it.isCompleted }

    Scaffold(
        bottomBar = {
            NavigationBar(
                modifier = Modifier.height(90.dp),
                containerColor = Color.White,
                tonalElevation = 0.dp
            ) {
                screens.forEachIndexed { index, _ ->
                    val icon: ImageVector = when(index) {
                        0 -> Icons.Filled.DateRange
                        1 -> Icons.Filled.Home
                        2 -> Icons.Filled.Email
                        else -> Icons.Filled.Home
                    }
                    NavigationBarItem(
                        selected = selectedScreen == index,
                        onClick = { selectedScreen = index },
                        icon = {
                            Box(
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(CircleShape)
                                    .background(if (selectedScreen == index) Color.Black else Color.White),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = "Screen",
                                    modifier = Modifier
                                        .size(50.dp)
                                        .padding(12.dp),
                                    tint = if(selectedScreen == index) Color.White else Color.Black
                                )
                            }
                        }
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(ListEvent.AddEdit(null))
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .consumeWindowInsets(it),
            contentPadding = PaddingValues(
                start = 16.dp,
                top = 16.dp,
                bottom = 16.dp
            )
        ) {
            item {
                ProfileHeaderComponent()
            }
            item {
                Spacer(modifier = Modifier.height(30.dp))

                WelcomeMessageComponent(pendingTasks = pendingTasks)

                Spacer(modifier = Modifier.height(30.dp))
            }
            items(tasks) { task ->
                TaskComponent(
                    task = task,
                    onCompletedChange = {
                        onEvent(ListEvent.CompleteChanged(task.id, it))
                    },
                    onItemClick = {
                        onEvent(ListEvent.AddEdit(task.id))
                    },
                    onDeleteClick = {
                        onEvent(ListEvent.Delete(task.id))
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Preview
@Composable
private fun ListContentPreview() {
    Trab1_ToDoListTheme {
        ListContent(
            tasks = taskList,
            onEvent = {}
        )
    }
}