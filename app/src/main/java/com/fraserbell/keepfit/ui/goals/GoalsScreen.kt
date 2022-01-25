package com.fraserbell.keepfit.ui.goals

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun GoalsScreen(vm: GoalsViewModel = hiltViewModel()) {
    Column(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        Text(text = "Test")
    }
}
