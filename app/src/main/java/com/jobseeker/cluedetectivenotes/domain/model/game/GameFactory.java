package com.jobseeker.cluedetectivenotes.domain.model.game;

import com.jobseeker.cluedetectivenotes.domain.model.player.Player;

import java.util.List;

public class GameFactory {
    public static Game createGame(List<Player> players){
        return new Game(players);
    }
}
