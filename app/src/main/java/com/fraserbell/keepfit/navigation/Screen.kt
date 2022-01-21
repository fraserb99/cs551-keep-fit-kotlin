package com.fraserbell.keepfit.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Steps : Screen("steps", "Steps", Icons.Rounded.Home)
}
