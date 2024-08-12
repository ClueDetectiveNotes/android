package com.jobseeker.cluedetectivenotes.ui.viewModel.store.option

import com.jobseeker.cluedetectivenotes.ui.viewModel.model.OptionUiState
import kotlinx.coroutines.flow.StateFlow

interface OptionStateStore {
    val uiState : StateFlow<OptionUiState>
}