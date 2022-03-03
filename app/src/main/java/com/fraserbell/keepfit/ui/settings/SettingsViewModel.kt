package com.fraserbell.keepfit.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fraserbell.keepfit.data.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val dataStoreManager: DataStoreManager) : ViewModel() {
    val goalsEditable = dataStoreManager.goalsEditable
    val historyRecording = dataStoreManager.historyRecording

    suspend fun setGoalsEditableAsync(value: Boolean) = viewModelScope.async {
        dataStoreManager.setGoalsEditable(value)
    }

    suspend fun setHistoryRecordingAsync(value: Boolean) = viewModelScope.async {
        dataStoreManager.setHistoryRecording(value)
    }
}