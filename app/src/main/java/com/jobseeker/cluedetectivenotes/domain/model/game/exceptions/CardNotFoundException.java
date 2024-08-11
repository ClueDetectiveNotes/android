package com.jobseeker.cluedetectivenotes.domain.model.game.exceptions;

public class CardNotFoundException extends Exception{
    public CardNotFoundException(){
        super("card not found");
    }
}
