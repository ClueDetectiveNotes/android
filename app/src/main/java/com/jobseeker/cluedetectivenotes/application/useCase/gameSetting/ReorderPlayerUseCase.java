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

public class ReorderPlayerUseCase {
    public JSONObject execute(int from, int to) throws JSONException {
        List<UUID> playerIdList = new ArrayList<>();

        List<Player> players = GameSetter.getPlayersInstance();

        players.add(to, players.remove(from));

        for(Player player:players){
            playerIdList.add(player.getId());
        }

        JSONObject playerState = new JSONObject();
        playerState.put("playerIdList",playerIdList);

        return playerState;
    }
}
