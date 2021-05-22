package poker.client;

import poker.client.communication.ServerConnector;
import poker.client.controller.GameMenuController;
import poker.client.data.GameTable;
import poker.client.data.Player;
import poker.client.data.cards.Deck;
import poker.client.view.TableView;

public class Game {

	//private static Player player = new Player("");
	private static ServerConnector serverConnector;
	private static TableView tableView;

	public static void setServerConnector(ServerConnector connector){
		serverConnector = connector;
	}

	public static ServerConnector getServerConnector(){
		return serverConnector;
	}

	public static void setTableView(TableView view){
		tableView = view;
	}

	public static TableView getTableView(){
		return tableView;
	}

	//public static Player getPlayer(){
	//	return gameTable.getMainPlayer();
	//}

	//public static void setPlayer(Player mainPlayer){
	//	player = mainPlayer;
	//}
}