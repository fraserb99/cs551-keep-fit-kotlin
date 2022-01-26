package com.fraserbell.keepfit.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Goals : Screen("goals", "Goals", Icons.Rounded.Star)
    object Steps : Screen("steps", "Steps", Icons.Rounded.Home)
    object Settings : Screen("settings", "Settings", Icons.Rounded.Settings)
}
