package com.fraserbell.keepfit.ui.steps.composable

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.fraserbell.keepfit.data.entities.Goal
import com.fraserbell.keepfit.ui.goals.GoalsViewModel
import com.fraserbell.keepfit.ui.steps.DailyStepsViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import java.time.LocalDate

@ExperimentalMaterialApi
@Composable
fun SwitchGoalDialog(
    date: LocalDate,
    visible: Boolean,
    onCancel: () -> Unit,
    goalsViewModel: GoalsViewModel = hiltViewModel(),
    dailyStepsViewModel: DailyStepsViewModel = hiltViewModel()
) {
    val goals by goalsViewModel.goals.collectAsState(initial = listOf())
    val day by dailyStepsViewModel.getStepsForDate(date).collectAsState(null)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    fun onSelect(goal: Goal) {
        scope.launch {
            try {
                dailyStepsViewModel.updateDailyGoal(date, goal.goalId)
            } catch (e: Exception) {
                Toast.makeText(
                    context,
                    "There was a problem switching goal, please try again",
                    Toast.LENGTH_SHORT
                ).show()
            } finally {
                onCancel()
            }
        }
    }

    if (visible) {
        Dialog(onDismissRequest = onCancel) {
            Surface(
                elevation = 1.dp
            ) {
                Column {
                    Surface(
                        Modifier.fillMaxWidth(),
                        elevation = 4.dp,
                    ) {
                        Row(
                            Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Switch Goal")
                            Surface(
                                Modifier
                                    .clickable { onCancel() },
                                shape = MaterialTheme.shapes.medium
                            ) {
                                Icon(
                                    modifier = Modifier.padding(8.dp),
                                    imageVector = Icons.Rounded.Close,
                                    contentDescription = "close"
                                )
                            }
                        }
                    }
                    Divider()
                    Surface(
                        elevation = 0.dp
                    ) {
                        Column(
                            Modifier
                                .heightIn(0.dp, 396.dp)
                                .verticalScroll(rememberScrollState())
                        ) {
                            goals.forEach { goal ->
                                ListItem(
                                    text = { Text("%,d".format(goal.stepGoal)) },
                                    secondaryText = { Text(goal.name) },
                                    icon = {
                                        Box(
                                            modifier = Modifier.height(36.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            if (day?.goal?.goalId == goal.goalId) {
                                                Icon(
                                                    imageVector = Icons.Rounded.Check,
                                                    contentDescription = "checked"
                                                )
                                            }
                                        }
                                    },
                                    modifier = Modifier.clickable { onSelect(goal) }
                                )
                                Divider()
                            }
                        }
                    }
                }
            }
        }
    }
}