package com.jobseeker.cluedetectivenotes.application.useCase.gameSetting;

import com.jobseeker.cluedetectivenotes.domain.model.card.Card;
import com.jobseeker.cluedetectivenotes.domain.model.game.Deck;
import com.jobseeker.cluedetectivenotes.domain.model.game.Game;
import com.jobseeker.cluedetectivenotes.domain.model.game.GameSetter;
import com.jobseeker.cluedetectivenotes.domain.model.player.Player;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.exceptions.MarkerMismatchException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class LoadCardListUseCase {
    public JSONObject execute() throws JSONException, MarkerMismatchException {
        Game game = GameSetter.getGameInstance();
        Deck deck = game.getDeck();

        List<Card> suspects = deck.getSuspects();
        List<Card> weapons = deck.getWeapons();
        List<Card> crimeScenes = deck.getCrimeScenes();

        List<String> suspectCardList = new ArrayList<>();
        List<String> weaponCardList = new ArrayList<>();
        List<String> crimeSceneCardList = new ArrayList<>();

        List<String> publicCardList = new ArrayList<>();
        List<String> handList = new ArrayList<>();
        List<String> openedCardList = new ArrayList<>();

        for(Card card:suspects){
            suspectCardList.add(card.getName());
        }
        for(Card card:weapons){
            weaponCardList.add(card.getName());
        }
        for(Card card:crimeScenes){
            crimeSceneCardList.add(card.getName());
        }

        for(Card card:game.getUser().getCardList()){
            handList.add(card.getName());
        }
        for(Card card:game.getPublicOne().getCardList()){
            publicCardList.add(card.getName());
        }

        openedCardList.addAll(handList);
        openedCardList.addAll(publicCardList);

        JSONObject cardsState = new JSONObject();
        cardsState.put("suspectCardList", suspectCardList);
        cardsState.put("weaponCardList", weaponCardList);
        cardsState.put("crimeSceneCardList", crimeSceneCardList);
        cardsState.put("publicCardList", publicCardList);
        cardsState.put("handList", handList);
        cardsState.put("openedCardList", openedCardList);

        cardsState.put("numOfHands", game.getNumOfHands());
        cardsState.put("numOfPublicCards", game.getNumOfPublicCards());

        return cardsState;
    }
}