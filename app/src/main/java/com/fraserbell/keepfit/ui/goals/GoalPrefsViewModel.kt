package com.fraserbell.keepfit.ui.goals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fraserbell.keepfit.data.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class GoalPrefsViewModel @Inject constructor(private val dataStoreManager: DataStoreManager) : ViewModel() {
    val goalsEditable = dataStoreManager.goalsEditable

    suspend fun setGoalsEditableAsync(value: Boolean) = viewModelScope.async {
        dataStoreManager.setGoalsEditable(value)
    }
}