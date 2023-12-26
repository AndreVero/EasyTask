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
import cafe.adriel.voyager.navigator.Navigator
import home.di.appModule
import home.presentation.HomeScreen
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import home.presentation.HomeViewModel

@Composable
fun App() {
    KoinApplication(application = {
        modules(appModule())
    }) {
        Napier.base(DebugAntilog())
        MaterialTheme {
            Navigator(HomeScreen)
        }
    }
}

expect fun getPlatformName(): String