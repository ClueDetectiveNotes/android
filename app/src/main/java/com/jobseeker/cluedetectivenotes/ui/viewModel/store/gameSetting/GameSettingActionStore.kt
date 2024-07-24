package com.jobseeker.cluedetectivenotes.ui.viewModel.store.gameSetting

import com.jobseeker.cluedetectivenotes.ui.viewModel.model.GameSettingUiState
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.UUID

interface GameSettingActionStore {
    val _uiState : MutableStateFlow<GameSettingUiState>

    fun parsePlayerId(playerIdList : List<UUID>)
    fun parsePlayerName(playerNameMap : Map<UUID,String>)
    fun parsePlayerNumber(playerNumber: Int)
    fun parseSelectedOption(selectedOption: UUID)
    fun parsePlusButtonEnabled(plusButtonEnabled: Boolean)
    fun parseMinusButtonEnabled(minusButtonEnabled: Boolean)
    fun parsePlayerSettingNextButtonEnabled(playerSettingNextButtonEnabled: Boolean)
    fun parsePlayerOrderSettingNextButtonEnabled(playerOrderSettingNextButtonEnabled: Boolean)
}