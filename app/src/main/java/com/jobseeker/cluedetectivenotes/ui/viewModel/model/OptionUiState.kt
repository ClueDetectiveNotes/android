package com.jobseeker.cluedetectivenotes.ui.viewModel.model

data class OptionUiState (
    val language: String = "KR",
    val multiLang:Map<String,String> = emptyMap(),
    val commonCode:Map<String,List<Map<String,String>>> = emptyMap(),
    val isUseDarkTheme:Boolean = true,
    val darkThemeType:String = "FOLLOW_SYSTEM"
)