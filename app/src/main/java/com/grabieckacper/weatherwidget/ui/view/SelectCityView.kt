package com.grabieckacper.weatherwidget.ui.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.grabieckacper.weatherwidget.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectCityView(onNavigateBackToWeatherView: () -> Unit) {
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(text = "Select City")
            },
            navigationIcon = {
                IconButton(onClick = onNavigateBackToWeatherView) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = stringResource(id = R.string.arrow_back_content_description)
                    )
                }
            }
        )
    }) { innerPadding ->
        Text(
            text = "Select City View",
            modifier = Modifier.padding(innerPadding)
        )
    }
}
