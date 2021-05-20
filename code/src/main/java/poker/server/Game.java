package poker.server;

import org.json.JSONObject;
import poker.server.communication.ClientConnector;
import poker.server.data.database.DataBaseController;
import poker.server.data.GameTable;
import poker.server.gamecontrollers.GameController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;

public class Game {

    private static ClientConnector clientConnector;
    private static GameTable gameTable;
    private static DataBaseController dataBaseController;
    private static int startMoney;
    private static int blind;

    public static ClientConnector getClientConnector(){
        return clientConnector;
    }

    public static GameTable getGameTable(){
        return gameTable;
    }

    public static DataBaseController getDataBaseController(){
        return dataBaseController;
    }

    public static int getStartMoney(){
        return startMoney;
    }

    public static int getBlind(){
        return blind;
    }

    public static void main(String[] args) {
        int port;

        try{
            port = Integer.parseInt(args[0]);
        } catch (Exception e) {
            port = 5000;
        }

        try {
            startMoney = Integer.parseInt(args[1]);
        } catch (Exception e){
            startMoney = 200;
        }

        try {
            blind = Integer.parseInt(args[2]);
        } catch (Exception e){
            blind = 1;
        }

        System.out.println("Port number = " + port);
        System.out.println("Start money = " + startMoney);
        System.out.println("Blind = " + blind);

        try {
            clientConnector = new ClientConnector(port);
        } catch (IOException e){
            System.err.println("Cannot run a server");
            return;
        }

        gameTable = new GameTable();
        try {
            connectToDataBase();
        } catch (Exception e) {
            System.err.println("Cannot connect to database. " + e.getMessage());
        }

        GameController gameController = new GameController(gameTable);
        gameController.playGame();
    }

    private static void connectToDataBase() throws Exception {
        Properties databaseProp = new Properties();
        String path = new File("").getAbsolutePath() + "/src/main/resources/config/database.config";
        databaseProp.load(new FileInputStream(path));

        String url = databaseProp.getProperty("database.url");
        String username = databaseProp.getProperty("database.username");
        String password = databaseProp.getProperty("database.password");

        if(url == null || username == null || password == null){
            throw new Exception("Database config file must contain: " +
                    "database.url, database.username, database.password. " +
                    "File = " + path);
        }

        dataBaseController = new DataBaseController(
                databaseProp.getProperty("database.url"),
                databaseProp.getProperty("database.username"),
                databaseProp.getProperty("database.password")
        );
    }
}