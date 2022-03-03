package com.fraserbell.keepfit.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector? = null) {
    object Goals : Screen("goals", "Manage Goals", Icons.Rounded.Star)
    object GoalPrefs : Screen("settings", "Settings")
    object Steps : Screen("steps?initialDate={initialDate}", "Activity", Icons.Rounded.Home)
    object History : Screen("history", "History", Icons.Rounded.DateRange)
}
