package com.jobseeker.cluedetectivenotes.domain.model.player;

import com.jobseeker.cluedetectivenotes.domain.model.card.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CardHolder {
    protected UUID id;
    protected String name;
    protected List<Card> cards;

    public CardHolder(String name){
        this.id = UUID.randomUUID();
        this.name= name;
        this.cards = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public boolean isPlayer(){
        return false;
    }

    public void takeCard(Card card) {
        cards.add(card);
        card.setHolder(this);
    }

    public void removeCard(Card card) {
        cards.remove(card);
    }

    public boolean hasCard(String cardName){
        for(Card card : cards){
            if(card.getName().equals(cardName)){
                return true;
            }
        }
        return false;
    }

    public List<Card> getCardList(){
        return cards;
    }
}
