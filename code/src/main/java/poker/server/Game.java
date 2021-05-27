package poker.server;

import poker.server.communication.ClientConnector;
import poker.server.data.database.DataBaseController;
import poker.server.gamecontrollers.GameController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Game {

    private static int startMoney;
    private static int blind;
    private static int port;

    public static int getStartMoney(){
        return startMoney;
    }

    public static int getBlind(){
        return blind;
    }

    public static void main(String[] args) {
        initializeArgs(args);
        DataBaseController dbController = initDataBaseController();

        try {
            ClientConnector clientConnector = new ClientConnector(port, dbController);

            GameController gameController = new GameController(clientConnector);
            gameController.playGame();
        } catch (IOException e){
            System.err.println("Cannot run a server. " + e.getMessage());
        }
    }

    private static void initializeArgs(String[] args){
        initPort(args);
        initStartMoney(args);
        initBlind(args);

        System.out.println("Port number = " + port);
        System.out.println("Start money = " + startMoney);
        System.out.println("Blind = " + blind);
    }

    private static void initPort(String[] args){
        try{
            port = Integer.parseInt(args[0]);
        } catch (Exception e) {
            port = 5000;
        }
    }

    private static void initStartMoney(String[] args){
        try {
            startMoney = Integer.parseInt(args[1]);
        } catch (Exception e){
            startMoney = 200;
        }
    }

    private static void initBlind(String[] args){
        try {
            blind = Integer.parseInt(args[2]);
        } catch (Exception e){
            blind = 1;
        }
    }

    private static DataBaseController initDataBaseController(){
        try {
            return connectToDataBase();
        } catch (Exception e) {
            System.err.println("Cannot connect to database. " + e.getMessage());
            return null;
        }
    }

    private static DataBaseController connectToDataBase() throws Exception {
        Properties databaseProp = new Properties();
        String path = new File("").getAbsolutePath() + "/src/main/resources/config/database.config";
        databaseProp.load(new FileInputStream(path));

        String url = databaseProp.getProperty("database.url");
        String username = databaseProp.getProperty("database.username");
        String password = databaseProp.getProperty("database.password");

        return connectToDataBase(url, username, password, path);
    }

    private static DataBaseController connectToDataBase(String url, String username, String password, String path) throws Exception{
        if(url == null || username == null || password == null){
            throw new Exception("Database config file must contain: " +
                    "database.url, database.username, database.password. " +
                    "File = " + path);
        }

        return new DataBaseController(url, username, password);
    }
}