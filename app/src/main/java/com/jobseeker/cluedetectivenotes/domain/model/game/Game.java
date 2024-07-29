package com.jobseeker.cluedetectivenotes.domain.model.game;

import com.jobseeker.cluedetectivenotes.domain.model.player.CardHolder;
import com.jobseeker.cluedetectivenotes.domain.model.player.Player;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.Sheet;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final Deck deck;
    private final CardHolders holders;
    private Sheet sheet;
    public Game(){
        deck = new Deck();
        holders = new CardHolders();
    }

    public Sheet getSheet(){
        if(sheet == null){
            List<CardHolder> columns = new ArrayList<>(holders.getPlayers());
            columns.add(holders.getAnswer());
            sheet = new Sheet(columns);
        }
        return sheet;
    }

    public List<Player> getPlayers(){
        return holders.getPlayers();
    }

    public int getNumOfPublicCards(){
        return deck.getDeckSize() % holders.getPlayers().size();
    }

    public int getNumOfHands(){
        return (deck.getDeckSize() - getNumOfPublicCards()) / holders.getPlayers().size();
    }
}
