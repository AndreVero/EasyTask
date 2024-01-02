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
    mainCircleRadius: Dp = 130.dp,
    innerCircleRadius: Dp = 60.dp,
    onTaskClick: (Task) -> Unit,
) {

    val textMeasurer = rememberTextMeasurer()
    val path = remember {
        PathParser().parsePathString("m11,2.5c0-1.381,1.119-2.5,2.5-2.5s2.5,1.119,2.5,2.5-1.119,2.5-2.5,2.5-2.5-1.119-2.5-2.5Zm9.171,9.658l-2.625-1.312s-2.268-3.592-2.319-3.651c-.665-.76-1.625-1.195-2.634-1.195-1.274,0-2.549.301-3.688.871l-2.526,1.263c-.641.321-1.114.902-1.298,1.596l-.633,2.387c-.212.801.265,1.622,1.065,1.834.802.213,1.622-.264,1.834-1.065l.575-2.168,1.831-.916-.662,2.83c-.351,1.5.339,3.079,1.679,3.84l3.976,2.258c.156.089.253.256.253.436v3.336c0,.829.672,1.5,1.5,1.5s1.5-.671,1.5-1.5v-3.336c0-1.256-.679-2.422-1.771-3.043l-2.724-1.547.849-3.165.875,1.39c.146.232.354.42.599.543l3,1.5c.216.107.444.159.67.159.55,0,1.08-.304,1.343-.83.37-.741.07-1.642-.671-2.013Zm-10.312,5.465c-.812-.161-1.6.378-1.754,1.192l-.039.2-1.407,2.814c-.37.741-.07,1.642.671,2.013.215.107.444.159.67.159.55,0,1.08-.304,1.343-.83l1.5-3c.062-.123.106-.254.131-.39l.077-.404c.156-.813-.378-1.599-1.192-1.754Z")
            .toPath()
    }
    val pathBounds = remember {
        path.getBounds()
    }

    val taskUiItems by remember { mutableStateOf<MutableMap<Offset, Task>>(mutableMapOf()) }
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
            targetValue = 3f,
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
                taskUiItems.forEach { item ->
                    val rect = Rect(item.key, innerCircleRadius.toPx())
                    if (rect.contains(clickOffset)) {
                        onTaskClick(item.value)
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

                val changeAngle = touchAngel - dragStartedAngel

                angel = (oldAngel + (changeAngle).roundToInt())
            }
        }) {
        circleCenter = Offset(center.x, center.y)

        val distance = 360f / tasks.size

        taskUiItems.clear()
        tasks.forEachIndexed { i, task ->
            val angelInRad = (i * distance + angel - 90) * (PI / 180).toFloat()
            val currentOffset = Offset(
                x = mainCircleRadius.toPx() * cos(angelInRad) + circleCenter.x,
                y = mainCircleRadius.toPx() * sin(angelInRad) + circleCenter.y
            )

            taskUiItems.put(currentOffset, task)
            drawCircle(
                color = Color.White,
                radius = innerCircleRadius.toPx() * animateFloat.value,
                center = currentOffset
            )
            translate(
                left = currentOffset.x - pathBounds.right * 2 / 2,
                top = currentOffset.y - 15.dp.toPx() - pathBounds.bottom  * 2 / 2
            ) {
                scale(scale = animateFloatScale.value, pathBounds.topLeft) {
                    drawPath(
                        path = path,
                        color = Color.Black
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