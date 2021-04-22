package poker.server.communication.interpreters;

import org.json.JSONObject;
import poker.properties.PlayerStateProperties;
import poker.server.data.Player;
import poker.server.gamecontrollers.CycleController;

public class CallMsgInterpreter implements MsgInterpreter {

	@Override
	public void interpret(JSONObject msg, Player player, CycleController cycleController){
		int pot = cycleController.getMinPot();

		player.setPotValue(player.getPotValue() + pot);
		player.setMoney(player.getMoney() - pot);
		player.setState(PlayerStateProperties.INGAME);
	}

}