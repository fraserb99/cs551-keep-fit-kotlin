package com.fraserbell.keepfit.ui.steps.composable

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.fraserbell.keepfit.data.entities.DailySteps
import com.fraserbell.keepfit.data.entities.GoalDetails
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.flow.Flow
import kotlin.math.roundToInt

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun DailyStepInfo(onSwitchGoal: () -> Unit, stepsFlow: Flow<DailySteps?>) {
    val dailySteps by stepsFlow.collectAsState(initial = null)
    val goal = dailySteps?.goal

    Column(Modifier.padding(8.dp)) {
        InfoBubble(
            visible = true,
            label = "Goal",
            transitionState = Pair(dailySteps, goal),
            button = {
                Button(
                    modifier = Modifier
                        .height(54.dp)
                        .width(40.dp),
                    onClick = onSwitchGoal,
                    contentPadding = PaddingValues(4.dp),
                    shape = MaterialTheme.shapes.medium.copy(
                        topStart = CornerSize(0.dp),
                        bottomStart = CornerSize(0.dp)
                    )
                ) {
                    Icon(
                        imageVector = if (goal != null) Icons.Rounded.Menu else Icons.Rounded.Add,
                        contentDescription = "change goal"
                    )
                }
            }
        ) {
            (_, goalTarget) ->
                Text(
                    text = goalTarget?.name?.let { goalTarget.name }
                        ?: run { "N/A" },
                    style = MaterialTheme.typography.h6,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.widthIn(0.dp, 178.dp)
                )
        }
        InfoBubble(
            visible = goal != null,
            label = "Step Goal",
            transitionState = Pair(dailySteps, goal)
        ) { (_, goalTarget) ->
            Text(
                text = goalTarget?.stepGoal?.let { "%,d".format(goalTarget.stepGoal) }
                    ?: run { "N/A" },
                style = MaterialTheme.typography.h6
            )
        }
        InfoBubble(
            visible = goal != null,
            label = "Progress",
            transitionState = Pair(dailySteps, goal)
        ) { (day, goalTarget) ->
            Text(
                text = if (goalTarget?.stepGoal != null && day?.steps != null) {
                    (day.steps.toFloat() / goalTarget.stepGoal * 100).roundToInt()
                        .toString() + "%"
                } else {
                    "N/A"
                },
                style = MaterialTheme.typography.h6
            )
        }
    }
}


@ExperimentalAnimationApi
@Composable
fun InfoBubble(
    visible: Boolean = true,
    label: String,
    button: @Composable (() -> Unit)? = null,
    transitionState: Pair<DailySteps?, GoalDetails?>,
    content: @Composable (Pair<DailySteps?, GoalDetails?>) -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInHorizontally(),
        exit = slideOutHorizontally(),
    ) {
        Surface(
            modifier = Modifier
                .padding(bottom = 4.dp),
            elevation = 8.dp,
            shape = MaterialTheme.shapes.medium
        ) {
            Row {
                Column(
                    Modifier.padding(6.dp)
                ) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.overline,
                        modifier = Modifier.alpha(0.72f)
                    )
                    AnimatedContent(
                        targetState = transitionState,
                        transitionSpec = {
                            val initialGoal = initialState.second
                            val goal = targetState.second
                            val initialDay = initialState.first
                            val day = targetState.first

                            val transitionForward = initialDay?.let {
                                day?.dayId?.isBefore(initialDay.dayId)
                            }

                            when {
                                transitionForward == true -> {
                                    slideInHorizontally(initialOffsetX = { fullWidth -> -fullWidth }) + fadeIn() with
                                            slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth }) + fadeOut()
                                }
                                transitionForward == false -> {
                                    slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }) + fadeIn() with
                                            slideOutHorizontally(targetOffsetX = { fullWidth -> -fullWidth }) + fadeOut()
                                }
                                else -> {
                                    fadeIn() with fadeOut()
                                }
                            }.using(SizeTransform())
                        }
                    ) {
                        content(it)
                    }
                }
                button?.invoke()
            }
        }
    }
}