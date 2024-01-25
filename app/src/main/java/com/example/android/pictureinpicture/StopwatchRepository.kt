package com.example.android.pictureinpicture

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.example.android.pictureinpicture.data.PreferenceKeys
import com.example.android.pictureinpicture.data.StopwatchPreference
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class StopwatchRepository(private val dataStore: DataStore<Preferences>) {
    suspend fun fetchOnStartPreferences() =
        dataStore.data.catch {
            emit(emptyPreferences())
        }.map { preferences ->
            getStopwatchPreferences(preferences)
        }.first()

    private fun getStopwatchPreferences(preferences: Preferences): StopwatchPreference {
        val time = preferences[PreferenceKeys.STOPWATCH_TIME] ?: 0
        return StopwatchPreference(time)
    }

    suspend fun storeTime(time: Long) =
        runBlocking {
            dataStore.edit { preferences ->
                preferences[PreferenceKeys.STOPWATCH_TIME] = time
            }
        }
}
