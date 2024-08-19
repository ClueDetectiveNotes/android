package com.jobseeker.cluedetectivenotes.ui.viewModel.store

import com.jobseeker.cluedetectivenotes.ui.viewModel.store.cell.CellStore
import com.jobseeker.cluedetectivenotes.ui.viewModel.store.controlBar.ControlBarStore
import com.jobseeker.cluedetectivenotes.ui.viewModel.store.gameSetting.GameSettingStore
import com.jobseeker.cluedetectivenotes.ui.viewModel.store.option.OptionStore
import com.jobseeker.cluedetectivenotes.ui.viewModel.store.sheet.SheetStore

class StoreFactory {
    companion object{
        private val sheetStore : SheetStore = SheetStore()
        private val cellStore : CellStore = CellStore()
        private val controlBarStore : ControlBarStore = ControlBarStore()
        private val gameSettingStore : GameSettingStore = GameSettingStore()
        private val optionStore : OptionStore = OptionStore()

        fun getSheetStoreInstance(): SheetStore {
            return sheetStore
        }

        fun getCellStoreInstance(): CellStore {
            return cellStore
        }

        fun getGameSettingStoreInstance(): GameSettingStore {
            return gameSettingStore
        }

        fun getOptionStoreInstance(): OptionStore {
            return optionStore;
        }

        fun getControlBarStoreInstance() : ControlBarStore {
            return controlBarStore
        }
    }
}