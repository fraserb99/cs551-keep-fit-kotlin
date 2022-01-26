package com.fraserbell.keepfit.ui.steps.composable

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

@Composable
fun AddStepsDialog(
    onSave: (value: Int) -> Deferred<Unit>,
    onCancel: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var stepValue by remember { mutableStateOf("") }
    var stepFieldError by remember { mutableStateOf<String?>(null) }
    val focusRequester = remember { FocusRequester() }

    val onSubmit: () -> Unit = {
        val intStepValue = stepValue.toIntOrNull()
        when {
            intStepValue == null -> {
                stepFieldError = "Enter the number of steps to add"
            }
            intStepValue < 1 -> {
                stepFieldError = "Enter a positive number of steps"
            }
            else -> {
                scope.launch {
                    try {
                        onSave(intStepValue).await()
                        onCancel()
                    } catch (e: Exception) {
                        Toast.makeText(context, "There was a problem adding steps, please try again", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }

    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text("Add Steps") },
        text = {
            Column(Modifier.padding(top = 8.dp)) {
                TextField(
                    value = stepValue, onValueChange = { stepValue = it },
                    isError = stepFieldError != null,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    keyboardActions = KeyboardActions(onDone = { onSubmit() }),
                    singleLine = true,
                    modifier = Modifier.focusRequester(focusRequester)
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