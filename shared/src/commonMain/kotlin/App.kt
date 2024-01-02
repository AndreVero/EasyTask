import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import cafe.adriel.voyager.navigator.Navigator
import home.di.appModule
import home.presentation.HomeScreen
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.compose.KoinApplication
import utils.LocalSnackbarHostState

@Composable
fun App() {
    KoinApplication(application = {
        modules(appModule())
    }) {
        Napier.base(DebugAntilog())
        EasyTaskTheme {
            val localSnackbarHostState = LocalSnackbarHostState.current
            CompositionLocalProvider(
                LocalSnackbarHostState provides localSnackbarHostState
            ) {
                Scaffold(
                    snackbarHost = {
                        SnackbarHost(hostState = LocalSnackbarHostState.current) { data ->
                            val backgroundColor = MaterialTheme.colors.error
                            Snackbar(
                                snackbarData = data,
                                backgroundColor = backgroundColor,
                            )
                        }
                    }
                ) {
                    Navigator(HomeScreen)
                }
            }
        }
    }
}



expect fun getPlatformName(): String

@Composable
expect fun font(name: String, res: String, weight: FontWeight, style: FontStyle): Font