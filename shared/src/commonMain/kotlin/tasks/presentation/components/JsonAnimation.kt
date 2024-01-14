package tasks.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Resource


@OptIn(ExperimentalResourceApi::class)
@Composable
expect fun JsonAnimation(
    modifier: Modifier = Modifier,
    fileName: Resource,
    speed: Float = 1f,
    iterations: Int = 1,
    completed: () -> Unit = {}
)