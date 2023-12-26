import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import data.Task
import di.appModule
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import presentation.AppViewModel

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    KoinApplication(application = {
        modules(appModule())
    }) {
        Napier.base(DebugAntilog())
        MaterialTheme {
            var greetingText by remember { mutableStateOf("Hello, World!") }
            var showImage by remember { mutableStateOf(false) }
            val viewModel = koinInject<AppViewModel>()
            var list by remember { mutableStateOf(listOf<Task>()) }

            LaunchedEffect(Unit) {
                list = viewModel.getTasks()
            }

            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Button(onClick = {
                    greetingText = "Hello, ${getPlatformName()}"
                    showImage = !showImage
                }) {
                    Text(greetingText)
                }
                AnimatedVisibility(showImage) {
                    list.forEach {
                        Text(text = "${it.title} : ${it.description}")
                    }
                    Image(
                        painterResource("compose-multiplatform.xml"),
                        null
                    )
                }
            }
        }
    }
}

expect fun getPlatformName(): String