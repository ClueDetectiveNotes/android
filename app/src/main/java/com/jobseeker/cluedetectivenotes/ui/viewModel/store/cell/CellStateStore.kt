package com.jobseeker.cluedetectivenotes.ui.viewModel.store.cell

import com.jobseeker.cluedetectivenotes.ui.viewModel.model.CellUiState
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID

interface CellStateStore {
    val cells : HashMap<UUID, StateFlow<CellUiState>>
}