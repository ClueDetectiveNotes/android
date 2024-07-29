package com.jobseeker.cluedetectivenotes.domain.model.player;

public class User extends Player{

    public User(Player player){
        this.id = player.id;
        this.name = player.name;
    }
    @Override
    public Type getType() {
        return Type.USER;
    }
}
