package com.jobseeker.cluedetectivenotes.application.useCase.gameSetting;

import com.jobseeker.cluedetectivenotes.domain.model.game.GameSetter;

public class InitGameUseCase {
    public void execute(){
        GameSetter.destroyGame();
    }
}
