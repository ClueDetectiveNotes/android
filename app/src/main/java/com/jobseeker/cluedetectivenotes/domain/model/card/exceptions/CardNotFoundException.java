package com.jobseeker.cluedetectivenotes.domain.model.card.exceptions;

public class CardNotFoundException extends Exception{
    public CardNotFoundException(){
        super("Card Not Found");
    }
}
