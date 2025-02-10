package com.example.trab1_todolist.ui.feature.addedit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trab1_todolist.data.TaskRepository
import com.example.trab1_todolist.ui.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AddEditViewModel(
    private val repository: TaskRepository
) : ViewModel() {
    var title by mutableStateOf("")
        private set

    var description by mutableStateOf<String?>(null)
        private set

    var startTime by mutableStateOf("")
        private set

    var endTime by mutableStateOf("")
        private set

    private var _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: AddEditEvent) {
        when(event) {
            is AddEditEvent.TitleChanged -> {
                title = event.title
            }
            is AddEditEvent.DescriptionChanged -> {
                description = event.description
            }
            is AddEditEvent.StartTimeChanged -> {
                startTime = event.startTime
            }
            is AddEditEvent.EndTimeChanged -> {
                endTime = event.endTime
            }
            AddEditEvent.Save -> {
                saveTask()
            }
        }
    }

    private fun saveTask() {
        viewModelScope.launch {
            if(title.isBlank()) {
                _uiEvent.send(UiEvent.ShowSnackbar("Título não pode estar vazio"))
                return@launch
            }
            if(startTime.isBlank()) {
                _uiEvent.send(UiEvent.ShowSnackbar("Hora de início não pode estar vazio"))
                return@launch
            }
            if(endTime.isBlank()) {
                _uiEvent.send(UiEvent.ShowSnackbar("Hora de fim não pode estar vazio"))
                return@launch
            }

            repository.insert(title, description, startTime, endTime)
            _uiEvent.send(UiEvent.NavigateBack)
        }
    }
}