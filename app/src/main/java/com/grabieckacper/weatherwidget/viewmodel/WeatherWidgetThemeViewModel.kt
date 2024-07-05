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
class WeatherWidgetThemeViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
    data class WeatherWidgetThemeViewModelState(
        val darkTheme: Boolean = false
    )

    private val _state: MutableState<WeatherWidgetThemeViewModelState> = mutableStateOf(
        WeatherWidgetThemeViewModelState()
    )
    val state: State<WeatherWidgetThemeViewModelState>
        get() = _state

    init {
        viewModelScope.launch {
            dataStoreRepository.read(
                key = booleanPreferencesKey("darkTheme"),
                defaultValue = false
            ).collect { value: Boolean ->
                _state.value = _state.value.copy(darkTheme = value)
            }
        }
    }
}
