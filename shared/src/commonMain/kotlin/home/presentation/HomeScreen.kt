@file:OptIn(ExperimentalResourceApi::class, ExperimentalResourceApi::class,
    ExperimentalResourceApi::class
)

package home.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import home.presentation.components.tasks.TasksComponent
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
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

        Column(
            modifier = Modifier.fillMaxSize().background(Color.Black),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AnimatedContent(viewModel.state.isLoading) { isLoading ->
                if (!isLoading) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        TasksComponent(
                            modifier = Modifier.size(420.dp).align(Alignment.Center),
                            tasks = viewModel.state.tasks,
                            onTaskClick = {}
                        )
                        Image(
                            painter = painterResource("baseline_task_24.xml"),
                            contentDescription = "Home icon",
                            modifier = Modifier.align(Alignment.Center).size(48.dp)
                        )
                    }
                } else {
                    CircularProgressIndicator(modifier = Modifier.size(50.dp))
                }
            }
        }
    }
}