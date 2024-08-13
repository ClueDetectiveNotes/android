package com.jobseeker.cluedetectivenotes.domain.model.game;

import androidx.annotation.NonNull;

import com.jobseeker.cluedetectivenotes.domain.model.player.Other;
import com.jobseeker.cluedetectivenotes.domain.model.player.Player;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.Sheet;

import org.jetbrains.annotations.NonNls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameSetter {
    private static Game game;
    private static List<Player> players;
    private static Map<String,String> multiLang = new HashMap<>();

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

    public static Game getGameInstance() {
        if(game == null){
            game = GameFactory.createGame(players, multiLang);
        }
        return game;
    }

    public static void setMultiLanguage(Map<String,String> multiLang){
        GameSetter.multiLang.putAll(multiLang);
    }
}
