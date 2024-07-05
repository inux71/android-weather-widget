package com.grabieckacper.weatherwidget.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grabieckacper.weatherwidget.repository.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
    data class WeatherViewModelState(
        val name: String = ""
    )

    private val _state: MutableState<WeatherViewModelState> = mutableStateOf(WeatherViewModelState())
    val state: State<WeatherViewModelState>
        get() = _state

    init {
        viewModelScope.launch {
            dataStoreRepository.read(
                key = stringPreferencesKey("name"),
                defaultValue = ""
            ).collect { values: String ->
                _state.value = _state.value.copy(name = values)
            }
        }
    }
}
