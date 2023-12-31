package home.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.launch
import home.domain.repository.GoalRepository
import io.github.aakira.napier.Napier
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class HomeViewModel(
    private val goalRepository: GoalRepository
) : ViewModel() {

    var state by mutableStateOf(HomeState())
        private set

    private val _uiEvent = Channel<HomeUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init { getGoals() }

    private fun getGoals() {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            goalRepository.getGoals()
                .onSuccess { tasks ->
                    Napier.d { "Task success"}
                    state = state.copy(isLoading = false, goals = tasks)
                }
                .onFailure {
                    _uiEvent.send(HomeUiEvent.ShowError("Error in Goals loading"))
                }
        }
    }

}