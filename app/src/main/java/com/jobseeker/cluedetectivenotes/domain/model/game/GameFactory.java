package com.jobseeker.cluedetectivenotes.domain.model.game;

import com.jobseeker.cluedetectivenotes.domain.model.player.Player;

import java.util.List;
import java.util.Map;

public class GameFactory {
    public static Game createGame(List<Player> players, Map<String,String> multiLang) {
        return new Game(players, multiLang);
    }
}
