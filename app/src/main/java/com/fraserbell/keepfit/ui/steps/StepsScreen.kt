package com.fraserbell.keepfit.ui.steps

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
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
    var currentDayId by remember { mutableStateOf(initialDate?.let { LocalDate.ofEpochDay(it) } ?: LocalDate.now()) }

    var goalDialogVisible by remember { mutableStateOf(true) }
    var addDialogVisible by remember { mutableStateOf(false) }

    val pagerState = rememberPagerState()
    val tabPagerState = rememberPagerState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { addDialogVisible = true }
            ) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = "add goal")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        Column {
            TabMonthHeader(currentDayId)
            HorizontalPager(
                count = Int.MAX_VALUE,
                reverseLayout = true,
                state = tabPagerState
            ) { page ->
                val date = LocalDate.now().minusWeeks(page.toLong())
                DaysOfWeekTabPage(
                    startDate = date.getStartOfWeek(),
                    currentDate = currentDayId,
                    onDateChange = {
                        newDate -> currentDayId = newDate
                    }
                )
            }
            Box {
                HorizontalPager(
                    count = Int.MAX_VALUE,
                    state = pagerState,
                    reverseLayout = true
                ) { page ->
                    DailyStepsPage(index = page)
                }
                DailyStepInfo(date = currentDayId, onSwitchGoal = { goalDialogVisible = true })
            }
        }

        SwitchGoalDialog(
            date = currentDayId,
            visible = goalDialogVisible,
            onCancel = { goalDialogVisible = false }
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
            val date = LocalDate.now().minusDays(page.toLong())
            currentDayId = date
        }
    }
    LaunchedEffect(tabPagerState) {
        snapshotFlow { tabPagerState.currentPage }.collect { page ->
            val date = LocalDate.now().minusWeeks(page.toLong()).withDayOfWeek(currentDayId.getIsoDayOfWeek())

            if (!date.isInSameWeek(currentDayId)) {
                currentDayId = if (!date.isAfter(LocalDate.now())) date else LocalDate.now()
            }
        }
    }
    LaunchedEffect(currentDayId) {
        val dayDiff = (LocalDate.now().toEpochDay() - currentDayId.toEpochDay()).toInt()
        val weekDiff = (
                (LocalDate.now().getStartOfWeek().plusDays(6).toEpochDay() - currentDayId.toEpochDay()) / 7
            ).toInt()

        pagerState.animateScrollToPage(
            dayDiff
        )
        tabPagerState.animateScrollToPage(
            weekDiff
        )
    }
}