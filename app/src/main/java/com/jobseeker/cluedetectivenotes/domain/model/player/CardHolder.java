package com.jobseeker.cluedetectivenotes.domain.model.player;

import java.util.UUID;

public class CardHolder {
    protected UUID id;
    protected String name;

    public CardHolder(String name){
        this.id = UUID.randomUUID();
        this.name= name;
    }

    public UUID getId() {
        return id;
    }

    public String getName(){
        return name;
    }

    public boolean isPlayer(){
        return false;
    }
}
