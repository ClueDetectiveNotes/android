package com.jobseeker.cluedetectivenotes.ui.viewModel.model

import com.jobseeker.cluedetectivenotes.utils.DataMap

data class OptionUiState(
    val language: String = "KR",
    val multiLang: DataMap = DataMap(),
    val commonCode:Map<String,List<Map<String,String>>> = emptyMap(),
    val isUseDarkTheme:Boolean = true,
    val darkThemeType:String = "FOLLOW_SYSTEM",
    val blindTransparency:Float = 60.0f
)