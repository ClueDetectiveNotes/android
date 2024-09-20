package com.jobseeker.cluedetectivenotes.application.useCase.gameSetting;

import com.jobseeker.cluedetectivenotes.domain.model.game.GameSetter;
import com.jobseeker.cluedetectivenotes.domain.model.player.Other;
import com.jobseeker.cluedetectivenotes.domain.model.player.Player;
import com.jobseeker.cluedetectivenotes.domain.model.player.Type;
import com.jobseeker.cluedetectivenotes.domain.model.player.User;

import java.util.List;
import java.util.UUID;

public class SelectUserUseCase {
    public void execute(UUID id){

        List<Player> players = GameSetter.getPlayersInstance();
        int toUserIdx = -1;
        int toOtherIdx = -1;
        for(int i=0; i<players.size();i++){
            if(players.get(i).getId() == id){
                toUserIdx = i;
            }
            if(players.get(i).getType() == Type.USER){
                toOtherIdx = i;
            }
        }
        if(toUserIdx>-1){
            players.add(toUserIdx,new User(players.remove(toUserIdx)));
        }
        if(toOtherIdx>-1)
            players.add(toOtherIdx, new Other(players.remove(toOtherIdx)));
    }
}
