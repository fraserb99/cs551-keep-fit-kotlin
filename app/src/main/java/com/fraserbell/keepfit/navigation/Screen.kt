package com.fraserbell.keepfit.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val customTopBar: Boolean, val icon: ImageVector? = null) {
    object Goals : Screen(
        "goals",
        "Manage Goals",
        false,
        Icons.Rounded.Star
    )
    object Settings : Screen(
        "settings",
        "Settings",
        false
    )
    object Steps : Screen(
        "steps?initialDate={initialDate}",
        "Activity",
        false,
        Icons.Rounded.Home
    )
    object History : Screen(
        "history",
        "History",
        true,
        Icons.Rounded.DateRange
    )
}
