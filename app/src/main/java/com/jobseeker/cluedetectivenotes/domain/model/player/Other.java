package com.jobseeker.cluedetectivenotes.domain.model.player;

public class Other extends Player{

    public Other(){
        super();
    }
    public Other(Player player){
        this.id = player.id;
        this.name = player.name;
    }
    @Override
    public Type getType() {
        return Type.OTHER;
    }
}
