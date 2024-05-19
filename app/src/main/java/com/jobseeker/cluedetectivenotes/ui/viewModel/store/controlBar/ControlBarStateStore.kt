package com.jobseeker.cluedetectivenotes.ui.viewModel.store.controlBar

import androidx.compose.runtime.State
import com.jobseeker.cluedetectivenotes.ui.viewModel.model.CellUiState
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID

interface ControlBarStateStore {
    val cells : HashMap<UUID, StateFlow<CellUiState>>
}