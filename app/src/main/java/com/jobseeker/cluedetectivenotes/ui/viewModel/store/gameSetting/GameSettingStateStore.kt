package com.jobseeker.cluedetectivenotes.ui.viewModel.store.gameSetting

import com.jobseeker.cluedetectivenotes.ui.viewModel.model.GameSettingUiState
import kotlinx.coroutines.flow.StateFlow

interface GameSettingStateStore {
    val uiState : StateFlow<GameSettingUiState>
}