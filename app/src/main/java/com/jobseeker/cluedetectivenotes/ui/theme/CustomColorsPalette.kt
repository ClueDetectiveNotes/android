package com.jobseeker.cluedetectivenotes.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class CustomColorsPalette (
    val selectedCell: Color = Color.Unspecified,
    val selectedRowAndColname: Color = Color.Unspecified
)

val LocalCustomColorsPalette = staticCompositionLocalOf { CustomColorsPalette() }