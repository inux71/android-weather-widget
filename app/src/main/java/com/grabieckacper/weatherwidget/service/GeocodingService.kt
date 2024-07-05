package com.grabieckacper.weatherwidget.service

import com.grabieckacper.weatherwidget.model.GeocodingResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingService {
    @GET("search")
    fun getCities(@Query("name") name: String): Call<GeocodingResponse>
}
