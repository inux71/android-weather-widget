package com.grabieckacper.weatherwidget.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.grabieckacper.weatherwidget.R
import com.grabieckacper.weatherwidget.ui.component.SettingsOption
import com.grabieckacper.weatherwidget.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsView(
    viewModel: SettingsViewModel = hiltViewModel(),
    onNavigateBackToWeatherView: () -> Unit
) {
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(text = "Settings")
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
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SettingsOption(title = "Theme") {
                Switch(
                    checked = viewModel.state.value.darkMode,
                    onCheckedChange = {
                        viewModel.onCheckedChange(checked = !viewModel.state.value.darkMode)
                        viewModel.changeTheme()
                    },
                    thumbContent = {
                        Icon(
                            painter = if (viewModel.state.value.darkMode) {
                                painterResource(id = R.drawable.baseline_dark_mode_24)
                            } else {
                                painterResource(id = R.drawable.baseline_light_mode_24)
                            },
                            contentDescription = if (viewModel.state.value.darkMode) {
                                stringResource(id = R.string.dark_mode_content_description)
                            } else {
                                stringResource(id = R.string.light_mode_content_description)
                            }
                        )
                    }
                )
            }

            SettingsOption(
                title = "Clear cache",
                modifier = Modifier.clickable(onClick = {
                    viewModel.clearCache()
                })
            ) {}
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SettingsViewPreview() {
    SettingsView(
        onNavigateBackToWeatherView = {}
    )
}
