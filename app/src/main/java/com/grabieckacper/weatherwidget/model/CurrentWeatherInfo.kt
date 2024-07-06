package com.grabieckacper.weatherwidget.model

import com.google.gson.annotations.SerializedName

data class CurrentWeatherInfo(
    @SerializedName("temperature_2m")
    val temperature: Double = 0.0,
    @SerializedName("weather_code")
    val weatherCode: Int = -1
)
