package com.fraserbell.keepfit.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore("settings")

private val GOALS_EDITABLE = booleanPreferencesKey("goalsEditable")
private val HISTORY_RECORDING = booleanPreferencesKey("historyRecording")

class DataStoreManager @Inject constructor(@ApplicationContext appContext: Context) {
    private val settingsDataStore = appContext.dataStore

    suspend fun setGoalsEditable(value: Boolean) {
        settingsDataStore.edit { settings ->
            settings[GOALS_EDITABLE] = value
        }
    }

    val goalsEditable: Flow<Boolean> = settingsDataStore.data.map { preferences ->
        preferences[GOALS_EDITABLE] ?: true
    }

    suspend fun setHistoryRecording(value: Boolean) {
        settingsDataStore.edit { settings ->
            settings[HISTORY_RECORDING] = value
        }
    }

    val historyRecording: Flow<Boolean> = settingsDataStore.data.map { preferences ->
        preferences[HISTORY_RECORDING] ?: false
    }
}