@file:OptIn(ExperimentalResourceApi::class)

package home.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import goaldetails.presentation.GoalDetailsScreen
import home.presentation.components.tasks.GoalsComponent
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import tasks.presentation.TasksScreen
import utils.LocalSnackbarHostState

object HomeScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel = koinInject<HomeViewModel>()
        val localSnackbarHost = LocalSnackbarHostState.current
        val navigator = LocalNavigator.currentOrThrow

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
                        style = MaterialTheme.typography.h3,
                        color = Color.White
                    )
                },
                backgroundColor = Color.Transparent,
                actions = {
                    IconButton(
                        modifier = Modifier.clip(CircleShape).background(Color.White),
                        onClick = {}
                    ) {
                        Icon(
                            painter = painterResource("icon_chart.xml"),
                            tint = Color.Black,
                            contentDescription = "Statistic"
                        )
                    }
                },
                modifier = Modifier.padding(top = 16.dp)
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
                            GoalsComponent(
                                modifier = Modifier.size(420.dp).align(Alignment.Center),
                                goals = viewModel.state.goals,
                                onGoalClick = {
                                    navigator.push(GoalDetailsScreen(it.title))
                                }
                            )
                            Text(
                                text = "Choose Your Destiny",
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.h4,
                                modifier = Modifier.align(Alignment.Center).width(100.dp)
                            )
                        }
                    }
                }

                Column(modifier = Modifier.align(Alignment.BottomCenter)) {
                    AnimatedVisibility(
                        visible = !viewModel.state.isLoading,
                        enter = slideIn(
                            initialOffset = { IntOffset(0, it.height) },
                            animationSpec = spring(
                                stiffness = Spring.StiffnessMediumLow,
                                visibilityThreshold = IntOffset.VisibilityThreshold
                            )
                        )
                    ) {
                        Box(modifier = Modifier
                            .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                            .fillMaxWidth()
                            .height(120.dp)
                            .background(Color.White)
                            .clickable { navigator.push(TasksScreen) }
                        ) {
                            Text(
                                text = "Tasks for Today",
                                style = MaterialTheme.typography.h3,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
            }
        }
    }
}