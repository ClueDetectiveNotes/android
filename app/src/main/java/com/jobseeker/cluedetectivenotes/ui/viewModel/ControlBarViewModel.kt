package com.jobseeker.cluedetectivenotes.ui.viewModel

import androidx.lifecycle.ViewModel
import com.jobseeker.cluedetectivenotes.ui.viewModel.intent.ControlBarIntent
import com.jobseeker.cluedetectivenotes.ui.viewModel.store.cell.CellStateStore
import com.jobseeker.cluedetectivenotes.ui.viewModel.store.StoreFactory

class ControlBarViewModel : ViewModel() {
    private val _store = StoreFactory.getCellStoreInstance()
    val store : CellStateStore = _store
    val intent : ControlBarIntent = ControlBarIntent(_store,StoreFactory.getSheetStoreInstance())
}
