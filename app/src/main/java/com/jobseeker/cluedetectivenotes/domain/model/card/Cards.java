package com.jobseeker.cluedetectivenotes.domain.model.card;

import static com.jobseeker.cluedetectivenotes.domain.model.card.Edition.CLASSIC;

import com.jobseeker.cluedetectivenotes.domain.model.card.exceptions.CardNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Cards {
    SCARLET(Type.SUSPECT,CLASSIC), MUSTARD(Type.SUSPECT,CLASSIC), WHITE(Type.SUSPECT,CLASSIC), GREEN(Type.SUSPECT,CLASSIC), PEACOCK(Type.SUSPECT,CLASSIC), PLUM(Type.SUSPECT,CLASSIC),
    CANDLESTICK(Type.WEAPON,CLASSIC), KNIFE(Type.WEAPON,CLASSIC), LEAD_PIPE(Type.WEAPON,CLASSIC), REVOLVER(Type.WEAPON,CLASSIC), ROPE(Type.WEAPON,CLASSIC), WRENCH(Type.WEAPON,CLASSIC),
    BATHROOM(Type.CRIME_SCENE,CLASSIC), STUDY(Type.CRIME_SCENE,CLASSIC), GAME_ROOM(Type.CRIME_SCENE,CLASSIC), GARAGE(Type.CRIME_SCENE,CLASSIC), BEDROOM(Type.CRIME_SCENE,CLASSIC), LIVING_ROOM(Type.CRIME_SCENE,CLASSIC), KITCHEN(Type.CRIME_SCENE,CLASSIC), YARD(Type.CRIME_SCENE,CLASSIC), DINING_ROOM(Type.CRIME_SCENE,CLASSIC);

    private static final List<Cards> allCards = new ArrayList<>();
    private static final List<Cards> SUSPECTS = new ArrayList<Cards>();
    private static final List<Cards> WEAPONS = new ArrayList<Cards>();
    private static final List<Cards> CRIME_SCENES = new ArrayList<Cards>();

    static {
        allCards.addAll(Arrays.asList(values()));

        for(Cards card : values()){
            if(card.type == Type.SUSPECT){
                SUSPECTS.add(card);
            }else if(card.type == Type.WEAPON){
                WEAPONS.add(card);
            }else if(card.type == Type.CRIME_SCENE){
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

    public Type getType(){
        return type;
    }

    public static Cards findCard(String name) throws CardNotFoundException {
        List<Cards> foundCard = allCards.stream().filter(cards->cards.name().equals(name)).collect(Collectors.toList());
        if(foundCard.isEmpty()) throw new CardNotFoundException();
        return foundCard.get(0);
    }
}
