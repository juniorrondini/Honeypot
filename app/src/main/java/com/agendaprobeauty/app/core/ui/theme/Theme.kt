package com.agendaprobeauty.app.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF126B5A),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFD5F4EC),
    onPrimaryContainer = Color(0xFF073B31),
    secondary = Color(0xFF5B6472),
    tertiary = Color(0xFF9A4D3D),
    background = Color(0xFFFAFBF8),
    surface = Color(0xFFFFFFFF),
    surfaceVariant = Color(0xFFE1E6DD),
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF9AD8C9),
    onPrimary = Color(0xFF00382D),
    primaryContainer = Color(0xFF0F4F43),
    onPrimaryContainer = Color(0xFFC8F2E8),
    secondary = Color(0xFFC5CAD3),
    tertiary = Color(0xFFFFB4A3),
    background = Color(0xFF121411),
    surface = Color(0xFF1A1C19),
    surfaceVariant = Color(0xFF424940),
)

@Composable
fun AgendaProBeautyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = MaterialTheme.typography,
        content = content,
    )
}
