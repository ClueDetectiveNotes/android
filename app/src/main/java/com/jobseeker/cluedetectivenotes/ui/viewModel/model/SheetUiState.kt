package com.jobseeker.cluedetectivenotes.ui.viewModel.model

import java.util.UUID

data class SheetUiState (
    val selectedIds:List<UUID> = emptyList(),
    val selectedRownameIds:List<UUID> = emptyList(),
    val selectedColnameIds:List<UUID> = emptyList(),
    val isMultiMode: Boolean = false,
    val isInferenceMode : Boolean = false,
    val isCellsLocked : Boolean = false,

    val openConfirmToDefaultModeDialog : Boolean = false,
    val openAddSubMarkerDialog : Boolean = false,
)