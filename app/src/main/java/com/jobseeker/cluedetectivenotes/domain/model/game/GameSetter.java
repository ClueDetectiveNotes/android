package com.jobseeker.cluedetectivenotes.domain.model.game;

import com.jobseeker.cluedetectivenotes.domain.model.player.CardHolder;
import com.jobseeker.cluedetectivenotes.domain.model.player.Other;
import com.jobseeker.cluedetectivenotes.domain.model.player.Player;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.Sheet;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Cell;

import java.util.ArrayList;
import java.util.List;

public class GameSetter {
    private static Sheet sheet;
    private static final List<Player> players = new ArrayList<>();
    private static final List<CardHolder> holders = new ArrayList<>();

    public static List<Player> getPlayersInstance(){
        if(players.isEmpty()){
            players.add(new Other());
            players.add(new Other());
            players.add(new Other());
        }
        return players;
    }

    public static Sheet getSheetInstance(){
        if(sheet == null){
            holders.addAll(players);
            holders.add(new CardHolder("정답"));
            sheet = new Sheet(holders);
        }
        return sheet;
    }

    public static <UUID> Cell getCellInstance(UUID cellId){
        return sheet.getCells().get(cellId);
    }
}
