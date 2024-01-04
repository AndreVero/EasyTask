package tasks.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.github.aakira.napier.Napier
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import tasks.domain.repository.TasksRepository

class TasksViewModel(
    private val tasksRepository: TasksRepository
) : ViewModel() {

    var state by mutableStateOf(TasksScreenState())
        private set

    private val _uiEvent = Channel<TasksUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init { getTasks() }

    private fun getTasks() {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            tasksRepository.getTodayTasks()
                .onSuccess { tasks ->
                    Napier.d { "Task success"}
                    state = state.copy(isLoading = false, tasks = tasks)
                }
                .onFailure {
                    _uiEvent.send(TasksUiEvent.ShowError("Error in Tasks loading"))
                }
        }
    }
}