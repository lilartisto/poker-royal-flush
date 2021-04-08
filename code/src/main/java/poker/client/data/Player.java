package poker.client.data;

import poker.client.data.cards.Card;

public class Player {

    public final String nickname;
    private Card[] handCards;
    private int money;
    private int state;
    private int potValue;

    public Player(String nickname) {
        handCards = new Card[2];
		this.nickname = nickname;
    }

    public void setHandCards(Card card1, Card card2){
        handCards[0] = card1;
        handCards[1] = card2;
    }

    public Card[] getHandCards(){
        return handCards;
    }
}