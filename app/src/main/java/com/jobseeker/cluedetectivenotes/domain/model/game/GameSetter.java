package com.jobseeker.cluedetectivenotes.domain.model.game;

import com.jobseeker.cluedetectivenotes.domain.model.player.Player;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.Sheet;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Cell;

import java.util.ArrayList;
import java.util.List;

public class GameSetter {
    private static Sheet sheet;
    private static final List<Player> players = new ArrayList<>();

    public static List<Player> getPlayersInstance(){
        if(players.isEmpty()){
            players.add(new Player(""));
            players.add(new Player(""));
            players.add(new Player(""));
        }
        return players;
    }

    public static Sheet getSheetInstance(){
        if(sheet == null){
            sheet = new Sheet(players);
        }
        return sheet;
    }

    public static <UUID> Cell getCellInstance(UUID cellId){
        return sheet.getCells().get(cellId);
    }
}
