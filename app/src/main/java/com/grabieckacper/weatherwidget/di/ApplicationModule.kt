package com.grabieckacper.weatherwidget.di

import com.grabieckacper.weatherwidget.service.GeocodingService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {
    @Provides
    @Singleton
    @Named("geocoding")
    fun provideGeocodingRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://geocoding-api.open-meteo.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideGeocodingService(@Named("geocoding") retrofit: Retrofit): GeocodingService {
        return retrofit.create(GeocodingService::class.java)
    }
}
