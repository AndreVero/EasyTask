package home.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.launch
import home.domain.repository.TaskRepository
import io.github.aakira.napier.Napier

class HomeViewModel(
    private val taskRepository: TaskRepository
) : ViewModel() {

    var state by mutableStateOf(MainState())
        private set

    init {
        getTasks()
    }

    private fun getTasks() {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            taskRepository.getTasks()
                .onSuccess { tasks ->
                    Napier.d { "Task success"}
                    state = state.copy(isLoading = false, tasks = tasks)
                }
                .onFailure {

                }
        }
    }

}