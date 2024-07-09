package com.jobseeker.cluedetectivenotes.ui.viewModel.intent

import com.jobseeker.cluedetectivenotes.application.useCase.gameSetting.AddPlayerUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.gameSetting.InitPlayersUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.gameSetting.RemoveLastPlayerUseCase
import com.jobseeker.cluedetectivenotes.ui.viewModel.store.gameSetting.GameSettingActionStore
import org.json.JSONObject
import java.util.UUID

class GameSettingIntent(private val store : GameSettingActionStore) {
    val initPlayersUseCase: InitPlayersUseCase = InitPlayersUseCase()
    val removeLastPlayerUseCase: RemoveLastPlayerUseCase = RemoveLastPlayerUseCase()
    val addPlayerUseCase: AddPlayerUseCase = AddPlayerUseCase()

    fun initPlayers() {
        val playerState : JSONObject = initPlayersUseCase.execute()
        val playerIdList:List<UUID> = playerState.get("playerIdList") as List<UUID>
        val playerNameMap:Map<UUID,String> = playerState.get("playerNameMap") as Map<UUID, String>

        store.parsePlayerId(playerIdList)
        store.parsePlayerName(playerNameMap)
        store.parsePlayerNumber(playerIdList.size)
    }

    fun removeLastPlayer() {
        val playerState : JSONObject = removeLastPlayerUseCase.execute()
        val playerIdList:List<UUID> = playerState.get("playerIdList") as List<UUID>
        val playerNameMap:Map<UUID,String> = playerState.get("playerNameMap") as Map<UUID, String>

        store.parsePlayerId(playerIdList)
        store.parsePlayerName(playerNameMap)
        store.parsePlayerNumber(playerIdList.size)
    }

    fun addPlayer() {
        val playerState : JSONObject = addPlayerUseCase.execute()
        val playerIdList:List<UUID> = playerState.get("playerIdList") as List<UUID>
        val playerNameMap:Map<UUID,String> = playerState.get("playerNameMap") as Map<UUID, String>

        store.parsePlayerId(playerIdList)
        store.parsePlayerName(playerNameMap)
        store.parsePlayerNumber(playerIdList.size)
    }
}