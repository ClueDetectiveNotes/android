package com.jobseeker.cluedetectivenotes.application.useCase.gameSetting;

import com.jobseeker.cluedetectivenotes.domain.model.game.GameSetter;
import com.jobseeker.cluedetectivenotes.utils.DataMap;

import java.util.Map;

public class MultiLanguageSettingUseCase {
    public void execute(DataMap multiLang){
        GameSetter.setMultiLanguage(multiLang);
    }
}
