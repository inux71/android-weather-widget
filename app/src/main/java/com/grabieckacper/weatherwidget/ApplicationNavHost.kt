package com.grabieckacper.weatherwidget

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.grabieckacper.weatherwidget.ui.view.SelectCityView
import com.grabieckacper.weatherwidget.ui.view.WeatherView

@Composable
fun ApplicationNavHost(
    navHostController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination
    ) {
        composable(route = "weather-view") {
            WeatherView(onNavigateToSelectCityView = {
                navHostController.navigate(route = "select-city-view")
            })
        }

        composable(route = "select-city-view") {
            SelectCityView(onNavigateBackToWeatherView = {
                navHostController.popBackStack(
                    route = "weather-view",
                    inclusive = false
                )
            })
        }
    }
}
