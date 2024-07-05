package com.grabieckacper.weatherwidget.ui.view

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.grabieckacper.weatherwidget.R
import com.grabieckacper.weatherwidget.model.City
import com.grabieckacper.weatherwidget.ui.component.CityLabel
import com.grabieckacper.weatherwidget.viewmodel.SelectCityViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectCityView(
    viewModel: SelectCityViewModel = hiltViewModel(),
    onNavigateBackToWeatherView: () -> Unit
) {
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
        if (viewModel.state.value.isError) {
            Toast.makeText(LocalContext.current, "An error occurred!", Toast.LENGTH_SHORT)
                .show()
        }

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchBar(
                query = viewModel.state.value.query,
                onQueryChange = {
                    viewModel.onQueryChange(query = it)
                    viewModel.searchCities()
                },
                onSearch = {},
                active = viewModel.state.value.active,
                onActiveChange = {
                    viewModel.onActiveChange(active = !viewModel.state.value.active)
                },
                placeholder = {
                    Text(text = "Search city")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search, 
                        contentDescription = stringResource(id = R.string.search_content_description)
                    )
                },
                trailingIcon = {
                    if (viewModel.state.value.query.isNotEmpty()) {
                        IconButton(onClick = {
                            viewModel.onQueryChange(query = "")
                            viewModel.clearCities()
                        }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = stringResource(id = R.string.clear_content_description)
                            )
                        }
                    }
                }
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(viewModel.state.value.cities) { city: City ->
                        CityLabel(
                            name = city.name,
                            country = city.country,
                            countryCode = city.countryCode
                        ) {
                            viewModel.saveToDataStore(city = city)
                            onNavigateBackToWeatherView()
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SelectCityViewPreview() {
    SelectCityView {

    }
}
