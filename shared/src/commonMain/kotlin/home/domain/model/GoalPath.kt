package home.domain.model

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Path

data class GoalPath(
    val path: Path,
    val bounds: Rect,
)
