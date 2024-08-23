package com.jobseeker.cluedetectivenotes.application.useCase.gameSetting;

import com.jobseeker.cluedetectivenotes.domain.model.card.Card;
import com.jobseeker.cluedetectivenotes.domain.model.game.Game;
import com.jobseeker.cluedetectivenotes.domain.model.game.GameSetter;

import java.util.ArrayList;
import java.util.List;

public class InitPublicCardsUseCase {
    public void execute(){
        Game game = GameSetter.getGameInstance();
        List<Card> publicCards = new ArrayList<>(game.getPublicOne().getCardList());
        for(Card card:publicCards){
            game.getUnknownOne().takeCard(card);
        }
    }
}
