package poker.server.data;

import poker.server.data.cards.Card;
import poker.server.data.cards.Deck;

import java.util.Iterator;

public class GameTable {

    private Card[] tableCards;
    private Player[] players;
    private int potValue;
    private int starterPlayer;
    private Deck deck;

    public GameTable() {
        tableCards = new Card[5];
        players = new Player[6];
        potValue = 0;
        starterPlayer = 0;
        deck = new Deck();
    }

    public int getStarterPlayerIndex(){
        return starterPlayer;
    }

    public void setStarterPlayerIndex(int starterPlayerIndex){
        starterPlayer = starterPlayerIndex;
    }

    public int getPotValue(){
        return potValue;
    }

    public void setPotValue(int potValue) {
        this.potValue = potValue;
    }

    public Card[] getTableCards(){
        return tableCards;
    }

    public Player[] getPlayers(){
        return players;
    }

    public int addPlayer(Player player){
        for(int i = 0; i < players.length; i++){
            if(players[i] == null){
                players[i] = player;
                return i;
            }
        }
        return -1;
    }

    public void addTableCard(Card card){
        for(int i = 0; i < tableCards.length; i++){
            if(tableCards[i] == null){
                tableCards[i] = card;
                return;
            }
        }
    }

    public int numberOfPlayers(){
        int counter = 0;
        for(int i = 0; i < players.length; i++){
            if(players[i] != null){
                counter++;
            }
        }
        return counter;
    }

    public Iterator<Player> playersIterator(){
        return new Iterator<>() {
            int i = 0;

            @Override
            public boolean hasNext() {
                for (int j = i; j < players.length; j++) {
                    if (players[j] != null) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public Player next() {
                while(i < players.length) {
                    i++;
                    if (players[i-1] != null) {
                        return players[i-1];
                    }
                }
                return null;
            }
        };
    }
}