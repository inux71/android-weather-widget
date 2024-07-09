package com.grabieckacper.weatherwidget.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.components.Scaffold
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.text.Text
import com.grabieckacper.weatherwidget.MainActivity

@Composable
fun WidgetView() {
    Scaffold(modifier = GlanceModifier.clickable(onClick = actionStartActivity<MainActivity>())) {
        Column(
            modifier = GlanceModifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "30.0\u2103")

            Text(text = "Warsaw")
        }
    }
}

@Composable
@Preview(showBackground = true)
fun WidgetViewPreview() {
    GlanceTheme {
        WidgetView()
    }
}
