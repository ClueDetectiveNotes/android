package com.jobseeker.cluedetectivenotes.ui.viewModel.model

data class CellUiState (
    var mainMarker : String = "",
    var subMarkerItems : List<String> = emptyList(),
    var isInit : Boolean = false,
    var isLock : Boolean = false
)