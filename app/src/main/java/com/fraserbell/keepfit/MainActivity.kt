package com.fraserbell.keepfit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.room.Room
import com.fraserbell.keepfit.data.AppDatabase
import com.fraserbell.keepfit.data.entities.Goal
import com.fraserbell.keepfit.navigation.KeepFitBottomNav
import com.fraserbell.keepfit.ui.goals.GoalsViewModel
import com.fraserbell.keepfit.ui.theme.KeepFitTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    @ExperimentalPagerApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            KeepFitTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    KeepFitBottomNav()
                }
            }
        }
    }
}