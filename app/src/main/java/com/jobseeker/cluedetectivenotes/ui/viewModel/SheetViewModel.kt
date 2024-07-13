package com.jobseeker.cluedetectivenotes.ui.viewModel

import androidx.lifecycle.ViewModel
import com.jobseeker.cluedetectivenotes.ui.viewModel.intent.SheetIntent
import com.jobseeker.cluedetectivenotes.ui.viewModel.store.sheet.SheetStateStore
import com.jobseeker.cluedetectivenotes.ui.viewModel.store.StoreFactory

class SheetViewModel: ViewModel(){
    private val _store = StoreFactory.getSheetStoreInstance()
    val store : SheetStateStore = _store
    val intent : SheetIntent = SheetIntent(_store,StoreFactory.getCellStoreInstance())
    init {
        intent.loadSheetState()
        intent.initCells()
    }
}