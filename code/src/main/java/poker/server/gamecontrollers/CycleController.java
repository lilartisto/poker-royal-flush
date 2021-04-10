package poker.server.gamecontrollers;

import poker.client.Game;
import poker.client.communication.ServerConnector;
import poker.server.communication.ClientConnector;
import poker.server.data.GameTable;

public class CycleController {

	/*
		musi wyrzucac IllegalStateException kiedy
		wszyscy gracze spasowali i zostal tylko wygrany
	 */

	private GameTable gameTable;
	private ClientConnector clientConnector;

	public CycleController(GameTable gameTable, ClientConnector clientConnector){
		this.gameTable = gameTable;
		this.clientConnector = clientConnector;
	}

	public void playCycle(){
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		//throw new UnsupportedOperationException("Not implemented yet");
	}

	private boolean isOver(){
		throw new UnsupportedOperationException("Not implemented yet");
	}

	private void nextMove(){
		throw new UnsupportedOperationException("Not implemented yet");
	}

}