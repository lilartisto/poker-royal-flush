package poker.client;

import poker.client.communication.ServerConnector;
import poker.client.controller.GameMenuController;
import poker.client.data.GameTable;
import poker.client.data.Player;
import poker.client.data.cards.Deck;
import poker.client.view.TableView;

public class Game {

	private static GameTable gameTable;
	//private static Player player = new Player("");
	private static final Deck deck = new Deck();
	private static ServerConnector serverConnector;
	private static GameMenuController gameMenuController;
	private static TableView tableView;

	public static void setServerConnector(ServerConnector connector){
		serverConnector = connector;
	}

	public static ServerConnector getServerConnector(){
		return serverConnector;
	}

	public static Deck getDeck(){
		return deck;
	}

	public static void setTableView(TableView view){
		tableView = view;
	}

	public static TableView getTableView(){
		return tableView;
	}

	public static GameTable getGameTable(){
		return gameTable;
	}

	public static void setGameTable(GameTable table){
		gameTable = table;
	}

	//public static Player getPlayer(){
	//	return gameTable.getMainPlayer();
	//}

	//public static void setPlayer(Player mainPlayer){
	//	player = mainPlayer;
	//}

	public static GameMenuController getGameMenuController() {
		return gameMenuController;
	}

	public static void setGameMenuController(GameMenuController gameMenuController) {
		Game.gameMenuController = gameMenuController;
	}
}