package com.jobseeker.cluedetectivenotes.ui.viewModel.model

import java.util.UUID

data class GameSettingUiState (
    val playerIdList : List<UUID> = emptyList(),
    val playerNameMap : Map<UUID,String> = emptyMap(),
    val playerNumber : Int = 0,
    val selectedOption: UUID = UUID.randomUUID(),
    val numOfHands : Int = 0,
    val numOfPublicCards : Int = 0,

    val suspectCardList : List<String> = emptyList(),
    val weaponCardList : List<String> = emptyList(),
    val crimeSceneCardList : List<String> = emptyList(),
    val publicCardList : List<String> = emptyList(),
    val handList : List<String> = emptyList(),
    val openedCardList : List<String> = emptyList(),

    val minusButtonEnabled: Boolean = false,
    val plusButtonEnabled: Boolean = false,
    val playerSettingNextButtonEnabled: Boolean = false,
    val playerOrderSettingNextButtonEnabled: Boolean = false
)