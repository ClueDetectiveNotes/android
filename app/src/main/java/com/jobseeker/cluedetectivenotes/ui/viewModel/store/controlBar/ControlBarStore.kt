package com.jobseeker.cluedetectivenotes.ui.viewModel.store.controlBar

import com.jobseeker.cluedetectivenotes.ui.viewModel.model.ControlBarUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ControlBarStore : ControlBarStateStore, ControlBarActionStore {
    override val _uiState: MutableStateFlow<ControlBarUiState> = MutableStateFlow(ControlBarUiState())
    override val uiState: StateFlow<ControlBarUiState> = _uiState.asStateFlow()

    override fun parseIsSubMarkerBarOpen(isSubMarkerBarOpen: Boolean) {
        _uiState.update { it.copy(isSubMarkerBarOpen = isSubMarkerBarOpen) }
    }

    override fun parseSubMarkerItems(subMarkerItems: List<String>) {
        _uiState.update { it.copy(subMarkerItems = subMarkerItems) }
    }
}