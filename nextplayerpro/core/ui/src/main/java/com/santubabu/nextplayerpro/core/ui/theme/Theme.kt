package com.santubabu.nextplayerpro.core.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

/* ---------------- BRAND FALLBACK SCHEMES ---------------- */

private val LightColorScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    outline = outlineLight,
)

private val DarkColorScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    outline = outlineDark,
)

/* ---------------- THEME ---------------- */

@Composable
fun NextPlayerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    highContrastDarkTheme: Boolean = false, // kept for compatibility
    dynamicColor: Boolean = true,            // ✅ ENABLED
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current

    val colorScheme = when {
        // Android 12+ Dynamic Color
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val dynamicScheme = if (darkTheme) {
                dynamicDarkColorScheme(context)
            } else {
                dynamicLightColorScheme(context)
            }

            // 🔒 Lock brand identity (sage stays primary/secondary)
            dynamicScheme.copy(
                primary = if (darkTheme) primaryDark else primaryLight,
                secondary = if (darkTheme) secondaryDark else secondaryLight,
            )
        }

        // Fallback (older Android)
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}