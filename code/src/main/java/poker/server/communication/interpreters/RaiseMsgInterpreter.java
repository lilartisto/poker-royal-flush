package poker.server.communication.interpreters;

import org.json.JSONObject;
import poker.properties.PlayerStateProperties;
import poker.server.data.Player;
import poker.server.gamecontrollers.CycleController;

public class RaiseMsgInterpreter implements MsgInterpreter {

	public void interpret(JSONObject msg, Player player, CycleController cycleController){
		int raiseValue = msg.getInt("value");

		cycleController.setMinPot(raiseValue);
		cycleController.setStarterPlayer(player);

		player.setMoney(player.getMoney() - (raiseValue - player.getPotValue()));
		player.setPotValue(raiseValue);
		player.setState(PlayerStateProperties.INGAME);
	}

}