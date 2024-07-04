package com.grabieckacper.weatherwidget.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.grabieckacper.weatherwidget.R
import com.grabieckacper.weatherwidget.model.City

@Composable
fun CityLabel(
    city: City,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 0.dp,
                vertical = 8.dp
            )
            .clickable(onClick = onClick),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://hatscripts.github.io/circle-flags/flags/${city.countryCode.lowercase()}.svg")
                .decoderFactory(SvgDecoder.Factory())
                .build(),
            contentDescription = stringResource(id = R.string.flag_content_description),
            modifier = Modifier
                .width(32.dp)
                .height(32.dp)
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = CircleShape
                )
        )

        Text(text = city.name)

        Text(text = city.country)

        Text(text = city.admin1)
    }
}

@Composable
@Preview(showBackground = true)
fun CityLabelPreview() {
    CityLabel(
        city = City(
            name = "Berlin",
            countryCode = "DE",
            country = "Deutschland",
            admin1 = "Berlin"
        ),
        onClick = {}
    )
}
