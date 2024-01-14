@file:OptIn(ExperimentalResourceApi::class)

package tasks.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.resource
import tasks.domain.model.Task

@Composable
fun TaskComponent(
    task: Task,
    markTaskAsDone: () -> Unit,
    markTaskAsFailed: () -> Unit,
    distanceForSwipe: Dp = 150.dp,
    modifier: Modifier = Modifier
) {

    var expanded by remember { mutableStateOf(false) }
    var offset by remember { mutableStateOf(DpOffset.Zero) }
    val coroutineScope = rememberCoroutineScope()
    val animatable by remember { mutableStateOf(Animatable(1f)) }
    var doneAnimationVisible by remember { mutableStateOf(false) }
    var failedAnimationVisible by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(500.dp)
            .offset(x = offset.x * animatable.value, y = offset.y * animatable.value)
            .border(
                1.dp, Color.White, RoundedCornerShape(
                    topStartPercent = 10,
                    topEndPercent = 40,
                    bottomStartPercent = 10,
                    bottomEndPercent = 10
                )
            )
            .padding(16.dp)
            .pointerInput(true) {
                detectDragGestures(
                    onDragEnd = {
                        if (offset.x > distanceForSwipe) {
                            doneAnimationVisible = true
                            markTaskAsDone()
                        } else if (offset.x < -distanceForSwipe) {
                            failedAnimationVisible = true
                            markTaskAsFailed()
                        } else {
                            coroutineScope.launch {
                                animatable.animateTo(0f)
                                offset = DpOffset.Zero
                                animatable.snapTo(1f)
                            }
                        }
                    },
                    onDrag = { change, dragAmount ->
                        val x = offset.x.toPx()
                        val y = offset.y.toPx()

                        offset = DpOffset(
                            x = (x + dragAmount.x).toDp(),
                            y = y.toDp()
                        )
                    }
                )
            }
    ) {
        if (doneAnimationVisible) {
            JsonAnimation(
                modifier = Modifier.fillMaxSize(),
                fileName = resource("raw/anim2.json"),
                iterations = 10
            )
        }
        if (failedAnimationVisible) {
            JsonAnimation(
                modifier = Modifier.fillMaxSize(),
                fileName = resource("raw/anim2.json"),
                iterations = 10
            )
        }
        Column(modifier = Modifier.align(Alignment.TopStart)) {
            Text(
                text = "Title: " + task.title,
                color = Color.White,
                style = MaterialTheme.typography.body2,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Description: " + task.description,
                color = Color.White,
                style = MaterialTheme.typography.body2,
            )
            Spacer(modifier = Modifier.height(8.dp))
            AnimatedContent(expanded) { isExpanded ->
                if (isExpanded) {
                    Column {
                        Text(
                            text = "Motivation: " + task.motivation,
                            color = Color.White,
                            style = MaterialTheme.typography.body2,
                            modifier = Modifier.width(330.dp).clickable {
                                expanded = false
                            }
                        )
                    }
                } else {
                    Text(
                        text = "Tap to see motivation...",
                        color = Color.White,
                        style = MaterialTheme.typography.body2,
                        modifier = Modifier.clickable {
                            expanded = true
                        }
                    )
                }
            }

        }
        Canvas(modifier = Modifier.size(24.dp).align(Alignment.BottomEnd)) {
            val center = this.center
            val bounds = task.icon.getBounds()
            translate(left = center.x + bounds.width, center.y + bounds.height) {
                scale(3f) {
                    drawPath(
                        path = task.icon,
                        color = Color.White
                    )
                }
            }
        }

    }
}