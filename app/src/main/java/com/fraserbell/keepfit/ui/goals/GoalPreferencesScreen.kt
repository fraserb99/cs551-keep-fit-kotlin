package com.fraserbell.keepfit.ui.goals

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.Exception

@ExperimentalMaterialApi
@Composable
fun GoalPreferencesScreen(navController: NavController, vm: GoalPrefsViewModel = hiltViewModel()) {
    val scope = rememberCoroutineScope()
    val goalsEditable by vm.goalsEditable.collectAsState(initial = false)
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
        }
    }
}