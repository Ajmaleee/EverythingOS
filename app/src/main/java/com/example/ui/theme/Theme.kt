package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val NothingColorScheme = darkColorScheme(
    primary = NothingWhite,
    onPrimary = NothingBlack,
    primaryContainer = NothingSurfaceElevated,
    onPrimaryContainer = NothingWhite,
    secondary = NothingWhiteSecondary,
    onSecondary = NothingBlack,
    secondaryContainer = NothingSurface,
    onSecondaryContainer = NothingWhiteSecondary,
    tertiary = NothingRed,
    onTertiary = NothingWhite,
    background = NothingBlack,
    onBackground = NothingWhite,
    surface = NothingDarkBackground,
    onSurface = NothingWhite,
    surfaceVariant = NothingSurface,
    onSurfaceVariant = NothingWhiteMuted,
    outline = NothingSurfaceBorder,
    outlineVariant = NothingSurfaceBorderLight
)

@Composable
fun NothingLauncherTheme(
    darkTheme: Boolean = true, // Nothing OS signature is deep AMOLED black
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window ?: return@SideEffect
            WindowCompat.setDecorFitsSystemWindows(window, false)
            val insetsController = WindowCompat.getInsetsController(window, view)
            insetsController.isAppearanceLightStatusBars = false
            insetsController.isAppearanceLightNavigationBars = false
        }
    }

    MaterialTheme(
        colorScheme = NothingColorScheme,
        typography = NothingTypography,
        content = content
    )
}
