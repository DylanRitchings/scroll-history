package uk.dylanrit.scrollhistory.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val ParchmentGold = Color(0xFFC9A24B)
private val InkNavy = Color(0xFF1E2233)

private val LightColors = lightColorScheme(
    primary = ParchmentGold,
    onPrimary = Color.White,
    secondary = InkNavy,
    background = Color(0xFFFAF7F0),
    surface = Color(0xFFFAF7F0),
    onBackground = InkNavy,
    onSurface = InkNavy
)

private val DarkColors = darkColorScheme(
    primary = ParchmentGold,
    onPrimary = Color.Black,
    secondary = Color(0xFFCBD3E8),
    background = Color(0xFF14131C),
    surface = Color(0xFF1B1A26),
    onBackground = Color(0xFFEDEAE0),
    onSurface = Color(0xFFEDEAE0)
)

@Composable
fun ScrollHistoryTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColors
        else -> LightColors
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = ScrollHistoryTypography,
        content = content
    )
}
