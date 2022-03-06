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
fun DeleteGoalConfirmDialog (onDelete: () -> Unit, onCancel: () -> Unit) {
    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text("Are you sure you want to delete this Goal?") },
        confirmButton = {
            Button(
                onClick = {
                    onDelete()
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