package com.jobseeker.cluedetectivenotes.domain.model.game;

import com.jobseeker.cluedetectivenotes.domain.model.card.Card;
import com.jobseeker.cluedetectivenotes.domain.model.card.Cards;
import com.jobseeker.cluedetectivenotes.domain.model.game.exceptions.CardNotFoundException;
import com.jobseeker.cluedetectivenotes.domain.model.player.CardHolder;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    private final List<Card> suspects;
    private final List<Card> weapons;
    private final List<Card> crimeScenes;

    public Deck(CardHolder unknownOne){
        suspects = new ArrayList<>();
        weapons = new ArrayList<>();
        crimeScenes = new ArrayList<>();

        for(Cards card : Cards.getSuspects()){
            Card suspect = new Card(card);
            suspects.add(suspect);
            unknownOne.takeCard(suspect);
        }
        for(Cards card : Cards.getWeapons()){
            Card weapon = new Card(card);
            weapons.add(weapon);
            unknownOne.takeCard(weapon);
        }
        for(Cards card : Cards.getCrimeScenes()){
            Card crimeScene = new Card(card);
            crimeScenes.add(crimeScene);
            unknownOne.takeCard(crimeScene);
        }
    }
    public List<Card> getSuspects() {
        return suspects;
    }
    public List<Card> getWeapons() {
        return weapons;
    }
    public List<Card> getCrimeScenes() {
        return crimeScenes;
    }

    public int getDeckSize(){
        return suspects.size()+weapons.size()+crimeScenes.size();
    }

    public Card findCard(String cardName) throws CardNotFoundException {
        Card foundCard = null;
        List<Card> allCards = new ArrayList<>();
        allCards.addAll(suspects);
        allCards.addAll(weapons);
        allCards.addAll(crimeScenes);

        for(Card card : allCards){
            if(card.getName().equals(cardName)){
                foundCard = card;
            }
        }
        if(foundCard != null){
            return foundCard;
        }else{
            throw new CardNotFoundException();
        }
    }
}
