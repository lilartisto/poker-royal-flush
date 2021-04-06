package poker.client;

import poker.client.communication.ServerConnector;
import poker.client.controller.GameMenuController;
import poker.client.data.GameTable;
import poker.client.data.Player;
import poker.client.data.cards.Deck;
import poker.client.view.TableView;

public class Game {

	private static GameTable GAMETABLE;
	private static Player PLAYER;
	private static Deck DECK;
	private static ServerConnector SERVERCONNCETOR;
	private static GameMenuController GAMEMENUCONTROLLER;
	private static TableView TABLEVIEW;

	public static void setSERVERCONNCETOR(ServerConnector serverconncetor){
		SERVERCONNCETOR = serverconncetor;
	}
}