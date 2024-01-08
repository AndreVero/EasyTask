package goaldetails.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import goaldetails.presentation.components.GoalDetailsComponent
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.koinInject
import utils.LocalSnackbarHostState

data class GoalDetailsScreen(val goalId: String) : Screen {
    @Composable
    override fun Content() {
        val localSnackbarHost = LocalSnackbarHostState.current
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinInject<GoalDetailsViewModel>()
        val state = viewModel.state
        var appBarIsVisible by remember { mutableStateOf(false) }

        LaunchedEffect(true) {
            appBarIsVisible = true
            viewModel.getTasks(goalId)
            viewModel.uiEvent.collectLatest { event ->
                when (event) {
                    is GoalDetailsUiEvent.ShowError -> localSnackbarHost.showSnackbar(event.message)
                }
            }
        }

        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
                title = {
                    Text(
                        text = "Details",
                        style = MaterialTheme.typography.h3,
                        color = Color.White
                    )
                },
                backgroundColor = Color.Transparent,
                navigationIcon = {
                    IconButton(
                        modifier = Modifier.clip(CircleShape).background(Color.White),
                        onClick = { navigator.pop() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            tint = Color.Black,
                            contentDescription = "Statistic"
                        )
                    }
                },
                modifier = Modifier.padding(top = 16.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                if (state.goalDetails != null)
                    items(
                        viewModel.state.goalDetails!!.tasks,
                        key = { item -> item }
                    ) {
                        GoalDetailsComponent(
                            task = it,
                            goalDetails = state.goalDetails
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                else {
                    item {
                        Text(
                            text = "There is no task for today!",
                            style = MaterialTheme.typography.h3,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}