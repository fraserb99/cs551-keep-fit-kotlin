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
    val goals = vm.goals.collectAsState(null)
    val openDialog = remember { mutableStateOf(false) }
    val goalToDelete = remember { mutableStateOf<Goal?>(null) }
    var addDialogVisible by remember { mutableStateOf(false) }
    var goalToEdit by remember { mutableStateOf<Goal?>(null) }
    var currentOpenItem by remember { mutableStateOf<Int?>(null) }
    val listState = rememberLazyListState()
    val allowEditing by vm.allowEditing.collectAsState(initial = true)

    Scaffold(
        content = {
            LazyColumn(
                Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                state = listState,
            ) {
                goals.value?.forEach {
                    goal ->
                    item(goal.goalId) {
                        GoalListItem(
                            goal,
                            onEdit = {
                                goalToEdit = goal
                            },
                            onDelete = {
                                openDialog.value = true
                                goalToDelete.value = goal
                            },
                            currentOpenItem = currentOpenItem,
                            onSwipe = { open ->
                                if (open) {
                                    currentOpenItem = goal.goalId
                                }
                            },
                            allowEditing = allowEditing
                        )
                        Divider()
                    }
                }
            }
            if (openDialog.value) {
                DeleteGoalConfirmDialog(
                    onDelete = {
                        vm.deleteGoalAsync(goalToDelete.value!!)
                    },
                    onCancel = {
                        openDialog.value = false
                        goalToDelete.value = null
                    }
                )
            }
            AddGoalDialog(
                visible = addDialogVisible,
                onAdd = { goal -> vm.addGoalAsync(goal) },
                onCancel = {
                    addDialogVisible = false
                    currentOpenItem = null
                }
            )
            EditGoalDialog(
                onSave = { goal -> vm.updateGoalAsync(goal) },
                onCancel = {
                    goalToEdit = null
                    currentOpenItem = null
                },
                goal = goalToEdit
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
    )
}
