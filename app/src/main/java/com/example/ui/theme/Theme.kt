package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily

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

private val SepiaColorScheme = lightColorScheme(
    primary = GoldPrimaryLight,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE8D7B0),
    onPrimaryContainer = Color(0xFF2E1F00),
    secondary = Color(0xFF634D2A),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFEADBCE),
    onSecondaryContainer = Color(0xFF2B1A0E),
    background = Color(0xFFF9F1DF),
    onBackground = Color(0xFF2E2316),
    surface = Color(0xFFF5ECD8),
    onSurface = Color(0xFF2E2316),
    surfaceVariant = Color(0xFFEADECA),
    onSurfaceVariant = Color(0xFF473A2B),
    outlineVariant = Color(0xFFD3C5A8)
)

@Composable
fun TorahIaTheme(
    readerTheme: String = "Light",
    fontFamilyName: String = "SansSerif",
    fontSizeSp: Float = 16f,
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme = when (readerTheme) {
        "Dark" -> DarkColorScheme
        "Sepia" -> SepiaColorScheme
        "Light" -> LightColorScheme
        else -> {
            if (darkTheme) DarkColorScheme else LightColorScheme
        }
    }

    val resolvedFontFamily = when (fontFamilyName) {
        "Serif" -> FontFamily.Serif
        "Monospace" -> FontFamily.Monospace
        else -> FontFamily.SansSerif
    }

    val dynamicTypography = remember(resolvedFontFamily, fontSizeSp) {
        getAppTypography(fontFamily = resolvedFontFamily, fontSizeSp = fontSizeSp)
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = dynamicTypography,
        content = content
    )
}


