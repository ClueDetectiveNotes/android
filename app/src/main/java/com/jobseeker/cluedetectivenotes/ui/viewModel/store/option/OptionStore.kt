package com.jobseeker.cluedetectivenotes.ui.viewModel.store.option

import com.jobseeker.cluedetectivenotes.ui.viewModel.model.OptionUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class OptionStore() : OptionStateStore, OptionActionStore{
    override val _uiState: MutableStateFlow<OptionUiState> = MutableStateFlow(OptionUiState())
    override val uiState: StateFlow<OptionUiState> = _uiState.asStateFlow()

    override fun parseLanguage(language: String) {
        _uiState.update { it.copy(language = language) }
    }

    override fun parseMultiLang(multiLang: Map<String, String>) {
        _uiState.update { it.copy(multiLang = multiLang) }
    }
}