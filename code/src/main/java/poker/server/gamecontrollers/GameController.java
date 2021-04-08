package poker.server.gamecontrollers;

import poker.server.data.GameTable;

public class GameController {

	private GameTable gameTable;

	public GameController(GameTable gameTable){
		this.gameTable = gameTable;
	}

	public void playGame(){
		while(true) {
			waitForPlayers();

			RoundController roundController = new RoundController(gameTable);
			roundController.playRound();
		}
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