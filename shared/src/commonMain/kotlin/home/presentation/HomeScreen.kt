@file:OptIn(ExperimentalResourceApi::class)

package home.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
            verticalArrangement = Arrangement.Top
        ) {
            TopAppBar(
                title = {
                    Text(
                        text = "Easy Task",
                        color = Color.White
                    )
                },
                backgroundColor = Color.Transparent,
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.List,
                            tint = Color.White,
                            contentDescription = "Statistic"
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.height(100.dp))
            Box(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier.align(Alignment.TopCenter)) {
                    AnimatedVisibility(
                        !viewModel.state.isLoading,
                        enter = fadeIn(),
                        exit = fadeOut(),
                    ) {
                        Box {
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
                    }
                }

                Column(modifier = Modifier.align(Alignment.BottomCenter)) {
                    AnimatedVisibility(
                        !viewModel.state.isLoading,
                        enter = fadeIn() + expandVertically(),
                        exit = fadeOut() + shrinkVertically(),
                        modifier = Modifier
                    ) {
                        Box(modifier = Modifier
                            .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                            .fillMaxWidth()
                            .height(180.dp)
                            .background(Color.White)
                            .clickable {  }
                        ) {
                            Text(
                                text = "Tasks for Today",
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
            }
        }
    }
}