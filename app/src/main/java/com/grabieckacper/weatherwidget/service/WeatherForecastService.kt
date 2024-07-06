package com.grabieckacper.weatherwidget.service

import com.grabieckacper.weatherwidget.model.WeatherInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherForecastService {
    @GET("forecast?current=temperature_2m,weather_code")
    fun getWeatherInfo(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
    ): Call<WeatherInfo>
}
