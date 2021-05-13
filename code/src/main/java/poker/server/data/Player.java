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
    private double handStrength;
    private String lastMsg;

    public Player(String nickname) {
        this.nickname = nickname;
        money = Game.getStartMoney();
        handCards = new Card[2];
        state = PlayerStateProperties.AFTERFOLD;
        potValue = 0;
        handStrength = 0;
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

    public Card[] getHandCards(){
        return handCards;
    }

    public double getHandStrength(){
        return handStrength;
    }

    public void setHandStrength(double handStrength) {
        this.handStrength = handStrength;
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

    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }

    public String getLastMsg() {
        return lastMsg;
    }

    @Override
    public int hashCode() {
        return nickname.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Player){
            return nickname.equals(((Player) obj).nickname);
        }
        return false;
    }
}