package poker.server.communication.interpreters;

import org.json.JSONObject;
import poker.properties.PlayerStateProperties;
import poker.server.data.Player;
import poker.server.gamecontrollers.CycleController;

public class CallMsgInterpreter implements MsgInterpreter {

	@Override
	public void interpret(JSONObject msg, Player player, CycleController cycleController){
		int minPot = cycleController.getMinPot();

		int moneyToPut = Math.min(minPot - player.getPotValue(), player.getMoney());

		player.setMoney(player.getMoney() - moneyToPut);
		player.setPotValue(player.getPotValue() + moneyToPut);
		player.setState(PlayerStateProperties.INGAME);
	}

}