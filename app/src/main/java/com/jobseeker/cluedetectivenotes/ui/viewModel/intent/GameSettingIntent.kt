package com.jobseeker.cluedetectivenotes.ui.viewModel.intent

import com.jobseeker.cluedetectivenotes.application.useCase.gameSetting.AddPlayerUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.gameSetting.InitPlayersUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.gameSetting.RemoveLastPlayerUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.gameSetting.ReorderPlayerUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.gameSetting.SetPlayerNameUseCase
import com.jobseeker.cluedetectivenotes.ui.viewModel.store.gameSetting.GameSettingActionStore
import org.json.JSONObject
import java.util.UUID

class GameSettingIntent(private val store : GameSettingActionStore) {
    val initPlayersUseCase: InitPlayersUseCase = InitPlayersUseCase()
    val removeLastPlayerUseCase: RemoveLastPlayerUseCase = RemoveLastPlayerUseCase()
    val addPlayerUseCase: AddPlayerUseCase = AddPlayerUseCase()
    val setPlayerNameUseCase: SetPlayerNameUseCase = SetPlayerNameUseCase()
    val reorderPlayerUseCase: ReorderPlayerUseCase = ReorderPlayerUseCase()

    fun initPlayers() {
        val playerState : JSONObject = initPlayersUseCase.execute()
        val playerIdList:List<UUID> = playerState.get("playerIdList") as List<UUID>
        val playerNameMap:Map<UUID,String> = playerState.get("playerNameMap") as Map<UUID, String>

        store.parsePlayerId(playerIdList)
        store.parsePlayerName(playerNameMap)
        store.parsePlayerNumber(playerIdList.size)
        store.parseMinusButtonEnabled(playerIdList.size>3)
        store.parsePlusButtonEnabled(playerIdList.size<6)
    }

    fun removeLastPlayer() {
        val playerState : JSONObject = removeLastPlayerUseCase.execute()
        val playerIdList:List<UUID> = playerState.get("playerIdList") as List<UUID>
        val playerNameMap:Map<UUID,String> = playerState.get("playerNameMap") as Map<UUID, String>

        store.parsePlayerId(playerIdList)
        store.parsePlayerName(playerNameMap)
        store.parsePlayerNumber(playerIdList.size)
        store.parseMinusButtonEnabled(playerIdList.size>3)
        store.parsePlusButtonEnabled(playerIdList.size<6)
    }

    fun addPlayer() {
        val playerState : JSONObject = addPlayerUseCase.execute()
        val playerIdList:List<UUID> = playerState.get("playerIdList") as List<UUID>
        val playerNameMap:Map<UUID,String> = playerState.get("playerNameMap") as Map<UUID, String>

        store.parsePlayerId(playerIdList)
        store.parsePlayerName(playerNameMap)
        store.parsePlayerNumber(playerIdList.size)
        store.parseMinusButtonEnabled(playerIdList.size>3)
        store.parsePlusButtonEnabled(playerIdList.size<6)
    }

    fun setPlayerName(id: UUID, name: String) {
        val playerState : JSONObject = setPlayerNameUseCase.execute(id, name)
        val playerNameMap:Map<UUID,String> = playerState.get("playerNameMap") as Map<UUID, String>

        var playerSettingNextButtonEnabled = true
        val playerNames : HashSet<String> = HashSet()
        for(key in playerNameMap.keys){
            val playerName = playerNameMap[key]
            if(playerName == ""){
                playerSettingNextButtonEnabled = false
            }
            playerNames.add(playerName!!)
        }
        if(playerNameMap.keys.size != playerNames.size){
            playerSettingNextButtonEnabled = false
        }

        store.parsePlayerSettingNextButtonEnabled(playerSettingNextButtonEnabled)
    }

    fun reorderPlayer(from: Int, to: Int) {
        val playerState : JSONObject = reorderPlayerUseCase.execute(from, to)
        val playerIdList:List<UUID> = playerState.get("playerIdList") as List<UUID>

        store.parsePlayerId(playerIdList)
    }

    fun selectPlayer(id: UUID) {
        store.parseSelectedOption(id)
        store.parsePlayerOrderSettingNextButtonEnabled(true)
    }
}