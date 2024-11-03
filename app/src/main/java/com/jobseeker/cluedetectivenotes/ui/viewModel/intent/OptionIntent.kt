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
        val isUseDarkTheme = options["IS_USE_DARK_THEME"]!!["VALUE"]!! == "Y"
        val darkThemeType = options["DARK_THEME_TYPE"]!!["VALUE"]!!
        val blindTransparency = options["BLIND_TRANSPARENCY"]!!["VALUE"]!!

        store.parseLanguage(language)
        store.parseIsUseDarkTheme(isUseDarkTheme)
        store.parseDarkThemeType(darkThemeType)
        store.parseBlindTransparency(blindTransparency.toFloat())

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

    fun setDarkThemeType(darkThemeType: String) {
        sqLiteHelper.updateOption("DARK_THEME_TYPE", darkThemeType)
        store.parseDarkThemeType(darkThemeType)
    }

    fun setUseDarkTheme(isUseDarkTheme: Boolean) {
        sqLiteHelper.updateOption("IS_USE_DARK_THEME", if(isUseDarkTheme) "Y" else "N")
        store.parseIsUseDarkTheme(isUseDarkTheme)
    }

    fun setBlindTransparency(blindTransparency: Float) {
        sqLiteHelper.updateOption("BLIND_TRANSPARENCY", blindTransparency.toString())
        store.parseBlindTransparency(blindTransparency)
    }
}