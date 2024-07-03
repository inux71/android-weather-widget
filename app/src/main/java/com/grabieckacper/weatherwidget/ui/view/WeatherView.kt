package com.grabieckacper.weatherwidget.ui.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.grabieckacper.weatherwidget.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherView(onNavigateToSelectCityView: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Gliwice")
            })
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
        Text(
            text = "Weather View",
            modifier = Modifier.padding(innerPadding)
        )
    }
}
