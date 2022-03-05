package com.fraserbell.keepfit.ui.history

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fraserbell.keepfit.data.entities.DailySteps
import com.fraserbell.keepfit.navigation.Screen
import com.fraserbell.keepfit.ui.history.composable.ClearHistoryModal
import com.fraserbell.keepfit.ui.steps.composable.getProgressColour
import io.github.boguszpawlowski.composecalendar.SelectableCalendar
import io.github.boguszpawlowski.composecalendar.day.DayState
import io.github.boguszpawlowski.composecalendar.rememberSelectableCalendarState
import io.github.boguszpawlowski.composecalendar.selection.DynamicSelectionState
import io.github.boguszpawlowski.composecalendar.selection.SelectionMode
import kotlinx.coroutines.flow.Flow
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*
import kotlin.math.roundToInt

@Composable
fun HistoryScreen(navController: NavController, vm: HistoryViewModel = hiltViewModel()) {
    var currentDate by remember { vm.currentDate }
    val calendarState = rememberSelectableCalendarState(
        initialSelectionMode = SelectionMode.Single,
        onSelectionChanged = { selection -> currentDate = selection.elementAtOrNull(0) },
    )
    var showClearModal by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(Screen.History.title) },
                actions = {
                    IconButton(onClick = { showClearModal = true }) {
                        Icon(
                            Icons.Rounded.Delete,
                            "clearHistory"
                        )
                    }
                    IconButton(onClick = { navController.navigate(Screen.Settings.route) }) {
                        Icon(
                            imageVector = Icons.Rounded.Settings,
                            contentDescription = "settings"
                        )
                    }
                }
            )
        }
    ) {
        Column(
            Modifier
                .fillMaxHeight()
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            HistoryCard(currentDate, navController, vm)
            Row(Modifier.height(400.dp)) {
                SelectableCalendar(
                    Modifier
                        .padding(4.dp)
                        .animateContentSize(),
                    firstDayOfWeek = DayOfWeek.MONDAY,
                    calendarState = calendarState,
                    dayContent = {
                        DayContent(
                            dayState = it,
                            stepsState = vm.getStepsForDate(it.date)
                        )
                    }
                )
            }
        }
        if (showClearModal) {
            ClearHistoryModal(
                onDelete = { vm.clearHistory() },
                onCancel = { showClearModal = false }
            )
        }
    }
}

@Composable
fun HistoryCard(currentDate: LocalDate?, navController: NavController, vm: HistoryViewModel) {
    Row(Modifier.height(200.dp)) {
        Card(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            elevation = 4.dp
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                currentDate?.let { date ->
                    val steps by vm.getStepsForDate(date).collectAsState(initial = null)
                    val progress = steps?.let {
                        it.goal?.stepGoal?.let { goal -> it.steps.toFloat() / goal }
                    } ?: 0f

                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(30.dp)
                            .background(if (steps?.goal != null) getProgressColour(progress) else Color.Transparent)
                    )
                    Column(
                        Modifier.padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            date.dayOfMonth.toString(),
                            style = MaterialTheme.typography.h1
                        )
                        Text(
                            date.month.getDisplayName(TextStyle.FULL, Locale.UK),
                            style = MaterialTheme.typography.caption
                        )
                    }
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            steps?.let {
                                Column {
                                    Column(Modifier.padding(4.dp)) {
                                        Text("Goal", style = MaterialTheme.typography.caption)
                                        Text(
                                            it.goal?.name ?: "N/A",
                                            style = MaterialTheme.typography.h6
                                        )
                                    }
                                    Column(Modifier.padding(4.dp)) {
                                        Text("Step Goal", style = MaterialTheme.typography.caption)
                                        Text(
                                            it.goal?.stepGoal?.let { "%,d".format(it) } ?: "N/A",
                                            style = MaterialTheme.typography.h6
                                        )
                                    }
                                    Column(Modifier.padding(4.dp)) {
                                        Text("Progress", style = MaterialTheme.typography.caption)
                                        Text(
                                            it.goal?.stepGoal?.let {
                                                (progress * 100).roundToInt()
                                                    .toString() + "%"
                                            } ?: "N/A",
                                            style = MaterialTheme.typography.h6
                                        )
                                    }
                                }
                                Column(Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
                                    Column(Modifier.padding(4.dp)) {
                                        Text("Steps", style = MaterialTheme.typography.caption)
                                        Text(
                                            "%,d".format(it.steps),
                                            style = MaterialTheme.typography.h4
                                        )
                                    }
                                }
                            } ?: run {
                                Text("No Activity for this day")
                            }
                        }
                        Button(
                            modifier = Modifier
                                .align(Alignment.BottomEnd),
                            shape = RoundedCornerShape(
                                MaterialTheme.shapes.medium.topStart,
                                CornerSize(0.dp),
                                MaterialTheme.shapes.medium.bottomEnd,
                                CornerSize(0.dp)
                            ),
                            onClick = { navController.navigate("steps?initialDate=${currentDate.toEpochDay()}") }
                        ) {
                            Text("View")
                            Icon(Icons.Rounded.ArrowForward, contentDescription = "view")
                        }
                    }
                } ?: run {
                    Text(
                        "Select a date to view the history",
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun DayContent(dayState: DayState<DynamicSelectionState>, stepsState: Flow<DailySteps>) {
    val dayWithGoal by stepsState.collectAsState(initial = null)
    val selectionState = dayState.selectionState
    val isSelected = selectionState.isDateSelected(dayState.date)

    val steps = dayWithGoal?.steps
    val stepGoal = dayWithGoal?.goal?.stepGoal
    val progress = steps?.let {
        stepGoal?.let { goal -> it.toFloat() / goal }
    } ?: 0f

    val backgroundColor = if (dayState.date.isAfter(LocalDate.now()))
        Color.LightGray else MaterialTheme.colors.surface

    Card(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(2.dp)
            .clickable(enabled = !dayState.date.isAfter(LocalDate.now())) {
                selectionState.onDateSelected(dayState.date)
            },
        elevation = if (dayState.isFromCurrentMonth) 2.dp else 0.dp,
        border = if (dayState.isCurrentDay) BorderStroke(1.dp, MaterialTheme.colors.primary) else null,
        backgroundColor = backgroundColor,
        contentColor = if (isSelected) MaterialTheme.colors.primary else contentColorFor(
            backgroundColor = MaterialTheme.colors.surface
        )
    ) {
        Box(contentAlignment = Alignment.Center) {
            Box(
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = dayState.date.dayOfMonth.toString(),
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h6
                )
            }
            if (stepGoal != null) {
                Divider(
                    Modifier.align(Alignment.BottomCenter),
                    color = getProgressColour(progress),
                    thickness = 8.dp
                )
            }
        }
    }
}