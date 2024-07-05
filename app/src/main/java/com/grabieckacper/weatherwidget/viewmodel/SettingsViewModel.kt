package com.grabieckacper.weatherwidget.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grabieckacper.weatherwidget.repository.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
    data class SettingsViewModelState(
        val darkMode: Boolean = false
    )

    private val _state: MutableState<SettingsViewModelState> = mutableStateOf(SettingsViewModelState())
    val state: State<SettingsViewModelState>
        get() = _state

    init {
        viewModelScope.launch {
            dataStoreRepository.read(
                key = booleanPreferencesKey("darkTheme"),
                defaultValue = false
            ).collect { value: Boolean ->
                _state.value = _state.value.copy(darkMode = value)
            }
        }
    }

    fun onCheckedChange(checked: Boolean) {
        _state.value = _state.value.copy(darkMode = checked)
    }

    fun changeTheme() {
        viewModelScope.launch {
            dataStoreRepository.write(
                key = booleanPreferencesKey("darkTheme"),
                value = _state.value.darkMode
            )
        }
    }

    fun clearCache() {
        viewModelScope.launch {
            dataStoreRepository.clear()
        }
    }
}
