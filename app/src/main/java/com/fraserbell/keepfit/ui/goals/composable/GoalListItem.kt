package com.fraserbell.keepfit.ui.goals.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.fraserbell.keepfit.data.entities.Goal
import kotlin.math.roundToInt

@ExperimentalMaterialApi
@Composable
fun GoalListItem(
    goal: Goal,
    onEdit: (goal: Goal) -> Unit,
    onDelete: (goal: Goal) -> Unit,
    currentOpenItem: Int?,
    onSwipe: (open: Boolean) -> Unit,
) {
    val itemSize by remember { mutableStateOf(IntSize.Zero) }
    val swipeableState = rememberSwipeableState(1)
    val actionsSize = 144.dp
    val sizePx = with(LocalDensity.current) { actionsSize.toPx() }

    Surface( modifier = Modifier.swipeable(
        state = swipeableState,
        anchors = mapOf(itemSize.width - sizePx to 0, 0f to 1),
        thresholds = { _, _ -> FractionalThreshold(0.5f) },
        orientation = Orientation.Horizontal
    )) {
        Box {
            Row(
                Modifier
                    .height(72.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Surface(
                    Modifier
                        .fillMaxHeight()
                        .width(actionsSize / 2)
                        .clickable(onClick = { onEdit(goal) }),
                    color = MaterialTheme.colors.primary,
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Edit,
                            contentDescription = "edit",
                            tint = Color.White,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }
                GoalActionButton(
                    icon = Icons.Rounded.Edit,
                    onClick = { onEdit(goal) },
                    backgroundColor = Color(0xFF8021f3),
                    width = actionsSize / 2
                )
                Surface(
                    Modifier
                        .fillMaxHeight()
                        .width(actionsSize / 2)
                        .clickable(onClick = { onDelete(goal) }),
                    color = MaterialTheme.colors.error
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Delete,
                            contentDescription = "delete",
                            tint = Color.White,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }
            }
            Surface(
                elevation = 1.dp,
                modifier = Modifier.offset {
                    IntOffset(
                        swipeableState.offset.value.roundToInt(),
                        0
                    )
                }
            ) {
                ListItem(
                    icon = { Icon(imageVector = Icons.Rounded.Star, contentDescription = "") },
                    text = { Text("%,d".format(goal.stepCount)) },
                    secondaryText = { if (goal.name != null) { Text(goal.name) }},
                )
            }
        }
    }

    LaunchedEffect(currentOpenItem) {
        if (currentOpenItem != goal.goalId) {
            swipeableState.animateTo(1)
        }
    }
    LaunchedEffect(swipeableState.currentValue) {
        onSwipe(swipeableState.currentValue == 0)
    }
    LaunchedEffect(swipeableState.offset.value) {
        if (swipeableState.currentValue == 1) {
            onSwipe(true)
        }
    }
}

@Composable
fun GoalActionButton(
    icon: ImageVector,
    onClick: () -> Unit,
    backgroundColor: Color,
    width: Dp
) {
    Surface(
        Modifier
            .fillMaxHeight()
            .width(width)
            .clickable(onClick = onClick),
        color = backgroundColor
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier.size(36.dp)
            )
        }
    }
}