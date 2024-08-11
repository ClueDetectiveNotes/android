package com.jobseeker.cluedetectivenotes.ui.viewModel.store.gameSetting

import com.jobseeker.cluedetectivenotes.ui.viewModel.model.GameSettingUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

class GameSettingStore : GameSettingStateStore, GameSettingActionStore {
    override val _uiState: MutableStateFlow<GameSettingUiState> = MutableStateFlow(GameSettingUiState())
    override val uiState: StateFlow<GameSettingUiState> = _uiState.asStateFlow()

    override fun parsePlayerId(playerIdList: List<UUID>) {
        _uiState.update { it.copy(playerIdList = playerIdList) }
    }

    override fun parsePlayerName(playerNameMap: Map<UUID, String>) {
        _uiState.update { it.copy(playerNameMap = playerNameMap) }
    }

    override fun parsePlayerNumber(playerNumber: Int) {
        _uiState.update { it.copy(playerNumber = playerNumber) }
    }

    override fun parseSelectedOption(selectedOption: UUID) {
        _uiState.update { it.copy(selectedOption = selectedOption) }
    }

    override fun parseNumOfHands(numOfHands: Int) {
        _uiState.update { it.copy(numOfHands = numOfHands) }
    }

    override fun parseNumOfPublicCards(numOfPublicCards: Int) {
        _uiState.update { it.copy(numOfPublicCards = numOfPublicCards) }
    }

    override fun parseSuspectCardList(suspectCardList: List<String>) {
        _uiState.update { it.copy(suspectCardList = suspectCardList) }
    }

    override fun parseWeaponCardList(weaponCardList: List<String>) {
        _uiState.update { it.copy(weaponCardList = weaponCardList) }
    }

    override fun parseCrimeSceneCardList(crimeSceneCardList: List<String>) {
        _uiState.update { it.copy(crimeSceneCardList = crimeSceneCardList) }
    }

    override fun parsePublicCardList(publicCardList: List<String>) {
        _uiState.update { it.copy(publicCardList = publicCardList) }
    }

    override fun parseHandList(handList: List<String>) {
        _uiState.update { it.copy(handList = handList) }
    }

    override fun parseOpenedCardList(openedCardList: List<String>) {
        _uiState.update { it.copy(openedCardList = openedCardList) }
    }

    override fun parsePlusButtonEnabled(plusButtonEnabled: Boolean) {
        _uiState.update { it.copy(plusButtonEnabled = plusButtonEnabled) }
    }

    override fun parseMinusButtonEnabled(minusButtonEnabled: Boolean) {
        _uiState.update { it.copy(minusButtonEnabled = minusButtonEnabled) }
    }

    override fun parsePlayerSettingNextButtonEnabled(playerSettingNextButtonEnabled: Boolean) {
        _uiState.update { it.copy(playerSettingNextButtonEnabled = playerSettingNextButtonEnabled) }
    }

    override fun parsePlayerOrderSettingNextButtonEnabled(playerOrderSettingNextButtonEnabled: Boolean) {
        _uiState.update { it.copy(playerOrderSettingNextButtonEnabled = playerOrderSettingNextButtonEnabled) }
    }
}