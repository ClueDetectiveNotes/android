package com.jobseeker.cluedetectivenotes.ui.viewModel.model

import java.util.UUID

data class SheetUiState (
    val selectedIds:List<UUID> = emptyList(),
    val selectedRownameIds:List<UUID> = emptyList(),
    val selectedColnameIds:List<UUID> = emptyList(),
    var isMultiMode: Boolean = false,
    var openConfirmToDefaultModeDialog : Boolean = false,
    var openAddSubMarkerDialog : Boolean = false,
)