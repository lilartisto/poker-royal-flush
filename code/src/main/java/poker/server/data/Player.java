package poker.server.data;

import poker.server.data.cards.Card;

public class Player {

    public final String nickname;
    private int money;
    private Card[] handCards;
    private int state;
    private int potValue;

    public Player(String nickname) {
        this.nickname = nickname;
    }
}