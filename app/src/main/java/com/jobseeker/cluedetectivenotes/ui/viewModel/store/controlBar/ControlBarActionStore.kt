package com.jobseeker.cluedetectivenotes.ui.viewModel.store.controlBar

import com.jobseeker.cluedetectivenotes.ui.viewModel.model.ControlBarUiState
import kotlinx.coroutines.flow.MutableStateFlow

interface ControlBarActionStore {
    val _uiState : MutableStateFlow<ControlBarUiState>

    fun parseIsSubMarkerBarOpen(isSubMarkerBarOpen : Boolean)
    fun parseSubMarkerItems(subMarkerItems: List<String>)
    fun parseAddedSubMarkerItems(addedSubMarkerItems: List<String>)
}