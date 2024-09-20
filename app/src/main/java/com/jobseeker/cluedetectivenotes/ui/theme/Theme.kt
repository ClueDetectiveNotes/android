package com.jobseeker.cluedetectivenotes.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jobseeker.cluedetectivenotes.ui.viewModel.OptionViewModel

private val DarkColorScheme = darkColorScheme(
        primary = Purple80,
        secondary = PurpleGrey80,
        tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
        primary = Purple40,
        secondary = PurpleGrey40,
        tertiary = Pink40

        /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

private val OnLightCustomColorsPalette = CustomColorsPalette(
    selectedCell = Color(android.graphics.Color.parseColor("#feffba")),
    selectedRowAndColname = Color(android.graphics.Color.parseColor("#00ffba"))
)

private val OnDarkCustomColorsPalette = CustomColorsPalette(
    selectedCell = Color(android.graphics.Color.parseColor("#b7a903")),
    selectedRowAndColname = Color(android.graphics.Color.parseColor("#3f907b"))
)

@Composable
fun ClueDetectiveNotesTheme(
        darkTheme: Boolean = isSystemInDarkTheme(),
        optionViewModel: OptionViewModel = viewModel(),
        // Dynamic color is available on Android 12+
        dynamicColor: Boolean = true,
        content: @Composable () -> Unit
) {
    val uiState = optionViewModel.store.uiState.collectAsState()
    val isUseDarkTheme = uiState.value.isUseDarkTheme
    val darkThemeType = uiState.value.darkThemeType
    val isDarkTheme = if(isUseDarkTheme) { if(darkThemeType == "FOLLOW_SYSTEM") darkTheme else true } else false

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (isDarkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        isDarkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = isDarkTheme
        }
    }

    val customColorsPalette = if(isDarkTheme) OnDarkCustomColorsPalette else OnLightCustomColorsPalette

    CompositionLocalProvider(LocalCustomColorsPalette provides customColorsPalette) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}