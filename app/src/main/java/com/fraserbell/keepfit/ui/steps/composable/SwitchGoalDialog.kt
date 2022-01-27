package com.fraserbell.keepfit.ui.steps.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.fraserbell.keepfit.ui.goals.GoalsViewModel
import com.fraserbell.keepfit.ui.steps.DailyStepsViewModel
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
                        Row(Modifier.padding(16.dp)) {
                            Text("Switch Goal")
                        }
                    }
                    Surface(
                        elevation = 0.dp
                    ) {
                        Column {
                            goals.forEach { goal ->
                                ListItem(
                                    text = { Text("%,d".format(goal.stepGoal)) },
                                    secondaryText = { Text(goal.name) },
                                    icon = {
                                        Box(
                                            modifier = Modifier.height(36.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = Icons.Rounded.Check,
                                                contentDescription = "checked"
                                            )
                                        }
                                    },
                                    modifier = Modifier.clickable { }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}