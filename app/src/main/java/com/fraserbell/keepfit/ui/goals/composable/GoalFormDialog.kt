package com.fraserbell.keepfit.ui.goals.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

data class GoalFormValues(
    val name: String,
    val stepGoal: Int
)

@Composable
fun GoalFormDialog(
    title: String,
    initialValues: GoalFormValues = GoalFormValues("", 0),
    onSave: (values: GoalFormValues) -> Unit,
    onCancel: () -> Unit
) {
    var nameFieldValue by remember { mutableStateOf(TextFieldValue(initialValues.name, TextRange(initialValues.name.length))) }
    var nameFieldError by remember { mutableStateOf<String?>(null) }

    val stepStringVal = initialValues.stepGoal?.toString() ?: ""
    var stepValue by remember { mutableStateOf(TextFieldValue(stepStringVal, TextRange(stepStringVal.length))) }
    var stepFieldError by remember { mutableStateOf<String?>(null) }

    val focusRequester = remember { FocusRequester() }

    val onSubmit: () -> Unit = {
        val intStepValue = stepValue.text.toIntOrNull()
        when {
            intStepValue == null -> {
                stepFieldError = "Enter a step goal"
            }
            intStepValue < 1000 -> {
                stepFieldError = "Minimum goal is 1000 steps"
            }
            intStepValue > 100000 -> {
                stepFieldError = "Please enter a goal of less than 100,000 steps"
            } else -> {
                stepFieldError = null
            }
        }

        when {
            nameFieldValue.text.isEmpty() -> {
                nameFieldError = "Please enter a name for the Goal"
            }
            else -> nameFieldError = null
        }

        if (stepFieldError == null && nameFieldError == null && intStepValue != null) {
            onSave(GoalFormValues(nameFieldValue.text, intStepValue))
        }
    }

    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text(title) },
        text = {
            Column(Modifier.padding(top = 8.dp)) {
                TextField(
                    value = nameFieldValue, onValueChange = { nameFieldValue = it },
                    label = { Text(text = "Name") },
                    isError = nameFieldError != null,
                    keyboardActions = KeyboardActions(onDone = { onSubmit() }),
                    singleLine = true,
                    modifier = Modifier.focusRequester(focusRequester)
                )
                nameFieldError?.let { error ->
                    Text(
                        error,
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.error
                    )
                }
                TextField(
                    value = stepValue, onValueChange = { stepValue = it },
                    label = { Text(text = "Step Goal") },
                    isError = stepFieldError != null,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    keyboardActions = KeyboardActions(onDone = { onSubmit() }),
                    singleLine = true,
                )
                stepFieldError?.let { error ->
                    Text(
                        error,
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.error
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onSubmit,
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(
                onClick = onCancel,
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface)
            ) {
                Text("Cancel")
            }
        }
    )

    LaunchedEffect(Unit) {
        delay(100)
        focusRequester.requestFocus()
    }
}