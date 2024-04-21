package com.jobseeker.cluedetectivenotes.model.card;

import static com.jobseeker.cluedetectivenotes.model.card.Edition.CLASSIC;
import static com.jobseeker.cluedetectivenotes.model.card.Type.CRIME_SCENE;
import static com.jobseeker.cluedetectivenotes.model.card.Type.SUSPECT;
import static com.jobseeker.cluedetectivenotes.model.card.Type.WEAPON;

import java.util.ArrayList;
import java.util.List;

public enum Cards {
    SCARLET(SUSPECT,CLASSIC), MUSTARD(SUSPECT,CLASSIC), WHITE(SUSPECT,CLASSIC), GREEN(SUSPECT,CLASSIC), PEACOCK(SUSPECT,CLASSIC), PLUM(SUSPECT,CLASSIC),
    CANDLESTICK(WEAPON,CLASSIC), KNIFE(WEAPON,CLASSIC), LEAD_PIPE(WEAPON,CLASSIC), REVOLVER(WEAPON,CLASSIC), ROPE(WEAPON,CLASSIC), WRENCH(WEAPON,CLASSIC),
    BATHROOM(CRIME_SCENE,CLASSIC), STUDY(CRIME_SCENE,CLASSIC), GAME_ROOM(CRIME_SCENE,CLASSIC), GARAGE(CRIME_SCENE,CLASSIC), BEDROOM(CRIME_SCENE,CLASSIC), LIVING_ROOM(CRIME_SCENE,CLASSIC), KITCHEN(CRIME_SCENE,CLASSIC), YARD(CRIME_SCENE,CLASSIC), DINING_ROOM(CRIME_SCENE,CLASSIC);

    private static final List<Cards> SUSPECTS = new ArrayList<Cards>();
    private static final List<Cards> WEAPONS = new ArrayList<Cards>();
    private static final List<Cards> CRIME_SCENES = new ArrayList<Cards>();

    static {
        for(Cards card : values()){
            if(card.type == SUSPECT){
                SUSPECTS.add(card);
            }else if(card.type == WEAPON){
                WEAPONS.add(card);
            }else if(card.type == CRIME_SCENE){
                CRIME_SCENES.add(card);
            }
        }
    }

    private final Type type;
    private final Edition edition;

    Cards(Type type, Edition edition){
        this.type = type;
        this.edition = edition;
    }

    public static List<Cards> getSuspects(){
        return SUSPECTS;
    }
    public static List<Cards> getWeapons(){
        return WEAPONS;
    }
    public static List<Cards> getCrimeScenes(){
        return CRIME_SCENES;
    }
}
