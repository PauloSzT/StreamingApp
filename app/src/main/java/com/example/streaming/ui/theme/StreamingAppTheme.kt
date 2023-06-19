package com.example.streaming.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

val DarkColorPalette = darkColorScheme(
    primary = Color(0xFF6F7583),
    inversePrimary = Color(0xFF325CA8),
    background = Color(0xFF1B1B1F),
    onBackground = Color(0xFFE3E2E6),
    primaryContainer = Color(0xFF12448F),
    onPrimaryContainer = Color(0xFF006874),
    secondaryContainer = Color(0xFF0D8D04),
    onSecondaryContainer = Color(0xFFE1E4E9),
    secondary = Color(0xFFFFFFFF),
    onSecondary = Color(0xFF006874)
)

val LightColorPalette = lightColorScheme(
    primary = Color(0xFF325CA8),
    inversePrimary = Color(0xFF898C94),
    background = Color(0xFFFEFBFF),
    onBackground = Color(0xFF1B1B1F),
    primaryContainer = Color(0xFF12448F),
    onPrimaryContainer = Color(0xFF0D8D04),
    secondaryContainer = Color(0xFF006874),
    onSecondaryContainer = Color(0xFF030303),
    secondary = Color(0xFF006874),
    onSecondary = Color(0xFFFFFFFF),
)

@Composable
fun StreamingAppTheme(
    isDynamicColor: Boolean = true,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val dynamicColor = isDynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

    val colors = if (darkTheme) {
        if (dynamicColor) {
            dynamicDarkColorScheme(LocalContext.current)
        } else {
            DarkColorPalette
        }
    } else {
        if (dynamicColor) {
            dynamicLightColorScheme(LocalContext.current)
        } else {
            LightColorPalette
        }
    }
    MaterialTheme(
        colorScheme = colors, typography = Typography, content = content
    )
}
