package com.jobseeker.cluedetectivenotes.ui.viewModel.store.sheet

import com.jobseeker.cluedetectivenotes.ui.viewModel.model.SheetUiState
import kotlinx.coroutines.flow.StateFlow

interface SheetStateStore {
    val uiState : StateFlow<SheetUiState>
}