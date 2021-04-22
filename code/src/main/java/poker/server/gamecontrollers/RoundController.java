package poker.server.gamecontrollers;

import poker.server.Game;
import poker.server.communication.ClientConnector;
import poker.server.communication.msgformats.GameInfoMsgFormat;
import poker.server.communication.msgformats.StartMsgFormat;
import poker.server.data.GameTable;
import poker.server.data.Player;
import poker.server.data.cards.Deck;

import java.util.Iterator;

public class RoundController {

	private GameTable gameTable;
	private Deck deck;
	private ClientConnector clientConnector;

	public RoundController(GameTable gameTable, ClientConnector clientConnector){
		this.gameTable = gameTable;
		this.clientConnector = clientConnector;
		deck = new Deck();
	}

	public void playRound(){
		CycleController cycle = new CycleController(gameTable, clientConnector);

		try {
			startRound();
			gameTable.setPotValue(0);
			cycle.setMinPot(Game.getBlind());
			cycle.playCycle();

			for (int i = 0; i < 3; i++) {
				drawTableCards(i == 0 ? 3 : 1);
				cycle.setMinPot(0);
				cycle.playCycle();
			}

			chooseHandCardsWinner();
		} catch (IllegalStateException e) {
			chooseFoldWinner();
		}
	}

	private void startRound(){
		Iterator<Player> players = gameTable.playersIterator();

		while (players.hasNext()) {
			Player player = players.next();
			player.setHandCards(deck.getRandomCard(), deck.getRandomCard());
			clientConnector.sendMsg(StartMsgFormat.getMsg(player.getHandCards()), player);
		}
	}

	private void sendGameInfoMsg(){
		clientConnector.sendMsgToAll(GameInfoMsgFormat.getMsg(gameTable));
	}

	private void drawTableCards(int amount){
		for(int i = 0; i < amount; i++){
			gameTable.addTableCard(deck.getRandomCard());
		}
	}

	private Player chooseHandCardsWinner(){
		//TODO

		System.out.println("Wybieram zwyciezce");
		return null;
		//throw new UnsupportedOperationException("Not implemented yet");
	}

	private Player chooseFoldWinner(){
		throw new UnsupportedOperationException("Not implemented yet");
	}

}