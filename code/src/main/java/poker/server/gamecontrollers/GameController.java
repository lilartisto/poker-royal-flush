package poker.server.gamecontrollers;

import poker.server.communication.ClientConnector;
import poker.server.communication.msgformats.GameInfoMsgFormat;
import poker.server.communication.msgformats.StartMsgFormat;
import poker.server.data.GameTable;
import poker.server.data.cards.Card;

public class GameController {

    private final GameTable gameTable;
    private final ClientConnector clientConnector;

    public GameController(ClientConnector clientConnector) {
        gameTable = GameTable.getInstance();
        this.clientConnector = clientConnector;
    }

    public void playGame() {
        while (true) {
            if (gameTable.numberOfPlayers() <= 1) {
                waitForPlayers();
            }
            waitRoundDelay();

            try {
                RoundController roundController = new RoundController(gameTable, clientConnector);
                roundController.playRound();
                gameTable.moveStarterPlayerIndex();
            } catch (IllegalStateException ignored) {
            }
        }
    }

    private void waitForPlayers() {
        waitRoundDelay();
        sendStartMsgWithNullCards();
        sendGameInfo();
        waitForPlayersToJoin();
        sendGameInfo();
    }

    private void sendGameInfo() {
        clientConnector.sendMsgToAll(GameInfoMsgFormat.getMsg(GameTable.getInstance()));
    }

    private void sendStartMsgWithNullCards() {
        clientConnector.sendMsgToAll(StartMsgFormat.getMsg(new Card[]{null, null}));
    }

    private void waitRoundDelay() {
        long roundDelay = 6000;
        try {
            Thread.sleep(roundDelay);
        } catch (InterruptedException e) {
        }
    }

    private void waitForPlayersToJoin() {
        while (gameTable.numberOfPlayers() <= 1) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException ignored) {
            }
        }
    }

}