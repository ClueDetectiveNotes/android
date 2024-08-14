package com.jobseeker.cluedetectivenotes.ui.viewModel.store.option

import com.jobseeker.cluedetectivenotes.ui.viewModel.model.OptionUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface OptionActionStore {
    val _uiState : MutableStateFlow<OptionUiState>

    fun parseLanguage(language:String)
    fun parseMultiLang(multiLang:Map<String,String>)
}