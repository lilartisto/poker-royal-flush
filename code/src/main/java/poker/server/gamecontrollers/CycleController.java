package poker.server.gamecontrollers;

import org.json.JSONException;
import org.json.JSONObject;
import poker.properties.PlayerMoveProperties;
import poker.properties.PlayerStateProperties;
import poker.server.communication.ClientConnector;
import poker.server.communication.interpreters.*;
import poker.server.communication.msgformats.GameInfoMsgFormat;
import poker.server.communication.msgformats.MoveRequestMsgFormat;
import poker.server.data.GameTable;
import poker.server.data.Player;

import java.io.IOException;

public class CycleController {

	private final GameTable gameTable;
	private final ClientConnector clientConnector;
	private int starterPlayer;
	private int minPot;

	public CycleController(GameTable gameTable, ClientConnector clientConnector){
		this.gameTable = gameTable;
		starterPlayer = gameTable.getStarterPlayerIndex();
		this.clientConnector = clientConnector;
	}

	public void setMinPot(int minPot){
		this.minPot = minPot;
	}

	public int getMinPot(){
		return minPot;
	}

	public void setStarterPlayer(Player player){
		Player[] players = gameTable.getPlayers();

		for(int i = 0; i < players.length; i++){
			if(players[i] != null && players[i].nickname.equals(player.nickname)){
				starterPlayer = i;
				return;
			}
		}
	}

	public void playCycle(boolean firstCycle){
		Player[] players = gameTable.getPlayers();
		starterPlayer = gameTable.getStarterPlayerIndex();

		if(firstCycle){
			firstMove(players[starterPlayer]);
		} else {
			nextMove(players[starterPlayer]);
		}

		for(int i = starterPlayer + 1; i != starterPlayer; i = (i + 1) % 6){
			if(isOver()){
				addPlayersPotsToTablePot(players);
				throw new IllegalStateException("Players have folded or disconnected");
			} else if(players[i] != null) {
				nextMove(players[i]);
			}
		}

		addPlayersPotsToTablePot(players);
	}

	private void addPlayersPotsToTablePot(Player[] players){
		int pot = 0;

		for (Player player : players) {
			if (player != null) {
				pot += player.getPotValue();
				player.setPotValue(0);
			}
		}

		gameTable.setPotValue(gameTable.getPotValue() + pot);
	}

	private void firstMove(Player player){
		if(player == null){
			return;
		}

		if(player.getMoney() >= minPot){
			player.setMoney(player.getMoney() - minPot);
			player.setPotValue(minPot);
			sendGameInfo();
		} else {
			//TODO
			// delete player + other cases
			return;
		}
	}

	private boolean isOver(){
		return gameTable.numberOfActivePlayers() <= 1;
	}

	private void nextMove(Player player){
		if(player == null || player.getState() == PlayerStateProperties.AFTERFOLD || player.getMoney() == 0){
			return;
		}

		player.setState(PlayerStateProperties.INMOVE);
		sendGameInfo();

		int pot = Math.min(player.getMoney(), minPot);
		clientConnector.sendMsg(MoveRequestMsgFormat.getMsg(pot), player);
		receiveMsg(player);
		sendGameInfo();
	}

	private void sendGameInfo(){
		clientConnector.sendMsgToAll(GameInfoMsgFormat.getMsg(gameTable));
	}

	private void receiveMsg(Player player){
		long maxWaitingTime = 20 * 1000;
		long threadSleepingTime = 20;
		int maxLoopCounter = (int)(maxWaitingTime/threadSleepingTime);

		for(int i = 0; i < maxLoopCounter; i++) {
			try {
				String msg = clientConnector.getPlayerLastMsg(player);
				if (msg != null) {
					interpret(msg, player);
					return;
				}

				Thread.sleep(20);
			} catch (InterruptedException e){
				System.err.println(e.getMessage());
				return;
			} catch (IllegalArgumentException e){
				return;
			}
		}
		player.setState(PlayerStateProperties.AFTERFOLD);
	}

	private void interpret(String msg, Player player){
		JSONObject jsonMsg = new JSONObject(msg);
		try{
			int type = jsonMsg.getInt("type");
			MsgInterpreter interpreter;

			if(type == PlayerMoveProperties.CALL){
				interpreter = new CallMsgInterpreter();
			} else if(type == PlayerMoveProperties.FOLD){
				interpreter = new FoldMsgInterpreter();
			} else if(type == PlayerMoveProperties.CHECK){
				interpreter = new CheckMsgInterpreter();
			} else if(type == PlayerMoveProperties.RAISE){
				interpreter = new RaiseMsgInterpreter();
			} else {
				return;
			}
			interpreter.interpret(jsonMsg, player, this);
		} catch(JSONException ignored){}
	}
}