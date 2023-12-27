package home.presentation.components.tasks

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ScaleStyle(
    val scaleWidth: Dp = 100.dp,
    val radius: Dp = 50.dp,
    val normalLineColor: Color = Color.LightGray,
    val normalLineLength: Dp = 30.dp,
)
