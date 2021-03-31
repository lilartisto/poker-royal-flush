package poker.client;

import poker.client.communication.ServerConnector;
import poker.client.controller.GameMenuController;
import poker.client.data.GameTable;
import poker.client.data.Player;
import poker.client.data.cards.Deck;
import poker.client.view.TableView;

public class Game {

	public static final GameTable GAMETABLE = new GameTable();
	//public static final Player PLAYER;
	public static final Deck DECK = new Deck();
	public static final ServerConnector SERVERCONNCETOR = new ServerConnector();
	public static final GameMenuController GAMEMENUCONTROLLER = new GameMenuController();
	public static final TableView TABLEVIEW = new TableView();

	public static void main(String[] args){
		System.out.println("client works");
	}

}