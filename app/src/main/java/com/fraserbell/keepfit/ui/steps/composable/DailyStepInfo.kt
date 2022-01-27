package com.fraserbell.keepfit.ui.steps.composable

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
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
import com.fraserbell.keepfit.ui.steps.DailyStepsViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import java.time.LocalDate
import kotlin.math.roundToInt

@ExperimentalAnimationApi
@SuppressLint("UnusedCrossfadeTargetStateParameter")
@ExperimentalPagerApi
@Composable
fun DailyStepInfo(date: LocalDate, vm: DailyStepsViewModel = hiltViewModel()) {
    val dailySteps by vm.getStepsForDate(date).collectAsState(initial = null)

    Column(Modifier.padding(8.dp)) {
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
                        text = "Goal",
                        style = MaterialTheme.typography.overline,
                        modifier = Modifier.alpha(0.72f)
                    )
                    AnimatedContent(targetState = dailySteps) { day ->
                        Text(
                            text = "Working"
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
                    onClick = { /*TODO*/ },
                    contentPadding = PaddingValues(4.dp),
                    shape = MaterialTheme.shapes.medium.copy(topStart = CornerSize(0.dp), bottomStart = CornerSize(0.dp))
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Menu,
                        contentDescription = "change goal"
                    )
                }
            }
        }
        Surface(
            modifier = Modifier
                .padding(bottom = 4.dp),
            elevation = 8.dp,
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                Modifier.padding(6.dp)
            ) {
                Text(
                    text = "Step Goal",
                    style = MaterialTheme.typography.overline,
                    modifier = Modifier.alpha(0.72f)
                )
                AnimatedContent(targetState = dailySteps) { day ->
                    Text(
                        text = day?.stepGoal?.let { "%,d".format(day.stepGoal) }
                            ?: run { "N/A" },
                        style = MaterialTheme.typography.h6
                    )
                }
            }
        }
        Surface(
            elevation = 8.dp,
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                Modifier.padding(6.dp)
            ) {
                Text(
                    text = "Progress",
                    style = MaterialTheme.typography.overline,
                    modifier = Modifier.alpha(0.72f)
                )
                AnimatedContent(targetState = dailySteps) { day ->
                    Text(
                        text = if (day?.stepGoal != null) {
                            (day.steps.toFloat() / day.stepGoal * 100).roundToInt()
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