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

    override fun parsCommonCode(commonCode: Map<String, List<Map<String, String>>>) {
        _uiState.update { it.copy(commonCode = commonCode) }
    }

    override fun parseDarkThemeType(darkThemeType: String) {
        _uiState.update { it.copy(darkThemeType = darkThemeType) }
    }

    override fun parseIsUseDarkTheme(isUseDarkTheme: Boolean) {
        _uiState.update { it.copy(isUseDarkTheme = isUseDarkTheme) }
    }
}