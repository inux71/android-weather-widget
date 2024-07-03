package com.grabieckacper.weatherwidget

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.grabieckacper.weatherwidget.ui.theme.WeatherWidgetTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            WeatherWidgetTheme {
                val navHostController: NavHostController = rememberNavController()

                ApplicationNavHost(
                    navHostController = navHostController,
                    startDestination = "weather-view"
                )
            }
        }
    }
}
