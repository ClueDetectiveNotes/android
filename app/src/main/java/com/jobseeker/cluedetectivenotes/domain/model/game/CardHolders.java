package com.jobseeker.cluedetectivenotes.domain.model.game;

import com.jobseeker.cluedetectivenotes.domain.model.player.CardHolder;
import com.jobseeker.cluedetectivenotes.domain.model.player.Other;
import com.jobseeker.cluedetectivenotes.domain.model.player.Player;
import com.jobseeker.cluedetectivenotes.domain.model.player.Type;
import com.jobseeker.cluedetectivenotes.domain.model.player.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CardHolders {
    private final List<Player> players;
    private final CardHolder answer;
    private final CardHolder publicOne;
    private final CardHolder unknownOne;
    private final Map<String, String> multiLang;

    public CardHolders(List<Player> players, Map<String,String> multiLang){
        this.players = players;
        this.multiLang = multiLang;
        answer = new CardHolder(this.multiLang.get("CRD_HD.ANSWER"));
        publicOne = new CardHolder(this.multiLang.get("CRD_HD.PUBLIC"));
        unknownOne = new CardHolder(this.multiLang.get("CRD_HD.UNKNOWN"));
    }

    public List<Player> getPlayers() {
        return players;
    }
    public CardHolder getAnswer(){
        return answer;
    }
    public CardHolder getPublicOne(){
        return publicOne;
    }
    public CardHolder getUnknownOne(){
        return unknownOne;
    }
    public Player getUser(){
        Player user = null;
        for(Player player : players){
            if(player.getType() == Type.USER){
                user = player;
            }
        }
        return user;
    }

    public void renewMultiLang() {
        answer.setName(multiLang.get("CRD_HD.ANSWER"));
        publicOne.setName(multiLang.get("CRD_HD.PUBLIC"));
        unknownOne.setName(multiLang.get("CRD_HD.UNKNOWN"));
    }
}
