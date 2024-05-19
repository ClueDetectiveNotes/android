package com.jobseeker.cluedetectivenotes.ui.viewModel.model

import java.util.UUID

data class SheetUiState (
    val selectedIds:List<UUID> = emptyList(),
    var isMultiMode: Boolean = false
)