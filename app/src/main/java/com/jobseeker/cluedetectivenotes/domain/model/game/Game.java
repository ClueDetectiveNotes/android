package com.jobseeker.cluedetectivenotes.domain.model.game;

import com.jobseeker.cluedetectivenotes.domain.model.player.CardHolder;
import com.jobseeker.cluedetectivenotes.domain.model.player.Player;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.Sheet;

import java.util.List;
import java.util.Map;

public class Game {
    private final Deck deck;
    private final CardHolders holders;
    private Sheet sheet;
    public Game(List<Player> players, Map<String,String> multiLang){
        holders = new CardHolders(players, multiLang);
        deck = new Deck(holders.getUnknownOne());
    }

    public Sheet getSheet() {
        if(sheet == null){
            sheet = new Sheet(holders);
        }
        return sheet;
    }

    public List<Player> getPlayers(){
        return holders.getPlayers();
    }

    public int getNumOfPublicCards(){
        return (deck.getDeckSize() - 3) % holders.getPlayers().size();
    }

    public int getNumOfHands(){
        return (deck.getDeckSize() - 3 - getNumOfPublicCards()) / holders.getPlayers().size();
    }

    public Player getUser(){
        return holders.getUser();
    }

    public CardHolder getPublicOne() { return holders.getPublicOne(); }
    public CardHolder getUnknownOne() { return holders.getUnknownOne(); }

    public Deck getDeck(){ return deck; }

    public void renewMultiLang() {
        holders.renewMultiLang();
    }
}
