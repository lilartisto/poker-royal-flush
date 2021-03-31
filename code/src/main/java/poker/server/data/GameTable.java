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

    }
}