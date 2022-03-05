package com.fraserbell.keepfit.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.fraserbell.keepfit.ui.goals.GoalsScreen
import com.fraserbell.keepfit.ui.history.HistoryScreen
import com.fraserbell.keepfit.ui.settings.SettingsScreen
import com.fraserbell.keepfit.ui.steps.StepsScreen
import com.google.accompanist.pager.ExperimentalPagerApi
import java.time.LocalDate

val items = listOf(Screen.Goals, Screen.Steps, Screen.History)

@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun KeepFitBottomNav() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentScreen = items.find { it.route == currentDestination?.route }

    Scaffold(
        topBar = {
            currentScreen?.let {
                if (!it.customTopBar) {
                    TopAppBar(
                        title = { Text(it.title) },
                        actions = {
                            IconButton(onClick = { navController.navigate(Screen.Settings.route) }) {
                                Icon(
                                    imageVector = Icons.Rounded.Settings,
                                    contentDescription = "settings"
                                )
                            }
                        }
                    )
                }
            }
        },
        bottomBar =  {
            BottomNavigation {
                items.forEach { screen ->
                    BottomNavigationItem(
                        icon = { screen.icon?.let { Icon(it, contentDescription = null) } },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        label = { Text(screen.title) }
                    )
                }
            }
        }
    ) {
        NavHost(navController, startDestination = Screen.Steps.route, Modifier.padding(it)) {
            composable(
                Screen.Steps.route,
                arguments = listOf(navArgument("initialDate") {
                    type = NavType.LongType
                    defaultValue = 0L
                })
            ) { backNavigation ->
                StepsScreen(navController, backNavigation.arguments?.getLong("initialDate"))
            }
            composable(Screen.Goals.route) {
                GoalsScreen(navController)
            }
            composable(Screen.Settings.route) {
                SettingsScreen(navController)
            }
            composable(Screen.History.route) {
                HistoryScreen(navController)
            }
        }
    }
}