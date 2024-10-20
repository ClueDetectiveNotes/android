package com.jobseeker.cluedetectivenotes.domain.model.game;

import com.jobseeker.cluedetectivenotes.domain.model.player.Other;
import com.jobseeker.cluedetectivenotes.domain.model.player.Player;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.Sheet;
import com.jobseeker.cluedetectivenotes.utils.DataMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameSetter {
    private static Game game;
    private static List<Player> players;
    private static final Map<String,String> multiLang = new HashMap<>();

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

    public static void setMultiLanguage(DataMap multiLang){
        if(GameSetter.multiLang.isEmpty()){
            GameSetter.multiLang.putAll(multiLang.getMap());
        }else{
            GameSetter.multiLang.clear();
            GameSetter.multiLang.putAll(multiLang.getMap());
            if(game != null) game.renewMultiLang();
        }
    }

    public static void destroyPlayers() {
        players = null;
    }

    public static void destroyGame() {
        game = null;
        if(players != null){
            for(Player player : players){
                player.clearCards();
            }
        }
    }
}
