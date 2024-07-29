package com.jobseeker.cluedetectivenotes.domain.model.sheet;

import com.jobseeker.cluedetectivenotes.domain.model.player.CardHolder;
import com.jobseeker.cluedetectivenotes.domain.model.player.Player;
import com.jobseeker.cluedetectivenotes.domain.model.player.Type;

import java.util.UUID;

public class Colname {
    CardHolder holder;
    public Colname(CardHolder holder) {
        this.holder = holder;
    }

    public String getName(){
        return holder.getName();
    }
    public UUID getId() { return holder.getId(); }
    public boolean isUser(){
        return holder.isPlayer() && ((Player) holder).getType() == Type.USER;
    }
}
