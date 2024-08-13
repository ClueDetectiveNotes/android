package com.jobseeker.cluedetectivenotes.application.useCase.gameSetting;

import androidx.annotation.NonNull;

import com.jobseeker.cluedetectivenotes.domain.model.card.Card;
import com.jobseeker.cluedetectivenotes.domain.model.game.Deck;
import com.jobseeker.cluedetectivenotes.domain.model.game.Game;
import com.jobseeker.cluedetectivenotes.domain.model.game.GameSetter;
import com.jobseeker.cluedetectivenotes.domain.model.game.exceptions.CardNotFoundException;
import com.jobseeker.cluedetectivenotes.domain.model.player.Player;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.exceptions.MarkerMismatchException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SelectHandUseCase {
    public JSONObject execute(String cardName) throws JSONException, CardNotFoundException, MarkerMismatchException {
        Game game = GameSetter.getGameInstance();
        Deck deck = game.getDeck();
        Player user = game.getUser();

        if(user.hasCard(cardName)){
            game.getUnknownOne().takeCard(deck.findCard(cardName));
        }else{
            if(user.getCardList().size() < game.getNumOfHands()){
                user.takeCard(deck.findCard(cardName));
            }
        }

        return getJsonObject(user, game);
    }

    @NonNull
    private static JSONObject getJsonObject(Player user, Game game) throws JSONException {
        List<String> publicCardList = new ArrayList<>();
        List<String> handList = new ArrayList<>();

        for(Card card: user.getCardList()){
            handList.add(card.getName());
        }
        for(Card card: game.getPublicOne().getCardList()){
            publicCardList.add(card.getName());
        }

        JSONObject cardsState = new JSONObject();
        cardsState.put("publicCardList", publicCardList);
        cardsState.put("handList", handList);
        return cardsState;
    }
}
