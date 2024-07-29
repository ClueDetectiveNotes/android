package com.jobseeker.cluedetectivenotes.domain.model.card;

import com.jobseeker.cluedetectivenotes.domain.model.player.CardHolder;

public class Card {
    Cards card;
    CardHolder holder;

    public Card(Cards card){
        this.card = card;
    }

    public Type getCardType(){
        return card.getType();
    }

    public void setHolder(CardHolder holder){
        this.holder = holder;
    }

    public CardHolder getHolder(){
        return holder;
    }
}
