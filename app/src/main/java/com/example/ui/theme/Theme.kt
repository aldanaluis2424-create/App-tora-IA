package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = GoldPrimaryDark,
    onPrimary = GoldOnPrimaryDark,
    primaryContainer = GoldContainerDark,
    onPrimaryContainer = OnGoldContainerDark,
    secondary = RoyalNavySecondaryDark,
    onSecondary = OnRoyalNavySecondaryDark,
    secondaryContainer = SecondaryContainerDark,
    onSecondaryContainer = OnSecondaryContainerDark,
    background = ParchmentBackgroundDark,
    onBackground = OnParchmentDark,
    surface = ParchmentSurfaceDark,
    onSurface = OnParchmentDark,
    surfaceVariant = ParchmentSurfaceVariantDark,
    onSurfaceVariant = OnParchmentDark
)

private val LightColorScheme = lightColorScheme(
    primary = GoldPrimaryLight,
    onPrimary = GoldOnPrimaryLight,
    primaryContainer = GoldContainerLight,
    onPrimaryContainer = OnGoldContainerLight,
    secondary = RoyalNavySecondaryLight,
    onSecondary = OnRoyalNavySecondaryLight,
    secondaryContainer = SecondaryContainerLight,
    onSecondaryContainer = OnSecondaryContainerLight,
    background = ParchmentBackgroundLight,
    onBackground = OnParchmentLight,
    surface = ParchmentSurfaceLight,
    onSurface = OnParchmentLight,
    surfaceVariant = ParchmentSurfaceVariantLight,
    onSurfaceVariant = OnParchmentLight
)

@Composable
fun TorahIaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Set false to maintain rich parchment gold branding
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
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

