package com.jobseeker.cluedetectivenotes.ui.viewModel.model

data class ControlBarUiState(
    var isSubMarkerBarOpen : Boolean = false,
    val subMarkerItems : List<String> = emptyList(),
    val addedSubMarkerItems : List<String> = emptyList(),
)