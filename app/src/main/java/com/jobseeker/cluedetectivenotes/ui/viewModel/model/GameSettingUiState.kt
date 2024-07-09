package com.jobseeker.cluedetectivenotes.ui.viewModel.model

import java.util.UUID

data class GameSettingUiState (
    val playerIdList : List<UUID> = emptyList(),
    val playerNameMap : Map<UUID,String> = emptyMap(),
    val playerNumber : Int = 0
)