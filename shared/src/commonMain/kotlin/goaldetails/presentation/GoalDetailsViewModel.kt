package goaldetails.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import goaldetails.domain.repository.GoalDetailsRepository
import io.github.aakira.napier.Napier
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class GoalDetailsViewModel(
    private val goalDetailsRepository: GoalDetailsRepository
) : ViewModel() {

    var state by mutableStateOf(GoalDetailsScreenState())
        private set

    private val _uiEvent = Channel<GoalDetailsUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun getTasks(id: String) {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            goalDetailsRepository.getGoalDetails(id)
                .onSuccess { goalDetails ->
                    Napier.d { "Task success"}
                    state = state.copy(isLoading = false, goalDetails = goalDetails)
                }
                .onFailure {
                    _uiEvent.send(GoalDetailsUiEvent.ShowError("Error in Tasks loading"))
                }
        }
    }
}