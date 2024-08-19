package com.jobseeker.cluedetectivenotes.ui.viewModel

import androidx.lifecycle.ViewModel
import com.jobseeker.cluedetectivenotes.ui.viewModel.intent.ControlBarIntent
import com.jobseeker.cluedetectivenotes.ui.viewModel.store.StoreFactory
import com.jobseeker.cluedetectivenotes.ui.viewModel.store.controlBar.ControlBarStateStore

class ControlBarViewModel : ViewModel() {
    private val _store = StoreFactory.getControlBarStoreInstance()
    val store : ControlBarStateStore = _store
    val intent : ControlBarIntent = ControlBarIntent(
                                                        _store,
                                                        StoreFactory.getSheetStoreInstance(),
                                                        StoreFactory.getCellStoreInstance()
                                                    )
}
