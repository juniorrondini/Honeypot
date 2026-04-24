package com.agendaprobeauty.app.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF315FA8),
    onPrimary = Color.White,
    secondary = Color(0xFF58677F),
    tertiary = Color(0xFF6F5B7C),
    background = Color(0xFFFAFAFC),
    surface = Color(0xFFFFFFFF),
    surfaceVariant = Color(0xFFE1E5EF),
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFFAFC6FF),
    onPrimary = Color(0xFF002E69),
    secondary = Color(0xFFC0C7D7),
    tertiary = Color(0xFFD9BDE8),
    background = Color(0xFF111318),
    surface = Color(0xFF191C20),
    surfaceVariant = Color(0xFF424750),
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
