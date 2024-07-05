package com.grabieckacper.weatherwidget.model

import com.google.gson.annotations.SerializedName

data class City(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    @SerializedName("country_code")
    val countryCode: String,
    val country: String
)
