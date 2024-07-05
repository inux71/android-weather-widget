package com.grabieckacper.weatherwidget.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.grabieckacper.weatherwidget.model.City
import com.grabieckacper.weatherwidget.model.GeocodingResponse
import com.grabieckacper.weatherwidget.service.GeocodingService
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SelectCityViewModel @Inject constructor(private val service: GeocodingService) : ViewModel() {
    data class SelectCityViewModelState(
        val query: String = "",
        val active: Boolean = false,
        val isError: Boolean = false,
        val cities: List<City> = emptyList()
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

    fun searchCities() {
        service.getCities(_state.value.query)
            .enqueue(object : Callback<GeocodingResponse> {
                override fun onResponse(call: Call<GeocodingResponse>, response: Response<GeocodingResponse>) {
                    if (response.isSuccessful) {
                        val geocodingResponse: GeocodingResponse? = response.body()
                        val cities: List<City> = geocodingResponse?.results ?: emptyList()

                        _state.value = _state.value.copy(cities = cities)
                    } else {
                        _state.value = _state.value.copy(isError = true)

                        Log.e("[onResponse]", response.code().toString())
                    }
                }

                override fun onFailure(call: Call<GeocodingResponse>, t: Throwable) {
                    _state.value = _state.value.copy(isError = true)

                    Log.e("[onFailure]", t.localizedMessage ?: "")
                }
            })
    }

    fun clearCities() {
        _state.value = _state.value.copy(cities = emptyList())
    }
}
