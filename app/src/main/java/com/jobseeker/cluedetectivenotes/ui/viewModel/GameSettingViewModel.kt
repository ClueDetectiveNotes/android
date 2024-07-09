package com.jobseeker.cluedetectivenotes.ui.viewModel

import androidx.lifecycle.ViewModel
import com.jobseeker.cluedetectivenotes.ui.viewModel.intent.GameSettingIntent
import com.jobseeker.cluedetectivenotes.ui.viewModel.store.StoreFactory
import com.jobseeker.cluedetectivenotes.ui.viewModel.store.gameSetting.GameSettingStateStore

class GameSettingViewModel: ViewModel() {
    private val _store = StoreFactory.getGameSettingStoreInstance()
    val store : GameSettingStateStore = _store
    val intent : GameSettingIntent = GameSettingIntent(_store)

    init{
        intent.initPlayers()
    }
}