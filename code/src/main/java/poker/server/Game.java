package poker.server;

import poker.server.communication.ClientConnector;
import poker.server.data.database.DataBaseController;
import poker.server.data.GameTable;

public class Game {

    public static final ClientConnector CLIENTCONNECTOR = new ClientConnector();
    public static final GameTable GAMETABLE = new GameTable();
    public static final DataBaseController DBCONTROLLER = new DataBaseController();

    public static void main(String[] args) {
        System.out.println("server works");
    }

}