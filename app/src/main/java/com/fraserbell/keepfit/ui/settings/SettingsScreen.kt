package com.fraserbell.keepfit.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import java.lang.Exception

@ExperimentalMaterialApi
@Composable
fun SettingsScreen(navController: NavController, vm: SettingsViewModel = hiltViewModel()) {
    val scope = rememberCoroutineScope()
    val goalsEditable by vm.goalsEditable.collectAsState(initial = false)
    val historyRecording by vm.historyRecording.collectAsState(initial = false)
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Goal Preferences") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "back")
                    }
                }
            )
        },
        scaffoldState = scaffoldState
    ) {
        Column {
            ListItem(
                text = { Text("Allow Goal Editing") },
                trailing = {
                    Switch(
                        goalsEditable,
                        onCheckedChange =  {
                            value -> scope.launch {
                                try {
                                    vm.setGoalsEditableAsync(value).await()
                                } catch (e: Exception) {
                                    scaffoldState.snackbarHostState.showSnackbar(
                                        message = "",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            }
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = MaterialTheme.colors.primary
                        )
                    )
                }
            )
            ListItem(
                text = { Text("Allow Historical Activity Recording") },
                trailing = {
                    Switch(
                        historyRecording,
                        onCheckedChange =  {
                            value -> scope.launch {
                                try {
                                    vm.setHistoryRecordingAsync(value).await()
                                } catch (e: Exception) {
                                    scaffoldState.snackbarHostState.showSnackbar(
                                        message = "",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            }
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = MaterialTheme.colors.primary
                        )
                    )
                }
            )
        }
    }
}