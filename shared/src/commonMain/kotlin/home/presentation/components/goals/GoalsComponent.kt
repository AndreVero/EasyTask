package home.presentation.components.goals

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import home.domain.model.Goal
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
fun GoalsComponent(
    modifier: Modifier = Modifier,
    goals: List<Goal> = emptyList(),
    mainCircleRadius: Dp = 130.dp,
    innerCircleRadius: Dp = 60.dp,
    textStyle: TextStyle = MaterialTheme.typography.body2,
    onGoalClick: (Goal) -> Unit,
) {

    val textMeasurer = rememberTextMeasurer()

    val goalUiItems by remember { mutableStateOf<MutableMap<Offset, Goal>>(mutableMapOf()) }
    val animateFloat = remember { Animatable(0f) }
    val animateFloatScale = remember { Animatable(1f) }
    val animateFloatText = remember { Animatable(0f) }

    var circleCenter by remember { mutableStateOf(Offset.Zero) }

    var angel by remember { mutableStateOf(0f) }
    var dragStartedAngle by remember { mutableStateOf(0f) }
    var oldAngle by remember { mutableStateOf(angel) }

    val angleRatio = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = goals) {
        angleRatio.animateTo(
            targetValue = 100f,
            animationSpec = tween(
                durationMillis = 1500
            )
        )
    }

    LaunchedEffect(animateFloat) {
        animateFloatText.animateTo(
            targetValue = 1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessVeryLow
            )
        )
    }
    LaunchedEffect(animateFloat) {
        animateFloatScale.animateTo(
            targetValue = 3.5f,
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
                goalUiItems.forEach { item ->
                    val rect = Rect(item.key, innerCircleRadius.toPx())
                    if (rect.contains(clickOffset)) {
                        onGoalClick(item.value)
                    }
                }
            }
        }
        .pointerInput(true) {
            detectDragGestures(
                onDragStart = { offset ->
                    dragStartedAngle = -atan2(
                        circleCenter.x - offset.x,
                        circleCenter.y - offset.y,
                    ) * (180f / PI.toFloat())
                    dragStartedAngle = (dragStartedAngle + 180f).mod(360f)
                },
                onDragEnd = {
                    oldAngle = angel
                }
            ) { change, _ ->
                var touchAngle = -atan2(
                    circleCenter.x - change.position.x,
                    circleCenter.y - change.position.y,
                ) * (180f / PI.toFloat())
                touchAngle = (touchAngle + 180f).mod(360f)

                val changeAngle = touchAngle - dragStartedAngle

                angel = (oldAngle + (changeAngle).roundToInt())
            }
        }) {
        circleCenter = Offset(center.x, center.y)

        val distance = 360f / goals.size

        goalUiItems.clear()
        goals.forEachIndexed { i, task ->
            val angelInRad = (i * distance + angel - 90) * (PI / 180).toFloat()
            val currentOffset = Offset(
                x = mainCircleRadius.toPx() * cos(angelInRad) + circleCenter.x,
                y = mainCircleRadius.toPx() * sin(angelInRad) + circleCenter.y
            )

            goalUiItems[currentOffset] = task
            drawCircle(
                color = Color.White,
                radius = innerCircleRadius.toPx() * animateFloat.value,
                center = currentOffset
            )

            val diameter = innerCircleRadius.toPx() * 2
            val pathBounds = task.goalPath.bounds
            task.progress?.let { progress ->
                val progressX = currentOffset.x - innerCircleRadius.toPx()
                val progressY = currentOffset.y - innerCircleRadius.toPx()

                drawArc(
                    topLeft = Offset(progressX, progressY),
                    color = Color.Gray,
                    startAngle = 0f,
                    sweepAngle = 360f,
                    useCenter = false,
                    size = Size(  diameter * animateFloat.value, diameter * animateFloat.value),
                    style = Stroke(
                        width = 4.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                )
                drawArc(
                    topLeft = Offset(progressX, progressY),
                    color = Color(0xFF23A145),
                    startAngle = 90f,
                    sweepAngle = 360f * (progress / angleRatio.value),
                    useCenter = false,
                    size = Size(diameter * animateFloat.value, diameter * animateFloat.value),
                    style = Stroke(
                        width = 4.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                )
            }

            translate(
                left = currentOffset.x - pathBounds.right * 1.5f,
                top = currentOffset.y - 15.dp.toPx() - pathBounds.bottom  * 1.5f
            ) {
                scale(scale = animateFloatScale.value, pathBounds.topLeft) {
                    drawPath(
                        path = task.goalPath.path,
                        color = Color.Black
                    )
                }
            }

            val fontSize = textStyle.fontSize
            val result = textMeasurer.measure(
                task.title,
                constraints = Constraints(
                    maxWidth = (innerCircleRadius.toPx() * 2 - 40f).toInt()
                ),
                style = textStyle.copy(
                    textAlign = TextAlign.Center,
                    fontSize = (fontSize.toPx() * animateFloatText.value).toSp()
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