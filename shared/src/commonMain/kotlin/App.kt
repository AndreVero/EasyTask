
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import home.di.appModule
import home.presentation.HomeScreen
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.compose.KoinApplication

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