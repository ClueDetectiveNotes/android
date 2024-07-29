package com.jobseeker.cluedetectivenotes.domain.model.game;

import com.jobseeker.cluedetectivenotes.domain.model.player.Player;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.Sheet;

import java.util.List;

public class GameSetter {
    private static final Game game = new Game();

    public static List<Player> getPlayersInstance(){
        return game.getPlayers();
    }

    public static Sheet getSheetInstance(){
        return game.getSheet();
    }
}
