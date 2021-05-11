package poker.server;

import org.json.JSONObject;
import poker.server.communication.ClientConnector;
import poker.server.data.database.DataBaseController;
import poker.server.data.GameTable;
import poker.server.gamecontrollers.GameController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

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
            //TODO
            // moze wrzucic to do jakiegos pliku
            dataBaseController = new DataBaseController(
                    "jdbc:postgresql://localhost:7432/poker_database",
                    "poker_user",
                    "Fl94yuwHClB6eKltjLnPYQ=="
            );
        } catch (SQLException e) {
            System.err.println("Cannot connect to database. " + e.getMessage());
        }

        GameController gameController = new GameController(gameTable);
        gameController.playGame();
    }
}