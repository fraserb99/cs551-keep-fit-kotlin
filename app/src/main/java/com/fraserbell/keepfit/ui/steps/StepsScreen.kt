package com.fraserbell.keepfit.ui.steps

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fraserbell.keepfit.ui.steps.composable.AddStepsDialog
import com.fraserbell.keepfit.ui.steps.composable.DailyStepsPage
import com.fraserbell.keepfit.ui.steps.composable.DaysOfWeekTabPage
import com.fraserbell.keepfit.ui.steps.composable.StepBar
import com.fraserbell.keepfit.util.toInt
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import java.lang.Exception
import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.roundToInt

fun LocalDate.getStartOfWeek(): LocalDate {
    val date = LocalDate.from(this)
    val dayOfWeek = date.dayOfWeek.value
    when {
        dayOfWeek == 1 -> {
            return date.minusDays(6)
        }
        dayOfWeek > 2 -> {
            return date.minusDays((dayOfWeek - 1).toLong())
        }
        else -> return date
    }
}

@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun StepsScreen(navController: NavController, initialDate: Int?, vm: StepsViewModel = hiltViewModel()) {
    var currentDayId by remember { mutableStateOf(initialDate ?: Date().toInt()) }
    var addDialogVisible by remember { mutableStateOf(false) }

    val pagerState = rememberPagerState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("26/01/2020") },
                actions = {
                    Button(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Rounded.DateRange, contentDescription = "change date")
                    }
                }
            )
        },
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
            HorizontalPager(count = Int.MAX_VALUE, reverseLayout = true) { page ->
                val date = LocalDate.now().minusWeeks(page.toLong())
                val currentDate = try {
                    LocalDate.parse(
                        currentDayId.toString(),
                        DateTimeFormatter.ofPattern("yyyyMMdd")
                    )
                } catch (e: Exception) {
                    LocalDate.now()
                }
                DaysOfWeekTabPage(startDate = date.getStartOfWeek(), currentDate = currentDate)
            }
            Divider()
            HorizontalPager(
                count = Int.MAX_VALUE,
                state = pagerState,
                reverseLayout = true
            ) { page ->
                DailyStepsPage(index = page)
            }
        }

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

    LaunchedEffect(pagerState.currentPage) {
        currentDayId = Date.from(Date().toInstant().minus(Duration.ofDays(pagerState.currentPage.toLong()))).toInt()
    }
}