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

	public void playCycle(){
		Player[] players = gameTable.getPlayers();
		starterPlayer = gameTable.getStarterPlayerIndex();
		firstMove(players[starterPlayer]);

		for(int i = starterPlayer + 1; i != starterPlayer; i = (i + 1) % 6){
			if(isOver()){
				throw new IllegalStateException("Players have folded");
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
		if(player != null){
			nextMove(player);
		}
	}

	private boolean isOver(){
		return gameTable.numberOfActivePlayers() <= 1;
	}

	private void nextMove(Player player){
		if(player.getMoney() == 0){
			return;
		}

		player.setState(PlayerStateProperties.INMOVE);
		sendGameInfo();

		int pot = Math.min(player.getMoney(), minPot);
		clientConnector.sendMsg(MoveRequestMsgFormat.getMsg(pot), player);
		interpret(clientConnector.listenForPlayerMsg(player), player);
	}

	private void sendGameInfo(){
		clientConnector.sendMsgToAll(GameInfoMsgFormat.getMsg(gameTable));
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