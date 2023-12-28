package home.presentation.components.tasks

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.runtime.Composable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import home.domain.model.Task
import io.github.aakira.napier.Napier
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
fun TasksComponent(
    modifier: Modifier = Modifier,
    tasks: List<Task> = emptyList(),
    mainCircleRadius: Dp = 150.dp,
    innerCircleRadius: Dp = 60.dp
) {

    val textMeasurer = rememberTextMeasurer()
    val path = remember {
        PathParser().parsePathString("M22.05,7.08A7.08,7.08 0,1 0,15 14.16,7.08 7.08,0 0,0 22.05,7.08ZM0,25.2V43.25a2.8,2.8 0,0 0,5.6 0V25.67H6.71l0.06,21.16v29a3.73,3.73 0,0 0,3.73 3.74h0.08a3.73,3.73 0,0 0,3.73 -3.74v-29h1.33V75.69a3.73,3.73 0,0 0,3.73 3.73h0.08a3.73,3.73 0,0 0,3.73 -3.73v-50h1.17V43.25a2.8,2.8 0,0 0,5.6 0v-18c0,-2.95 -0.78,-9.64 -6.77,-9.64H6.69C0.77,15.62 0,22.26 0,25.2Z")
            .toPath()
    }
    val pathBounds = remember {
        path.getBounds()
    }

    val rects by remember { mutableStateOf<MutableList<Offset>>(mutableListOf()) }
    val animateFloat = remember { Animatable(0f) }
    val animateFloatScale = remember { Animatable(1f) }
    val animateFloatText = remember { Animatable(10f) }

    var circleCenter by remember { mutableStateOf(Offset.Zero) }

    var angel by remember { mutableStateOf(0f) }
    var dragStartedAngel by remember { mutableStateOf(0f) }
    var oldAngel by remember { mutableStateOf(angel) }

    LaunchedEffect(animateFloat) {
        animateFloatText.animateTo(
            targetValue = 40f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessVeryLow
            )
        )
    }
    LaunchedEffect(animateFloat) {
        animateFloatScale.animateTo(
            targetValue = 2f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessVeryLow
            )
        )
    }
    LaunchedEffect(animateFloat) {
        animateFloat.animateTo(
            targetValue = 1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessVeryLow
            )
        )
    }

    Canvas(modifier = modifier
        .pointerInput(true) {
            detectTapGestures { clickOffset ->
                rects.forEach { taskOffset ->
                    val rect = Rect(taskOffset, innerCircleRadius.toPx())
                    if (rect.contains(clickOffset)) {
                        Napier.d { "Click detected" }
                    }
                }
            }
        }
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
            }
        }) {
        circleCenter = Offset(center.x, center.y)

        val distance = 360f / tasks.size

        rects.clear()
        tasks.forEachIndexed { i, task ->
            val angelInRad = (i * distance + angel - 90) * (PI / 180).toFloat()
            val currentOffset = Offset(
                x = mainCircleRadius.toPx() * cos(angelInRad) + circleCenter.x,
                y = mainCircleRadius.toPx() * sin(angelInRad) + circleCenter.y
            )

            rects.add(currentOffset)
            drawCircle(
                color = Color.Red,
                radius = innerCircleRadius.toPx() * animateFloat.value,
                center = currentOffset
            )
            translate(
                left = currentOffset.x - pathBounds.right * 2 / 2,
                top = currentOffset.y - 20.dp.toPx() - pathBounds.bottom  * 2 / 2
            ) {
                scale(scale = animateFloatScale.value, pathBounds.topLeft) {
                    drawPath(
                        path = path,
                        color = Color.Green
                    )
                }
            }

            val result = textMeasurer.measure(
                task.title,
                style = TextStyle(
                    fontSize = animateFloatText.value.toSp()
                )
            )

            drawText(
                textLayoutResult = result,
                topLeft = Offset(
                    x = currentOffset.x - result.size.width / 2,
                    y = currentOffset.y + pathBounds.height
                )
            )
        }
    }
}