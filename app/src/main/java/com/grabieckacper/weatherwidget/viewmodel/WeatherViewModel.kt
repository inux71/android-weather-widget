package com.grabieckacper.weatherwidget.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grabieckacper.weatherwidget.model.WeatherInfo
import com.grabieckacper.weatherwidget.repository.DataStoreRepository
import com.grabieckacper.weatherwidget.service.WeatherForecastService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherForecastService: WeatherForecastService,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
    data class WeatherViewModelState(
        val name: String = "",
        val latitude: Double = 0.0,
        val longitude: Double = 0.0,
        val isError: Boolean = false,
        val weatherInfo: WeatherInfo = WeatherInfo()
    )

    private val _state: MutableState<WeatherViewModelState> = mutableStateOf(WeatherViewModelState())
    val state: State<WeatherViewModelState>
        get() = _state

    init {
        viewModelScope.launch {
            dataStoreRepository.read(
                key = stringPreferencesKey("name"),
                defaultValue = ""
            ).collect { value: String ->
                _state.value = _state.value.copy(name = value)
            }
        }

        viewModelScope.launch {
            dataStoreRepository.read(
                key = doublePreferencesKey("latitude"),
                defaultValue = 0.0
            ).collect { value: Double ->
                _state.value = _state.value.copy(latitude = value)
            }
        }

        viewModelScope.launch {
            dataStoreRepository.read(
                key = doublePreferencesKey("longitude"),
                defaultValue = 0.0
            ).collect { value: Double ->
                _state.value = _state.value.copy(longitude = value)

                getWeatherInfo()
            }
        }
    }

    private fun getWeatherInfo() {
        weatherForecastService.getWeatherInfo(
            latitude = _state.value.latitude,
            longitude = _state.value.longitude
        ).enqueue(object : Callback<WeatherInfo> {
            override fun onResponse(call: Call<WeatherInfo>, response: Response<WeatherInfo>) {
                if (response.isSuccessful) {
                    val weatherInfo: WeatherInfo = response.body() ?: WeatherInfo()

                    _state.value = _state.value.copy(weatherInfo = weatherInfo)
                } else {
                    _state.value = _state.value.copy(isError = true)

                    Log.e("[onResponse]", response.code().toString())
                }
            }

            override fun onFailure(call: Call<WeatherInfo>, t: Throwable) {
                _state.value = _state.value.copy(isError = true)

                Log.e("[onFailure]", t.localizedMessage ?: "")
            }
        })
    }
}
