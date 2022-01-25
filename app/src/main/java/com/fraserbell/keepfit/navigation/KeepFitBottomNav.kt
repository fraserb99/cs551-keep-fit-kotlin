package com.fraserbell.keepfit.navigation

import android.provider.ContactsContract
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.fraserbell.keepfit.ui.goals.GoalsScreen
import com.fraserbell.keepfit.ui.goals.GoalsViewModel
import com.fraserbell.keepfit.ui.steps.StepsScreen

val items = listOf(Screen.Goals, Screen.Steps)

@Composable
fun KeepFitBottomNav() {
    val navController = rememberNavController()

    Scaffold( bottomBar =  {
        BottomNavigation {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            items.forEach { screen ->
                BottomNavigationItem(
                    icon = { Icon(screen.icon, contentDescription = null) },
                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    } ) {
        NavHost(navController, startDestination = Screen.Steps.route, Modifier.padding(it)) {
            composable(Screen.Steps.route) { StepsScreen(navController) }
            composable(Screen.Goals.route) {
                GoalsScreen()
            }
        }
    }
}