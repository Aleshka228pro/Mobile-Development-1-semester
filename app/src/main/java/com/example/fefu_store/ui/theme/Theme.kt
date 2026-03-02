package com.example.fefu_store.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val DarkGray = Color(0xFF333333)
val White = Color(0xFFFFFFFF)
val LightGray = Color(0xFF9A9A9A)

private val LightColorScheme = lightColorScheme(
    primary = DarkGray,
    onPrimary = Color.White,
    background = White,
    surface = White,
    onSurface = Color.Black
)

@Composable
fun FEFU_StoreTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
