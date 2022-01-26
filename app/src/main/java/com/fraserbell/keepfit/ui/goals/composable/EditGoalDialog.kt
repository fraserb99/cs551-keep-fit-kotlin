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
fun EditGoalDialog(
    goal: Goal?,
    onSave: (goal: Goal) -> Deferred<Unit>,
    onCancel: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    if (goal != null) {
        GoalFormDialog(
            title = "Add Goal",
            onSave = { stepCount ->
                scope.launch {
                    try {
                        val updated = Goal(goalId = goal.goalId, stepCount = stepCount)
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
            initialValue = goal.stepCount
        )
    }
}