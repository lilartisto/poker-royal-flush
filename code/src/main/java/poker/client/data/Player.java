package poker.client.data;

import poker.client.data.cards.Card;

public class Player {

    public final String nickname;
    private Card[] handCards;
    private int money;
    private int state;
    private int potValue;

    public Player(String nickname) {
		this.nickname = nickname;
    }

}