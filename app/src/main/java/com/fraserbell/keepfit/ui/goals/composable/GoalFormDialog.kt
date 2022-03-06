package com.fraserbell.keepfit.ui.goals.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.fraserbell.keepfit.ui.goals.GoalsViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class GoalFormValues(
    val name: String,
    val stepGoal: Int? = null
)

@Composable
fun GoalFormDialog(
    title: String,
    initialValues: GoalFormValues = GoalFormValues(""),
    onSave: (values: GoalFormValues) -> Unit,
    onCancel: () -> Unit,
    checkNameExists: (name: String) -> Deferred<Boolean>
) {
    val scope = rememberCoroutineScope()

    var nameFieldValue by remember { mutableStateOf(TextFieldValue(initialValues.name, TextRange(initialValues.name.length))) }
    var nameFieldError by remember { mutableStateOf<String?>(null) }

    val stepStringVal = initialValues.stepGoal?.toString() ?: ""
    var stepValue by remember { mutableStateOf(TextFieldValue(stepStringVal, TextRange(stepStringVal.length))) }
    var stepFieldError by remember { mutableStateOf<String?>(null) }

    val focusRequester = remember { FocusRequester() }

    fun onSubmit() = scope.launch {
        val intStepValue = stepValue.text.toIntOrNull()
        stepFieldError = when {
            intStepValue == null -> {
                "Enter a step goal"
            }
            intStepValue <= 0 -> {
                "Please enter a goal greater than 0 steps"
            }
            intStepValue > 100000 -> {
                "Please enter a goal of less than 100,000 steps"
            } else -> {
                null
            }
        }

        val nameExists = checkNameExists(nameFieldValue.text).await()
        nameFieldError = when {
            nameFieldValue.text.isEmpty() -> {
                "Please enter a name for the Goal"
            }
            nameExists -> {
                "A goal with this name already exists"
            }
            else -> null
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
                Spacer(modifier = Modifier.height(8.dp))
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
                onClick = { onSubmit() },
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
        focusRequester.requestFocus()
    }
}