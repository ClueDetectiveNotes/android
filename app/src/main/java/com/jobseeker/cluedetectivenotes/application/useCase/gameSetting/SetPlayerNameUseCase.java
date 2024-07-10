package com.jobseeker.cluedetectivenotes.application.useCase.gameSetting;

import com.jobseeker.cluedetectivenotes.domain.model.game.GameSetter;
import com.jobseeker.cluedetectivenotes.domain.model.player.Player;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SetPlayerNameUseCase {
    public JSONObject execute(UUID id, String name) throws JSONException {
        Map<UUID,String> playerNameMap = new HashMap<>();
        List<Player> players = GameSetter.getPlayersInstance();

        for(Player player:players){
            if(player.getId() == id) player.setName(name);
        }

        for(Player player:players){
            playerNameMap.put(player.getId(),player.getName());
        }

        JSONObject playerState = new JSONObject();
        playerState.put("playerNameMap", playerNameMap);

        return playerState;
    }
}
