package com.fraserbell.keepfit.ui.steps

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fraserbell.keepfit.ui.steps.composable.AddStepsDialog
import com.fraserbell.keepfit.ui.steps.composable.StepBar
import com.fraserbell.keepfit.util.toInt
import java.util.*

@Composable
fun StepsScreen(navController: NavController, initialDate: Int?, vm: StepsViewModel = hiltViewModel()) {
    var currentDayId by remember { mutableStateOf(initialDate ?: Date().toInt()) }
    val day by vm.getDayById(currentDayId).collectAsState(initial = null)
    var addDialogVisible by remember { mutableStateOf(false) }


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
        Column(
            Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            if (day != null) {
                day!!.stepGoal?.let { it1 -> StepBar(steps = day!!.steps, goal = it1) }
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
}