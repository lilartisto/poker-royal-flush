package poker.server.gamecontrollers;

import poker.server.Game;
import poker.server.communication.msgformats.GameInfoMsgFormat;
import poker.server.communication.msgformats.StartMsgFormat;
import poker.server.data.GameTable;
import poker.server.data.cards.Card;

public class GameController {

	private GameTable gameTable;

	public GameController(GameTable gameTable){
		this.gameTable = gameTable;
	}

	public void playGame(){
		while(true) {
			if(gameTable.numberOfPlayers() <= 1) {
				waitRoundDelay();
				sendStartMsgWithNullCards();
				sendGameInfo();
				waitForPlayers();
				sendGameInfo();
			}
			waitRoundDelay();

			try {
				RoundController roundController = new RoundController(gameTable, Game.getClientConnector());
				roundController.playRound();
				gameTable.moveStarterPlayerIndex();
			} catch (IllegalStateException ignored){ }
		}
	}

	private void sendGameInfo(){
		Game.getClientConnector().sendMsgToAll(GameInfoMsgFormat.getMsg(Game.getGameTable()));
	}

	private void sendStartMsgWithNullCards(){
		Game.getClientConnector().sendMsgToAll(StartMsgFormat.getMsg(new Card[]{null, null}));
	}

	private void waitRoundDelay(){
		long roundDelay = 6000;
		try {
			Thread.sleep(roundDelay);
		} catch (InterruptedException e) { }
	}

	private void waitForPlayers(){
		while (gameTable.numberOfPlayers() <= 1) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException ignored) {
			}
		}
	}

}