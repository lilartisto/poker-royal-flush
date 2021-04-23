package poker.server.gamecontrollers;

import poker.properties.PlayerStateProperties;
import poker.server.Game;
import poker.server.communication.ClientConnector;
import poker.server.communication.msgformats.EndMsgFormat;
import poker.server.communication.msgformats.GameInfoMsgFormat;
import poker.server.communication.msgformats.StartMsgFormat;
import poker.server.data.GameTable;
import poker.server.data.Player;
import poker.server.data.cards.Deck;
import poker.server.gamecontrollers.hands.PokerHandCalculator;

import java.util.*;

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

			endRoundByHandCardsWinner();
		} catch (IllegalStateException e) {
			endRoundByFoldWinner();
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

	private void endRoundByHandCardsWinner(){
		Set<Player> winners = getWinnersByHandStrength(getPlayersSortedByHandStrength());
		clientConnector.sendMsgToAll(EndMsgFormat.getMsg(gameTable, winners));
		updateGameTableAndPlayers(winners);
	}

	private void endRoundByFoldWinner(){
		Set<Player> winners = getWinnersByNotFolded();
		clientConnector.sendMsgToAll(EndMsgFormat.getMsg(gameTable, winners));
		updateGameTableAndPlayers(winners);
	}

	private Set<Player> getWinnersByNotFolded(){
		Set<Player> winners = new HashSet<>();

		for(Player player: gameTable.getPlayers()){
			if(player != null && player.getState() != PlayerStateProperties.AFTERFOLD){
				winners.add(player);
			}
		}

		return winners;
	}

	private Set<Player> getWinnersByHandStrength(Player[] players){
		Set<Player> winnersSet = new HashSet<>();

		for(int i = 1; i < players.length; i++){
			winnersSet.add(players[i - 1]);
			if(players[i - 1].getHandStrength() > players[i].getHandStrength()){
				break;
			}
		}

		return winnersSet;
	}

	private Player[] getPlayersSortedByHandStrength(){
		PokerHandCalculator calculator = new PokerHandCalculator(gameTable.getTableCards());
		Player[] players = gameTable.getPlayers().clone();

		for(Player player: players){
			player.setHandStrength(calculator.handStrength(player.getHandCards()));
		}

		Arrays.sort(players, (o1, o2) -> Double.compare(o2.getHandStrength(), o1.getHandStrength()));

		return players;
	}

	private void updateGameTableAndPlayers(Set<Player> winners){
		int prize = gameTable.getPotValue()/winners.size();
		Player[] players = gameTable.getPlayers();

		for(Player player: players){
			if(player != null){
				if(winners.contains(player)){
					player.setMoney(player.getMoney() + prize);
				}
				player.setPotValue(0);
				player.setState(PlayerStateProperties.INGAME);
			}
		}

		gameTable.setPotValue(0);
	}

}