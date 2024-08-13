package com.jobseeker.cluedetectivenotes.application.useCase.gameSetting;

import com.jobseeker.cluedetectivenotes.domain.model.game.GameSetter;

import java.util.Map;

public class MultiLanguageSettingUseCase {
    public void execute(Map<String,String> multiLang){
        GameSetter.setMultiLanguage(multiLang);
    }
}
