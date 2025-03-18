package com.nemo.cineman.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF1877F2), // Xanh dương Facebook
    onPrimary = Color.White,

    secondary = Color(0xFFB0BEC5), // Xám nhạt
    onSecondary = Color.Black,

    tertiary = Color(0xFF4A6572), // Xám xanh
    onTertiary = Color.White,

    background = Color(0xFF18191A), // Xám đen (Dark Mode)
    onBackground = Color.White,

    surface = Color(0xFF242526), // Xám đậm (Surface màu Card, Dialog)
    onSurface = Color.White,

    error = Color(0xFFD32F2F), // Đỏ tươi
    onError = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF1877F2), // Xanh dương Facebook
    onPrimary = Color.White,

    secondary = Color(0xFF757575), // Xám trung tính
    onSecondary = Color.White,

    tertiary = Color(0xFFB0BEC5), // Xám xanh nhạt
    onTertiary = Color.Black,

    background = Color(0xFFFFFFFF), // Trắng (Light Mode)
    onBackground = Color.Black,

    surface = Color(0xFFFFFFFF), // Trắng (Surface màu Card, Dialog)
    onSurface = Color.Black,

    error = Color(0xFFD32F2F), // Đỏ tươi
    onError = Color.White
)

@Composable
fun CineManTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}