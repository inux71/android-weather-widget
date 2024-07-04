package com.grabieckacper.weatherwidget.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SelectCityViewModel @Inject constructor() : ViewModel() {
    data class SelectCityViewModelState(
        val query: String = "",
        val active: Boolean = false
    )

    private val _state: MutableState<SelectCityViewModelState> = mutableStateOf(SelectCityViewModelState())
    val state: State<SelectCityViewModelState>
        get() = this._state

    fun onQueryChange(query: String) {
        _state.value = _state.value.copy(query = query)
    }

    fun onActiveChange(active: Boolean) {
        _state.value = _state.value.copy(active = active)
    }
}
