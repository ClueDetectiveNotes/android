package com.jobseeker.cluedetectivenotes.domain.model.player;

import java.util.UUID;

public class Player {
    UUID id;
    String name;

    public Player(String name){
        this.id = UUID.randomUUID();
        this.name= name;
    }

    public UUID getId() {
        return id;
    }

    public String getName(){
        return name;
    }
}
