package home.presentation.components.tasks

import androidx.compose.runtime.Composable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import home.domain.model.Task
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
fun TasksComponent(
    modifier: Modifier = Modifier,
    tasks: List<Task> = emptyList(),
    style: ScaleStyle
) {

    val radius = style.radius
    val scaleWidth = style.scaleWidth

    var circleCenter by remember { mutableStateOf(Offset.Zero) }

    var angel by remember { mutableStateOf(0f) }
    var dragStartedAngel by remember { mutableStateOf(0f) }
    var oldAngel by remember { mutableStateOf(angel) }

    Canvas(modifier = modifier
        .pointerInput(true) {
            detectDragGestures(
                onDragStart = { offset ->
                    dragStartedAngel = -atan2(
                        circleCenter.x - offset.x,
                        circleCenter.y - offset.y,
                    ) * (180f / PI.toFloat())
                    dragStartedAngel = (dragStartedAngel + 180f).mod(360f)
                },
                onDragEnd = {
                    oldAngel = angel
                }
            ) { change, _ ->
                var touchAngel = -atan2(
                    circleCenter.x - change.position.x,
                    circleCenter.y - change.position.y,
                ) * (180f / PI.toFloat())
                touchAngel = (touchAngel + 180f).mod(360f)

                val changeAngle = touchAngel - oldAngel

                angel = (oldAngel + (changeAngle).roundToInt())
//                val newAngel = oldAngel + (touchAngel - dragStartedAngel)
//                angel = newAngel.coerceIn(
//                    minimumValue = 0f,
//                    maximumValue = 360f
//                )
            }
        }) {
        circleCenter = Offset(center.x, scaleWidth.toPx() / 2f + radius.toPx())

        val outerRadius = radius.toPx() + scaleWidth.toPx() / 2f

        val distance = 360f / tasks.size

        tasks.forEachIndexed { i, _ ->
            val angelInRad = (i * distance + angel - 90) * (PI / 180).toFloat()

            drawCircle(
                color = Color.Red,
                radius = 20f,
                center = Offset(
                    x = (outerRadius - 30.dp.toPx()) * cos(angelInRad) + circleCenter.x,
                    y = (outerRadius - 30.dp.toPx()) * sin(angelInRad) + circleCenter.y
                )
            )
        }
    }
}