package poker.client;

import poker.client.communication.ServerConnector;
import poker.client.controller.GameMenuController;
import poker.client.data.GameTable;
import poker.client.data.Player;
import poker.client.data.cards.Deck;
import poker.client.view.TableView;

public class Game {

	private static GameTable gameTable;
	private static Player player;
	private static final Deck deck = new Deck();
	private static ServerConnector serverConnector;
	private static GameMenuController gameMenuController;
	private static TableView tableView;

	public static void setServerConnector(ServerConnector connector){
		serverConnector = connector;
	}

	public static Deck getDeck(){
		return deck;
	}
}