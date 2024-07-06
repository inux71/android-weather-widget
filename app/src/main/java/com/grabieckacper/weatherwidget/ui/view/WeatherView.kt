package com.grabieckacper.weatherwidget.ui.view

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.grabieckacper.weatherwidget.R
import com.grabieckacper.weatherwidget.viewmodel.WeatherViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherView(
    viewModel: WeatherViewModel = hiltViewModel(),
    onNavigateToSelectCityView: () -> Unit,
    onNavigateToSettingsView: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = viewModel.state.value.name)
                },
                actions = {
                    IconButton(onClick = onNavigateToSettingsView) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = stringResource(id = R.string.settings_content_description)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToSelectCityView) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.search_content_description)
                )
            }
        }
    ) { innerPadding ->
        if (viewModel.state.value.isError) {
            Toast.makeText(LocalContext.current, "An error occurred!", Toast.LENGTH_SHORT)
                .show()
        }

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = viewModel.getWeatherIcon()),
                contentDescription = stringResource(id = viewModel.getContentDescription()),
                modifier = Modifier.size(64.dp)
            )

            Text(
                text = "${viewModel.state.value.weatherInfo.current.temperature}\u2103",
                fontSize = 64.sp
            )

            Text(text = viewModel.getWeatherDescription())
        }
    }
}

@Composable
@Preview(showBackground = true)
fun WeatherViewPreview() {
    WeatherView(
        onNavigateToSelectCityView = {},
        onNavigateToSettingsView = {}
    )
}
