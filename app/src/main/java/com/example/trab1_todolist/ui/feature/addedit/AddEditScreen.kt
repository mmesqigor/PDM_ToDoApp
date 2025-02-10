package com.example.trab1_todolist.ui.feature.addedit

import android.media.MediaDrm.OnEventListener
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trab1_todolist.data.TaskDatabase
import com.example.trab1_todolist.data.TaskDatabaseProvider
import com.example.trab1_todolist.data.TaskRepository
import com.example.trab1_todolist.data.TaskRepositoryImpl
import com.example.trab1_todolist.ui.UiEvent
import com.example.trab1_todolist.ui.theme.Trab1_ToDoListTheme

@Composable
fun AddEditScreen(
    navigateBack: () -> Unit,
) {
    val context = LocalContext.current.applicationContext
    val database = TaskDatabaseProvider.provide(context)
    val repository = TaskRepositoryImpl(dao = database.taskDao)
    val viewModel = viewModel<AddEditViewModel> {
        AddEditViewModel(repository = repository)
    }

    val title = viewModel.title
    val description = viewModel.description
    val startTime = viewModel.startTime
    val endTime = viewModel.endTime

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { uiEvent ->
            when(uiEvent) {
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = uiEvent.message
                    )
                }
                UiEvent.NavigateBack -> {
                    navigateBack()
                }
                is UiEvent.Navigate<*> -> {

                }
            }
        }
    }

    AddEditContent(
        title = title,
        description = description,
        startTime = startTime,
        endTime = endTime,
        snackbarHostState = snackbarHostState,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun AddEditContent(
    title: String,
    description: String?,
    startTime: String,
    endTime: String,
    snackbarHostState: SnackbarHostState,
    onEvent: (AddEditEvent) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEvent(AddEditEvent.Save)
                }
            ) {
                Icon(Icons.Default.Check, contentDescription = "Save")
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .consumeWindowInsets(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = title,
                onValueChange = {
                    onEvent(
                        AddEditEvent.TitleChanged(it)
                    )
                },
                placeholder = {
                    Text(text = "Título")
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = description ?: "",
                onValueChange = {
                    onEvent(
                        AddEditEvent.DescriptionChanged(it)
                    )
                },
                placeholder = {
                    Text(text = "Descrição (opcional)")
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = startTime,
                onValueChange = {
                    onEvent(
                        AddEditEvent.StartTimeChanged(it)
                    )
                },
                placeholder = {
                    Text(text = "Hora início")
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = endTime,
                onValueChange = {
                    onEvent(
                        AddEditEvent.EndTimeChanged(it)
                    )
                },
                placeholder = {
                    Text(text = "Hora fim")
                }
            )
        }
    }
}

@Preview
@Composable
private fun AddEditScreenPreview() {
    Trab1_ToDoListTheme {
        AddEditContent(
            title = "",
            description = null,
            startTime = "",
            endTime = "",
            snackbarHostState = SnackbarHostState(),
            onEvent = {}
        )
    }
}