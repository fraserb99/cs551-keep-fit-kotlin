package com.fraserbell.keepfit.ui.steps

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.fraserbell.keepfit.ui.steps.composable.*
import com.fraserbell.keepfit.util.*
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.collect
import java.lang.Exception
import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*
import kotlin.math.roundToInt

@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun StepsScreen(navController: NavController, initialDate: Long?, vm: StepsViewModel = hiltViewModel()) {
    var currentDayId by remember { vm.currentDayId }
    val goals by vm.goals.collectAsState(listOf())
    val currentSteps by vm.getStepsForDate(currentDayId).collectAsState(null)
    val allowHistoricalRecording by vm.allowHistoricalRecording.collectAsState(initial = false)

    var goalDialogVisible by remember { vm.goalDialogVisible }
    var addDialogVisible by remember { vm.addDialogVisible }

    val pagerState by remember { vm.pagerState }
    val weekPagerState by remember { vm.weekPagerState }

    Scaffold(
        scaffoldState = vm.scaffoldState.value,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    vm.showAddDialog {
                        navController.navigate(it) {

                        }
                    }
                },
                backgroundColor =
                    if (allowHistoricalRecording || vm.isToday()) MaterialTheme.colors.secondary else Color.LightGray,
            ) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = "add goal")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        Column {
            TabMonthHeader(currentDayId)
            HorizontalPager(
                reverseLayout = true,
                state = weekPagerState
            ) { page ->
                val date = LocalDate.now().minusWeeks(page.toLong())
                DaysOfWeekTabPage(
                    startDate = date.getStartOfWeek(),
                    currentDate = currentDayId,
                    onDateChange = { newDate ->
                        currentDayId = newDate
                    }
                )
            }
            Box {
                HorizontalPager(
                    state = pagerState,
                    reverseLayout = true
                ) { page ->
                    DailyStepsPage(vm.getStepsForDayIndex(page))
                }
                DailyStepInfo(
                    onSwitchGoal = { goalDialogVisible = true },
                    stepsFlow = vm.getStepsForDate(currentDayId)
                )
            }
        }

        SwitchGoalDialog(
            date = currentDayId,
            currentGoalId = currentSteps?.goal?.goalId,
            visible = goalDialogVisible,
            onCancel = { goalDialogVisible = false },
            onUpdate = { date, goalId -> vm.updateDailyGoalAsync(date, goalId) },
            goals = goals,
            navController = navController
        )
        if (addDialogVisible) {
            AddStepsDialog(
                onSave = {
                    steps -> vm.addStepsToDayAsync(currentDayId, steps)
                },
                onCancel = {
                    addDialogVisible = false
                }
            )
        }
    }

    // sync scrollStates and currentDate
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            vm.onDayChange(page)
        }
    }
    LaunchedEffect(weekPagerState) {
        snapshotFlow { weekPagerState.currentPage }.collect { page ->
            vm.onWeekChange(page)
        }
    }
    LaunchedEffect(currentDayId) {
        vm.syncPagers()
    }
    LaunchedEffect(initialDate) {
        vm.setInitialDate(initialDate)
    }
}