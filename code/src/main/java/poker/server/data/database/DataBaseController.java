package poker.server.data.database;

import poker.server.data.Player;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseController {

    private Connection connection;
    private Statement statement;

    public DataBaseController(String url, String username, String password) {
        try{
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to database");
        } catch (SQLException e) {
            e.printStackTrace();
            //throw new IllegalArgumentException("Cannot connect to database");
        }
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

    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}