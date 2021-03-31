package poker.server.data.database;

import poker.server.data.Player;

import java.sql.Connection;
import java.sql.Statement;

public class DataBaseController {

    private Connection connection;
    private Statement statement;

    public DataBaseController() {

    }

    public void createTable() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void insertPlayer(Player player) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public Player getPlayer(String nickname) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void deletePlayer(Player player) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}