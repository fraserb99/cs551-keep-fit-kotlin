package com.fraserbell.keepfit.ui.goals.composable

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.fraserbell.keepfit.data.entities.Goal
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.Exception

@Composable
fun EditGoalDialog(
    goal: Goal?,
    onSave: (goal: Goal) -> Deferred<Unit>,
    onCancel: () -> Unit,
    checkNameExists: (name: String, currentGoalId: Int) -> Deferred<Boolean>
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    if (goal != null) {
        GoalFormDialog(
            title = "Add Goal",
            onSave = { values ->
                scope.launch {
                    try {
                        if (values.stepGoal == null) throw Exception("Step goal is null")

                        val updated = Goal(goalId = goal.goalId, name = values.name, stepGoal = values.stepGoal)
                        onSave(updated).await()
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
            initialValues = GoalFormValues(goal.name, goal.stepGoal),
            checkNameExists = { name -> checkNameExists(name, goal.goalId) }
        )
    }
}