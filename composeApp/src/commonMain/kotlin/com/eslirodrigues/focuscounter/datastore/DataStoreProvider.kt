package com.eslirodrigues.focuscounter.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreProvider(private val dataStore: DataStore<Preferences>) {

    companion object {
        val IS_SOUND_ENABLED = booleanPreferencesKey("is_sound_enabled")
        val IS_COUNT_VISIBLE = booleanPreferencesKey("is_count_visible")
        val IS_RANDOM_COLOR_ENABLED = booleanPreferencesKey("is_random_color_enabled")
    }

    val isSoundEnabled: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[IS_SOUND_ENABLED] ?: false
    }

    val isCountVisible: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[IS_COUNT_VISIBLE] ?: true
    }

    val isRandomColorEnabled: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[IS_RANDOM_COLOR_ENABLED] ?: false
    }

    suspend fun setSoundEnabled(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_SOUND_ENABLED] = enabled
        }
    }

    suspend fun setCountVisible(visible: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_COUNT_VISIBLE] = visible
        }
    }

    suspend fun setRandomColorEnabled(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_RANDOM_COLOR_ENABLED] = enabled
        }
    }
}
