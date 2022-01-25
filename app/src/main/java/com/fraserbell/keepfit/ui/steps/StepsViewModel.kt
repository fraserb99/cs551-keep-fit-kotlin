package com.fraserbell.keepfit.ui.steps

import androidx.lifecycle.ViewModel
import com.fraserbell.keepfit.data.dao.GoalDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StepsViewModel @Inject constructor(private val goalDao: GoalDao) : ViewModel() {

}