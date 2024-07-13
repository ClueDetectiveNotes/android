package com.jobseeker.cluedetectivenotes.ui.viewModel.store

import com.jobseeker.cluedetectivenotes.ui.viewModel.store.cell.CellStore
import com.jobseeker.cluedetectivenotes.ui.viewModel.store.gameSetting.GameSettingStore
import com.jobseeker.cluedetectivenotes.ui.viewModel.store.sheet.SheetStore

class StoreFactory {
    companion object{
        private val sheetStore : SheetStore = SheetStore()
        private val cellStore : CellStore = CellStore()
        private val gameSettingStore : GameSettingStore = GameSettingStore()

        fun getSheetStoreInstance(): SheetStore {
            return sheetStore
        }

        fun getCellStoreInstance(): CellStore {
            return cellStore
        }

        fun getGameSettingStoreInstance(): GameSettingStore {
            return gameSettingStore
        }
    }
}