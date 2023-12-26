package home.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import org.koin.compose.koinInject

object HomeScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel = koinInject<HomeViewModel>()

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AnimatedContent(viewModel.state.isLoading) {isLoading ->
                if (!isLoading) {
                    viewModel.state.tasks.forEach {
                        Text(text = "${it.title} : ${it.description}")
                    }
                } else {
                    CircularProgressIndicator(modifier = Modifier.size(50.dp))
                }
            }
        }
    }
}