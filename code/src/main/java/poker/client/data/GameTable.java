package poker.client.data;

import poker.client.Game;
import poker.client.data.cards.Card;

public class GameTable {

	private Card[] tableCards;
	private Player[] players;
	private int potValue;

	public GameTable(int mainPlayerSeat, Player mainPlayer){
		tableCards = new Card[5];
		players = new Player[6];
		players[mainPlayerSeat] = mainPlayer;
	}

	public void addPlayer(Player player, int index){
		players[index] = player;
	}

}