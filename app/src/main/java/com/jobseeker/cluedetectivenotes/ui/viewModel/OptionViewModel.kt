package com.jobseeker.cluedetectivenotes.ui.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.jobseeker.cluedetectivenotes.ui.viewModel.intent.OptionIntent
import com.jobseeker.cluedetectivenotes.ui.viewModel.store.StoreFactory
import com.jobseeker.cluedetectivenotes.ui.viewModel.store.option.OptionStateStore

class OptionViewModel(application: Application) : AndroidViewModel(application) {
    private val _store = StoreFactory.getOptionStoreInstance()
    val store : OptionStateStore = _store
    val intent : OptionIntent = OptionIntent(application, _store)
}