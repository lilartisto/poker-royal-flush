package poker.server.gamecontrollers;

import poker.server.Game;
import poker.server.communication.ClientConnector;
import poker.server.communication.MsgFormat;
import poker.server.data.GameTable;
import poker.server.data.Player;
import poker.server.data.cards.Deck;

import java.util.Iterator;

public class RoundController {

	private GameTable gameTable;
	private Deck deck;

	public RoundController(GameTable gameTable){
		this.gameTable = gameTable;
		deck = new Deck();
	}

	public void playRound(){
		startRound();

		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void startRound(){
		Iterator<Player> players = gameTable.playersIterator();
		ClientConnector clientConnector = Game.getClientConnector();

		while (players.hasNext()) {
			Player player = players.next();

			System.out.println("Losuje 2 karty dla gracza " + player.nickname);

			player.setHandCards(deck.getRandomCard(), deck.getRandomCard());
			clientConnector.sendMsg(MsgFormat.startMsg(player.getHandCards()), player);
		}
	}

	private void drawThreeTableCards(){
		throw new UnsupportedOperationException("Not implemented yet");
	}

	private void drawOneTableCard(){
		throw new UnsupportedOperationException("Not implemented yet");
	}

	private Player chooseWinner(){
		throw new UnsupportedOperationException("Not implemented yet");
	}

}