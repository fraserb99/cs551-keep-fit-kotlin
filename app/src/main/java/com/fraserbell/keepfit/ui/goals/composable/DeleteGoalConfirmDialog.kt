package com.fraserbell.keepfit.ui.goals.composable

import android.widget.Toast
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Exception

@Composable
fun DeleteGoalConfirmDialog (onDelete: () -> Deferred<Unit>, onCancel: () -> Unit) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text("Are you sure you want to delete this Goal?") },
        confirmButton = {
            Button(
                onClick = {
                    scope.launch {
                        try {
                            onDelete().await()
                            onCancel()
                        } catch (e: Exception) {
                            print("Caught exception")
                            Toast.makeText(context, "There was an error, please try again", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                colors = ButtonDefaults
                    .buttonColors(backgroundColor = MaterialTheme.colors.error)
            ) {
                Text("Delete")
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
}