package poker.server.data;

import poker.properties.PlayerStateProperties;
import poker.server.Game;
import poker.server.data.cards.Card;

public class Player {

    public final String nickname;
    private int money;
    private Card[] handCards;
    private int state;
    private int potValue;

    public Player(String nickname) {
        this.nickname = nickname;
        money = Game.getStartMoney();
        handCards = new Card[2];
        state = PlayerStateProperties.AFTERFOLD;
        potValue = 0;
    }

    public int getMoney(){
        return money;
    }

    public int getState(){
        return state;
    }

    public int getPotValue(){
        return potValue;
    }
}