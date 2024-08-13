package com.jobseeker.cluedetectivenotes.application.useCase.gameSetting;

import androidx.annotation.NonNull;

import com.jobseeker.cluedetectivenotes.domain.model.card.Card;
import com.jobseeker.cluedetectivenotes.domain.model.game.Deck;
import com.jobseeker.cluedetectivenotes.domain.model.game.Game;
import com.jobseeker.cluedetectivenotes.domain.model.game.GameSetter;
import com.jobseeker.cluedetectivenotes.domain.model.game.exceptions.CardNotFoundException;
import com.jobseeker.cluedetectivenotes.domain.model.player.CardHolder;
import com.jobseeker.cluedetectivenotes.domain.model.player.Player;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.exceptions.MarkerMismatchException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SelectPublicCardUseCase {
    public JSONObject execute(String cardName) throws JSONException, CardNotFoundException, MarkerMismatchException {
        Game game = GameSetter.getGameInstance();
        Deck deck = game.getDeck();
        CardHolder publicOne = game.getPublicOne();

        if(publicOne.hasCard(cardName)){
            game.getUnknownOne().takeCard(deck.findCard(cardName));
        }else{
            if(publicOne.getCardList().size() < game.getNumOfPublicCards()){
                publicOne.takeCard(deck.findCard(cardName));
            }
        }

        return getJsonObject(publicOne, game);
    }

    @NonNull
    private static JSONObject getJsonObject(CardHolder publicOne, Game game) throws JSONException {
        List<String> publicCardList = new ArrayList<>();
        List<String> handList = new ArrayList<>();

        for(Card card: publicOne.getCardList()){
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
