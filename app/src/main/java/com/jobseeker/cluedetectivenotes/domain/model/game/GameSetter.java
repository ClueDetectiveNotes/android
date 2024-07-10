package com.jobseeker.cluedetectivenotes.domain.model.game;

import com.jobseeker.cluedetectivenotes.domain.model.player.Player;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.Sheet;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Cell;

import java.util.ArrayList;
import java.util.List;

public class GameSetter {
    private static Sheet sheet;
    private static final List<Player> setPlayers = new ArrayList<>();
    private static final List<Player> players = new ArrayList<>();

    public static List<Player> getPlayersInstance(){
        if(setPlayers.isEmpty()){
            setPlayers.add(new Player(""));
            setPlayers.add(new Player(""));
            setPlayers.add(new Player(""));
        }
        return setPlayers;
    }

    public static Sheet getSheetInstance(){
        if(sheet == null){
            players.addAll(setPlayers);
            players.add(new Player("정답"));
            sheet = new Sheet(players);
        }
        return sheet;
    }

    public static <UUID> Cell getCellInstance(UUID cellId){
        return sheet.getCells().get(cellId);
    }
}
