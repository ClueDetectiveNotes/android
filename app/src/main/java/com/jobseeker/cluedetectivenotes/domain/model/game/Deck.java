package com.jobseeker.cluedetectivenotes.domain.model.game;

import com.jobseeker.cluedetectivenotes.domain.model.card.Card;
import com.jobseeker.cluedetectivenotes.domain.model.card.Cards;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    private final List<Card> suspects;
    private final List<Card> weapons;
    private final List<Card> crimeScene;

    public Deck(){
        suspects = new ArrayList<>();
        weapons = new ArrayList<>();
        crimeScene = new ArrayList<>();

        for(Cards card : Cards.getSuspects()){
            suspects.add(new Card(card));
        }
        for(Cards card : Cards.getWeapons()){
            weapons.add(new Card(card));
        }
        for(Cards card : Cards.getCrimeScenes()){
            crimeScene.add(new Card(card));
        }
    }
    public List<Card> getSuspects() {
        return suspects;
    }
    public List<Card> getWeapons() {
        return weapons;
    }
    public List<Card> getCrimeScene() {
        return crimeScene;
    }

    public int getDeckSize(){
        return suspects.size()+weapons.size()+crimeScene.size();
    }
}
