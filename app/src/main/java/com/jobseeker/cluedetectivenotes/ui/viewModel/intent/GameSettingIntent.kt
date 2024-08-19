package com.jobseeker.cluedetectivenotes.ui.viewModel.intent

import com.jobseeker.cluedetectivenotes.application.useCase.gameSetting.AddPlayerUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.gameSetting.InitPlayersUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.gameSetting.LoadCardListUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.gameSetting.RemoveLastPlayerUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.gameSetting.ReorderPlayerUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.gameSetting.SelectHandUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.gameSetting.SelectPublicCardUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.gameSetting.SelectUserUseCase
import com.jobseeker.cluedetectivenotes.application.useCase.gameSetting.SetPlayerNameUseCase
import com.jobseeker.cluedetectivenotes.ui.viewModel.store.gameSetting.GameSettingActionStore
import org.json.JSONObject
import java.util.UUID

class GameSettingIntent(private val store : GameSettingActionStore) {
    private val initPlayersUseCase: InitPlayersUseCase = InitPlayersUseCase()
    private val removeLastPlayerUseCase: RemoveLastPlayerUseCase = RemoveLastPlayerUseCase()
    private val addPlayerUseCase: AddPlayerUseCase = AddPlayerUseCase()
    private val setPlayerNameUseCase: SetPlayerNameUseCase = SetPlayerNameUseCase()
    private val reorderPlayerUseCase: ReorderPlayerUseCase = ReorderPlayerUseCase()
    private val selectUserUseCase:SelectUserUseCase = SelectUserUseCase()
    private val loadCardListUseCase:LoadCardListUseCase = LoadCardListUseCase()
    private val selectHandUseCase:SelectHandUseCase = SelectHandUseCase()
    private val selectPublicCardUseCase: SelectPublicCardUseCase = SelectPublicCardUseCase()

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

        setPlayerSettingNextButtonEnable(playerNameMap)
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

        setPlayerSettingNextButtonEnable(playerNameMap)
    }

    fun setPlayerName(id: UUID, name: String) {
        val playerState : JSONObject = setPlayerNameUseCase.execute(id, name)
        val playerNameMap:Map<UUID,String> = playerState.get("playerNameMap") as Map<UUID, String>

        setPlayerSettingNextButtonEnable(playerNameMap)
    }

    private fun setPlayerSettingNextButtonEnable(playerNameMap:Map<UUID,String>){
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
        selectUserUseCase.execute(id)

        store.parseSelectedOption(id)
        store.parsePlayerOrderSettingNextButtonEnabled(true)
    }

    fun loadCardList(){
        val cardsState = loadCardListUseCase.execute()

        store.parseSuspectCardList(cardsState.get("suspectCardList") as List<String>)
        store.parseWeaponCardList(cardsState.get("weaponCardList") as List<String>)
        store.parseCrimeSceneCardList(cardsState.get("crimeSceneCardList") as List<String>)
        store.parsePublicCardList(cardsState.get("publicCardList") as List<String>)
        store.parseHandList(cardsState.get("handList") as List<String>)

        store.parseNumOfHands(cardsState.getInt("numOfHands"))
        store.parseNumOfPublicCards(cardsState.getInt("numOfPublicCards"))
    }

    fun selectHand(cardName: String) {
        val cardsState = selectHandUseCase.execute(cardName)

        store.parsePublicCardList(cardsState.get("publicCardList") as List<String>)
        store.parseHandList(cardsState.get("handList") as List<String>)
    }

    fun selectPublicCard(cardName: String) {
        val cardsState = selectPublicCardUseCase.execute(cardName)

        store.parsePublicCardList(cardsState.get("publicCardList") as List<String>)
        //store.parseHandList(cardsState.get("handList") as List<String>)
    }
}