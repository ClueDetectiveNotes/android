package com.jobseeker.cluedetectivenotes.ui.viewModel.store.controlBar

import com.jobseeker.cluedetectivenotes.ui.viewModel.model.ControlBarUiState
import kotlinx.coroutines.flow.StateFlow

interface ControlBarStateStore {
    val uiState : StateFlow<ControlBarUiState>
}