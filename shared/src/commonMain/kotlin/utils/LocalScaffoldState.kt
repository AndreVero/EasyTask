package utils

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.staticCompositionLocalOf

val LocalSnackbarHostState = staticCompositionLocalOf { SnackbarHostState() }