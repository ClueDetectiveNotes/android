package com.jobseeker.cluedetectivenotes.ui.viewModel.intent

import android.content.Context
import com.jobseeker.cluedetectivenotes.application.useCase.gameSetting.MultiLanguageSettingUseCase
import com.jobseeker.cluedetectivenotes.persistence.db.SQLiteHelper
import com.jobseeker.cluedetectivenotes.ui.viewModel.store.option.OptionActionStore

class OptionIntent (context: Context, private val store:OptionActionStore) {
    private val sqLiteHelper : SQLiteHelper = SQLiteHelper(context)
    private val multiLanguageSettingUseCase : MultiLanguageSettingUseCase = MultiLanguageSettingUseCase()

    fun loadOptions() {
        //sqLiteHelper.initDb()
        val options = sqLiteHelper.getOptions()
        val language = options["LANGUAGE"]!!["VALUE"]!!
        store.parseLanguage(language)

        val multiLang = sqLiteHelper.getMultiLang(language)
        val commonCode = sqLiteHelper.getCommonCode()

        multiLanguageSettingUseCase.execute(multiLang)
        store.parseMultiLang(multiLang)
        store.parsCommonCode(commonCode)
    }

    fun setLanguage(language: String){
        sqLiteHelper.updateOption("LANGUAGE", language)
        store.parseLanguage(language)

        val multiLang = sqLiteHelper.getMultiLang(language)

        multiLanguageSettingUseCase.execute(multiLang)
        store.parseMultiLang(multiLang)
    }
}