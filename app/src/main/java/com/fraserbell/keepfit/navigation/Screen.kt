package com.fraserbell.keepfit.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector? = null) {
    object Goals : Screen("goals", "Goals", Icons.Rounded.Star)
    object GoalPrefs : Screen("goals/prefs", "Goal Preferences")
    object Steps : Screen("steps?initialDate={initialDate}", "Steps", Icons.Rounded.Home)
    object History : Screen("history", "History", Icons.Rounded.DateRange)
}
