import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import main.di.appModule
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import main.presentation.AppViewModel

@Composable
fun App() {
    KoinApplication(application = {
        modules(appModule())
    }) {
        Napier.base(DebugAntilog())
        MaterialTheme {
            val viewModel = koinInject<AppViewModel>()

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
}

expect fun getPlatformName(): String