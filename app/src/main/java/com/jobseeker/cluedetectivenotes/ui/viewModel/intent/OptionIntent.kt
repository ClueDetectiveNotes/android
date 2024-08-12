package com.jobseeker.cluedetectivenotes.ui.viewModel.intent

import android.content.Context
import com.jobseeker.cluedetectivenotes.persistence.db.SQLiteHelper
import com.jobseeker.cluedetectivenotes.ui.viewModel.store.option.OptionActionStore

class OptionIntent (private val store:OptionActionStore) {
    private lateinit var sqLiteHelper : SQLiteHelper

    fun loadOptions(context: Context) {
        sqLiteHelper = SQLiteHelper(context)
        sqLiteHelper.initDb()
        val multiLang = sqLiteHelper.getMultiLang()
        store.parseMultiLang(multiLang)
    }
}