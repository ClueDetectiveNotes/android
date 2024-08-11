package com.jobseeker.cluedetectivenotes.domain.model.card;

import com.jobseeker.cluedetectivenotes.domain.model.player.CardHolder;

public class Card {
    Cards card;
    CardHolder holder;

    public Card(Cards card){
        this.card = card;
    }

    public String getName() { return card.name(); }

    public Type getCardType(){
        return card.getType();
    }

    public void setHolder(CardHolder holder){
        if(this.holder != null){
            this.holder.removeCard(this);
        }
        this.holder = holder;
    }

    public CardHolder getHolder(){
        return holder;
    }
}
