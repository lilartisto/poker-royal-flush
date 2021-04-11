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

    public void setState(int state){
        this.state = state;
    }

    public void setMoney(int money){
        this.money = money;
    }

    public void setPotValue(int potValue){
        this.potValue = potValue;
    }

    public Card[] getHandCards(){
        return handCards;
    }

    public int getState(){
        return state;
    }

    public int getPotValue(){
        return potValue;
    }

    public int getMoney(){
        return money;
    }
}