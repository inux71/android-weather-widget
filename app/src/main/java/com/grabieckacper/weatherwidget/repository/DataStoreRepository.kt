package com.grabieckacper.weatherwidget.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepository @Inject constructor(private val dataStore: DataStore<Preferences>) {
    suspend fun <T> write(key: Preferences.Key<T>, value: T) {
        dataStore.edit { mutablePreferences: MutablePreferences ->
            mutablePreferences[key] = value
        }
    }

    suspend fun <T> read(key: Preferences.Key<T>, defaultValue: T): Flow<T> {
        return dataStore.data.map { value: Preferences ->
            val result = value[key] ?: defaultValue
            result
        }
    }

    suspend fun clear() {
        dataStore.edit { mutablePreferences: MutablePreferences ->
            mutablePreferences.clear()
        }
    }
}
