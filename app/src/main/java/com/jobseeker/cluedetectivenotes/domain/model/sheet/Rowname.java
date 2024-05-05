package com.jobseeker.cluedetectivenotes.domain.model.sheet;

import com.jobseeker.cluedetectivenotes.domain.model.card.Cards;

public class Rowname {
    private Cards card;
    public Rowname(Cards card){
        this.card = card;
    }
    public Cards getCard() {
        return this.card;
    }
}
