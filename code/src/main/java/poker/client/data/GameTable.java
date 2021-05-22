package poker.client.data;

import poker.client.data.cards.Card;

public class GameTable {

	private static GameTable instance;

	public static GameTable getInstance(){
		if(instance == null){
			instance = new GameTable();
		}
		return instance;
	}

	private Card[] tableCards;
	private Player[] players;
	private int potValue;
	private int mainPlayerSeat;
	private Card[] handCards;

	private GameTable(){
		tableCards = new Card[5];
		players = new Player[6];
		//players[mainPlayerSeat] = mainPlayer;
		mainPlayerSeat = 0;
		handCards = new Card[2];
	}

	public void addPlayer(Player player, int index){
		players[index] = player;
	}

	public Player[] getPlayers(){
		return players;
	}

	public void setMainPlayerSeat(int mainPlayerSeat){
		this.mainPlayerSeat = mainPlayerSeat;
	}

	public Player getMainPlayer(){
		return players[mainPlayerSeat];
	}

	public int getPotValue(){
		return potValue;
	}

	public Card[] getTableCards(){
		return tableCards;
	}

	public void setPotValue(int potValue){
		this.potValue = potValue;
	}

	public void setPlayer(Player player, int index){
		players[index] = player;
	}

	public void setTableCard(Card card, int index){
		tableCards[index] = card;
	}

	public void setHandCards(Card card0, Card card1){
		handCards[0] = card0;
		handCards[1] = card1;
	}

	public Card[] getHandCards(){
		return handCards;
	}

	public void seat(int mainPlayerSeat){
		this.mainPlayerSeat = mainPlayerSeat;
		handCards[0] = null;
		handCards[1] = null;
	}
}