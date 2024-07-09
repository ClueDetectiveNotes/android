package com.jobseeker.cluedetectivenotes.application.useCase.gameSetting;

import com.jobseeker.cluedetectivenotes.domain.model.game.GameSetter;
import com.jobseeker.cluedetectivenotes.domain.model.player.Player;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class RemoveLastPlayerUseCase {
    public JSONObject execute() throws JSONException {
        List<UUID> playerIdList = new ArrayList<>();
        Map<UUID,String> playerNameMap = new HashMap<>();

        List<Player> players = GameSetter.getPlayersInstance();

        players.remove(players.size()-1);

        for(Player player:players){
            playerIdList.add(player.getId());
            playerNameMap.put(player.getId(),player.getName());
        }

        JSONObject playerState = new JSONObject();
        playerState.put("playerIdList",playerIdList);
        playerState.put("playerNameMap", playerNameMap);

        return playerState;
    }
}
