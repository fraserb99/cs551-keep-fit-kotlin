package com.fraserbell.keepfit.ui.goals.composable

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.fraserbell.keepfit.data.entities.Goal
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.launch
import java.lang.Exception

@Composable
fun AddGoalDialog(
    visible: Boolean,
    onAdd: (goal: Goal) -> Deferred<Unit>,
    onCancel: () -> Unit,
    checkNameExists: (name: String) -> Deferred<Boolean>
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    if (visible) {
        GoalFormDialog(
            title = "Add Goal",
            onSave = { values ->
                scope.launch {
                    try {
                        if (values.stepGoal == null) throw Exception("Step goal is null")

                        val goal = Goal(stepGoal = values.stepGoal, name = values.name)
                        onAdd(goal).await()
                        onCancel()
                    } catch (e: Exception) {
                        Toast.makeText(
                            context,
                            "There was an error saving the goal, please try again",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            },
            onCancel = onCancel,
            checkNameExists = { name -> checkNameExists(name) }
        )
    }
}