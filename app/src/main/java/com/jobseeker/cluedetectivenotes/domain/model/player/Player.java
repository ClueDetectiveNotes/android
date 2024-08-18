package com.jobseeker.cluedetectivenotes.domain.model.player;

public abstract class Player extends CardHolder{

    public Player() {
        super("");
    }

    @Override
    public boolean isPlayer(){
        return true;
    }

    public abstract Type getType();
}
