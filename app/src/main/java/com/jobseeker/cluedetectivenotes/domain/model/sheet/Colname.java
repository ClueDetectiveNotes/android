package com.jobseeker.cluedetectivenotes.domain.model.sheet;

import com.jobseeker.cluedetectivenotes.domain.model.player.Player;

public class Colname {
    Player player;
    public Colname(Player player) {
        this.player = player;
    }

    public String getName(){
        return player.getName();
    }
}
