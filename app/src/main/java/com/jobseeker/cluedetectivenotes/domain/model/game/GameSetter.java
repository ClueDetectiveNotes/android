package com.jobseeker.cluedetectivenotes.domain.model.game;

import com.jobseeker.cluedetectivenotes.domain.model.player.Other;
import com.jobseeker.cluedetectivenotes.domain.model.player.Player;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.Sheet;

import java.util.ArrayList;
import java.util.List;

public class GameSetter {
    private static Game game;
    private static List<Player> players;

    public static List<Player> getPlayersInstance(){
        if(players == null){
            players = new ArrayList<>();
            players.add(new Other());
            players.add(new Other());
            players.add(new Other());
        }
        return players;
    }

    public static Sheet getSheetInstance(){
        return game.getSheet();
    }

    public static Game getGameInstance(){
        if(game == null){
            game = GameFactory.createGame(players);
        }
        return game;
    }
}
