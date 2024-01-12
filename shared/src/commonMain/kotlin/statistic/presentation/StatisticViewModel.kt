package statistic.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.github.aakira.napier.Napier
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import statistic.domain.repository.StatisticRepository

class StatisticViewModel(
    private val statisticRepository: StatisticRepository
) : ViewModel() {
    var state by mutableStateOf(StatisticUiState())
        private set

    private val _uiEvent = Channel<StatisticUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init { getTasks() }

    private fun getTasks() {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            statisticRepository.getStatistic()
                .onSuccess { statistics ->
                    Napier.d { "Task success"}
                    state = state.copy(isLoading = false, statistics = statistics)
                }
                .onFailure {
                    _uiEvent.send(StatisticUiEvent.ShowError("Error in Statistic loading"))
                }
        }
    }
}