package com.jobseeker.cluedetectivenotes.ui.viewModel.intent

import android.content.Context
import com.jobseeker.cluedetectivenotes.application.useCase.gameSetting.MultiLanguageSettingUseCase
import com.jobseeker.cluedetectivenotes.persistence.db.SQLiteHelper
import com.jobseeker.cluedetectivenotes.ui.viewModel.store.option.OptionActionStore

class OptionIntent (context: Context, private val store:OptionActionStore) {
    private val sqLiteHelper : SQLiteHelper = SQLiteHelper(context)
    private val multiLanguageSettingUseCase : MultiLanguageSettingUseCase = MultiLanguageSettingUseCase()

    fun loadOptions() {
        sqLiteHelper.initDb()
        val multiLang = sqLiteHelper.getMultiLang(store._uiState.value.language)
        val commonCode = sqLiteHelper.getCommonCode()

        multiLanguageSettingUseCase.execute(multiLang)
        store.parseMultiLang(multiLang)
        store.parsCommonCode(commonCode)
    }

    fun setLanguage(language: String){
        store.parseLanguage(language)

        val multiLang = sqLiteHelper.getMultiLang(store._uiState.value.language)

        multiLanguageSettingUseCase.execute(multiLang)
        store.parseMultiLang(multiLang)
    }
}