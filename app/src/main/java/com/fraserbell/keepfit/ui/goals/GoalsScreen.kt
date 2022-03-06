package com.fraserbell.keepfit.ui.goals

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fraserbell.keepfit.data.entities.Goal
import com.fraserbell.keepfit.navigation.Screen
import com.fraserbell.keepfit.ui.goals.composable.*
import kotlinx.coroutines.runBlocking
import kotlin.math.roundToInt

@ExperimentalMaterialApi
@Composable
fun GoalsScreen(navController: NavController, vm: GoalsViewModel = hiltViewModel()) {
    val goals by vm.goals.collectAsState(null)
    val activeId by vm.activeGoal.collectAsState(initial = null)
    val allowEditing by vm.allowEditing.collectAsState(initial = true)

    Scaffold(
        scaffoldState = vm.scaffoldState.value,
        content = {
            LazyColumn(
                Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
            ) {
                goals?.forEach {
                    goal ->
                    item(goal.goalId) {
                        GoalListItem(
                            goal,
                            isActive = goal.goalId == activeId,
                            onEdit = {
                                vm.onEditClicked(it)
                            },
                            onDelete = {
                                vm.onDeleteClicked(it)
                            },
                            currentOpenItem = vm.currentOpenItem.value,
                            onSwipe = { open ->
                                if (open) {
                                    vm.currentOpenItem.value = goal.goalId
                                }
                            },
                            allowEditing = allowEditing
                        )
                        Divider()
                    }
                }
            }
            if (goals.isNullOrEmpty()) {
                Row {
                    Text(
                        "Click the + button to add a goal",
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp, 24.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
            if (vm.goalToDelete.value != null) {
                DeleteGoalConfirmDialog(
                    onDelete = {
                        vm.deleteGoalAsync(vm.goalToDelete.value!!)
                    },
                    onCancel = {
                        vm.goalToDelete.value = null
                    }
                )
            }
            AddGoalDialog(
                visible = vm.addDialogOpen.value,
                onAdd = { goal -> vm.addGoalAsync(goal) },
                onCancel = {
                    vm.addDialogOpen.value = false
                    vm.currentOpenItem.value = null
                },
                checkNameExists = { name -> vm.checkGoalNameExists(name) }
            )
            EditGoalDialog(
                onSave = { goal -> vm.updateGoalAsync(goal) },
                onCancel = {
                    vm.goalToEdit.value = null
                    vm.currentOpenItem.value = null
                },
                goal = vm.goalToEdit.value,
                checkNameExists = { name, goalId -> vm.checkGoalNameExists(name, goalId) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    vm.addDialogOpen.value = true
                }
            ) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = "add goal")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    )
}
