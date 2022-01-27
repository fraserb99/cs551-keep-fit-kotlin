package com.fraserbell.keepfit.ui.steps.composable

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fraserbell.keepfit.data.entities.DailySteps
import com.fraserbell.keepfit.data.entities.DayWithGoal
import com.fraserbell.keepfit.data.entities.Goal
import com.fraserbell.keepfit.ui.steps.DailyStepsViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import java.time.LocalDate
import kotlin.math.roundToInt

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun DailyStepInfo(date: LocalDate, onSwitchGoal: () -> Unit, vm: DailyStepsViewModel = hiltViewModel()) {
    val dayWithSteps by vm.getStepsForDate(date).collectAsState(initial = null)
    val dailySteps = dayWithSteps?.dailySteps
    val goal = dayWithSteps?.goal

    Column(Modifier.padding(8.dp)) {
        InfoBubble {
            Row {
                Column(
                    Modifier.padding(6.dp)
                ) {
                    Text(
                        text = "Goal",
                        style = MaterialTheme.typography.overline,
                        modifier = Modifier.alpha(0.72f)
                    )
                    AnimatedContent(targetState = dailySteps) { day ->
                        Text(
                            text = goal?.name?.let { goal.name }
                                ?: run { "N/A" },
                            style = MaterialTheme.typography.h6,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.widthIn(0.dp, 178.dp)
                        )
                    }
                }
                Button(
                    modifier = Modifier
                        .height(54.dp)
                        .width(40.dp),
                    onClick = onSwitchGoal,
                    contentPadding = PaddingValues(4.dp),
                    shape = MaterialTheme.shapes.medium.copy(topStart = CornerSize(0.dp), bottomStart = CornerSize(0.dp))
                ) {
                    Icon(
                        imageVector = if (goal != null) Icons.Rounded.Menu else Icons.Rounded.Add,
                        contentDescription = "change goal"
                    )
                }
            }
        }
        InfoBubble(visible = goal != null) {
            Column(
                Modifier.padding(6.dp)
            ) {
                Text(
                    text = "Step Goal",
                    style = MaterialTheme.typography.overline,
                    modifier = Modifier.alpha(0.72f)
                )
                AnimatedContent(targetState = goal) { goalTarget ->
                    Text(
                        text = goalTarget?.stepGoal?.let { "%,d".format(goalTarget.stepGoal) }
                            ?: run { "N/A" },
                        style = MaterialTheme.typography.h6
                    )
                }
            }
        }
        InfoBubble(visible = goal != null) {
            Column(
                Modifier.padding(6.dp)
            ) {
                Text(
                    text = "Progress",
                    style = MaterialTheme.typography.overline,
                    modifier = Modifier.alpha(0.72f)
                )
                AnimatedContent(targetState = Pair(dailySteps, goal)) { (day, goalTarget) ->
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
    }
}

@ExperimentalAnimationApi
@Composable
fun InfoBubble(
    visible: Boolean = true,
    content: @Composable() () -> Unit
) {
    AnimatedVisibility(visible = visible) {
        Surface(
            modifier = Modifier
                .padding(bottom = 4.dp),
            elevation = 8.dp,
            shape = MaterialTheme.shapes.medium
        ) {
            content()
        }
    }
}