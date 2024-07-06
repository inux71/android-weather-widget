package com.grabieckacper.weatherwidget.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.grabieckacper.weatherwidget.service.GeocodingService
import com.grabieckacper.weatherwidget.service.WeatherForecastService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(produceFile = {
            context.preferencesDataStoreFile("datastore")
        })
    }

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
    @Named("weather-forecast")
    fun provideWeatherForecastRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideGeocodingService(@Named("geocoding") retrofit: Retrofit): GeocodingService {
        return retrofit.create(GeocodingService::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherForecastService(@Named("weather-forecast") retrofit: Retrofit): WeatherForecastService {
        return retrofit.create(WeatherForecastService::class.java)
    }
}
