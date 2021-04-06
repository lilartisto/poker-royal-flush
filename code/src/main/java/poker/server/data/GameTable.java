package poker.server.data;

import poker.server.data.cards.Card;
import poker.server.data.cards.Deck;

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

    public boolean addPlayer(Player player){
        for(int i = 0; i < players.length; i++){
            if(players[i] == null){
                players[i] = player;
                return true;
            }
        }
        return false;
    }
}