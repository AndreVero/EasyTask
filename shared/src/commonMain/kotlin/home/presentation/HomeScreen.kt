package home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import home.domain.model.Task
import home.presentation.components.tasks.TasksComponent
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.koinInject
import utils.LocalSnackbarHostState

object HomeScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel = koinInject<HomeViewModel>()
        val localSnackbarHost = LocalSnackbarHostState.current

        LaunchedEffect(true) {
            viewModel.uiEvent.collectLatest { event ->
                when (event) {
                    is HomeUiEvent.ShowError -> localSnackbarHost.showSnackbar(event.message)
                }
            }
        }

        Column (
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TasksComponent(
                modifier = Modifier.size(420.dp).background(Color.Gray),
                tasks = listOf(
                    Task(title = "TEST", ""),
                    Task(title = "TEST", ""),
                    Task(title = "TEST", "")
                )
            )


//            AnimatedContent(viewModel.state.isLoading) {isLoading ->
//                if (!isLoading) {
//                    viewModel.state.tasks.forEach {
//                        Text(text = "${it.title} : ${it.description}")
//                    }
//
//                } else {
//                    CircularProgressIndicator(modifier = Modifier.size(50.dp))
//                }
//            }
        }
    }
}