package com.jobseeker.cluedetectivenotes.ui.viewModel.store

import com.jobseeker.cluedetectivenotes.ui.viewModel.store.controlBar.ControlBarStore
import com.jobseeker.cluedetectivenotes.ui.viewModel.store.gameSetting.GameSettingStore
import com.jobseeker.cluedetectivenotes.ui.viewModel.store.sheet.SheetStore

class StoreFactory {
    companion object{
        private val sheetStore : SheetStore = SheetStore()
        private val controlBarStore : ControlBarStore = ControlBarStore()
        private val gameSettingStore : GameSettingStore = GameSettingStore()

        fun getSheetStoreInstance(): SheetStore {
            return sheetStore
        }

        fun getControlBarStoreInstance(): ControlBarStore {
            return controlBarStore
        }

        fun getGameSettingStoreInstance(): GameSettingStore {
            return gameSettingStore
        }
    }
}