package com.jobseeker.cluedetectivenotes.ui.viewModel

import androidx.lifecycle.ViewModel
import com.jobseeker.cluedetectivenotes.ui.viewModel.store.StoreFactory
import com.jobseeker.cluedetectivenotes.ui.viewModel.store.cell.CellStateStore

class CellViewModel : ViewModel() {
    private val _store = StoreFactory.getCellStoreInstance()
    val store : CellStateStore = _store
}