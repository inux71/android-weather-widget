package com.grabieckacper.weatherwidget.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grabieckacper.weatherwidget.R
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

    fun getWeatherDescription(): String {
        return when(_state.value.weatherInfo.current.weatherCode) {
            0 -> "Clear sky"
            1 -> "Mainly clear"
            2 -> "Partly cloudy"
            3 -> "Overcast"
            45 -> "Fog"
            48 -> "Depositing rime fog"
            51 -> "Drizzle: Light"
            53 -> "Drizzle: Moderate"
            55 -> "Drizzle: Dense"
            56 -> "Freezing Drizzle: Light"
            57 -> "Freezing Drizzle: Dense"
            61 -> "Rain: Slight"
            63 -> "Rain: Moderate"
            65 -> "Rain: Heavy"
            66 -> "Freezing Rain: Light"
            67 -> "Freezing Rain: Heavy"
            71 -> "Snow fall: Slight"
            73 -> "Snow fall: Moderate"
            75 -> "Snow fall: Heavy"
            77 -> "Snow grains"
            80 -> "Rain showers: Slight"
            81 -> "Rain showers: Moderate"
            82 -> "Rain showers: Violent"
            85 -> "Snow showers: Slight"
            86 -> "Snow showers: Heavy"
            95 -> "Thunderstorm: Slight or moderate"
            96 -> "Thunderstorm with slight hail"
            99 -> "Thunderstorm with heavy hail"
            else -> ""
        }
    }

    fun getWeatherIcon(): Int {
        return when(_state.value.weatherInfo.current.weatherCode) {
            0, 1 -> R.drawable.outline_clear_day_24
            2 -> R.drawable.outline_partly_cloudy_day_24
            3 -> R.drawable.outline_cloud_24
            45, 48 -> R.drawable.outline_foggy_24
            51, 53, 55, 56, 57, 61 -> R.drawable.outline_rainy_24
            63 -> R.drawable.outline_rainy_light_24
            65 -> R.drawable.outline_rainy_heavy_24
            66, 67 -> R.drawable.outline_rainy_snow_24
            71 -> R.drawable.outline_weather_snowy_24
            73 -> R.drawable.outline_snowing_24
            75 -> R.drawable.outline_snowing_heavy_24
            77 -> R.drawable.outline_grain_24
            80, 81, 82 -> R.drawable.outline_snowing_24
            85, 86 -> R.drawable.outline_snowing_heavy_24
            95, 96, 99 -> R.drawable.outline_thunderstorm_24
            else -> R.drawable.outline_clear_day_24
        }
    }

    fun getContentDescription(): Int {
        return when(_state.value.weatherInfo.current.weatherCode) {
            0, 1 -> R.string.clear_day_content_description
            2 -> R.string.partly_cloudy_day_content_description
            3 -> R.string.cloud_content_description
            45, 48 -> R.string.foggy_content_description
            51, 53, 55, 56, 57, 61 -> R.string.rainy_content_description
            63 -> R.string.rainy_light_content_description
            65 -> R.string.rainy_heavy_content_description
            66, 67 -> R.string.rainy_snow_content_description
            71 -> R.string.weather_snowy_content_description
            73 -> R.string.snowing_content_description
            75 -> R.string.snowing_heavy_content_description
            77 -> R.string.grain_content_description
            80, 81, 82 -> R.string.snowing_content_description
            85, 86 -> R.string.snowing_heavy_content_description
            95, 96, 99 -> R.string.thunderstorm_content_description
            else -> R.string.clear_day_content_description
        }
    }
}
