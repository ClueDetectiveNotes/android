package com.jobseeker.cluedetectivenotes.domain.model.game;

import com.jobseeker.cluedetectivenotes.domain.model.player.Player;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.Sheet;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.Cell;

import java.util.ArrayList;
import java.util.List;

public class GameSetter {
    private static Sheet sheet;

    public static Sheet getSheetInstance(){
        if(sheet == null){
            List<Player> players = new ArrayList<>();
            players.add(new Player("코코"));
            players.add(new Player("다산"));
            players.add(new Player("메리"));
            sheet = new Sheet(players);
        }
        return sheet;
    }

    public static <UUID> Cell getCellInstance(UUID cellId){
        return sheet.getCells().get(cellId);
    }
}
