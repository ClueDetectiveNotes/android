package com.jobseeker.cluedetectivenotes.application.useCase.gameSetting;

import com.jobseeker.cluedetectivenotes.domain.model.game.GameSetter;
import com.jobseeker.cluedetectivenotes.domain.model.player.Player;

import java.util.List;
import java.util.UUID;

public class SetPlayerNameUseCase {
    public void execute(UUID id, String name){
        List<Player> players = GameSetter.getPlayersInstance();

        for(Player player:players){
            if(player.getId() == id) player.setName(name);
        }
    }
}
