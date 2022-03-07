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
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.fraserbell.keepfit.data.entities.Goal
import com.fraserbell.keepfit.navigation.Screen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.lang.Exception
import java.time.LocalDate

@ExperimentalMaterialApi
@Composable
fun SwitchGoalDialog(
    date: LocalDate,
    visible: Boolean,
    onUpdate: (date: LocalDate, goalId: Int) -> Unit,
    onCancel: () -> Unit,
    currentGoalId: Int?,
    goals: List<Goal>,
    navController: NavController
) {
    fun onSelect(goal: Goal) {
        onUpdate(date, goal.goalId)
    }

    fun onNavigate(dest: String) {
        navController.navigate(dest) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
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
                            IconButton(onClick = { onCancel() }) {
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
                                            if (currentGoalId == goal.goalId) {
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
                            if (goals.isEmpty()) {
                                Row(Modifier.fillMaxWidth()) {
                                    Column(
                                        Modifier
                                            .fillMaxWidth()
                                            .height(100.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.SpaceAround
                                    ) {
                                        Text("Add a goal to get started")
                                        Button(onClick = {
                                            onNavigate(Screen.Goals.route)
                                        }) {
                                            Text("Manage Goals")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}