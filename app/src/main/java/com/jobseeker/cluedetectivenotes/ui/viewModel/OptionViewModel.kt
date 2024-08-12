package com.jobseeker.cluedetectivenotes.ui.viewModel

import androidx.lifecycle.ViewModel
import com.jobseeker.cluedetectivenotes.ui.viewModel.intent.OptionIntent
import com.jobseeker.cluedetectivenotes.ui.viewModel.store.StoreFactory
import com.jobseeker.cluedetectivenotes.ui.viewModel.store.option.OptionStateStore

class OptionViewModel : ViewModel() {
    private val _store = StoreFactory.getOptionStoreInstance()
    val store : OptionStateStore = _store
    val intent : OptionIntent = OptionIntent(_store)
}