package com.jobseeker.cluedetectivenotes.model.sheet;

import com.jobseeker.cluedetectivenotes.model.card.Cards;

public class Rowname {
    private Cards card;
    public Rowname(Cards card){
        this.card = card;
    }
    public Cards getCard() {
        return this.card;
    }
}
