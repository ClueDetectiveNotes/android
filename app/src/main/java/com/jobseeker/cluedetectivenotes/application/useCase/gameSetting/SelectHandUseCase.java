package com.jobseeker.cluedetectivenotes.application.useCase.gameSetting;

import androidx.annotation.NonNull;

import com.jobseeker.cluedetectivenotes.domain.model.card.Card;
import com.jobseeker.cluedetectivenotes.domain.model.game.Deck;
import com.jobseeker.cluedetectivenotes.domain.model.game.Game;
import com.jobseeker.cluedetectivenotes.domain.model.game.GameSetter;
import com.jobseeker.cluedetectivenotes.domain.model.game.exceptions.CardNotFoundException;
import com.jobseeker.cluedetectivenotes.domain.model.player.Player;
import com.jobseeker.cluedetectivenotes.application.useCase.gameSetting.exceptions.HaveAllOneKindOfCardException;
import com.jobseeker.cluedetectivenotes.domain.model.sheet.cell.exceptions.MarkerMismatchException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SelectHandUseCase {
    public JSONObject execute(String cardName) throws JSONException, CardNotFoundException, MarkerMismatchException, HaveAllOneKindOfCardException {
        Game game = GameSetter.getGameInstance();
        Deck deck = game.getDeck();
        Player user = game.getUser();

        if(user.hasCard(cardName)){
            game.getUnknownOne().takeCard(deck.findCard(cardName));
        }else{
            if(user.getCardList().size() < game.getNumOfHands()){
                //용의자 카드를 모두 선택한 경우
                if(new HashSet<Card>(){{
                    addAll(user.getCardList());
                    add(deck.findCard(cardName));
                }}.containsAll(new HashSet<>(deck.getSuspects()))) throw new HaveAllOneKindOfCardException();
                //흉기 카드를 모두 선택한 경우
                if(new HashSet<Card>(){{
                    addAll(user.getCardList());
                    add(deck.findCard(cardName));
                }}.containsAll(new HashSet<>(deck.getWeapons()))) throw new HaveAllOneKindOfCardException();

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
